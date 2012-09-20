package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PrefrenceSetting extends Activity {
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
	// 几个大选项
	LinearLayout layout_notyfy;
	LinearLayout layout_float;
	LinearLayout layout_help_info;
	LinearLayout layout_freshplv;
	LinearLayout layout_cleardata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		layout_notyfy = (LinearLayout) findViewById(R.id.setting_notify);
		layout_float = (LinearLayout) findViewById(R.id.setting_float);
		layout_help_info = (LinearLayout) findViewById(R.id.setting_help_message);
		layout_freshplv = (LinearLayout) findViewById(R.id.setting_freshplv);
		layout_cleardata = (LinearLayout) findViewById(R.id.setting_cleardata);
		PrefrenceBeen prefBeen = new PrefrenceBeen(context);
		prefBeen.initCheckBoxNotyfy(layout_notyfy);
		prefBeen.initCheckBoxFloat(layout_float);
		prefBeen.initCheckBoxHelpMessage(layout_help_info);
		prefBeen.initListBoxFresh(layout_freshplv);
		prefBeen.initClickBoxDataClear(layout_cleardata);
		// 皮肤设置
		final ImageView back = (ImageView) findViewById(R.id.setting_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				back.setImageResource(R.drawable.back_black);
			}
		});
		// this.addPreferencesFromResource(R.xml.setting_pref);
		// isNotifyOpen = (CheckBoxPreference) findPreference(SYS_PRE_NOTIFY);
		// isfloatIndicatorOpen = (CheckBoxPreference)
		// findPreference(SYS_PRE_FLOAT_CTRL);
		// refreshFres = (ListPreference) findPreference(SYS_PRE_REFRESH_FRZ);
		// clearData = (PreferenceScreen) findPreference(SYS_PRE_CLEAR_DATA);
		// fireTip = (CheckBoxPreference) findPreference(SYS_PRE_CLEAR_FIRE);

		// // 监听
		// isNotifyOpen.setOnPreferenceClickListener(oclick);
		// isfloatIndicatorOpen.setOnPreferenceClickListener(oclick);
		// fireTip.setOnPreferenceClickListener(oclick);
		// refreshFres.setOnPreferenceChangeListener(ochange);
		// clearData.setOnPreferenceClickListener(oclick);
		// sharedData = new SharedPrefrenceData(context);
		//
		//
		//
		// // 初始化
		// String refreshValue = sharedData.getWidgetFresh();
		// refreshFres.setValue(refreshValue);
	}

	private void initScene() {
		FrameLayout title = (FrameLayout) findViewById(R.id.settingTitleBackground);
		title.setBackgroundResource(SkinCustomMains.buttonTitleBackground());
		layout_notyfy.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_float.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_help_info
				.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_freshplv.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_cleardata
				.setBackgroundResource(SkinCustomMains.barsBackground());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initScene();
	}

	//
	// OnPreferenceClickListener oclick = new OnPreferenceClickListener() {
	//
	// @Override
	// public boolean onPreferenceClick(Preference preference) {
	// // TODO Auto-generated method stub
	// if (preference.equals(isNotifyOpen)) {
	// boolean isOpenNotify = isNotifyOpen.isChecked();
	// // boolean text=sharedData.isNotifyOpen();
	// AlarmSet alset = new AlarmSet();
	// // showLog(text + "");
	// if (isOpenNotify == true) {
	// alset.StartWidgetAlarm(context);
	// } else {
	// alset.StopWidgetAlarm(context);
	// }
	// return true;
	// }
	// if (preference.equals(isfloatIndicatorOpen)) {
	// boolean isfloatOpened = isfloatIndicatorOpen.isChecked();
	// if (isfloatOpened) {
	// startService(new Intent("com.hiapk.server"));
	// } else {
	// stopService(new Intent("com.hiapk.server"));
	// }
	// return true;
	// }
	// if (preference.equals(fireTip)) {
	// boolean tip = fireTip.isChecked();
	// if (tip) {
	// Block.fireTipSet(context, true);
	// } else {
	// Block.fireTipSet(context, false);
	// }
	// }
	// if (preference.equals(clearData)) {
	// dialogClearDataBaseConfirm().show();
	//
	// }
	// return false;
	// }
	// };
	//
	// // OnPreferenceChangeListener ochange = new OnPreferenceChangeListener()
	// {
	// //
	// // @Override
	// // public boolean onPreferenceChange(Preference preference, Object
	// newValue) {
	// // // TODO Auto-generated method stub
	// // if (preference.equals(refreshFres)) {
	// // AlarmSet alset = new AlarmSet();
	// // boolean isOpenNotify = isNotifyOpen.isChecked();
	// // sharedData.setWidgetFresh((String) newValue);
	// // if (isOpenNotify == true) {
	// // alset.StartWidgetAlarm(context);
	// // } else {
	// // alset.StopWidgetAlarm(context);
	// // }
	// // showLog(newValue + "");
	// // return true;
	// // }
	// //
	// // return true;
	// // }
	// // };
	//
	// private class AsyncTaskonClearSQL extends
	// AsyncTask<Context, Integer, Integer> {
	// @Override
	// protected void onPreExecute() {
	// // TODO Auto-generated method stub
	// mydialog = ProgressDialog
	// .show(context, "请稍等...", "正在清空数据...", true);
	//
	// super.onPreExecute();
	// }
	//
	// @Override
	// protected Integer doInBackground(Context... params) {
	// int timetap = 0;
	// while (!SQLStatic.setSQLTotalOnUsed(true)) {
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// timetap++;
	// if (timetap > 10) {
	// publishProgress(1);
	// SQLStatic.setSQLTotalOnUsed(false);
	// return 1;
	// }
	// }
	// while (!SQLStatic.setSQLUidOnUsed(true)) {
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// timetap++;
	// if (timetap > 10) {
	// publishProgress(1);
	// SQLStatic.setSQLTotalOnUsed(false);
	// SQLStatic.setSQLUidOnUsed(false);
	// return 1;
	// }
	// }
	// while (!SQLStatic.setSQLIndexOnUsed(true)) {
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// timetap++;
	// if (timetap > 10) {
	// publishProgress(1);
	// SQLStatic.setSQLTotalOnUsed(false);
	// SQLStatic.setSQLUidOnUsed(false);
	// SQLStatic.setSQLIndexOnUsed(false);
	// return 1;
	// }
	// }
	// while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
	// try {
	// Thread.sleep(100);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// timetap++;
	// if (timetap > 10) {
	// publishProgress(1);
	// SQLStatic.setSQLTotalOnUsed(false);
	// SQLStatic.setSQLUidOnUsed(false);
	// SQLStatic.setSQLIndexOnUsed(false);
	// SQLStatic.setSQLUidTotalOnUsed(false);
	// return 1;
	// }
	// }
	//
	// sharedData.setSQLinited(false);
	// // 删除数据库
	// params[0].deleteDatabase("SQLTotal.db");
	// params[0].deleteDatabase("SQLUid.db");
	// params[0].deleteDatabase("SQLUidIndex.db");
	// params[0].deleteDatabase("SQLTotaldata.db");
	// // 重新初始化数据库
	// List<PackageInfo> packages = params[0].getPackageManager()
	// .getInstalledPackages(0);
	// int[] uids = new int[packages.size()];
	// String[] packagenames = new String[packages.size()];
	// for (int i = 0; i < packages.size(); i++) {
	// PackageInfo packageinfo = packages.get(i);
	// packagenames[i] = packageinfo.packageName;
	// // Log.d("pac", packagenames[i]);
	// uids[i] = packageinfo.applicationInfo.uid;
	// }
	// SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// sqlhelperTotal.initSQL(params[0], uids, packagenames);
	// return 0;
	// }
	//
	// @Override
	// protected void onProgressUpdate(Integer... values) {
	// // TODO Auto-generated method stub
	// super.onProgressUpdate(values);
	// if (values[0] == 1) {
	// mydialog.dismiss();
	// alert(context, "清空数据失败请重试").show();
	// }
	// }
	//
	// @Override
	// protected void onPostExecute(Integer result) {
	// // TODO Auto-generated method stub
	// if (result == 0) {
	// SQLStatic.setSQLTotalOnUsed(false);
	// SQLStatic.setSQLUidOnUsed(false);
	// SQLStatic.setSQLIndexOnUsed(false);
	// SQLStatic.setSQLUidTotalOnUsed(false);
	// sharedData.setSQLinited(true);
	// mydialog.dismiss();
	// }
	//
	// }
	// }
	//
	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// Log.d("setting", string);
	// }
	//
	// /**
	// * 进行清空数据库确认对话框
	// *
	// * @return
	// */
	// public AlertDialog dialogClearDataBaseConfirm() {
	// AlertDialog dayWarning = new AlertDialog.Builder(context)
	// .setTitle("注意！")
	// .setMessage("该操作将清除所有历史数据，您确定要继续吗？")
	// // .setView(textEntryView)
	// .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// new AsyncTaskonClearSQL().execute(context);
	// // SQLHelperTotal aa=new SQLHelperTotal();
	// // showLog(aa.getIsInit(context)+"");
	// // return true;
	// }
	// })
	// .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// }
	// }).create();
	// return dayWarning;
	//
	// }
	//
	// /**
	// * Display a simple alert box
	// *
	// * @param ctx
	// * context
	// * @param msg
	// * message
	// */
	// public AlertDialog alert(Context ctx, CharSequence msg) {
	// if (ctx != null) {
	// AlertDialog dayWarning = new AlertDialog.Builder(ctx)
	// .setNeutralButton(android.R.string.ok, null)
	// .setMessage(msg).create();
	// return dayWarning;
	// }
	// return null;
	// }

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
