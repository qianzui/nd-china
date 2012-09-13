package com.hiapk.ui.widget;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.widget.AlertActionMobileDataControl;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.Splash;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Appwidget extends AppWidgetProvider {
	private String BROADCAST_WIFI = "com.hiapk.wifiwidget";
	private String BROADCAST_GPRS = "com.hiapk.prgswidget";
	private String BROADCAST_TRAFF = "com.hiapk.traffwidget";
	// 初始化wifi与mobile状态
	private String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	private int WIFI_STATE_DISABLED = 0x00000001;
	private int WIFI_STATE_DISABLING = 0x00000000;
	private int WIFI_STATE_ENABLED = 0x00000003;
	private int WIFI_STATE_ENABLING = 0x00000002;

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		showLog("onEnabled");
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		sharedDatawidget.setWidGet14Open(true);
		showLog("isWidGet14Open=" + sharedDatawidget.isWidGet14Open());
		// boolean isNotifyOpen = sharedData.isNotifyOpen();
		if (SQLStatic.TableWiFiOrG23 == "") {
			SQLStatic.initTablemobileAndwifi(context);
		}
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
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		sharedDatawidget.setWidGet14Open(false);
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
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
						pendingIntentgprs);
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
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
						pendingIntentgprs);
				views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
						pendingIntenttraff);
				initWidget(context, views);
				// 设置文本状态
				if (SetText.text1 != "今日已用: ...") {
					views.setCharSequence(R.id.widgetTextview1, "setText",
							SetText.text1);
				}
				if (SetText.text2 != "距结算日: ...") {
					views.setCharSequence(R.id.widgetTextview2, "setText",
							SetText.text2);
				}
				if (SetText.text3 != null) {
					views.setCharSequence(R.id.widgetTextview3, "setText",
							SetText.text3);
				}
			}

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
		if (wfm_on_off.getWifiState() == WIFI_STATE_ENABLED) {
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_on);
		} else if (wfm_on_off.getWifiState() == WIFI_STATE_DISABLED) {
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_off);
		} else if (wfm_on_off.getWifiState() == WIFI_STATE_ENABLING) {
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_turing);
		} else if (wfm_on_off.getWifiState() == WIFI_STATE_DISABLING) {
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_turing);
		} else {
			views.setImageViewResource(R.id.widget_wifi,
					R.drawable.widget_wifi_off);
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
		} else {
			if (mobile_on_of.isMobileDataEnable(context)) {
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_on);

			} else {
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_off);
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
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
					pendingIntentgprs);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
					pendingIntenttraff);
		} else {
			views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout);

			// 设置监听
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout1,
					pendingIntentwifi);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout2,
					pendingIntentgprs);
			views.setOnClickPendingIntent(R.id.widget1X4LinnerLayout3,
					pendingIntenttraff);
			if (SetText.text1 != "今日已用: ...") {
				views.setCharSequence(R.id.widgetTextview1, "setText",
						SetText.text1);
			}
			if (SetText.text2 != "距结算日: ...") {
				views.setCharSequence(R.id.widgetTextview2, "setText",
						SetText.text2);
			}
			if (SetText.text3 != null) {
				views.setCharSequence(R.id.widgetTextview3, "setText",
						SetText.text3);
			}

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

		if (wfm_on_off.getWifiState() == WIFI_STATE_ENABLED) {
			wfm_on_off.setWifiEnabled(false);
			Toast.makeText(context, R.string.wifi_off, Toast.LENGTH_SHORT)
					.show();
		} else if (wfm_on_off.getWifiState() == WIFI_STATE_DISABLED) {
			wfm_on_off.setWifiEnabled(true);
			Toast.makeText(context, R.string.wifi_on, Toast.LENGTH_SHORT)
					.show();
		} else {
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
			Toast.makeText(context, R.string.mobile_notsupport,
					Toast.LENGTH_SHORT).show();
		} else {
			if (tm.getSimState() != TelephonyManager.SIM_STATE_READY) {
				mobile_on_of.setMobileDataDisable(context);
				views.setImageViewResource(R.id.widget_gprs,
						R.drawable.widget_gprs_off);
				// views.setInt(R.id.widgetImageText2, "setTextColor",
				// Color.GRAY);
				Toast.makeText(context, R.string.mobile_nosim,
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
					Toast.makeText(context, R.string.mobile_off,
							Toast.LENGTH_SHORT).show();

				} else {
					mobile_on_of.setMobileDataEnable(context);
					// views.setInt(R.id.widgetImage2, "setBackgroundResource",
					// R.drawable.icon_mobile_on);
					views.setImageViewResource(R.id.widget_gprs,
							R.drawable.widget_gprs_on);
					// views.setInt(R.id.widgetImageText2, "setTextColor",
					// Color.GREEN);
					Toast.makeText(context, R.string.mobile_on,
							Toast.LENGTH_SHORT).show();
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
		if (SQLStatic.isshowLog) {
			Log.d("Appwidget", string);
		}
	}
}
