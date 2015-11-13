package com.sxj.science.website.controller.optimization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.poi.transformer.ExcelTransformer;
import com.sxj.science.entity.export.AloneOptimEntity;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.model.result.OptimizedModel;
import com.sxj.science.service.IAloneOptimService;
import com.sxj.science.service.IScienceService;
import com.sxj.science.website.controller.BaseController;
import com.sxj.science.website.model.optimization.OptimizationParam;
import com.sxj.science.website.model.optimization.OptimizationParamParent;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.util.common.FileUtil;
import com.sxj.util.common.StringUtils;
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
    
    @Autowired
    private IAloneOptimService optimService;
    
    @Value("${excel.aloneOptiom.tmp}")
    private String aloneModePath;
    
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
            map.put("minLength", "6000");
            map.put("maxLength", "6000");
            map.put("kerf", "10");
            map.put("step", "500");
            map.put("projectId", projectId);
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
    
    @RequestMapping("/removeOptim")
    public @ResponseBody Map<String, Object> removeOptim(String id)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            optimService.removeAloneOptim(id);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除原始数据文件错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", "删除原始数据文件错误");
        }
        return map;
    }
    
    @RequestMapping("/aloneOptim")
    public String aloneOptim(@RequestParam("projectId") String projectId,
            @RequestParam("ids") String[] ids, HttpSession session, ModelMap map)
            throws WebException
    {
        try
        {
            List<ScienceEntity> list = optimService.readExcel(ids);
            OptimizedModel model = scienceService.process(list,
                    projectId,
                    "6000",
                    "10.0");
            map.put("minLength", "6000");
            map.put("maxLength", "6000");
            map.put("kerf", "10");
            map.put("step", "500");
            map.put("projectId", projectId);
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
    
    @RequestMapping("/uploadOptim")
    public void uploadAloneOptim(String projectId, HttpServletRequest request,
            HttpServletResponse response)
    {
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            if (!(request instanceof DefaultMultipartHttpServletRequest))
            {
                return;
            }
            DefaultMultipartHttpServletRequest re = (DefaultMultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMaps = re.getFileMap();
            Collection<MultipartFile> files = fileMaps.values();
            List<String> fileIds = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            for (MultipartFile myfile : files)
            {
                if (myfile.isEmpty())
                {
                    System.err.println("文件未上传");
                }
                else
                {
                    String originalName = myfile.getOriginalFilename();
                    String extName = FileUtil.getFileExtName(originalName);
                    String filePath = storageClientService.uploadFile(null,
                            new ByteArrayInputStream(myfile.getBytes()),
                            myfile.getBytes().length,
                            extName.toUpperCase());
                    SxjLogger.info("siteUploadFilePath=" + filePath,
                            this.getClass());
                    fileIds.add(filePath);
                    fileNames.add(new String(URLDecoder.decode(originalName,
                            "UTF-8").getBytes(), "ISO8859-1"));
                    
                    // 上传元数据
                    NameValuePair[] metaList = new NameValuePair[1];
                    metaList[0] = new NameValuePair("originalName",
                            URLEncoder.encode(originalName, "UTF-8"));
                    storageClientService.overwriteMetadata(filePath, metaList);
                    AloneOptimEntity alone = new AloneOptimEntity();
                    alone.setFilePath(filePath);
                    alone.setFileName(originalName);
                    alone.setUploadTime(new Date());
                    alone.setMemberNo(getLoginInfo(request.getSession()));
                    alone.setProjectId(projectId);
                    optimService.addAloneOptim(alone);
                }
            }
            map.put("fileIds", fileIds);
            map.put("fileNames", fileNames);
            String res = JsonMapper.nonDefaultMapper().toJson(map);
            response.setContentType("text/plain;UTF-8");
            PrintWriter out = response.getWriter();
            out.print(res);
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }
    
    @RequestMapping("/optimization")
    public String optimization(@ModelAttribute OptimizationParamParent optims,
            HttpSession session, ModelMap map) throws WebException
    {
        try
        {
            List<ScienceEntity> list = new ArrayList<ScienceEntity>();
            List<ScienceEntity> list2 = new ArrayList<ScienceEntity>();
            List<OptimizedModel> modelList = new ArrayList<>();
            for (OptimizationParam param : optims.getParamList())
            {
                for (int i = 0; i < param.getLength().length; i++)
                {
                    ScienceEntity s = new ScienceEntity();
                    s.setName(param.getName());
                    s.setType(param.getType());
                    s.setLength(param.getLength()[i]);
                    s.setQuantity(param.getQuantity()[i]);
                    if (StringUtils.isNotEmpty(param.getYlength()))
                    {
                        s.setyLength(param.getYlength());
                        list.add(s);
                    }
                    else
                    {
                        list2.add(s);
                    }
                }
            }
            int numMinLength = Integer.parseInt(optims.getMinLength());
            int numMaxLength = Integer.parseInt(optims.getMaxLength());
            int numStep = Integer.parseInt(optims.getStep());
            
            if (list.size() > 0 && list2.size() == 0)
            {
                OptimizedModel model = scienceService.process(list,
                        optims.getProjectId(),
                        null,
                        optims.getKerf());
                map.put("list", model.getResult());
                session.setAttribute("cllingliaoPath",
                        model.getCllingliaoPath());
                session.setAttribute("cltongjiPath", model.getCltongjiPath());
                session.setAttribute("xlyouhuaPath", model.getXlyouhuaPath());
            }
            else if (list.size() == 0 && list2.size() > 0)
            {
                for (int i = numMinLength; i <= numMaxLength; i = i + numStep)
                {
                    OptimizedModel model = scienceService.process(list2,
                            optims.getProjectId(),
                            i + "",
                            optims.getKerf());
                    modelList.add(model);
                }
                if (modelList.size() > 0)
                {
                    OptimizedModel max = modelList.get(0);
                    for (OptimizedModel optimizedModel : modelList)
                    {
                        OptimizedModel temp = optimizedModel;
                        if (temp.getUsagePercent() > max.getUsagePercent())
                        {
                            max = temp;
                        }
                    }
                    map.put("list", max.getResult());
                    session.setAttribute("cllingliaoPath",
                            max.getCllingliaoPath());
                    session.setAttribute("cltongjiPath", max.getCltongjiPath());
                    session.setAttribute("xlyouhuaPath", max.getXlyouhuaPath());
                }
            }
            else if (list.size() > 0 && list2.size() > 0)
            {
                for (int i = numMinLength; i <= numMaxLength; i = i + numStep)
                {
                    List<ScienceEntity> allList = new ArrayList<ScienceEntity>();
                    for (ScienceEntity scienceEntity : list)
                    {
                        allList.add(scienceEntity);
                    }
                    for (ScienceEntity scienceEntity : list2)
                    {
                        scienceEntity.setyLength(i + "");
                        allList.add(scienceEntity);
                    }
                    OptimizedModel model = scienceService.process(allList,
                            optims.getProjectId(),
                            null,
                            optims.getKerf());
                    modelList.add(model);
                }
                if (modelList.size() > 0)
                {
                    OptimizedModel max = modelList.get(0);
                    for (OptimizedModel optimizedModel : modelList)
                    {
                        OptimizedModel temp = optimizedModel;
                        if (temp.getUsagePercent() > max.getUsagePercent())
                        {
                            max = temp;
                        }
                    }
                    map.put("list", max.getResult());
                    session.setAttribute("cllingliaoPath",
                            max.getCllingliaoPath());
                    session.setAttribute("cltongjiPath", max.getCltongjiPath());
                    session.setAttribute("xlyouhuaPath", max.getXlyouhuaPath());
                }
            }
            map.put("minLength", optims.getMinLength());
            map.put("maxLength", optims.getMaxLength());
            map.put("kerf", optims.getKerf());
            map.put("step", optims.getStep());
            map.put("projectId", optims.getProjectId());
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
            SxjLogger.error("获取文件错误", e, this.getClass());
        }
        
    }
    
    @RequestMapping("downloadAloneTmp")
    public void downloadAloneTmp(HttpServletResponse response,
            HttpServletRequest request) throws WebException
    {
        try
        {
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            byte[] fileByte = LocalFileUtil.readByte(aloneModePath);
            String fileName = "私享家原始数据模板.xls";
            if (isMSIE)
            {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            else
            {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="
                    + fileName);
            response.setContentType("application/x-download");
            response.addHeader("Content-Length", "" + fileByte.length);
            response.setContentLength((int) fileByte.length);
            ServletOutputStream output = response.getOutputStream();//输出流  
            output.write(fileByte);
            output.flush();
            output.close();
            response.flushBuffer();
        }
        catch (Exception e)
        {
            SxjLogger.error("获取文件错误", e, this.getClass());
        }
    }
    
    @RequestMapping("downloadAloneFile")
    public void downloadAloneFile(String id, HttpServletResponse response,
            HttpServletRequest request) throws WebException
    {
        try
        {
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            AloneOptimEntity optim = optimService.getAloneOptim(id);
            
            if (StringUtils.isNotEmpty(optim.getFilePath()))
            {
                
                String group = optim.getFilePath().substring(0,
                        optim.getFilePath().indexOf("/"));
                String fileId = optim.getFilePath()
                        .substring(optim.getFilePath().indexOf("/") + 1,
                                optim.getFilePath().length());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                storageClientService.downloadFile(group, fileId, out);
                String fileName = optim.getFileName();
                if (isMSIE)
                {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                }
                else
                {
                    fileName = new String(fileName.getBytes("UTF-8"),
                            "ISO-8859-1");
                }
                response.reset();
                response.addHeader("Content-Disposition",
                        "attachment;filename=" + fileName);
                response.setContentType("application/x-download");
                response.addHeader("Content-Length", "" + out.size());
                response.setContentLength((int) out.size());
                ServletOutputStream output = response.getOutputStream();//输出流  
                output.write(out.toByteArray());
                output.flush();
                output.close();
                response.flushBuffer();
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("获取文件错误", e, this.getClass());
            
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
