package com.sxj.supervisor.service.member;

import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.util.persistent.ResultList;

public interface IMemberService {

	public void addMember(MemberEntity member);

	public void modifyMember(MemberEntity member);

	public MemberEntity getMember(String id);

	public ResultList<MemberEntity> queryMembers(MemberQuery query);

	public void removeMember(String id);
}
