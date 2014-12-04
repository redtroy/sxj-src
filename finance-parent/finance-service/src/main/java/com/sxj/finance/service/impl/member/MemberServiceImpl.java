package com.sxj.finance.service.impl.member;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.finance.dao.member.IMemberDao;
import com.sxj.finance.entity.member.MemberEntity;
import com.sxj.finance.enu.member.MemberCheckStateEnum;
import com.sxj.finance.model.member.MemberQuery;
import com.sxj.finance.service.member.IMemberService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;


@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

	@Autowired
	private IMemberDao menberDao;
	
	/**
	 * 查找会员
	 */
	@Override
	@Transactional(readOnly = true)
	public MemberEntity getMember(String id) {
		MemberEntity member = menberDao.getMember(id);
		return member;
	}
	
	/**
	 * 会员高级查询
	 */
	@Override
	@Transactional(readOnly = true)
	public List<MemberEntity> queryMembers(MemberQuery query)
			throws ServiceException {
		try {
			if (query == null) {
				return null;
			}
			QueryCondition<MemberEntity> condition = new QueryCondition<MemberEntity>();
			condition.addCondition("memberNo", query.getMemberNo());// 会员号
			condition.addCondition("name", query.getMemberName());// 会员名称
			condition.addCondition("contacts", query.getContacts());// 联系人名称
			condition.addCondition("phoneNo", query.getPhoneNo());// 联系人电话
			condition.addCondition("area", query.getArea());// 地理区域
			condition.addCondition("bLicenseNo", query.getbLicenseNo());// 营业执照号
			condition.addCondition("energyNo", query.getEnergyNo());// 节能标识号
			condition.addCondition("type", query.getMemberType());// 会员类型
			condition.addCondition("checkState", query.getCheckState());
			condition.addCondition("state", query.getMemberState());
			condition.addCondition("startDate", query.getStartDate());// 开始时间
			condition.addCondition("endDate", query.getEndDate());// 结束时间
			condition.addCondition("typeB", query.getMemberTypeB());
			condition.setPage(query);
			List<MemberEntity> memberList = menberDao.queryMembers(condition);
			query.setPage(condition);
			return memberList;
		} catch (Exception e) {
			throw new ServiceException("查询会员信息错误", e);
		}

	}
	
	/**
	 * 根据会员号，查询会员信息
	 */
	@Override
	@Transactional(readOnly = true)
	public MemberEntity memberInfo(String memberNo) throws ServiceException {
		if (StringUtils.isEmpty(memberNo)) {
			return null;
		}
		MemberQuery query = new MemberQuery();
		query.setMemberNo(memberNo);
		List<MemberEntity> list = queryMembers(query);
		if (list.size() > 0) {
			MemberEntity member = list.get(0);
			return member;
		}
		return null;
	}
	
	/**
	 * 更改审核状态
	 */
	@Override
	@Transactional
	public void editCheckState(String id, Integer state)
			throws ServiceException {
		try {
			if (state != null && StringUtils.isNotEmpty(id)) {
				MemberEntity member = new MemberEntity();
				member.setId(id);
				member.setFinanceState(state);
				member.setVersion(menberDao.getMember(id).getVersion());
				menberDao.updateMember(member);
			}
		} catch (Exception e) {
			throw new ServiceException("更改审核状态错误", e);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public MemberEntity getMemberByName(String name) throws ServiceException {
		try {
			return menberDao.getMemberByName(name);
		} catch (Exception e) {
			SxjLogger.error("根据会员名称查询会员错误", e, this.getClass());
			throw new ServiceException("根据会员名称查询会员错误", e);
		}
	}
}
