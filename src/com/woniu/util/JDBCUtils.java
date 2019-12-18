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
	//�̱߳��ر��� ��֤�����һ����
	static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();
	
	public static Connection getConn() throws SQLException {
		Connection conn=tl.get();
		//���û�����Ӿʹ���
		if(conn==null) {
			conn=DriverManager.getConnection(url,user,password);
			//�Ž��̱߳���
			tl.set(conn);
		}
		
		return conn;
	}
	public static void closeConn() throws SQLException{
		//����̱߳����������
		Connection conn=tl.get();
		if(conn==null) {
			conn.close();
			//�رյ�ʱ�򣬰��̱߳��ر������conn��գ��Ա��´�����ʼʱ�����µ�����
			tl.set(null);
		}
		
	}
}
