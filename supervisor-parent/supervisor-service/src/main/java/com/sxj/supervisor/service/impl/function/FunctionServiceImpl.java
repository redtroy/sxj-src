package com.sxj.supervisor.service.impl.function;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.system.IFunctionDao;
import com.sxj.supervisor.entity.system.FunctionEntity;
import com.sxj.supervisor.model.function.FunctionModel;
import com.sxj.supervisor.service.system.IFunctionService;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {
	@Autowired
	private IFunctionDao functiondao;
    
	
	/**
	 * 获取左侧菜单
	 */
	@Override
	@Transactional(readOnly=true)
	public List<FunctionModel> queryFunctions() {
		QueryCondition<FunctionEntity> query = new QueryCondition<FunctionEntity>();
		query.addCondition("parentId", 0);
		List<FunctionEntity> functionList = functiondao.queryFunction(query);
		List<FunctionModel> list = new ArrayList<FunctionModel>();
		for (FunctionEntity functionEntity : functionList) {
			if (functionEntity == null) {
				continue;
			}
			QueryCondition<FunctionEntity> childrenQuery = new QueryCondition<FunctionEntity>();
			childrenQuery.addCondition("parentId", functionEntity.getId());
			List<FunctionEntity> childrenList = functiondao
					.queryFunction(childrenQuery);
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
	@Transactional(readOnly=true)
	public FunctionEntity getFunction(String id) {
		FunctionEntity fe = functiondao.getFunction(id);
		return fe;
	}

}
