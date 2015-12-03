package com.sxj.supervisor.dao.purchase;

import java.sql.SQLException;

import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.supervisor.entity.purchase.PurchaseEntity;

public interface IPurchaseDao {

	@Update
	public void updatePurchase(PurchaseEntity purchase) throws SQLException;

	@Get
	public PurchaseEntity getPurchase(String id) throws SQLException;

}
