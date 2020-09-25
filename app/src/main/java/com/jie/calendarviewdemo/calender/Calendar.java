package com.jie.calendarviewdemo.calender;

import java.io.Serializable;

@SuppressWarnings("all")
public class Calendar implements Serializable {

	private int year;
	private int month;
	private int day;
	private boolean currentMonth;
	private boolean isCurrentDay;
	private boolean selected;
	private boolean isHead = false;//展现月份
	private boolean isSign = false;

	public boolean getSign() {
		return isSign;
	}

	public void setSign(boolean isSign) {
		this.isSign = isSign;
	}

	public boolean getHead() {
		return isHead;
	}

	public void setHead(boolean isHead) {
		this.isHead = isHead;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean isCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(boolean currentMonth) {
		this.currentMonth = currentMonth;
	}

	public boolean isCurrentDay() {
		return isCurrentDay;
	}

	public void setCurrentDay(boolean currentDay) {
		isCurrentDay = currentDay;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Calendar) {
			if (((Calendar) o).getYear() == year && ((Calendar) o).getMonth() == month && ((Calendar) o).getDay() == day)
				return true;
		}
		return super.equals(o);
	}

	@Override
	public String toString() {
		return year + "" + (month < 10 ? "0" + month : month) + "" + (day < 10 ? "0" + day : day);
	}
}
