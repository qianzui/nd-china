package com.hiapk.broadcreceiver;

import java.util.Calendar;

import com.hiapk.logs.Logs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeChangeToStartDateChange extends BroadcastReceiver {
	private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
	private String TAG = "TimeChange";

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Logs.d(TAG, action);
		if (ACTION_TIME_CHANGED.equals(action)) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.setTimeInMillis(c.getTimeInMillis() + 1000 * 60 * 60 * 24 - 1500);
			long triggerTime = c.getTimeInMillis();
			AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			// before
			Intent beforeIntent = new Intent(context,
					DateChangeBroadcastBefore.class);
			PendingIntent beforependingIntent = PendingIntent.getBroadcast(
					context, 0, beforeIntent, 0);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,
					1000 * 60 * 60 * 24, beforependingIntent);
			// after
			Intent afterIntent = new Intent(context,
					DateChangeBroadcastAfter.class);
			PendingIntent afterpendingIntent = PendingIntent.getBroadcast(
					context, 0, afterIntent, 0);
			alarmManager
					.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime + 1800,
							1000 * 60 * 60 * 24, afterpendingIntent);
		}
	}

	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// // Log.d("DataChangeBroad", string);
	// }
}
