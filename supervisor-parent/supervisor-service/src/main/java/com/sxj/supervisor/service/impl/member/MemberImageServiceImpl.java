package com.sxj.supervisor.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.ICertificateDao;
import com.sxj.supervisor.dao.member.ICertificateLevelDao;
import com.sxj.supervisor.dao.member.ILevelDao;
import com.sxj.supervisor.dao.member.IMemberImageDao;
import com.sxj.supervisor.entity.member.CertificateEntity;
import com.sxj.supervisor.entity.member.CertificateLevelEntity;
import com.sxj.supervisor.entity.member.LevelEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.supervisor.service.member.IMemberImageService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class MemberImageServiceImpl implements IMemberImageService
{
    
    @Autowired
    private IMemberImageDao imageDao;
    
    @Autowired
    private ICertificateLevelDao certificateLevelDao;
    
    @Autowired
    private ILevelDao levelDao;
    
    @Autowired
    private ICertificateDao certificateDao;
    
    @Override
    public List<MemberImageEntity> getImages(String memberNo, String state,
            String certificateType) throws ServiceException
    {
        try
        {
            QueryCondition<MemberImageEntity> condition = new QueryCondition<MemberImageEntity>();
            condition.addCondition("memberNo", memberNo);// 会员号
            condition.addCondition("state", state);// 状态
            condition.addCondition("certificateType", certificateType);// 状态
            List<MemberImageEntity> list = imageDao.queryMemberImage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员图片信息错误", e, this.getClass());
            throw new ServiceException("查询图片信息错误");
        }
    }
    
    @Override
    public List<CertificateLevelEntity> getCertificateLevels(String imageId)
            throws ServiceException
    {
        try
        {
            List<CertificateLevelEntity> list = certificateLevelDao.getCertificateLevel(imageId);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询证书等级信息错误", e, this.getClass());
            throw new ServiceException("查询证书等级信息错误");
        }
        
    }
    
    @Override
    public List<LevelEntity> getLevel(String name)
    {
        try
        {
            List<LevelEntity> list = levelDao.getLevel(name);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询等级信息错误", e, this.getClass());
            throw new ServiceException("查询等级信息错误");
        }
    }
    
    @Override
    public List<CertificateEntity> getCertificate(String name)
    {
        try
        {
            List<CertificateEntity> list = certificateDao.getCertificate(name);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询证书信息错误", e, this.getClass());
            throw new ServiceException("查询证书信息错误");
        }
    }
    
    public List<MemberImageEntity> historyImage(String memberNo)
    {
        try
        {
            imageDao.historyImage(memberNo);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询历史数据错误", e, this.getClass());
            throw new ServiceException("查询历史数据错误");
        }
        return null;
        
    }
    
    public void websiteAddImage(MemberEntity member) throws ServiceException
    {
        try
        {
            List<MemberImageEntity> list = getImages(member.getMemberNo(),
                    "1",
                    null);
            if (list.size() > 0)
            {
                for (MemberImageEntity image : list)
                {
                    int flag = imageDao.updatelock(member.getMemberNo(),
                            image.getVersion(),
                            StringUtils.getUUID());
                    if (flag == 0)
                    {
                        throw new ServiceException("增加图片失败");
                    }
                }
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("增加图片失败", e, this.getClass());
            throw new ServiceException("增加图片失败");
        }
    }
}
