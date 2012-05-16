package com.hiapk.broadcreceiver;

import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootAndShutdownBroadcast extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// 设置闹钟与数据库操作
		AlarmSet alset = new AlarmSet();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		// 设置IsInit信息
		sqlhelperTotal.getIsInit(context);

		// 识别到开机信号
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			// 开启防火墙
			Block.applyIptablesRules(context, false);
			// 初始化网络信号
			sqlhelperTotal.initTablemobileAndwifi(context);
			// 查看数据库是否已初始化

			if (sqlhelperTotal.getIsInit(context)) {
				SharedPrefrenceData sharedData = new SharedPrefrenceData(
						context);
				;
				// 启动闹钟
				alset.StartAlarm(context);
				if (sharedData.isNotifyOpen()) {
					alset.StartWidgetAlarm(context);
				}
				// 开启总流量与uid自动记录功能并进行首次记录
				if (SQLHelperTotal.isSQLTotalOnUsed != true) {
					SQLHelperTotal.isSQLTotalOnUsed = true;
					sqlhelperTotal.RecordTotalwritestats(context, false);
					SQLHelperTotal.isSQLTotalOnUsed = false;
				}
				if (SQLHelperTotal.isSQLUidOnUsed != true) {
					SQLHelperTotal.isSQLUidOnUsed = true;
					sqlhelperUid.RecordUidwritestats(context, false);
					SQLHelperTotal.isSQLUidOnUsed = false;
				}
				showLog(sqlhelperTotal.gettime() + "onboot the system");
			}
		}
		// 识别到关机信号
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			// 关闭wifi自动记录并记录当前数据
			alset.StopAlarm(context);
			if (sqlhelperTotal.getIsInit(context)) {
				if (SQLHelperTotal.TableWiFiOrG23 != "") {
					if (SQLHelperTotal.isSQLTotalOnUsed != true) {
						SQLHelperTotal.isSQLTotalOnUsed = true;
						sqlhelperTotal.RecordTotalwritestats(context, false);
						SQLHelperTotal.isSQLTotalOnUsed = false;
					}
					if (SQLHelperTotal.isSQLUidOnUsed != true) {
						SQLHelperTotal.isSQLUidOnUsed = true;
						sqlhelperUid.RecordUidwritestats(context, false);
						SQLHelperTotal.isSQLUidOnUsed = false;
					}
				}
			}
			showLog(sqlhelperTotal.gettime() + "shutdown the system");
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
