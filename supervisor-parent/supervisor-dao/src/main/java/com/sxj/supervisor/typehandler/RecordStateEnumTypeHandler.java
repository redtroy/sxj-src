package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.record.RecordStateEnum;

public class RecordStateEnumTypeHandler extends EnumOrdinalTypeHandler<RecordStateEnum>{

	public RecordStateEnumTypeHandler(Class<RecordStateEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
