package com.sxj.supervisor.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IMemberToMemberDao;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.supervisor.service.member.IMemberToMemberService;
import com.sxj.util.exception.ServiceException;
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
    
}
