package com.sxj.supervisor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.IImageDao;
import com.sxj.supervisor.entity.ImageEntity;
import com.sxj.supervisor.service.IImageService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class ImageServiceImpl implements IImageService
{
    
    @Autowired
    private IImageDao imageDao;
    
    @Override
    public void addImages(List<ImageEntity> images) throws ServiceException
    {
        try
        {
            imageDao.addImages(images);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加图片数据错误", e, this.getClass());
            throw new ServiceException("添加图片数据错误", e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true)
    public ImageEntity getImage(String md5) throws ServiceException
    {
        try
        {
            return imageDao.getImage(md5);
        }
        catch (Exception e)
        {
            SxjLogger.error("获取图片数据错误", e, this.getClass());
            throw new ServiceException("获取图片数据错误", e);
        }
    }
}
