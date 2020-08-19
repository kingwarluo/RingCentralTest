package com.ringcentral;

public class QuarterSalesItem {
	private int quarter;
	private double value;
	
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "QuarterSalesItem{" +
				"quarter=" + quarter +
				", value=" + value +
				'}';
	}
}
