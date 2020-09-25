package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jie.calendarviewdemo.R;

import java.util.HashMap;
import java.util.Map;

import androidx.core.content.ContextCompat;


public class HorizontalScrollViewCustom extends HorizontalScrollView implements View.OnClickListener {

	private Map<View, Integer> mViewPos = new HashMap<>();
	private LinearLayout mContainer;
	private HorizontalScrollViewAdapter mAdapter;
	private OnItemClickListener mOnClickListener;
	private int xScroll = 0;

	public interface OnItemClickListener {
		void onClick(View view, int position);
	}

	public HorizontalScrollViewCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mContainer = (LinearLayout) getChildAt(0);
	}

	public void initData(HorizontalScrollViewAdapter adapter) {
		this.mAdapter = adapter;
		mContainer = (LinearLayout) getChildAt(0);
		mContainer.removeAllViews();
		mViewPos.clear();
		int len = mAdapter.getCount();
		for (int i = 0; i < len; i++) {
			View view = mAdapter.getView(i, null, mContainer);
			view.setOnClickListener(this);
			mViewPos.put(view, i);
			mContainer.addView(view);
		}
	}

	/**
	 * 使用View.getMeasuredWidth和View.getMeasuredHeight方法获取当前组件的宽度和高度。
	 * 在调用这两个方法之前，必须调用View.measure方法先测量组件的宽度和高度。
	 * @param position
	 */
	public void setSelectTextColor(int position) {
//		Log.e("my", "setSelectTextColor: position = " + position);
		xScroll = 0;
		for (int i = 0; i < mContainer.getChildCount(); i++) {
			View view = mContainer.getChildAt(i);
			((TextView) view.findViewById(R.id.tv_txt)).setTextColor(ContextCompat.getColor(getContext(), R.color.clr_000000));
			if (position > i) {
//				Log.e("my", "setSelectTextColor: i = " + i);
				view.measure(0, 0);
				xScroll += view.getMeasuredWidth();
			}
		}
		if (position > -1) {
			View view = mContainer.getChildAt(position);
			((TextView) view.findViewById(R.id.tv_txt)).setTextColor(ContextCompat.getColor(getContext(), R.color.clr_ff7200));
//			Log.e("my", "setSelectTextColor: xScroll = " + xScroll);
			post(new Runnable() {
				@Override
				public void run() {
					scrollTo(xScroll, 0);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		if (mOnClickListener != null) {
			for (int i = 0; i < mContainer.getChildCount(); i++) {
				View view = mContainer.getChildAt(i);
				((TextView) view.findViewById(R.id.tv_txt)).setTextColor(ContextCompat.getColor(getContext(), R.color.clr_000000));
			}
			((TextView) v.findViewById(R.id.tv_txt)).setTextColor(ContextCompat.getColor(getContext(), R.color.clr_ff7200));
			mOnClickListener.onClick(v, mViewPos.get(v));
		}
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnClickListener = listener;
	}
}