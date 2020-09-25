package com.jie.calendarviewdemo.calender;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	@SuppressLint("SimpleDateFormat")
	public static int getDate(String formatStr, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return Integer.parseInt(format.format(date));
	}

	public static int getMonthDaysCount(int year, int month) {
		int count = 0;
		java.util.Calendar date = java.util.Calendar.getInstance();
		date.set(year, month - 1, 1);

		//判断大月份
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			count = 31;
		}

		//判断小月
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			count = 30;
		}

		//判断平年与闰年
		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				count = 29;
			} else {
				count = 28;
			}
		}
		return count;
	}

}
