package com.hiapk.broadcreceiver;

import java.util.List;

import com.hiapk.alertaction.TrafficAlert;
import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLStatic;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.util.MonthDisplayHelper;

public class RecordDataReceiver extends BroadcastReceiver {
	//
	public static final int MODE_PRIVATE = 0;
	// use database
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	private SQLiteDatabase sqlDataBase;
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 流量预警
	String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
	String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
	// date
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;
	private String network;
	// fortest
	long time;
	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		// 初始化数据库后进行操作
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				if (SQLStatic.setSQLTotalOnUsed(true)) {
					time = System.currentTimeMillis();
					new AsyncTaskonRecordTotalData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else
					showLog("数据库忙，未记录");
			} else {
				if (SQLStatic.setSQLTotalOnUsed(true)) {
					// SQLHelperTotal.isSQLTotalOnUsed = true;
					initDataWithnoNetwork(context);
					SQLStatic.setSQLTotalOnUsed(false);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else
					showLog("数据库忙，未记录");
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
		}
	}

	private void initDataWithnoNetwork(Context context) {
		long mobile_month_use_afterSet = 0;
		long[] wifi_month_data = new long[64];
		long[] mobile_month_data = new long[64];
		long[] mobile_week_data = new long[6];
		MonthlyUseData monthlyUseData = new MonthlyUseData();
		sqlDataBase = sqlhelperTotal.creatSQLTotal(context);
		sqlDataBase.beginTransaction();
		try {
			// 生成基本常用数据
			initTime();

			if (SQLHelperTotal.TotalWiFiOrG23 != "") {
				network = SQLHelperTotal.TotalWiFiOrG23;
				SQLHelperTotal.TotalWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
				// 断网后的最后一次记录
				sqlhelperTotal.updateSQLtotalType(sqlDataBase, network, 1,
						null, 1);
				sqlhelperTotal.RecordTotalwritestats(sqlDataBase, false,
						network);
				// 生成基本常用数据
				initTime();
				mobile_month_use_afterSet = monthlyUseData.getMonthUseData(
						context, sqlDataBase);
				wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase,
						year, month);
				mobile_month_data = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year, month);
				mobile_week_data = sqlhelperTotal.SelectWeekData(sqlDataBase,
						year, month, monthDay, weekDay);
			} else {
				network = SQLHelperTotal.TotalWiFiOrG23;
				// 无网络进行数据显示，不进行记录
				//
				mobile_month_use_afterSet = monthlyUseData.getMonthUseData(
						context, sqlDataBase);
				wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase,
						year, month);
				mobile_month_data = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year, month);
				mobile_week_data = sqlhelperTotal.SelectWeekData(sqlDataBase,
						year, month, monthDay, weekDay);
			}
			sqlDataBase.setTransactionSuccessful();
			// 对数据进行赋值
			TrafficManager.mobile_month_use_afterSet = mobile_month_use_afterSet;
			TrafficManager.wifi_month_data = wifi_month_data;
			TrafficManager.mobile_month_data = mobile_month_data;
			TrafficManager.mobile_week_data = mobile_week_data;
			// showLog("wifitotal=" + wifi_month_data[0] + "");
		} catch (Exception e) {
			// TODO: handle exception
			showLog("数据记录失败");
		} finally {
			sqlDataBase.endTransaction();
		}
		sqlhelperTotal.closeSQL(sqlDataBase);
	}

	/**
	 * 进行预警判断并执行预警操作
	 * 
	 * @param context
	 */
	private void trafficAlertTest(Context context) {
		// TODO Auto-generated method stub
		TrafficAlert trafficalert = new TrafficAlert();
		// showLog("month" + trafficalert.isTrafficOverMonthSet(context) + "day"
		// + trafficalert.isTrafficOverDaySet(context));
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		boolean monthHasWarning = prefs.getBoolean(MOBILE_HAS_WARNING_MONTH,
				false);
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
		long[] mobile_week_data = new long[6];
		MonthlyUseData monthlyUseData = new MonthlyUseData();
		sqlDataBase.beginTransaction();
		try {
			sqlhelperTotal.updateSQLtotalType(sqlDataBase, network, 1, null, 1);
			sqlhelperTotal.RecordTotalwritestats(sqlDataBase, false, network);
			// 生成基本常用数据
			initTime();
			mobile_month_use_afterSet = monthlyUseData.getMonthUseData(context,
					sqlDataBase);
			wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase, year,
					month);
			mobile_month_data = sqlhelperTotal.SelectMobileData(sqlDataBase,
					year, month);
			mobile_week_data = sqlhelperTotal.SelectWeekData(sqlDataBase, year,
					month, monthDay, weekDay);
			sqlDataBase.setTransactionSuccessful();
			// 对数据进行赋值
			TrafficManager.mobile_month_use_afterSet = mobile_month_use_afterSet;
			TrafficManager.wifi_month_data = wifi_month_data;
			TrafficManager.mobile_month_data = mobile_month_data;
			TrafficManager.mobile_week_data = mobile_week_data;
			// showLog("wifitotal=" + wifi_month_data[0] + "");
		} catch (Exception e) {
			// TODO: handle exception
			showLog("数据记录失败");
		} finally {
			sqlDataBase.endTransaction();
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
			// TODO Auto-generated method stub
			super.onPreExecute();
			// SQLHelperTotal.isSQLTotalOnUsed = true;
			sqlDataBase = sqlhelperTotal.creatSQLTotal(context);
			if (SQLHelperTotal.TotalWiFiOrG23 == "") {
				network = SQLHelperTotal.TableWiFiOrG23;
				SQLHelperTotal.TotalWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
			} else {
				network = SQLHelperTotal.TotalWiFiOrG23;
				SQLHelperTotal.TotalWiFiOrG23 = SQLHelperTotal.TableWiFiOrG23;
			}

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
			// TODO Auto-generated method stub
			sqlhelperTotal.closeSQL(sqlDataBase);
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
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		// 取得系统时间。
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("ReceiverTotal", string);
	}

}
