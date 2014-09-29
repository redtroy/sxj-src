package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.ContractWindowTypeEnum;


public class ContractWindowTypeEnumTypeHandler  extends
		EnumOrdinalTypeHandler<ContractWindowTypeEnum> {
	public ContractWindowTypeEnumTypeHandler(Class<ContractWindowTypeEnum> type) {
		super(type);
	}

}
