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
	 * ���õı�����������������������
	 * 
	 * @return
	 */
	public void dialogHasUsedLongTooMuch() {
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle("ע�⣡").setMessage("�����õı�������������������������")
				.setwindowHeight(0.35)
				// .setView(textEntryView)
				.setPositiveButton("ȷ��", null).create();
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
				.setTitle("δ�ܿ�������ǽ")
				.setTv_size(18)
				.setwindowHeight(0.7)
				.setMessage(
						"���ڰ�׿ϵͳ������,ֻ�л�����Ȩ��(��Ϊ\"Root\")�Ļ�������ʹ�÷���ǽ���ܡ�\n��ǰ����ʧ��,����ԭ����:\n\n1.���ܾ���RootȨ�� \n2.ϵͳԭ��,����ǽӦ��ʧ�� \n3.���ֻ��Ͳ�֧�ַ���ǽ����")
				.setPositiveButton("ȷ��", null).create();
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
				.setTitle("ע�⣡").setwindowHeight((double) 0.35)
				.setMessage("�ò��������������ʷ���ݣ���ȷ��Ҫ������")
				.setPositiveButton("ȷ��", null).setNegativeButton("ȡ��", null)
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
				.setTitle("��Ǹ").setMessage("�������ʧ��������")
				.setPositiveButton("ȷ��", null).create();
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
			// .show(context, "���Ե�...", "�����������...", true);
			CustomProgressDialog customProgressDialog = new CustomProgressDialog(
					context);

			customdialog = customProgressDialog.createDialog(context);
			customdialog.setCancelable(false);
			customProgressDialog.setTitile("�����ʷ��¼...");
			customProgressDialog.setMessage("����У����Ժ򡣡���");
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
			// ɾ�����ݿ�
			params[0].deleteDatabase("SQLTotal.db");
			params[0].deleteDatabase("SQLUid.db");
			params[0].deleteDatabase("SQLTotaldata.db");
			// ���³�ʼ�����ݿ�
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
