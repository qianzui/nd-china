package com.hiapk.provider;

import android.graphics.Color;
import android.widget.TextView;

public class ColorChangeMainBeen {
	// ����������������ʾ��������ֵʱ���
	public static int colorRed = Color.rgb(208, 31, 60);
	public static int colorBlue = Color.rgb(64, 149, 239);

	/**
	 * �������ñ���ʣ�����ֵ�Լ��������õ���ɫ
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
