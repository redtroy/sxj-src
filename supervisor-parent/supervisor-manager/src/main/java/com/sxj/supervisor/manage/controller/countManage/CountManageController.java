package com.sxj.supervisor.manage.controller.countManage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.countManage.AloneOptimEntity;
import com.sxj.supervisor.model.countManage.HistoryListModel;
import com.sxj.supervisor.model.countManage.HistoryModel;
import com.sxj.supervisor.model.countManage.ItemModel;
import com.sxj.supervisor.model.countManage.ProjectEntity;
import com.sxj.supervisor.model.countManage.ProjectItemsModel;
import com.sxj.supervisor.model.countManage.ProjectListModel;
import com.sxj.supervisor.model.countManage.ProjectModel;
import com.sxj.supervisor.model.countManage.WindowTypeAndAreaModel;
import com.sxj.supervisor.model.countManage.WindowTypeModel;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/openCount")
public class CountManageController extends BaseController
{
    @Autowired
    private ISxjHttpClient httpClient;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Value("${countManager.hostName}")
    private String hostName;
    
    @RequestMapping("/query")
    public String query(WindowTypeModel query,ModelMap map){
       try
       {
           if (query != null)
           {
               query.setPagable(true);
               query.setShowCount(20);
           }
            Map<String,String> params=new HashMap<String,String>();
            if(!StringUtil.isBlank(query.getWinId())){
                params.put("winId", query.getWinId());
            }
            if(!StringUtil.isBlank(query.getArea())){
                params.put("area", query.getArea());
            }
            if(!StringUtil.isBlank(query.getCompanyName())){
                params.put("companyName", query.getCompanyName());
            }
            if(!StringUtil.isBlank(query.getType())){
                params.put("type", query.getType());
            }
            if(!StringUtil.isBlank(query.getSeries())){
                params.put("series",query.getSeries());
            }
            if(!StringUtil.isBlank(query.getName())){
                params.put("name", query.getName());
            }
            Integer currentPage=query.getCurrentPage();
            params.put("currentPage", currentPage.toString());
            String res = httpClient.post(hostName+"openQuery.htm", params);
            JsonMapper jm=new JsonMapper();
            WindowTypeAndAreaModel resultMap=jm.fromJson(res, WindowTypeAndAreaModel.class);
            List<WindowTypeModel> list= resultMap.getList();
            List<AreaEntity> areaList=resultMap.getAreaList();
            query.setPage(resultMap.getQuery());
            
            map.put("list", list);
            map.put("areaList", areaList);
            map.put("query", query);
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            return "manage/countManage/count_template";
        }
    
    @RequestMapping("/downloadTem")
    public void downloadTem(String modelPath,String name,HttpServletRequest request,
            HttpServletResponse response) throws WebException, IOException
    {
        InputStream in = null;
        ServletOutputStream out = null;
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            String filePath = "sl/私享家-算料模板";
            dropFolderOrFile(new File("sl"));//删除文件
            File directory = new File(filePath);
            directory.mkdirs();//创建目录
            String temPath=modelPath;
            String group=temPath.split("/")[0];
            String pathId=temPath.substring(group.length()+1);
            String suffix=temPath.split("\\.")[1];
            File temFile=new File(filePath+"/"+name + "."+suffix);
            name=name+"."+suffix;
            FileOutputStream output =new FileOutputStream(temFile);
    //        ServletOutputStream output=response.getOutputStream();
            storageClientService.downloadFile(group, pathId, output);
            output.close();
            
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            if (isMSIE)
            {
                name = URLEncoder.encode(name, "UTF-8");
            }
            else
            {
                name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.reset();        
            response.addHeader("Content-Disposition", "attachment;filename="
                    + name);
            response.setContentType("application/x-download");
            response.addHeader("Content-Length", "" + temFile.length());
            response.setContentLength((int) temFile.length());
            in = new FileInputStream(temFile); //获取文件的流
            int len = 0;
            byte buf[] = new byte[1024];//缓存作用  
            out = response.getOutputStream();//输出流  
            while ((len = in.read(buf)) > 0) //切忌这后面不能加 分号 ”;“  
            {
                out.write(buf, 0, len);//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取  
            }
            out.flush();
            response.flushBuffer();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    SxjLogger.error("下载文件错误", e, this.getClass());
                    map.put("error", e.getMessage());
                }
            }
            
            if (out != null)
            {
                try
                {
                    
                    out.close();
                    
                }
                catch (IOException e)
                {
                    SxjLogger.error("下载文件错误", e, this.getClass());
                    map.put("error", e.getMessage());
                }
            }
        }
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
    
    @RequestMapping("/loadAddPage")
    public String loadAddPage(ModelMap map){
        List<AreaEntity> areaList=areaService.getChildrenAreas("32");
        map.put("areaList", areaList);
        return "manage/countManage/count_template_add";
    }
    
    @RequestMapping("/loadEditPage")
    public String loadEditPage(String id,ModelMap map){
        List<AreaEntity> areaList=areaService.getChildrenAreas("32");
        map.put("areaList", areaList);
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        String res;
        try
        {
            res = httpClient.post(hostName+"loadFeeding.htm", params);
            JsonMapper jm=new JsonMapper(); 
            WindowTypeModel windowType=jm.fromJson(res, WindowTypeModel.class);
            map.put("windowType", windowType);
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "manage/countManage/count_template_edit";
    }
    
    @RequestMapping("/addCountTem")
    public @ResponseBody Map<String, Object> addCountTem(WindowTypeModel model) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("area",model.getArea());
        params.put("companyName",model.getCompanyName());
        params.put("type",model.getType());
        params.put("series", model.getSeries());
        params.put("name",model.getName());
        params.put("modelPath", model.getModelPath());
        params.put("imageSrc", model.getImageSrc());
        params.put("finish", "0");
        String res = httpClient.post(hostName+"addCountTem.htm", params);
        if(!StringUtils.isBlank(res)){
            map.put("isOk", "true");
            map.put("winId", res);
        }else{
            map.put("isOk", "false");
        }
        return map;
    }
    
    @RequestMapping("/editCountTem")
    public @ResponseBody Map<String, Object> editCountTem(WindowTypeModel model) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", model.getId());
        params.put("area",model.getArea());
        params.put("companyName",model.getCompanyName());
        params.put("type",model.getType());
        params.put("series", model.getSeries());
        params.put("name",model.getName());
        params.put("modelPath", model.getModelPath());
        params.put("imageSrc", model.getImageSrc());
        String res = httpClient.post(hostName+"editCountTem.htm", params);
        if(!StringUtils.isBlank(res)){
            map.put("isOk", "true");
            map.put("winId", res);
        }else{
            map.put("isOk", "false");
        }
        return map;
    }
    
    @RequestMapping("/delCountTem")
    public @ResponseBody Map<String, Object> delCountTem(String id) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        String res = httpClient.post(hostName+"delCountTem.htm", params);
        if(!StringUtils.isBlank(res)){
            map.put("isOk", "true");
        }else{
            map.put("isOk", "false");
        }
        return map;
    }
    
    @RequestMapping("autoCompanyName")
    public @ResponseBody Map<String, String> autoCompanyName(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        Map<String,String> params=new HashMap<String,String>();
        params.put("keyword", keyword);
        String json = httpClient.post(hostName+"autoCompanyName.htm", params);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
    
    @RequestMapping("autoType")
    public @ResponseBody Map<String, String> autoType(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        Map<String,String> params=new HashMap<String,String>();
        params.put("keyword", keyword);
        String json = httpClient.post(hostName+"autoType.htm", params);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
    
    @RequestMapping("autoSeries")
    public @ResponseBody Map<String, String> autoSeries(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        Map<String,String> params=new HashMap<String,String>();
        params.put("keyword", keyword);
        String json = httpClient.post(hostName+"autoSeries.htm", params);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
    
    @RequestMapping("/loadFeeding")
    public String loadFeeding(String id,ModelMap map){
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        String res;
        try
        {
            res = httpClient.post(hostName+"loadFeeding.htm", params);
            JsonMapper jm=new JsonMapper(); 
            WindowTypeModel windowType=jm.fromJson(res, WindowTypeModel.class);
            map.put("windowType", windowType);
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "manage/countManage/feeding single";
    }
    
    @RequestMapping("/saveHtml")
    public @ResponseBody Map<String, Object> saveHtml(String id,String finish,String htmlData,String htmlDataBackup) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        params.put("htmlDataBackup", htmlDataBackup);
        params.put("htmlData", htmlData);
        params.put("finish", finish);
        String res = httpClient.post(hostName+"saveHtml.htm", params);
        if(!StringUtils.isBlank(res)){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("/queryProject")
    public String queryProject(ProjectModel query,ModelMap map){
       try
       {
           if (query != null)
           {
               query.setPagable(true);
               query.setShowCount(20);
           }
            Map<String,String> params=new HashMap<String,String>();
            if(!StringUtil.isBlank(query.getProjectNo())){
                params.put("projectNo", query.getProjectNo());
            }
            if(!StringUtil.isBlank(query.getName())){
                params.put("name", query.getName());
            }
            if(!StringUtil.isBlank(query.getMemberName())){
                params.put("memberName", query.getMemberName());
            }
            if(!StringUtil.isBlank(query.getZhaoBiaoNo())){
                params.put("zhaoBiaoNo",query.getZhaoBiaoNo());
            }
            if(!StringUtil.isBlank(query.getBeiAnNo())){
                params.put("beiAnNo", query.getBeiAnNo());
            }
            Integer currentPage=query.getCurrentPage();
            params.put("currentPage", currentPage.toString());
            String res = httpClient.post(hostName+"openQueryProject.htm", params);
            JsonMapper jm=new JsonMapper();
            ProjectListModel resultMap=jm.fromJson(res, ProjectListModel.class);
            List<ProjectModel> list= resultMap.getList();
//            List<AreaEntity> areaList=resultMap.getAreaList();
            query.setPage(resultMap.getQuery());
            
            map.put("list", list);
//            map.put("areaList", areaList);
            map.put("query", query);
        }
        catch (ClientProtocolException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            return "manage/countManage/countman";
        }
    
    @RequestMapping("/getItems")
    public String getProjectItem(String projectId, ModelMap map)
            throws WebException
    {
        try
        {
            Map<String,String> params=new HashMap<String,String>();
            params.put("projectId", projectId);
            String res = httpClient.post(hostName+"getItems.htm", params);
            JsonMapper jm=new JsonMapper();
            ProjectItemsModel projectItemsModel=jm.fromJson(res, ProjectItemsModel.class);
            
            List<ItemModel> list = projectItemsModel.getProjectItems();
            ProjectEntity temPro = projectItemsModel.getProject();
            List<AloneOptimEntity> optimList=projectItemsModel.getOptimList();
            map.put("projectItems", list);
            map.put("projectId", projectId);
            map.put("project", temPro);
            map.put("optimList", optimList);
            return "manage/countManage/projectItem";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程条目错误", e, this.getClass());
            throw new WebException("查询工程条目错误", e);
        }
    }
    
    @RequestMapping("/deleteProject")
    public @ResponseBody Map<String, Object> deleteProject(String id) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        String res = httpClient.post(hostName+"deleteProject.htm", params);
        JsonMapper jm=new JsonMapper();
        Map<String,String> resMap=jm.fromJson(res, Map.class);
        String result=resMap.get("isOK");
        if(result.equals("true")){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("/deleteProjectItem")
    public @ResponseBody Map<String, Object> deleteProjectItem(String id) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        String res = httpClient.post(hostName+"deleteProjectItem.htm", params);
        JsonMapper jm=new JsonMapper();
        Map<String,String> resMap=jm.fromJson(res, Map.class);
        String result=resMap.get("isOK");
        if(result.equals("true")){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("/queryHistory")
    public String queryHistory(String projectId, ModelMap map)
            throws WebException
    {
        try
        {
            Map<String,String> params=new HashMap<String,String>();
            params.put("projectId", projectId);
            String res = httpClient.post(hostName+"queryHistory.htm", params);
            JsonMapper jm=new JsonMapper();
            HistoryListModel historyListModel=jm.fromJson(res, HistoryListModel.class);
            List<HistoryModel> list=historyListModel.getList();
            map.put("list", list);
            map.put("projectId", projectId);
            return "manage/countManage/counthistory";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询报表历史错误", e, this.getClass());
            throw new WebException("查询报表历史错误", e);
        }
    }
    
    @RequestMapping("/changeShow")
    public @ResponseBody Map<String, Object> changeShow(String id,String isShow) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        params.put("isShow", isShow);
        String res = httpClient.post(hostName+"changeShow.htm", params);
        JsonMapper jm=new JsonMapper();
        Map<String,String> resMap=jm.fromJson(res, Map.class);
        String result=resMap.get("isOK");
        if(result.equals("true")){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("/changeItemShow")
    public @ResponseBody Map<String, Object> changeItemShow(String id,String isShow) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        params.put("isShow", isShow);
        String res = httpClient.post(hostName+"changeItemShow.htm", params);
        JsonMapper jm=new JsonMapper();
        Map<String,String> resMap=jm.fromJson(res, Map.class);
        String result=resMap.get("isOK");
        if(result.equals("true")){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("/changeAloneShow")
    public @ResponseBody Map<String, Object> changeAloneShow(String id,String isShow) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        params.put("isShow", isShow);
        String res = httpClient.post(hostName+"changeAloneShow.htm", params);
        JsonMapper jm=new JsonMapper();
        Map<String,String> resMap=jm.fromJson(res, Map.class);
        String result=resMap.get("isOK");
        if(result.equals("true")){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("/delAlone")
    public @ResponseBody Map<String, Object> delAlone(String id) throws ClientProtocolException, IOException{
        Map<String,Object> map=new HashMap<String,Object>();
        Map<String,String> params=new HashMap<String,String>();
        params.put("id", id);
        String res = httpClient.post(hostName+"delAlone.htm", params);
        JsonMapper jm=new JsonMapper();
        Map<String,String> resMap=jm.fromJson(res, Map.class);
        String result=resMap.get("isOK");
        if(result.equals("true")){
            map.put("isOK", "true");
        }else{
            map.put("isOK", "false");
        }
        return map;
    }
    
    @RequestMapping("downloadAloneFile")
    public void downloadAloneFile(String id, HttpServletResponse response,
            HttpServletRequest request) throws WebException
    {
        try
        {
            String agent = request.getHeader("User-Agent");
            boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
            Map<String,String> params=new HashMap<String,String>();
            params.put("id", id);
            String res = httpClient.post(hostName+"getAlone.htm", params);
            JsonMapper jm=new JsonMapper();
            ProjectItemsModel projectItemsModel=jm.fromJson(res, ProjectItemsModel.class);
            List<AloneOptimEntity>temList= projectItemsModel.getOptimList();
            AloneOptimEntity optim=temList.get(0);
            
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
}
