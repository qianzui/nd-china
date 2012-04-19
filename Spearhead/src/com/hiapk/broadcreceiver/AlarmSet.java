package com.hiapk.broadcreceiver;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class AlarmSet {
	private int totalrefreshtime;
	private int uidrefreshtime;
	private final String PREFS_NAME = "allprefs";
	private final String TOTAL_REFLASH = "totalrefreshtime";
	private final String UID_REFLASH = "uidrefreshtime";

	/**
	 * ����Ԥ��ֵ���ü�ʱ����Ĭ��������ͳ�Ƽ��1���ӣ�uidͳ�Ƽ��5����
	 * 
	 * @param context
	 */
	public void StartAlarm(Context context) {
		setdefaulttime(context);
		TotalAlarmStart(context, totalrefreshtime);
		UidAlarmStart(context, uidrefreshtime);
		showLog("������ͳ�Ƽ��" + totalrefreshtime + "  uidͳ�Ƽ��" + uidrefreshtime);
	}

	/**
	 * �������ݼ�¼�������λ����
	 * ��������������Ϊ1-60���ӣ�uid��������Ϊ3-240����
	 * @param context
	 * @param totalrefreshtime
	 *            ���������ݼ�¼���
	 * @param uidrefreshtime
	 *            uid���ݼ�¼���
	 * @return
	 * ����true��д��ɹ�������false��д��ʧ��
	 */
	public boolean SetAlarm(Context context, int totalrefreshtime,
			int uidrefreshtime) {
		Editor passfileEditor = context.getSharedPreferences(PREFS_NAME, 0)
				.edit();
		if (0<totalrefreshtime&&totalrefreshtime<61&&2<uidrefreshtime&&uidrefreshtime<241) {
			passfileEditor.putInt(TOTAL_REFLASH, totalrefreshtime);
			passfileEditor.putInt(UID_REFLASH, uidrefreshtime);
			passfileEditor.commit();
			return true;
		}
		return false;
	}

	/**
	 * ��ȡˢ�¼��ʱ��
	 * 
	 * @param context
	 */
	private void setdefaulttime(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		totalrefreshtime = prefs.getInt(TOTAL_REFLASH, 1);
		uidrefreshtime = prefs.getInt(UID_REFLASH, 5);
	}

	// /**
	// * ���ü�ʱ����ָ��ʱ���� ��λ����
	// *
	// * @param context
	// * @param totalrefreshtime
	// * ������ͳ�Ƽ��
	// * @param uidrefreshtime
	// * uidͳ�Ƽ��
	// */
	// public void StartAlarm(Context context, int totalrefreshtime,
	// int uidrefreshtime) {
	// TotalAlarmStart(context, totalrefreshtime);
	// UidAlarmStart(context, uidrefreshtime);
	// showLog("������ͳ�Ƽ��" + totalrefreshtime + "  uidͳ�Ƽ��" + uidrefreshtime);
	// }

	/**
	 * ͣ�ü�ʱ��
	 * 
	 * @param context
	 */
	public void StopAlarm(Context context) {
		TotalAlarmStop(context);
		UidAlarmStop(context);
	}

	/**
	 * ������������ʱ��
	 * 
	 * @param context
	 * @param i
	 *            iΪ���ü�ʱ���������
	 */
	private void TotalAlarmStart(Context context, int i) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, RecordDataReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
				i * 60000, pendingIntent);
	}

	/**
	 * ����uid������ʱ��
	 * 
	 * @param context
	 * @param i
	 *            iΪ���ü�ʱ���������
	 */
	private void UidAlarmStart(Context context, int i) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, RecordUidDataReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
				i * 60000, pendingIntent);
	}

	/**
	 * ͣ����������ʱ��
	 * 
	 * @param context
	 */
	private void TotalAlarmStop(Context context) {
		Intent intent = new Intent(context, RecordDataReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		alarmManager.cancel(pendingIntent);
	}

	/**
	 * ͣ��uid������ʱ��
	 * 
	 * @param context
	 */
	private void UidAlarmStop(Context context) {
		Intent intent = new Intent(context, RecordUidDataReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		alarmManager.cancel(pendingIntent);
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}

}
