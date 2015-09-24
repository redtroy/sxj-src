package com.sxj.supervisor.service.impl.member;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.ca.store.CaTool;
import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.spring.modules.util.Identities;
import com.sxj.spring.modules.util.Reflections;
import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.dao.member.IMemberToMemberDao;
import com.sxj.supervisor.dao.member.IRelevanceMemberDao;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.supervisor.entity.member.RelevanceMember;
import com.sxj.supervisor.entity.message.MessageConfigEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.message.MessageTypeEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.model.open.ApiModel;
import com.sxj.supervisor.service.member.IMemberImageService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.EncryptUtil;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.message.NewSendMessage;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class MemberServiceImpl implements IMemberService
{
    
    @Autowired
    private IMemberDao menberDao;
    
    @Autowired
    private RedisTopics redisTopics;
    
    @Autowired
    private IMessageConfigService configService;
    
    @Autowired
    private IMemberToMemberDao memberToMemberDao;
    
    @Autowired
    private IMemberImageService memberImageService;
    
    @Autowired
    private IRelevanceMemberDao relevanceMemberDao;
    
    @Value("${mobile.smsUrl}")
    private String smsUrl;
    
    @Value("${mobile.userName}")
    private String userName;
    
    @Value("${mobile.password}")
    private String password;
    
    @Value("${mobile.sign}")
    private String sign;
    
    @Value("${mobile.type}")
    private String type;
    
    @Value("${ca.employeePath}")
    private String employeePath;
    
    @Value("${ca.cilentName}")
    private String cilentName;
    
    @Value("${ca.cilentPath}")
    private String cilentPath;
    
    @Value("${ca.days}")
    private String days;
    
    @Value("${ca.au}")
    private String au;
    
    @Value("${ca.caName}")
    private String caName;
    
    @Value("${ca.caPath}")
    private String caPath;
    
    @Value("${ca.interName}")
    private String interName;
    
    @Value("${ca.interPath}")
    private String interPath;
    
    @Value("${ca.serverName}")
    private String serverName;
    
    @Value("${ca.serverPath}")
    private String serverPath;
    
    @Value("${ca.password}")
    private String caPassword;
    
    @Value("${mobile.serviceURL}")
    private String serviceURL;
    
    @Value("${mobile.sn}")
    private String sn;
    
    @Value("${mobile.pwd}")
    private String pwd;
    
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
            else if (MemberTypeEnum.DEVELOPERS.equals(member.getType()))
            {
                member.setNoType("D");
            }
            else if (MemberTypeEnum.PRODUCTS.equals(member.getType()))
            {
                member.setNoType("P");
            }
            else if (MemberTypeEnum.FRAMEFACTORY.equals(member.getType()))//副框厂
            {
                member.setNoType("F");
            }
            else if (MemberTypeEnum.AGENT.equals(member.getType())
                    || MemberTypeEnum.DISTRIBUTOR.equals(member.getType()))//如果为代理商，经销商则用型材厂类型
            {
                member.setNoType("X");
            }
            else
            {
                member.setNoType("MEM");
            }
            menberDao.addMember(member);
            //如果是代理商或者是经销商
            if (MemberTypeEnum.AGENT.equals(member.getType())
                    || MemberTypeEnum.DISTRIBUTOR.equals(member.getType()))
            {
                MemberToMemberEntity mm = new MemberToMemberEntity();
                mm.setMemberNo(member.getMemberNo());
                mm.setParentNo(member.getParentNo());
                mm.setParentName(member.getParentName());
                mm.setMemberType(member.getType().getId());
                mm.setMemberName(member.getName());
                memberToMemberDao.addTo(mm);
            }
            List<MessageConfigEntity> configList = buildMessageConfig(member);
            configService.addConfig(member.getMemberNo(), configList);
            CometServiceImpl.takeCount(MessageChannel.MEMBER_MESSAGE);
            redisTopics.getTopic(MessageChannel.TOPIC_NAME)
                    .publish(MessageChannel.MEMBER_MESSAGE);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    private List<MessageConfigEntity> buildMessageConfig(MemberEntity member)
    {
        List<MessageConfigEntity> configList = new ArrayList<>();
        MessageConfigEntity config1 = new MessageConfigEntity();
        config1.setIsAccetp(true);
        config1.setMemberNo(member.getMemberNo());
        config1.setPhone(member.getPhoneNo());
        config1.setMessageType(MessageTypeEnum.CONTRACT);
        configList.add(config1);
        
        MessageConfigEntity config2 = new MessageConfigEntity();
        config2.setIsAccetp(true);
        config2.setMemberNo(member.getMemberNo());
        config2.setPhone(member.getPhoneNo());
        config2.setMessageType(MessageTypeEnum.DELIVER);
        configList.add(config2);
        
        MessageConfigEntity config3 = new MessageConfigEntity();
        config3.setIsAccetp(true);
        config3.setMemberNo(member.getMemberNo());
        config3.setPhone(member.getPhoneNo());
        config3.setMessageType(MessageTypeEnum.PAY);
        configList.add(config3);
        
        MessageConfigEntity config4 = new MessageConfigEntity();
        config4.setIsAccetp(true);
        config4.setMemberNo(member.getMemberNo());
        config4.setPhone(member.getPhoneNo());
        config4.setMessageType(MessageTypeEnum.RECEIPT);
        configList.add(config4);
        
        MessageConfigEntity config5 = new MessageConfigEntity();
        config5.setIsAccetp(true);
        config5.setMemberNo(member.getMemberNo());
        config5.setPhone(member.getPhoneNo());
        config5.setMessageType(MessageTypeEnum.SMS);
        configList.add(config5);
        
        MessageConfigEntity config6 = new MessageConfigEntity();
        config6.setIsAccetp(true);
        config6.setMemberNo(member.getMemberNo());
        config6.setPhone(member.getPhoneNo());
        config6.setMessageType(MessageTypeEnum.SYSTEM);
        configList.add(config6);
        
        MessageConfigEntity config7 = new MessageConfigEntity();
        config7.setIsAccetp(true);
        config7.setMemberNo(member.getMemberNo());
        config7.setPhone(member.getPhoneNo());
        config7.setMessageType(MessageTypeEnum.TENDER);
        configList.add(config7);
        return configList;
    }
    
    /**
     * 更新会员
     */
    @Override
    @Transactional
    public MemberEntity modifyMember(MemberEntity member, Boolean flag)
            throws ServiceException
    {
        try
        {
            MemberEntity m = menberDao.getMember(member.getId());
            if (member.getType() == null
                    || member.getType().equals(m.getType()))
            {
                member.setVersion(m.getVersion());
                menberDao.updateMember(member);
                List<MessageConfigEntity> configList = configService.queryConfigList(m.getMemberNo());
                for (Iterator<MessageConfigEntity> iterator = configList.iterator(); iterator.hasNext();)
                {
                    MessageConfigEntity messageConfig = iterator.next();
                    if (messageConfig == null)
                    {
                        continue;
                    }
                    messageConfig.setPhone(member.getPhoneNo());
                }
                configService.updateConfig(configList);
            }
            else
            {
                assembleMemeberToModify(member, m);
                menberDao.deleteMember(member.getId());
                menberDao.addMember(m);
                List<MessageConfigEntity> configList = buildMessageConfig(member);
                configService.addConfig(member.getMemberNo(), configList);
            }
            // 如果该会员之前没有完善会员资料，则提示
            if (!m.getFlag() && flag)
            {
                CometServiceImpl.takeCount(MessageChannel.MEMBER_PERFECT_MESSAGE);
                redisTopics.getTopic(MessageChannel.TOPIC_NAME)
                        .publish(MessageChannel.MEMBER_PERFECT_MESSAGE);
            }
            return menberDao.getMember(member.getId());
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
            case DEVELOPERS:
                m.setNoType("D");
                break;
            case PRODUCTS:
                m.setNoType("P");
                break;
            case FRAMEFACTORY:
                m.setNoType("F");
                break;
            case AGENT:
                m.setNoType("X");
                break;
            case DISTRIBUTOR:
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
     * 前台会员中心查询兼容新老3C，资质证书图片
     */
    @Override
    public MemberEntity getMemberNew(String id)
    {
        try
        {
            MemberEntity member = menberDao.getMember(id);
            List<MemberImageEntity> list = memberImageService.getImages(member.getMemberNo(),
                    "1");
            if (list.size() > 0)
            {
                String images = "";
                for (MemberImageEntity mImage : list)
                {
                    images = images + mImage.getImage() + ",";
                }
                images = images.substring(0, images.length() - 1);
                if (member.getType().getId() == 1)
                {
                    member.setCcc_img(images);
                }
                else if (member.getType().getId() == 0)
                {
                    member.setQualification_img(images);
                }
                
            }
            return member;
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
            condition.addCondition("flag", query.getFlag());
            condition.addCondition("changeImageFlag",
                    query.getChangeImageFlag());
            condition.addCondition("startAuthorDate",
                    query.getStartAuthorDate());// 开始时间
            condition.addCondition("endAuthorDate", query.getEndAuthorDate());// 结束时间
            condition.addCondition("sort", query.getSort());//排序
            condition.addCondition("filterStr", query.getFilterStr());//筛选测试账号
            condition.setPage(query);
            if (query.getMemberType() == MemberTypeEnum.FRAMEFACTORY.getId())
            {
                memberList = menberDao.queryFramefactory(condition);
            }
            else
            {
                memberList = menberDao.queryMembers(condition);
            }
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
    
    @Override
    public List<RelevanceMember> getListRelevanceMember(String memberNo)
            throws ServiceException
    {
        try
        {
            List<RelevanceMember> list = relevanceMemberDao.getEnityBymemberNo(memberNo);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询新上传资质证书", e, this.getClass());
            throw new ServiceException("查询新上传资质证书", e);
        }
    }
    
    @Override
    @Transactional
    public String addRelevanceMember(List<RelevanceMember> list)
            throws ServiceException
    {
        try
        {
            relevanceMemberDao.del(list.get(0).getMemberNo());
            for (RelevanceMember re : list)
            {
                relevanceMemberDao.add(re);
            }
            return "ok";
        }
        catch (Exception e)
        {
            SxjLogger.error("新增关联企业错误", e, this.getClass());
            throw new ServiceException("新增关联企业错误", e);
        }
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
            NewSendMessage.getInstance(smsUrl, userName, password, sign, type)
                    .sendMessage(phoneNo, message + "(平台注册验证码，10分钟有效)");
            //            NewNewSendMessage.getInstance(serviceURL, sn, pwd)
            //                    .sendMessage(phoneNo, message + "(平台注册验证码，10分钟有效)");
        }
        catch (Exception e)
        {
            SxjLogger.error("发送验证码错误", e, this.getClass());
            throw new ServiceException("发送验证码错误", e);
        }
        return message;
    }
    
    /**
     * 会员高级查询
     */
    @Override
    @Transactional(readOnly = true)
    public List<ApiModel> apiQueryMembers(String name, String type, String city)
            throws ServiceException
    {
        try
        {
            List<MemberEntity> memberList = new ArrayList<MemberEntity>();
            QueryCondition<MemberEntity> condition = new QueryCondition<MemberEntity>();
            if (!StringUtils.isEmpty(name))
            {
                try
                {
                    condition.addCondition("name",
                            new String(name.getBytes("iso8859-1"), "utf-8"));// 会员名称
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
            condition.addCondition("type", type);// 会员类型
            condition.addCondition("city", city);// 会员类型
            memberList = menberDao.apiQueryMembers(condition);
            List<ApiModel> apiList = new ArrayList<ApiModel>();
            for (MemberEntity memberEntity : memberList)
            {
                ApiModel api = new ApiModel();
                api.setId(memberEntity.getId());
                api.setName(memberEntity.getName());
                api.setTel(memberEntity.getTelNum());
                api.setAddress(memberEntity.getAddress());
                apiList.add(api);
                
            }
            return apiList;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new ServiceException("查询会员信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public String createPfx(MemberEntity member, AccountEntity account)
            throws ServiceException
    {
        try
        {
            String employeeName = member.getName() + "_"
                    + account.getAccountName() + "_employee";
            String commonName = member.getName() + "_"
                    + account.getAccountName();
            String giveName = member.getMemberNo() + ","
                    + account.getAccountNo();
            CaTool.createEmployeeCSR(employeeName,
                    employeePath,
                    commonName,
                    au,
                    giveName);
            CaTool.createEmployeeCert(employeeName,
                    employeePath,
                    cilentName,
                    cilentPath,
                    Integer.parseInt(days));
            CaTool.createEmployeePfx(caName,
                    caPath,
                    interName,
                    interPath,
                    serverName,
                    serverPath,
                    cilentName,
                    cilentPath,
                    employeeName,
                    employeePath,
                    caPassword);
            return employeePath;
        }
        catch (Exception e)
        {
            SxjLogger.error("生成用户证书失败", e, this.getClass());
            throw new ServiceException("生成用户证书失败", e);
        }
        
    }
    
    @Override
    @Transactional
    public MemberEntity websiteModifyMember(MemberEntity member)
            throws ServiceException
    {
        try
        {
            MemberEntity newMember = modifyMember(member, true);
            memberImageService.websiteAddImage(member.getMemberNo(),
                    member.getQualification_img());
            newMember.setQualification_img(member.getQualification_img());
            return newMember;
        }
        catch (Exception e)
        {
            SxjLogger.error("更新会员失败", e, this.getClass());
            throw new ServiceException("更新会员失败", e);
        }
    }
    
    @Override
    public void ChangeImageFlagClear() throws ServiceException
    {
        try
        {
            menberDao.updateChangeImageFlag();
        }
        catch (Exception e)
        {
            SxjLogger.error("资质证书标记清0 报错", e, this.getClass());
            throw new ServiceException("资质证书标记清0 报错", e);
        }
        
    }
}
