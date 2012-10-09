package com.hiapk.broadcreceiver;

import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.logs.Logs;
import com.hiapk.logs.WriteLog;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.util.SharedPrefrenceData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class DateChangeBroadcastAfter extends BroadcastReceiver {
	private int monthDay;
	private String TAG = "DateChangeAft";

	@Override
	public void onReceive(Context context, Intent intent) {
		Logs.d(TAG, "afterDate");
		resetRecordData(context);
	}

	/**
	 * ��������������ͳ������
	 * 
	 * @param context
	 */
	private void resetRecordData(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		Logs.d(TAG, "setTodayMobileDataLong=0");
		sharedData.setTodayMobileDataLong(0);
		initTime();
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
		// Logs.d(TAG, "SetText.textUpaf=" + SetText.textUp);
	}

	/**
	 * ��ʼ��ϵͳʱ��
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // ȡ��ϵͳʱ�䡣
		monthDay = t.monthDay;
	}

}
