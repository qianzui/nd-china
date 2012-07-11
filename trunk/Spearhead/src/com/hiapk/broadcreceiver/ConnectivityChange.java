package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class ConnectivityChange extends BroadcastReceiver {
	private String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	private String BROADCAST_TRAFF = "com.hiapk.traffwidget";
	private int WIFI_STATE_DISABLING = 0x00000000;
	private int WIFI_STATE_ENABLING = 0x00000002;

	@Override
	public void onReceive(Context context, Intent intent) {
		// �������������ݿ����
		AlarmSet alset = new AlarmSet();
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		if (SQLStatic.getIsInit(context)) {
			// ����״̬�仯ʱ
			if (intent.getAction().equals(
					"android.net.conn.CONNECTIVITY_CHANGE")) {
				if (SQLStatic.TableWiFiOrG23 == "") {
					SQLStatic.initTablemobileAndwifi(context);
				}
				// ��¼֮ǰ��
				alset.StartAlarm(context);
				// ����С����
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
					// // ��������
					// alset.StartAlarm(context);
					if (sharedDatawidget.isNotifyOpen()) {
						alset.StartWidgetAlarm(context);
					}
					showLog("���ַ�ʽ����" + SQLStatic.TableWiFiOrG23);
				} else {

					if (sharedDatawidget.isNotifyOpen()) {
						alset.StartWidgetAlarm(context);
					}
					showLog("�޿�������");
					alset.StopAlarm(context);
				}
			}
		}
		if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
			Intent intentNetUpdate = new Intent();
			intentNetUpdate.setAction(APPWIDGET_UPDATE);
			context.sendBroadcast(intentNetUpdate);
			WifiManager wfm_on_off;
			wfm_on_off = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (wfm_on_off.getWifiState() != WIFI_STATE_DISABLING
					&& wfm_on_off.getWifiState() != WIFI_STATE_ENABLING) {
				SQLStatic.initTablemobileAndwifi(context);
			}
		}

	}

	private void showLog(String string) {
		// Log.d("ConnectivityChange", string);
	}
}
