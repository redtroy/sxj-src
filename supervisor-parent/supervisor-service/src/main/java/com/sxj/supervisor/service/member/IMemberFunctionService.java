package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.util.exception.ServiceException;

public interface IMemberFunctionService {

	/**
	 * 读取子菜单
	 * 
	 * @return
	 */
	public List<MemberFunctionEntity> queryChildrenFunctions(String parentId)
			throws ServiceException;

	/**
	 * 读取所有菜单(按照层级)
	 * 
	 * @return
	 */
	public List<MemberFunctionModel> queryFunctions() throws ServiceException;


}
