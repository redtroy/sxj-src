package com.sxj.supervisor.tasks;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ConnectionDb {
	public static final String url = "jdbc:mysql://192.168.1.218:3306/sxj-supervisor?characterEncoding=utf8";
	public static final String name = "com.mysql.jdbc.Driver";
	public static final String user = "sxj-supervisor";
	public static final String password = "sxj-supervisor";

	public Connection conn = null;
	public PreparedStatement pst = null;

	public void DBHelper(String sql) {
		try {
			Class.forName(name);// 指定连接类型
			conn = (Connection) DriverManager
					.getConnection(url, user, password);// 获取连接
			pst = (PreparedStatement) conn.prepareStatement(sql);// 准备执行语句

			pst.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DBHelper2(String sql, List<String> list) {
		try {
			Class.forName(name);// 指定连接类型
			conn = (Connection) DriverManager
					.getConnection(url, user, password);// 获取连接
			pst = (PreparedStatement) conn.prepareStatement(sql);// 准备执行语句
			for (int i = 1; i <= list.size(); i++) {
				pst.setString(i, list.get(i - 1));
			}
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
