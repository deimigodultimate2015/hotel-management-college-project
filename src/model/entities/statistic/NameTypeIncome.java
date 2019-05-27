package model.entities.statistic;

import java.text.DecimalFormat;

public class NameTypeIncome {
	private String name;
	private String type;
	private double Income;
	
	public NameTypeIncome() {
		
	}

	public NameTypeIncome(String name, String type, double income) {
		super();
		this.name = name;
		this.type = type;
		Income = income;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getIncome() {
		return Income;
	}

	public void setIncome(double income) {
		Income = income;
	}

	public String getIncomeAF() {
    	DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
		return df.format(Income);
    }
}
