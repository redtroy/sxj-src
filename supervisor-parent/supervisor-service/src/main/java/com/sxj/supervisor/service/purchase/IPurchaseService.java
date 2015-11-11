package com.sxj.supervisor.service.purchase;

import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.util.exception.ServiceException;

public interface IPurchaseService {

	/**
	 * 同步会员
	 * @param entity
	 * @throws ServiceException
	 */
	public void syncMember(MemberEntity entity)throws ServiceException;
}
