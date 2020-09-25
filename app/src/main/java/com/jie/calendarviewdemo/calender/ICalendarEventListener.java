package com.jie.calendarviewdemo.calender;

public interface ICalendarEventListener {
	interface OnDateSelectedListener{
		void onDateSelected(int year, int month, int day);
	}

	interface OnMonthChangeListener {
		void onMonthChangeListener(int year, int month);
	}
}
