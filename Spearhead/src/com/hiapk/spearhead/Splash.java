package com.hiapk.spearhead;

import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

public class Splash extends Activity {
	private Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		AlarmSet alset = new AlarmSet();
		if (SQLStatic.TableWiFiOrG23 == "") {
			SQLStatic.initTablemobileAndwifi(context);
		}
		alset.StartAlarm(context);
		SQLStatic.initTablemobileAndwifi(context);
		// MobclickAgent.onError(this);
		new AsyncTaskonResume().execute(context);
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {

			getuids();
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
			// 等待数据读取
			int tap = 0;
			while (testInit() == false) {
				tap += 1;
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (tap > 8) {
					break;
				}
			}
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