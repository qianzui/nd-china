package com.hiapk.rebootandstartaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;

public class Onsysreboot {
	boolean isNotifyOpen = true;
	boolean isFloatOpen = false;
	boolean isWidget1X4Open = true;
	Context context;
	AlarmSet alset = new AlarmSet();

	public void onsysreboot(Context context) {
		this.context = context;
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		// 初始化网络信号
		SQLStatic.initTablemobileAndwifi(context, true);

		alset.StartAlarm(context);
		// 查看数据库是否已初始化
		if (SQLStatic.getIsInit(context)) {
			isNotifyOpen = sharedDatawidget.isNotifyOpen();
			isFloatOpen = sharedDatawidget.isFloatOpen();
			isWidget1X4Open = sharedDatawidget.isWidGet14Open();
			// showLog("isNotifyOpen"+isNotifyOpen);
			// showLog("isFloatOpen"+isFloatOpen);
			if ((TrafficManager.mobile_month_data[0] == 0)
					&& (TrafficManager.wifi_month_data[0] == 0)) {
				new AsyncTaskonBoot().execute(context);
			} else {
				if (isFloatOpen) {
					context.startService(new Intent("com.hiapk.server"));
				} else {
					context.stopService(new Intent("com.hiapk.server"));
				}
				if (isNotifyOpen || isWidget1X4Open) {
					alset.StartWidgetAlarm(context);
				} else {
					alset.StopWidgetAlarm(context);
				}
			}

			// Intent intentTextUpdate = new Intent();
			// intentTextUpdate.setAction(BROADCAST_TRAFF);
			// context.sendBroadcast(intentTextUpdate);
			// Toast.makeText(context, "qidong", Toast.LENGTH_SHORT);
			showLog("xitongqidong");
		}
		// 开启防火墙
		if (!iptableEmpty(context)) {
			Block.applyIptablesRules(context, false);
		}
		// 启动闹钟
		// 开启总流量与uid自动记录功能并进行首次记录(startAlarm已经记录)
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

	private class AsyncTaskonBoot extends AsyncTask<Context, Long, Long> {

		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while ((TrafficManager.mobile_month_data[0] == 0)
					&& (TrafficManager.wifi_month_data[0] == 0)) {
				timetap++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 15) {
					break;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			if (isFloatOpen) {
				context.startService(new Intent("com.hiapk.server"));
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
			if (isNotifyOpen || isWidget1X4Open) {
				alset.StartWidgetAlarm(context);
			} else {
				alset.StopWidgetAlarm(context);
			}
		}
	}

	private boolean iptableEmpty(Context context) {
		String PREFS_NAME = "DroidWallPrefs";
		String PREF_3G_UIDS = "AllowedUids3G";
		String PREF_WIFI_UIDS = "AllowedUidsWifi";
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final String savedUids_wifi = prefs.getString(PREF_WIFI_UIDS, "");
		final String savedUids_3g = prefs.getString(PREF_3G_UIDS, "");
		if ((savedUids_wifi == "") && savedUids_3g == "") {
			return true;
		} else {
			return false;
		}
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("Onsysreboot", string);
	}
}
