package com.sxj.supervisor.service.impl.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IMemberFunctionDao;
import com.sxj.supervisor.entity.member.MemberFunctionEntity;
import com.sxj.supervisor.model.member.MemberFunctionModel;
import com.sxj.supervisor.service.member.IMemberFunctionService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class MemberFunctionServiceImpl implements IMemberFunctionService {

	@Autowired
	private IMemberFunctionDao functionDao;

	@Override
	@Transactional(readOnly = true)
	public List<MemberFunctionEntity> queryChildrenFunctions(String parentId)
			throws ServiceException {
		try {
			QueryCondition<MemberFunctionEntity> query = new QueryCondition<MemberFunctionEntity>();
			query.addCondition("parentid", parentId);
			List<MemberFunctionEntity> list = functionDao.queryFunctions(query);
			return list;
		} catch (Exception e) {
			SxjLogger.error("查询会员菜单错误", e, this.getClass());
			throw new ServiceException("查询会员菜单错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<MemberFunctionModel> queryFunctions() throws ServiceException {
		try {
			QueryCondition<MemberFunctionEntity> query = new QueryCondition<MemberFunctionEntity>();
			query.addCondition("parentId", "0");
			List<MemberFunctionEntity> functionList = functionDao
					.queryFunctions(query);
			List<MemberFunctionModel> list = new ArrayList<MemberFunctionModel>();
			for (MemberFunctionEntity functionEntity : functionList) {
				if (functionEntity == null) {
					continue;
				}
				QueryCondition<MemberFunctionEntity> childrenQuery = new QueryCondition<MemberFunctionEntity>();
				childrenQuery.addCondition("parentId", functionEntity.getId());
				List<MemberFunctionEntity> childrenList = functionDao
						.queryFunctions(childrenQuery);
				MemberFunctionModel model = new MemberFunctionModel();
				model.setFunction(functionEntity);
				model.setChildren(childrenList);
				list.add(model);

			}
			return list;
		} catch (Exception e) {
			SxjLogger.error("查询所有会员菜单错误", e, this.getClass());
			throw new ServiceException("查询所有会员菜单错误", e);
		}
	}

}
