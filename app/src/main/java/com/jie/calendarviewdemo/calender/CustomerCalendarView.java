package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jie.calendarviewdemo.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class CustomerCalendarView extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

	private int mMinYear, mMaxYear, mCurYear, mCurMonth, mSelectYear, mSelectDay;
	private int mClickMode; // 日期点击的模式  1.所有日期都可以  2.有数据的日期才可点击
	private boolean mIsPageScroll; // ViewPager滑动
	private Context mContext;
	private WrapViewPager mViewPager;
	private Calendar mSelectedCalendar;
//	private CheckBox mBtnSelectYear;
	private LinearLayout mLlSelectYearAndMonth;
	private HorizontalScrollViewCustom mHSVYear, mHSVMonth; // 点击年份，选择年份和月份
	private CalendarViewPagerAdapter mAdapter;
	private ICalendarEventListener.OnMonthChangeListener mMonthChangeListener; // 月份切换监听
	private ICalendarEventListener.OnDateSelectedListener mDateSelectedListener; // 日期选择监听
	private Map<String, CalendarCardView> mCalendarCardMap = new HashMap<>();
	private int mIndex;

	public CustomerCalendarView(@NonNull Context context) {
		super(context);
	}

	public CustomerCalendarView(@NonNull Context context, @NonNull AttributeSet attrs) {
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomerCalendarView);
		mClickMode = typedArray.getInteger(R.styleable.CustomerCalendarView_click_mode, 1);
		typedArray.recycle();

		init(context);
	}

	private void init(Context context) {
		mContext = context;
	}

	public void initView(String curDate) {
		try {
			// 初始化当前的日期
			if (!TextUtils.isEmpty(curDate)) {
				String[] dateArr = curDate.split("-");
				if (dateArr.length == 3) {
					mSelectedCalendar = new Calendar();
					mSelectedCalendar.setYear(Integer.parseInt(dateArr[0]));
					mSelectedCalendar.setMonth(Integer.parseInt(dateArr[1]));
					mSelectedCalendar.setDay(Integer.parseInt(dateArr[2]));
				}
			}
			if (mSelectedCalendar == null) {
				mSelectedCalendar = new Calendar();
				Date date = new Date();
				mSelectedCalendar.setYear(Util.getDate("yyyy", date));
				mSelectedCalendar.setMonth(Util.getDate("MM", date));
				mSelectedCalendar.setDay(Util.getDate("dd", date));
			}
			mCurYear = mSelectedCalendar.getYear();
			mCurMonth = mSelectedCalendar.getMonth();
			mSelectDay = mSelectedCalendar.getDay();

			View view = LayoutInflater.from(mContext).inflate(R.layout.layout_calendar_view, this, true);
			mViewPager = view.findViewById(R.id.vp_calendar);
			mViewPager.setOrientation(DirectionalViewPager.HORIZONTAL); // 设置 左右 HORIZONTAL，上下 VERTICAL 滑动切换月份
			mViewPager.setScrollable(false); // 是否开启滑动
			mLlSelectYearAndMonth = view.findViewById(R.id.ll_select_year_month);
			// BtnSelectYear
			Drawable leftDrawable = ContextCompat.getDrawable(this.getContext(), R.drawable.sel_left_arrow);
			int n = (int) getResources().getDimension(R.dimen.dimen13);
			leftDrawable.setBounds(0, 0, n, n);
//			mBtnSelectYear = view.findViewById(R.id.btn_select_year);
//			mBtnSelectYear.setCompoundDrawables(leftDrawable, null, null, null);
//			mBtnSelectYear.setText(String.format(getResources().getString(R.string.space_year), String.valueOf(mCurYear)));
//			mBtnSelectYear.setOnCheckedChangeListener(this);
			mMinYear = mCurYear - 1;
			mMaxYear = mCurYear + 2;

			final String[] list1 = new String[]{String.valueOf(mCurYear - 1), String.valueOf(mCurYear), String.valueOf(mCurYear + 1)};
			final String[] list2 = new String[]{mContext.getResources().getString(R.string.january), mContext
					.getResources().getString(R.string.february), mContext.getResources().getString(R.string.march), mContext
					.getResources().getString(R.string.april), mContext.getResources().getString(R.string.may), mContext
					.getResources().getString(R.string.june), mContext.getResources().getString(R.string.july), mContext
					.getResources().getString(R.string.august), mContext.getResources().getString(R.string.september), mContext
					.getResources().getString(R.string.october), mContext.getResources().getString(R.string.november), mContext
					.getResources().getString(R.string.december)};
			HorizontalScrollViewAdapter yearAdapter = new HorizontalScrollViewAdapter(mContext, list1);
			HorizontalScrollViewAdapter monthAdapter = new HorizontalScrollViewAdapter(mContext, list2);

			// 横向选择年份
			mHSVYear = view.findViewById(R.id.hsv_year);
			mHSVYear.initData(yearAdapter);
			mHSVYear.setSelectTextColor(mCurYear - mMinYear);
			mHSVYear.setOnItemClickListener(new HorizontalScrollViewCustom.OnItemClickListener() {
				@Override
				public void onClick(View view, int position) {
					mSelectYear = Integer.valueOf(list1[position]);
					mHSVMonth.setSelectTextColor(-1);
				}
			});

			// 横向选择月份
			mHSVMonth = view.findViewById(R.id.hsv_month);
			mHSVMonth.initData(monthAdapter);
			mHSVMonth.setSelectTextColor(mCurMonth - 1);
			mHSVMonth.setOnItemClickListener(new HorizontalScrollViewCustom.OnItemClickListener() {
				@Override
				public void onClick(View view, int position) {
					mCurMonth = position + 1;
					if (mSelectYear > 0 && mCurMonth > 0) {
						mCurYear = mSelectYear;
						mIndex = 12 * (mCurYear - mMinYear) + mCurMonth - 1;
						mViewPager.setCurrentItem(mIndex);
//						mBtnSelectYear.setChecked(false);
//						mBtnSelectYear.setText(String.format(getResources().getString(R.string.space_year), String.valueOf(mCurYear)));
						mMonthChangeListener.onMonthChangeListener(mCurYear, mCurMonth);
					}
				}
			});

			mIsPageScroll = false;
			// 监听日历的滑动
			mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					mIsPageScroll = true;
				}

				@Override
				public void onPageSelected(int position) {
					if (mMonthChangeListener != null) {
						mCurYear = position / 12 + mMinYear;
						mCurMonth = position % 12 + 1;
						// 滑动时更新当前月份的day显示效果
						String key = mCurYear + "-" + mCurMonth;
						if (mCalendarCardMap.containsKey(key)) {
							CalendarCardView calendarCardView = mCalendarCardMap.get(key);
							calendarCardView.updateView(mSelectDay);
						}
//						mBtnSelectYear.setText(String.format(getResources().getString(R.string.space_year), String.valueOf(mCurYear)));
					}
				}

				/**
				 * state = 0 ：静止状态
				 * state = 1 ：滑动状态
				 * state = 2 ：滑动停止
				 */
				@Override
				public void onPageScrollStateChanged(int state) {
					if (state == 0) {
						mIsPageScroll = false;
					}
				}
			});

			int y = mSelectedCalendar.getYear() - mMinYear;
			mIndex = 12 * y + mSelectedCalendar.getMonth() - 1;
			mAdapter = new CalendarViewPagerAdapter();
			mViewPager.setAdapter(mAdapter);
			mViewPager.setCurrentItem(mIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 回到手机当前时间的月份
	 */
	public void restore() {
		int y = mSelectedCalendar.getYear() - mMinYear;
		int position = 12 * y + mSelectedCalendar.getMonth() - 1;
		mViewPager.setCurrentItem(position);
	}

	/**
	 * 初始化时设置当前月份的数据
	 */
	public void initData(Map<String, Integer> map) {
		String key = mCurYear + "-" + mCurMonth;
		if (mAdapter != null) mAdapter.initData(key, map);
	}

	/**
	 * 更新月份数据
	 */
	public void update(Map<String, Integer> map) {
		String key = mCurYear + "-" + mCurMonth;
		if (mAdapter != null) mAdapter.updateData(key, map);
	}

	public void setOnMonthChangeListener(ICalendarEventListener.OnMonthChangeListener listener) {
		this.mMonthChangeListener = listener;
	}

	public void setOnDateSelectedListener(ICalendarEventListener.OnDateSelectedListener listener) {
		this.mDateSelectedListener = listener;
	}

	/**
	 * 监听BtnSelectYear的Check事件（点击弹出年/月选择器，并将当前的年份和月份标注）
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			final int year = mCurYear - mMinYear;
			mHSVYear.setSelectTextColor(year);
			mSelectYear = mCurYear;
//			mHSVMonth.post(new Runnable() {
//				@Override
//				public void run() {
//					mHSVYear.scrollTo(year, 0);
//				}
//			});

			final int month = mCurMonth - 1;
			mHSVMonth.setSelectTextColor(month);
//			mHSVMonth.post(new Runnable() {
//				@Override
//				public void run() {
//					mHSVMonth.scrollTo(200, 100);
//				}
//			});
			mLlSelectYearAndMonth.setVisibility(VISIBLE);
		} else {
			mLlSelectYearAndMonth.setVisibility(GONE);
		}
	}

	private class CalendarViewPagerAdapter extends PagerAdapter {

		private String mKey;
		private Map<String, Integer> mDataMap = new HashMap<>();

		@Override
		public int getCount() {
			return 12 * (mMaxYear - mMinYear);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			int year = position / 12 + mMinYear;
			int month = position % 12 + 1;
			CalendarCardView view = new CalendarCardView(getContext(), null);
			view.setTag(position);
			view.setCurrentDate(year, month, mSelectDay);
			view.setClickMode(mClickMode);
			view.setSelectedCalendar(mSelectedCalendar);
//			view.setWrapContentHeightViewPager(mViewPager, position);
			view.setOnDateSelectedListener(new ICalendarEventListener.OnDateSelectedListener() {
				@Override
				public void onDateSelected(int year, int month, int day) {
					mSelectDay = day;
					mDateSelectedListener.onDateSelected(year, month, day);
				}
			});
			container.addView(view);
			String key = year + "-" + month;
			mCalendarCardMap.put(key, view);
			if (key.equals(mKey)) {
				updateData(mKey, mDataMap);
			}
			Log.e("my", "instantiateItem: mIndex = " + mIndex);
			Log.e("my", "instantiateItem: position = " + position);
			mViewPager.setViewForPosition(view, position);
			mViewPager.resetHeight(mIndex);
			if (mIndex == position) {
//
			}
			return view;
		}

		public void initData(String key, Map<String, Integer> map) {
			mKey = key;
			mDataMap = map;
		}

		private void updateData(String key, Map<String, Integer> map) {
			if (!TextUtils.isEmpty(key) && mCalendarCardMap.containsKey(key)) {
				CalendarCardView view = mCalendarCardMap.get(key);
				if (view != null) {
					view.updateData(map);
				}
			}
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			(container).removeView((View) object);
		}
	}

	public int getCurYear(){
		return mCurYear;
	}

	public int getCurMonth(){
		return mCurMonth;
	}
}

