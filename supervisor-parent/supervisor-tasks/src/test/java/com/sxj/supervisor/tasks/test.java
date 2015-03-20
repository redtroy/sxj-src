package com.sxj.supervisor.tasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.dao.gather.AlDao;
import com.sxj.supervisor.entity.gather.AlEntity;
import com.sxj.supervisor.tasks.Model.DataMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class test {

	@Autowired
	private AlDao al;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void al() {
		try {
			String name = getJsonString("");
			DataMap dm = JsonMapper.nonEmptyMapper().fromJson(name,
					DataMap.class);
			for (Map<String, String> map : dm.getData().get("3").values()) {
				String uuid = UUID.randomUUID().toString();
				AlEntity alEntity = new AlEntity();
				alEntity.setDate(map.get("date"));
				alEntity.setMax(map.get("max"));
				alEntity.setMin(map.get("min"));
				alEntity.setAverage(map.get("average"));
				al.addAl(alEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getJsonString(String urlPath) throws Exception {
		URL url = new URL("http://market.cnal.com/share/market/cj30.json");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection
				.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0");
		connection.setRequestMethod("GET");
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		// 对应的字符编码转换
		Reader reader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(reader);
		String str = null;
		StringBuffer sb = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			sb.append(str);
		}
		reader.close();
		connection.disconnect();
		return sb.toString();
	}

}
