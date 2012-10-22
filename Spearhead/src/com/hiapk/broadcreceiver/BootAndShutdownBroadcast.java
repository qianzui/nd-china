package com.hiapk.broadcreceiver;

import com.hiapk.control.bootandclose.Onshutdown;
import com.hiapk.control.bootandclose.Onsysreboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootAndShutdownBroadcast extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// �������������ݿ����
		// ʶ�𵽿����ź�
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Onsysreboot sysreboot = new Onsysreboot();
			sysreboot.onsysreboot(context);
			// showLog("getbootaction");
		}
		// ǿ�������ź�
//		if (intent.getAction()
//				.equals("android.intent.action.SIM_STATE_CHANGED")) {
//			Onsysreboot sysreboot = new Onsysreboot();
//			sysreboot.onsysreboot(context);
//			// showLog("getbootaction");
//		}
		// ʶ�𵽹ػ��ź�
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			Onshutdown sysshutdown = new Onshutdown();
			sysshutdown.onsysshutdown(context);
			// showLog("getshutdownaction");
		}

	}

}
