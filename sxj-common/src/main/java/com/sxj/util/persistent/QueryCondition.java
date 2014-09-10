package com.sxj.util.persistent;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.sxj.mybatis.pagination.Pagable;
import com.sxj.util.common.StringUtils;

/** 查询条件 */
public class QueryCondition<T> extends Pagable implements Serializable {

	private static final long serialVersionUID = 4676611900143160715L;

	public static final String ASC = "asc";

	public static final String DESC = "desc";

	private Map<String, Object> condition = new HashMap<String, Object>();

	private Set<String> columns = new HashSet<String>();

	private Set<String> groups = new HashSet<String>();

	/**
	 * 增加条件
	 * 
	 * @param key
	 *            键（查询条件字段名称）
	 * @param value
	 *            查询条件字段取值
	 */
	public void addCondition(String key, Object value) {
		if (StringUtils.isNotEmpty(key)) {
			condition.put(key, value);
		}
	}

	/**
	 * 增加查询返回列
	 * 
	 * @param columnName
	 *            列名称
	 */
	public void addColumn(String columnName) {
		columns.add(columnName);

	}

	/**
	 * 增加查询排序列
	 * 
	 * @param groupName
	 *            列名称
	 */
	public void addGroup(String groupName) {
		groups.add(groupName);

	}

	/**
	 * 将实体中所有的列格式化成sql查询列
	 * 
	 * @param entity
	 *            实体
	 */
	public void addAllColumn(Class<T> entity) {
		try {
			Field[] fields = entity.getDeclaredFields();
			for (Field field : fields) {
				if (Modifier.isStatic(field.getModifiers())) {
					if (field.getName().equals("serialVersionUID")) {
						continue;
					}
					columns.add((String) field.get(null));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Set<String> getColumns() {
		return columns;
	}

	public void setColumns(Set<String> columns) {
		if (columns == null) {

		}
		this.columns = columns;
	}

	public Set<String> getGroups() {
		return groups;
	}

	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}

	public Map<String, Object> getCondition() {
		return condition;
	}

	public void setCondition(Map<String, Object> condition) {
		this.condition = condition;
	}

	/**
	 * 获取SQL查询列字符串
	 * <p/>
	 * 即：select c1,c2,c3 from table_1 中的 c1,c2,c3
	 * 
	 * @return SQL查询列字符串
	 */
	public String getSelectColumn() {
		Iterator<String> iter = columns.iterator();
		String val;
		String sql = "";
		int i = 1;
		while (iter.hasNext()) {
			val = iter.next();
			if (i < columns.size()) {
				sql = sql + val + ",";
			} else {
				sql = sql + val;
			}
			i++;
		}
		if (StringUtils.isNotEmpty(sql)) {
			return sql;
		}
		return "";
	}

}
