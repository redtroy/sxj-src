package com.sxj.science.website.controller.optimization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.poi.transformer.ExcelTransformer;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.model.result.OptimizedModel;
import com.sxj.science.service.IScienceService;
import com.sxj.science.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/optim")
public class OptimizationController extends BaseController
{
    
    @Autowired
    private IScienceService scienceService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @RequestMapping("/getScienceItems")
    public String getScienceItem(@RequestParam("projectId") String projectId,
            @RequestParam("itemId") String[] itemId, HttpSession session,
            ModelMap map) throws WebException
    {
        try
        {
            List<ScienceEntity> list = scienceService.getScienceList(itemId);
            OptimizedModel model = scienceService.process(list,
                    projectId,
                    "6000",
                    "10.0");
            map.put("list", model.getResult());
            session.setAttribute("cllingliaoPath", model.getCllingliaoPath());
            session.setAttribute("cltongjiPath", model.getCltongjiPath());
            session.setAttribute("xlyouhuaPath", model.getXlyouhuaPath());
            return "site/youhua/youhua";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询下料数据错误", e, this.getClass());
            throw new WebException("查询下料数据错误", e);
        }
    }
    
    @RequestMapping("/optimization")
    public String optimization(String projectId, String kerf, String minLength,
            String maxLength, String step, String[] name, String[] type,
            String[] length, String[] quantity, HttpSession session,
            ModelMap map) throws WebException
    {
        try
        {
            List<ScienceEntity> list = new ArrayList<ScienceEntity>();
            for (int i = 0; i < name.length; i++)
            {
                ScienceEntity s = new ScienceEntity();
                s.setName(name[i]);
                s.setType(type[i]);
                s.setLength(length[i]);
                s.setQuantity(quantity[i]);
                list.add(s);
                
            }
            int numMinLength = Integer.parseInt(minLength);
            int numMaxLength = Integer.parseInt(maxLength);
            int numStep = Integer.parseInt(step);
            
            List<OptimizedModel> modelList = new ArrayList<>();
            for (int i = numMinLength; i <= numMaxLength; i = i + numStep)
            {
                OptimizedModel model = scienceService.process(list,
                        projectId,
                        i + "",
                        kerf);
                modelList.add(model);
            }
            OptimizedModel max = null;
            if (modelList.size() > 0)
            {
                max = modelList.get(0);
                for (OptimizedModel optimizedModel : modelList)
                {
                    OptimizedModel temp = optimizedModel;
                    if (temp.getUsagePercent() > max.getUsagePercent())
                    {
                        max = temp;
                    }
                }
            }
            map.put("list", max.getResult());
            session.setAttribute("cllingliaoPath", max.getCllingliaoPath());
            session.setAttribute("cltongjiPath", max.getCltongjiPath());
            session.setAttribute("xlyouhuaPath", max.getXlyouhuaPath());
            return "site/youhua/youhua";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询下料数据错误", e, this.getClass());
            throw new WebException("查询下料数据错误", e);
        }
    }
    
    @RequestMapping("/viewExcel")
    public @ResponseBody void viewExcel(@RequestParam("path") String path,
            HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            String group = path.substring(0, path.indexOf("/"));
            String id = path.substring(path.indexOf("/") + 1, path.length());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            storageClientService.downloadFile(group, id, out);
            ByteArrayInputStream in = new ByteArrayInputStream(
                    out.toByteArray());
            
            ExcelTransformer transformer = new ExcelTransformer();
            transformer.setOutputHTMLTag(true);
            transformer.toHTML(in, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        
    }
    
    @RequestMapping("downloadFile")
    public void downloadFile(@RequestParam("excelName") String[] excelName,
            @RequestParam("path") String[] path, HttpServletResponse response,
            HttpServletRequest request) throws WebException
    {
        try
        {
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            String filePath = "excelFile";
            String zipFilePath = "zipFile";
            LocalFileUtil.dropFolderOrFile(new File("excelFile"));//删除文件
            File directory = new File(filePath);
            directory.mkdirs();//创建目录
            
            if (path != null && path.length > 0)
            {
                for (int i = 0; i < path.length; i++)
                {
                    
                    String group = path[i].substring(0, path[i].indexOf("/"));
                    String id = path[i].substring(path[i].indexOf("/") + 1,
                            path[i].length());
                    String name = excelName[i];
                    File excelFile = new File(filePath + "/" + name);
                    FileOutputStream output = new FileOutputStream(excelFile);
                    storageClientService.downloadFile(group, id, output);
                    output.flush();
                    output.close();
                }
                
                File zipFile = LocalFileUtil.zipFile(filePath,
                        zipFilePath,
                        "优化单.zip");
                response.reset();
                String fileName = zipFile.getName();
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
                response.addHeader("Content-Length", "" + zipFile.length());
                response.setContentLength((int) zipFile.length());
                FileInputStream in = new FileInputStream(zipFile); //获取文件的流  
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
        }
        catch (Exception e)
        {
            SxjLogger.error("获取文件错误", e, this.getClass());
            
        }
    }
    
    @RequestMapping("/toDownload")
    public String toDownload(HttpSession session, ModelMap map)
            throws WebException
    {
        try
        {
            
            String cllingliaoPath = (String) session.getAttribute("cllingliaoPath");
            String cltongjiPath = (String) session.getAttribute("cltongjiPath");
            String xlyouhuaPath = (String) session.getAttribute("xlyouhuaPath");
            
            map.put("cllingliaoPath", cllingliaoPath);
            map.put("cltongjiPath", cltongjiPath);
            map.put("xlyouhuaPath", xlyouhuaPath);
            return "site/youhua/download";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询下料数据错误", e, this.getClass());
            throw new WebException("查询下料数据错误", e);
        }
    }
}
