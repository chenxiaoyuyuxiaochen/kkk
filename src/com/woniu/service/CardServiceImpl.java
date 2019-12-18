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
			//被转账的账户信息
			Card inCard=cd.findOne(inCid);
			//被转账用户金额增加
			inCard.setCmoney(inCard.getCmoney()+transferMoney);
			cd.update(inCard);
			//转账的账户信息
			Card outCard = cd.findOne(outCid);
			if(outCard.getCmoney()<transferMoney) {
				throw new RuntimeException("转出账户余额不足");
			}
			outCard.setCmoney(outCard.getCmoney()-transferMoney);
			cd.update(outCard);
			
			conn.commit();
			System.out.println("转账成功");
			
		} catch (Exception e) {
			try {
				//出现异常就回滚 保证事务的一致性
				conn.rollback();
				System.out.println("转账时出现异常，转账取消");
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
