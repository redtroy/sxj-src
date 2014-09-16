package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.window.RfidStateEnum;


public class WindowTypeEnumTypeHandler  extends
		EnumOrdinalTypeHandler<RfidStateEnum> {
	public WindowTypeEnumTypeHandler(Class<RfidStateEnum> type) {
		super(type);
	}

}
