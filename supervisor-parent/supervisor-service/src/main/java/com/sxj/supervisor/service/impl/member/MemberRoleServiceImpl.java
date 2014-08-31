package com.sxj.supervisor.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.member.IMemberRoleDao;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.service.member.IMemberRoleService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
public class MemberRoleServiceImpl implements IMemberRoleService {

	@Autowired
	private IMemberRoleDao roleDao;

	@Override
	public List<MemberFunctionEntity> getAllRoleFunction(String accountId)
			throws ServiceException {
		try {
			List<MemberFunctionEntity> list = roleDao
					.getAllRoleFunction(accountId);
			return list;
		} catch (Exception e) {
			SxjLogger.error("查询会员账户所有权限列表错误", e, this.getClass());
			throw new ServiceException("查询会员账户所有权限列表错误");
		}
	}

}
