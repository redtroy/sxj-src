package com.sxj.science.website.controller.downloadTemplet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.science.entity.export.HistoryEntity;
import com.sxj.science.entity.export.ItemEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.entity.export.WindowTypeEntity;
import com.sxj.science.entity.system.AreaEntity;
import com.sxj.science.model.ItemModel;
import com.sxj.science.model.ProjectQuery;
import com.sxj.science.service.IAreaService;
import com.sxj.science.service.IDownloadTemService;
import com.sxj.science.service.IHistoryService;
import com.sxj.science.service.IProjectService;
import com.sxj.science.website.controller.BaseController;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/downloadTem")
public class DownloadTemController extends BaseController
{
    
    @Autowired
    private IDownloadTemService downloadTemService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IHistoryService historyService;
    
    @RequestMapping("/getTemp")
    public void getTemp(String[] id, HttpServletResponse response)
            throws WebException
    {
        try
        {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < id.length; i++)
            {
                WindowTypeEntity type = downloadTemService.getType(id[0]);
                sb.append(type.getHtmlData());
            }
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.getWriter().write(sb.toString());
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }
    
    @RequestMapping("/query")
    public String query(WindowTypeEntity query, ModelMap map, String areaId,
            String searchStr) throws WebException
    {
        try
        {
            //            if (query != null)
            //            {
            //                query.setPagable(true);
            //            }
            List<WindowTypeEntity> allList = downloadTemService.queryWindowType(new WindowTypeEntity());
            List<String> areaIdList = new ArrayList<String>();
            for (int i = 0; i < allList.size(); i++)
            {
                if (!areaIdList.contains(allList.get(i).getArea()))
                    areaIdList.add(allList.get(i).getArea());
            }
            query.setArea(areaId);
            query.setSearchStr(searchStr);
            List<WindowTypeEntity> list = downloadTemService.queryWindowType(query);
            List<String> companyNameList = new ArrayList<String>();
            List<String> typeList = new ArrayList<String>();
            List<String> seriesList = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++)
            {
                if (!companyNameList.contains(list.get(i).getCompanyName()))
                    companyNameList.add(list.get(i).getCompanyName());
                if (!typeList.contains(list.get(i).getType()))
                    typeList.add(list.get(i).getType());
                if (!seriesList.contains(list.get(i).getSeries()))
                    seriesList.add(list.get(i).getSeries());
            }
            List<AreaEntity> areaList = areaService.getAreaByIdList(areaIdList);
            map.put("areaList", areaList);
            map.put("companyNameList", companyNameList);
            map.put("typeList", typeList);
            map.put("seriesList", seriesList);
            map.put("list", list);
            map.put("query", query);
            return "site/downloadTemplet/downloadTemplet";
        }
        catch (Exception e)
        {
            SxjLogger.error("加载模板页面错误", e, this.getClass());
            throw new WebException("加载模板页面错误");
        }
    }
    
    @RequestMapping("/clickSearch")
    public String clickSearch(WindowTypeEntity query, ModelMap map,
            String areaId, String companyArr, String typeArr, String seriesArr,
            String searchStr) throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            List<WindowTypeEntity> allList = downloadTemService.queryWindowType(new WindowTypeEntity());
            List<String> areaIdList = new ArrayList<String>();
            for (int i = 0; i < allList.size(); i++)
            {
                if (!areaIdList.contains(allList.get(i).getArea()))
                    areaIdList.add(allList.get(i).getArea());
            }
            query.setArea(areaId);
            query.setCompanyName(companyArr);
            query.setType(typeArr);
            query.setSeries(seriesArr);
            query.setSearchStr(searchStr);
            //            Map<String,String> searchMap=new HashMap<String,String>();
            //            searchMap.put("companyArr", companyArr);
            //            searchMap.put("typeArr", typeArr);
            //            searchMap.put("seriesArr", seriesArr);
            List<WindowTypeEntity> list = downloadTemService.searchWindowType(query);
            List<String> companyNameList = new ArrayList<String>();
            List<String> typeList = new ArrayList<String>();
            List<String> seriesList = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++)
            {
                if (!companyNameList.contains(list.get(i).getCompanyName()))
                    companyNameList.add(list.get(i).getCompanyName());
                if (!typeList.contains(list.get(i).getType()))
                    typeList.add(list.get(i).getType());
                if (!seriesList.contains(list.get(i).getSeries()))
                    seriesList.add(list.get(i).getSeries());
            }
            List<AreaEntity> areaList = areaService.getAreaByIdList(areaIdList);
            map.put("areaList", areaList);
            map.put("companyNameList", companyNameList);
            map.put("typeList", typeList);
            map.put("seriesList", seriesList);
            map.put("list", list);
            map.put("query", query);
            return "site/downloadTemplet/downloadTemplet";
        }
        catch (Exception e)
        {
            SxjLogger.error("加载模板页面错误", e, this.getClass());
            throw new WebException("加载模板页面错误");
        }
    }
    
    @RequestMapping("/downloadTem")
    public void downloadTem(String idArr, HttpServletRequest request,
            HttpServletResponse response) throws WebException, IOException
    {
        InputStream in = null;
        ServletOutputStream out = null;
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            if (!StringUtil.isBlank(idArr))
            {
                List<WindowTypeEntity> list = downloadTemService.getWindowTypeByIds(idArr);
                String filePath = "sl/私享家-算料模板";
                dropFolderOrFile(new File("sl"));//删除文件
                File directory = new File(filePath);
                directory.mkdirs();//创建目录
                for (int i = 0; i < list.size(); i++)
                {
                    String temPath = list.get(i).getModelPath();
                    String group = temPath.split("/")[0];
                    String pathId = temPath.substring(group.length() + 1);
                    String suffix = temPath.split("\\.")[1];
                    File temFile = new File(filePath + "/"
                            + list.get(i).getName() + "." + suffix);
                    FileOutputStream output = new FileOutputStream(temFile);
                    storageClientService.downloadFile(group, pathId, output);
                    output.close();
                }
                zipFile(filePath);
                File zipFile = new File(filePath + ".zip");
                //
                String agent = request.getHeader("User-Agent");
                boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
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
                response.reset();
                
                response.addHeader("Content-Disposition",
                        "attachment;filename=" + fileName);
                //response.setContentType("APPLICATION/OCTET-STREAM");
                //response.setContentType("application/zip");
                response.setContentType("application/x-download");
                System.err.println(zipFile.getName());
                response.addHeader("Content-Length", "" + zipFile.length());
                response.setContentLength((int) zipFile.length());
                in = new FileInputStream(zipFile); //获取文件的流  
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
     * 将指定文件夹打包成zip
     * @param folder
     * @throws IOException 
     */
    private void zipFile(String folder) throws IOException
    {
        File zipFile = new File(folder + ".zip");
        if (zipFile.exists())
        {
            zipFile.delete();
        }
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(
                zipFile));
        File dir = new File(folder);
        File[] fs = dir.listFiles();
        byte[] buf = null;
        if (fs != null)
        {
            for (File f : fs)
            {
                zipout.putNextEntry(new ZipEntry(f.getName()));
                FileInputStream fileInputStream = new FileInputStream(f);
                buf = new byte[2048];
                BufferedInputStream origin = new BufferedInputStream(
                        fileInputStream, 2048);
                int len;
                while ((len = origin.read(buf, 0, 2048)) != -1)
                {
                    zipout.write(buf, 0, len);
                }
                zipout.flush();
                origin.close();
                fileInputStream.close();
            }
        }
        zipout.flush();
        zipout.close();
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
    
    @RequestMapping("/openQuery")
    public @ResponseBody Map<String, Object> openQuery(String area,
            String companyName, String type, String series, String name,
            String currentPage) throws WebException
    {
        try
        {
            WindowTypeEntity query = new WindowTypeEntity();
            if (query != null)
            {
                query.setPagable(true);
            }
            query.setCurrentPage(Integer.parseInt(currentPage));
            query.setShowCount(20);
            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (!StringUtil.isBlank(area))
            {
                query.setArea(area);
            }
            if (!StringUtil.isBlank(companyName))
            {
                query.setCompanyName(companyName);
            }
            if (!StringUtil.isBlank(type))
            {
                query.setType(type);
            }
            if (!StringUtil.isBlank(series))
            {
                query.setSeries(series);
            }
            if (!StringUtil.isBlank(name))
            {
                query.setName(name);
            }
            //            List<WindowTypeEntity> allList=downloadTemService.queryWindowType(new WindowTypeEntity());
            //            List<String> areaIdList=new ArrayList<String>();
            //            for(int i=0;i<allList.size();i++){
            //                if(!areaIdList.contains(allList.get(i).getArea()))
            //                    areaIdList.add(allList.get(i).getArea());
            //            }
            //            List<WindowTypeEntity> list = downloadTemService.queryWindowType(query);
            //            List<String> companyNameList=new ArrayList<String>();
            //            List<String> typeList=new ArrayList<String>();
            //            List<String> seriesList=new ArrayList<String>();
            //            for(int i=0;i<list.size();i++){
            //                if(!companyNameList.contains(list.get(i).getCompanyName()))
            //                    companyNameList.add(list.get(i).getCompanyName());
            //                if(!typeList.contains(list.get(i).getType()))
            //                    typeList.add(list.get(i).getType());
            //                if(!seriesList.contains(list.get(i).getSeries()))
            //                    seriesList.add(list.get(i).getSeries());
            //            }
            //            List<AreaEntity> areaList = areaService.getAreaByIdList(areaIdList);
            List<WindowTypeEntity> list = downloadTemService.openQueryWindowType(query);
            List<AreaEntity> areaList = areaService.getChildrenAreas("32");
            Map<String, String> areaMap = new HashMap<String, String>();
            for (int i = 0; i < areaList.size(); i++)
            {
                AreaEntity temArea = areaList.get(i);
                areaMap.put(temArea.getId(), temArea.getName());
            }
            for (int i = 0; i < list.size(); i++)
            {
                list.get(i).setAreaName(areaMap.get(list.get(i).getArea()));
            }
            resultMap.put("list", list);
            resultMap.put("areaList", areaList);
            resultMap.put("query", query);
            //            JsonMapper jm=new JsonMapper();
            //            String listJson=jm.toJson(list);
            //            String areaJson=jm.toJson(areaList);
            //            map.put("areaList", areaList);
            //            map.put("list", list);+"&&"+areaJson
            //            map.put("query", query);
            return resultMap;
        }
        catch (Exception e)
        {
            SxjLogger.error("加载模板接口错误", e, this.getClass());
            throw new WebException("加载模板接口错误");
        }
    }
    
    @RequestMapping("/addCountTem")
    public @ResponseBody String addCountTem(WindowTypeEntity query)
            throws WebException
    {
        //        Map<String,Object> resultMap=new HashMap<String,Object>();
        try
        {
            this.downloadTemService.addWindowType(query);
            //            resultMap.put("isOk", true);
            return query.getId();
        }
        catch (Exception e)
        {
            //            resultMap.put("isOk", false);
            SxjLogger.error("加载模板接口错误", e, this.getClass());
            throw new WebException("加载模板接口错误");
        }
    }
    
    @RequestMapping("/editCountTem")
    public @ResponseBody String editCountTem(WindowTypeEntity query)
            throws WebException
    {
        try
        {
            this.downloadTemService.updateWindowType(query);
            return query.getId();
        }
        catch (Exception e)
        {
            SxjLogger.error("加载模板接口错误", e, this.getClass());
            throw new WebException("加载模板接口错误");
        }
    }
    
    @RequestMapping("/delCountTem")
    public @ResponseBody String delCountTem(String id) throws WebException
    {
        //        Map<String,Object> resultMap=new HashMap<String,Object>();
        try
        {
            this.downloadTemService.delCountTem(id);
            //            resultMap.put("isOk", true);
            return "true";
        }
        catch (Exception e)
        {
            //            resultMap.put("isOk", false);
            SxjLogger.error("加载模板接口错误", e, this.getClass());
            throw new WebException("加载模板接口错误");
        }
    }
    
    @RequestMapping("/autoCompanyName")
    public @ResponseBody String autoCompanyName(String keyword)
            throws WebException
    {
        WindowTypeEntity query = new WindowTypeEntity();
        if (keyword != "" && keyword != null)
        {
            query.setCompanyName(keyword);
        }
        List<WindowTypeEntity> list = downloadTemService.autoWindowType(query);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (WindowTypeEntity temp : list)
        {
            sb = "{\"title\":\"" + temp.getCompanyName() + "\",\"result\":\""
                    + temp.getId() + "\",\"name\":\"" + temp.getName() + "\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        return json;
    }
    
    @RequestMapping("/autoType")
    public @ResponseBody String autoType(String keyword) throws WebException
    {
        WindowTypeEntity query = new WindowTypeEntity();
        if (keyword != "" && keyword != null)
        {
            query.setType(keyword);
        }
        List<WindowTypeEntity> list = downloadTemService.autoWindowType(query);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (WindowTypeEntity temp : list)
        {
            sb = "{\"title\":\"" + temp.getType() + "\",\"result\":\""
                    + temp.getId() + "\",\"name\":\"" + temp.getName() + "\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        return json;
    }
    
    @RequestMapping("/autoSeries")
    public @ResponseBody String autoSeries(String keyword) throws WebException
    {
        WindowTypeEntity query = new WindowTypeEntity();
        if (keyword != "" && keyword != null)
        {
            query.setSeries(keyword);
        }
        List<WindowTypeEntity> list = downloadTemService.autoWindowType(query);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (WindowTypeEntity temp : list)
        {
            sb = "{\"title\":\"" + temp.getSeries() + "\",\"result\":\""
                    + temp.getId() + "\",\"name\":\"" + temp.getName() + "\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        return json;
    }
    
    @RequestMapping("/loadFeeding")
    public @ResponseBody WindowTypeEntity loadFeeding(String id)
            throws WebException
    {
        WindowTypeEntity windowType = downloadTemService.getWindowTypeById(id);
        List<String> strlist = new ArrayList<String>();
        //        String sb = "";
        //        for (WindowTypeEntity temp : list)
        //        {
        //            sb = "{\"title\":\"" + temp.getSeries() + "\",\"result\":\""
        //                    + temp.getId() + "\",\"name\":\""+temp.getName()+"\"}";
        //            strlist.add(sb);
        //        }
        //        String json = "{\"data\":" + strlist.toString() + "}";
        return windowType;
    }
    
    @RequestMapping("/saveHtml")
    public @ResponseBody String saveHtml(String id, String htmlData)
            throws WebException
    {
        //        Map<String,Object> resultMap=new HashMap<String,Object>();
        try
        {
            WindowTypeEntity windowType = downloadTemService.getWindowTypeById(id);
            windowType.setHtmlData(htmlData);
            downloadTemService.updateWindowType(windowType);
            //            resultMap.put("isOk", true);
            return "true";
        }
        catch (Exception e)
        {
            //            resultMap.put("isOk", false);
            SxjLogger.error("加载模板接口错误", e, this.getClass());
            throw new WebException("加载模板接口错误");
        }
    }
    
    @RequestMapping("/openQueryProject")
    public @ResponseBody Map<String, Object> openQueryProject(String projectNo,
            String name, String memberName, String zhaoBiaoNo, String beiAnNo,
            String currentPage) throws WebException
    {
        try
        {
            ProjectQuery query = new ProjectQuery();
            if (query != null)
            {
                query.setPagable(true);
            }
            query.setCurrentPage(Integer.parseInt(currentPage));
            query.setShowCount(20);            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            if (!StringUtil.isBlank(projectNo))
            {
                query.setProjectNo(projectNo);
            }
            if (!StringUtil.isBlank(name))
            {
                query.setName(name);
            }
            if (!StringUtil.isBlank(memberName))
            {
                query.setMemberName(memberName);
            }
            if (!StringUtil.isBlank(zhaoBiaoNo))
            {
                query.setZhaoBiaoNo(zhaoBiaoNo);
            }
            if (!StringUtil.isBlank(beiAnNo))
            {
                query.setBeiAnNo(beiAnNo);
            }
            List<ProjectEntity> list = projectService.openQueryProject(query);
            resultMap.put("list", list);
            resultMap.put("query", query);
            return resultMap;
        }
        catch (Exception e)
        {
            SxjLogger.error("加载工程接口错误", e, this.getClass());
            throw new WebException("加载工程接口错误");
        }
    }
    
    @RequestMapping("/getItems")
    public @ResponseBody Map<String, Object> getProjectItem(String projectId)
            throws WebException
    {
        try
        {
            List<ItemModel> list = projectService.queryItems(projectId);
            ProjectEntity temPro = projectService.getProject(projectId);
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("projectItems", list);
            resultMap.put("project", temPro);
            resultMap.put("projectName", temPro.getName());
            return resultMap;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程条目错误", e, this.getClass());
            throw new WebException("查询工程条目错误", e);
        }
    }
    
    @RequestMapping("/deleteProject")
    public @ResponseBody Map<String, Object> deleteProject(String id)
            throws WebException
    {
        try
        {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            List<ItemModel> list = projectService.queryItems(id);
            if(list!=null&&list.size()>0){
                resultMap.put("isOK", "false");
            }else{
                projectService.deleteProject(id);
                resultMap.put("isOK", "true");
            }
            
            return resultMap;
        }
        catch (Exception e)
        {
            SxjLogger.error("删除工程错误", e, this.getClass());
            throw new WebException("删除工程错误", e);
        }
    }
    
    @RequestMapping("/deleteProjectItem")
    public @ResponseBody Map<String, Object> deleteProjectItem(String id)
            throws WebException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            projectService.deleteProjectItem(id);
            resultMap.put("isOK", "true");
        }
        catch (Exception e)
        {
            resultMap.put("isOK", "false");
            SxjLogger.error("删除批次错误", e, this.getClass());
            throw new WebException("删除批次错误", e);
        }
        return resultMap;
    }
    
    @RequestMapping("/queryHistory")
    public @ResponseBody Map<String, Object> queryHistory(String projectId)
            throws WebException
    {
        try
        {
            List<HistoryEntity> list = historyService.queryHistory(projectId);
            
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("list", list);
            return resultMap;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询工程条目错误", e, this.getClass());
            throw new WebException("查询工程条目错误", e);
        }
    }
    
    @RequestMapping("/changeShow")
    public @ResponseBody Map<String, Object> changeShow(String id,String isShow)
            throws WebException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            ProjectEntity temPro=projectService.getProject(id);
            temPro.setIsShow(Integer.parseInt(isShow));
            projectService.updateProject(temPro);
            resultMap.put("isOK", "true");
        }
        catch (Exception e)
        {
            resultMap.put("isOK", "false");
            SxjLogger.error("删除批次错误", e, this.getClass());
            throw new WebException("删除批次错误", e);
        }
        return resultMap;
    }
    
    @RequestMapping("/changeItemShow")
    public @ResponseBody Map<String, Object> changeItemShow(String id,String isShow)
            throws WebException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            ItemEntity temItem=projectService.getItemById(id);
            temItem.setIsShow(Integer.parseInt(isShow));
            projectService.updateItem(temItem);
            resultMap.put("isOK", "true");
        }
        catch (Exception e)
        {
            resultMap.put("isOK", "false");
            SxjLogger.error("删除批次错误", e, this.getClass());
            throw new WebException("删除批次错误", e);
        }
        return resultMap;
    }
}
