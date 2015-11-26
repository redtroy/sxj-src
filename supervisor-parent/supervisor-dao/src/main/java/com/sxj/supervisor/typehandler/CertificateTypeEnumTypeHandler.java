package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.member.CertificateTypeEnum;

public class CertificateTypeEnumTypeHandler extends
		EnumOrdinalTypeHandler<CertificateTypeEnum> {
	public CertificateTypeEnumTypeHandler(Class<CertificateTypeEnum> type) {
		super(type);
	}
}
