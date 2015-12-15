package com.sxj.supervisor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hslf.record.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.redis.core.concurrent.RedisConcurrent;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.purchase.ApplyEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.util.comet.CometServiceImpl;
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
    @Autowired
    RedisConcurrent rConcurrent;
    
    
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
        	
        //	String loginUrl = "http://www.menchuang.org.cn/purchase/addRecord.htm";
        	String loginUrl = "http://www.menchuang.org.cn:8080/supervisor-website/purchase/getContractState.htm";
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
//        	
//        	ReleaseRecordEntity rr = new ReleaseRecordEntity();
//        	rr.setAdminId("3290");
//        	rr.setPurchase(1);
//        	rr.setNum(1000);
//        	rr.setPriceType(0);
//        	rr.setPriceRange("5000-10000");
//        	rr.setReleaseTime(new Date());
//        	rr.setRecordNumber("123456789");
//        	
//        	ApplyEntity rr = new ApplyEntity();
//        	rr.setSerialNumber("213123");
//        	rr.setCompany("南京门窗厂");
//        	rr.setApplyType(1);
//        	rr.setApplyNum(1000);
//        	rr.setPrice("2000");
//        	rr.setApplyTime(new Date());
//        	rr.setApplyStatus(1);
//        	rr.setScanNumber("group1/M00/00/EC/wKgB21ZFlNqAZSmnAAB2qqmXN4g298.PNG");
//        	rr.setSetNumber("213123");
//        	rr.setMemberNo("X000039");
        	
//        	PurchaseEntity rr = new  PurchaseEntity();
//        	rr.setcId("1");
//        	rr.setDeepGlass(1001);
//        	rr.setDeepIncrease(101);
//        	rr.setDeepReduce(101);
//        	rr.setFitting(1001);
//        	rr.setFittingIncrease(101);
//        	rr.setFittingReduce(101);
//        	rr.setOrdinaryGlass(1001);
//        	rr.setOrdinaryIncrease(101);
//        	rr.setOrdinaryReduce(101);
//        	rr.setProfiles(1001);
//        	rr.setProfilesIncrease(101);
//        	rr.setProfilesReduce(101);
//        	RecordEntity rr = new RecordEntity();
//        	rr.setApplyId("B000159");
//        	rr.setMemberIdA("MEM000001");
//        	rr.setImgPath("22222");
//        	rr.setRecordType(1);
        	Map<String, String> map = new HashMap<String, String>();
			map.put("contractNos", "CT15040221,CT15120001");
        	String json = JsonMapper.nonDefaultMapper().toJson("CT15040221,CT15120001");
			System.err.println(json);
			String a=httpClient.postJson(loginUrl, json);
			System.err.println(a);
//        	InputStream is = TestJunit.class.getClassLoader().getResourceAsStream("config/11.csv");
//        	InputStreamReader freader = new InputStreamReader(is, "UTF-8");
//            CsvBeanReader reader = new CsvBeanReader(freader,
//                    CsvPreference.STANDARD_PREFERENCE);
//            String[] headers = reader.getHeader(false);
//            WindowRfidEntity bean = null;
//            List<WindowRfidEntity> windowList = new ArrayList<WindowRfidEntity>();
//            while ((bean = reader.read(WindowRfidEntity.class, headers)) != null)
//            {
//                windowList.add(bean);
//            }
//            for (WindowRfidEntity windowRfidEntity : windowList) {
//            	//CometServiceImpl.setCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT+windowRfidEntity.getRfidNo(),Long.valueOf(windowRfidEntity.getGid()));
////            	Long a =CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT+windowRfidEntity.getRfidNo());
//            	long s = rConcurrent.getAtomicLong(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT+windowRfidEntity.getRfidNo()).ttl();
//            	if(s==-1){
//                CometServiceImpl.setCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT+windowRfidEntity.getRfidNo(),Long.valueOf(windowRfidEntity.getGid()));
//            	}
//            	Long a =CometServiceImpl.getCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT+windowRfidEntity.getRfidNo());
//            	System.err.println(a+"____________"+windowRfidEntity.getGid()+"_____"+windowRfidEntity.getRfidNo());
//			}
//            
//        	List<String> cache = CometServiceImpl.get("record_push_message_B000159");
//        	for (String string : cache) {
//				System.err.println("redis数据-------------"+string);
//			}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
