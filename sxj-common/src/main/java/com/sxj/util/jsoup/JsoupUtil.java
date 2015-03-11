package com.sxj.util.jsoup;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.sxj.util.common.SxjHttpClientImpl;

public class JsoupUtil
{
    
    public static void main(String... args)
    {
        try
        {
            System.out.println("\u95c2\u3127\u7365");
            String projectName = "门窗";
            System.out.println(URLEncoder.encode(projectName, "GBK"));
            System.out.println(new String(projectName.getBytes(), "GBK"));
            String url = "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx";
            Response response = Jsoup.connect(url).timeout(30000).execute();
            Document document = response.parse();
            String _VIEWSTATE = document.select("#__VIEWSTATE").val();
            String _EVENTVALIDATION = document.select("#__EVENTVALIDATION")
                    .val();
            Map<String, String> cookies = response.cookies();
            SxjHttpClientImpl im = new SxjHttpClientImpl();
            Document post = Jsoup.connect(url)
                    .timeout(30000)
                    .cookies(cookies)
                    .header("Host", "www1.njcein.com.cn")
                    .header("Referer",
                            "http://www1.njcein.com.cn/njxxnew/xmxx/zbgg/default.aspx")
                    .header("User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:36.0) Gecko/20100101 Firefox/36.0")
                    .header("Accept-Language",
                            " zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data("drpBiaoDuanType", "0")
                    .data("txtProjectName", projectName)
                    .data("__VIEWSTATE", _VIEWSTATE)
                    .data("__EVENTVALIDATION", _EVENTVALIDATION)
                    .data("ImageButton1.x", "19")
                    .encoding("GBK")
                    .data("ImageButton1.y", "5")
                    .post();
            _VIEWSTATE = post.select("#__VIEWSTATE").val();
            _EVENTVALIDATION = post.select("#__EVENTVALIDATION").val();
            System.out.println(post.toString());
            System.out.println(post.select("#txtProjectName").val());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
