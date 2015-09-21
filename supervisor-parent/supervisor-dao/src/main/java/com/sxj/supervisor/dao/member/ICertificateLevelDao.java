package com.sxj.supervisor.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.member.CertificateLevelEntity;

public interface ICertificateLevelDao
{
    
    /**
     * 添加证书等级
     * 
     * @throws SQLException
     */
    @Insert
    public void addLevel(CertificateLevelEntity certificateLevelEntity)
            throws SQLException;
    
    /**
     * 更新证书等级
     * 
     * @throws SQLException
     */
    @Update
    public void updateLevel(CertificateLevelEntity certificateLevelEntity)
            throws SQLException;
    
    /**
     * 获取证书等级
     * 
     * @param imageId
     * @return
     * @throws SQLException
     */
    public List<CertificateLevelEntity> getCertificateLevel(String imageId)
            throws SQLException;
    
}
