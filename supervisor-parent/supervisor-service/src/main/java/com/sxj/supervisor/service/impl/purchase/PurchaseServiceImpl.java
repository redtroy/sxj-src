package com.sxj.supervisor.service.impl.purchase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.dao.purchase.IApplyDao;
import com.sxj.supervisor.dao.purchase.IPurchaseDao;
import com.sxj.supervisor.dao.purchase.IReleaseRecordDao;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.supervisor.entity.purchase.PurchaseEntity;
import com.sxj.supervisor.entity.purchase.ReleaseRecordEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.member.IMemberToMemberService;
import com.sxj.supervisor.service.purchase.IPurchaseService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class PurchaseServiceImpl implements IPurchaseService {

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMemberDao memberDao;

	@Autowired
	private IMemberToMemberService memberToMemberService;

	@Autowired
	private IApplyDao applyDao;

	@Autowired
	private IPurchaseDao purchaseDao;

	@Autowired
	private IReleaseRecordDao releaseRecordDao;

	@Autowired
	private IContractDao contractDao;

	@Override
	@Transactional
	public void syncMember(MemberEntity member) throws ServiceException {
		try {
			Assert.notNull(member, "会员数据为空!");
			MemberEntity memberEntity = memberService.getMemberByName(member
					.getName());
			if (memberEntity == null) {
				// 插入信息
				if (MemberTypeEnum.DAWP.equals(member.getType())) {
					member.setNoType("M");
				} else if (MemberTypeEnum.GLASSFACTORY.equals(member.getType())) {
					member.setNoType("B");
				} else if (MemberTypeEnum.GENRESFACTORY
						.equals(member.getType())) {
					member.setNoType("X");
				} else if (MemberTypeEnum.DEVELOPERS.equals(member.getType())) {
					member.setNoType("D");
				} else if (MemberTypeEnum.PRODUCTS.equals(member.getType())) {
					member.setNoType("P");
				} else if (MemberTypeEnum.FRAMEFACTORY.equals(member.getType()))// 副框厂
				{
					member.setNoType("F");
				} else if (MemberTypeEnum.AGENT.equals(member.getType())
						|| MemberTypeEnum.DISTRIBUTOR.equals(member.getType()))// 如果为代理商，经销商则用型材厂类型
				{
					member.setNoType("X");
				} else {
					member.setNoType("MEM");
				}
				member.setState(MemberStatesEnum.NORMAL);
				member.setCheckState(MemberCheckStateEnum.UNRECOGNIZED);
				member.setPurchaseState(1);
				member.setFlag(false);
				// 增加汇窗编号 状态
				memberDao.addMember(member);
			} else {
				// 更新汇窗编号
				member.setId(memberEntity.getId());
				member.setMemberNo(memberEntity.getMemberNo());
				memberEntity.setPurchaseNo(member.getPurchaseNo());
				if (memberEntity.getPurchaseState() == 0) {
					memberDao.updateMember(memberEntity);
				} else {
					memberDao.updateMember(member);
				}

			}
			// 更新关联企业
			if (member.getType().equals(MemberTypeEnum.AGENT)
					|| member.getType().equals(MemberTypeEnum.DISTRIBUTOR)) {
				if (!StringUtils.isEmpty(member.getAffiliates())) {
					MemberEntity affiliates = memberService
							.getMemberByName(member.getAffiliates());
					MemberToMemberEntity m = new MemberToMemberEntity();
					if (memberEntity == null) {
						m.setMemberNo(member.getMemberNo());
					} else {
						m.setMemberNo(memberEntity.getMemberNo());
					}
					// 删除关联企业
					memberToMemberService.delbyName(m.getMemberNo());
					m.setMemberName(member.getName());
					m.setContacts(member.getContacts());
					m.setTelNum(member.getTelNum());
					if (affiliates != null) {
						m.setParentNo(affiliates.getMemberNo());
						m.setParentContacts(affiliates.getContacts());
						m.setParentTelNum(affiliates.getTelNum());
					}
					m.setParentName(member.getAffiliates());
					m.setCreateTime(new Date());
					m.setMemberType(2);// 型材厂
					if (StringUtils.isNotEmpty(m.getParentName())) {
						memberToMemberService.addMemberToMember(m);
					}
				}
			}
		} catch (Exception e) {
			SxjLogger.error("同步会员出错", e, this.getClass());
			throw new ServiceException("同步会员出错", e);
		}

	}

	@Override
	@Transactional
	public void addApply(ApplyEntity apply) {
		try {
			Assert.notNull(apply, "采购申请单数据为空!");
			applyDao.insertApply(apply);
		} catch (Exception e) {
			SxjLogger.error("添加采购申请单出错", e, this.getClass());
			throw new ServiceException("添加采购申请单出错", e);
		}
	}

	@Override
	@Transactional
	public void updateApply(ApplyEntity apply) {
		try {
			Assert.notNull(apply, "采购申请单数据为空!");
			applyDao.updateApply(apply);
		} catch (Exception e) {
			SxjLogger.error("更新采购申请单出错", e, this.getClass());
			throw new ServiceException("更新采购申请单出错", e);
		}
	}

	@Override
	@Transactional
	public void updatePurchase(PurchaseEntity purchaseEntity) {
		try {
			Assert.notNull(purchaseEntity, "采购池数据为空!");
			purchaseDao.updatePurchase(purchaseEntity);
		} catch (Exception e) {
			SxjLogger.error("添加采购池数据出错", e, this.getClass());
			throw new ServiceException("添加采购池数据出错", e);
		}
	}

	@Override
	public PurchaseEntity getPurchase() {
		try {
			PurchaseEntity purchaseEntity = purchaseDao.getPurchase("1");
			return purchaseEntity;
		} catch (Exception e) {
			SxjLogger.error("获取采购池数据出错", e, this.getClass());
			throw new ServiceException("获取采购池数据出错", e);
		}
	}

	@Override
	public List<ApplyEntity> queryApply(ApplyEntity apply) {
		try {
			List<ApplyEntity> applyList = new ArrayList<ApplyEntity>();
			if (apply == null) {
				return applyList;
			}
			QueryCondition<ApplyEntity> condition = new QueryCondition<ApplyEntity>();
			condition.addCondition("memberNo", apply.getMemberNo());// 会员号
			condition.addCondition("applyType", apply.getApplyType());// 类型
			condition.addCondition("applyStatus", apply.getApplyStatus());// 类型
			condition.addCondition("serialNumber", apply.getSerialNumber());// 类型
			condition.addCondition("starDate", apply.getStartDate());
			condition.addCondition("endDate", apply.getEndDate());
			condition.setPage(apply);
			applyList = applyDao.queryApplysList(condition);
			apply.setPage(condition);
			return applyList;
		} catch (Exception e) {
			SxjLogger.error("查询采购申请单信息错误", e, this.getClass());
			throw new ServiceException("查询采购申请单信息错误", e);
		}
	}

	@Override
	@Transactional
	public void addReleaseRecordDao(ReleaseRecordEntity releaseRecordEntity) {
		try {
			Assert.notNull(releaseRecordEntity, "采购池数据为空!");
			releaseRecordDao.insertReleaseRecord(releaseRecordEntity);
		} catch (Exception e) {
			SxjLogger.error("添加采购池数据出错", e, this.getClass());
			throw new ServiceException("添加采购池数据出错", e);
		}
	}

	@Override
	public List<ReleaseRecordEntity> queryReleaseRecords(
			ReleaseRecordEntity releaseRecordEntity) {
		try {
			List<ReleaseRecordEntity> releaseRecordList = new ArrayList<ReleaseRecordEntity>();
			if (releaseRecordEntity == null) {
				return releaseRecordList;
			}
			QueryCondition<ReleaseRecordEntity> condition = new QueryCondition<ReleaseRecordEntity>();
			condition.addCondition("purchase",
					releaseRecordEntity.getPurchase());// 类型
			// condition.addCondition("name", query.getMemberName());// 会员名称
			condition.setPage(releaseRecordEntity);
			releaseRecordList = releaseRecordDao
					.queryReleaseRecordList(condition);
			releaseRecordEntity.setPage(condition);
			return releaseRecordList;
		} catch (Exception e) {
			SxjLogger.error("查询采购申请单信息错误", e, this.getClass());
			throw new ServiceException("查询采购申请单信息错误", e);
		}
	}

	@Override
	public Map<String, Integer> getContractState(String contractNos) {
		try {
			List<ContractEntity> list = contractDao
					.getContractState(contractNos.split(","));
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (ContractEntity contractEntity : list) {
				map.put(contractEntity.getContractNo(), contractEntity
						.getConfirmState().getId());
			}
			return map;
		} catch (Exception e) {
			SxjLogger.error("查询合同状态错误", e, this.getClass());
			throw new ServiceException("查询合同状态错误", e);
		}

	}
}
