package com.hiapk.broadcreceiver;

import com.hiapk.logs.Logs;
import com.hiapk.util.ResetDataToZero;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DateChangeBroadcastAfter extends BroadcastReceiver {
	private String TAG = "DateChangeAft";

	@Override
	public void onReceive(Context context, Intent intent) {
		Logs.d(TAG, "afterDate");
		resetRecordData(context);
	}

	/**
	 * 清零月与日流量统计数据
	 * 
	 * @param context
	 */
	private void resetRecordData(Context context) {
		ResetDataToZero.resetData(context);

		// Logs.d(TAG, "SetText.textUpaf=" + SetText.textUp);
	}

}
