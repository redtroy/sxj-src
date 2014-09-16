package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.record.ContractTypeEnum;

public class ContractTypeEnumTypeHandler extends EnumOrdinalTypeHandler<ContractTypeEnum>{

	public ContractTypeEnumTypeHandler(Class<ContractTypeEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
