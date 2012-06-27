package com.hiapk.rebootandstartaction;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLStatic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class OnUninstallitself {
	AlarmSet alset = new AlarmSet();
	Context context;

	public void unInstallAction(Context context) {
		this.context = context;
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		sqlhelperTotal.initTablemobileAndwifi(context, true);
		// …Ë÷√IsInit–≈œ¢
		boolean isInit = sqlhelperTotal.getIsInit(context);
		if (isInit) {
			boolean isNotifyOpen = sharedData.isNotifyOpen();
			boolean isFloatOpen = sharedData.isFloatOpen();
			// showLog("isNotifyOpen"+isNotifyOpen);
			// showLog("isFloatOpen"+isFloatOpen);
			// alset.StartAlarm(context);
			if (isFloatOpen) {
				context.startService(new Intent("com.hiapk.server"));
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
			if (isNotifyOpen == true) {
				alset.StartWidgetAlarm(context);
			} else {
				alset.StopWidgetAlarm(context);
			}
			// new AsyncTaskonReinstallItself().execute(context);
			alset.StartAlarm(context);
			// alset.StopAlarm(context);
		}
	}

	// private class AsyncTaskonReinstallItself extends
	// AsyncTask<Context, Long, Long> {
	//
	// @Override
	// protected Long doInBackground(Context... params) {
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Long result) {
	// // TODO Auto-generated method stub
	// alset.StartAlarm(context);
	// }
	// }

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("Onreinstall", string);
	}
}
