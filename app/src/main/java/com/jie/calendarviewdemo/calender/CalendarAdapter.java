package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import com.jie.calendarviewdemo.R;

import androidx.recyclerview.widget.RecyclerView;


public class CalendarAdapter extends BaseRecyclerAdapter<Calendar> {

	private int mThemeColor = 0xffff7200, mCurColor = Color.WHITE;
	private int mSelectedPosition = -1;
	private int mLunarTextColor = 0xffff7200;

	public CalendarAdapter(Context context) {
		super(context);
	}

	void setStyle(int mThemeColor, int mCurColor) {
		this.mThemeColor = mThemeColor;
		this.mCurColor = mCurColor;
	}

	public void setSelectedCalendar(Calendar calendar) {
		mSelectedPosition = mItems.indexOf(calendar);
	}

	public void update(int position) {
		if (position != mSelectedPosition) {
			int p = mSelectedPosition;
			mSelectedPosition = position;
			if (p > -1) updateItem(p);
			updateItem(mSelectedPosition);
		}
	}

	private void updateItem(int position) {
		if (getItemCount() > position) {
			notifyItemChanged(position);
		}
	}

	@Override
	public RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
		return new CalenderViewHolder(mInflater.inflate(R.layout.item_list_calendar, parent, false));
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, Calendar item, int position) {
		CalenderViewHolder h = (CalenderViewHolder) holder;
//		Log.e("my", "onBindViewHolder: item = " + JsonUtil.beanToJson(item));
		h.itemView.setVisibility(item.isCurrentMonth() ? View.VISIBLE : View.GONE);
		CellView view = h.mCellView;
		view.init(item);
		if (item.isSelected()) {
			view.setTextColor(mCurColor, mLunarTextColor);
		} else {
			view.setTextColor(Color.BLACK, mLunarTextColor);
		}
	}

	private static class CalenderViewHolder extends RecyclerView.ViewHolder {
		CellView mCellView;

		CalenderViewHolder(View itemView) {
			super(itemView);
			mCellView = itemView.findViewById(R.id.cellView);
		}
	}
}
