package com.hiapk.widget;

import java.lang.annotation.Annotation;

import com.hiapk.alertaction.AlertActionMobileDataControl;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.spearhead.Splash;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RemoteViews.RemoteView;

public class Appwidget extends AppWidgetProvider {
	String BROADCAST_WIFI = "com.hiapk.wifiwidget";
	String BROADCAST_GPRS = "com.hiapk.prgswidget";
	String BROADCAST_TRAFF = "com.hiapk.traffwidget";
	String APPWIDGET_UPDATE = "com.hiapkAPPWIDGET_UPDATE";
	SharedPrefrenceData sharedData;

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		sharedData = new SharedPrefrenceData(context);
		sharedData.setWidGet14Open(true);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		sharedData = new SharedPrefrenceData(context);
		sharedData.setWidGet14Open(false);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		sharedData = new SharedPrefrenceData(context);
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
					context, 0, intentwifi, 0);
			Intent intentgpprs = new Intent();
			intentgpprs.setAction(BROADCAST_GPRS);
			PendingIntent pendingIntentgprs = PendingIntent.getBroadcast(
					context, 0, intentgpprs, 0);
			Intent intenttraff = new Intent(context, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			intenttraff.putExtras(choosetab);
			// intenttraff.setAction(BROADCAST_TRAFF);
			PendingIntent pendingIntenttraff = PendingIntent.getActivity(
					context, 0, intenttraff, 0);
			// Get the layout for the App Widget and attach an on-click listener
			// to the button
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout);
			// 设置监听
			views.setOnClickPendingIntent(R.id.widgetImage1, pendingIntentwifi);
			views.setOnClickPendingIntent(R.id.widgetImageText1,
					pendingIntentwifi);
			views.setOnClickPendingIntent(R.id.widgetImage2, pendingIntentgprs);
			views.setOnClickPendingIntent(R.id.widgetImageText2,
					pendingIntentgprs);
			views.setOnClickPendingIntent(R.id.widgetTextview1,
					pendingIntenttraff);
			views.setOnClickPendingIntent(R.id.widgetTextview2,
					pendingIntenttraff);
			views.setOnClickPendingIntent(R.id.widgetTextview3,
					pendingIntenttraff);
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
			views.setImageViewResource(R.id.widgetImage1,
					R.drawable.icon_wifi_on);
			// TextView aa;
			// aa.setTextColor(color)
			views.setInt(R.id.widgetImageText1, "setTextColor", Color.GREEN);
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_on);
		} else {
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_off);
			views.setImageViewResource(R.id.widgetImage1,
					R.drawable.icon_wifi_off);
			views.setInt(R.id.widgetImageText1, "setTextColor", Color.GRAY);
		}
		// 初始化mobile
		AlertActionMobileDataControl mobile_on_of = new AlertActionMobileDataControl();
		if (mobile_on_of.isMobileDataEnable(context)) {
			views.setImageViewResource(R.id.widgetImage2,
					R.drawable.icon_mobile_on);
			views.setInt(R.id.widgetImageText2, "setTextColor", Color.GREEN);
			// views.setInt(R.id.widgetImage2, "setBackgroundResource",
			// R.drawable.icon_mobile_on);

		} else {
			views.setImageViewResource(R.id.widgetImage2,
					R.drawable.icon_mobile_off);
			views.setInt(R.id.widgetImageText2, "setTextColor", Color.GRAY);
			// views.setInt(R.id.widgetImage2, "setBackgroundResource",
			// R.drawable.icon_mobile_off);
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
		showLog(intent.getAction());
		// Intent intentAppUpdate = new Intent();
		// intentAppUpdate.setAction(APPWIDGET_UPDATE);
		RemoteViews views = new RemoteViews(context.getPackageName(),
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
			views.setCharSequence(R.id.widgetTextview1, "setText",
					SetText.text1);
			views.setCharSequence(R.id.widgetTextview2, "setText",
					SetText.text2);
			views.setCharSequence(R.id.widgetTextview3, "setText",
					SetText.text3);
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			appWidgetManager.updateAppWidget(new ComponentName(context,
					Appwidget.class), views);
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
		}
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

		if (wfm_on_off.isWifiEnabled()) {
			wfm_on_off.setWifiEnabled(false);
			// ImageView aa;
			// aa.setImageResource(resId)
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_off);
			views.setImageViewResource(R.id.widgetImage1,
					R.drawable.icon_wifi_off);
			views.setInt(R.id.widgetImageText1, "setTextColor", Color.GRAY);
			Toast.makeText(context, "wifi正在关闭", Toast.LENGTH_SHORT).show();

		} else {
			wfm_on_off.setWifiEnabled(true);
			// views.setInt(R.id.widgetImage1, "setImageResource",
			// R.drawable.icon_wifi_on);
			views.setImageViewResource(R.id.widgetImage1,
					R.drawable.icon_wifi_on);
			views.setInt(R.id.widgetImageText1, "setTextColor", Color.GREEN);
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
		AlertActionMobileDataControl mobile_on_of = new AlertActionMobileDataControl();
		if (mobile_on_of.isMobileDataEnable(context)) {
			mobile_on_of.setMobileDataDisable(context);
			// views.setInt(R.id.widgetImage2, "setBackgroundResource",
			// R.drawable.icon_mobile_off);
			views.setImageViewResource(R.id.widgetImage2,
					R.drawable.icon_mobile_off);
			views.setInt(R.id.widgetImageText2, "setTextColor", Color.GRAY);
			Toast.makeText(context, "移动网络正在关闭", Toast.LENGTH_SHORT).show();

		} else {
			mobile_on_of.setMobileDataEnable(context);
			// views.setInt(R.id.widgetImage2, "setBackgroundResource",
			// R.drawable.icon_mobile_on);
			views.setImageViewResource(R.id.widgetImage2,
					R.drawable.icon_mobile_on);
			views.setInt(R.id.widgetImageText2, "setTextColor", Color.GREEN);
			Toast.makeText(context, "移动网络正在开启", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("appwidget", string);
	}
}
