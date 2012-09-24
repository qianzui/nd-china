package com.hiapk.broadcreceiver;

import com.hiapk.control.traff.MonthlyUseData;
import com.hiapk.control.traff.TrafficAlert;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.total.SQLHelperTotal;
import com.hiapk.util.SQLStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;

public class RecordDataReceiver extends BroadcastReceiver {
	//
	public static final int MODE_PRIVATE = 0;
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// 流量预警
	private String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
	private String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
	// 是否允许流量预警
	private String IsAllowAlert = "isallowalert";
	// date
	private int year;
	private int month;
	private String network;

	// fortest
	// long time;

	@Override
	public void onReceive(Context context, Intent intent) {
		showLog("isTotalAlarmRecording=" + SQLStatic.isTotalAlarmRecording);
		if (SQLStatic.isTotalAlarmRecording == true)
			return;
		SQLStatic.isTotalAlarmRecording = true;
		SQLStatic.initTablemobileAndwifi(context);
		if (SQLStatic.TableWiFiOrG23 == "") {
			network = SQLStatic.TableWiFiOrG23Before;
		} else {
			network = SQLStatic.TableWiFiOrG23;
		}
		showLog("TableWiFiOrG23=" + SQLStatic.TableWiFiOrG23);
		showLog("TableWiFiOrG23Before=" + SQLStatic.TableWiFiOrG23Before);
		// 初始化数据库后进行操作
		if (SQLStatic.getIsInit(context)) {

			if (network != "") {
				if (SQLStatic.setSQLTotalOnUsed(true)) {
					// time = System.currentTimeMillis();
					new AsyncTaskonRecordTotalData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else {
					SQLStatic.isTotalAlarmRecording = false;
					showLog("数据库忙，未记录");
				}
			} else {
				if (TrafficManager.mobile_month_use == 1) {
					SQLHelperDataexe.initShowDataOnBroadCast(context);
				} else {
					SQLStatic.isTotalAlarmRecording = false;
				}
			}
		} else {
			// sqlhelper.initSQL(context);
			SQLStatic.isTotalAlarmRecording = false;
			showLog("please init the database");
		}
	}

	/**
	 * 进行预警判断并执行预警操作
	 * 
	 * @param context
	 */
	private void trafficAlertTest(Context context) {
		// 判断当前网络状态
		if (network != "mobile")
			return;
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		// 判断是否允许预警
		boolean isAllowAlert = prefs.getBoolean(IsAllowAlert, true);
		if (isAllowAlert == false)
			return;
		boolean monthHasWarning = prefs.getBoolean(MOBILE_HAS_WARNING_MONTH,
				false);
		// showLog("month" + trafficalert.isTrafficOverMonthSet(context) +
		// "day"
		// + trafficalert.isTrafficOverDaySet(context));

		TrafficAlert trafficalert = new TrafficAlert(context);
		if (!monthHasWarning) {
			// 月度
			if (trafficalert.isTrafficOverMonthSet(context)) {
				trafficalert.exeWarningActionMonth(context);
			}
		}
		// 日预警
		boolean dayHasWarning = prefs.getBoolean(MOBILE_HAS_WARNING_DAY, false);
		if (!dayHasWarning) {
			if (trafficalert.isTrafficOverDaySet(context)) {
				trafficalert.exeWarningActionDay(context);
			}
		}
	}

	/**
	 * 进行数据记录
	 */
	private void totalRecord(Context context) {
		// 进行数据更新以及记录
		long mobile_month_use_afterSet = 0;
		long[] wifi_month_data = new long[64];
		long[] mobile_month_data = new long[64];
		long[] wifi_month_data_before = new long[64];
		long[] mobile_month_data_before = new long[64];
		MonthlyUseData monthlyUseData = new MonthlyUseData();
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose
				.creatSQLTotal(context);
		sqlDataBase.beginTransaction();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		try {
			// sqlhelperTotal.updateSQLtotalType(sqlDataBase, network, 1, null,
			// 1);
			sqlhelperTotal.RecordTotalwritestats(context, sqlDataBase, false,
					network);
			// 生成基本常用数据
			initTime();
			mobile_month_use_afterSet = monthlyUseData.getMonthUseData(context,
					sqlDataBase);
			wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase, year,
					month);
			mobile_month_data = sqlhelperTotal.SelectMobileData(sqlDataBase,
					year, month);
			if (month == 1) {
				mobile_month_data_before = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year - 1, 12);
				wifi_month_data_before = sqlhelperTotal.SelectWifiData(
						sqlDataBase, year - 1, 12);
			} else {
				mobile_month_data_before = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year, month - 1);
				wifi_month_data_before = sqlhelperTotal.SelectWifiData(
						sqlDataBase, year, month - 1);
			}
			sqlhelperTotal.autoClearData(sqlDataBase);
			sqlDataBase.setTransactionSuccessful();
			// 对数据进行赋值
			TrafficManager.wifi_month_data = wifi_month_data;
			TrafficManager.mobile_month_data = mobile_month_data;
			TrafficManager.mobile_month_data_before = mobile_month_data_before;
			TrafficManager.wifi_month_data_before = wifi_month_data_before;
			TrafficManager.mobile_month_use = mobile_month_use_afterSet;
			// 数据存入xml
			// showLog("wifitotal=" + wifi_month_data[0] + "");
		} catch (Exception e) {
			showLog("数据记录失败");
		} finally {
			sqlDataBase.endTransaction();
			SQLHelperCreateClose.closeSQL(sqlDataBase);
		}

		// TrafficManager.setMonthUseDate(context);
		// 输出日志
		showLog("实时总体更新数据" + network + "  " + "TotalTxBytes()="
				+ TrafficStats.getTotalTxBytes() + "TotalRxBytes()="
				+ TrafficStats.getTotalRxBytes() + "MobileTxBytes()="
				+ TrafficStats.getMobileTxBytes() + "MobileRxBytes()="
				+ TrafficStats.getMobileRxBytes());
	}

	private class AsyncTaskonRecordTotalData extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// SQLHelperTotal.isSQLTotalOnUsed = true;
			showLog("network=" + network);
		}

		@Override
		protected Long doInBackground(Context... params) {
			totalRecord(params[0]);
			// 更新流量数据
			// trafficManager.statsTotalTraffic(params[0], false);
			// long monthuse = trafficManager.countMonthUseDate(params[0]);
			// trafficManager.setMonthUseDate(params[0],monthuse);
			trafficAlertTest(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			SQLStatic.isTotalAlarmRecording = false;
			// SQLHelperTotal.isSQLTotalOnUsed = false;
			SQLStatic.setSQLTotalOnUsed(false);
			// time = System.currentTimeMillis() - time;
			// showLog("更新记录完毕" + time);
		}
	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		// 取得系统时间。
		year = t.year;
		month = t.month + 1;

	}

	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("ReceiverTotal", string);
		}
	}

}
