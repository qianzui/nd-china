package com.hiapk.control.bootandclose;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.hiapk.broadcreceiver.ExitAppBroadcast;
import com.hiapk.spearhead.Mapplication;
import com.hiapk.ui.widget.SetText;
import com.hiapk.util.SharedPrefrenceDataWidget;

public class OnExit {

	public void onExit(Context context) {
		SharedPrefrenceDataWidget sharedWidget = new SharedPrefrenceDataWidget(
				context);
		if (sharedWidget.isFloatOpen()) {
			if (SetText.FloatIntX != 50) {
				sharedWidget.setIntX(SetText.FloatIntX);
			}
			if (SetText.FloatIntY != 50) {
				sharedWidget.setIntY(SetText.FloatIntY);
			}

		}
		Intent intent = new Intent(context, ExitAppBroadcast.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		alarmManager.setRepeating(AlarmManager.RTC,
				calendar.getTimeInMillis() + 500, 356 * 24 * 60 * 60 * 1000,
				pendingIntent);
		Mapplication.getInstance().exit();
	}

}
