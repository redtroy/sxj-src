package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.util.exception.ServiceException;

public interface IMemberRoleService {
	
	public List<MemberFunctionEntity> getAllRoleFunction(String accountId)
			throws ServiceException;
}
