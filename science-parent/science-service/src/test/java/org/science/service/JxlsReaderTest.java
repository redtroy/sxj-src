package org.science.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.SheetsReader;

import com.sxj.science.model.DocModel;
import com.sxj.spring.modules.util.ClassLoaderUtil;

/**
 * Unit test for simple App.
 */
public class JxlsReaderTest
{
    
    @Test
    public void testLoopRead() throws IOException
    {
        String xml = "loop.xml";
        String xls = "XABC.xls";
        InputStream xmlInputStream = null;
        InputStream xlsInputStream = null;
        try
        {
            xmlInputStream = ClassLoaderUtil.getResourceAsStream(xml);
            //            XLSReader reader = ReaderBuilder.buildFromXML(xmlInputStream);
            SheetsReader reader = ReaderBuilder.buildSheetsReaderFromXML(xmlInputStream);
            xlsInputStream = ClassLoaderUtil.getResourceAsStream(xls);
            
            //DemoDocModel docModel = new DemoDocModel();
            Map<String, Object> beans = new HashMap<>();
            List<DocModel> docList = new ArrayList<DocModel>();
            beans.put("docList", docList);
            reader.read(xlsInputStream, beans);
            //            System.out.println(docModel.getDocs().size());
            //            System.out.println(docList.size());
            List<Object> result = reader.getResult();
            System.out.println(result.size());
            for (Object o : result)
            {
                DocModel doc = (DocModel) o;
                System.out.println(doc.getGlassList().size());
                System.out.println(doc.getGlassList().get(0).getQuantity());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            xlsInputStream.close();
            xmlInputStream.close();
        }
    }
}
