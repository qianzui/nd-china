package com.hiapk.rebootandstartaction;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class OnUninstallitself {
	AlarmSet alset = new AlarmSet();
	Context context;
	boolean isNotifyOpen = true;
	boolean isFloatOpen = false;
	boolean isWidget1X4Open = true;

	public void unInstallAction(Context context) {
		this.context = context;
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		SQLStatic.initTablemobileAndwifi(context);
		alset.StartAlarm(context);
		// …Ë÷√IsInit–≈œ¢
		boolean isInit = SQLStatic.getIsInit(context);
		isNotifyOpen = sharedDatawidget.isNotifyOpen();
		isFloatOpen = sharedDatawidget.isFloatOpen();
		isWidget1X4Open = sharedDatawidget.isWidGet14Open();
		if (isInit) {
			if (TrafficManager.mobile_month_use == 1) {
				if (SQLStatic.TableWiFiOrG23 == "") {
					SQLHelperDataexe.initShowDataOnBroadCast(context);
				} else
					new AsyncTaskonBoot().execute(context);
			} else {
				// showLog("isNotifyOpen"+isNotifyOpen);
				// showLog("isFloatOpen"+isFloatOpen);
				// alset.StartAlarm(context);
				if (isFloatOpen) {
					context.startService(new Intent("com.hiapk.server"));
				} else {
					context.stopService(new Intent("com.hiapk.server"));
				}
				if (isNotifyOpen || isWidget1X4Open) {
					alset.StartWidgetAlarm(context);
				} else {
					alset.StopWidgetAlarm(context);
				}
			}
			// new AsyncTaskonReinstallItself().execute(context);
			// alset.StopAlarm(context);
		}
	}

	private class AsyncTaskonBoot extends AsyncTask<Context, Long, Long> {

		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while (TrafficManager.mobile_month_use == 1) {
				timetap++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 10) {
					break;
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			if (isFloatOpen) {
				context.startService(new Intent("com.hiapk.server"));
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
			if (isNotifyOpen || isWidget1X4Open) {
				alset.StartWidgetAlarm(context);
			} else {
				alset.StopWidgetAlarm(context);
			}
		}
	}
	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// // Log.d("Onreinstall", string);
	// }
}
