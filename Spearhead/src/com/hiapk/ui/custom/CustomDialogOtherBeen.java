package com.hiapk.ui.custom;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;

public class CustomDialogOtherBeen {
	private Context context;
	private SharedPrefrenceDataWidget sharedDatawidget;
	private SharedPrefrenceData sharedData;

	public CustomDialogOtherBeen(Context context) {
		this.context = context;
		sharedDatawidget = new SharedPrefrenceDataWidget(context);
		sharedData = SpearheadApplication.getInstance().getsharedData();
	}

	/**
	 * 设置的本月已用流量超过包月流量
	 * 
	 * @return
	 */
	public void dialogHasUsedLongTooMuch() {
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle(R.string.weibosdk_attention)
				.setMessage("您设置的本月已用流量超过包月流量！")
				// .setView(textEntryView)
				.setPositiveButton("确定", null).create();
		dayWarning.show();
		Button btn_cancel = (Button) dayWarning
				.findViewById(R.id.positiveButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PrefrenceStaticOperator.resetHasWarning(context);
				dayWarning.dismiss();
			}
		});

	}

	public void dialogConfirmClearData() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle(R.string.weibosdk_attention)
				.setMessage("该操作将清除所有历史数据，您确定要继续吗？")
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTaskonClearSQL().execute(context);
				monthSetAlert.dismiss();
			}
		});
		Button btn_cancel = (Button) monthSetAlert
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthSetAlert.dismiss();
			}
		});

	}

	public void dialogClearDataFail() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("抱歉").setMessage("清空数据失败请重试")
				.setPositiveButton("确定", null).create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTaskonClearSQL().execute(context);
				monthSetAlert.dismiss();
			}
		});
	}

	public void dialogisHasknowFirewallRuleSave() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle(R.string.caution)
				.setMessage(R.string.tip_has_know_firewall_rule_save)
				.setPositiveButton(R.string.has_know, null).create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				monthSetAlert.dismiss();
				sharedData.setisHasknowFireWallSave(true);
			}
		});
	}

	public void dialogisKnowShakeToSwitch() {
		String tip = context.getResources().getString(
				R.string.tip_know_shake_to_switch);
		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle(R.string.caution).setMessage(tip)
				.setPositiveButton(R.string.has_know, null).create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				monthSetAlert.dismiss();
				sharedData.setIsKnowShakeToSwitch(true);
			}
		});
	}

	CustomDialog customProgressDialog;

	private class AsyncTaskonClearSQL extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// mydialog = ProgressDialog
			// .show(context, "请稍等...", "正在清空数据...", true);
			CustomProgressDialogBeen customPDBeen = new CustomProgressDialogBeen(
					context);
			customProgressDialog = customPDBeen.progressDialog();
			customProgressDialog.setCancelable(false);
			customProgressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Context... params) {
			int timetap = 0;
			while (!SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					return 1;
				}
			}
			while (!SQLStatic.setSQLUidOnUsed(true)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					SQLStatic.setSQLUidOnUsed(false);
					return 1;
				}
			}
			while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timetap++;
				if (timetap > 10) {
					publishProgress(1);
					SQLStatic.setSQLTotalOnUsed(false);
					SQLStatic.setSQLUidOnUsed(false);
					SQLStatic.setSQLUidTotalOnUsed(false);
					return 1;
				}
			}

			sharedDatawidget.setSQLinited(false);
			// 删除数据库
			params[0].deleteDatabase("SQLTotal.db");
			params[0].deleteDatabase("SQLUid.db");
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
				TrafficManager.clearUidtraff(context, uids[i]);
			}
			SQLHelperInitSQL sqlhelperInit = new SQLHelperInitSQL(context);
			sqlhelperInit.initSQL(params[0], uids, packagenames);
			while (SQLStatic.getIsInit(context) == false) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			return 0;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			if (values[0] == 1) {
				customProgressDialog.dismiss();
				dialogClearDataFail();
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 0) {
				TrafficManager.wifi_month_data = new long[64];
				TrafficManager.wifi_month_data_before = new long[64];
				TrafficManager.mobile_month_data = new long[64];
				TrafficManager.mobile_month_data_before = new long[64];
				TrafficManager.mobile_month_use = 1;
				sharedData.setMonthHasUsedStack(0);
				sharedData.setTodayMobileDataLong(0);
				SQLStatic.uiddataToday = null;
				SQLStatic.uiddataWeek = null;
				SQLStatic.uiddataMonth = null;
				SetText.resetWidgetAndNotify(context);
				SQLStatic.setSQLTotalOnUsed(false);
				SQLStatic.setSQLUidOnUsed(false);
				SQLStatic.setSQLUidTotalOnUsed(false);
				sharedDatawidget.setSQLinited(true);
				customProgressDialog.dismiss();
				Toast.makeText(context, "清除成功！", Toast.LENGTH_SHORT).show();
			}

		}
	}
}
