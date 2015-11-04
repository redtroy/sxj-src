package com.sxj.science.website.controller.countReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.jsoup.helper.StringUtil;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.poi.transformer.ExcelTransformer;
import com.sxj.poi.transformer.POITransformException;
import com.sxj.science.DocReportModel;
import com.sxj.science.model.DocModel;
import com.sxj.science.model.PartsModel;
import com.sxj.science.model.ProductModel;
import com.sxj.science.service.ICountReportService;
import com.sxj.science.website.controller.BaseController;
import com.sxj.spring.modules.util.ClassLoaderUtil;
import com.sxj.util.logger.SxjLogger;


@Controller
@RequestMapping("/countReport")
public class CountReportController extends BaseController
{
    @Value("${excel.productReport}")
    private String productReport;
    
    @Value("${excel.partsReport}")
    private String partsReport;
    
    @Value("${excel.docReport}")
    private String docReport;
    
    private String outputPath="report/统计报表";
    
    @Autowired
    private ICountReportService countReportService;
    
    @RequestMapping("/productReport")
    public String productReport(String itemIds,String projectName) throws IOException{
        List<String> temList=new ArrayList<String>();
//        temList.add("item1");
        if(!StringUtils.isBlank(itemIds)){
            String[] temArr=itemIds.split(",");
            Collections.addAll(temList, temArr);
        }
        List<ProductModel> proList= countReportService.getProductReport(temList);
        Integer sumQuantity=0;
        Double sumArea=0.0;
        for(int i=0;i<proList.size();i++){
            ProductModel temPro=proList.get(i);
            if(!StringUtils.isBlank(temPro.getQuantity())){
                sumQuantity+=Integer.parseInt(temPro.getQuantity());
            }
            sumArea+=temPro.getArea();
            temPro.setIndex(i+1);
        }
//        File file=new File(productReportExc);
        File file=new File(productReport);
        InputStream is=new FileInputStream(file);
//        InputStream is = new FileInputStream(productReportExc);
        dropFolderOrFile(new File("report"));//删除文件
        File directory = new File(outputPath);
        directory.mkdirs();//创建目录
        OutputStream os = new FileOutputStream(outputPath+"/productReport_output.xls");
        Context context = new Context();
        context.putVar("proList", proList);
        context.putVar("projectName", projectName);
        DecimalFormat   df   =   new   DecimalFormat("#####0.00"); 
        context.putVar("sumQuantity", sumQuantity);
        context.putVar("sumArea", df.format(sumArea));
        context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        return "";
    }
    
    @RequestMapping("/partsReport")
    public String partsReport(String itemIds,String projectName) throws IOException{
        List<String> temList=new ArrayList<String>();
        temList.add("item2");
        if(!StringUtils.isBlank(itemIds)){
            String[] temArr=itemIds.split(",");
            Collections.addAll(temList, temArr);
        }
        List<PartsModel> partsList= countReportService.getPartsReport(temList);
        List<PartsModel> installList=new ArrayList<PartsModel>();
        List<PartsModel> makeList=new ArrayList<PartsModel>();
        Integer makeIndex=1;
        Integer installIndex=1;
        for(int i=0;i<partsList.size();i++){
            PartsModel temP=partsList.get(i);
            if(StringUtil.isBlank(temP.getUsed())){
                continue;
            }
            if(temP.getUsed().equals("制")){
                temP.setIndex(makeIndex);
                makeIndex++;
                makeList.add(temP);
            }else{
                temP.setIndex(installIndex);
                installIndex++;
                installList.add(temP);
            }
        }
        
//        File file=new File(productReportExc);
        File file=new File(partsReport);
        InputStream is=new FileInputStream(file);
//        InputStream is = new FileInputStream(productReportExc);
        dropFolderOrFile(new File("report"));//删除文件
        File directory = new File(outputPath);
        directory.mkdirs();//创建目录
        OutputStream os = new FileOutputStream(outputPath+"/partsReport_output.xls");
        Context context = new Context();
        context.putVar("makeList", makeList);
        context.putVar("installList", installList);
        context.putVar("projectName", projectName);
        context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        return "";
    }
    
    @RequestMapping("/docReport")
    public String docReport(String itemIds,String projectName) throws IOException{
        List<String> temList=new ArrayList<String>();
        temList.add("item1");
        temList.add("item2");
        if(!StringUtils.isBlank(itemIds)){
            String[] temArr=itemIds.split(",");
            Collections.addAll(temList, temArr);
        }
        List<DocReportModel> docList= countReportService.getDocReport(temList);
        Integer sum=0;
        for(int i=0;i<docList.size();i++){
            DocReportModel temD=docList.get(i);
            if(!StringUtils.isBlank(temD.getQuantity())){
                sum+=Integer.parseInt(temD.getQuantity());
            }
        }
        
//        File file=new File(productReportExc);
        File file=new File(docReport);
        InputStream is=new FileInputStream(file);
//        InputStream is = new FileInputStream(productReportExc);
        dropFolderOrFile(new File("report"));//删除文件
        File directory = new File(outputPath);
        directory.mkdirs();//创建目录
        OutputStream os = new FileOutputStream(outputPath+"/docReport_output.xls");
        Context context = new Context();
        context.putVar("docList", docList);
        context.putVar("sum", sum);
        context.putVar("projectName", projectName);
        context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        return "";
    }
    /**
     * 删除文件夹
     * @param folder
     */
    private void dropFolderOrFile(File file)
    {
        if (file.isDirectory())
        {
            File[] fs = file.listFiles();
            for (File f : fs)
            {
                dropFolderOrFile(f);
            }
        }
        file.delete();
    }
    
    @RequestMapping("/previewProductReport")
    public @ResponseBody void previewProductReport(String itemIds,String projectName,HttpServletRequest request,
            HttpServletResponse response) throws IOException, POITransformException{
        List<String> temList=new ArrayList<String>();
//        temList.add("item1");
        if(!StringUtils.isBlank(itemIds)){
            String[] temArr=itemIds.split(",");
            Collections.addAll(temList, temArr);
        }
        List<ProductModel> proList= countReportService.getProductReport(temList);
        Integer sumQuantity=0;
        Double sumArea=0.0;
        for(int i=0;i<proList.size();i++){
            ProductModel temPro=proList.get(i);
            if(!StringUtils.isBlank(temPro.getQuantity())){
                sumQuantity+=Integer.parseInt(temPro.getQuantity());
            }
            sumArea+=temPro.getArea();
            temPro.setIndex(i+1);
        }
//        File file=new File(productReportExc);
        File file=new File(productReport);
        InputStream is=new FileInputStream(file);
//        InputStream is = new FileInputStream(productReportExc);
        dropFolderOrFile(new File("report"));//删除文件
        File directory = new File(outputPath);
        directory.mkdirs();//创建目录
        OutputStream os = new FileOutputStream(outputPath+"/productReport_output.xls");
        Context context = new Context();
        context.putVar("proList", proList);
        context.putVar("projectName", projectName);
        DecimalFormat   df   =   new   DecimalFormat("#####0.00"); 
        context.putVar("sumQuantity", sumQuantity);
        context.putVar("sumArea", df.format(sumArea));
        context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        
        //生成html
        FileInputStream xls = new FileInputStream(new File(
                outputPath+"/productReport_output.xls"));

        ExcelTransformer transformer = new ExcelTransformer();
        transformer.setOutputHTMLTag(true);
        transformer.toHTML(xls, response.getOutputStream());
//        transformer.toHTML(xlsx, xlsxHTML);
    }
    
    @RequestMapping("/previewPartsReport")
    public @ResponseBody void previewPartsReport(String itemIds,String projectName,HttpServletRequest request,
            HttpServletResponse response) throws IOException, POITransformException{
        List<String> temList=new ArrayList<String>();
//        temList.add("item2");
        if(!StringUtils.isBlank(itemIds)){
            String[] temArr=itemIds.split(",");
            Collections.addAll(temList, temArr);
        }
        List<PartsModel> partsList= countReportService.getPartsReport(temList);
        List<PartsModel> installList=new ArrayList<PartsModel>();
        List<PartsModel> makeList=new ArrayList<PartsModel>();
        Integer makeIndex=1;
        Integer installIndex=1;
        for(int i=0;i<partsList.size();i++){
            PartsModel temP=partsList.get(i);
            if(StringUtil.isBlank(temP.getUsed())){
                continue;
            }
            if(temP.getUsed().equals("制")){
                temP.setIndex(makeIndex);
                makeIndex++;
                makeList.add(temP);
            }else{
                temP.setIndex(installIndex);
                installIndex++;
                installList.add(temP);
            }
        }
        
//        File file=new File(productReportExc);
        File file=new File(partsReport);
        InputStream is=new FileInputStream(file);
//        InputStream is = new FileInputStream(productReportExc);
        dropFolderOrFile(new File("report"));//删除文件
        File directory = new File(outputPath);
        directory.mkdirs();//创建目录
        OutputStream os = new FileOutputStream(outputPath+"/partsReport_output.xls");
        Context context = new Context();
        context.putVar("makeList", makeList);
        context.putVar("installList", installList);
        context.putVar("projectName", projectName);
        context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        
        //生成html
        FileInputStream xls = new FileInputStream(new File(
                outputPath+"/partsReport_output.xls"));

        ExcelTransformer transformer = new ExcelTransformer();
        transformer.setOutputHTMLTag(true);
        transformer.toHTML(xls, response.getOutputStream());
//        transformer.toHTML(xlsx, xlsxHTML);
    }
    
    @RequestMapping("/previewDocReport")
    public @ResponseBody void previewDocReport(String itemIds,String projectName,HttpServletRequest request,
            HttpServletResponse response) throws IOException, POITransformException{
        List<String> temList=new ArrayList<String>();
//        temList.add("item1");
//        temList.add("item2");
        if(!StringUtils.isBlank(itemIds)){
            String[] temArr=itemIds.split(",");
            Collections.addAll(temList, temArr);
        }
        List<DocReportModel> docList= countReportService.getDocReport(temList);
        Integer sum=0;
        for(int i=0;i<docList.size();i++){
            DocReportModel temD=docList.get(i);
            if(!StringUtils.isBlank(temD.getQuantity())){
                sum+=Integer.parseInt(temD.getQuantity());
            }
        }
        
//        File file=new File(productReportExc);
        File file=new File(docReport);
        InputStream is=new FileInputStream(file);
//        InputStream is = new FileInputStream(productReportExc);
        dropFolderOrFile(new File("report"));//删除文件
        File directory = new File(outputPath);
        directory.mkdirs();//创建目录
        OutputStream os = new FileOutputStream(outputPath+"/docReport_output.xls");
        Context context = new Context();
        context.putVar("docList", docList);
        context.putVar("sum", sum);
        context.putVar("projectName", projectName);
        context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        JxlsHelper.getInstance().processTemplate(is, os, context);
        is.close();
        os.close();
        
        //生成html
        FileInputStream xls = new FileInputStream(new File(
                outputPath+"/docReport_output.xls"));

        ExcelTransformer transformer = new ExcelTransformer();
        transformer.setOutputHTMLTag(true);
        transformer.toHTML(xls, response.getOutputStream());
//        transformer.toHTML(xlsx, xlsxHTML);
    }
    
    @RequestMapping("/downloadProductReport")
    public  void downloadProductReport(String itemIds,String projectName,HttpServletRequest request,
            HttpServletResponse response) throws IOException{
            List<String> temList=new ArrayList<String>();
    //      temList.add("item1");
          if(!StringUtils.isBlank(itemIds)){
              String[] temArr=itemIds.split(",");
              Collections.addAll(temList, temArr);
          }
          List<ProductModel> proList= countReportService.getProductReport(temList);
          Integer sumQuantity=0;
          Double sumArea=0.0;
          for(int i=0;i<proList.size();i++){
              ProductModel temPro=proList.get(i);
              if(!StringUtils.isBlank(temPro.getQuantity())){
                  sumQuantity+=Integer.parseInt(temPro.getQuantity());
              }
              sumArea+=temPro.getArea();
              temPro.setIndex(i+1);
          }
    //      File file=new File(productReportExc);
//          InputStream is = ClassLoaderUtil.getResourceAsStream(docReport);
          File file=new File(productReport);
          InputStream is=new FileInputStream(file);
    //      InputStream is = new FileInputStream(productReportExc);
          dropFolderOrFile(new File("report"));//删除文件
          File directory = new File(outputPath);
          directory.mkdirs();//创建目录
          OutputStream os = new FileOutputStream(outputPath+"/productReport_output.xls");
          Context context = new Context();
          context.putVar("proList", proList);
          context.putVar("projectName", projectName);
          DecimalFormat   df   =   new   DecimalFormat("#####0.00"); 
          context.putVar("sumQuantity", sumQuantity);
          context.putVar("sumArea", df.format(sumArea));
          context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
          JxlsHelper.getInstance().processTemplate(is, os, context);
          is.close();
          os.close();
          
          try{
              
          File temFile=new File(outputPath+"/productReport_output.xls");
         
          response.reset();
          String fileName = "外框加工清单.xls";
          String agent = request.getHeader("User-Agent");
          boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
          if (isMSIE)
          {
              fileName = URLEncoder.encode(fileName, "UTF-8");
          }
          else
          {
              fileName = new String(fileName.getBytes("UTF-8"),
                      "ISO-8859-1");
          }
          response.addHeader("Content-Disposition",
                  "attachment;filename=" + fileName);
          response.setContentType("application/x-download");
          response.addHeader("Content-Length", "" + temFile.length());
          response.setContentLength((int) temFile.length());
          FileInputStream in = new FileInputStream(temFile); //获取文件的流  
          int len = 0;
          byte buf[] = new byte[1024];//缓存作用  
          
          ServletOutputStream out = response.getOutputStream();//输出流  
          while ((len = in.read(buf)) > 0) //切忌这后面不能加 分号 ”;“  
          {
              out.write(buf, 0, len);//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取  
          }
          out.flush();
          response.flushBuffer();
          }
          catch (Exception e)
          {
              SxjLogger.error("获取图片错误", e, this.getClass());
              
          }
    }
    
    @RequestMapping("/downloadPartsReport")
    public  void downloadPartsReport(String itemIds,String projectName,HttpServletRequest request,
            HttpServletResponse response) throws IOException{
        List<String> temList=new ArrayList<String>();
//      temList.add("item2");
      if(!StringUtils.isBlank(itemIds)){
          String[] temArr=itemIds.split(",");
          Collections.addAll(temList, temArr);
      }
      List<PartsModel> partsList= countReportService.getPartsReport(temList);
      List<PartsModel> installList=new ArrayList<PartsModel>();
      List<PartsModel> makeList=new ArrayList<PartsModel>();
      Integer makeIndex=1;
      Integer installIndex=1;
      for(int i=0;i<partsList.size();i++){
          PartsModel temP=partsList.get(i);
          if(StringUtil.isBlank(temP.getUsed())){
              continue;
          }
          if(temP.getUsed().equals("制")){
              temP.setIndex(makeIndex);
              makeIndex++;
              makeList.add(temP);
          }else{
              temP.setIndex(installIndex);
              installIndex++;
              installList.add(temP);
          }
      }
      
//      File file=new File(productReportExc);
      File file=new File(partsReport);
      InputStream is=new FileInputStream(file);
//      InputStream is = new FileInputStream(productReportExc);
      dropFolderOrFile(new File("report"));//删除文件
      File directory = new File(outputPath);
      directory.mkdirs();//创建目录
      OutputStream os = new FileOutputStream(outputPath+"/partsReport_output.xls");
      Context context = new Context();
      context.putVar("makeList", makeList);
      context.putVar("installList", installList);
      context.putVar("projectName", projectName);
      context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
      JxlsHelper.getInstance().processTemplate(is, os, context);
      is.close();
      os.close();
      
          try{
              
          File temFile=new File(outputPath+"/partsReport_output.xls");
         
          response.reset();
          String fileName = "五金配件采购计划单.xls";
          String agent = request.getHeader("User-Agent");
          boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
          if (isMSIE)
          {
              fileName = URLEncoder.encode(fileName, "UTF-8");
          }
          else
          {
              fileName = new String(fileName.getBytes("UTF-8"),
                      "ISO-8859-1");
          }
          response.addHeader("Content-Disposition",
                  "attachment;filename=" + fileName);
          response.setContentType("application/x-download");
          response.addHeader("Content-Length", "" + temFile.length());
          response.setContentLength((int) temFile.length());
          FileInputStream in = new FileInputStream(temFile); //获取文件的流  
          int len = 0;
          byte buf[] = new byte[1024];//缓存作用  
          
          ServletOutputStream out = response.getOutputStream();//输出流  
          while ((len = in.read(buf)) > 0) //切忌这后面不能加 分号 ”;“  
          {
              out.write(buf, 0, len);//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取  
          }
          out.flush();
          response.flushBuffer();
          }
          catch (Exception e)
          {
              SxjLogger.error("获取图片错误", e, this.getClass());
              
          }
    }
    
    @RequestMapping("/downloadDocReport")
    public  void downloadDocReport(String itemIds,String projectName,HttpServletRequest request,
            HttpServletResponse response) throws IOException{
        List<String> temList=new ArrayList<String>();
//      temList.add("item1");
//      temList.add("item2");
      if(!StringUtils.isBlank(itemIds)){
          String[] temArr=itemIds.split(",");
          Collections.addAll(temList, temArr);
      }
      List<DocReportModel> docList= countReportService.getDocReport(temList);
      Integer sum=0;
      for(int i=0;i<docList.size();i++){
          DocReportModel temD=docList.get(i);
          if(!StringUtils.isBlank(temD.getQuantity())){
              sum+=Integer.parseInt(temD.getQuantity());
          }
      }
      
//      File file=new File(productReportExc);
      File file=new File(docReport);
      InputStream is=new FileInputStream(file);
//      InputStream is = new FileInputStream(productReportExc);
      dropFolderOrFile(new File("report"));//删除文件
      File directory = new File(outputPath);
      directory.mkdirs();//创建目录
      OutputStream os = new FileOutputStream(outputPath+"/docReport_output.xls");
      Context context = new Context();
      context.putVar("docList", docList);
      context.putVar("sum", sum);
      context.putVar("projectName", projectName);
      context.putVar("currentDate", DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
      JxlsHelper.getInstance().processTemplate(is, os, context);
      is.close();
      os.close();
      
          try{
              
          File temFile=new File(outputPath+"/docReport_output.xls");
         
          response.reset();
          String fileName = "门窗结算清单表.xls";
          String agent = request.getHeader("User-Agent");
          boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
          if (isMSIE)
          {
              fileName = URLEncoder.encode(fileName, "UTF-8");
          }
          else
          {
              fileName = new String(fileName.getBytes("UTF-8"),
                      "ISO-8859-1");
          }
          response.addHeader("Content-Disposition",
                  "attachment;filename=" + fileName);
          response.setContentType("application/x-download");
          response.addHeader("Content-Length", "" + temFile.length());
          response.setContentLength((int) temFile.length());
          FileInputStream in = new FileInputStream(temFile); //获取文件的流  
          int len = 0;
          byte buf[] = new byte[1024];//缓存作用  
          
          ServletOutputStream out = response.getOutputStream();//输出流  
          while ((len = in.read(buf)) > 0) //切忌这后面不能加 分号 ”;“  
          {
              out.write(buf, 0, len);//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取  
          }
          out.flush();
          response.flushBuffer();
          }
          catch (Exception e)
          {
              SxjLogger.error("获取图片错误", e, this.getClass());
              
          }
    }
    
    @RequestMapping("/testView")
    public @ResponseBody void testView(String type,HttpServletRequest request,
            HttpServletResponse response) throws IOException, POITransformException{
        File file=null;
        if(type.equals("1")){
            file=new File("E:/测试单2.xls");
        }else{
            file=new File("E:/测试单100张3.xls");
        }
        
        //生成html
        FileInputStream xls = new FileInputStream(file);

        ExcelTransformer transformer = new ExcelTransformer();
        transformer.setOutputHTMLTag(true);
        transformer.toHTML(xls, response.getOutputStream());
//        transformer.toHTML(xlsx, xlsxHTML);
    }

}
