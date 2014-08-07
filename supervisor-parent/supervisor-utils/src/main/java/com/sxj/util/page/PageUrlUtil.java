/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sxj.util.page;

import java.util.Map;

import com.sxj.util.common.StringUtils;

/**
 * 根据HTTP请求路径和查询参数生成分页URL的工具类
 */
public class PageUrlUtil {

    public static String getPageUrl(Map paramMap, String url) {
        String urlParam = "";
        String[] paramValues;
        int i = 0;
        for (Object o : paramMap.entrySet()) {
            String key;
            String value;
            Map.Entry entry = (Map.Entry) o;
            key = entry.getKey().toString();
            paramValues = (String[]) entry.getValue();
            value = paramValues[0];
            if (key.equals("page.curPageNo") || key.equals("page.pageSize")) {
                continue;
            }
            if (i < (paramMap.size() - 1)) {
                urlParam += key + "=" + value + "&";
            } else {
                urlParam += key + "=" + value;
            }
            i++;
        }
        if (StringUtils.isEmpty((urlParam))) {
            return url;
        }
        return url + "?" + urlParam;
    }
}
