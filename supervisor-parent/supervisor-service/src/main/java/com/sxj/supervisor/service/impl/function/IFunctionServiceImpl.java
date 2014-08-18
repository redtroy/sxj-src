package com.sxj.supervisor.service.impl.function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sxj.supervisor.dao.member.IFunctionDao;
import com.sxj.supervisor.entity.member.FunctionEntity;
import com.sxj.supervisor.model.function.FunctionModel;
import com.sxj.supervisor.service.function.IFunctionService;
import com.sxj.util.persistent.QueryCondition;

public class IFunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao functiondao;

	@Override
	public List<FunctionModel> queryFunctions() {
		QueryCondition<FunctionEntity> query = new QueryCondition<FunctionEntity>();
		query.addCondition("level", 1);
		List<FunctionEntity> functionList = functiondao.queryFunction(query);
		List<FunctionModel> list = new ArrayList<FunctionModel>();
		for (FunctionEntity functionEntity : functionList) {
			if (functionEntity == null) {
				continue;
			}
			QueryCondition<FunctionEntity> childrenQuery = new QueryCondition<FunctionEntity>();
			childrenQuery.addCondition("level", 2);
			List<FunctionEntity> childrenList = functiondao
					.queryFunction(query);
			FunctionModel model = new FunctionModel();
			model.setFunction(functionEntity);
			model.setChildren(childrenList);
			list.add(model);

		}
		return list;
	}

	/**
	 * 获取系统功能信息
	 */
	@Override
	public FunctionEntity getFunction(String id) {
		FunctionEntity fe = functiondao.getFunction(id);
		return fe;
	}

}
