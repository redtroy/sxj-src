package com.sxj.supervisor.open.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.model.open.BatchModel;
import com.sxj.supervisor.model.open.WinTypeModel;
import com.sxj.supervisor.service.rfid.open.IOpenRfidService;
import com.sxj.util.exception.ServiceException;

@Controller
@RequestMapping("/rfid")
public class OpenRfidController {
	@Autowired
	IOpenRfidService openRfidService;
	

	/**
	 * 登陆
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "login")
	public @ResponseBody Map<String, Object> login(String userId,
			String password) {
		return null;
	}

	/**
	 * 注销
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "logout")
	public @ResponseBody Map<String, Object> logout(String userId) {
		return null;
	}

	/**
	 * 获取批次信息
	 * 
	 * @param rfidNo
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "info/batch/{rfidNo}")
	public @ResponseBody BatchModel getRfidBatchInfo(
			@PathVariable String rfidNo, HttpServletResponse response)
			throws SQLException {
		try {
			BatchModel model = openRfidService.getBatchByRfid(rfidNo);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			System.err.println(JsonMapper.nonEmptyMapper().toJson(model));
			out.print(JsonMapper.nonEmptyMapper().toJson(model));
			out.flush();
			out.close();
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 获取合同规格信息
	 * 
	 * @param rfidNo
	 * @return
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value = "info/contract/{rfidNo}")
	public @ResponseBody WinTypeModel getRfidContractInfo(
			@PathVariable String rfidNo,HttpServletResponse response) throws ServiceException, SQLException {
		
		try {
			WinTypeModel win=openRfidService.getWinTypeByRfid(rfidNo);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			System.err.println(JsonMapper.nonEmptyMapper().toJson(win));
			out.print(JsonMapper.nonEmptyMapper().toJson(win));
			out.flush();
			out.close();
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	/**
	 * 发货
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "send/{rfidNo}")
	public @ResponseBody Map<String, Object> sendGoods(
			@PathVariable String rfidNo) {
		return null;

	}

	/**
	 * 验收
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "check/{rfidNo}")
	public @ResponseBody Map<String, Object> checkAndAccept(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;
	}

	/**
	 * 质检
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "test/{rfidNo}")
	public @ResponseBody Map<String, Object> testRfid(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("contractNo", "CT1410250001");
		String[] batchNos = new String[2];
		batchNos[0] = "AAAA0001";
		batchNos[1] = "AAAA0002";
		map.put("batchNo", batchNos);
		return map;

	}

	/**
	 * 安装
	 * 
	 * @param rfidNo
	 * @return
	 */
	@RequestMapping(value = "setup/{rfidNo}")
	public @ResponseBody Map<String, Object> setupRfid(
			@PathVariable String rfidNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", 1);
		map.put("contractNo", "CT1410250001");
		map.put("batchNo", "00001");
		return map;

	}
	
	/**
	 * 获取合同地址信息
	 * 
	 * @param rfidNo
	 * @return
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	@RequestMapping(value = "info/address/{contractNo}")
	public @ResponseBody Map<String, Object> getAddress(
			@PathVariable String contractNo) throws ServiceException, SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		String address =openRfidService.getAddress(contractNo);
		map.put("address", address);
		return map;
	}

}
