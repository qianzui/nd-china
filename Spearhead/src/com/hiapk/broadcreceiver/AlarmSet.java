package com.hiapk.broadcreceiver;

import java.util.Calendar;

import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.widget.ProgramNotify;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class AlarmSet {
	private int totalrefreshtime = 30;
	private int uidrefreshtime = 30;
	private int widgetrefreshtime = 30;
	private final String PREFS_NAME = "allprefs";
	private final String TOTAL_REFLASH = "totalrefreshtime";
	private final String UID_REFLASH = "uidrefreshtime";

	/**
	 * ����Ԥ��ֵ���ü�ʱ����Ĭ��������ͳ�Ƽ��30�룬uidͳ�Ƽ��240����
	 * 
	 * @param context
	 */
	public void StartAlarm(Context context) {
		setdefaulttime(context);
		setwidgetdefaulttime(context);
		int totaltime = totalrefreshtime < widgetrefreshtime ? totalrefreshtime
				: widgetrefreshtime;
		int uidtime = uidrefreshtime;
		// < widgetrefreshtime ? uidrefreshtime
		// : widgetrefreshtime
		showLog("isTotalAlarmRecording=" + SQLStatic.isTotalAlarmRecording
				+ "isUidAlarmRecording" + SQLStatic.isUidAlarmRecording);
		// showLog("StartAlarm="+totaltime+"");
		if (SQLStatic.isTotalAlarmRecording != true) {
			TotalAlarmStart(context, totaltime);
		}
		if (SQLStatic.isUidAlarmRecording != true) {
			UidAlarmStart(context, uidtime);
		}
		// if (SQLStatic.isUidTotalAlarmRecording != true) {
		// UidTotalAlarmStart(context, uidrefreshtime);
		// }

		// showLog("������ͳ�Ƽ��" + totalrefreshtime + "  uidͳ�Ƽ��" + uidrefreshtime);
	}

	/**
	 * ����С������ʱ��
	 * 
	 * @param context
	 */
	public void StartWidgetAlarm(Context context) {
		setwidgetdefaulttime(context);
		// showLog("StartWidgetAlarm="+widgetrefreshtime+"");
		WidgetAlarmStart(context, widgetrefreshtime);
		// showLog("������ͳ�Ƽ��" + totalrefreshtime + "  uidͳ�Ƽ��" + uidrefreshtime);
	}

	public void StartAlarmMobile(Context context) {
		setdefaulttime(context);
		setwidgetdefaulttime(context);
		int totaltime = totalrefreshtime < widgetrefreshtime ? totalrefreshtime
				: widgetrefreshtime;
		if (SQLStatic.isTotalAlarmRecording != true) {
			TotalAlarmStart(context, totaltime);
		}
		// showLog("������ͳ�Ƽ��" + totalrefreshtime + "  uidͳ�Ƽ��" + uidrefreshtime);
	}

	public void StartAlarmUid(Context context) {
		setdefaulttime(context);
		// setwidgetdefaulttime(context);
		int uidtime = uidrefreshtime
		// < widgetrefreshtime ? uidrefreshtime
		// : widgetrefreshtime
		;
		if (SQLStatic.isUidAlarmRecording != true) {
			UidAlarmStart(context, uidtime);
		}
		// showLog("������ͳ�Ƽ��" + totalrefreshtime + "  uidͳ�Ƽ��" + uidrefreshtime);
	}

	/**
	 * �������ݼ�¼�������λ���� ��������������Ϊ1-60���ӣ�uid��������Ϊ3-240����
	 * 
	 * @param context
	 * @param totalrefreshtime
	 *            ���������ݼ�¼���
	 * @param uidrefreshtime
	 *            uid���ݼ�¼���
	 * @return ����true��д��ɹ�������false��д��ʧ��
	 */
	public boolean SetAlarm(Context context, int totalrefreshtime,
			int uidrefreshtime) {
		Editor passfileEditor = context.getSharedPreferences(PREFS_NAME, 0)
				.edit();
		if (0 < totalrefreshtime && totalrefreshtime < 61 && 2 < uidrefreshtime
				&& uidrefreshtime < 241) {
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
		totalrefreshtime = prefs.getInt(TOTAL_REFLASH, 30);
		uidrefreshtime = prefs.getInt(UID_REFLASH, 60);
	}

	/**
	 * ��ȡС����ˢ�¼��ʱ��
	 * 
	 * @param context
	 */
	private void setwidgetdefaulttime(Context context) {
		SharedPrefrenceDataWidget sharedata = new SharedPrefrenceDataWidget(
				context);
		int set = sharedata.getWidgetFresh();
		showLog(set + "");
		switch (set) {
		case 0:
			widgetrefreshtime = 5;
			break;
		case 1:
			widgetrefreshtime = 15;
			break;
		case 2:
			widgetrefreshtime = 31;
			break;
		case 3:
			widgetrefreshtime = 60;
			break;
		case 4:
			widgetrefreshtime = 120;
			break;
		case 5:
			widgetrefreshtime = 300;
			break;

		default:
			widgetrefreshtime = 31;
			break;
		}
	}

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
	 * ͣ��С������ʱ��
	 * 
	 * @param context
	 */
	public void StopWidgetAlarm(Context context) {
		WidgetAlarmStop(context);
	}

	/**
	 * ������������ʱ��
	 * 
	 * @param context
	 * @param i
	 *            iΪ���ü�ʱ�����
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
				i * 1000, pendingIntent);
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
				i * 1000, pendingIntent);
	}

	/**
	 * ����С������ʱ��
	 * 
	 * @param context
	 * @param i
	 *            iΪ���ü�ʱ�����
	 */
	private void WidgetAlarmStart(Context context, int i) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, UpdateWidget.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
				i * 1000, pendingIntent);
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

	/**
	 * ͣ��widget������ʱ��
	 * 
	 * @param context
	 */
	private void WidgetAlarmStop(Context context) {
		Intent intent = new Intent(context, UpdateWidget.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService("alarm");
		alarmManager.cancel(pendingIntent);
		ProgramNotify programNotify = new ProgramNotify();
		programNotify.cancelProgramNotify(context);
	}

	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("AlarmSet", string);
		}

	}

}
