package com.hiapk.broadcreceiver;

import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;
import com.hiapk.widget.SetText;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateWidget extends BroadcastReceiver {
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";
	Context context;
	String BROADCAST_WIFI = "com.hiapk.wifiwidget";
	String BROADCAST_GPRS = "com.hiapk.prgswidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		boolean isopen = sharedDatawidget.isFloatOpen();
		if (SQLStatic.getIsInit(context)) {
			if (SQLStatic.TableWiFiOrG23 != "") {
				SetText.resetWidgetAndNotify(context);
			}
			if (isopen) {
				context.startService(new Intent("com.hiapk.server"));
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
	// // TODO Auto-generated method stub
	// // Log.d("Receiver", string);
	// }

}
