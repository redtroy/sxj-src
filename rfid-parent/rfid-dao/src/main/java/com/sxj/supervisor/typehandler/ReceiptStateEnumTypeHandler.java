package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.Enum.rfid.base.ReceiptStateEnum;

public class ReceiptStateEnumTypeHandler extends
		EnumOrdinalTypeHandler<ReceiptStateEnum> {

	public ReceiptStateEnumTypeHandler(Class<ReceiptStateEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
