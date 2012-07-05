package com.hiapk.widget;

import com.hiapk.alertaction.AlertActionMobileDataControl;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.Splash;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Appwidget extends AppWidgetProvider {
	String BROADCAST_WIFI = "com.hiapk.wifiwidget";
	String BROADCAST_GPRS = "com.hiapk.prgswidget";
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";
	// 初始化wifi与mobile状态
	String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	String MOTOROLA_WIDGET_ADD = "com.motorola.blur.home.ACTION_WIDGET_ADDED";

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		showLog("onEnabled");
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		sharedData.setWidGet14Open(true);
		// boolean isNotifyOpen = sharedData.isNotifyOpen();
		AlarmSet alset = new AlarmSet();
		alset.StartAlarm(context);
		// SetText.setText(context);
		Intent intentTextUpdate = new Intent();
		intentTextUpdate.setAction(BROADCAST_TRAFF);
		context.sendBroadcast(intentTextUpdate);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		showLog("onDisabled");
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		sharedData.setWidGet14Open(false);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		showLog("onupdate");
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			// Create an Intent to launch ExampleActivity
			// 设置监听广播
			Intent intentwifi = new Intent();
			intentwifi.setAction(BROADCAST_WIFI);
			PendingIntent pendingIntentwifi = PendingIntent.getBroadcast(
					context, 0, intentwifi, PendingIntent.FLAG_UPDATE_CURRENT);
			Intent intentgpprs = new Intent();
			intentgpprs.setAction(BROADCAST_GPRS);
			PendingIntent pendingIntentgprs = PendingIntent.getBroadcast(
					context, 0, intentgpprs, PendingIntent.FLAG_UPDATE_CURRENT);
			Intent intenttraff = new Intent(context, Splash.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			intenttraff.putExtras(choosetab);
			// intenttraff.setAction(BROADCAST_TRAFF);
			PendingIntent pendingIntenttraff = PendingIntent.getActivity(
					context, 0, intenttraff, PendingIntent.FLAG_UPDATE_CURRENT);
			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout);
			long monthSet = sharedData.getMonthMobileSetOfLong();
			if (monthSet == 0) {
				views = new RemoteViews(context.getPackageName(),
						R.layout.appwidget_layout_not_set);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout1,
						pendingIntentwifi);
				// views.setOnClickPendingIntent(R.id.widgetImageText1,
				// pendingIntentwifi);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
						pendingIntentgprs);
				// views.setOnClickPendingIntent(R.id.widgetImageText2,
				// pendingIntentgprs);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
						pendingIntenttraff);
				initWidget(context, views);
			} else {
				views = new RemoteViews(context.getPackageName(),
						R.layout.appwidget_layout);
				// 设置监听
				// views.setOnClickPendingIntent(R.id.widgetImage1,
				// pendingIntentwifi);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout1,
						pendingIntentwifi);
				// views.setOnClickPendingIntent(R.id.widgetImageText1,
				// pendingIntentwifi);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
						pendingIntentgprs);
				// views.setOnClickPendingIntent(R.id.widgetImageText2,
				// pendingIntentgprs);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
						pendingIntenttraff);
				// views.setOnClickPendingIntent(R.id.widgetTextview2,
				// pendingIntenttraff);
				// views.setOnClickPendingIntent(R.id.widgetTextview3,
				// pendingIntenttraff);
				initWidget(context, views);
				// 设置文本状态
				// TextView aa;
				// aa.setText(text)
				views.setCharSequence(R.id.widgetTextview1, "setText",
						SetText.text1);
				views.setCharSequence(R.id.widgetTextview2, "setText",
						SetText.text2);
				views.setCharSequence(R.id.widgetTextview3, "setText",
						SetText.text3);
			}
			// // 进行设置
			// WifiManager wfm_on_off;
			// wfm_on_off = (WifiManager) context
			// .getSystemService(Context.WIFI_SERVICE);
			//
			// if (wfm_on_off.isWifiEnabled()) {
			// // imbtn_wifi.setBackgroundResource(R.drawable.cross);
			// views.setInt(R.id.widgetImage1, "setBackgroundResource",
			// R.drawable.cross);
			// } else {
			// views.setInt(R.id.widgetImage1, "setBackgroundResource",
			// R.drawable.ic_launcher);
			// }

			// views.setInt(R.id.widgetImage2, "setBackgroundResource",
			// R.drawable.ic_launcher);
			// ImageView daa;
			// daa.setBackgroundResource(resid)
			// Tell the AppWidgetManager to perform an update on the current App
			// Widget

			appWidgetManager.updateAppWidget(appWidgetId, views);
			// appWidgetManager.updateAppWidget(new ComponentName(context,
			// Appwidget.class), views);

		}

	}

	/**
	 * 初始化状态
	 * 
	 * @param context
	 * @param views
	 */
	private void initWidget(Context context, RemoteViews views) {
		// TODO Auto-generated method stub
		// 初始化wifi
		WifiManager wfm_on_off;
		wfm_on_off = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wfm_on_off.isWifiEnabled()) {
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_on);
			// TextView aa;
			// aa.setTextColor(color)
			// views.setInt(R.id.widgetImageText1, "setTextColor", Color.GREEN);
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_on);
		} else {
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_off);
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_off);
			// views.setInt(R.id.widgetImageText1, "setTextColor", Color.GRAY);
		}
		// 初始化mobile
		AlertActionMobileDataControl mobile_on_of = new AlertActionMobileDataControl();
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() != TelephonyManager.SIM_STATE_READY
				|| (Integer.valueOf(android.os.Build.VERSION.SDK) < 10)) {
			if (mobile_on_of.isMobileDataEnable(context)) {
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_on);
			} else {
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_off);
			}
			// mobile_on_of.setMobileDataDisable(context);

			// views.setInt(R.id.widgetImageText2, "setTextColor", Color.GRAY);
		} else {
			if (mobile_on_of.isMobileDataEnable(context)) {
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_on);
				// views.setInt(R.id.widgetImageText2, "setTextColor",
				// Color.GREEN);
				// views.setInt(R.id.widgetImage2, "setBackgroundResource",
				// R.drawable.icon_mobile_on);

			} else {
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_off);
				// views.setInt(R.id.widgetImageText2, "setTextColor",
				// Color.GRAY);
				// views.setInt(R.id.widgetImage2, "setBackgroundResource",
				// R.drawable.icon_mobile_off);
			}
		}

		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		appWidgetManager.updateAppWidget(new ComponentName(context,
				Appwidget.class), views);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		showLog("onReceive=" + intent.getAction());
		// Intent intentAppUpdate = new Intent();
		// intentAppUpdate.setAction(APPWIDGET_UPDATE);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.appwidget_layout);
		long monthSet = 0;
		try {
			SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
			monthSet = sharedData.getMonthMobileSetOfLong();
		} catch (Exception e) {
			// TODO: handle exception
			monthSet = 0;
		}

		if (monthSet == 0) {
			views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout_not_set);
			if (intent.getAction().equals(BROADCAST_WIFI)) {
				wifiswitch(context, views);
				// 更新小部件
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				appWidgetManager.updateAppWidget(new ComponentName(context,
						Appwidget.class), views);
				// context.sendBroadcast(intentAppUpdate);
			} else if (intent.getAction().equals(BROADCAST_GPRS)) {
				mobileswitch(context, views);
				// 更新小部件
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				appWidgetManager.updateAppWidget(new ComponentName(context,
						Appwidget.class), views);
				// context.sendBroadcast(intentAppUpdate);
			} else if (intent.getAction().equals(BROADCAST_TRAFF)) {
				setwidgetListenerAndInit(context, monthSet);
				// context.sendBroadcast(intentAppUpdate);
			} else if (intent.getAction().equals(APPWIDGET_UPDATE)) {
				initWidget(context, views);
				// views.setCharSequence(R.id.widgetTextview1, "setText",
				// SetText.text1);
				// views.setCharSequence(R.id.widgetTextview2, "setText",
				// SetText.text2);
				// views.setCharSequence(R.id.widgetTextview3, "setText",
				// SetText.text3);
				// AppWidgetManager appWidgetManager = AppWidgetManager
				// .getInstance(context);
				// appWidgetManager.updateAppWidget(new ComponentName(context,
				// Appwidget.class), views);
			} else {
				setwidgetListenerAndInit(context, monthSet);
			}
		} else {
			views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout);
			if (intent.getAction().equals(BROADCAST_WIFI)) {
				wifiswitch(context, views);
				// 更新小部件
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				appWidgetManager.updateAppWidget(new ComponentName(context,
						Appwidget.class), views);
				// context.sendBroadcast(intentAppUpdate);
			} else if (intent.getAction().equals(BROADCAST_GPRS)) {
				mobileswitch(context, views);
				// 更新小部件
				AppWidgetManager appWidgetManager = AppWidgetManager
						.getInstance(context);
				appWidgetManager.updateAppWidget(new ComponentName(context,
						Appwidget.class), views);
				// context.sendBroadcast(intentAppUpdate);
			} else if (intent.getAction().equals(BROADCAST_TRAFF)) {
				setwidgetListenerAndInit(context, monthSet);
				// context.sendBroadcast(intentAppUpdate);
			} else if (intent.getAction().equals(APPWIDGET_UPDATE)) {
				initWidget(context, views);
				// views.setCharSequence(R.id.widgetTextview1, "setText",
				// SetText.text1);
				// views.setCharSequence(R.id.widgetTextview2, "setText",
				// SetText.text2);
				// views.setCharSequence(R.id.widgetTextview3, "setText",
				// SetText.text3);
				// AppWidgetManager appWidgetManager = AppWidgetManager
				// .getInstance(context);
				// appWidgetManager.updateAppWidget(new ComponentName(context,
				// Appwidget.class), views);
			} else {
				setwidgetListenerAndInit(context, monthSet);
			}
		}
	}

	private void setwidgetListenerAndInit(Context context, long monthSet) {
		// 设置监听广播
		Intent intentwifi = new Intent();
		intentwifi.setAction(BROADCAST_WIFI);
		PendingIntent pendingIntentwifi = PendingIntent.getBroadcast(context,
				0, intentwifi, PendingIntent.FLAG_UPDATE_CURRENT);
		Intent intentgpprs = new Intent();
		intentgpprs.setAction(BROADCAST_GPRS);
		PendingIntent pendingIntentgprs = PendingIntent.getBroadcast(context,
				0, intentgpprs, PendingIntent.FLAG_UPDATE_CURRENT);
		Intent intenttraff = new Intent(context, Splash.class);
		Bundle choosetab = new Bundle();
		choosetab.putInt("TAB", 1);
		intenttraff.putExtras(choosetab);
		// intenttraff.setAction(BROADCAST_TRAFF);
		PendingIntent pendingIntenttraff = PendingIntent.getActivity(context,
				0, intenttraff, PendingIntent.FLAG_UPDATE_CURRENT);
		// Get the layout for the App Widget and attach an on-click listener
		// to the button
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.appwidget_layout);
		if (monthSet == 0) {
			views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout_not_set);
			// 设置监听
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout1,
					pendingIntentwifi);
			// views.setOnClickPendingIntent(R.id.widgetImageText1,
			// pendingIntentwifi);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
					pendingIntentgprs);
			// views.setOnClickPendingIntent(R.id.widgetImageText2,
			// pendingIntentgprs);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
					pendingIntenttraff);
		} else {
			views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout);

			// 设置监听
			// views.setOnClickPendingIntent(R.id.widgetImage1,
			// pendingIntentwifi);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout1,
					pendingIntentwifi);
			// views.setOnClickPendingIntent(R.id.widgetImageText1,
			// pendingIntentwifi);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
					pendingIntentgprs);
			// views.setOnClickPendingIntent(R.id.widgetImageText2,
			// pendingIntentgprs);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
					pendingIntenttraff);
			// views.setOnClickPendingIntent(R.id.widgetTextview2,
			// pendingIntenttraff);
			// views.setOnClickPendingIntent(R.id.widgetTextview3,
			// pendingIntenttraff);
			views.setCharSequence(R.id.widgetTextview1, "setText",
					SetText.text1);
			views.setCharSequence(R.id.widgetTextview2, "setText",
					SetText.text2);
			views.setCharSequence(R.id.widgetTextview3, "setText",
					SetText.text3);
		}

		initWidget(context, views);
	}

	/**
	 * wifi控制
	 * 
	 * @param context
	 * @param views
	 */
	private void wifiswitch(Context context, RemoteViews views) {

		WifiManager wfm_on_off;
		wfm_on_off = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		wfm_on_off.getWifiState();
		if (wfm_on_off.isWifiEnabled()) {
			wfm_on_off.setWifiEnabled(false);
			// ImageView aa;
			// aa.setImageResource(resId)
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_off);
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_off);
			// views.setInt(R.id.widgetImageText1, "setTextColor", Color.GRAY);
			Toast.makeText(context, "wifi正在关闭", Toast.LENGTH_SHORT).show();
		} else {
			wfm_on_off.setWifiEnabled(true);
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_on);
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_on);
			// views.setInt(R.id.widgetImageText1, "setTextColor", Color.GREEN);
			Toast.makeText(context, "wifi正在开启", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 移动控制
	 * 
	 * @param context
	 * @param views
	 */
	private void mobileswitch(Context context, RemoteViews views) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		AlertActionMobileDataControl mobile_on_of = new AlertActionMobileDataControl();
		tm.getSimState();
		if (Integer.valueOf(android.os.Build.VERSION.SDK) < 10) {
			Toast.makeText(context, "您的手机或者Android系统版本不支持操作移动数据开关",
					Toast.LENGTH_SHORT).show();
		} else {
			if (tm.getSimState() != TelephonyManager.SIM_STATE_READY) {
				mobile_on_of.setMobileDataDisable(context);
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_off);
				// views.setInt(R.id.widgetImageText2, "setTextColor",
				// Color.GRAY);
				Toast.makeText(context, "未检测到SIM卡或者SIM卡未就绪，无法启动移动网络",
						Toast.LENGTH_SHORT).show();
				if (mobile_on_of.isMobileDataEnable(context)) {
					views.setImageViewResource(R.id.widget_gprs,
							R.drawable.widget_gprs_on);
				} else {
					views.setImageViewResource(R.id.widget_gprs,
							R.drawable.widget_gprs_off);
				}
			} else {
				if (mobile_on_of.isMobileDataEnable(context)) {
					mobile_on_of.setMobileDataDisable(context);
					// views.setInt(R.id.widgetImage2, "setBackgroundResource",
					// R.drawable.icon_mobile_off);
					views.setImageViewResource(R.id.widget_gprs,
							R.drawable.widget_gprs_off);
					// views.setInt(R.id.widgetImageText2, "setTextColor",
					// Color.GRAY);
					Toast.makeText(context, "移动网络正在关闭", Toast.LENGTH_SHORT)
							.show();

				} else {
					mobile_on_of.setMobileDataEnable(context);
					// views.setInt(R.id.widgetImage2, "setBackgroundResource",
					// R.drawable.icon_mobile_on);
					views.setImageViewResource(R.id.widget_gprs,
							R.drawable.widget_gprs_on);
					// views.setInt(R.id.widgetImageText2, "setTextColor",
					// Color.GREEN);
					Toast.makeText(context, "移动网络正在开启", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}

	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("appwidget", string);
	}
}
