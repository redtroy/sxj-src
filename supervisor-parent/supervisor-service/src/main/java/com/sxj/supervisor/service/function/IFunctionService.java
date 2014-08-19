package com.sxj.supervisor.service.function;

import java.util.List;

import com.sxj.supervisor.entity.member.FunctionEntity;
import com.sxj.supervisor.model.function.FunctionModel;


public interface IFunctionService {

	/**
	 * 
	 * @return
	 */
	public List<FunctionModel> queryFunctions();

	/**
	 * 
	 * @param id
	 * @return
	 */
	public FunctionEntity getFunction(String id);
}
