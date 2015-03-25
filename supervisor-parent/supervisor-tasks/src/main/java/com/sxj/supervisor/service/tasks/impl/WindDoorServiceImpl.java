package com.sxj.supervisor.service.tasks.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import third.rewrite.fastdfs.service.IStorageClientService;
import third.rewrite.fastdfs.service.impl.ByteArrayFdfsFileInputStreamHandler;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.supervisor.dao.gather.WindDoorDao;
import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.service.message.ITransMessageService;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;

@Service
public class WindDoorServiceImpl implements IWindDoorService
{
    
    @Autowired
    private WindDoorDao wda;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Autowired
    private ITransMessageService tms;
    
    @Override
    @Transactional
    public void WindDoorGather() throws ServiceException
    {
        String oldGongGaoGuid = (String) HierarchicalCacheManager.get(CacheLevel.REDIS,
                "windDoor",
                "GongGaoGuid");
        System.out.println("WDGatherstar");
        try
        {
            Response response = Jsoup.connect("http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
                    .timeout(30000)
                    .execute();
            Document doc = response.parse();
            String __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
            String __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
                    .val();
            Map<String, String> cookies = response.cookies();
            int pageNum = page(__VIEWSTATE, __EVENTVALIDATION, cookies);
            int flag = 0;
            if (wda.getMaxWindDoor().size() < 1)
            {
                oldGongGaoGuid = null;
            }
            STOP: for (int i = 1; i <= pageNum; i++)
            {
                Map map = new HashMap();
                // map.put("ImageButton1.x", "32");
                // map.put("ImageButton1.y", "10");
                map.put("__EVENTVALIDATION", __EVENTVALIDATION);
                map.put("__VIEWSTATE", __VIEWSTATE);
                map.put("__EVENTTARGET", "Pager");
                map.put("__EVENTARGUMENT", "" + i);
                map.put("drpBiaoDuanType", "0");
                map.put("txtProjectName", "门窗");
                doc = (Document) Jsoup.connect("http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
                        .data(map)
                        .timeout(30000)
                        .cookies(cookies)
                        .header("Accept",
                                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                        .header("Host", "www1.njcein.com.cn")
                        .header("Referer",
                                "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
                        .header("User-Agent",
                                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0")
                        .header("Connection", "keep-alive")
                        .encoding("GBK")
                        .post();
                int k = doc.getElementById("tdcontent")
                        .getElementsByTag("tr")
                        .size();
                __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
                __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
                        .val();
                Elements trs = doc.getElementById("tdcontent")
                        .getElementsByTag("tr");
                for (Element tr : trs)
                {
                    String bdfl = tr.getElementsByTag("td").get(1).text(); // 标段分类
                    String xmmc = tr.getElementsByTag("td").get(2).text();// 项目名称
                    String url = tr.getElementsByTag("td")
                            .get(2)
                            .getElementsByTag("a")
                            .attr("href");
                    String qy = tr.getElementsByTag("td").get(3).text();// 区域
                    String jzsj = tr.getElementsByTag("td").get(4).text();// 截至日期
                    // System.out.println(bdfl);
                    // System.out.println(xmmc);
                    // System.out.println(qy);
                    // System.out.println(jzsj);
                    // System.out.println(url);
                    String GongGaoGuid = url.split("GongGaoGuid=")[1];
                    if (oldGongGaoGuid != null
                            && GongGaoGuid.equals(oldGongGaoGuid))
                    {
                        break STOP;
                    }
                    if (flag == 0)
                    {
                        HierarchicalCacheManager.set(CacheLevel.REDIS,
                                "windDoor",
                                "GongGaoGuid",
                                GongGaoGuid);
                    }
                    
                    Map<String, String> contentMap = content("http://www1.njcein.com.cn"
                            + url);
                    String filePath = storageClientService.uploadFile(null,
                            new ByteArrayInputStream(contentMap.get("content")
                                    .getBytes()),
                            contentMap.get("content").getBytes().length,
                            "jpg".toUpperCase());
                    System.out.println(filePath);
                    WindDoorEntity windDoor = new WindDoorEntity();
                    windDoor.setBdfl(bdfl);
                    windDoor.setFilePath(filePath);
                    windDoor.setJzrq(jzsj);
                    windDoor.setQy(qy);
                    windDoor.setXmmc(xmmc);
                    windDoor.setNowDate(new Date());
                    if (contentMap.get("gifPath") != null)
                    {
                        windDoor.setGifPath(contentMap.get("gifPath"));
                    }
                    wda.addWindDoor(windDoor);
                    CometServiceImpl.add(MessageChannel.MEMBER_TENDER_MESSAGE_INFO,
                            windDoor.getId());
                    flag++;
                }
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("抓取门窗信息出错", e, this.getClass());
            HierarchicalCacheManager.set(CacheLevel.REDIS,
                    "windDoor",
                    "GongGaoGuid",
                    oldGongGaoGuid);
            throw new ServiceException("抓取门窗信息出错", e);
        }
        System.out.println("WDGatherEnd");
        
    }
    
    public int page(String __VIEWSTATE, String __EVENTVALIDATION,
            Map<String, String> cookies) throws ServiceException
    {
        try
        {
            Map map = new HashMap();
            // map.put("ImageButton1.x", "32");
            // map.put("ImageButton1.y", "10");
            map.put("__EVENTVALIDATION", __EVENTVALIDATION);
            map.put("__VIEWSTATE", __VIEWSTATE);
            map.put("__EVENTTARGET", "Pager");
            map.put("__EVENTARGUMENT", "1");
            map.put("drpBiaoDuanType", "0");
            map.put("txtProjectName", "门窗");
            Document doc = (Document) Jsoup.connect("http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
                    .data(map)
                    .timeout(3000)
                    .cookies(cookies)
                    .header("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Host", "www1.njcein.com.cn")
                    .header("Referer",
                            "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0")
                    .header("Connection", "keep-alive")
                    .encoding("GBK")
                    .post();
            String pageNum = doc.getElementById("Pager")
                    .getElementsByTag("td")
                    .get(0)
                    .text()
                    .split("/")[1];
            return Integer.valueOf(pageNum);
        }
        catch (Exception e)
        {
            SxjLogger.error("抓取门窗信息出错", e, this.getClass());
            throw new ServiceException("抓取门窗分页信息出错", e);
        }
    }
    
    public Map<String, String> content(String url)
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            Document doc = (Document) Jsoup.connect(url).timeout(3000).get();
            Element element = doc.getElementById("ZBGGDetail1_tblInfo");
            if (element.getElementById("ZBGGDetail1_trAttach") != null
                    && !"".equals(element.getElementById("ZBGGDetail1_trAttach")))
            {
                element.getElementById("ZBGGDetail1_trAttach").remove();
            }
            element.getElementsByTag("tr").last().remove();
            String img = element.getElementById("ZBGGDetail1_divDS")
                    .getElementsByTag("img")
                    .attr("src");
            if (img != null && !"".equals(img))
            {
                String gifPath = downPicture("http://www1.njcein.com.cn/njxxnew/ZtbInfo/"
                        + img);
                map.put("gifPath", gifPath);
                element.getElementById("ZBGGDetail1_divDS")
                        .getElementsByTag("img")
                        .attr("src",
                                "http://storage.menchuang.org.cn/" + gifPath);
                element.getElementById("ZBGGDetail1_divDS").removeAttr("style");
                element.getElementById("ZBGGDetail1_divDS").attr("style",
                        "position: absolute;");
            }
            map.put("content", element.toString());
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("抓取门窗信息详情出错", e, this.getClass());
            throw new ServiceException("抓取门窗信息详情出错", e);
        }
    }
    
    public String downPicture(String picttrueUrl)
    {
        try
        {
            /* 将网络资源地址传给,即赋值给url */
            URL url = new URL(picttrueUrl);
            String fileName = picttrueUrl.substring(picttrueUrl.lastIndexOf("=") + 1,
                    picttrueUrl.length());
            System.out.println(fileName);
            /* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(
                    connection.getInputStream());
            
            /* 此处也可用BufferedInputStream与BufferedOutputStream 需要保存的路径 */
            // DataOutputStream out = new DataOutputStream(new FileOutputStream(
            // "D:\\test\\" + fileName + ".gif"));
            //
            // /* 将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址 */
            // byte[] buffer = new byte[4096];
            // int count = 0;
            // while ((count = in.read(buffer)) > 0)/* 将输入流以字节的形式读取并写入buffer中 */
            // {
            // out.write(buffer, 0, count);
            // }
            // out.close();/* 后面三行为关闭输入输出流以及网络资源的固定格式 */
            String gifPath = storageClientService.uploadFile(null,
                    in,
                    in.available(),
                    "gif".toUpperCase());
            in.close();
            connection.disconnect();
            return gifPath;/* 网络资源截取并存储本地成功返回true */
            
        }
        catch (Exception e)
        {
            SxjLogger.error("抓取门窗信息图片详情出错", e, this.getClass());
            return null;
        }
    }
    
    @Override
    public WindDoorEntity getInfoById(String id) throws ServiceException
    {
        try
        {
            WindDoorEntity wde = wda.getInfoById(id);
            String group = wde.getFilePath().substring(0,
                    wde.getFilePath().indexOf("/"));
            String path = wde.getFilePath().substring(wde.getFilePath()
                    .indexOf("/") + 1,
                    wde.getFilePath().length());
            String file = new String(storageClientService.downloadFile(group,
                    path,
                    new ByteArrayFdfsFileInputStreamHandler()));
            System.out.println(file);
            wde.setFilePath(file);
            return wde;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询门窗信息详情出错", e, this.getClass());
            throw new ServiceException("查询门窗信息详情出错", e);
        }
    }
    
    public static void main(String[] args)
    {
        WindDoorServiceImpl wd = new WindDoorServiceImpl();
        wd.content("http://www1.njcein.com.cn/njxxnew/ZtbInfo/ZBGGInfo.aspx?GongGaoGuid=47ab054f-ce98-4248-994f-906b63e24cd6");
    }
}
