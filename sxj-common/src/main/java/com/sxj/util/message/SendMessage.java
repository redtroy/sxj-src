package com.sxj.util.message;

import java.util.List;

import com.shcm.bean.BalanceResultBean;
import com.shcm.bean.SendResultBean;
import com.shcm.send.DataApi;
import com.shcm.send.OpenApi;
import com.sxj.util.exception.SystemException;
import com.sxj.util.logger.SxjLogger;

public class SendMessage {

	private String sOpenUrl;

	private String sDataUrl;

	// 接口帐号
	private String account;

	// 接口密钥
	private String authkey;

	// 通道组编号
	private int cgid;

	// 默认使用的签名编号(未指定签名编号时传此值到服务器)
	private int csid;

	private SendMessage(String sOpenUrl, String sDataUrl, String account,
			String authkey, int cgit, int csid) {
		this.sOpenUrl = sOpenUrl;
		this.sDataUrl = sDataUrl;
		this.account = account;
		this.authkey = authkey;
		this.cgid = cgit;
		this.csid = csid;
		// 发送参数
		OpenApi.initialzeAccount(this.sOpenUrl, this.account, this.authkey,
				this.cgid, this.csid);

		// 状态及回复参数
		DataApi.initialzeAccount(this.sDataUrl, this.account, this.authkey);
	}

	public static SendMessage getInstance(String sOpenUrl, String sDataUrl,
			String account, String authkey, int cgit, int csid) {
		return new SendMessage(sOpenUrl, sDataUrl, account, authkey, cgit, csid);
	}

	public void sendMessage(String mobiles, String message)
			throws SystemException {
		// 取帐户余额
		BalanceResultBean br = OpenApi.getBalance();
		if (br.getResult() < 1) {
			SxjLogger.info("获取短信可用余额失败: " + br.getErrMsg(), this.getClass());
			throw new SystemException("获取短信可用余额失败: " + br.getErrMsg());
		}
		SxjLogger.info("可用条数: " + br.getRemain(), this.getClass());

		List<SendResultBean> listItem = OpenApi.sendOnce(mobiles, message,
				cgid, csid, null);
		if (listItem != null) {
			for (SendResultBean t : listItem) {
				if (t.getResult() < 1) {
					SxjLogger.error("短信发送提交失败: " + t.getErrMsg(),
							this.getClass());
					throw new SystemException("短信发送提交失败: " + t.getErrMsg());
				}
				SxjLogger.info(
						"发送成功: 消息编号<" + t.getMsgId() + "> 总数<" + t.getTotal()
								+ "> 余额<" + t.getRemain() + ">",
						this.getClass());
			}
		}

	}
}
