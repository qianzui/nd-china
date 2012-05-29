package com.hiapk.prefrencesetting;

import java.util.List;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.firewall.Block;
import com.hiapk.spearhead.Main3;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.spearhead.Splash;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLStatic;
import com.hiapk.widget.ProgramNotify;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;

public class PrefrenceSetting extends PreferenceActivity {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 系统设置
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	String SYS_PRE_CLEAR_FIRE = "firetip";
	//
	CheckBoxPreference isNotifyOpen;
	CheckBoxPreference isfloatIndicatorOpen;
	CheckBoxPreference fireTip;
	ListPreference refreshFres;
	PreferenceScreen clearData;
	Context context = this;

	SharedPrefrenceData sharedData;
	ProgressDialog mydialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.setting_pref);
		isNotifyOpen = (CheckBoxPreference) findPreference(SYS_PRE_NOTIFY);
		isfloatIndicatorOpen = (CheckBoxPreference) findPreference(SYS_PRE_FLOAT_CTRL);
		refreshFres = (ListPreference) findPreference(SYS_PRE_REFRESH_FRZ);
		clearData = (PreferenceScreen) findPreference(SYS_PRE_CLEAR_DATA);
		fireTip = (CheckBoxPreference) findPreference(SYS_PRE_CLEAR_FIRE);
		// 监听
		isNotifyOpen.setOnPreferenceClickListener(oclick);
		isfloatIndicatorOpen.setOnPreferenceClickListener(oclick);
		fireTip.setOnPreferenceClickListener(oclick);
		refreshFres.setOnPreferenceChangeListener(ochange);
		clearData.setOnPreferenceClickListener(oclick);
		sharedData = new SharedPrefrenceData(context);
		// 初始化
		String refreshValue = sharedData.getWidgetFresh();
		refreshFres.setValue(refreshValue);
	}

	OnPreferenceClickListener oclick = new OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			if (preference.equals(isNotifyOpen)) {
				boolean isOpenNotify = isNotifyOpen.isChecked();
				// boolean text=sharedData.isNotifyOpen();
				AlarmSet alset = new AlarmSet();
				// showLog(text + "");
				if (isOpenNotify == true) {
					alset.StartWidgetAlarm(context);
				} else {
					alset.StopWidgetAlarm(context);
				}
				return true;
			}
			if (preference.equals(isfloatIndicatorOpen)) {
				boolean isfloatOpened = isfloatIndicatorOpen.isChecked();
				if (isfloatOpened) {
					startService(new Intent("com.hiapk.server"));
				} else {
					stopService(new Intent("com.hiapk.server"));
				}
				return true;
			}
			if (preference.equals(fireTip)) {
				boolean tip = fireTip.isChecked();
				if (tip) {
					Block.fireTipSet(context, true);
				} else {
					Block.fireTipSet(context, false);
				}
			}
			if (preference.equals(clearData)) {
				dialogClearDataBaseConfirm().show();

			}
			return false;
		}
	};

	OnPreferenceChangeListener ochange = new OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			if (preference.equals(refreshFres)) {
				AlarmSet alset = new AlarmSet();
				boolean isOpenNotify = isNotifyOpen.isChecked();
				sharedData.setWidgetFresh((String) newValue);
				if (isOpenNotify == true) {
					alset.StartWidgetAlarm(context);
				} else {
					alset.StopWidgetAlarm(context);
				}
				showLog(newValue + "");
				return true;
			}

			return true;
		}
	};

	private class AsyncTaskonClearSQL extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			mydialog = ProgressDialog
					.show(context, "请稍等...", "正在清空数据...", true);

			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Context... params) {
			int timetap = 0;
			while (!SQLStatic.setSQLTotalOnUsed(true)) {
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					return 1;
				}
			}
			while (!SQLStatic.setSQLUidOnUsed(true)) {
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					SQLStatic.setSQLUidOnUsed(false);
					return 1;
				}
			}
			while (!SQLStatic.setSQLIndexOnUsed(true)) {
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					SQLStatic.setSQLUidOnUsed(false);
					SQLStatic.setSQLIndexOnUsed(false);
					return 1;
				}
			}
			while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					SQLStatic.setSQLUidOnUsed(false);
					SQLStatic.setSQLIndexOnUsed(false);
					SQLStatic.setSQLUidTotalOnUsed(false);
					return 1;
				}
			}

			sharedData.setSQLinited(false);
			// 删除数据库
			params[0].deleteDatabase("SQLTotal.db");
			params[0].deleteDatabase("SQLUid.db");
			params[0].deleteDatabase("SQLUidIndex.db");
			params[0].deleteDatabase("SQLTotaldata.db");
			// 重新初始化数据库
			List<PackageInfo> packages = params[0].getPackageManager()
					.getInstalledPackages(0);
			int[] uids = new int[packages.size()];
			String[] packagenames = new String[packages.size()];
			for (int i = 0; i < packages.size(); i++) {
				PackageInfo packageinfo = packages.get(i);
				packagenames[i] = packageinfo.packageName;
				// Log.d("pac", packagenames[i]);
				uids[i] = packageinfo.applicationInfo.uid;
			}
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			sqlhelperTotal.initSQL(params[0], uids, packagenames);
			return 0;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if (values[0] == 1) {
				mydialog.dismiss();
				alert(context, "清空数据失败请重试").show();
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if (result == 0) {
				SQLStatic.setSQLTotalOnUsed(false);
				SQLStatic.setSQLUidOnUsed(false);
				SQLStatic.setSQLIndexOnUsed(false);
				SQLStatic.setSQLUidTotalOnUsed(false);
				sharedData.setSQLinited(true);
				mydialog.dismiss();
			}

		}
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("setting", string);
	}

	/**
	 * 进行清空数据库确认对话框
	 * 
	 * @return
	 */
	public AlertDialog dialogClearDataBaseConfirm() {
		AlertDialog dayWarning = new AlertDialog.Builder(context)
				.setTitle("注意！")
				.setMessage("该操作将清除所有历史数据，您确定要继续吗？")
				// .setView(textEntryView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						new AsyncTaskonClearSQL().execute(context);
						// SQLHelperTotal aa=new SQLHelperTotal();
						// showLog(aa.getIsInit(context)+"");
						// return true;
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				}).create();
		return dayWarning;

	}

	/**
	 * Display a simple alert box
	 * 
	 * @param ctx
	 *            context
	 * @param msg
	 *            message
	 */
	public AlertDialog alert(Context ctx, CharSequence msg) {
		if (ctx != null) {
			AlertDialog dayWarning = new AlertDialog.Builder(ctx)
					.setNeutralButton(android.R.string.ok, null)
					.setMessage(msg).create();
			return dayWarning;
		}
		return null;
	}
}
