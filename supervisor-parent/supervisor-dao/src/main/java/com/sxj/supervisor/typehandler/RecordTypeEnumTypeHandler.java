package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.record.RecordTypeEnum;

public class RecordTypeEnumTypeHandler  extends EnumOrdinalTypeHandler<RecordTypeEnum>{

	public RecordTypeEnumTypeHandler(Class<RecordTypeEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
