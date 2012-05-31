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

		int icon = R.drawable.icon_3_n; // ֪ͨͼ��

		CharSequence tickerText = "�Ѵ�������Ԥ��ֵ"; // ״̬��(Status Bar)��ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); // ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// ������ת����
		Intent intent = new Intent(context, SpearheadActivity.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// ����֪ͨ
		notification.setLatestEventInfo(context, "�ȷ��������", "�������ѳ���", pi);
		mNotificationManager.notify(DAY_WARNING_ID, notification);
	}

	public void startNotifyMonth(Context context, boolean vibrate) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		int icon = R.drawable.icon_3_n; // ֪ͨͼ��

		CharSequence tickerText = "�Ѵ�������Ԥ��ֵ"; // ״̬��(Status Bar)��ʾ��֪ͨ�ı���ʾ

		long when = System.currentTimeMillis(); // ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ

		Notification notification = new Notification(icon, tickerText, when);
		if (vibrate) {
			long[] tVibrate = { 0, 100, 200, 300 };
			notification.vibrate = tVibrate;
		}
		// ������ת����
		Intent intent = new Intent(context, SpearheadActivity.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 3);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
		// ����֪ͨ
		notification.setLatestEventInfo(context, "�ȷ��������", "�������ѳ���", pi);
		mNotificationManager.notify(MONTH_WARNING_ID, notification);
	}

	public void cancelAlertNotify(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(3);
		mNotificationManager.cancel(2);
	}
}