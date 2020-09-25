package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

	LayoutInflater mInflater;
	List<T> mItems;
	private OnItemClickListener onItemClickListener;
	private OnClickListener onClickListener;

	public BaseRecyclerAdapter(Context context) {
		this.mItems = new ArrayList<>();
		mInflater = LayoutInflater.from(context);
		onClickListener = new OnClickListener() {
			@Override
			public void onClick(int position, long itemId) {
				if (onItemClickListener != null) onItemClickListener.onItemClick(position, itemId);
			}
		};
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
		if (holder != null) {
			holder.itemView.setTag(holder);
			holder.itemView.setOnClickListener(onClickListener);
		}
		return holder;
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		onBindViewHolder(holder, mItems.get(position), position);
	}

	public abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

	public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public void addAll(List<T> items) {
		if (items != null && items.size() > 0) {
			mItems.addAll(items);
			notifyDataSetChanged();
		}
	}

	public final void addItem(T item) {
		if (item != null) {
			this.mItems.add(item);
			notifyItemChanged(mItems.size());
		}
	}

	public final List<T> getItems() {
		return mItems;
	}


	public final T getItem(int position) {
		if (position < 0 || position >= mItems.size()) return null;
		return mItems.get(position);
	}

	public static abstract class OnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
			onClick(holder.getAdapterPosition(), holder.getItemId());
		}

		public abstract void onClick(int position, long itemId);
	}

	public interface OnItemClickListener {
		void onItemClick(int position, long itemId);
	}
}
