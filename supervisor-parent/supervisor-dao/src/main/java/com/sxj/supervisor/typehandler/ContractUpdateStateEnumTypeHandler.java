package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.ContractUpdateStateEnum;

public class ContractUpdateStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<ContractUpdateStateEnum> {

	public ContractUpdateStateEnumTypeHandler(
			Class<ContractUpdateStateEnum> type) {
		super(type);
	}

}
