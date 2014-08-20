package com.sxj.supervisor.entity.contract;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.contract.IContractItemDao;

/**
 * 合同变更产品条目信息
 * @author Ann
 *
 */
@Entity(mapper = IContractItemDao.class)
@Table(name = "M_CONTRACT_ITEM_HIS")
public class ModifyItemEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1564168265758964083L;
	/**
	 * 主键
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	/**
	 * 变更记录ID
	 **/
	@Column(name = "MODIFY_ID")
	private String modifyId;

	/**
	 * 产品名称
	 **/
	@Column(name = "PRODUCT_NAME")
	private String productName;

	/**
	 * 规格型号
	 **/
	@Column(name = "PRODUCT_MODEL")
	private String productModel;

	/**
	 * 数量
	 **/
	@Column(name = "QUANTITY")
	private Float quantity;

	/**
	 * 单价
	 **/
	@Column(name = "PRICE")
	private Long price;

	/**
	 * 金额
	 **/
	@Column(name = "AMOUNT")
	private Long amount;

	/**
	 * 备注
	 **/
	@Column(name = "REMARKS")
	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
