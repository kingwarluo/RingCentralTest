package com.ringcentral;

import java.math.BigDecimal;

public class QuarterSalesItem {
	private int quarter;
	private BigDecimal value;
	
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public QuarterSalesItem(int quarter) {
		this.quarter = quarter;
	}

	@Override
	public String toString() {
		return "QuarterSalesItem{" +
				"quarter=" + quarter +
				", value=" + value +
				'}';
	}
}
