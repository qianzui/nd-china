package com.hiapk.widget;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.Time;
import android.text.style.ForegroundColorSpan;
import com.hiapk.dataexe.MonthDay;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.provider.ColorChangeMainBeen;

public class SetText {
	public static String textUp = "��������: 0 KB";
	public static String textDown;
	public static String text1 = "��������: ...";
	public static String text2 = "�������: ...";
	public static SpannableStringBuilder text3 = null;
	public static int FloatIntX = 50;
	public static int FloatIntY = 50;
	public static int ProgressBarPercent = 0;
	public static boolean HasSetMonthUsed = false;

	/**
	 * ��ȡ֪ͨ����С������ʾ����
	 * 
	 * @param context
	 * @param i
	 *            i=0����֪ͨ���ϣ�1����֪ͨ���£�2-4����С���������¡�
	 * @return
	 */
	public static synchronized void setText(Context context) {
		// ��¼��������
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
		String date = getdate(context, year, month, monthDay);
		String day = getday(context, year, month, monthDay);
		ProgressBarPercent = (int) (((double) monthUsedLong / monthSetLong) * 100);
		if (monthSetLong != 0) {
			HasSetMonthUsed = true;
		}
		// textUp = "�������ã�xxx kB(MB)";
		// textDown = "xx MB / 50 MB --> 2012.06.01";
		textUp = "��������: " + todayUsedStr;// + (int) (30 * Math.random());
		textDown = monthUsedStr + " / " + monthSetStr + " --> " + date;
		// text1 = "��������: " + todayUsedStr + (int) (30 * Math.random());
		text1 = textUp;
		// text2 = "��������: " + monthUsedStr + "/" + monthSetStr;
		// text3 = "��������: " + date;
		text2 = "�������: " + day + "��";
		// text3����
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

		// showLog(textUp);
	}

	/**
	 * ����С������֪ͨ��
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
			ProgramNotify programNotify = new ProgramNotify();
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

	private static String getdate(Context context, int year, int month,
			int monthDay) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int setDay = sharedData.getCountDay() + 1;
		int maxDay = MonthDay.countDay(year, month);
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

		} else if (setDay == monthDay) {
			month += 1;
			return year + "." + month + "." + setDay;
		} else {
			return year + "." + month + "." + setDay;
		}

	}

	// /**
	// * ������ʾ��־
	// *
	// * @param string
	// */
	// private static void showLog(String string) {
	// // Log.d("SetText", string);
	// }
}
