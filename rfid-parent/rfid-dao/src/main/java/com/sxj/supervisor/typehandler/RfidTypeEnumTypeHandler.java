package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

<<<<<<< HEAD
import com.sxj.supervisor.enu.rfid.window.RfidTypeEnum;


public class RfidTypeEnumTypeHandler  extends
		EnumOrdinalTypeHandler<RfidTypeEnum> {
	public RfidTypeEnumTypeHandler(Class<RfidTypeEnum> type) {
		super(type);
=======
import com.sxj.supervisor.Enum.rfid.base.RfidTypeEnum;

public class RfidTypeEnumTypeHandler extends
		EnumOrdinalTypeHandler<RfidTypeEnum> {

	public RfidTypeEnumTypeHandler(Class<RfidTypeEnum> type) {
		super(type);
		// TODO Auto-generated constructor stub
>>>>>>> branch 'master' of scm@192.168.1.10:/home/scm/repositories/sxj-src.git
	}

}
