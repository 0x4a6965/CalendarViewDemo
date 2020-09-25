package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jie.calendarviewdemo.R;

public class HorizontalScrollViewAdapter {

	private String[] mList;
	private LayoutInflater mInflater;

	public HorizontalScrollViewAdapter(Context context, String[] list) {
		this.mList = list;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return mList.length;
	}

	public Object getItem(int position) {
		return mList[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.view_txt_item, parent, false);
			viewHolder.tvTxt = convertView.findViewById(R.id.tv_txt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvTxt.setText(mList[position]);
		return convertView;
	}

	private class ViewHolder {
		TextView tvTxt;
	}
}
