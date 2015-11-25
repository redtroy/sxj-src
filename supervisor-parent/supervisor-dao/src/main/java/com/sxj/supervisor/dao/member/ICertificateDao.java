package com.sxj.supervisor.dao.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sxj.supervisor.entity.member.CertificateEntity;

public interface ICertificateDao
{
    
    public List<CertificateEntity> getCertificate(@Param("name") String name);
    
    public List<CertificateEntity> getListImage(String image);
}
