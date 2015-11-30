package com.sxj.supervisor.service.tasks.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.supervisor.dao.gather.WindDoorDao;
import com.sxj.supervisor.dao.member.IMemberDao;
import com.sxj.supervisor.dao.message.ITenderMessageDao;
import com.sxj.supervisor.entity.gather.WindDoorEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.message.TenderMessageEntity;
import com.sxj.supervisor.enu.message.MessageStateEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.tasks.WindDoorModel;
import com.sxj.supervisor.service.message.IMessageConfigService;
import com.sxj.supervisor.service.tasks.IWindDoorService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

import third.rewrite.fastdfs.service.IStorageClientService;

@Service
public class WindDoorServiceImpl implements IWindDoorService
{
    
    private static final Logger logger = LoggerFactory
            .getLogger(WindDoorServiceImpl.class);
            
    @Autowired
    private WindDoorDao wda;
    
    @Autowired
    private ITenderMessageDao tenderMessageDao;
    
    @Autowired
    private IMemberDao memberDao;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Autowired
    private IMessageConfigService configService;
    
    @Autowired
    private ProjectGrabber grabber;
    
    @Override
    @Transactional
    public void WindDoorGather() throws ServiceException
    {
        List<WindDoorEntity> mc = grabber.grab("门窗");
        logger.info("共采集门窗信息:{}", mc.size());
        List<WindDoorEntity> mq = grabber.grab("幕墙");
        logger.info("共采集幕墙信息:{}", mq.size());
        mc.addAll(mq);
        List<WindDoorEntity> created = new ArrayList<>();
        for (WindDoorEntity entity : mc)
        {
            if (!saveWithoutTransaction(entity))
            {
                updateWithoutTransaction(entity);
            }
            else
            {
                created.add(entity);
            }
        }
        //updateTenderMessageSync(created);
        sendSmsSync(created.size());
    }
    
    private void sendSmsSync(final int count)
    {
        //final List<WindDoorEntity> list
        new Thread()
        {
            public void run()
            {
                for (int i = 0; i < count; i++)
                {
                    configService.sendAllMessage("您有一条新的开发商招标信息");
                }
            }
        }.start();
    }
    
    private void updateTenderMessageSync(final List<WindDoorEntity> list)
    {
        new Thread()
        {
            public void run()
            {
                try
                {
                    for (WindDoorEntity entity : list)
                    {
                        QueryCondition<MemberEntity> query = new QueryCondition<>();
                        List<MemberEntity> members = memberDao
                                .queryMembers(query);
                        for (MemberEntity e : members)
                        {
                            TenderMessageEntity message = new TenderMessageEntity();
                            message.setInfoId(entity.getId());
                            message.setMemberNo(e.getMemberNo());
                            message.setState(MessageStateEnum.UNREAD);
                            tenderMessageDao.save(message);
                        }
                    }
                }
                catch (Exception e)
                {
                
                }
            }
        }.start();
    }
    
    private boolean updateWithoutTransaction(WindDoorEntity entity)
    {
        try
        {
            WindDoorEntity old = wda.getByOid(entity.getOid());
            String id = old.getId();
            BeanUtils.copyProperties(entity, old);
            old.setId(id);
            wda.updateWind(old);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    private boolean saveWithoutTransaction(WindDoorEntity entity)
    {
        try
        {
            wda.save(entity);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    //    private void gatherMQ() throws ServiceException
    //    {
    //        String oldGongGaoGuid = (String) HierarchicalCacheManager
    //                .get(CacheLevel.REDIS, "MQ", "GongGaoGuid");
    //        //        oldGongGaoGuid = null;
    //        System.out.println("MQGatherstar");
    //        List<WindDoorEntity> bathList = new ArrayList<WindDoorEntity>();
    //        try
    //        {
    //            Response response = Jsoup
    //                    .connect(
    //                            "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
    //                    .timeout(30000)
    //                    .execute();
    //            Document doc = response.parse();
    //            String __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
    //            String __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
    //                    .val();
    //            Map<String, String> cookies = response.cookies();
    //            int pageNum = page(__VIEWSTATE, __EVENTVALIDATION, cookies);
    //            int flag = 0;
    //            if (wda.getMaxWindDoor().size() < 1)
    //            {
    //                oldGongGaoGuid = null;
    //            }
    //            STOP: for (int i = 1; i <= pageNum; i++)
    //            {
    //                Map map = new HashMap();
    //                // map.put("ImageButton1.x", "32");
    //                // map.put("ImageButton1.y", "10");
    //                map.put("__EVENTVALIDATION", __EVENTVALIDATION);
    //                map.put("__VIEWSTATE", __VIEWSTATE);
    //                map.put("__EVENTTARGET", "Pager");
    //                map.put("__EVENTARGUMENT", "" + i);
    //                map.put("drpBiaoDuanType", "0");
    //                map.put("txtProjectName", "幕墙");
    //                doc = (Document) Jsoup
    //                        .connect(
    //                                "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
    //                        .data(map)
    //                        .timeout(30000)
    //                        .cookies(cookies)
    //                        .header("Accept",
    //                                "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    //                        .header("Host", "www1.njcein.com.cn")
    //                        .header("Referer",
    //                                "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
    //                        .header("User-Agent",
    //                                "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:32.0) Gecko/20100101 Firefox/32.0")
    //                        .header("Connection", "keep-alive")
    //                        .encoding("GBK")
    //                        .post();
    //                int k = doc.getElementById("tdcontent")
    //                        .getElementsByTag("tr")
    //                        .size();
    //                __VIEWSTATE = doc.getElementById("__VIEWSTATE").val();
    //                __EVENTVALIDATION = doc.getElementById("__EVENTVALIDATION")
    //                        .val();
    //                Elements trs = doc.getElementById("tdcontent")
    //                        .getElementsByTag("tr");
    //                for (Element tr : trs)
    //                {
    //                    String bdfl = tr.getElementsByTag("td").get(1).text(); // 标段分类
    //                    String xmmc = tr.getElementsByTag("td").get(2).text();// 项目名称
    //                    String url = tr.getElementsByTag("td")
    //                            .get(2)
    //                            .getElementsByTag("a")
    //                            .attr("href");
    //                    String qy = tr.getElementsByTag("td").get(3).text();// 区域
    //                    String jzsj = tr.getElementsByTag("td").get(4).text();// 截至日期
    //                    // System.out.println(bdfl);
    //                    // System.out.println(xmmc);
    //                    // System.out.println(qy);
    //                    // System.out.println(jzsj);
    //                    // System.out.println(url);
    //                    String GongGaoGuid = url.split("GongGaoGuid=")[1];
    //                    if (oldGongGaoGuid != null
    //                            && GongGaoGuid.equals(oldGongGaoGuid))
    //                    {
    //                        break STOP;
    //                    }
    //                    if (flag == 0)
    //                    {
    //                        HierarchicalCacheManager.set(CacheLevel.REDIS,
    //                                "MQ",
    //                                "GongGaoGuid",
    //                                GongGaoGuid);
    //                    }
    //                    
    //                    Map<String, String> contentMap = content(
    //                            "http://www1.njcein.com.cn" + url);
    //                    if (contentMap == null
    //                            || contentMap.get("content").length() < 200)
    //                    {
    //                        continue;
    //                    }
    //                    String filePath = storageClientService.uploadFile(null,
    //                            new ByteArrayInputStream(
    //                                    contentMap.get("content").getBytes()),
    //                            contentMap.get("content").getBytes().length,
    //                            "jpg".toUpperCase());
    //                    System.out.println(filePath);
    //                    WindDoorEntity windDoor = new WindDoorEntity();
    //                    windDoor.setId(StringUtils.getUUID());
    //                    windDoor.setBdfl(bdfl);
    //                    windDoor.setFilePath(filePath);
    //                    windDoor.setJzrq(jzsj);
    //                    windDoor.setQy(qy);
    //                    windDoor.setXmmc(xmmc);
    //                    windDoor.setState(0);
    //                    windDoor.setPublishFirm("网站采集");
    //                    windDoor.setNowDate(new Date());
    //                    if (contentMap.get("gifPath") != null)
    //                    {
    //                        windDoor.setGifPath(contentMap.get("gifPath"));
    //                    }
    //                    bathList.add(windDoor);
    //                    flag++;
    //                }
    //            }
    //            if (bathList.size() > 0)
    //            {
    //                wda.addWindDoor(bathList);
    //                CometServiceImpl.setCount(
    //                        MessageChannel.MEMBER_TENDER_MESSAGE_COUNT,
    //                        Long.valueOf(bathList.size()));
    //                List<String> keys = CometServiceImpl
    //                        .get(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS);
    //                if (keys != null && keys.size() > 0)
    //                {
    //                    for (String key : keys)
    //                    {
    //                        if (key == null)
    //                        {
    //                            continue;
    //                        }
    //                        CometServiceImpl.setCount(key,
    //                                Long.valueOf(bathList.size()));
    //                    }
    //                }
    //            }
    //        }
    //        catch (Exception e)
    //        {
    //            SxjLogger.error("抓取幕墙信息出错", e, this.getClass());
    //            HierarchicalCacheManager.set(CacheLevel.REDIS,
    //                    "MQ",
    //                    "GongGaoGuid",
    //                    oldGongGaoGuid);
    //            throw new ServiceException("抓取幕墙信息出错", e);
    //        }
    //        for (int iii = 0; iii < bathList.size(); iii++)
    //        {
    //            //发短信
    //            configService.sendAllMessage("您有一条新的开发商招标信息");
    //            CometServiceImpl.add(MessageChannel.MEMBER_TENDER_MESSAGE_INFO,
    //                    bathList.get(iii).getId());
    //        }
    //        System.out.println("MQGatherEnd");
    //    }
    
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
            Document doc = (Document) Jsoup
                    .connect(
                            "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
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
            Document doc = (Document) Jsoup.connect(url).timeout(10000).get();
            Element element = doc.getElementById("ZBGGDetail1_tblInfo");
            if (element.getElementById("ZBGGDetail1_trAttach") != null && !""
                    .equals(element.getElementById("ZBGGDetail1_trAttach")))
            {
                element.getElementById("ZBGGDetail1_trAttach").remove();
            }
            element.getElementsByTag("tr").last().remove();
            String img = element.getElementById("ZBGGDetail1_divDS")
                    .getElementsByTag("img")
                    .attr("src");
            if (img != null && !"".equals(img))
            {
                String gifPath = downPicture(
                        "http://www1.njcein.com.cn/njxxnew/ZtbInfo/" + img);
                map.put("gifPath", gifPath);
                element.getElementById("ZBGGDetail1_divDS")
                        .getElementsByTag("img")
                        .attr("src", gifPath);
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
            return null;
        }
    }
    
    public String downPicture(String picttrueUrl)
    {
        try
        {
            /* 将网络资源地址传给,即赋值给url */
            URL url = new URL(picttrueUrl);
            String fileName = picttrueUrl.substring(
                    picttrueUrl.lastIndexOf("=") + 1, picttrueUrl.length());
            System.out.println(fileName);
            /* 此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流 */
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
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
    
    public String codeString(String fileName) throws Exception
    {
        ByteArrayInputStream bin = new ByteArrayInputStream(
                fileName.getBytes());
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        
        switch (p)
        {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        
        return code;
    }
    
    @Override
    public WindDoorEntity getInfoById(String id) throws ServiceException
    {
        try
        {
            WindDoorEntity wde = wda.getInfoById(id);
            String group = wde.getFilePath().substring(0,
                    wde.getFilePath().indexOf("/"));
            String path = wde.getFilePath().substring(
                    wde.getFilePath().indexOf("/") + 1,
                    wde.getFilePath().length());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            storageClientService.downloadFile(group, path, output);
            if (output.toByteArray() != null)
            {
                String file = new String(output.toByteArray());
                //                if (file.indexOf("GB2312") > 0 || file.indexOf("gb2312") > 0)
                //                    file = new String(output.toByteArray(), "GB2312");
                Document doc = Jsoup.parse(file);
                doc.select("body").removeClass("b2");
                /*String img = doc.getElementById("ZBGGDetail1_divDS")
                        .getElementsByTag("img")
                        .attr("src");
                if (img != null && !"".equals(img))
                {
                    doc.getElementById("ZBGGDetail1_divDS")
                            .getElementsByTag("img")
                            .attr("src", "http://storage.menchuang.org.cn/" + img);
                    doc.getElementById("ZBGGDetail1_divDS").removeAttr("style");
                    doc.getElementById("ZBGGDetail1_divDS").attr("style",
                            "position: absolute;");
                }*/
                wde.setFilePath(doc.toString());
                output.close();
            }
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
        wd.content(
                "http://www1.njcein.com.cn/njxxnew/ZtbInfo/ZBGGInfo.aspx?GongGaoGuid=47ab054f-ce98-4248-994f-906b63e24cd6");
    }
    
    @Override
    @Transactional
    public void insertWordHtml(WindDoorEntity wind) throws ServiceException
    {
        try
        {
            List<WindDoorEntity> bathList = new ArrayList<WindDoorEntity>();
            WindDoorEntity windDoor = new WindDoorEntity();
            windDoor.setId(StringUtils.getUUID());
            windDoor.setBdfl(wind.getBdfl());
            windDoor.setFilePath(wind.getFilePath());
            windDoor.setFilePathBack(wind.getFilePathBack());
            windDoor.setJzrq(wind.getJzrq());
            windDoor.setQy(wind.getQy());
            windDoor.setXmmc(wind.getXmmc());
            windDoor.setState(0);
            windDoor.setAdjunctPath(wind.getAdjunctPath());
            windDoor.setPublishFirm(wind.getPublishFirm());
            windDoor.setNowDate(new Date());
            bathList.add(windDoor);
            if (bathList.size() > 0)
            {
                wda.addWindDoor(bathList);
                CometServiceImpl
                        .takeCount(MessageChannel.MEMBER_TENDER_MESSAGE_COUNT);
                List<String> keys = CometServiceImpl
                        .get(MessageChannel.MEMBER_READTENDER_MESSAGE_KEYS);
                if (keys != null && keys.size() > 0)
                {
                    for (String key : keys)
                    {
                        if (key == null)
                        {
                            continue;
                        }
                        CometServiceImpl.takeCount(key);
                    }
                }
            }
            for (int iii = 0; iii < bathList.size(); iii++)
            {
                //发短信
                configService.sendAllMessage("您有一条新的开发商招标信息");
                CometServiceImpl.add(MessageChannel.MEMBER_TENDER_MESSAGE_INFO,
                        bathList.get(iii).getId());
            }
        }
        catch (Exception e)
        {
            SxjLogger.error("word导入市场信息失败", e, this.getClass());
            throw new ServiceException("word导入市场信息失败", e);
        }
        
    }
    
    @Override
    public List<WindDoorEntity> query(WindDoorModel query)
            throws ServiceException
    {
        try
        {
            QueryCondition<WindDoorEntity> condition = new QueryCondition<WindDoorEntity>();
            List<WindDoorEntity> windList = new ArrayList<WindDoorEntity>();
            if (query == null)
            {
                return windList;
            }
            condition.addCondition("publishFirm", query.getPublishFirm());// 发布单位
            condition.addCondition("xmmc", query.getXmmc());// 项目名称
            condition.addCondition("starDate", query.getStarDate());// 开始时间
            condition.addCondition("endDate", query.getEndDate());// 结束时间
            condition.addCondition("state", query.getState());// 状态
            condition.setPage(query);
            windList = wda.queryList(condition);
            query.setPage(condition);
            return windList;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询市场信息失败", e, this.getClass());
            throw new ServiceException("查询市场信息失败", e);
        }
    }
    
    @Override
    public void checkState(String id, Integer state) throws ServiceException
    {
        try
        {
            WindDoorEntity wd = new WindDoorEntity();
            wd.setId(id);
            wd.setState(state);
            wda.updateWind(wd);
        }
        catch (Exception e)
        {
            SxjLogger.error("更改市场信息状态失败", e, this.getClass());
            throw new ServiceException("更改市场信息状态失败", e);
        }
        
    }
    
    @Override
    public List<WindDoorEntity> queryUnread(String memberNo)
    {
        return wda.queryUnreadByMember(memberNo);
    }
}
