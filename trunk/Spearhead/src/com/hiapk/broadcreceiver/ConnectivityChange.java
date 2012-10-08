package com.hiapk.broadcreceiver;

import com.hiapk.control.widget.SetText;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;

public class ConnectivityChange extends BroadcastReceiver {
	private String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	private int WIFI_STATE_DISABLING = 0x00000000;
	private int WIFI_STATE_ENABLING = 0x00000002;
	private Context context;
	private AlarmSet alset = new AlarmSet();

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		// �������������ݿ����

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
				}
				SQLStatic.initTablemobileAndwifi(context);
				if (SQLStatic.TableWiFiOrG23 != "") {
					alset.StartWidgetAlarm(context);
					// // ��������
					// alset.StartAlarm(context);
					showLog("���ַ�ʽ����" + SQLStatic.TableWiFiOrG23);
				} else {
					showLog("�޿�������");
					if (SQLStatic.ConnectSleepWaiting == false) {
						new AsyncTaskonWaitingForStopAll().execute(context);
					}

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

	/**
	 * ��ʱ�жϣ�����ȡ���������������ʡ��
	 * 
	 * @author Administrator
	 * 
	 */
	private class AsyncTaskonWaitingForStopAll extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			SQLStatic.ConnectSleepWaiting = true;
		}

		@Override
		protected Long doInBackground(Context... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publishProgress((long) 0);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publishProgress((long) 1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Long... values) {
			super.onProgressUpdate(values);
			if (values[0] == 0) {
				alset.StartAlarm(context);
			}
			if (values[0] == 1) {
				SQLStatic.initTablemobileAndwifi(context);
				if (SQLStatic.TableWiFiOrG23 == "") {
					SQLStatic.TableWiFiOrG23Before = "";
					alset.StopAlarm(context);
					SQLStatic.isTotalAlarmRecording = false;
					SQLStatic.isUidAlarmRecording = false;
					SetText.resetWidgetAndNotify(context);
				} else {
					SetText.resetWidgetAndNotify(context);
				}
			}
		}

		@Override
		protected void onPostExecute(Long result) {
			if (SQLStatic.TableWiFiOrG23 == "") {
				if (!SQLStatic.isAppOpened) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
			}
			SQLStatic.ConnectSleepWaiting = false;
		}
	}

	private void showLog(String string) {
		// Log.d("ConnectivityChange", string);
	}
}
