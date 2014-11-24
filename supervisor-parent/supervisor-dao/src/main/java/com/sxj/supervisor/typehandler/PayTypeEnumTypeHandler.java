package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.contract.PayTypeEnum;

public class PayTypeEnumTypeHandler extends EnumOrdinalTypeHandler<PayTypeEnum> {
	public PayTypeEnumTypeHandler(Class<PayTypeEnum> type) {
		super(type);
	}

}
