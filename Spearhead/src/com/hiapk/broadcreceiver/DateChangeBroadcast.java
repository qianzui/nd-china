package com.hiapk.broadcreceiver;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.exception.WriteLog;
import com.hiapk.prefrencesetting.PrefrenceStaticOperator;
import com.hiapk.ui.widget.SetText;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

public class DateChangeBroadcast extends BroadcastReceiver {
	// private static final String ACTION_TIME_CHANGED =
	// Intent.ACTION_TIME_CHANGED;
	// date
	// private int year;
	// private int month;
	private int monthDay;
	private Context context;
	private AlarmSet alset = new AlarmSet();

	@Override
	public void onReceive(Context context, Intent intent) {
		// String action = intent.getAction();
		// if (ACTION_TIME_CHANGED.equals(action)) {
		// showLog("DataChange");
		this.context = context;
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		initTime();
		alset.StartAlarm(context);
		int countday = sharedData.getCountDay() + 1;
		if (monthDay == countday) {
			sharedData.setMonthHasUsedStack(0);
			PrefrenceStaticOperator.resetHasWarningMonth(context);
			TrafficManager.clearUidtraffMonthly(context);
			WriteLog writelog = new WriteLog(context);
			writelog.clearmonthLog();
		}
		sharedData.setTodayMobileDataLong(0);
		SetText.resetWidgetAndNotify(context);
		PrefrenceStaticOperator.resetHasWarningDay(context);
		showLog("todya is " + monthDay);
		if (SQLStatic.ConnectSleepWaiting == false) {
			new AsyncTaskonWaitingDayChange().execute(context);
		}
		// }
	}

	/**
	 * 延时判断，进行取消网络检测操作，可省电
	 * 
	 * @author Administrator
	 * 
	 */
	private class AsyncTaskonWaitingDayChange extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			SQLStatic.ConnectSleepWaiting = true;
		}

		@Override
		protected Long doInBackground(Context... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			SQLStatic.initTablemobileAndwifi(context);
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.TableWiFiOrG23Before = "";
				alset.StopAlarm(context);
				SQLStatic.isTotalAlarmRecording = false;
				SQLStatic.isUidAlarmRecording = false;
			}
			SQLStatic.ConnectSleepWaiting = false;
		}
	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		// year = t.year;
		// month = t.month + 1;
		monthDay = t.monthDay;
	}

	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("DataChangeBroad", string);
		}
	}
}
