package com.hiapk.spearhead;

import java.util.HashMap;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceDataOnUpdate;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.sqlhelper.total.SQLHelperTotal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

public class Splash extends Activity {
	private Context context = this;
	private AlarmSet alset = new AlarmSet();
	// date
	private int year;
	private int month;
	private int monthDay;
	long time;
	private String network;
	private boolean isinited;
	private static PackageManager pm;
	private static Editor UseEditor;
	private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		context.sendBroadcast(new Intent(ACTION_TIME_CHANGED));

		time = System.currentTimeMillis();
		// MobclickAgent.onError(this);
		isinited = SQLStatic.getIsInit(context);
		showLog("isinited=" + isinited);
		if (isinited) {
			alset.StartAlarm(context);
			alset.StartWidgetAlarm(context);
			new AsyncTaskonResume().execute(context);
		} else {
			// 数据库未初始化则不需要进行版本更新时的特殊操作
			SharedPrefrenceDataOnUpdate sharedUpdate = new SharedPrefrenceDataOnUpdate(
					context);
			sharedUpdate.setUidRecordUpdated(true);
			new AsyncTaskinitDatabase().execute(context);
		}

		showLog("startinitfire" + (System.currentTimeMillis() - time));
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				getList(context);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				showLog("initfireover" + (System.currentTimeMillis() - time));
			}
		}.execute();

	}

	public static void getList(Context context) {
		pm = context.getPackageManager();
		List<PackageInfo> packageInfo = context.getPackageManager()
				.getInstalledPackages(0);
		Block.appnamemap = new HashMap<Integer, String>();
		SharedPreferences prefs = context.getSharedPreferences("firewall", 0);
		UseEditor = context.getSharedPreferences("firewall", 0).edit();
		String appname;
		for (int i = 0; i < packageInfo.size(); i++) {
			final PackageInfo pkgInfo = packageInfo.get(i);
			final int uid = pkgInfo.applicationInfo.uid;
			final String pkgName = pkgInfo.applicationInfo.packageName;
			if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(
					Manifest.permission.INTERNET, pkgName)) {
				if (Block.filter.contains(pkgName)) {
				} else {
					appname = prefs.getString(pkgName, "");
					if (appname != "") {
						Block.appnamemap.put(uid, appname);
					} else {
						// new AsyncTaskSetappName().execute(pkgInfo);
						appname = pkgInfo.applicationInfo.loadLabel(pm)
								.toString();
						UseEditor.putString(pkgName, appname);
						Block.appnamemap.put(uid, appname);
					}
					// String appname = pkgInfo.applicationInfo.loadLabel(pm)
					// .toString();
					// Block.appnamemap.put(uid, appname);
					// appList.add(pkgInfo);
				}
			}
		}
		UseEditor.commit();
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.initTablemobileAndwifi(context);
			}
			showLog("alarmover" + (System.currentTimeMillis() - time));
			SQLStatic.initTablemobileAndwifi(context);
			// MobclickAgent.onError(this);
			showLog("uidinitbeforeover" + (System.currentTimeMillis() - time));
			SQLStatic.getuidsAndpacname(context);
			showLog("uidinitover" + (System.currentTimeMillis() - time));
			while (SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			initDataWithnoNetwork(context);
			showLog("overinitMaindata" + (System.currentTimeMillis() - time));
			return 3;
		}

		@Override
		protected void onPostExecute(Integer result) {
			SQLStatic.setSQLTotalOnUsed(false);
			showLog("startingMain" + (System.currentTimeMillis() - time));
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			mainIntent.putExtras(choosetab);
			Splash.this.startActivity(mainIntent);
			showLog("this.finish" + (System.currentTimeMillis() - time));
			Splash.this.finish();
		}
	}

	private class AsyncTaskinitDatabase extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.initTablemobileAndwifi(context);
			}
			showLog("alarmover" + (System.currentTimeMillis() - time));
			SQLStatic.initTablemobileAndwifi(context);
			// MobclickAgent.onError(this);
			showLog("uidinitbeforeover" + (System.currentTimeMillis() - time));
			SQLStatic.getuidsAndpacname(context);
			showLog("uidinitover" + (System.currentTimeMillis() - time));

			showLog("startinitSQL" + (System.currentTimeMillis() - time));
			initSQLdatabase(params[0], SQLStatic.uidnumbers,
					SQLStatic.packagenames);
			showLog("overinitSQL" + (System.currentTimeMillis() - time));
			return 3;
		}

		@Override
		protected void onPostExecute(Integer result) {
			showLog("startingMain" + (System.currentTimeMillis() - time));
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			mainIntent.putExtras(choosetab);
			Splash.this.startActivity(mainIntent);
			showLog("this.finish" + (System.currentTimeMillis() - time));
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
		SQLHelperInitSQL sqlhelperInit = new SQLHelperInitSQL(context);
		sqlhelperInit.initSQL(context, uids, packagename);
	}

	private void initDataWithnoNetwork(Context context) {
		SQLStatic.initTablemobileAndwifi(context);
		network = "nonetwork";
		showLog("initDataWithnoNet=" + network);
		long mobile_month_use_afterSet = 0;
		long[] wifi_month_data = new long[64];
		long[] mobile_month_data = new long[64];
		long[] wifi_month_data_before = new long[64];
		long[] mobile_month_data_before = new long[64];
		MonthlyUseData monthlyUseData = new MonthlyUseData();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose
				.creatSQLTotal(context);
		sqlDataBase.beginTransaction();
		try {
			// 断网后的最后一次记录
			sqlhelperTotal.RecordTotalwritestats(context, sqlDataBase, false,
					network);
			// 生成基本常用数据
			initTime();
			showLog(monthDay + "0");
			mobile_month_use_afterSet = monthlyUseData.getMonthUseData(context,
					sqlDataBase);
			showLog(monthDay + "1");
			wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase, year,
					month);
			showLog(monthDay + "2");
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
			// showLog("wifitotal=" + wifi_month_data[0] + "");
		} catch (Exception e) {
			// TODO: handle exception
			showLog("数据记录失败");
		} finally {
			sqlDataBase.endTransaction();
		}
		SQLHelperCreateClose.closeSQL(sqlDataBase);
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
		if (SQLStatic.isshowLog) {
			Log.d("Splash", string);
		}
	}
}
