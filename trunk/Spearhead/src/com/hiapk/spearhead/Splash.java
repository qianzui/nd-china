package com.hiapk.spearhead;

import java.util.HashMap;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataOnUpdate;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class Splash extends Activity {
	private Context context = this;
	private AlarmSet alset = new AlarmSet();
	long time;
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
		List<ApplicationInfo> appInfos = pm.getInstalledApplications(0);
		Block.appnamemap = new HashMap<Integer, String>();
		SharedPreferences prefs = context.getSharedPreferences("firewall", 0);
		UseEditor = context.getSharedPreferences("firewall", 0).edit();
		String appname;
		for (int i = 0; i < appInfos.size(); i++) {
			final ApplicationInfo appInfo = appInfos.get(i);
			final int uid = appInfo.uid;
			final String pkgName = appInfo.packageName;
			if (PackageManager.PERMISSION_GRANTED == pm.checkPermission(
					Manifest.permission.INTERNET, pkgName)) {
				if (Block.filter.contains(pkgName)) {
				} else {
					appname = prefs.getString(pkgName, "");
					if (appname != "") {
						Block.appnamemap.put(uid, appname);
					} else {
						// new AsyncTaskSetappName().execute(pkgInfo);
						appname = appInfo.loadLabel(pm).toString();
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
			showLog("alarmover" + (System.currentTimeMillis() - time));
			// MobclickAgent.onError(this);
			showLog("uidinitbeforeover" + (System.currentTimeMillis() - time));
			if (SQLStatic.packagenames == null) {
				SQLStatic.getuidsAndpacname(context);
			}
			showLog("uidinitover" + (System.currentTimeMillis() - time));
			showLog("SQLStatic.pac.size=" + SQLStatic.packagenames.length);
			// 说明已经初始化
			if (TrafficManager.mobile_month_use != 1)
				return 0;
			// 开始初始化数据
			SQLHelperDataexe.initShowDataOnSplash(params[0]);
			showLog("overinitMaindata" + (System.currentTimeMillis() - time));
			// 初始化中等待初始化完成
			while (SQLHelperDataexe.isiniting == true) {
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
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

	private class AsyncTaskinitDatabase extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showLog(String string) {
		Log.d("Splash", string);
	}
}
