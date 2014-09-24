package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.ref.AssociationTypes;

public class AssociationTypesTypeHandler extends
		EnumOrdinalTypeHandler<AssociationTypes> {

	public AssociationTypesTypeHandler(Class<AssociationTypes> type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

}
