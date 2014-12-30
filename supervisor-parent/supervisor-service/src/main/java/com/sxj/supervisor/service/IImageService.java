package com.sxj.supervisor.service;

import java.util.List;

import com.sxj.supervisor.entity.ImageEntity;
import com.sxj.util.exception.ServiceException;

public interface IImageService {

	public void addImages(List<ImageEntity> images) throws ServiceException;

	public ImageEntity getImage(String md5) throws ServiceException;
}
