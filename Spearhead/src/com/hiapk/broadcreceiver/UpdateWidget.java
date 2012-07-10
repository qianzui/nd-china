package com.hiapk.broadcreceiver;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.widget.ProgramNotify;
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
		boolean isNotifyOpen = sharedDatawidget.isNotifyOpen();
		boolean isWidGet14Open = sharedDatawidget.isWidGet14Open();
		boolean isopen = sharedDatawidget.isFloatOpen();
		if (SQLStatic.getIsInit(context) && (isNotifyOpen || isWidGet14Open)) {
			if (isNotifyOpen) {
				ProgramNotify programNotify = new ProgramNotify();
				// if (TrafficManager.mobile_month_data[0] == 0
				// && TrafficManager.wifi_month_data[0] == 0
				// && TrafficManager.mobile_month_data[63] == 0
				// && TrafficManager.wifi_month_data[63] == 0) {
				// new AsyncTaskonRefreshNotify().execute(context);
				// } else {
				programNotify.showNotice(context);
				// }
			}

			if (isWidGet14Open) {
				if (!(TrafficManager.mobile_month_data[0] == 0
						&& TrafficManager.wifi_month_data[0] == 0
						&& TrafficManager.mobile_month_data[63] == 0 && TrafficManager.wifi_month_data[63] == 0)) {
					SetText.setText(context);
				}
				// setWidgetOnlistener(context);
				Intent intentTextUpdate = new Intent();
				intentTextUpdate.setAction(BROADCAST_TRAFF);
				context.sendBroadcast(intentTextUpdate);
			}
			if (isopen) {
				context.startService(new Intent("com.hiapk.server"));
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
			// RemoteViews views = new RemoteViews(context.getPackageName(),
			// R.layout.appwidget_layout);
		}
	}

	// private void setWidgetOnlistener(Context context) {
	// // 设置监听广播
	// Intent intentwifi = new Intent();
	// intentwifi.setAction(BROADCAST_WIFI);
	// PendingIntent pendingIntentwifi = PendingIntent.getBroadcast(context,
	// 0, intentwifi, PendingIntent.FLAG_UPDATE_CURRENT);
	// Intent intentgpprs = new Intent();
	// intentgpprs.setAction(BROADCAST_GPRS);
	// PendingIntent pendingIntentgprs = PendingIntent.getBroadcast(context,
	// 0, intentgpprs, PendingIntent.FLAG_UPDATE_CURRENT);
	// Intent intenttraff = new Intent(context, Splash.class);
	// Bundle choosetab = new Bundle();
	// choosetab.putInt("TAB", 1);
	// intenttraff.putExtras(choosetab);
	// // intenttraff.setAction(BROADCAST_TRAFF);
	// PendingIntent pendingIntenttraff = PendingIntent.getActivity(context,
	// 0, intenttraff, PendingIntent.FLAG_UPDATE_CURRENT);
	// // Get the layout for the App Widget and attach an on-click listener
	// // to the button
	// RemoteViews views = new RemoteViews(context.getPackageName(),
	// R.layout.appwidget_layout);
	// // 设置监听
	// // views.setOnClickPendingIntent(R.id.widgetImage1,
	// // pendingIntentwifi);
	// views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout1,
	// pendingIntentwifi);
	// // views.setOnClickPendingIntent(R.id.widgetImageText1,
	// // pendingIntentwifi);
	// views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
	// pendingIntentgprs);
	// // views.setOnClickPendingIntent(R.id.widgetImageText2,
	// // pendingIntentgprs);
	// views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
	// pendingIntenttraff);
	// // views.setOnClickPendingIntent(R.id.widgetTextview2,
	// // pendingIntenttraff);
	// // views.setOnClickPendingIntent(R.id.widgetTextview3,
	// // pendingIntenttraff);
	// views.setCharSequence(R.id.widgetTextview1, "setText", SetText.text1);
	// views.setCharSequence(R.id.widgetTextview2, "setText", SetText.text2);
	// views.setCharSequence(R.id.widgetTextview3, "setText", SetText.text3);
	// }
	//
	// private class AsyncTaskonRefreshNotify extends
	// AsyncTask<Context, Long, Long> {
	// @Override
	// protected Long doInBackground(Context... params) {
	// int timetap = 0;
	// while (TrafficManager.mobile_month_data[0] == 0
	// && TrafficManager.wifi_month_data[0] == 0
	// && TrafficManager.mobile_month_data[63] == 0
	// && TrafficManager.wifi_month_data[63] == 0) {
	// try {
	// Thread.sleep(500);
	// timetap += 1;
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// if (timetap > 30)
	// break;
	//
	// }
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Long result) {
	// // TODO Auto-generated method stub
	// ProgramNotify programNotify = new ProgramNotify();
	// programNotify.showNotice(context);
	// Intent intentTextUpdate = new Intent();
	// intentTextUpdate.setAction(BROADCAST_TRAFF);
	// context.sendBroadcast(intentTextUpdate);
	// }
	// }
	//
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
