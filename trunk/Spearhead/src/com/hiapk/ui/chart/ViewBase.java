/**
 * @epic bin @2010
 */
package com.hiapk.ui.chart;

import android.content.Context;
import android.view.View;

/**
 * @pro FinMobile ViewBase
 * @uri view.ViewBase.java
 * @author bin
 * @mail/qq/msn ubin@vip.qq.com
 * @version 1.0
 * @date 2010-10-25 ÏÂÎç03:56:00
 */
public class ViewBase extends View {

	public int width;
	public int height;
	
	public ViewBase(Context context) {
		super(context);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		height = View.MeasureSpec.getSize(heightMeasureSpec); 
		width = View.MeasureSpec.getSize(widthMeasureSpec); 
		setMeasuredDimension(width,height); 
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
