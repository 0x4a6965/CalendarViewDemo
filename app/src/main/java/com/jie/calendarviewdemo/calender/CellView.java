package com.jie.calendarviewdemo.calender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jie.calendarviewdemo.R;

import androidx.annotation.Nullable;

public class CellView extends View {

	private Calendar mItem;
	private Paint mDayPaint = new Paint();
//	private Paint mMonthPaint = new Paint();
	private Paint mDotPaint = new Paint();
	private Paint mSelectedPaint = new Paint();
	private Paint mLinePaint = new Paint();
	private int mRadius;

	public CellView(Context context) {
		super(context);
	}

	public CellView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mDayPaint.setAntiAlias(true);
		mDayPaint.setColor(0xff1c1c1c);
		mDayPaint.setFakeBoldText(true);
		mDayPaint.setTextAlign(Paint.Align.CENTER);

//		mMonthPaint.setAntiAlias(true);
//		mMonthPaint.setColor(Color.BLACK);
//		mMonthPaint.setTextAlign(Paint.Align.CENTER);

		mDotPaint.setAntiAlias(true);
		mDotPaint.setColor(0xffff7200);
		mDotPaint.setTextAlign(Paint.Align.CENTER);

		mLinePaint.setAntiAlias(true);
		mLinePaint.setColor(0xffcdcdcd);//0xd4d4d4d4
		mLinePaint.setFakeBoldText(true);
		mLinePaint.setStyle(Paint.Style.FILL);

		mSelectedPaint.setAntiAlias(true);
		mSelectedPaint.setColor(0xffff7200);
		mSelectedPaint.setStyle(Paint.Style.FILL);

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CellView);
		mDayPaint.setTextSize(array.getDimensionPixelSize(R.styleable.CellView_cell_day_text_size, (int) getResources().getDimension(R.dimen.txtSize16)));
//		mMonthPaint.setTextSize(array.getDimensionPixelSize(R.styleable.CellView_cell_month_text_size, (int) getResources().getDimension(R.dimen.txtSize14)));
		mDotPaint.setTextSize(array.getDimensionPixelSize(R.styleable.CellView_cell_lunar_text_size, (int) getResources().getDimension(R.dimen.txtSize10)));
		mRadius = (int) array.getDimension(R.styleable.CellView_cell_scheme_radius, (int) getResources().getDimension(R.dimen.dimen5));
		mLinePaint.setTextSize(array.getDimensionPixelSize(R.styleable.CellView_cell_scheme_text_size, (int) getResources().getDimension(R.dimen.txtSize6)));
		array.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
			int width = getWidth();
			int height = getHeight();
			int w = (width - getPaddingLeft() - getPaddingRight());
			int h = (height - getPaddingTop() - getPaddingBottom()) / 4;
//			Calendar calendar = getCurrentCalendar();
//			if (mItem.getHead()) {
//				if (mItem.getYear() == calendar.getYear() && mItem.getMonth() == calendar.getMonth()) {
//					mMonthPaint.setColor(0xffff7200);//0xffff7200
//				} else {
//					mMonthPaint.setColor(0xff1c1c1c);
//				}
//				canvas.drawText(getMonthString(mItem.getMonth(),getContext()), w / 2, 4 * h + getPaddingTop(), mMonthPaint);
//			} else {
				canvas.drawLine(0, 0, width, 0, mLinePaint);
				if (mItem.isSelected()) {
					canvas.drawCircle(w / 2, 2 * h + getPaddingTop()/2 , Math.min(2*height / 7, 2*width / 7), mSelectedPaint);
				}

				if (mItem.getDay() > 0) {
					canvas.drawText(String.valueOf(mItem.getDay()), w / 2, 2 * h + getPaddingTop(), mDayPaint);
				}

				if (mItem.getSign()) {
					canvas.drawCircle(w / 2, 4 * h + getPaddingTop(), mRadius / 2, mDotPaint);
				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private Calendar getCurrentCalendar() {
//		Calendar calendar = new Calendar();
//		Date d = new Date();
//		calendar.setYear(Util.getDate("yyyy", d));
//		calendar.setMonth(Util.getDate("MM", d));
//		calendar.setDay(Util.getDate("dd", d));
//		return calendar;
//	}

	public void init(Calendar item) {
		this.mItem = item;
	}

	public void setTextColor(int textColor, int lunarTextColor) {
		mDayPaint.setColor(textColor);
		mDotPaint.setColor(lunarTextColor);
	}

	private String getMonthString(int month, Context context) {
		String str = "";
		switch (month) {
			case 1:
				str = context.getResources().getString(R.string.jan);
				break;
			case 2:
				str = context.getResources().getString(R.string.feb);
				break;
			case 3:
				str = context.getResources().getString(R.string.mar);
				break;
			case 4:
				str = context.getResources().getString(R.string.apr);
				break;
			case 5:
				str = context.getResources().getString(R.string.may_1);
				break;
			case 6:
				str = context.getResources().getString(R.string.jun);
				break;
			case 7:
				str = context.getResources().getString(R.string.jul);
				break;
			case 8:
				str = context.getResources().getString(R.string.aug);
				break;
			case 9:
				str = context.getResources().getString(R.string.sep);
				break;
			case 10:
				str = context.getResources().getString(R.string.oct);
				break;
			case 11:
				str = context.getResources().getString(R.string.nov);
				break;
			case 12:
				str = context.getResources().getString(R.string.dec);
				break;
		}
		return str;
	}
}