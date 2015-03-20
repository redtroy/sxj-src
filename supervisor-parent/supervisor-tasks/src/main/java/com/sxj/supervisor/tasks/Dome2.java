package com.sxj.supervisor.tasks;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.service.gather.IWindDoorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class Dome2 {

	@Autowired
	private IStorageClientService storageClientService;

	@Autowired
	private IWindDoorService wds;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void Dome() {
		// ConnectionDb cn = new ConnectionDb();
		try {
			Response response = Jsoup
					.connect(
							"http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
					.timeout(3000).execute();
			Document doc = response.parse();
			;
			String __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
			String __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
					.val();
			Map<String, String> cookies = response.cookies();
			int pageNum = page(__VIEWSTATE, __EVENTVALIDATION, cookies);
			for (int i = 1; i <= pageNum; i++) {
				Map map = new HashMap();
				// map.put("ImageButton1.x", "32");
				// map.put("ImageButton1.y", "10");
				map.put("__EVENTVALIDATION", __EVENTVALIDATION);
				map.put("__VIEWSTATE", __VIEWSTATE);
				map.put("__EVENTTARGET", "Pager");
				map.put("__EVENTARGUMENT", "" + i);
				map.put("drpBiaoDuanType", "0");
				map.put("txtProjectName", "门窗");
				doc = (Document) Jsoup
						.connect(
								"http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
						.data(map)
						.timeout(3000)
						.cookies(cookies)
						.header("Accept",
								"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
						.header("Host", "www1.njcein.com.cn")
						.header("Referer",
								"http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
						.header("User-Agent",
								"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0")
						.header("Connection", "keep-alive").encoding("GBK")
						.post();
				int k = doc.getElementById("tdcontent").getElementsByTag("tr")
						.size();
				__VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
				__EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
						.val();
				Elements trs = doc.getElementById("tdcontent")
						.getElementsByTag("tr");
				for (Element tr : trs) {
					String bdfl = tr.getElementsByTag("td").get(1).text(); // 标段分类
					String xmmc = tr.getElementsByTag("td").get(2).text();// 项目名称
					String url = tr.getElementsByTag("td").get(2)
							.getElementsByTag("a").attr("href");
					String qy = tr.getElementsByTag("td").get(3).text();// 区域
					String jzsj = tr.getElementsByTag("td").get(4).text();// 截至日期
					System.out.println(bdfl);
					System.out.println(xmmc);
					System.out.println(qy);
					System.out.println(jzsj);
					System.out.println(url);
					Map<String, String> contentMap = content("http://www1.njcein.com.cn"
							+ url);
					// String uuid = UUID.randomUUID().toString();
					// String sql =
					// "insert into WIND_DOOR(ID,BDFL,XMMC,QY,JZRQ,CONTENT) values(?,?,?,?,?,?)";
					String filePath = storageClientService.uploadFile(null,
							new ByteArrayInputStream(contentMap.get("content")
									.getBytes()), contentMap.get("content")
									.getBytes().length, "txt".toUpperCase());
					// List<String> list = new ArrayList<String>();
					// list.add(uuid);
					// list.add(bdfl);
					// list.add(xmmc);
					// list.add(qy);
					// list.add(jzsj);
					// list.add(filePath);
					// System.out.println(sql);
					// cn.DBHelper2(sql, list);
					WindDoorEntity wd = new WindDoorEntity();
					wd.setBdfl(bdfl);
					wd.setFilePath(filePath);
					wd.setJzrq(jzsj);
					wd.setQy(qy);
					wd.setXmmc(xmmc);
					if (contentMap.get("gifPath") != null) {
						wd.setGifPath(contentMap.get("gifPath"));
					}
					wds.addWindDoor(wd);
				}
				// System.out.println(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int page(String __VIEWSTATE, String __EVENTVALIDATION,
			Map<String, String> cookies) {
		try {
			Map map = new HashMap();
			// map.put("ImageButton1.x", "32");
			// map.put("ImageButton1.y", "10");
			map.put("__EVENTVALIDATION", __EVENTVALIDATION);
			map.put("__VIEWSTATE", __VIEWSTATE);
			map.put("__EVENTTARGET", "Pager");
			map.put("__EVENTARGUMENT", "1");
			map.put("drpBiaoDuanType", "0");
			map.put("txtProjectName", "门窗");
			Document doc = (Document) Jsoup
					.connect(
							"http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
					.data(map)
					.timeout(3000)
					.cookies(cookies)
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
					.header("Host", "www1.njcein.com.cn")
					.header("Referer",
							"http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0")
					.header("Connection", "keep-alive").encoding("GBK").post();
			String pageNum = doc.getElementById("Pager").getElementsByTag("td")
					.get(0).text().split("/")[1];
			return Integer.valueOf(pageNum);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public Map<String, String> content(String url) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			System.out.println(url);
			Document doc = (Document) Jsoup.connect(url).timeout(3000).get();
			Element element = doc.getElementById("ZBGGDetail1_tblInfo");
			if (element.getElementById("ZBGGDetail1_trAttach") != null
					&& !"".equals(element
							.getElementById("ZBGGDetail1_trAttach"))) {
				element.getElementById("ZBGGDetail1_trAttach").remove();
			}
			String img = element.getElementById("ZBGGDetail1_divDS")
					.getElementsByTag("img").attr("src");
			if (img != null && !"".equals(img)) {
				String gifPath = downPicture("http://www1.njcein.com.cn/njxxnew/ZtbInfo/"
						+ img);
				map.put("gifPath", gifPath);
			}
			map.put("content", element.toString());
			// System.out.println(element.toString());
			return map;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public String downPicture(String picttrueUrl) {
		try {
			/* 将网络资源地址传给,即赋值给url */
			URL url = new URL(picttrueUrl);
			String fileName = picttrueUrl.substring(
					picttrueUrl.lastIndexOf("=") + 1, picttrueUrl.length());
			System.out.println(fileName);
			/* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			DataInputStream in = new DataInputStream(
					connection.getInputStream());

			/* 此处也可用BufferedInputStream与BufferedOutputStream 需要保存的路径 */
			// DataOutputStream out = new DataOutputStream(new FileOutputStream(
			// "D:\\test\\" + fileName + ".gif"));
			//
			// /* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
			// byte[] buffer = new byte[4096];
			// int count = 0;
			// while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
			// {
			// out.write(buffer, 0, count);
			// }
			// out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
			String gifPath = storageClientService.uploadFile(null, in,
					in.available(), "gif".toUpperCase());
			in.close();
			connection.disconnect();
			return gifPath;/* 网络资源截取并存储本地成功返回true */

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
