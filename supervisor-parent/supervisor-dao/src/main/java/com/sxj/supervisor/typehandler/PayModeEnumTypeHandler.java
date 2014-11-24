package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayModeEnum;

public class PayModeEnumTypeHandler extends EnumOrdinalTypeHandler<PayModeEnum> {
	public PayModeEnumTypeHandler(Class<PayModeEnum> type) {
		super(type);
	}

}
