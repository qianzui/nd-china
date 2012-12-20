/*
 * Copyright (C) 2011 Patrik Åkerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiapk.firewall.viewpager;

import com.hiapk.logs.Logs;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.ui.skin.SkinCustomMains;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * A TitleFlowIndicator is a FlowIndicator which displays the title of left view
 * (if exist), the title of the current select view (centered) and the title of
 * the right view (if exist). When the user scrolls the ViewFlow then titles are
 * also scrolled.
 * 
 */
public class FlowIndicator extends TextView {

	private static final int SELECTED_COLOR = 0xFFFFC445;
	private static final int TEXT_COLOR = 0xFFAAAAAA;
	private static final int TEXT_SIZE = 15;
	private static final int FOOTER_LINE_HEIGHT = 4;
	private static final int FOOTER_COLOR = 0xFFFFC445;
	private static final int FOOTER_TRIANGLE_HEIGHT = 10;
	private int currentScroll = 0;
	private Paint paintSelected;
	private int footerTriangleHeight;
	private int footerLineHeight;
	private String TAG = "FlowIndicator";
	private int size = 2;
	// barColor
	private Bitmap bitmp_bar = BitmapFactory.decodeResource(getResources(),
			SkinCustomMains.flowIndicatorBackground(SpearheadApplication
					.getInstance().getSkinType()));

	public void setSize(int size) {
		this.size = size;
	}

	private int flowHeight = 0;

	/**
	 * Default constructor
	 */
	public FlowIndicator(Context context) {
		super(context);
		initDraw(TEXT_COLOR, TEXT_SIZE, SELECTED_COLOR, FOOTER_LINE_HEIGHT,
				FOOTER_COLOR);
	}

	/**
	 * The contructor used with an inflater
	 * 
	 * @param context
	 * @param attrs
	 */
	public FlowIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Retrieve styles attributs
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.TitleFlowIndicator);
		// Retrieve the colors to be used for this view and apply them.
		int footerColor = a.getColor(
				R.styleable.TitleFlowIndicator_footerColor, FOOTER_COLOR);
		footerLineHeight = a.getInt(
				R.styleable.TitleFlowIndicator_footerLineHeight,
				FOOTER_LINE_HEIGHT);
		footerTriangleHeight = a.getInt(
				R.styleable.TitleFlowIndicator_footerTriangleHeight,
				FOOTER_TRIANGLE_HEIGHT);
		int selectedColor = a.getColor(
				R.styleable.TitleFlowIndicator_selectedColor, SELECTED_COLOR);
		int textColor = a.getColor(R.styleable.TitleFlowIndicator_textColor,
				TEXT_COLOR);
		float textSize = a.getFloat(R.styleable.TitleFlowIndicator_textSize,
				TEXT_SIZE);
		initDraw(textColor, textSize, selectedColor, footerLineHeight,
				footerColor);
	}

	/**
	 * …Ë÷√øÌ∂»
	 * 
	 * @param flowHeight
	 */
	public void setFlowHeight(int flowHeight) {
		this.flowHeight = flowHeight;
	}

	/**
	 * Initialize draw objects
	 */
	private void initDraw(int textColor, float textSize, int selectedColor,
			int footerLineHeight, int footerColor) {
		paintSelected = new Paint();
		paintSelected.setColor(selectedColor);
		paintSelected.setTextSize(textSize);
		paintSelected.setAntiAlias(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Calculate views bounds
		Rect bound = calculateAllBounds(paintSelected);

		// Verify if the current view must be clipped to the screen
		// int curViewWidth = bound.right - bound.left;
		// if (bound.left < 0) {
		// // Try to clip to the screen (left side)
		// bound.left = 0;
		// bound.right = curViewWidth;
		// }
		// if (bound.right > getLeft() + getWidth()) {
		// // Try to clip to the screen (right side)
		// bound.right = getLeft() + getWidth();
		// bound.left = bound.right - curViewWidth;
		// }
		// showLog("bound.left=" + bound.left);
		// showLog("bound.right=" + bound.right);
		// showLog("bound.top=" + bound.top);
		// showLog("bound.bottom=" + bound.bottom);
		// Now draw views
		// Only if one side is visible
		// Paint paint = paintText;
		// Change the color is the title is closed to the center
		// int middle = (bound.left + bound.right) / 2;
		// if (bound.left > getWidth() / 3 * 2) {
		// Paint paint = paintSelected;
		// canvas.drawRect(bound.left, bound.top, getWidth(), bound.bottom,
		// paint);
		// canvas.drawRect(0, bound.top, getWidth() / size - getWidth()
		// + bound.left, bound.bottom, paint);
		// } else {
		Paint paint = paintSelected;
		// canvas.drawRect(bound.left, bound.top, bound.right, bound.bottom,
		// paint);
		// Logs.d(TAG, colorType + "");
		// if (colorType != colorBeforeType) {
		// bitmp_bar = BitmapFactory.decodeResource(getResources(),
		// SkinCustomMains.flowIndicatorBackground(colorType));
		// colorBeforeType = colorType;
		// }
		// bitmp_bar = BitmapFactory.decodeResource(getResources(),
		// SkinCustomMains.flowIndicatorBackground());
		canvas.drawBitmap(bitmp_bar, null, bound, paint);
		// }

	}

	/**
	 * Calculate views bounds and scroll them according to the current index
	 * 
	 * @param paint
	 * @param currentIndex
	 * @return
	 */
	private Rect calculateAllBounds(Paint paint) {
		// For each views (If no values then add a fake one)
		Rect bounds = new Rect();
		showLog("getWidth()=" + getWidth());
		// showLog("paint.descent()=" + paint.descent());
		// showLog("paint.ascent()=" + paint.ascent());
		bounds.bottom = (int) (paint.descent() - paint.ascent());
		int w = (int) getWidth() / size;
		int h = 0;
		if (flowHeight == 0) {
			h = getHeight();
		} else {
			h = flowHeight;
		}
		showLog("h =" + h);
		showLog("getHeight =" + getHeight());
		showLog("getMeasuredHeight =" + getMeasuredHeight());
		showLog("currentScroll=" + currentScroll);
		bounds.left = currentScroll % (getWidth() * size) / size;
		bounds.right = bounds.left + w;
		bounds.top = 0 + 1;
		bounds.bottom = h - 2;

		return bounds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.taptwo.android.widget.FlowIndicator#onScrolled(int, int, int,
	 * int)
	 */
	public void onScrolled(int ScrollX) {
		currentScroll = ScrollX;
		invalidate();
	}

	public void onStateChange(int newState) {
		if (newState == 0) {
			bitmp_bar = BitmapFactory.decodeResource(getResources(),
					SkinCustomMains
							.flowIndicatorBackground(SpearheadApplication
									.getInstance().getSkinType()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.taptwo.android.widget.ViewFlow.ViewSwitchListener#onSwitched(android
	 * .view.View, int)
	 */
	public void onSwitched(View view, int position) {
		invalidate();
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// * org.taptwo.android.widget.FlowIndicator#setViewFlow(org.taptwo.android
	// * .widget.ViewFlow)
	// */
	// @Override
	// public void setViewFlow(ViewFlow view) {
	// invalidate();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ViewFlow can only be used in EXACTLY mode.");
		}
		result = specSize;
		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// We were told how big to be
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		// Measure the height
		else {
			// Calculate the text bounds
			Rect bounds = new Rect();
			bounds.bottom = (int) (paintSelected.descent() - paintSelected
					.ascent());
			result = bounds.bottom - bounds.top + footerTriangleHeight
					+ footerLineHeight + 10;
			return result;
		}
		return result;
	}

	private void showLog(String str) {
		Logs.d(TAG, str);
	}
}
