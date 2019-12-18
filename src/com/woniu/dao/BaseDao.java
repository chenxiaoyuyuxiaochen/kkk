package com.woniu.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.woniu.util.JDBCUtils;

public class BaseDao<T> {
private Connection conn;
private PreparedStatement stat;
private ResultSet rs;
public void update(String sql, Object[] objs) {
	try {
		
		conn=JDBCUtils.getConn();
		//���Զ��ύ����ص� ��֤�����һ����
		conn.setAutoCommit(false);
		
		
		stat=conn.prepareStatement(sql);
		for(int i=0;i<objs.length;i++) {
			stat.setObject(i+1,objs[i] );
		}
		int row=stat.executeUpdate();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public List<T> select(String sql,Object[] objs,Class c){
	List<T> list=new ArrayList<T>();
	try {
		conn=JDBCUtils.getConn();
		stat=conn.prepareStatement(sql);
		for(int i=0;i<objs.length;i++) {
			stat.setObject(i+1, objs[i]);
		}
		rs=stat.executeQuery();
		Method[] ms = c.getDeclaredMethods();
		while(rs.next()) {
			Object obj=c.newInstance();
			for(Method m:ms) {
				String mName=m.getName();
				//��set��ͷ�ķ�����
				if(mName.startsWith("set")) {
					//��set������׺������������ͬ�Ľ��������
					String fieldName=mName.substring(3);
					//��������
					Class[] cs=m.getParameterTypes();
					if(cs[0]==Integer.class) {
						//ִ��set����         �������ȡ��ֵ
						m.invoke(obj, rs.getInt(fieldName));
					}
					// 
					if(cs[0]==String.class) {
						m.invoke(obj, rs.getString(fieldName));
					}
				}
			}
			list.add((T)obj);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return list;
}
}
