package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityChange extends BroadcastReceiver {
	String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		// 设置闹钟与数据库操作
		AlarmSet alset = new AlarmSet();
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		if (SQLStatic.getIsInit(context)) {
			// 网络状态变化时
			if (intent.getAction().equals(
					"android.net.conn.CONNECTIVITY_CHANGE")) {
				if (SQLStatic.TableWiFiOrG23 == "") {
					SQLStatic.initTablemobileAndwifi(context);
				}
				// 记录之前的
				alset.StartAlarm(context);
				// 更新小部件
				if (sharedDatawidget.isWidGet14Open()) {
					Intent intentNetUpdate = new Intent();
					intentNetUpdate.setAction(APPWIDGET_UPDATE);
					context.sendBroadcast(intentNetUpdate);
					Intent intentTextUpdate = new Intent();
					intentTextUpdate.setAction(BROADCAST_TRAFF);
					context.sendBroadcast(intentTextUpdate);
				}
				SQLStatic.initTablemobileAndwifi(context);
				if (SQLStatic.TableWiFiOrG23 != "") {
					// // 启动闹钟
					// alset.StartAlarm(context);
					if (sharedDatawidget.isNotifyOpen()) {
						alset.StartWidgetAlarm(context);
					}
					showLog("何种方式连线" + SQLStatic.TableWiFiOrG23);
				} else {

					if (sharedDatawidget.isNotifyOpen()) {
						alset.StartWidgetAlarm(context);
					}
					showLog("无可用网络");
					alset.StopAlarm(context);
				}
			}
		}

	}

	private void showLog(String string) {
		// Log.d("ConnectivityChange", string);
	}
}
