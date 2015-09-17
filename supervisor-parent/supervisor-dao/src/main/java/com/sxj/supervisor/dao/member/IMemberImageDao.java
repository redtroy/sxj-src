package com.sxj.supervisor.dao.member;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IMemberImageDao {

	/**
	 * 添加会员证书
	 * 
	 * @throws SQLException
	 */
	@Insert
	public void addMemberImage(MemberImageEntity imageEntity)
			throws SQLException;

	/**
	 * 更新会员证书
	 * 
	 * @throws SQLException
	 */
	@Update
	public void updateMemberImage(MemberImageEntity imageEntity)
			throws SQLException;

	/**
	 * 获取会员证书
	 * 
	 * @param imageId
	 * @return
	 * @throws SQLException
	 */
	@Get
	public MemberImageEntity getMemberImage(String id) throws SQLException;

	/**
	 * 获取会员所有图片
	 * 
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public List<MemberImageEntity> queryMemberImage(
			QueryCondition<MemberImageEntity> condition) throws SQLException;

	/**
	 * 历史图片
	 * @param menber
	 * @return
	 * @throws SQLException
	 */
	public List<MemberImageEntity> historyImage(String memberNo)
			throws SQLException;
}
