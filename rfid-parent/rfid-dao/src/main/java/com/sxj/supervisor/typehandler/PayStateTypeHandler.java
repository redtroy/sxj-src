package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;


public class PayStateTypeHandler extends
		EnumOrdinalTypeHandler<PayStateEnum> {

	public PayStateTypeHandler(Class<PayStateEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
