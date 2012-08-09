package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.widget.SetText;

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
		// 设置闹钟与数据库操作

		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		if (SQLStatic.getIsInit(context)) {
			// 网络状态变化时
			if (intent.getAction().equals(
					"android.net.conn.CONNECTIVITY_CHANGE")) {
				// if (SQLStatic.TableWiFiOrG23 == "") {
				// SQLStatic.initTablemobileAndwifi(context);
				// }
				// 记录之前的
				alset.StartAlarm(context);
				// 更新小部件
				if (sharedDatawidget.isWidGet14Open()) {
					Intent intentNetUpdate = new Intent();
					intentNetUpdate.setAction(APPWIDGET_UPDATE);
					context.sendBroadcast(intentNetUpdate);
					SetText.resetWidgetAndNotify(context);
				}
				SQLStatic.initTablemobileAndwifi(context);
				if (SQLStatic.TableWiFiOrG23 != "") {
					alset.StartWidgetAlarm(context);
					// // 启动闹钟
					// alset.StartAlarm(context);
					showLog("何种方式连线" + SQLStatic.TableWiFiOrG23);
				} else {
					// alset.StopWidgetAlarm(context);
					showLog("无可用网络");
					// alset.StopAlarm(context);
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
	 * 延时判断，进行取消网络检测操作，可省电
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
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publishProgress((long) 0);
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
			alset.StartAlarm(context);
		}

		@Override
		protected void onPostExecute(Long result) {
			SQLStatic.initTablemobileAndwifi(context);
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.TableWiFiOrG23Before = "";
				alset.StopAlarm(context);
			}
			SQLStatic.ConnectSleepWaiting = false;
		}
	}

	private void showLog(String string) {
		// Log.d("ConnectivityChange", string);
	}
}
