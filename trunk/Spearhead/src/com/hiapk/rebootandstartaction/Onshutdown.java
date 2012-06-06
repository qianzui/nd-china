package com.hiapk.rebootandstartaction;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;

public class Onshutdown {
	public void onsysshutdown(Context context) {
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		AlarmSet alset = new AlarmSet();
		// 查看数据库是否已初始化
		// 关闭wifi自动记录并记录当前数据
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				alset.StartAlarm(context);
				// trafficManager.statsTotalTraffic(context, true,
				// SQLHelperTotal.TableWiFiOrG23);
				// // sqlhelperTotal.RecordTotalwritestats(context, false);
				// trafficManager.statsUidTraffic(context, true,
				// SQLHelperTotal.TableWiFiOrG23);
			}
		}
		alset.StopAlarm(context);
		context.stopService(new Intent("com.hiapk.server"));
		alset.StopWidgetAlarm(context);
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
//		Log.d("Onshutdown", string);
	}
}
