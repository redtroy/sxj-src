package com.sxj.supervisor.service.impl.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.supervisor.dao.contract.IContractPayDao;
import com.sxj.supervisor.entity.pay.PayRecordEntity;
import com.sxj.supervisor.model.contract.ContractPayModel;
import com.sxj.supervisor.service.contract.IContractPayService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;

@Service
public class ContractPayServiceImpl implements IContractPayService {
	@Autowired
	private IContractPayDao payDao;

	@Override
	public List<PayRecordEntity> queryPayList(ContractPayModel query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<PayRecordEntity> condition = new QueryCondition<PayRecordEntity>();
			condition.addCondition("memberNo", query.getMemberNo());// 会员号
			condition.addCondition("payNo", query.getPayNo());// 支付单号
			condition.addCondition("contractNo", query.getContractNo());// 合同号
			condition.addCondition("rfidNo", query.getRfidNo());// Rfid编号
			condition.addCondition("startPayDate", query.getStartPayDate());// 开始时间
			condition.addCondition("endPayDate", query.getEndPayDate());// 结束时间
			condition.setPage(query);
			List<PayRecordEntity> payList = payDao.queryPayContract(condition);
			query.setPage(condition);
			return payList;
		} catch (Exception e) {
			throw new ServiceException("查询付款管理出错", e);
		}
	}

	@Override
	public void update_state(String id, Integer state) throws ServiceException {
		// TODO Auto-generated method stub

	}

}
