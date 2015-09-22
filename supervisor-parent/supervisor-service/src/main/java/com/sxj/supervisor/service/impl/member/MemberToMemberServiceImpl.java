package com.sxj.supervisor.service.impl.member;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IMemberToMemberDao;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.supervisor.service.member.IMemberToMemberService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class MemberToMemberServiceImpl implements IMemberToMemberService
{
    
    @Autowired
    private IMemberToMemberDao memberToMemberDao;
    
    @Override
    public void addMemberToMember(MemberToMemberEntity member)
            throws ServiceException
    {
        member.setCreateTime(new Date());
        memberToMemberDao.addTo(member);
        
    }
    
    @Override
    public List<MemberToMemberEntity> query(MemberToMemberEntity query)
            throws ServiceException
    {
        QueryCondition<MemberToMemberEntity> condition = new QueryCondition<MemberToMemberEntity>();
        condition.addCondition("memberNo", query.getMemberNo());// 会员id
        condition.addCondition("parentNo", query.getParentNo());// 型材厂id
        condition.addCondition("memberType", query.getMemberType());// 会员类型
        condition.setPage(query);
        List<MemberToMemberEntity> list = memberToMemberDao.query(condition);
        query.setPage(condition);
        return list;
    }
    
    @Override
    public void delInfo(String id) throws ServiceException
    {
        memberToMemberDao.deleteInfo(id);
        
    }
    
    @Override
    @Transactional
    public void delbyName(String memberNo) throws ServiceException
    {
        try
        {
            memberToMemberDao.del(memberNo);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除关联企业错误", e, this.getClass());
            throw new ServiceException("删除关联企业错误", e);
        }
    }
    
    @Override
    @Transactional
    public String addMemberToMember(List<MemberToMemberEntity> list)
            throws ServiceException
    {
        try
        {
            delbyName(list.get(0).getMemberNo());
            for (MemberToMemberEntity re : list)
            {
                addMemberToMember(re);
            }
            return "ok";
        }
        catch (Exception e)
        {
            SxjLogger.error("新增关联企业错误", e, this.getClass());
            throw new ServiceException("新增关联企业错误", e);
        }
    }
}
