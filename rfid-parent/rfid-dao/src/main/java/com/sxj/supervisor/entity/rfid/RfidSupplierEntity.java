package com.sxj.supervisor.entity.rfid;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.rfid.IRfidSupplierDao;

/**
 * RFID供应商
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IRfidSupplierDao.class)
@Table(name = "M_RFID_SUPPLIER")
public class RfidSupplierEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6361496754557084957L;

	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
