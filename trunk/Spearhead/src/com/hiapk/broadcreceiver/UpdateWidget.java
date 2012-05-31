package com.hiapk.broadcreceiver;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.hiapk.widget.ProgramNotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidget extends BroadcastReceiver {
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";
	Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		if (sharedData.isSQLinited() == true
				&& (sharedData.isNotifyOpen() == true || sharedData
						.isWidGet14Open() == true)) {
			if (sharedData.isNotifyOpen()) {
				ProgramNotify programNotify = new ProgramNotify();
				if (TrafficManager.mobile_month_data[0] == 0
						&& TrafficManager.wifi_month_data[0] == 0
						&& TrafficManager.mobile_month_data[63] == 0
						&& TrafficManager.wifi_month_data[63] == 0) {
					new AsyncTaskonRefreshNotify().execute(context);
				} else {
					programNotify.showNotice(context);
				}
			}

			if (sharedData.isWidGet14Open()) {
				Intent intentTextUpdate = new Intent();
				intentTextUpdate.setAction(BROADCAST_TRAFF);
				context.sendBroadcast(intentTextUpdate);
			}
			// RemoteViews views = new RemoteViews(context.getPackageName(),
			// R.layout.appwidget_layout);
		}
	}

	private class AsyncTaskonRefreshNotify extends
			AsyncTask<Context, Long, Long> {
		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while (TrafficManager.mobile_month_data[0] == 0
					&& TrafficManager.wifi_month_data[0] == 0
					&& TrafficManager.mobile_month_data[63] == 0
					&& TrafficManager.wifi_month_data[63] == 0) {
				try {
					Thread.sleep(50);
					timetap += 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 50)
					break;

			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			ProgramNotify programNotify = new ProgramNotify();
			programNotify.showNotice(context);
		}
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}

}
