package com.hiapk.control.widget;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.Splash;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NotificationWarningControl {
	private static final int MONTH_WARNING_ID = 2;
	private static final int DAY_WARNING_ID = 3;
	private NotificationManager mNotificationManager;

	public NotificationWarningControl(Context context) {
		mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void startNotifyDay(Context context, boolean vibrate) {
		int icon = R.drawable.icon_3_n; // ֪ͨͼ��

		CharSequence tickerText = context.getString(R.string.reach_day_alert); // ״̬��(Status
																				// Bar)��ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); // ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// ������ת����
		Intent intent = new Intent(context, Splash.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// ����֪ͨ
		notification.setLatestEventInfo(context,
				context.getString(R.string.spearheadnotify),
				context.getString(R.string.day_traff_over_set), pi);
		mNotificationManager.notify(DAY_WARNING_ID, notification);
	}

	public void startNotifyMonth(Context context, boolean vibrate) {
		int icon = R.drawable.icon_3_n; // ֪ͨͼ��

		CharSequence tickerText = context.getString(R.string.reach_month_alert); // ״̬��(Status
																					// Bar)��ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); // ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// ������ת����
		Intent intent = new Intent(context, Splash.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// ����֪ͨ
		notification.setLatestEventInfo(context,
				context.getString(R.string.spearheadnotify),
				context.getString(R.string.month_traff_over_set), pi);
		mNotificationManager.notify(MONTH_WARNING_ID, notification);
	}

	public void cancelAlertNotify(Context context) {
		mNotificationManager.cancel(DAY_WARNING_ID);
		mNotificationManager.cancel(MONTH_WARNING_ID);
	}
}
