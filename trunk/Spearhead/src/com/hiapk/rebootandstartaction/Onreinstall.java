package com.hiapk.rebootandstartaction;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Onreinstall {
	public void reInstallAction(Context context) {
		AlarmSet alset = new AlarmSet();
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		sqlhelperTotal.initTablemobileAndwifi(context);
		// ����IsInit��Ϣ
		sqlhelperTotal.getIsInit(context);
		boolean isNotifyOpen = sharedData.isNotifyOpen();
		boolean isFloatOpen = sharedData.isFloatOpen();
		showLog("isNotifyOpen"+isNotifyOpen);
		showLog("isFloatOpen"+isFloatOpen);
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

	}
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Onreinstall", string);
	}
}