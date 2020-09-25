package com.jie.calendarviewdemo;

import android.os.Bundle;
import android.widget.Toast;

import com.jie.calendarviewdemo.calender.CustomerCalendarView;
import com.jie.calendarviewdemo.calender.ICalendarEventListener;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//	private DatePicker datePicker;
	private CustomerCalendarView customerCalendarView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		customerCalendarView = findViewById(R.id.calendar_view);
		customerCalendarView.setOnDateSelectedListener(new ICalendarEventListener.OnDateSelectedListener() {
			@Override
			public void onDateSelected(int year, int month, int day) {
				Toast.makeText(MainActivity.this, "year : " + year + ", month : " + month + ", day : " + day, Toast.LENGTH_SHORT).show();
			}
		});

		customerCalendarView.setOnMonthChangeListener(new ICalendarEventListener.OnMonthChangeListener() {
			@Override
			public void onMonthChangeListener(int year, int month) {
				Toast.makeText(MainActivity.this, "year : " + year + ", month : " + month, Toast.LENGTH_SHORT).show();
			}
		});
		customerCalendarView.initView("2019-10-20");

		Map<String, Integer> map = new HashMap<>();
		map.put("1", 2);
		map.put("14", 1);
		map.put("19", 1);
		customerCalendarView.initData(map);

//		List<String> dateList = new ArrayList<>();
//		dateList.add("2010-10-01");
//		dateList.add("2010-10-11");
//		dateList.add("2010-10-14");
//		DPCManager.getInstance().setDecorTR(dateList);
//
//		datePicker = findViewById(R.id.dp_date);
//		datePicker.setDate(2019, 10);
//		datePicker.setMode(DPMode.SINGLE);
//		datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//			@Override
//			public void onDatePicked(String date) {
//				Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
//			}
//		});
//
//		datePicker.setDPDecor(new DPDecor() {
//			@Override
//			public void drawDecorTR(Canvas canvas, Rect rect, Paint paint) {
//				paint.setColor(Color.RED);
//				canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 4, paint);
//			}
//		});
	}
}
