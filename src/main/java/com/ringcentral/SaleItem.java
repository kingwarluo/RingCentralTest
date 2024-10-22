package com.ringcentral;

import java.math.BigDecimal;
import java.util.Date;

public class SaleItem {
	private int month;
	private Date date;
	private String transationId;
	private BigDecimal saleNumbers;
	
	
	public int getMonth() {
		return month;
	}
	public SaleItem setMonth(int month) {
		this.month = month;
		return this;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTransationId() {
		return transationId;
	}
	public void setTransationId(String transationId) {
		this.transationId = transationId;
	}
	public BigDecimal getSaleNumbers() {
		return saleNumbers;
	}
	public void setSaleNumbers(BigDecimal saleNumbers) {
		this.saleNumbers = saleNumbers;
	}

	public SaleItem(int month, BigDecimal saleNumbers) {
		this.month = month;
		this.saleNumbers = saleNumbers;
	}
}
