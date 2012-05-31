package com.hiapk.widget;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.SharedPrefrenceData;

public class SetText {
	public static String textUp;
	public static String textDown;
	public static String text1;
	public static String text2;
	public static String text3;

	/**
	 * 获取通知栏与小部件显示文字
	 * 
	 * @param context
	 * @param i
	 *            i=0返回通知栏上，1返回通知栏下，2-4返回小部件上中下。
	 * @return
	 */
	public static void setText(Context context) {
		// TODO Auto-generated method stub
		TrafficManager trafficManager = new TrafficManager();
		// 记录数据命令
		// trafficManager.statsTotalTraffic(context, false);
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int monthDay = t.monthDay;
		UnitHandler unitHandler = new UnitHandler();
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		long[] monthUsed_this = new long[64];
		monthUsed_this = TrafficManager.mobile_month_data;
		long monthSetLong = sharedData.getMonthMobileSetOfLong();
		long monthUsedLong = trafficManager.getMonthUseData(context);
		// trafficManager.setMonthUseDate(monthUsedLong);
		long todayUsedLong = monthUsed_this[monthDay]
				+ monthUsed_this[monthDay + 31];
		String monthUsedStr = unitHandler.unitHandlerAccurate(monthUsedLong);
		String monthSetStr = unitHandler.unitHandlerAccurate(monthSetLong);
		String todayUsedStr = unitHandler.unitHandlerAccurate(todayUsedLong);
		String date = getdate(context, year, month, monthDay);
		// textUp = "今日已用：xxx kB(MB)";
		// textDown = "xx MB / 50 MB --> 2012.06.01";
		textUp = "今日已用: " + todayUsedStr;// + (int) (30 * Math.random());
		textDown = monthUsedStr + " / " + monthSetStr + " --> " + date;
		// text1 = "今日已用: " + todayUsedStr + (int) (30 * Math.random());
		text1 = textUp;
		text2 = "本月流量: " + monthUsedStr + " / " + monthSetStr;
		text3 = "结算日期: " + date;
		// showLog(textUp);
	}

	/**
	 * 更新小部件与通知栏
	 * 
	 * @param context
	 */
	public static void resetWidgetAndNotify(Context context) {
		String BROADCAST_TRAFF = "com.hiapk.traffwidget";
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		boolean isNotifyOpen = sharedData.isNotifyOpen();
		boolean isWidGet14Open = sharedData.isWidGet14Open();
		if (isNotifyOpen) {
			ProgramNotify programNotify = new ProgramNotify();
			programNotify.showNotice(context);
			// }
		}
		if (isWidGet14Open) {
			if (!isNotifyOpen) {
				setText(context);
			}
			Intent intentTextUpdate = new Intent();
			intentTextUpdate.setAction(BROADCAST_TRAFF);
			context.sendBroadcast(intentTextUpdate);
		}
	}

	private static String getdate(Context context, int year, int month,
			int monthDay) {
		// TODO Auto-generated method stub
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int setDay = sharedData.getCountDay() + 1;
		int maxDay = countDay(year, month);
		if (setDay > maxDay) {
			setDay = maxDay;
		}
		if (setDay < monthDay) {
			if (month == 12) {
				year += 1;
				return year + "." + 1 + "." + setDay;
			} else {
				month += 1;
				return year + "." + month + "." + setDay;
			}

		} else {
			return year + "." + month + "." + setDay;
		}

	}

	/**
	 * 计算单月有几天
	 * 
	 * @param year
	 *            输入年份
	 * @param month
	 *            输入月份
	 * @return 返回天数
	 */
	private static int countDay(int year, int month) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))
				&& month == 2) {
			return 29;
		} else {
			switch (month) {
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			}
		}
		return 31;
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private static void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("SetText", string);
	}
}
