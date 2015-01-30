package com.sxj.supervisor.service.impl.member;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.spring.modules.util.Identities;
import com.sxj.spring.modules.util.Reflections;
import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.CometServiceImpl;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.message.SendMessage;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class MemberServiceImpl implements IMemberService
{
    
    @Autowired
    private IMemberDao menberDao;
    
    @Value("${mobile.sOpenUrl}")
    private String sOpenUrl;
    
    @Value("${mobile.sDataUrl}")
    private String sDataUrl;
    
    @Value("${mobile.account}")
    private String account;
    
    @Value("${mobile.authkey}")
    private String authkey;
    
    @Value("${mobile.cgid}")
    private Integer cgid;
    
    @Value("${mobile.csid}")
    private Integer csid;
    
    /**
     * 新增会员
     */
    @Override
    @Transactional
    public void addMember(MemberEntity member) throws ServiceException
    {
        try
        {
            MemberEntity oldMember = getMemberByName(member.getName());
            if (oldMember != null)
            {
                throw new ServiceException("会员名称已经存在");
            }
            if (MemberTypeEnum.DAWP.equals(member.getType()))
            {
                member.setNoType("M");
            }
            else if (MemberTypeEnum.GLASSFACTORY.equals(member.getType()))
            {
                member.setNoType("B");
            }
            else if (MemberTypeEnum.GENRESFACTORY.equals(member.getType()))
            {
                member.setNoType("X");
            }
            else
            {
                member.setNoType("MEM");
            }
            menberDao.addMember(member);
            CometServiceImpl.takeCount(MessageChannel.MEMBER_MESSAGE);
            MessageChannel.initTopic().publish(MessageChannel.MEMBER_MESSAGE);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    /**
     * 更新会员
     */
    @Override
    @Transactional
    public void modifyMember(MemberEntity member) throws ServiceException
    {
        try
        {
            MemberEntity m = menberDao.getMember(member.getId());
            if (member.getType() == null
                    || member.getType().equals(m.getType()))
            {
                member.setVersion(m.getVersion());
                menberDao.updateMember(member);
            }
            else
            {
                assembleMemeberToModify(member, m);
                menberDao.addMember(m);
                menberDao.deleteMember(member.getId());
            }
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("会员信息更新失败！", e);
        }
    }
    
    private void assembleMemeberToModify(MemberEntity member, MemberEntity m)
    {
        switch (member.getType())
        {
            case DAWP:
                m.setNoType("M");
                break;
            case GLASSFACTORY:
                m.setNoType("B");
                break;
            case GENRESFACTORY:
                m.setNoType("X");
                break;
            default:
                m.setNoType("MEM");
                break;
        }
        // if (MemberTypeEnum.DAWP.equals(member.getType())) {
        // m.setNoType("M");
        // } else if (MemberTypeEnum.glassFactory.equals(member.getType())) {
        // m.setNoType("B");
        // } else if (MemberTypeEnum.genresFactory
        // .equals(member.getType())) {
        // m.setNoType("X");
        // } else {
        // m.setNoType("MEM");
        // }
        Class<? extends MemberEntity> c = member.getClass();
        Field field[] = c.getDeclaredFields();
        for (Field f : field)
        {
            if ((!f.getName().equals("id"))
                    && (!"serialVersionUID".equals(f.getName()))
                    && Reflections.getFieldValue(member, f.getName()) != null)
            {
                Reflections.setFieldValue(m,
                        f.getName(),
                        Reflections.getFieldValue(member, f.getName()));
            }
        }
        m.setId(null);
    }
    
    /**
     * 查找会员
     */
    @Override
    @Transactional(readOnly = true)
    public MemberEntity getMember(String id)
    {
        try
        {
            return menberDao.getMember(id);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("会员查找失败！", e);
        }
    }
    
    /**
     * 会员高级查询
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberEntity> queryMembers(MemberQuery query)
            throws ServiceException
    {
        try
        {
            List<MemberEntity> memberList = new ArrayList<MemberEntity>();
            if (query == null)
            {
                return memberList;
            }
            QueryCondition<MemberEntity> condition = new QueryCondition<MemberEntity>();
            condition.addCondition("memberNo", query.getMemberNo());// 会员号
            condition.addCondition("name", query.getMemberName());// 会员名称
            condition.addCondition("contacts", query.getContacts());// 联系人名称
            condition.addCondition("phoneNo", query.getContactsPhone());// 联系人电话
            condition.addCondition("area", query.getArea());// 地理区域
            condition.addCondition("bLicenseNo", query.getbLicenseNo());// 营业执照号
            condition.addCondition("energyNo", query.getEnergyNo());// 节能标识号
            condition.addCondition("type", query.getMemberType());// 会员类型
            condition.addCondition("checkState", query.getCheckState());
            condition.addCondition("state", query.getMemberState());
            condition.addCondition("startDate", query.getStartDate());// 开始时间
            condition.addCondition("endDate", query.getEndDate());// 结束时间
            condition.addCondition("typeB", query.getMemberTypeB());
            condition.setPage(query);
            memberList = menberDao.queryMembers(condition);
            query.setPage(condition);
            return memberList;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new ServiceException("查询会员信息错误", e);
        }
        
    }
    
    /**
     * 删除会员
     */
    @Override
    @Transactional
    public void removeMember(String id)
    {
        menberDao.deleteMember(id);
        
    }
    
    @Override
    @Transactional
    public String initializePwd(String memberId) throws ServiceException
    {
        try
        {
            MemberEntity member = menberDao.getMember(memberId);
            // 随机密码
            int maxNum = 999999;
            int rondom = NumberUtils.getRandomIntInMax(maxNum);
            String password = StringUtils.getLengthStr(rondom + "", 6, '0');
            String md5Passwrod = EncryptUtil.md5Hex(password);
            member.setPassword(md5Passwrod);
            menberDao.updateMember(member);
            return password;
        }
        catch (Exception e)
        {
            SxjLogger.error("初始化密码错误", e, this.getClass());
            throw new ServiceException("初始化密码错误", e.getMessage());
        }
        
    }
    
    /**
     * 更改账户状态
     */
    @Override
    @Transactional
    public void editState(String id, Integer state) throws ServiceException
    {
        try
        {
            if (state != null && StringUtils.isNotEmpty(id))
            {
                MemberEntity member = new MemberEntity();
                member.setId(id);
                member.setState(MemberStatesEnum.getEnum(state));
                member.setVersion(menberDao.getMember(id).getVersion());
                menberDao.updateMember(member);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("更改会员状态错误", e, this.getClass());
            throw new ServiceException("更改会员状态错误", e);
        }
        
    }
    
    /**
     * 更改审核状态
     */
    @Override
    @Transactional
    public void editCheckState(String id, Integer state)
            throws ServiceException
    {
        try
        {
            if (state != null && StringUtils.isNotEmpty(id))
            {
                MemberEntity member = new MemberEntity();
                member.setId(id);
                member.setCheckState(MemberCheckStateEnum.getEnum(state));
                if (state == 2)
                {
                    member.setAuthorDate(new Date());
                }
                member.setVersion(menberDao.getMember(id).getVersion());
                menberDao.updateMember(member);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("更改审核状态错误", e, this.getClass());
            throw new ServiceException("更改审核状态错误", e);
        }
        
    }
    
    /**
     * 根据会员号，查询会员信息
     */
    @Override
    @Transactional(readOnly = true)
    public MemberEntity memberInfo(String memberNo) throws ServiceException
    {
        MemberEntity member = new MemberEntity();
        if (StringUtils.isEmpty(memberNo))
        {
            return member;
        }
        MemberQuery query = new MemberQuery();
        query.setMemberNo(memberNo);
        List<MemberEntity> list = queryMembers(query);
        if (list.size() > 0)
        {
            member = list.get(0);
            return member;
        }
        return member;
    }
    
    /**
     * 修改密码
     */
    @Override
    @Transactional
    public void editPwd(String id, String pwd) throws ServiceException
    {
        try
        {
            MemberEntity member = new MemberEntity();
            String md5Passwrod = EncryptUtil.md5Hex(pwd);
            member.setId(id);
            member.setPassword(md5Passwrod);
            member.setVersion(menberDao.getMember(id).getVersion());
            menberDao.updateMember(member);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改会员超级密码错误", e, this.getClass());
            throw new ServiceException("修改会员超级密码错误", e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public MemberEntity getMemberByName(String name) throws ServiceException
    {
        try
        {
            return menberDao.getMemberByName(name);
        }
        catch (Exception e)
        {
            SxjLogger.error("根据会员名称查询会员错误", e, this.getClass());
            throw new ServiceException("根据会员名称查询会员错误", e);
        }
    }
    
    @Override
    public String createvalidata(String phoneNo, String message)
            throws ServiceException
    {
        try
        {
            message = Identities.randomNumber(6);
            SendMessage.getInstance(sOpenUrl,
                    sDataUrl,
                    account,
                    authkey,
                    cgid,
                    csid).sendMessage(phoneNo, message + "(平台注册验证码，10分钟有效)");
        }
        catch (Exception e)
        {
            SxjLogger.error("发送验证码错误", e, this.getClass());
            throw new ServiceException("发送验证码错误", e);
        }
        return message;
    }
}
