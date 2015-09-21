package com.sxj.supervisor.service.impl.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.supervisor.model.member.MemberImageModel;
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
    public List<MemberImageEntity> getImages(String memberNo, String state)
            throws ServiceException
    {
        try
        {
            QueryCondition<MemberImageEntity> condition = new QueryCondition<MemberImageEntity>();
            condition.addCondition("memberNo", memberNo);// 会员号
            condition.addCondition("state", state);// 状态
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
            return imageDao.historyImage(memberNo);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询历史数据错误", e, this.getClass());
            throw new ServiceException("查询历史数据错误");
        }
        
    }
    
    @Override
    @Transactional
    public void websiteAddImage(String memberNo, String images)
            throws ServiceException
    {
        try
        {
            List<MemberImageEntity> list = getImages(memberNo, "1");
            if (list.size() > 0)
            {
                String viso = StringUtils.getUUID();
                int flag = imageDao.updatelock(memberNo, list.get(0)
                        .getVersion(), viso);
                if (flag == 0)
                {
                    throw new ServiceException("增加图片失败");
                }
                Date date = new Date();
                viso = StringUtils.getUUID();
                for (String image : images.split(","))
                {
                    Boolean Flag = true;
                    for (MemberImageEntity mem : list)
                    {
                        if (image.equals(mem.getImage()))
                        {
                            Flag = false;
                            mem.setId(null);
                            mem.setCreationDate(date);
                            mem.setVersion(viso);
                            mem.setMemberNo(memberNo);
                            imageDao.addMemberImage(mem);
                        }
                    }
                    if (Flag)
                    {
                        MemberImageEntity mem = new MemberImageEntity();
                        mem.setCreationDate(date);
                        mem.setVersion(viso);
                        mem.setImage(image);
                        mem.setState(1);
                        mem.setMemberNo(memberNo);
                        imageDao.addMemberImage(mem);
                    }
                }
                
            }
            else
            {
                Date date = new Date();
                String viso = StringUtils.getUUID();
                for (String image : images.split(","))
                {
                    MemberImageEntity mem = new MemberImageEntity();
                    mem.setCreationDate(date);
                    mem.setVersion(viso);
                    mem.setImage(image);
                    mem.setMemberNo(memberNo);
                    mem.setState(1);
                    imageDao.addMemberImage(mem);
                }
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("增加图片失败", e, this.getClass());
            throw new ServiceException("增加图片失败");
        }
    }
    
    @Override
    @Transactional
    public void ManageAddImage(List<MemberImageEntity> list)
            throws ServiceException
    {
        try
        {
            List<MemberImageEntity> querylist = getImages(list.get(0)
                    .getMemberNo(), "1");
            if (querylist.size() > 0)
            {
                String viso = StringUtils.getUUID();
                int flag = imageDao.updatelock(list.get(0).getMemberNo(),
                        querylist.get(0).getVersion(),
                        viso);
                if (flag == 0)
                {
                    throw new ServiceException("增加图片失败");
                }
                Date date = new Date();
                viso = StringUtils.getUUID();
                for (MemberImageEntity mi : list)
                {
                    mi.setCreationDate(date);
                    mi.setVersion(viso);
                    mi.setState(1);
                    imageDao.addMemberImage(mi);
                }
                
            }
            else
            {
                Date date = new Date();
                String viso = StringUtils.getUUID();
                for (MemberImageEntity mi : list)
                {
                    mi.setCreationDate(date);
                    mi.setVersion(viso);
                    mi.setState(1);
                    imageDao.addMemberImage(mi);
                }
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("增加图片失败", e, this.getClass());
            throw new ServiceException("增加图片失败");
        }
    }
    
    @Override
    public List<MemberImageEntity> delImage(String memberNo)
            throws ServiceException
    {
        try
        {
            QueryCondition<MemberImageEntity> condition = new QueryCondition<MemberImageEntity>();
            condition.addCondition("memberNo", memberNo);// 会员号
            condition.addCondition("state", -1);// 会员号
            List<MemberImageEntity> list = imageDao.queryMemberImage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询删除数据错误", e, this.getClass());
            throw new ServiceException("查询删除数据错误");
        }
        
    }
    
    @Override
    public List<MemberImageEntity> newImage(String memberNo)
            throws ServiceException
    {
        try
        {
            List<MemberImageEntity> newList = new ArrayList<MemberImageEntity>();
            QueryCondition<MemberImageEntity> condition = new QueryCondition<MemberImageEntity>();
            condition.addCondition("memberNo", memberNo);// 会员号
            condition.addCondition("state", 1);// 类型
            List<MemberImageEntity> list = imageDao.queryMemberImage(condition);
            List<MemberImageEntity> historyList = imageDao.queryMemberImage(condition);
            for (MemberImageEntity memberImage : list)
            {
                for (MemberImageEntity oldMenberImage : historyList)
                {
                    if (memberImage.getImage()
                            .equals(oldMenberImage.getImage()))
                    {
                        newList.add(memberImage);
                    }
                }
            }
            return newList;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询新数据错误", e, this.getClass());
            throw new ServiceException("查询新数据错误");
        }
        
    }

	@Override
	public MemberImageModel getMemberImageByImageId(String imageId)
			throws ServiceException {
		try {
			MemberImageModel model = new MemberImageModel();
			MemberImageEntity imageEntity= imageDao.getMemberImageByImageId(imageId);
			List<CertificateLevelEntity> level = certificateLevelDao.getCertificateLevel(imageId);
			model.setMemberImage(imageEntity);
			model.setList(level);
			return model;
		} catch (Exception e) {
			SxjLogger.error("查询新数据错误", e, this.getClass());
			throw new ServiceException("查询新数据错误");
		}
	}

	@Override
	public void addMemberImage(MemberImageEntity memberImage)
			throws SQLException {
		try {
			if(memberImage.getId()==null){
				imageDao.addMemberImage(memberImage);
			}else{
				imageDao.updateMemberImage(memberImage);
			}
		} catch (Exception e) {
			SxjLogger.error("查询新数据错误", e, this.getClass());
			throw new ServiceException("查询新数据错误");
		}
		
	}
}
