package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class WrapViewPager extends DirectionalViewPager {

	private int currentIndex;
	private int height = 0;
	//保存view对应的索引
	private HashMap<Integer, View> mChildrenViews =new LinkedHashMap<>();

	public WrapViewPager(Context context) {
		super(context);
	}

	public WrapViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int index = getCurrentItem();
//		int height = 0;
//		View v = ((CalendarCardView) getAdapter().instantiateItem(this, index));
////		View v = (CalendarCardView) getTag(17);
//		if (v != null) {
//			v.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//			height = v.getMeasuredHeight();
//		}
//		Log.e("my", "onMeasure: index = " + index + ", height = " + height);
//		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

//		int height = 0;
//		//下面遍历所有child的高度
//		Log.e("my", "onMeasure: getChildCount = " + getChildCount());
//		Log.e("my", "onMeasure: getFocusedChild() = " + getFocusedChild());
//		View v = getFocusedChild();
//		Log.e("my", "onMeasure: v = " + v);
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			Log.e("my", "onMeasure: i = " + i + ", h = " + h);
//			//采用最大的view的高度。
//			if (h > height) {
//				height = h;
//			}
//		}
//		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

		Log.e("my", "setViewForPosition: childView = " + mChildrenViews.toString());
		View child = mChildrenViews.get(currentIndex);
		child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		height = child.getMeasuredHeight();
		Log.e("my", "onMeasure: height = " + height);
		if (mChildrenViews.size() != 0) {
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 重新设置高度
	 *
	 * @param current
	 */
	public void resetHeight(int current) {
		Log.e("my", "resetHeight: current = " + current);
		currentIndex = current;
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
		} else {
			layoutParams.height = height;
		}
		setLayoutParams(layoutParams);
	}

	/**
	 * 保存View对应的索引,需要自适应高度的一定要设置这个
	 */
	public void setViewForPosition(View view, int position) {
		mChildrenViews.put(position, view);
	}
}
