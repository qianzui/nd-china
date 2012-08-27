package com.hiapk.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.R.color;
import com.hiapk.spearhead.Splash;

/**
 * �����֪ͨ��
 * 
 * @author Administrator
 * 
 */
public class ProgramNotify {
	// ϵͳ����
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	String textUp = "11";
	String textDown = "22";
	// Notification��ʾID
	private static final int ID = 5;

	public void showNotice(Context context, int percent) {

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
		contentView.setImageViewResource(R.id.image, R.drawable.monitor);
		// setText(context);
		// textUp = SetText.textUp;
		// textDown = SetText.textDown;
		// SetText.setText(context);
		contentView.setTextViewText(R.id.textUp, SetText.textUp);
		contentView.setTextViewText(R.id.textDown, SetText.textDown);
		if (SetText.HasSetMonthUsed == false) {
			contentView.setInt(R.id.probar_down, "setProgress", 0);
			contentView.setInt(R.id.probar_down, "setSecondaryProgress", 0);
		} else {
			if (percent > 90) {
				contentView.setInt(R.id.probar_down, "setProgress", 0);
				contentView.setInt(R.id.probar_down, "setSecondaryProgress",
						percent);
			} else {
				contentView.setInt(R.id.probar_down, "setProgress", percent);
			}
		}
		ProgressBar aa = new ProgressBar(context);
		// aa.setSecondaryProgress(10);
		notification.contentView = contentView;
		notification.flags = Notification.FLAG_ONGOING_EVENT;

		// ʵ����Intent
		Intent intent = new Intent(context, Splash.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 1);
		intent.putExtras(choosetab);
		// ���PendingIntent
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		// �����¼���Ϣ
		notification.contentIntent = pi;
		// ����֪ͨ
		mNotification.notify(ID, notification);
	}

	// private void setText(Context context) {
	// // TODO Auto-generated method stub
	// TrafficManager trafficManager = new TrafficManager();
	// trafficManager.statsTotalTraffic(context, false);
	// Time t = new Time();
	// t.setToNow();
	// int year = t.year;
	// int month = t.month + 1;
	// int monthDay = t.monthDay;
	// UnitHandler unitHandler = new UnitHandler();
	// SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
	// long[] monthUsed_this = trafficManager.getMobileMonthTraffic(context);
	// long monthSetLong = sharedData.getMonthMobileSetOfLong();
	// long monthUsedLong = trafficManager.getMonthUseData(context);
	// long todayUsedLong = monthUsed_this[monthDay]
	// + monthUsed_this[monthDay + 31];
	// String monthUsedStr = unitHandler.unitHandlerAccurate(monthUsedLong);
	// String monthSetStr = unitHandler.unitHandlerAccurate(monthSetLong);
	// String todayUsedStr = unitHandler.unitHandlerAccurate(todayUsedLong);
	//
	// // textUp = "�������ã�xxx kB(MB)";
	// // textDown = "xx MB / 50 MB --> 2012.06.01";
	// textUp = "�������ã�" + todayUsedStr + (int) (30 * Math.random());
	// textDown = monthUsedStr + " / " + monthSetStr + " --> " + year + "."
	// + month + "." + monthDay;
	// }

	public void cancelProgramNotify(Context context) {
		NotificationManager mNotification = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotification.cancel(ID);
	}
}
