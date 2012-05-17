package com.hiapk.widget;

import android.content.Context;
import android.text.format.Time;

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
		trafficManager.statsTotalTraffic(context, false);
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		int monthDay = t.monthDay;
		UnitHandler unitHandler = new UnitHandler();
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		long[] monthUsed_this = trafficManager.getMobileMonthTraffic(context);
		long monthSetLong = sharedData.getMonthMobileSetOfLong();
		long monthUsedLong = trafficManager.getMonthUseData(context);
		long todayUsedLong = monthUsed_this[monthDay]
				+ monthUsed_this[monthDay + 31];
		String monthUsedStr = unitHandler.unitHandlerAccurate(monthUsedLong);
		String monthSetStr = unitHandler.unitHandlerAccurate(monthSetLong);
		String todayUsedStr = unitHandler.unitHandlerAccurate(todayUsedLong);

		// textUp = "今日已用：xxx kB(MB)";
		// textDown = "xx MB / 50 MB --> 2012.06.01";
		textUp = "今日已用: " + todayUsedStr + (int) (30 * Math.random());
		textDown = monthUsedStr + " / " + monthSetStr + " --> " + year + "."
				+ month + "." + monthDay;
//		text1 = "今日已用: " + todayUsedStr + (int) (30 * Math.random());
		text1=textUp;
		text2 = "本月流量: " + monthUsedStr + " / " + monthSetStr;
		text3 = "日期: " + year + "." + month + "." + monthDay;
	}
}
