package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;

public class AuditStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<AuditStateEnum> {

	public AuditStateEnumTypeHandler(Class<AuditStateEnum> type) {
		super(type);
	}

}
