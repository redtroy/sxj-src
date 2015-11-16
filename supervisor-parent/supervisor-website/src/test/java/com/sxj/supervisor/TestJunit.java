package com.sxj.supervisor;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.util.common.ISxjHttpClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestJunit
{
    
    //    @Autowired
    //    private IWindDoorService ids;
    @Autowired
    private IMessageConfigService configService;
    
    @Autowired
    private ISxjHttpClient httpClient;
    //    @Autowired
    //    private AlGatherImpl al;
    
    @Test
    public void test()
    {
        try
        {
            //ids.WindDoorGather();
            //        CometServiceImpl.takeCount(MessageChannel.MEMBER_PAY_MESSAGE_COUNT
            //                + "MEM000001");
            //         FileInputStream doc = new FileInputStream(new File(
            //                 "D:\\scm-repository\\git\\sxj\\abc.doc"));
            //         FileOutputStream docHTML = new FileOutputStream(new File(
            //                 "D:\\scm-repository\\git\\sxj\\abc.html"));
            //            FileInputStream docx = new FileInputStream(new File(
            //                    "D:\\采集导入\\江苏省交通技师学院新校区建设工程项目三标段幕墙工程{镇江}.docx"));
            //         FileOutputStream docxHTML = new FileOutputStream(new File(
            //                 "D:\\scm-repository\\git\\sxj\\bcd.html"));
            //            WordTransformer transformer = new WordTransformer();
            //         AbstractPictureExactor exactor = new LocalPictureExactor("c:\\test",
            //                 "file");
            //         transformer.setPictureExactor(exactor)
            //         //        transformer.toHTML(doc, docHTML);
            //   OutputStream docxHTML = response.getOutputStream();
            //   transformer.toHTML(docx, docxHTML);
            // System.out.println(docxHTML.toString());
            //   al.gather();
            //configService.sendAllMessage("测试短信");
        	
        	String loginUrl = "http://www.menchuang.org.cn:8080/supervisor-website/purchase/syncMember.htm";
			MemberEntity memberEntity = new MemberEntity();
			memberEntity.setName("南京市测试厂");
			memberEntity.setAddress("仙林大道");
			String json = JsonMapper.nonDefaultMapper().toJson(memberEntity);
			System.err.println(json);
			String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
			Map<String, String> map = new HashMap<String, String>();
			map.put("json", encoderJson);
			String a=httpClient.post(loginUrl, map);
			System.err.println(a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
