package com.sxj.finance.service.member;

import java.util.List;

import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.model.member.MemberQuery;
import com.sxj.util.exception.ServiceException;

public interface IMemberService {

	MemberEntity memberInfo(String memberNo) throws ServiceException;

	List<MemberEntity> queryMembers(MemberQuery query) throws ServiceException;

	MemberEntity getMember(String id);

	void editCheckState(String id, Integer state) throws ServiceException;

	
}
