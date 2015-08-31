package com.sxj.supervisor;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sxj.poi.transformer.WordTransformer;
import com.sxj.supervisor.service.tasks.IWindDoorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestJunit
{
    
    @Autowired
    private IWindDoorService ids;
    
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
            FileInputStream docx = new FileInputStream(new File(
                    "D:\\采集导入\\江苏省交通技师学院新校区建设工程项目三标段幕墙工程{镇江}.docx"));
            //         FileOutputStream docxHTML = new FileOutputStream(new File(
            //                 "D:\\scm-repository\\git\\sxj\\bcd.html"));
            WordTransformer transformer = new WordTransformer();
            //         AbstractPictureExactor exactor = new LocalPictureExactor("c:\\test",
            //                 "file");
            //         transformer.setPictureExactor(exactor)
            //         //        transformer.toHTML(doc, docHTML);
            //   OutputStream docxHTML = response.getOutputStream();
            //   transformer.toHTML(docx, docxHTML);
            // System.out.println(docxHTML.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
