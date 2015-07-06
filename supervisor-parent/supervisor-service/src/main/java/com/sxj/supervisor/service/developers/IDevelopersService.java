package com.sxj.supervisor.service.developers;

import java.util.List;

import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.model.open.ApiModel;
import com.sxj.util.exception.ServiceException;

public interface IDevelopersService {
	/**
	 * 开发商高级查询
	 * @param query
	 * @return DevelopersEntity
	 * @throws ServiceException
	 * @author nist
	 */
	 public List<DevelopersEntity> queryDeveloperList(DevelopersEntity query)  throws ServiceException;
	 /**
	  * 新增开发商
	  * @param entity
	  * @throws ServiceException
	  * @author nist
	  */
	 public void addDeveloper(DevelopersEntity entity) throws ServiceException;
	 /**
	  * 修改开发商
	  * @param entity
	  * @throws ServiceException
	  * @author nist
	  */
	 public void updateDeveloper(DevelopersEntity entity) throws ServiceException;
	 /**
	  * 删除开发商
	  * @param id
	  * @throws ServiceException
	  * @author nist
	  */
	 public void delDeveloper(String id) throws ServiceException;
	 /**
	  * 获取开发商详情
	  * @param id
	  * @throws ServiceException
	  * @author nist
	  */
	 public DevelopersEntity getDeveloper(String id) throws ServiceException;
	 
	 public List<ApiModel> apiQueryDevelopers(DevelopersEntity query)  throws ServiceException;
}
