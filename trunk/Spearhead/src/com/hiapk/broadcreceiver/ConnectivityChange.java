package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityChange extends BroadcastReceiver {
	String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
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

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("ConnectivityChange", string);
	}
}
