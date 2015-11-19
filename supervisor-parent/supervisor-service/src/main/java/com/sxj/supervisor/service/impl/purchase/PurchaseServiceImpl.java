package com.sxj.supervisor.service.impl.purchase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.purchase.IPurchaseService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
@Transactional
public class PurchaseServiceImpl implements IPurchaseService {

	@Autowired
	private IMemberService memberService;

	@Autowired
	private IMemberDao memberDao;

	@Override
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
				member.setFlag(true);
				// 增加汇窗编号 状态
				memberDao.addMember(member);
			} else {
				// 更新汇窗编号
				memberEntity.setPurchaseNo(member.getPurchaseNo());
				memberEntity.setPurchaseState(1);
				memberDao.addMember(memberEntity);
			}
			// 更新关联企业
		} catch (Exception e) {
			SxjLogger.error("同步会员出错", e, this.getClass());
			throw new ServiceException("同步会员出错", e);
		}

	}

}
