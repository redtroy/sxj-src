package com.sxj.util.persistent;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.sxj.util.common.StringUtils;

/**
 * 排序条件
 */
public class OrderCondition implements Serializable {

    private static final long serialVersionUID = -5451918343188085904L;

    public static final String ASC = "asc";

    public static final String DESC = "desc";

    private Map<String, String> conditions = new LinkedHashMap<String, String>();

    public Map<String, String> getConditions() {
        return conditions;
    }

    /**
     * 添加排序字段和排序方式
     *
     * @param key   排序字段名
     * @param value asc|desc
     */
    public void add(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            if (!DESC.equalsIgnoreCase(value))
                conditions.put(key, ASC);
            else
                conditions.put(key, value);
        }
    }

    /**
     * 删除排序字段
     *
     * @param key 排序字段名
     */
    public void remove(String key) {
        if (StringUtils.isNotEmpty(key)) {
            conditions.remove(key);
        }
    }

    /**
     * 获取排序方式
     *
     * @param key 排序字段名
     * @return 如果排序字段名存在返回 [asc|desc]，不存在返回 null
     */
    public String getValue(String key) {
        return conditions.get(key);
    }

    /**
     * 合并排序字段
     *
     * @param prefix 字段名前缀
     * @return 排序字段
     */
    public String aggOrder(String prefix) {
        String sql = " order by ";
        int i = 1;
        if (conditions.size() == 0) {
            sql += prefix + ".id " + DESC;
        }
        for (Map.Entry<String, String> entry : conditions.entrySet()) {
            if (i < conditions.size()) {
                sql += prefix + "." + entry.getKey() + " " + entry.getValue() + ",";
            } else {
                sql += prefix + "." + entry.getKey() + " " + entry.getValue();
            }
            i++;
        }
        return sql;
    }


}
