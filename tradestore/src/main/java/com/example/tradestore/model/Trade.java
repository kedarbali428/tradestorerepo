package com.example.tradestore.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trade_store")
public class Trade implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7561628911457279328L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String tradeid;
	
	private Integer version;
	
	private String counterpartyid;
	
	private String bookid;
	
	private Timestamp maturitydate;
	
	private Timestamp createddate;
	
	private Character expired;
	
	public Trade() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTradeid() {
		return tradeid;
	}

	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCounterpartyid() {
		return counterpartyid;
	}

	public void setCounterpartyid(String counterpartyid) {
		this.counterpartyid = counterpartyid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public Timestamp getMaturitydate() {
		return maturitydate;
	}

	public void setMaturitydate(Timestamp maturitydate) {
		this.maturitydate = maturitydate;
	}

	public Timestamp getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	
	public Character getExpired() {
		return expired;
	}

	public void setExpired(Character expired) {
		this.expired = expired;
	}

}
