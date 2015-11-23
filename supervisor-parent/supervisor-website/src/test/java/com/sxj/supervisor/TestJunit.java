package com.sxj.supervisor;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.purchase.PurchaseEntity;
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
        	
        	String loginUrl = "http://www.menchuang.org.cn:8080/supervisor-website/purchase/updatePurchase.htm";
        	
//			MemberEntity memberEntity = new MemberEntity();
//			memberEntity.setName("南京海阔天空科技有限公司");
//			memberEntity.setAddress("仙林大道");
//			memberEntity.setParentNo("Z00001");
//			memberEntity.setType(MemberTypeEnum.AGENT);
//			memberEntity.setContacts("嵇康");
//			memberEntity.setPhoneNo("18913039663");
//			memberEntity.setRegDate(new Date());
//			memberEntity.setAffiliates("南京市型材巡航厂");
//			memberEntity.setbLicenseNo("21092982382938");
//			memberEntity.setbLicensePath("group1/M00/00/EC/wKgB21ZFlNeAfAd1ABWE8XUxiFI910.PNG");
        	
//        	ReleaseRecordEntity rr = new ReleaseRecordEntity();
//        	rr.setId("12345");
//        	rr.setAdminId("3290");
//        	rr.setPurchase(1);
//        	rr.setNum(1000);
//        	rr.setPriceType(0);
//        	rr.setPriceRange("5000-10000");
//        	rr.setReleaseTime(new Date());
//        	rr.setRecordNumber("123456789");
        	
//        	ApplyEntity rr = new ApplyEntity();
//        	rr.setId("10");
//        	rr.setSerialNumber("213123");
//        	rr.setCompany("南京门窗厂");
//        	rr.setApplyType(1);
//        	rr.setApplyNum(1000);
//        	rr.setPrice("2000");
//        	rr.setApplyTime(new Date());
//        	rr.setApplyStatus(1);
//        	rr.setBeianStatus(1);
//        	rr.setScanNumber("group1/M00/00/EC/wKgB21ZFlNqAZSmnAAB2qqmXN4g298.PNG");
//        	rr.setSetNumber("213123");
//        	rr.setMemberNo("X000039");
        	
        	PurchaseEntity rr = new  PurchaseEntity();
        	rr.setcId("1");
        	rr.setDeepGlass(1000);
        	rr.setDeepIncrease(100);
        	rr.setDeepReduce(100);
        	rr.setFitting(1000);
        	rr.setFittingIncrease(100);
        	rr.setFittingReduce(100);
        	rr.setOrdinaryGlass(1000);
        	rr.setOrdinaryIncrease(100);
        	rr.setOrdinaryReduce(100);
        	rr.setProfiles(1000);
        	rr.setProfilesIncrease(100);
        	rr.setProfilesReduce(100);
        	String json = JsonMapper.nonDefaultMapper().toJson(rr);
			System.err.println(json);
			Map<String, String> map = new HashMap<String, String>();
			map.put("json", json);
			String a=httpClient.postJson(loginUrl, json);
			System.err.println(a);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
