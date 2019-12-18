package com.woniu.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.woniu.dao.CardDAOImpl;
import com.woniu.dao.ICardDAO;
import com.woniu.pojo.Card;
import com.woniu.util.JDBCUtils;

public class CardServiceImpl implements ICardService {
	ICardDAO cd=new CardDAOImpl();
	@Override
	public void transferAccounts(Integer outCid, Integer inCid, Double transferMoney) {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn=JDBCUtils.getConn();
			//��ת�˵��˻���Ϣ
			Card inCard=cd.findOne(inCid);
			//��ת���û��������
			inCard.setCmoney(inCard.getCmoney()+transferMoney);
			cd.update(inCard);
			//ת�˵��˻���Ϣ
			Card outCard = cd.findOne(outCid);
			if(outCard.getCmoney()<transferMoney) {
				throw new RuntimeException("ת���˻�����");
			}
			outCard.setCmoney(outCard.getCmoney()-transferMoney);
			cd.update(outCard);
			
			conn.commit();
			System.out.println("ת�˳ɹ�");
			
		} catch (Exception e) {
			try {
				//�����쳣�ͻع� ��֤�����һ����
				conn.rollback();
				System.out.println("ת��ʱ�����쳣��ת��ȡ��");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try {
				JDBCUtils.closeConn();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
