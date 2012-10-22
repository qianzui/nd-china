package com.hiapk.broadcreceiver;

import com.hiapk.control.bootandclose.Onshutdown;
import com.hiapk.control.bootandclose.Onsysreboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootAndShutdownBroadcast extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// 设置闹钟与数据库操作
		// 识别到开机信号
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Onsysreboot sysreboot = new Onsysreboot();
			sysreboot.onsysreboot(context);
			// showLog("getbootaction");
		}
		// 强化开机信号
//		if (intent.getAction()
//				.equals("android.intent.action.SIM_STATE_CHANGED")) {
//			Onsysreboot sysreboot = new Onsysreboot();
//			sysreboot.onsysreboot(context);
//			// showLog("getbootaction");
//		}
		// 识别到关机信号
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			Onshutdown sysshutdown = new Onshutdown();
			sysshutdown.onsysshutdown(context);
			// showLog("getshutdownaction");
		}

	}

}
