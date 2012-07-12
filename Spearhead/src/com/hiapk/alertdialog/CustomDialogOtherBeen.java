package com.hiapk.alertdialog;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.progressdialog.CustomProgressDialog;
import com.hiapk.spearhead.R;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.widget.SetText;

public class CustomDialogOtherBeen {
	Context context;
	SharedPrefrenceDataWidget sharedDatawidget;
	SharedPrefrenceData sharedData;

	public CustomDialogOtherBeen(Context context) {
		this.context = context;
		sharedDatawidget = new SharedPrefrenceDataWidget(context);
		sharedData = new SharedPrefrenceData(context);
	}

	/**
	 * 设置的本月已用流量超过包月流量
	 * 
	 * @return
	 */
	public void dialogHasUsedLongTooMuch() {
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle("注意！").setMessage("您设置的本月已用流量超过包月流量！")
				.setwindowHeight(0.35)
				// .setView(textEntryView)
				.setPositiveButton("确定", null).create();
		dayWarning.show();
		Button btn_cancel = (Button) dayWarning
				.findViewById(R.id.positiveButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PrefrenceOperatorUnit.resetHasWarning(context);
				dayWarning.dismiss();
			}
		});

	}

	public void dialogOpenFireWallFail() {
		final CustomDialog monthSetAlert;
		monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("未能开启防火墙")
				.setTv_size(18)
				.setwindowHeight(0.7)
				.setMessage(
						"由于安卓系统的限制,只有获得最高权限(称为\"Root\")的机器才能使用防火墙功能。\n当前操作失败,可能原因有:\n\n1.您拒绝了Root权限 \n2.系统原因,防火墙应用失败 \n3.部分机型不支持防火墙操作")
				.setPositiveButton("确定", null).create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				monthSetAlert.dismiss();
			}
		});
	}

	public void dialogConfirmClearData() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("注意！").setwindowHeight((double) 0.35)
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
				// TODO Auto-generated method stub
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

	CustomProgressDialog customdialog;

	private class AsyncTaskonClearSQL extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// mydialog = ProgressDialog
			// .show(context, "请稍等...", "正在清空数据...", true);
			CustomProgressDialog customProgressDialog = new CustomProgressDialog(
					context);

			customdialog = customProgressDialog.createDialog(context);
			customdialog.setCancelable(false);
			customProgressDialog.setTitile("清除历史记录...");
			customProgressDialog.setMessage("清除中，请稍候。。。");
			customdialog.show();
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Context... params) {
			int timetap = 0;
			while (!SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
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
			}
			SQLHelperInitSQL sqlhelperInit = new SQLHelperInitSQL(context);
			sqlhelperInit.initSQL(params[0], uids, packagenames);
			while (SQLStatic.getIsInit(context) == false) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			return 0;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			if (values[0] == 1) {
				customdialog.dismiss();
				dialogClearDataFail();
			}
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if (result == 0) {
				TrafficManager.mobile_month_use = 1;
				TrafficManager.wifi_month_data = new long[64];
				TrafficManager.wifi_month_data_before = new long[64];
				TrafficManager.mobile_month_data = new long[64];
				TrafficManager.mobile_month_data_before = new long[64];
				sharedData.setMonthHasUsedStack(0);
				sharedData.setTodayMobileDataLong(0);
				SetText.resetWidgetAndNotify(context);
				SQLStatic.setSQLTotalOnUsed(false);
				SQLStatic.setSQLUidOnUsed(false);
				SQLStatic.setSQLUidTotalOnUsed(false);
				sharedDatawidget.setSQLinited(true);
				customdialog.dismiss();
			}

		}
	}
}
