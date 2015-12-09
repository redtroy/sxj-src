package com.supervisor.ocr;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import com.sxj.spring.modules.util.ClassLoaderUtil;

public class ImageCodeTest
{
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test() throws Exception
    {
        String image = "classpath:cert.JPG";
        String valCode = new OCR().recognizeText(
                new File(ClassLoaderUtil.getResource(image).getURI()), "jpg");
        System.out.println(valCode);
    }
    
}
