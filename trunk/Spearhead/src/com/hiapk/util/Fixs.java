package com.hiapk.util;

import com.hiapk.logs.Logs;

import android.content.Context;

public class Fixs {
	private String TAG = "Fixs";

	public void update121(Context context, long[] mobile_month_data) {
		SharedPrefrenceDataOnUpdate sharedUpdate = new SharedPrefrenceDataOnUpdate(
				context);
		Logs.d(TAG, "fixstart,data=" + mobile_month_data[1]
				+ mobile_month_data[32]);
		if ((mobile_month_data[1] + mobile_month_data[32]) != 0) {
			boolean isupdated = sharedUpdate.isTotal121updated();
			Logs.d(TAG, "isupdated=" + isupdated);
			if (isupdated == false) {
				SharedPrefrenceData sharedpref = new SharedPrefrenceData(
						context);
				sharedpref.setMonthHasUsedStack(mobile_month_data[0]
						+ mobile_month_data[63]);
				Logs.d(TAG, "data=" + mobile_month_data[0]
						+ mobile_month_data[63]);
				sharedUpdate.setTotal121updated(true);
			}
		}
	}
}