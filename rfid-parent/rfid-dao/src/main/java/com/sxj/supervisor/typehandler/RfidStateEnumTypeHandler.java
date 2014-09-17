package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.window.RfidStateEnum;


public class RfidStateEnumTypeHandler  extends
		EnumOrdinalTypeHandler<RfidStateEnum> {
	public RfidStateEnumTypeHandler(Class<RfidStateEnum> type) {
		super(type);
	}

}
