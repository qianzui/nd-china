package com.hiapk.broadcreceiver;

import com.hiapk.control.widget.SetText;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateWidget extends BroadcastReceiver {
	String TAG = "ReceiverWidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		// Logs.d(TAG, "onReceiveWidget");
		if (SQLStatic.getIsInit(context)) {
			if (SQLStatic.TableWiFiOrG23 != "") {
				SetText.resetWidgetAndNotify(context);
				// Logs.d(TAG, "sendnotification");
			}
			boolean isopen = sharedDatawidget.isFloatOpen();
			if (isopen) {
				context.startService(new Intent("com.hiapk.server"));
				// Logs.d(TAG, "sendfloat");
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
		}
	}

	// /**
	// * 用于显示日志
	// *
	// * @param string
	// */
	// private void showLog(String string) {
	// // Log.d("Receiver", string);
	// }

}
