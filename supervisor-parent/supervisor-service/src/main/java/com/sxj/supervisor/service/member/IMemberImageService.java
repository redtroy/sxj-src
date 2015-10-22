package com.sxj.supervisor.service.member;

import java.sql.SQLException;
import java.util.List;

import com.sxj.supervisor.entity.member.CertificateEntity;
import com.sxj.supervisor.entity.member.CertificateLevelEntity;
import com.sxj.supervisor.entity.member.LevelEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.supervisor.model.member.MemberImageModel;
import com.sxj.util.exception.ServiceException;

public interface IMemberImageService
{
    
    public List<MemberImageEntity> getImages(String memberNo, String state)
            throws ServiceException;
    
    public List<CertificateLevelEntity> getCertificateLevels(String imageId)
            throws ServiceException;
    
    List<LevelEntity> getLevel(String name);
    
    List<CertificateEntity> getCertificate(String name);
    
    /**
     * 获取历史数据
     * 
     * @param memberNo
     * @return
     */
    List<MemberImageEntity> historyImage(String memberNo);
    
    /**
     * 被删除的数据
     * 
     * @param memberNo
     * @return
     * @throws ServiceException
     */
    List<MemberImageEntity> delImage(String memberNo) throws ServiceException;
    
    /**
     * 新的数据
     * 
     * @param memberNo
     * @return
     * @throws ServiceException
     */
    List<MemberImageEntity> newImage(String memberNo) throws ServiceException;
    
    /**
     * 新增图片（保存，前台）
     * @param memberNo
     * @param images
     */
    public void websiteAddImage(String memberNo, String images);
    
    /**
     * 新增图片（保存，后台）
     * @param list
     */
    public void ManageAddImage(List<MemberImageEntity> list);
    
    MemberImageModel getMemberImageByImageId(String imageId, String memberNo)
            throws ServiceException;
    
    void addMemberImage(MemberImageEntity memberImage, String[] levels)
            throws SQLException;
    
    public void delImageByImagePath(String image);
    
    public String getLevelStr(String memberNo) throws ServiceException;
}
