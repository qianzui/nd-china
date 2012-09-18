package com.hiapk.control.widget;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.Splash;
import com.hiapk.spearhead.WindowNotifyDialog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NotificationFireFailOnsysBoot {
	private static final int FIREWALL_OPEN_FAIL_ID = 5;
	private NotificationManager mNotificationManager;

	public NotificationFireFailOnsysBoot(Context context) {
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void startNotifyDay(Context context, boolean vibrate) {
		int icon = R.drawable.ic_launcher; // 通知图标

		CharSequence tickerText = context
				.getString(R.string.fire_wall_open_fail_sysboot); // 状态栏(Status
		// Bar)显示的通知文本提示

		long when = System.currentTimeMillis(); // 通知产生的时间，会在通知信息里显示

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// 设置跳转界面
		Intent intent = new Intent(context, WindowNotifyDialog.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// 获得PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// 发出通知
		notification
				.setLatestEventInfo(context, context
						.getString(R.string.spearheadnotify), context
						.getString(R.string.fire_wall_open_fail_sysboot_set),
						pi);
		mNotificationManager.notify(FIREWALL_OPEN_FAIL_ID, notification);
	}

	public void cancelAlertNotify(Context context) {
		mNotificationManager.cancel(FIREWALL_OPEN_FAIL_ID);
	}
}
