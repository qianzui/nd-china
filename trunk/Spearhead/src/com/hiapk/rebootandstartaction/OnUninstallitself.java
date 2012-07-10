package com.hiapk.rebootandstartaction;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;

import android.content.Context;
import android.content.Intent;

public class OnUninstallitself {
	AlarmSet alset = new AlarmSet();
	Context context;

	public void unInstallAction(Context context) {
		this.context = context;
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		SQLStatic.initTablemobileAndwifi(context);
		// …Ë÷√IsInit–≈œ¢
		boolean isInit = SQLStatic.getIsInit(context);
		if (isInit) {
			boolean isNotifyOpen = sharedDatawidget.isNotifyOpen();
			boolean isFloatOpen = sharedDatawidget.isFloatOpen();
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

	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// // Log.d("Onreinstall", string);
	// }
}
