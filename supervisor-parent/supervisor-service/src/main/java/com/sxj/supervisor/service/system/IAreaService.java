package com.sxj.supervisor.service.system;

import java.util.List;

import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.util.exception.ServiceException;

public interface IAreaService {

	public List<AreaEntity> getChildrenAreas(String parentId) throws ServiceException;

}
