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
			Intent updateTimeIntent = new Intent(context,
					DateChangeBroadcast.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, updateTimeIntent, 0);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,
					1000 * 60 * 60 * 24, pendingIntent);
		}
	}

	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// // Log.d("DataChangeBroad", string);
	// }
}
