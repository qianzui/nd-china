package com.hiapk.broadcreceiver;

import com.hiapk.alertaction.TrafficAlert;
import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperTotal;

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
	private String network;
	// fortest
	long time;
	Context context;
	SharedPrefrenceDataWidget sharedData;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		showLog("onReceive");
		sharedData = new SharedPrefrenceDataWidget(context);
		if (SQLStatic.isTotalAlarmRecording == false) {
			SQLStatic.isTotalAlarmRecording = true;
			SQLStatic.initTablemobileAndwifi(context);
			this.context = context;
			if (SQLStatic.TableWiFiOrG23 == "") {
				network = SQLStatic.TableWiFiOrG23Before;
			} else {
				network = SQLStatic.TableWiFiOrG23;
			}
			showLog("TableWiFiOrG23=" + SQLStatic.TableWiFiOrG23);
			showLog("TableWiFiOrG23Before=" + SQLStatic.TableWiFiOrG23Before);
			// ��ʼ�����ݿ����в���
			if (SQLStatic.getIsInit(context)) {

				if (network != "") {
					if (SQLStatic.setSQLTotalOnUsed(true)) {
						time = System.currentTimeMillis();
						sqlDataBase = SQLHelperCreateClose
								.creatSQLTotal(context);
						new AsyncTaskonRecordTotalData().execute(context);
						// showLog(SQLHelperTotal.TableWiFiOrG23);
					} else {
						SQLStatic.isTotalAlarmRecording = false;
						showLog("���ݿ�æ��δ��¼");
					}
				} else {
					if (TrafficManager.mobile_month_use == 1) {
						SQLHelperDataexe.initShowData(context);
					}
					SQLStatic.isTotalAlarmRecording = false;
				}
			} else {
				// sqlhelper.initSQL(context);
				SQLStatic.isTotalAlarmRecording = false;
				showLog("please init the database");
			}
		}
	}

	/**
	 * ����Ԥ���жϲ�ִ��Ԥ������
	 * 
	 * @param context
	 */
	private void trafficAlertTest(Context context) {
		if (network == "mobile") {
			TrafficAlert trafficalert = new TrafficAlert();
			// showLog("month" + trafficalert.isTrafficOverMonthSet(context) +
			// "day"
			// + trafficalert.isTrafficOverDaySet(context));
			SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,
					0);
			boolean monthHasWarning = prefs.getBoolean(
					MOBILE_HAS_WARNING_MONTH, false);
			if (!monthHasWarning) {
				// �¶�
				if (trafficalert.isTrafficOverMonthSet(context)) {
					trafficalert.exeWarningActionMonth(context);
				}
			}
			// ��Ԥ��
			boolean dayHasWarning = prefs.getBoolean(MOBILE_HAS_WARNING_DAY,
					false);
			if (!dayHasWarning) {
				if (trafficalert.isTrafficOverDaySet(context)) {
					trafficalert.exeWarningActionDay(context);
				}
			}
		}
	}

	/**
	 * �������ݼ�¼
	 */
	private void totalRecord(Context context) {
		// �������ݸ����Լ���¼
		long mobile_month_use_afterSet = 0;
		long[] wifi_month_data = new long[64];
		long[] mobile_month_data = new long[64];
		long[] wifi_month_data_before = new long[64];
		long[] mobile_month_data_before = new long[64];
		MonthlyUseData monthlyUseData = new MonthlyUseData();
		sqlDataBase.beginTransaction();
		try {
			// sqlhelperTotal.updateSQLtotalType(sqlDataBase, network, 1, null,
			// 1);
			sqlhelperTotal.RecordTotalwritestats(context, sqlDataBase, false,
					network);
			// ���ɻ�����������
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
			// �����ݽ��и�ֵ
			TrafficManager.mobile_month_use = mobile_month_use_afterSet;
			TrafficManager.wifi_month_data = wifi_month_data;
			TrafficManager.mobile_month_data = mobile_month_data;
			TrafficManager.mobile_month_data_before = mobile_month_data_before;
			TrafficManager.wifi_month_data_before = wifi_month_data_before;
			sharedData.setTodayMobileDataLong(mobile_month_data[monthDay]
					+ mobile_month_data[monthDay + 31]);
			// showLog("wifitotal=" + wifi_month_data[0] + "");
		} catch (Exception e) {
			// TODO: handle exception
			showLog("���ݼ�¼ʧ��");
		} finally {
			sqlDataBase.endTransaction();
		}

		// TrafficManager.setMonthUseDate(context);
		// �����־
		showLog("ʵʱ�����������" + network + "  " + "TotalTxBytes()="
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
			showLog("network=" + network);
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
			SQLStatic.isTotalAlarmRecording = false;
			SQLHelperCreateClose.closeSQL(sqlDataBase);
			// SQLHelperTotal.isSQLTotalOnUsed = false;
			SQLStatic.setSQLTotalOnUsed(false);
			// time = System.currentTimeMillis() - time;
			// showLog("���¼�¼���" + time);
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

	}

	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("ReceiverTotal", string);
		}
	}

}
