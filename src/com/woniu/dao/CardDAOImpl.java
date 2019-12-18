package com.woniu.dao;

import java.util.List;

import com.woniu.pojo.Card;

public class CardDAOImpl implements ICardDAO {
	BaseDao<Card> bd = new BaseDao<Card>();
	@Override
	public void save(Card obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Card card) {
		// TODO Auto-generated method stub
		String sql="update card set cno=?,cname=?,cmoney=? where cid=?";
		Object[] objs= {card.getCno(),card.getCname(),card.getCmoney(),card.getCid()};
		bd.update(sql,objs);
	}

	@Override
	public void delete(Integer cid) {
		// TODO Auto-generated method stub
		String sql="delete from card where cid=?";
		Object[] objs= {cid};
		bd.update(sql, objs);
	}

	@Override
	public Card findOne(Integer cid) {
		String sql="select * from card where cid=?";
		Object[] objs= {cid};
		List<Card> cards=bd.select(sql, objs, Card.class);
		return cards.size()==0?null:cards.get(0);
	}

	@Override
	public List<Card> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
