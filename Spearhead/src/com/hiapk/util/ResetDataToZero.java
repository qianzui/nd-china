package com.hiapk.util;

import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.logs.Logs;
import com.hiapk.logs.WriteLog;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.ui.scene.PrefrenceStaticOperator;

import android.content.Context;
import android.text.format.Time;

public class ResetDataToZero {
	private static String TAG = "ResetDataToZero";
	private static int monthDay;

	/**
	 * ��������������ͳ������
	 * 
	 * @param context
	 */
	public static void resetData(Context context) {
		SharedPrefrenceData sharedData = SpearheadApplication.getInstance().getsharedData();
		int beforeResetDay = sharedData.getBeforeResetDay();
		Time t = new Time();
		t.setToNow(); // ȡ��ϵͳʱ�䡣
		monthDay = t.monthDay;
		if (beforeResetDay != monthDay) {
			Logs.d(TAG, "setTodayMobileDataLong=0");
			sharedData.setTodayMobileDataLong(0);
			// ��¼����ʱ��
			sharedData.setBeforeResetDay(monthDay);
			int countday = sharedData.getCountDay() + 1;
			if (monthDay == countday) {
				sharedData.setMonthHasUsedStack(0);
				TrafficManager.clearUidtraffMonthly(context);
				WriteLog writelog = new WriteLog(context);
				writelog.clearmonthLog();
				// ��ֹ�³����ݴ���
				TrafficManager.mobile_month_use = 1;
				PrefrenceStaticOperator.resetHasWarningMonth(context);
				// // ��ʼ�µ�һ�³�ʼ������
				// SQLHelperDataexe.initShowDataOnSplash(context);
			}
			//
			// SharedPrefrenceDataWidget sharedDatawidget = new
			// SharedPrefrenceDataWidget(
			// context);
			// boolean isNotifyOpen = sharedDatawidget.isNotifyOpen();
			// Logs.d(TAG, "isNotifyOpen=" + isNotifyOpen);
			// Logs.d(TAG, "SetText.textUpbef=" + SetText.textUp);
			SetText.resetWidgetAndNotify(context);
			PrefrenceStaticOperator.resetHasWarningDay(context);
			Logs.d(TAG, "dayRest=" + monthDay);
		}
	}
}
