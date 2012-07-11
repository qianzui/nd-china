package com.hiapk.spearhead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.GetRoot;
import com.hiapk.firewall.MyListView;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.sqlhelper.total.SQLHelperTotal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

public class Splash extends Activity {
	private  Context context = this;
	private AlarmSet alset = new AlarmSet();
	// date
	private int year;
	private int month;
	private int monthDay;
	private String network;
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		// MobclickAgent.onError(this);
		AlarmSet alset = new AlarmSet();
		if (SQLStatic.TableWiFiOrG23 == "") {
			SQLStatic.initTablemobileAndwifi(context);
		}
		alset.StartAlarm(context);
		SQLStatic.initTablemobileAndwifi(context);
		// MobclickAgent.onError(this);
		new AsyncTaskonResume().execute(context);
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				getList(context);
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
			}
		}.execute();
	}

	public static void getList(Context context){
		List<PackageInfo> packageInfo = context.getPackageManager().getInstalledPackages(0);
		final PackageManager pm = context.getPackageManager();
		ArrayList<PackageInfo> appList = new ArrayList<PackageInfo>();
		Block.appnamemap = new HashMap<Integer, String>();
		for (int i = 0; i < packageInfo.size(); i++) {
			final PackageInfo pkgInfo = packageInfo.get(i);
			final int uid = pkgInfo.applicationInfo.uid;
			final String pkgName = pkgInfo.applicationInfo.packageName;
			if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(
							Manifest.permission.INTERNET,pkgName
							)) {
				if (Block.filter.contains(pkgName)) {
				} else {
					String appname =  pkgInfo.applicationInfo.loadLabel(pm).toString();
					Block.appnamemap.put(uid,appname);
					appList.add(pkgInfo);
				}
			}
		}
	}
	
	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {

			SQLStatic.uids = SQLStatic.selectUidnumbers(params[0]);
			while (SQLStatic.uids == null) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (SQLStatic.getIsInit(params[0]) == false) {
				initSQLdatabase(params[0], SQLStatic.uids,
						SQLStatic.packagenames);
			}

			while (SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			initDataWithnoNetwork(context);
			SQLStatic.setSQLTotalOnUsed(false);
			return 3;
		}

		@Override
		protected void onPostExecute(Integer result) {
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			mainIntent.putExtras(choosetab);
			Splash.this.startActivity(mainIntent);
			Splash.this.finish();
		}
	}

	/**
	 * 初始化数据库
	 * 
	 * @param uids
	 *            uid数组
	 * @param packagename
	 *            uid对应的包名组
	 */
	private void initSQLdatabase(Context context, int[] uids,
			String[] packagename) {
		SQLHelperInitSQL sqlhelperInit = new SQLHelperInitSQL();
		if (!SQLStatic.getIsInit(context)) {
			sqlhelperInit.initSQL(context, uids, packagename);
		}
	}

	private void initDataWithnoNetwork(Context context) {
		SQLStatic.initTablemobileAndwifi(context);
		network = SQLStatic.TableWiFiOrG23;
		showLog("initDataWithnoNetwork=" + network);
		if (TrafficManager.mobile_month_use == 1) {
			long mobile_month_use_afterSet = 0;
			long[] wifi_month_data = new long[64];
			long[] mobile_month_data = new long[64];
			long[] wifi_month_data_before = new long[64];
			long[] mobile_month_data_before = new long[64];
			MonthlyUseData monthlyUseData = new MonthlyUseData();
			SQLiteDatabase sqlDataBase = SQLHelperCreateClose
					.creatSQLTotal(context);
			sqlDataBase.beginTransaction();
			try {
				// 断网后的最后一次记录
				sqlhelperTotal.RecordTotalwritestats(context, sqlDataBase,
						false, network);
				// 生成基本常用数据
				initTime();
				showLog(monthDay + "0");
				mobile_month_use_afterSet = monthlyUseData.getMonthUseData(
						context, sqlDataBase);
				showLog(monthDay + "1");
				wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase,
						year, month);
				showLog(monthDay + "2");
				mobile_month_data = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year, month);
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
				TrafficManager.mobile_month_use = mobile_month_use_afterSet;
				TrafficManager.wifi_month_data = wifi_month_data;
				TrafficManager.mobile_month_data = mobile_month_data;
				TrafficManager.mobile_month_data_before = mobile_month_data_before;
				TrafficManager.wifi_month_data_before = wifi_month_data_before;

				// showLog("wifitotal=" + wifi_month_data[0] + "");
			} catch (Exception e) {
				// TODO: handle exception
				showLog("数据记录失败");
			} finally {
				sqlDataBase.endTransaction();
				SQLStatic.isTotalAlarmRecording = false;
			}
			SQLHelperCreateClose.closeSQL(sqlDataBase);
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

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Splash", string);
	}
}
