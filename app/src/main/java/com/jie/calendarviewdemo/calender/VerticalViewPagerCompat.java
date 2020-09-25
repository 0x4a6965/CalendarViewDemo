package com.jie.calendarviewdemo.calender;

import androidx.viewpager.widget.PagerAdapter;

public final class VerticalViewPagerCompat {

	private VerticalViewPagerCompat() {
	}

	public static void setDataSetObserver(PagerAdapter adapter, DataSetObserver observer) {
		adapter.registerDataSetObserver(observer);
	}

	public static class DataSetObserver extends android.database.DataSetObserver {
	}
}
