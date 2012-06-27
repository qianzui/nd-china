package com.hiapk.alertaction;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.spearhead.Splash;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlertActionNotify {
	private static final int MONTH_WARNING_ID = 2;
	private static final int DAY_WARNING_ID = 3;

	public void startNotifyDay(Context context, boolean vibrate) {
		NotificationManager mNotificationManager;
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		int icon = R.drawable.icon_3_n; // 通知图标

		CharSequence tickerText = "已达日流量预警值"; // 状态栏(Status Bar)显示的通知文本提示

		long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// 设置跳转界面
		Intent intent = new Intent(context, Splash.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// 获得PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// 发出通知
		notification.setLatestEventInfo(context, "先锋流量监控", "日流量已超标", pi);
		mNotificationManager.notify(DAY_WARNING_ID, notification);
	}

	public void startNotifyMonth(Context context, boolean vibrate) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		int icon = R.drawable.icon_3_n; // 通知图标

		CharSequence tickerText = "已达月流量预警值"; // 状态栏(Status Bar)显示的通知文本提示

		long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// 设置跳转界面
		Intent intent = new Intent(context, Splash.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// 获得PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// 发出通知
		notification.setLatestEventInfo(context, "先锋流量监控", "月流量已超标", pi);
		mNotificationManager.notify(MONTH_WARNING_ID, notification);
	}

	public void cancelAlertNotify(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(3);
		mNotificationManager.cancel(2);
	}
}
