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
	 * 依据预设值启用计时器，默认总流量统计间隔30秒，uid统计间隔240分钟
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

		// showLog("总流量统计间隔" + totalrefreshtime + "  uid统计间隔" + uidrefreshtime);
	}

	/**
	 * 启动小部件计时器
	 * 
	 * @param context
	 */
	public void StartWidgetAlarm(Context context) {
		setwidgetdefaulttime(context);
		// showLog("StartWidgetAlarm="+widgetrefreshtime+"");
		WidgetAlarmStart(context, widgetrefreshtime);
		// showLog("总流量统计间隔" + totalrefreshtime + "  uid统计间隔" + uidrefreshtime);
	}

	public void StartAlarmMobile(Context context) {
		setdefaulttime(context);
		setwidgetdefaulttime(context);
		int totaltime = totalrefreshtime < widgetrefreshtime ? totalrefreshtime
				: widgetrefreshtime;
		if (SQLStatic.isTotalAlarmRecording != true) {
			TotalAlarmStart(context, totaltime);
		}
		// showLog("总流量统计间隔" + totalrefreshtime + "  uid统计间隔" + uidrefreshtime);
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
		// showLog("总流量统计间隔" + totalrefreshtime + "  uid统计间隔" + uidrefreshtime);
	}

	/**
	 * 设置数据记录间隔，单位分钟 总流量数据限制为1-60分钟，uid数据限制为3-240分钟
	 * 
	 * @param context
	 * @param totalrefreshtime
	 *            总流量数据记录间隔
	 * @param uidrefreshtime
	 *            uid数据记录间隔
	 * @return 返回true则写入成功，返回false则写入失败
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
	 * 获取刷新间隔时间
	 * 
	 * @param context
	 */
	private void setdefaulttime(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		totalrefreshtime = prefs.getInt(TOTAL_REFLASH, 30);
		uidrefreshtime = prefs.getInt(UID_REFLASH, 60);
	}

	/**
	 * 获取小部件刷新间隔时间
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
	 * 停用计时器
	 * 
	 * @param context
	 */
	public void StopAlarm(Context context) {
		TotalAlarmStop(context);
		UidAlarmStop(context);
	}

	/**
	 * 停用小部件计时器
	 * 
	 * @param context
	 */
	public void StopWidgetAlarm(Context context) {
		WidgetAlarmStop(context);
	}

	/**
	 * 启动总流量计时器
	 * 
	 * @param context
	 * @param i
	 *            i为设置计时间隔秒
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
				i * 1000, pendingIntent);
	}

	/**
	 * 启动小部件计时器
	 * 
	 * @param context
	 * @param i
	 *            i为设置计时间隔秒
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

	/**
	 * 停用widget流量计时器
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
