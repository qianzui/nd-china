package com.hiapk.broadcreceiver;

import java.util.List;

import com.hiapk.alertaction.TrafficAlert;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.sqlhelper.SQLHelperTotal;

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
	// ����sharedprefrence
	String PREFS_NAME = "allprefs";
	// ����Ԥ��
	String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
	String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
	// date
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		// ��ʼ�����ݿ����в���
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				if (SQLHelperTotal.isSQLTotalOnUsed != true) {
					sqlDataBase = sqlhelperTotal.creatSQLTotal(context);
					new AsyncTaskonRecordTotalData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else
					showLog("���ݿ�æ��δ��¼");
			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
		}
	}

	/**
	 * ����Ԥ���жϲ�ִ��Ԥ������
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
			// �¶�
			if (trafficalert.isTrafficOverMonthSet(context)) {
				trafficalert.exeWarningActionMonth(context);
			}
		}
		// ��Ԥ��
		boolean dayHasWarning = prefs.getBoolean(MOBILE_HAS_WARNING_DAY, false);
		if (!dayHasWarning) {
			if (trafficalert.isTrafficOverDaySet(context)) {
				trafficalert.exeWarningActionDay(context);
			}
		}

	}

	/**
	 * �������ݼ�¼
	 */
	private void totalRecord(Context context) {
		// �������ݸ����Լ���¼
		sqlhelperTotal.updateSQLtotalType(sqlDataBase,
				SQLHelperTotal.TableWiFiOrG23, 1, null, 1);
		sqlhelperTotal.RecordTotalwritestats(sqlDataBase, false);
		// ���ɻ�����������
		initTime();
		long mobile_month_use_afterSet = 0;
		long[] wifi_month_data = new long[64];
		long[] mobile_month_data = new long[64];
		long[] mobile_week_data = new long[6];
		// mobile_month_use_afterSet=sqlhelperTotal.se
		wifi_month_data = sqlhelperTotal.SelectWifiData(context, year, month);
		mobile_month_data = sqlhelperTotal.SelectMobileData(context, year,
				month);
		mobile_week_data = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		sqlhelperTotal.closeSQL(sqlDataBase);
		// �����ݽ��и�ֵ
		TrafficManager.wifi_month_data = wifi_month_data;
		TrafficManager.mobile_month_data = mobile_month_data;
		TrafficManager.mobile_week_data = mobile_week_data;
		showLog("wifitotal=" + wifi_month_data[0] + "");
		// TrafficManager.setMonthUseDate(context);
		// �����־
		showLog("ʵʱ�����������" + SQLHelperTotal.TableWiFiOrG23 + "  "
				+ "TotalTxBytes()=" + TrafficStats.getTotalTxBytes()
				+ "TotalRxBytes()=" + TrafficStats.getTotalRxBytes()
				+ "MobileTxBytes()=" + TrafficStats.getMobileTxBytes()
				+ "MobileRxBytes()=" + TrafficStats.getMobileRxBytes());
	}

	private class AsyncTaskonRecordTotalData extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			SQLHelperTotal.isSQLTotalOnUsed = true;
		}

		@Override
		protected Long doInBackground(Context... params) {
			totalRecord(params[0]);
			// ������������
			// trafficManager.statsTotalTraffic(params[0], false);
			// long monthuse = trafficManager.countMonthUseDate(params[0]);
			// trafficManager.setMonthUseDate(params[0],monthuse);
			trafficAlertTest(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			SQLHelperTotal.isSQLTotalOnUsed = false;
			showLog("���¼�¼���");
		}
	}

	/**
	 * ��ʼ��ϵͳʱ��
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // ȡ��ϵͳʱ�䡣
		// ȡ��ϵͳʱ�䡣
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}

}
