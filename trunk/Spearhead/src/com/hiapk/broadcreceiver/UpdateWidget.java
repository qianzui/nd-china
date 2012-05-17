package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.hiapk.widget.ProgramNotify;
import com.hiapk.widget.SetText;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidget extends BroadcastReceiver {
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		if (sharedData.isSQLinited() == true
				|| sharedData.isWidGet14Open() == true) {
			SetText.setText(context);
			if (sharedData.isSQLinited()) {
				ProgramNotify programNotify = new ProgramNotify();
				programNotify.showNotice(context);
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
