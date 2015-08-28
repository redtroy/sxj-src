package com.sxj.supervisor;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext_2.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestJunit
{
    
    @Autowired
    private IWindDoorService ids;
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test()
    {
        //ids.WindDoorGather();
        //        CometServiceImpl.takeCount(MessageChannel.MEMBER_PAY_MESSAGE_COUNT
        //                + "MEM000001");
        //         FileInputStream doc = new FileInputStream(new File(
        //                 "D:\\scm-repository\\git\\sxj\\abc.doc"));
        //         FileOutputStream docHTML = new FileOutputStream(new File(
        //                 "D:\\scm-repository\\git\\sxj\\abc.html"));
        //  FileInputStream docx = new FileInputStream(new File(
        //        "D:\\scm-repository\\git\\sxj\\abc.docx"));
        //         FileOutputStream docxHTML = new FileOutputStream(new File(
        //                 "D:\\scm-repository\\git\\sxj\\bcd.html"));
        /* WordTransformer transformer = new WordTransformer();
         AbstractPictureExactor exactor = new LocalPictureExactor("c:\\test",
                 "file");
         transformer.setPictureExactor(exactor)
         //        transformer.toHTML(doc, docHTML);
         transformer.toHTML(docx, docxHTML);*/
        
    }
}
