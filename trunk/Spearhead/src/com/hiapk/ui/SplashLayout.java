package com.hiapk.ui;

import java.util.HashMap;
import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.ui.custom.CustomDialogFAQBeen;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataOnUpdate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashLayout extends LinearLayout {

	private Context context;
	private LinearLayout layout;
	private ImageView firehelp;
	private AlarmSet alset = new AlarmSet();
	long time;
	private boolean isinited;
	private String TAG = "SplashLayout";
	private static PackageManager pm;
	private static Editor UseEditor;
	private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
	private Handler handle;
	private long sysTimeStart = 0;
	private Runnable delayShow = new Runnable() {

		@Override
		public void run() {
			long sysTimeNow = System.currentTimeMillis();
			Logs.d(TAG, "sysTimeStart=" + sysTimeStart);
			Logs.d(TAG, "sysTimeNow=" + sysTimeNow);
			Logs.d(TAG, "diff=" + (sysTimeNow - sysTimeStart));
			if (sysTimeNow - sysTimeStart > 1000) {
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handle.handleMessage(null);
			} else {
				try {
					Thread.sleep(1800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handle.handleMessage(null);
			}

		}
	};

	public SplashLayout(LinearLayout layout, ImageView firehelp,
			Context context) {
		super(context);
		this.context = context;
		this.layout = layout;
		this.firehelp = firehelp;
		onCreateView();
		handleMessage();
	}

	private void handleMessage() {
		handle = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				layout.removeAllViews();
				layout.addView(firehelp);
				versionUpdateWindiw();
				super.handleMessage(msg);
			}
		};
	}

	private void onCreateView() {
		sysTimeStart = System.currentTimeMillis();
		// LayoutInflater.from(context).inflate(R.layout.splash, this);
		// Window window = activity.getWindow();
		// int heigh =
		// window.getWindowManager().getDefaultDisplay().getHeight();
		// int width = window.getWindowManager().getDefaultDisplay().getWidth();
		// this.setLayoutParams(new LayoutParams(width, heigh));
	}

	private void versionUpdateWindiw() {
		SharedPrefrenceDataOnUpdate sharedupdate = new SharedPrefrenceDataOnUpdate(
				context);
		if (sharedupdate.isVersionupdated() == false) {
			CustomDialogFAQBeen dialogupdate = new CustomDialogFAQBeen(context);
			dialogupdate.dialogUpdateInfoOnFirst();
			sharedupdate.setVersionupdated(true);
		}

	}

	public void onCreateOperator() {
		// setContentView(R.layout.splash);

		context.sendBroadcast(new Intent(ACTION_TIME_CHANGED));
		SQLStatic.isAppOpened = true;
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
			sharedUpdate.setTotal121updated(true);
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
			delayShow.run();
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
			delayShow.run();
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
		Logs.d(TAG, string);
	}
}
