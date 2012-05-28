package com.hiapk.rebootandstartaction;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;

public class Onsysreboot {
	public void onsysreboot(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		// ��������ǽ
		Block.applyIptablesRules(context, false);
		// ��ʼ�������ź�
		sqlhelperTotal.initTablemobileAndwifi(context, true);
		AlarmSet alset = new AlarmSet();
		// �鿴���ݿ��Ƿ��ѳ�ʼ��
		if (sqlhelperTotal.getIsInit(context)) {
			boolean isNotifyOpen = sharedData.isNotifyOpen();
			boolean isFloatOpen = sharedData.isFloatOpen();
			// showLog("isNotifyOpen"+isNotifyOpen);
			// showLog("isFloatOpen"+isFloatOpen);
			alset.StartAlarm(context);
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
		// ��������
		// ������������uid�Զ���¼���ܲ������״μ�¼(startAlarm�Ѿ���¼)
		// if (SQLHelperTotal.isSQLTotalOnUsed != true) {
		// SQLHelperTotal.isSQLTotalOnUsed = true;
		// trafficManager.statsTotalTraffic(context, true);
		// // sqlhelperTotal.RecordTotalwritestats(context, false);
		// SQLHelperTotal.isSQLTotalOnUsed = false;
		// }
		// if (SQLHelperTotal.isSQLUidOnUsed != true) {
		// SQLHelperTotal.isSQLUidOnUsed = true;
		// sqlhelperUid.RecordUidwritestats(context, false);
		// SQLHelperTotal.isSQLUidOnUsed = false;
		// }
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Onsysreboot", string);
	}
}
