package com.hiapk.broadcreceiver;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityChange extends BroadcastReceiver {
	String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// �������������ݿ����
		AlarmSet alset = new AlarmSet();
		TrafficManager trafficManager = new TrafficManager();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		if (sqlhelperTotal.getIsInit(context)) {
			// ����״̬�仯ʱ
			if (intent.getAction().equals(
					"android.net.conn.CONNECTIVITY_CHANGE")) {
				// ����С����
				if (sharedData.isWidGet14Open()) {
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
					// ��������
					alset.StartAlarm(context);
					NetworkInfo info = connec.getActiveNetworkInfo();
					String typeName = info.getTypeName(); // mobile@wifi
					if (typeName.equals("WIFI")) {
						// ��������uid�Զ���¼���ܲ��޸���������
						// trafficManager.statsTotalTraffic(context, false);
						// sqlhelperTotal.RecordTotalwritestats(context, false);
						sqlhelperUid.RecordUidwritestats(context, false);
						SQLHelperTotal.TableWiFiOrG23 = "wifi";
					}
					if (typeName.equals("mobile")) {
						// ��������uid�Զ���¼���ܲ��޸���������
						// trafficManager.statsTotalTraffic(context, false);
						// sqlhelperTotal.RecordTotalwritestats(context, false);
						sqlhelperUid.RecordUidwritestats(context, false);
						SQLHelperTotal.TableWiFiOrG23 = "mobile";
					}
					// showLog("���ַ�ʽ����" + typeName);
				} else {
					// trafficManager.statsTotalTraffic(context, false);
					// sqlhelperTotal.RecordTotalwritestats(context, false);
					sqlhelperUid.RecordUidwritestats(context, false);
					SQLHelperTotal.TableWiFiOrG23 = "";
					showLog("�޿�������");
					alset.StopAlarm(context);
				}
			}
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
