package com.sxj.supervisor.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.system.IAreaDao;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
public class AreaServiceImpl implements IAreaService {

	@Autowired
	private IAreaDao areaDao;

	@Override
	public List<AreaEntity> getChildrenAreas(String parentId)
			throws ServiceException {
		try {
			QueryCondition<AreaEntity> query = new QueryCondition<AreaEntity>();
			query.addCondition("parentId", parentId);
			List<AreaEntity> list = areaDao.getAreas(query);
			return list;
		} catch (Exception e) {
			throw new ServiceException("查询地理信息错误", e);
		}
	}
}
