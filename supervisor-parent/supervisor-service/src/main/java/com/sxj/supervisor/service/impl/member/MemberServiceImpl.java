package com.sxj.supervisor.service.impl.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;

@Service
@Transactional
public class MemberServiceImpl implements IMemberService {

	@Autowired
	private IMemberDao menberDao;

	/**
	 * 新增会员
	 */
	@Override
	public void addMember(MemberEntity member) {
		menberDao.addMember(member);
	}

	/**
	 * 更新会员
	 */
	@Override
	public void modifyMember(MemberEntity member) {
		MemberEntity mb=menberDao.getMember(member.getId());
		mb.setName(member.getName());
		mb.setbLicenseNo(member.getbLicenseNo());
		mb.setEnergyNo(member.getEnergyNo());
		mb.setContacts(member.getContacts());
		mb.setPhoneNo(member.getPhoneNo());
		mb.setAddress(member.getAddress());
		mb.setTelNum(member.getTelNum());
		menberDao.updateMember(mb);
	}

	/**
	 * 查找会员
	 */
	@Override
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
			QueryCondition<MemberEntity> qc = new QueryCondition<MemberEntity>();
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("memberNo", query.getMemberNo());// 会员号
			condition.put("name", query.getMemberName());// 会员名称
			condition.put("contacts", query.getContacts());// 联系人名称
			condition.put("phoneNo", query.getContactsPhone());// 联系人电话
			condition.put("area", query.getArea());// 地理区域
			condition.put("bLicenseNo", query.getbLicenseNo());// 营业执照号
			condition.put("energyNo", query.getEnergyNo());// 节能标识号
			condition.put("type", query.getMemberType());// 会员类型
			condition.put("state", query.getMemberState());// 状态
			condition.put("startDate", query.getStartDate());// 开始时间
			condition.put("endDate", query.getEndDate());// 结束时间
			qc.setCondition(condition);
			List<MemberEntity> memberList = menberDao.queryMembers(qc);
			return memberList;
		} catch (Exception e) {
			throw new ServiceException("查询会员信息错误", e);
		}

	}

	/**
	 * 删除会员
	 */
	@Override
	public void removeMember(String id) {
		menberDao.deleteMember(id);

	}

	@Override
	@Transactional
	public String initializePwd(String memberId) throws ServiceException {
		try {
			MemberEntity member = getMember(memberId);
			// 随机密码
			String password = "123456";
			member.setPassword(password);
			modifyMember(member);
			return password;
		} catch (Exception e) {
			throw new ServiceException("初始化密码错误", e.getMessage());
		}

	}

}
