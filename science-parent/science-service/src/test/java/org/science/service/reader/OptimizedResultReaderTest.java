package org.science.service.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.sxj.science.model.result.OptimizedResult;
import com.sxj.science.service.OptimizedResultReader;

public class OptimizedResultReaderTest
{
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void parse() throws IOException
    {
        OptimizedResult result = OptimizedResultReader.read(new FileInputStream(
                new File("D:/sheji/CAD_FILE/XABC.TXT")),
                "GBK");
        System.out.println(result);
    }
}
