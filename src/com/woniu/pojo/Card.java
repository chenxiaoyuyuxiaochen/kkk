package com.woniu.pojo;

import java.io.Serializable;

public class Card implements Serializable {
	private Integer cid;
	private String cno;
	private String cname;
	private Double cmoney;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCno() {
		return cno;
	}
	public void setCno(String cno) {
		this.cno = cno;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Double getCmoney() {
		return cmoney;
	}
	public void setCmoney(Double cmoney) {
		this.cmoney = cmoney;
	}
	@Override
	public String toString() {
		return "Card [cid=" + cid + ", cno=" + cno + ", cname=" + cname + ", cmoney=" + cmoney + "]";
	}
	
}
