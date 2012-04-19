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
	 * 依据预设值启用计时器，默认总流量统计间隔1分钟，uid统计间隔5分钟
	 * 
	 * @param context
	 */
	public void StartAlarm(Context context) {
		setdefaulttime(context);
		TotalAlarmStart(context, totalrefreshtime);
		UidAlarmStart(context, uidrefreshtime);
		showLog("总流量统计间隔" + totalrefreshtime + "  uid统计间隔" + uidrefreshtime);
	}

	/**
	 * 设置数据记录间隔，单位分钟
	 * 总流量数据限制为1-60分钟，uid数据限制为3-240分钟
	 * @param context
	 * @param totalrefreshtime
	 *            总流量数据记录间隔
	 * @param uidrefreshtime
	 *            uid数据记录间隔
	 * @return
	 * 返回true则写入成功，返回false则写入失败
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
	 * 获取刷新间隔时间
	 * 
	 * @param context
	 */
	private void setdefaulttime(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		totalrefreshtime = prefs.getInt(TOTAL_REFLASH, 1);
		uidrefreshtime = prefs.getInt(UID_REFLASH, 5);
	}

	// /**
	// * 启用计时器，指定时间间隔 单位分钟
	// *
	// * @param context
	// * @param totalrefreshtime
	// * 总流量统计间隔
	// * @param uidrefreshtime
	// * uid统计间隔
	// */
	// public void StartAlarm(Context context, int totalrefreshtime,
	// int uidrefreshtime) {
	// TotalAlarmStart(context, totalrefreshtime);
	// UidAlarmStart(context, uidrefreshtime);
	// showLog("总流量统计间隔" + totalrefreshtime + "  uid统计间隔" + uidrefreshtime);
	// }

	/**
	 * 停用计时器
	 * 
	 * @param context
	 */
	public void StopAlarm(Context context) {
		TotalAlarmStop(context);
		UidAlarmStop(context);
	}

	/**
	 * 启动总流量计时器
	 * 
	 * @param context
	 * @param i
	 *            i为设置计时间隔分钟数
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
	 * 启动uid流量计时器
	 * 
	 * @param context
	 * @param i
	 *            i为设置计时间隔分钟数
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
	 * 停用总流量计时器
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
	 * 停用uid流量计时器
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
