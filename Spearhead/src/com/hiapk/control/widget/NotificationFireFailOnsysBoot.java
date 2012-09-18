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
		int icon = R.drawable.ic_launcher; // ֪ͨͼ��

		CharSequence tickerText = context
				.getString(R.string.fire_wall_open_fail_sysboot); // ״̬��(Status
		// Bar)��ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); // ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// ������ת����
		Intent intent = new Intent(context, WindowNotifyDialog.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// ����֪ͨ
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
