package com.woniu.dao;

import java.util.List;

import com.woniu.pojo.Card;

public interface ICardDAO {
	void save(Card obj);
	void update(Card obj);
	void delete(Integer cid);
	Card findOne(Integer cid);
	List<Card> findAll();
}
