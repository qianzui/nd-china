package com.hiapk.spearhead;

import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.GetRoot;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLStatic;
import com.umeng.analytics.MobclickAgent;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Splash extends Activity {
	private Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		sqlhelperTotal.initTablemobileAndwifi(context, false);

		// MobclickAgent.onError(this);
		SharedPrefrenceData sp = new SharedPrefrenceData(context);
		if (sp.isFirstBoot()) {
			// Intent i = new Intent();
			// i.setClass(context, Help.class);
			new AsyncTaskonFirstResume().execute(context);
			// startActivity(i);
		} else

			// 获取root权限
			// GetRoot.cmdRoot("chmod 777 " + getPackageCodePath());
			new AsyncTaskonResume().execute(context);
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// SQLHelperTotal.isSQLTotalOnUsed = true;
			// SQLHelperTotal.isSQLUidOnUsed = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			SharedPrefrenceData sharedData = new SharedPrefrenceData(params[0]);
			if (sharedData.isSQLinited() == false) {
				getuids();
				while (SQLStatic.uids == null) {
					initSQLdatabase(params[0], SQLStatic.uids,
							SQLStatic.packagenames);
				}
			}

			AlarmSet alset = new AlarmSet();
			// 初始化网络状态
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			// sqlhelperTotal.initTablemobileAndwifi(params[0],false);
			if (SQLHelperTotal.TableWiFiOrG23 != ""
					&& sqlhelperTotal.getIsInit(params[0])) {
				// 启动闹钟
				alset.StartAlarm(params[0]);
			}
			if (SQLHelperTotal.TableWiFiOrG23 == "") {
				alset.StartAlarm(params[0]);
				alset.StopAlarm(params[0]);
			}
			// 等待数据读取
			int tap = 0;
			while (testInit() == false) {
				tap += 1;
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (tap > 10) {
					break;
				}
			}
			return 3;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			mainIntent.putExtras(choosetab);
			Splash.this.startActivity(mainIntent);
			Splash.this.finish();
		}
	}

	private class AsyncTaskonFirstResume extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// SQLHelperTotal.isSQLTotalOnUsed = true;
			// SQLHelperTotal.isSQLUidOnUsed = true;
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			sqlhelperTotal.initTablemobileAndwifi(context, false);
		}

		@Override
		protected Integer doInBackground(Context... params) {
			SharedPrefrenceData sharedData = new SharedPrefrenceData(params[0]);
			if (sharedData.isSQLinited() == false) {
				getuids();
				while (SQLStatic.uids == null) {
					initSQLdatabase(params[0], SQLStatic.uids,
							SQLStatic.packagenames);
				}
			}

			AlarmSet alset = new AlarmSet();
			// 初始化网络状态
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			// sqlhelperTotal.initTablemobileAndwifi(params[0],false);
			if (SQLHelperTotal.TableWiFiOrG23 != ""
					&& sqlhelperTotal.getIsInit(params[0])) {
				// 启动闹钟
				alset.StartAlarm(params[0]);
			}
			if (SQLHelperTotal.TableWiFiOrG23 == "") {
				alset.StartAlarm(params[0]);
				alset.StopAlarm(params[0]);
			}
			// 等待数据读取
			int tap = 0;
			while (testInit() == false) {
				tap += 1;
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (tap > 10) {
					break;
				}
			}
			return 3;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			// Intent i = new Intent();
			// i.setClass(context, Help.class);
			// startActivity(i);
			// Splash.this.finish();
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
		// TODO Auto-generated method stub
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		if (!sqlhelperTotal.getIsInit(context)) {
			sqlhelperTotal.initSQL(context, uids, packagename);
		}
	}

	/**
	 * 临时函数
	 */
	private void getuids() {
		int j = 0;
		PackageManager pkgmanager = context.getPackageManager();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		int[] uidstp = new int[packages.size()];
		String[] packagenamestp = new String[packages.size()];
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			String fliter = Block.filter;
			String pacname = packageinfo.packageName;
			int uid = packageinfo.applicationInfo.uid;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					uidstp[j] = uid;
					packagenamestp[j] = pacname;
					// showLog("进行显示的uid=" + uid);
					j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		SQLStatic.uids = new int[j];
		SQLStatic.packagenames = new String[j];
		for (int i = 0; i < j; i++) {
			SQLStatic.uids[i] = uidstp[i];
			SQLStatic.packagenames[i] = packagenamestp[i];
		}
		// SQLHelperUid sqlhelpuid = new SQLHelperUid();
		// uids = sqlhelpuid.selectUidnumbers(context);
		// packagenames = sqlhelpuid.selectPackagenames(context);
	}

	private boolean testInit() {
		if (TrafficManager.mobile_month_data[0] == 0
				&& TrafficManager.wifi_month_data[0] == 0
				&& TrafficManager.mobile_month_data[63] == 0
				&& TrafficManager.wifi_month_data[63] == 0)
			return false;
		else {
			return true;
		}

	}
}