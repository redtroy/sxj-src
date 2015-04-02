package com.sxj.supervisor.service.impl.developer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.developer.IDevelopersDao;
import com.sxj.supervisor.entity.developers.DevelopersEntity;
import com.sxj.supervisor.service.developer.IDevelopersService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class DevelopersServiceImpl implements IDevelopersService {
	@Autowired
	private IDevelopersDao developerDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<DevelopersEntity> queryDeveloperList(DevelopersEntity query) throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<DevelopersEntity> condition = new QueryCondition<DevelopersEntity>();
			condition.addCondition("name", query.getName());// 开发商名称
			condition.addCondition("city", query.getCity());// 城市
			condition.addCondition("url", query.getUrl());// 链接地址
			condition.addCondition("address", query.getAddress());// 公司地址
			condition.addCondition("telPhone", query.getTelPhone());// 电话
			condition.setPage(query);
			List<DevelopersEntity> recordList = developerDao.query(condition);
			query.setPage(condition);
			return recordList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("查询开发商信息错误", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void addDeveloper(DevelopersEntity entity) throws ServiceException {
		try {
			developerDao.addDevelopers(entity);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("新增开发商信息错误", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDeveloper(DevelopersEntity entity) throws ServiceException {
		try {
			developerDao.updateDevelopers(entity);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("修改开发商信息错误", e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void delDeveloper(String id) throws ServiceException {
		try {
			developerDao.delDevelopers(id);
		} catch (Exception e) {
			SxjLogger.error(e.getMessage(), e, this.getClass());
			throw new ServiceException("修改开发商信息错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public DevelopersEntity getDeveloper(String id) throws ServiceException {
		DevelopersEntity record = developerDao.getDeveloper(id);
		return record;
	}

}
