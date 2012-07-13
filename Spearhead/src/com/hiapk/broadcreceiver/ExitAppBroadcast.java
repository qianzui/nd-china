package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.widget.FloatService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ExitAppBroadcast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPrefrenceDataWidget sharedData = new SharedPrefrenceDataWidget(
				context);
		if (sharedData.isFloatOpen()) {
			context.startService(new Intent("com.hiapk.server"));
		}
	}

	// private void showLog(String string) {
	// // TODO Auto-generated method stub
	// // Log.d("DataChangeBroad", string);
	// }
}
