package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.purchase.DeliveryStateEnum;


public class DeliveryStateEnumTypeHandler  extends
		EnumOrdinalTypeHandler<DeliveryStateEnum> {
	public DeliveryStateEnumTypeHandler(Class<DeliveryStateEnum> type) {
		super(type);
	}

}
