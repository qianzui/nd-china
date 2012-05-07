package com.hiapk.broadcreceiver;

import com.hiapk.alertaction.TrafficAlert;
import com.hiapk.sqlhelper.SQLHelperTotal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.util.Log;

public class RecordDataReceiver extends BroadcastReceiver {
	//
	public static final int MODE_PRIVATE = 0;
	// use database
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	private SQLiteDatabase sqlDataBase;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		// 初始化数据库后进行操作
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				sqlDataBase = sqlhelperTotal.creatSQL(context);
				totalRecord();
				trafficAlertTest(context);
				showLog(SQLHelperTotal.TableWiFiOrG23);
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
		}
	}

	/**
	 * 进行预警判断并执行预警操作
	 * 
	 * @param context
	 */
	private void trafficAlertTest(Context context) {
		// TODO Auto-generated method stub
		TrafficAlert trafficalert = new TrafficAlert();
		showLog("month" + trafficalert.isTrafficOverMonthSet(context) + "day"
				+ trafficalert.isTrafficOverDaySet(context));
		if (trafficalert.isTrafficOverMonthSet(context)) {
			trafficalert.exeWarningActionMonth(context);
		}
		if (trafficalert.isTrafficOverDaySet(context)) {
			trafficalert.exeWarningActionDay(context);
		}
	}

	/**
	 * 进行数据记录
	 */
	private void totalRecord() {
		// 实时更新数据两个1代表数据更新
		sqlhelperTotal.updateSQLtotalType(sqlDataBase,
				SQLHelperTotal.TableWiFiOrG23, 1, null, 1);
		sqlhelperTotal.closeSQL(sqlDataBase);
		showLog("实时总体更新数据" + SQLHelperTotal.TableWiFiOrG23 + "  "
				+ "TotalTxBytes()=" + TrafficStats.getTotalTxBytes()
				+ "TotalRxBytes()=" + TrafficStats.getTotalRxBytes()
				+ "MobileTxBytes()=" + TrafficStats.getMobileTxBytes()
				+ "MobileRxBytes()=" + TrafficStats.getMobileRxBytes());
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}

}
