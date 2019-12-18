package com.woniu.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
	static String driver;
	static String url;
	static String user;
	static String password;
	static {
		
		try {
			Properties ps=new Properties();
			ps.load(JDBCUtils.class.getResourceAsStream("jdbc.properties"));
			driver=ps.getProperty("driver");
			url=ps.getProperty("url");
			user = ps.getProperty("user");
			password = ps.getProperty("password");
			Class.forName(driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//线程本地变量 保证事务的一致性
	static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	
	public static Connection getConn() throws SQLException {
		Connection conn=tl.get();
		//如果没有连接就创建
		if(conn==null) {
			conn=DriverManager.getConnection(url,user,password);
			//放进线程变量
			tl.set(conn);
		}
		
		return conn;
	}
	public static void closeConn() throws SQLException{
		//获得线程变量里的连接
		Connection conn=tl.get();
		if(conn==null) {
			conn.close();
			//关闭的时候，把线程本地变量里的conn清空，以便下次事务开始时创建新的连接
			tl.set(null);
		}
		
	}
}
