package com.hiapk.ui.custom;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.logs.Logs;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.sqlhelper.total.SQLHelperInitSQL;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;

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
	 * 通知栏广告需要获取root权限-----未被使用
	 */
	public void dialogNotificationNeedRoot() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("注意：").setMessage("广告检测功能需要Root权限！")
				.setPositiveButton("确定", null).create();
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// SharedPrefrenceDataOnUpdate sharedUP = new
				// SharedPrefrenceDataOnUpdate(
				// context);
				// sharedUP.setAdbRootAllow(true);
				// NotificationInfo.startRootcomand(context);
				monthSetAlert.dismiss();
			}
		});
	}

	/**
	 * 通知栏检测时获取root权限失败
	 */
	public void dialogNotificationRootFail() {

		final CustomDialog scanNotificationRootFail = new CustomDialog.Builder(
				context)
				.setTitle("操作失败")
				.setMessage(
						"获取Root权限失败，可能原因：\n\n1.您的设备未破解\n2.尝试获取Root失败\n建议退出后重新尝试。")
				.setPositiveButton("重试", null).setNegativeButton("取消", null)
				.create();
		scanNotificationRootFail.show();
		FireWallActivity.isInScene = false;
//		FireWallActivity.isNotifFail = true;
		Button btn_ok = (Button) scanNotificationRootFail
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FireWallActivity.isInScene = false;
//				FireWallActivity.isNotifFail = false;
				// NotificationInfo.callbyonResume = false;
				NotificationInfo.isgettingdata = false;
				NotificationInfo.notificationRes = new StringBuilder();
				NotificationInfo.hasdata = false;
				SpearheadActivity.switchScene(0);
				SpearheadActivity.switchScene(1);
				scanNotificationRootFail.dismiss();
			}
		});
		Button btn_cancel = (Button) scanNotificationRootFail
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FireWallActivity.isInScene = true;
//				FireWallActivity.isNotifFail = false;
				sharedData.setFireWallType(0);
				NotificationInfo.callbyonCancel = true;
				NotificationInfo.notificationRes = new StringBuilder();
				SpearheadActivity.switchScene(0);
				SpearheadActivity.switchScene(1);
				scanNotificationRootFail.dismiss();
			}
		});
		scanNotificationRootFail.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				FireWallActivity.isInScene = true;
				sharedData.setFireWallType(0);
				NotificationInfo.notificationRes = new StringBuilder();
				NotificationInfo.callbyonCancel = true;
				SpearheadActivity.switchScene(0);
				SpearheadActivity.switchScene(1);
				scanNotificationRootFail.dismiss();
			}
		});
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

	public void dialogOpenFireWallFail() {
		final CustomDialog monthSetAlert;
		monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("未能开启防火墙")
				.setTv_size(18)
				.setMessage(
						"由于安卓系统的限制,只有获得最高权限(称为\"Root\")的机器才能使用防火墙功能。\n当前操作失败,可能原因有:\n\n1.您拒绝了Root权限 \n2.系统原因,防火墙应用失败 \n3.部分机型不支持防火墙操作")
				.setPositiveButton("确定", null).create();
		FireWallActivity.isRootFail = true;
		monthSetAlert.show();
		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FireWallActivity.isRootFail = false;
				monthSetAlert.dismiss();
			}
		});
		monthSetAlert.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				FireWallActivity.isRootFail = false;
				monthSetAlert.dismiss();
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
		String tip = context.getResources().getString(R.string.tip_know_shake_to_switch);
		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle(R.string.caution)
				.setMessage(tip)
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
