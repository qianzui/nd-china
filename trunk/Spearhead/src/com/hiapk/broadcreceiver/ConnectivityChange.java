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
				// �ж�����
				ConnectivityManager connec = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connec.getActiveNetworkInfo() != null) {
					// // ��������
					// alset.StartAlarm(context);
					if (sharedDatawidget.isNotifyOpen()) {
						alset.StartWidgetAlarm(context);
					}
					NetworkInfo info = connec.getActiveNetworkInfo();
					String typeName = info.getTypeName(); // mobile@wifi
					if (typeName.equals("WIFI")) {
						SQLStatic.TableWiFiOrG23 = "wifi";
					}
					if (typeName.equals("mobile")) {
						SQLStatic.TableWiFiOrG23 = "mobile";
					}
					showLog("���ַ�ʽ����" + typeName);
				} else {

					if (sharedDatawidget.isNotifyOpen()) {
						alset.StartWidgetAlarm(context);
					}
					SQLStatic.TableWiFiOrG23 = "";
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
