package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarCardView extends RecyclerView {

	private CalendarAdapter mAdapter;
	private int mYear, mMonth, mDay;
	private int mClickMode = 1;
	private ICalendarEventListener.OnDateSelectedListener mListener;
	private List<Calendar> mItems;

	public CalendarCardView(Context context) {
		super(context);
	}

	public CalendarCardView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		try {
			setLayoutManager(new GridLayoutManager(context, 7));
			mAdapter = new CalendarAdapter(context);
			setAdapter(mAdapter);
			setOverScrollMode(OVER_SCROLL_NEVER);
			mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(int position, long itemId) {
					Calendar date = mAdapter.getItem(position);
					if (date == null) return;
					removeCurrentDay();
					date.setSelected(true);
					mAdapter.update(position);

					boolean isClickable = true;
					if (mClickMode == 2 && !date.getSign()) {
						isClickable = false;
					}
					if (mListener != null && isClickable) {
						mListener.onDateSelected(date.getYear(), date.getMonth(), date.getDay());
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeCurrentDay() {
		int size = mAdapter.mItems.size();
		for (int i = 0; i < size; i++) {
			Calendar calendar = mAdapter.mItems.get(i);
			if (calendar.isSelected()) {
				mAdapter.setSelectedCalendar(calendar);
			}
			calendar.setSelected(false);
		}
	}

	void setCurrentDate(int year, int month, int day) {
		mYear = year;
		mMonth = month;
		mDay = day;
		initCalendar();
	}

	void setClickMode(int clickMode) {
		mClickMode = clickMode;
	}

	void setSelectedCalendar(Calendar calendar) {
		mAdapter.setSelectedCalendar(calendar);
	}

	void setOnDateSelectedListener(ICalendarEventListener.OnDateSelectedListener listener) {
		this.mListener = listener;
	}

	void setWrapContentHeightViewPager(WrapViewPager wrapViewPager, int position) {
		wrapViewPager.setViewForPosition(this, position);
	}

	private void initCalendar() {
		try {
//			Log.e("my", "initCalendar: mYear = " + mYear + ", mMonth = " + mMonth);
			java.util.Calendar date = java.util.Calendar.getInstance();
			date.set(mYear, mMonth - 1, 1);
			int firstDayOfWeek = date.get(java.util.Calendar.DAY_OF_WEEK) - 1;//月第一天为星期几,星期天 == 0
			int mDaysCount = Util.getMonthDaysCount(mYear, mMonth);
			date.set(mYear, mMonth - 1, mDaysCount);
			int lastDayOfWeek = date.get(java.util.Calendar.DAY_OF_WEEK) - 1;//月最后一天为星期几,星期天 == 0

			int nextMonthDaysOffset = 6 - lastDayOfWeek;//下个月的日偏移天数

//			Log.e("my", "initCalendar: firstDayOfWeek = " + firstDayOfWeek);
//			Log.e("my", "initCalendar: lastDayOfWeek = " + lastDayOfWeek);
//			Log.e("my", "initCalendar: mDaysCount = " + mDaysCount);
//			Log.e("my", "initCalendar: nextMonthDaysOffset = " + nextMonthDaysOffset);

			int preYear, preMonth;
			int nextYear, nextMonth;

			int mSize = firstDayOfWeek + mDaysCount + nextMonthDaysOffset;//日历卡要显示的总格数

			int preMonthDaysCount;
			if (mMonth == 1) {
				//如果是1月
				preYear = mYear - 1;
				preMonth = 12;
				nextYear = mYear;
				nextMonth = mMonth + 1;
				preMonthDaysCount = firstDayOfWeek == 0 ? 0 : Util.getMonthDaysCount(preYear, preMonth);
			} else if (mMonth == 12) {
				//如果是12月
				preYear = mYear;
				preMonth = mMonth - 1;
				nextYear = mYear + 1;
				nextMonth = 1;
				preMonthDaysCount = firstDayOfWeek == 0 ? 0 : Util.getMonthDaysCount(preYear, preMonth);
			} else {
				//平常
				preYear = mYear;
				preMonth = mMonth - 1;
				nextYear = mYear;
				nextMonth = mMonth + 1;
				preMonthDaysCount = firstDayOfWeek == 0 ? 0 : Util.getMonthDaysCount(preYear, preMonth);
			}
			int nextDay = 1;
			if (mItems == null) mItems = new ArrayList<>();
			mItems.clear();
//			boolean isHead = false;
//			for (int i = 0; i < 7; i++) {
//				Calendar calendarDate = new Calendar();
//				if (!(i < firstDayOfWeek || i >= mDaysCount + firstDayOfWeek) && !isHead) {
//					calendarDate.setCurrentMonth(true);
//					calendarDate.setHead(true);
//					calendarDate.setYear(mYear);
//					calendarDate.setMonth(mMonth);
//					isHead = true;
//				}
//				mItems.add(calendarDate);
//			}

			boolean hasSetSelectedDay = false;
			for (int i = 0; i < mSize; i++) {
				Calendar calendarDate = new Calendar();
				if (i < firstDayOfWeek) {
					//上月的
					calendarDate.setYear(preYear);
					calendarDate.setMonth(preMonth);
					calendarDate.setDay(preMonthDaysCount - firstDayOfWeek + i + 1);
				} else if (i >= mDaysCount + firstDayOfWeek) {
					//下月的
					calendarDate.setYear(nextYear);
					calendarDate.setMonth(nextMonth);
					calendarDate.setDay(nextDay);
					++nextDay;
				} else {
					//当前月的
					calendarDate.setYear(mYear);
					calendarDate.setMonth(mMonth);
					calendarDate.setCurrentMonth(true);
					calendarDate.setDay(i - firstDayOfWeek + 1);
				}
				if (calendarDate.getMonth() == mMonth && calendarDate.getDay() == mDay) {
					hasSetSelectedDay = true;
					calendarDate.setSelected(true);
				}
				mItems.add(calendarDate);
			}

			if (!hasSetSelectedDay) {
				for (int i = mItems.size() - 1; i > 0; i--) {
					Calendar calendarDate = mItems.get(i);
					if (calendarDate.getMonth() == mMonth && mDay > calendarDate.getDay()) {
						calendarDate.setSelected(true);
						break;
					}
				}
			}
//			Log.e("my", "initCalendar: mItems = " + mItems.toString());
			mAdapter.addAll(mItems);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateData(Map<String, Integer> map) {
		try {
			int len = mAdapter.getItemCount();
			for (int i = 0; i < len; i++) {
				Calendar calendar = mAdapter.getItem(i);
				String day = String.valueOf(calendar.getDay());
				int count = 0;
				if (!TextUtils.isEmpty(day) && map.containsKey(day)) {
					count = map.get(day);
				}
				if (count > 0) {
					calendar.setSign(true);
				} else {
					calendar.setSign(false);
				}
			}
			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateView(int day) {
		try {
			// mDay是当前月份上次有效果的day
			// day是已经变换效果的day
			// 如果相同，则不需要更新
			if (mDay == day) return;

			boolean isDayLarge = day > mDay; // 比较天数的大小，循环执行的逻辑顺序不同，减少循环次数
			boolean hasSetSelectedDay = false;
			List<Calendar> calendarList = mAdapter.getItems();
			int size = calendarList.size();
			for (int i = 0; i < size; i++) {
				Calendar calendarDate = calendarList.get(i);
				if (isDayLarge) {
					if (calendarDate.getMonth() == mMonth && calendarDate.getDay() == mDay) {
						calendarDate.setSelected(false);
					} else if (calendarDate.getMonth() == mMonth && calendarDate.getDay() == day) {
						hasSetSelectedDay = true;
						calendarDate.setSelected(true);
						break;
					}
				} else {
					if (calendarDate.getMonth() == mMonth && calendarDate.getDay() == day) {
						hasSetSelectedDay = true;
						calendarDate.setSelected(true);
					} else if (calendarDate.getMonth() == mMonth && calendarDate.getDay() == mDay) {
						calendarDate.setSelected(false);
						break;
					}
				}
			}
			mDay = day;

			// 如果没有匹配到相应的day，则选中当前月份的最后一天。例如3月31，切换到2月份时，2月没有31，所以要选中2月的最后一天
			if (!hasSetSelectedDay) {
				for (int i = size - 1; i > 0; i--) {
					Calendar calendarDate = calendarList.get(i);
					if (calendarDate.getMonth() == mMonth && day > calendarDate.getDay()) {
						mDay = calendarDate.getDay();
						calendarDate.setSelected(true);
						break;
					}
				}
			}
			mAdapter.notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
