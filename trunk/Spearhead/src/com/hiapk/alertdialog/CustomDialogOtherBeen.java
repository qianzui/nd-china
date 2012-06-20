package com.hiapk.alertdialog;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLStatic;

public class CustomDialogOtherBeen {
	Context context;
	SharedPrefrenceData sharedData;

	public CustomDialogOtherBeen(Context context) {
		this.context = context;
		sharedData = new SharedPrefrenceData(context);
	}

	public void dialogConfirmClearData() {

		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("注意！").setMessage("该操作将清除所有历史数据，您确定要继续吗？")
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

	ProgressDialog mydialog;

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
			while (!SQLStatic.setSQLIndexOnUsed(true)) {
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
					SQLStatic.setSQLIndexOnUsed(false);
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
				dialogClearDataFail();
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
}
