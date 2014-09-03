package com.sxj.supervisor.model.member;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.member.MemberFunctionEntity;

public class MemberFunctionModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5577853171472611549L;

	private MemberFunctionEntity function;

	private List<MemberFunctionEntity> children;

	public MemberFunctionEntity getFunction() {
		return function;
	}

	public void setFunction(MemberFunctionEntity function) {
		this.function = function;
	}

	public List<MemberFunctionEntity> getChildren() {
		return children;
	}

	public void setChildren(List<MemberFunctionEntity> children) {
		this.children = children;
	}

}
