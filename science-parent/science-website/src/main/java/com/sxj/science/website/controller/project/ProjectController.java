package com.sxj.science.website.controller.project;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import third.rewrite.fastdfs.NameValuePair;
import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.file.common.LocalFileUtil;
import com.sxj.science.entity.export.AloneOptimEntity;
import com.sxj.science.entity.export.ItemEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.model.AloneOptimQuery;
import com.sxj.science.model.ItemModel;
import com.sxj.science.service.IAloneOptimService;
import com.sxj.science.service.IExeclOperator;
import com.sxj.science.service.IProjectService;
import com.sxj.science.website.controller.BaseController;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.util.common.FileUtil;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController
{
    
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Autowired
    private IExeclOperator excelOperator;
    
    @Autowired
    private IAloneOptimService optimService;
    
    @RequestMapping("/addProject")
    public @ResponseBody Map<String, Object> addProject(String name,
            HttpSession session) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ProjectEntity project = new ProjectEntity();
            project.setName(name);
            project.setFileCount(0);
            project.setMemberName(getLoginInfo(session));
            project.setMemberNo(getLoginInfo(session));
            projectService.addProject(project);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加工程错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", "添加工程错误");
        }
        return map;
    }
    
    @RequestMapping("/addItem")
    public @ResponseBody Map<String, Object> addItem(String projectId,
            String name) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ItemEntity item = new ItemEntity();
            item.setProjectId(projectId);
            item.setName(name);
            item.setUploadTime(new Date());
            item.setCount(0);
            item.setState(0);
            projectService.addItem(item);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("添加工程批次错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", "添加工程批次错误");
        }
        return map;
    }
    
    @RequestMapping("/getItems")
    public String getProjectItem(String projectId, ModelMap map)
            throws WebException
    {
        try
        {
            List<ItemModel> list = projectService.queryItems(projectId);
            ProjectEntity temPro = projectService.getProject(projectId);
            map.put("projectItems", list);
            map.put("projectId", projectId);
            map.put("projectName", temPro.getName());
            
            AloneOptimQuery optimQuery = new AloneOptimQuery();
            optimQuery.setProjectId(projectId);
            List<AloneOptimEntity> optimList = optimService.query(optimQuery);
            map.put("optimList", optimList);
            return "site/project/projectItem";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程条目错误", e, this.getClass());
            throw new WebException("查询工程条目错误", e);
        }
    }
    
    @RequestMapping("/removeItem")
    public @ResponseBody Map<String, Object> removeItem(String itemId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ItemEntity item = projectService.getItemById(itemId);
            item.setIsShow(0);
            projectService.updateItem(item);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除下料单错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", "删除下料单错误");
        }
        return map;
    }
    
    @RequestMapping("/removeProject")
    public @ResponseBody Map<String, Object> removeProject(String projectId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ProjectEntity project = projectService.getProject(projectId);
            project.setIsShow(0);
            projectService.updateProject(project);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除工程错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", "删除工程错误");
        }
        return map;
    }
    
    @RequestMapping("/modifyProject")
    public @ResponseBody Map<String, Object> modifyProject(
            @RequestParam("projectId") String projectId,
            @RequestParam("name") String name) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ProjectEntity project = projectService.getProject(projectId);
            if (project == null)
            {
                throw new WebException("工程信息不存在");
            }
            project.setName(name);
            projectService.modifyProject(project);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改工程错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/modifyItem")
    public @ResponseBody Map<String, Object> modifyItem(
            @RequestParam("itemId") String itemId,
            @RequestParam("name") String name) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ItemEntity item = new ItemEntity();
            item.setId(itemId);
            item.setName(name);
            projectService.modifyItem(item);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改工程批次错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 上传文件
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("upload")
    public void uploadFile(String projectId, HttpServletRequest request,
            HttpServletResponse response) throws IOException
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
                
                // 上传元数据
                NameValuePair[] metaList = new NameValuePair[1];
                metaList[0] = new NameValuePair("originalName",
                        URLEncoder.encode(originalName, "UTF-8"));
                storageClientService.overwriteMetadata(filePath, metaList);
                excelOperator.uploadExcel(getLoginInfo(request.getSession()),
                        originalName,
                        filePath,
                        projectId,
                        myfile.getBytes());
            }
        }
        map.put("fileIds", fileIds);
        String res = JsonMapper.nonDefaultMapper().toJson(map);
        response.setContentType("text/plain;UTF-8");
        PrintWriter out = response.getWriter();
        out.print(res);
        out.flush();
        out.close();
    }
    
    @RequestMapping("downloadFile")
    public void downloadFile(String[] projectItemId,
            HttpServletResponse response, HttpServletRequest request)
            throws WebException
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
            List<ItemEntity> itemList = projectService.getItem(projectItemId);
            List<String> fileIdList = new ArrayList<String>();
            if (itemList != null && itemList.size() > 0)
            {
                for (ItemEntity projectItem : itemList)
                {
                    fileIdList.add(projectItem.getFilePath());
                    String group = projectItem.getFilePath().substring(0,
                            projectItem.getFilePath().indexOf("/"));
                    String id = projectItem.getFilePath()
                            .substring(projectItem.getFilePath().indexOf("/") + 1,
                                    projectItem.getFilePath().length());
                    String name = projectItem.getName();
                    File excelFile = new File(filePath + "/" + name);
                    FileOutputStream output = new FileOutputStream(excelFile);
                    storageClientService.downloadFile(group, id, output);
                    output.flush();
                    output.close();
                }
                
                File zipFile = LocalFileUtil.zipFile(filePath,
                        zipFilePath,
                        "算料单.zip");
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
            SxjLogger.error("获取图片错误", e, this.getClass());
            
        }
    }
}
