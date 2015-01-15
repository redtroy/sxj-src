package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.applymanager.M_PayStateEnum;

public class M_PayStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<M_PayStateEnum> {

	public M_PayStateEnumTypeHandler(Class<M_PayStateEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
