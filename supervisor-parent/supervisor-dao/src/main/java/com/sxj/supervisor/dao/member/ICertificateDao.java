package com.sxj.supervisor.dao.member;

import java.util.List;

import com.sxj.supervisor.entity.member.CertificateEntity;

public interface ICertificateDao {

	public List<CertificateEntity> getCertificate(String name);
}
