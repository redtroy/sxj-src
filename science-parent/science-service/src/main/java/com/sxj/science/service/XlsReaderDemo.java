package com.sxj.science.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.reader.ReaderBuilder;
import org.jxls.reader.XLSReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sxj.science.entity.export.GlassEntity;
import com.sxj.science.model.DocModel;

public class XlsReaderDemo
{
    static Logger logger = LoggerFactory.getLogger(XlsReaderDemo.class);
    
    private static String dataFile = "C:/Users/dujinxin/Desktop/模板/模板/下料单格式.xls";
    
    public static final String xmlConfig = "E:/work/sxj-src/science-parent/science-website/src/main/resources/export/excelTemp_1.xml";
    
    public static final String xmlConfig2 = "E:/work/sxj-src/science-parent/science-website/src/main/resources/export/excelTemp_2.xml";
    
    public static void main(String[] args) throws IOException, SAXException,
            InvalidFormatException
    {
        logger.info("Running XLS Reader demo");
        XlsReaderDemo.execute();
    }
    
    public static void execute() throws IOException, SAXException,
            InvalidFormatException
    {
        logger.info("Reading xml config file and constructing XLSReader");
        InputStream xmlInputStream = new FileInputStream(xmlConfig);
        InputStream xmlInputStream2 = new FileInputStream(xmlConfig2);
        XLSReader reader = ReaderBuilder.buildFromXML(xmlInputStream);
        XLSReader reader2 = ReaderBuilder.buildFromXML(xmlInputStream2);
        InputStream xlsInputStream = new FileInputStream(dataFile);
        InputStream xlsInputStream2 = new FileInputStream(dataFile);
        
        DocModel doc = new DocModel();
        List<DocModel> docList = new ArrayList<>();
        Map<String, Object> beans = new HashMap<>();
        beans.put("doc", doc);
        logger.info("Reading the data...");
        reader.read(xlsInputStream, beans);
        // reader2.read(xlsInputStream2, beans);
        Object newdoc = beans.get("doc");
        for (GlassEntity glasss : doc.getGlassList())
        {
            logger.info(glasss.getHeight() + ": " + glasss.getWidth());
        }
        xlsInputStream.close();
        xmlInputStream.close();
    }
}
