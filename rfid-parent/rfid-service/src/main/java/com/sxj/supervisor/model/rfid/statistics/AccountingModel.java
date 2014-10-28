package com.sxj.supervisor.model.rfid.statistics;

import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;

public class AccountingModel extends Pagable {

	private String contractNo;

	private String recordNo;

	private String memberNameA;

	private String memberNameB;

	private String contractType;

	private Date signDate;

	private Double amount;

	private Double payAmount;

	private Double noPayAmount;

	private Float speed;

}
