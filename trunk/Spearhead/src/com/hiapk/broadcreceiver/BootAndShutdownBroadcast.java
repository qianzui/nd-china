package com.hiapk.broadcreceiver;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.rebootandstartaction.Onshutdown;
import com.hiapk.rebootandstartaction.Onsysreboot;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.System;
import android.util.Log;

public class BootAndShutdownBroadcast extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 设置闹钟与数据库操作
		// 识别到开机信号
		showLog("getaction");
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Onsysreboot sysreboot = new Onsysreboot();
			sysreboot.onsysreboot(context);
			showLog("getbootaction");
			// showLog("onboot the system");
		}
		// 识别到关机信号
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			Onshutdown sysshutdown = new Onshutdown();
			sysshutdown.onsysshutdown(context);
			// showLog("shutdown the system");
			showLog("getshutdownaction");
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
