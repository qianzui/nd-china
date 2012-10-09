package com.hiapk.broadcreceiver;

import com.hiapk.logs.Logs;
import com.hiapk.util.SQLStatic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class DateChangeBroadcastBefore extends BroadcastReceiver {
	private Context context;
	private AlarmSet alset = new AlarmSet();
	private String TAG = "DateChangeBef";

	@Override
	public void onReceive(Context context, Intent intent) {
		Logs.d(TAG, "beforeDate");
		this.context = context;
		alset.StartAlarm(context);
		if (SQLStatic.ConnectSleepWaiting == false) {
			new AsyncTaskonWaitingDayChange().execute(context);
		}
	}

	/**
	 * 延时判断，进行取消网络检测操作，可省电
	 * 
	 * @author Administrator
	 * 
	 */
	private class AsyncTaskonWaitingDayChange extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			SQLStatic.ConnectSleepWaiting = true;
		}

		@Override
		protected Long doInBackground(Context... params) {
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			SQLStatic.initTablemobileAndwifi(context);
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.TableWiFiOrG23Before = "";
				alset.StopAlarm(context);
				SQLStatic.isTotalAlarmRecording = false;
				SQLStatic.isUidAlarmRecording = false;
			}
			SQLStatic.ConnectSleepWaiting = false;
		}
	}

}
