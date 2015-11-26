package com.sxj.supervisor.service.tasks.impl;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.spring.modules.util.StringUtils;
import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.service.tasks.grabber.Info;
import com.sxj.supervisor.service.tasks.grabber.RequestHeader;

import third.rewrite.fastdfs.service.IStorageClientService;

/**
 * 工程信息抓取
 * @author Administrator
 *
 */
@Service
public class ProjectGrabber
{
    private static final String DOMAIN = "http://www1.njcein.com.cn";
    
    private static final String PAGER = "Pager";
    
    private static final String TXT_PROJECT_NAME = "txtProjectName";
    
    private static final String DRP_BIAO_DUAN_TYPE = "drpBiaoDuanType";
    
    private static final String EVENTARGUMENT = "__EVENTARGUMENT";
    
    private static final String EVENTTARGET = "__EVENTTARGET";
    
    private static final String VIEWSTATE = "__VIEWSTATE";
    
    private static final String EVENTVALIDATION = "__EVENTVALIDATION";
    
    private static final String TARGET = "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx";
    
    private static final Logger logger = LoggerFactory
            .getLogger(ProjectGrabber.class);
            
    @Autowired
    private IStorageClientService storageClientService;
    
    public void grab(String projectName)
    {
        try
        {
            logger.debug("---------开始抓取数据---------");
            RequestHeader param = touch();
            List<WindDoorEntity> list = new ArrayList<>();
            page(projectName, param, list);
            logger.debug("");
            for (WindDoorEntity entity : list)
                System.out.println(
                        entity.getXmmc() + "------" + entity.getGifPath()
                                + "------" + entity.getFilePath());
        }
        catch (IOException e)
        {
            logger.error("---------抓取数据异常---------", e);
        }
    }
    
    private Map<String, String> buildRequestParam(String projectName,
            RequestHeader param)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(EVENTVALIDATION, param.getEventValidation());
        map.put(VIEWSTATE, param.getViewState());
        if (StringUtils.isNotEmpty(param.getEventTarget()))
            map.put(EVENTTARGET, param.getEventTarget());
        if (StringUtils.isNotEmpty(param.getEventArgument()))
            map.put(EVENTARGUMENT, param.getEventArgument());
        map.put(DRP_BIAO_DUAN_TYPE, "0");
        map.put(TXT_PROJECT_NAME, projectName);
        return map;
    }
    
    private void page(String projectName, RequestHeader param,
            List<WindDoorEntity> list) throws IOException
    {
        if (param.getPages() >= Integer.parseInt(param.getEventArgument()))
        {
            
            Map<String, String> map = buildRequestParam(projectName, param);
            Document doc = Jsoup.connect(TARGET)
                    .data(map)
                    .timeout(30000)
                    .cookies(param.getCookies())
                    .header("Accept",
                            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Host", "www1.njcein.com.cn")
                    .header("Referer", TARGET)
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0")
                    .header("Connection", "keep-alive")
                    .encoding("GBK")
                    .post();
            logger.debug("正在从{}抓取整页数据，参数{}", TARGET, param);
            list.addAll(table(doc));
            updateHeader(doc, param);
            page(projectName, param, list);
        }
        
    }
    
    private List<WindDoorEntity> table(Document doc) throws IOException
    {
        List<WindDoorEntity> list = new ArrayList<>();
        Elements trs = doc.getElementById("tdcontent").getElementsByTag("tr");
        for (Element tr : trs)
        {
            Elements tds = tr.getElementsByTag("td");
            String bdfl = tds.get(1).text(); // 标段分类
            String xmmc = tds.get(2).text();// 项目名称
            String url = tds.get(2).getElementsByTag("a").attr("href");
            String qy = tds.get(3).text();// 区域
            String jzsj = tds.get(4).text();// 截至日期
            
            Info info = row(DOMAIN + url);
            byte[] bytes = info.getContent().getBytes();
            String filePath = storageClientService.uploadFile(null,
                    new ByteArrayInputStream(bytes),
                    bytes.length,
                    "JPG");
            WindDoorEntity windDoor = new WindDoorEntity();
            windDoor.setId(StringUtils.getUUID());
            windDoor.setBdfl(bdfl);
            windDoor.setFilePath(filePath);
            windDoor.setJzrq(jzsj);
            windDoor.setQy(qy);
            windDoor.setXmmc(xmmc);
            windDoor.setState(0);
            windDoor.setPublishFirm("网站采集");
            windDoor.setNowDate(new Date());
            if (StringUtils.isNotEmpty(info.getImage()))
            {
                windDoor.setGifPath(info.getImage());
            }
            list.add(windDoor);
        }
        return list;
    }
    
    private Info row(String url) throws IOException
    {
        logger.debug("正在从{}抓取单行数据", url);
        Document doc = (Document) Jsoup.connect(url).timeout(10000).get();
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
        Info info = new Info();
        if (StringUtils.isNotEmpty(img))
        {
            String gifPath = fetchImage(
                    "http://www1.njcein.com.cn/njxxnew/ZtbInfo/" + img);
            element.getElementById("ZBGGDetail1_divDS")
                    .getElementsByTag("img")
                    .attr("src", gifPath);
            element.getElementById("ZBGGDetail1_divDS").removeAttr("style");
            element.getElementById("ZBGGDetail1_divDS").attr("style",
                    "position: absolute;");
            info.setImage(gifPath);
        }
        info.setContent(element.toString());
        return info;
    }
    
    private String fetchImage(String url) throws IOException
    {
        logger.debug("正在从{}下载图片", url);
        DataInputStream in = null;
        HttpURLConnection connection = null;
        try
        {
            /* 将网络资源地址传给,即赋值给url */
            URL u = new URL(url);
            /* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
            connection = (HttpURLConnection) u.openConnection();
            in = new DataInputStream(connection.getInputStream());
            String gifPath = storageClientService.uploadFile(null,
                    in,
                    in.available(),
                    "gif".toUpperCase());
            connection.disconnect();
            return gifPath;/* 网络资源截取并存储本地成功返回true */
        }
        catch (IOException ioe)
        {
            throw new IOException(ioe);
        }
        finally
        {
            if (in != null)
                in.close();
            if (connection != null)
                connection.disconnect();
        }
        
    }
    
    private RequestHeader getHeader(Document doc)
    {
        String __VIEWSTATE = doc.getElementById(VIEWSTATE).val();
        String __EVENTVALIDATION = doc.getElementById(EVENTVALIDATION).val();
        //        Map<String, String> cookies = response.cookies();
        RequestHeader header = new RequestHeader(__VIEWSTATE,
                __EVENTVALIDATION);
        header.setEventTarget(PAGER);
        header.setEventArgument("1");
        header.setPages(1);
        return header;
    }
    
    private void updateHeader(Document doc, RequestHeader header)
    {
        String __VIEWSTATE = doc.getElementById(VIEWSTATE).val();
        String __EVENTVALIDATION = doc.getElementById(EVENTVALIDATION).val();
        header.setViewState(__VIEWSTATE);
        header.setEventValidation(__EVENTVALIDATION);
        //配置分页参数
        String pageNum = doc.getElementById(PAGER)
                .getElementsByTag("td")
                .get(0)
                .text()
                .split("/")[1];
        if (StringUtils.isNotEmpty(pageNum))
        {
            header.setPages(Integer.parseInt(pageNum));
            header.setEventTarget(PAGER);
            header.setEventArgument(String
                    .valueOf(Integer.parseInt(header.getEventArgument()) + 1));
        }
        else
            header.setPages(0);
    }
    
    private RequestHeader touch() throws IOException
    {
        Response response = Jsoup.connect(TARGET)
                .timeout(30000)
                .encoding("GBK")
                .execute();
        Document doc = response.parse();
        RequestHeader header = getHeader(doc);
        header.setCookies(response.cookies());
        return header;
    }
    
    public void setStorageClientService(
            IStorageClientService storageClientService)
    {
        this.storageClientService = storageClientService;
    }
    
}
