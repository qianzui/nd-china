package com.hiapk.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadActivity;

public class ProgramNotify {
	// ϵͳ����
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	String textUp = "11";
	String textDown = "22";
	// Notification��ʾID
	private static final int ID = 1;

	public void showNotice(Context context) {

		// ���NotificationManagerʵ��
		String service = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotification = (NotificationManager) context
				.getSystemService(service);
		// ������ʾͼ�꣬��ͼ�����״̬����ʾ
		int icon = R.drawable.ic_launcher;
		// ������ʾ��ʾ��Ϣ������ϢҲ����״̬����ʾ
		CharSequence tickerText = "�ȷ��������";
		// ��ʾʱ��
		long when = System.currentTimeMillis();
		// ʵ����Notification
		Notification notification = new Notification(icon, tickerText, when);
		RemoteViews contentView = new RemoteViews(context.getPackageName(),
				R.layout.notice);
		contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher);
		contentView.setTextViewText(R.id.textUp, textUp);
		contentView.setTextViewText(R.id.textDown, textDown);
		notification.contentView = contentView;
		notification.flags = Notification.FLAG_ONGOING_EVENT;

		// ʵ����Intent
		Intent intent = new Intent(context, SpearheadActivity.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 1);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		// �����¼���Ϣ
		notification.contentIntent = pi;
		// ϵͳ�����ж��Ƿ���Ҫ��֪ͨ
		SharedPreferences prefs_setting = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean allowNotify = prefs_setting.getBoolean(SYS_PRE_NOTIFY, true);
		// showLog(allowNotify + "");
		if (allowNotify) {
			// ����֪ͨ
			mNotification.notify(ID, notification);
		}
	}
	
	public void cancelProgramNotify(Context context){
		NotificationManager mNotification = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification.cancel(ID);
	}
}
