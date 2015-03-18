package com.sxj.supervisor.entity.gather;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.supervisor.dao.gather.AlDao;

@Entity(mapper = AlDao.class)
@Table(name = "AL_PRICE")
public class AlEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176532861291727407L;

	/**
	 * 主键标识
	 **/
	@Id(column = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "DATE")
	private String date;

	@Column(name = "MIN")
	private String min;

	@Column(name = "MAX")
	private String max;

	@Column(name = "AVERAGE")
	private String average;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}
}
