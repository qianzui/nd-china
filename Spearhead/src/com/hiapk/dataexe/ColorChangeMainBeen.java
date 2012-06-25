package com.hiapk.dataexe;

import com.hiapk.spearhead.R.color;

import android.graphics.Color;
import android.widget.TextView;

public class ColorChangeMainBeen {
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

	public static long setRemainTraff(long mobileSet, long mobileUse) {
		long usedTraff = 0;
		if (mobileUse > mobileSet) {
			usedTraff = 0;
		} else {
			usedTraff = mobileSet - mobileUse;
		}

		return usedTraff;
	}

}
