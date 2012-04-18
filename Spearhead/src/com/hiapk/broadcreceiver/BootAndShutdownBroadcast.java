package com.hiapk.broadcreceiver;

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
			// 初始化网络信号
			sqlhelperTotal.initTablemobileAndwifi(context);
			// 查看数据库是否已初始化

			if (sqlhelperTotal.getIsInit(context)) {
				// 启动闹钟
				alset.StartAlarm(context);
				// 开启总流量与uid自动记录功能并进行首次记录
				sqlhelperTotal.RecordTotalwritestats(context, false);
				sqlhelperUid.RecordUidwritestats(context, false);
				showLog(sqlhelperTotal.gettime() + "onboot the system");
			}
		}
		// 识别到关机信号
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			// 关闭wifi自动记录并记录当前数据
			alset.StopAlarm(context);
			if (sqlhelperTotal.getIsInit(context)) {
				if (SQLHelperTotal.TableWiFiOrG23 != "") {
					sqlhelperTotal.RecordTotalwritestats(context, false);
					sqlhelperUid.RecordUidwritestats(context, false);
				}
			}
			showLog(sqlhelperTotal.gettime() + "shutdown the system");
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}
}
