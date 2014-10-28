package com.sxj.supervisor.entity.rfid;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.supervisor.dao.rfid.IRfidKeyDao;

@Entity(mapper = IRfidKeyDao.class)
@Table(name = "R_GLOBAL_KEY")
public class RfidKeyEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6380009754153582277L;

	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
