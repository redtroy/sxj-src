package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.windowref.LinkStateEnum;

public class LinkStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<LinkStateEnum> {
	public LinkStateEnumTypeHandler(Class<LinkStateEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
}
