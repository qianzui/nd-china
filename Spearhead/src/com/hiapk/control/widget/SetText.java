package com.hiapk.control.widget;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;

import com.hiapk.control.traff.TrafficManager;
import com.hiapk.ui.skin.ColorChangeMainBeen;
import com.hiapk.util.MonthDay;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;
import com.hiapk.util.UnitHandler;

public class SetText {
	public static String textUp = "今日已用: 0 KB";
	public static String textDown;
	public static String text1 = "今日已用: ...";
	public static String text2 = "距结算日: ...";
	public static String textToday = "0";
	public static String textTodayShort = "今日: ...";
	public static String textTodayUnit = "KB";
	public static SpannableStringBuilder text3 = null;
	public static int FloatIntX = 50;
	public static int FloatIntY = 50;
	public static int ProgressBarPercent = 0;
	public static boolean HasSetMonthUsed = false;

	/**
	 * 获取通知栏与小部件显示文字
	 * 
	 * @param context
	 * @param i
	 *            i=0返回通知栏上，1返回通知栏下，2-4返回小部件上中下。
	 * @return
	 */
	private synchronized static void setText(Context context) {
		// 记录数据命令
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int monthDay = t.monthDay;
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		// long[] monthUsed_this = new long[64];
		// monthUsed_this = TrafficManager.mobile_month_data;
		long monthSetLong = sharedData.getMonthMobileSetOfLong();
		long monthUsedLong = TrafficManager.getMonthUseMobile(context);
		// trafficManager.setMonthUseDate(monthUsedLong);
		long todayUsedLong = sharedData.getTodayMobileDataLong();
		String monthUsedStr = UnitHandler.unitHandlerAccurate(monthUsedLong);
		String monthSetStr = UnitHandler.unitHandler(monthSetLong);
		String todayUsedStr = UnitHandler.unitHandlerAccurate(todayUsedLong);
		String[] widget11 = todayUsedStr.split(" ");
		String date = getdate(context, year, month, monthDay).toString();
		String day = getday(context, year, month, monthDay);
		ProgressBarPercent = (int) (((double) monthUsedLong / monthSetLong) * 100);
		if (ProgressBarPercent == 0 && monthUsedLong > 50000) {
			ProgressBarPercent = 1;
		}

		if (monthSetLong != 0) {
			HasSetMonthUsed = true;
		}
		// textUp = "今日已用：xxx kB(MB)";
		// textDown = "xx MB / 50 MB --> 2012.06.01";
		textUp = "今日已用: " + todayUsedStr;// + (int) (30 * Math.random());
		textDown = monthUsedStr + " / " + monthSetStr + " --> " + date;
		// text1 = "今日已用: " + todayUsedStr + (int) (30 * Math.random());
		text1 = textUp;
		// text2 = "本月流量: " + monthUsedStr + "/" + monthSetStr;
		// text3 = "结算日期: " + date;
		text2 = "距结算日: " + day + "天";
		// text3设置
		long monSet = sharedData.getMonthMobileSetOfLong();
		String text3tp = "" + monthUsedStr + " / " + monthSetStr;
		SpannableStringBuilder style = new SpannableStringBuilder(text3tp);
		if (monthUsedLong > monSet) {
			style.setSpan(
					new ForegroundColorSpan(ColorChangeMainBeen.colorRed), 0,
					monthUsedStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		} else {
			style.setSpan(
					new ForegroundColorSpan(ColorChangeMainBeen.colorBlue), 0,
					monthUsedStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		}

		text3 = style;
		textTodayShort = "今日: " + todayUsedStr;
		textToday = widget11[0];
		textTodayUnit = widget11[1];
		// showLog(textUp);
	}

	/**
	 * 初始化小部件及通知栏数据（widget用）
	 * 
	 * @param context
	 */
	public static void initText(Context context) {
		setText(context);
	}

	/**
	 * 更新小部件与通知栏
	 * 
	 * @param context
	 */
	public static void resetWidgetAndNotify(Context context) {
		String BROADCAST_TRAFF = "com.hiapk.traffwidget";
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		boolean isNotifyOpen = sharedDatawidget.isNotifyOpen();
		boolean isWidGet14Open = sharedDatawidget.isWidGet14Open();
		setText(context);
		if (isNotifyOpen) {
			NotificatiionProgramControl programNotify = new NotificatiionProgramControl();
			programNotify.showNotice(context, ProgressBarPercent);
			// }
		}
		if (isWidGet14Open) {
			Intent intentTextUpdate = new Intent();
			intentTextUpdate.setAction(BROADCAST_TRAFF);
			context.sendBroadcast(intentTextUpdate);
		}
	}

	private static String getday(Context context, int year, int month,
			int monthDay) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int setDay = sharedData.getCountDay() + 1;
		int maxDay = MonthDay.countDay(year, month);
		if (setDay > maxDay) {
			setDay = maxDay;
		}
		if (setDay < monthDay) {
			return (setDay + maxDay - monthDay) + "";

		} else if (setDay == monthDay) {
			return maxDay + "";
		} else {
			return (setDay - monthDay) + "";
		}

	}

	private static StringBuilder getdate(Context context, int year, int month,
			int monthDay) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		StringBuilder date = new StringBuilder();
		int setDay = sharedData.getCountDay() + 1;
		int maxDay = MonthDay.countDay(year, month);
		if (setDay > maxDay) {
			setDay = maxDay;
		}
		if (setDay <= monthDay) {
			if (month == 12) {
				year += 1;
				return date.append(year).append(".").append(1).append(".")
						.append(setDay);
			} else {
				month += 1;
				return date.append(year).append(".").append(month).append(".")
						.append(setDay);
			}

		} else {
			return date.append(year).append(".").append(month).append(".")
					.append(setDay);
		}

	}
	// /**
	// * 用于显示日志
	// *
	// * @param string
	// */
	// private static void showLog(String string) {
	// // Log.d("SetText", string);
	// }
}
