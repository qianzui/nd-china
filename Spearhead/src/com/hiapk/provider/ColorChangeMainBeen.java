package com.hiapk.provider;

import com.hiapk.spearhead.R.color;

import android.graphics.Color;
import android.widget.TextView;

public class ColorChangeMainBeen {
	// 用于主界面流量显示超过特殊值时变红
	public static int colorRed = Color.rgb(208, 31, 60);
	public static int colorBlue = Color.rgb(64, 149, 239);

	/**
	 * 用于设置本月剩余的数值以及本月已用的颜色
	 * 
	 * @param mobileSet
	 * @param mobileUse
	 * @param tvUsed
	 * @return
	 */
	public static long setRemainTraff(long mobileSet, long mobileUse,
			TextView tvUsed) {
		long usedTraff = 0;
		if (mobileUse > mobileSet) {
			tvUsed.setTextColor(Color.rgb(208, 31, 60));
			usedTraff = 0;
		} else {
			tvUsed.setTextColor(Color.rgb(64, 149, 239));
			usedTraff = mobileSet - mobileUse;
		}

		return usedTraff;
	}


}
