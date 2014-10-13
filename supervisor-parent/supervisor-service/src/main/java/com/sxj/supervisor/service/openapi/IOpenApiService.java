package com.sxj.supervisor.service.openapi;

import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.util.exception.ServiceException;

public interface IOpenApiService {

	public ContractBatchEntity getBatchInfo(String rfidNo)
			throws ServiceException;

}
