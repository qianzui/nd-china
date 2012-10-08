package com.hiapk.broadcreceiver;

import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.logs.Logs;
import com.hiapk.logs.WriteLog;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.format.Time;

public class DateChangeBroadcast extends BroadcastReceiver {
	private int monthDay;
	private Context context;
	private AlarmSet alset = new AlarmSet();
	private String TAG = "DateChangeBr";

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		new AsyncTaskonResetmonthAndtodayData().execute(context);
		if (SQLStatic.ConnectSleepWaiting == false) {
			new AsyncTaskonWaitingDayChange().execute(context);
		}
	}

	/**
	 * 延时统计数据后重置本月与本日数据
	 * 
	 * @author Administrator
	 * 
	 */
	private class AsyncTaskonResetmonthAndtodayData extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			alset.StartAlarm(context);
		}

		@Override
		protected Long doInBackground(Context... params) {
			try {
				Thread.sleep(1800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			resetRecordData(context);
		}
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
				Thread.sleep(2500);
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
	 * 清零月与日流量统计数据
	 * 
	 * @param context
	 */
	private void resetRecordData(Context context) {
		initTime();
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int countday = sharedData.getCountDay() + 1;
		if (monthDay == countday) {
			sharedData.setMonthHasUsedStack(0);
			PrefrenceStaticOperator.resetHasWarningMonth(context);
			TrafficManager.clearUidtraffMonthly(context);
			WriteLog writelog = new WriteLog(context);
			writelog.clearmonthLog();
		}
		sharedData.setTodayMobileDataLong(0);
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
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		monthDay = t.monthDay;
	}

}
