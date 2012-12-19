package com.hiapk.widget;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.widget.SetText;
import com.hiapk.spearhead.R;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class Appwidget11 extends AppWidgetProvider {

	@Override
	public void onEnabled(Context context) {
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
		intentTextUpdate.setAction(WidgetStatic.BROADCAST_TRAFF);
		context.sendBroadcast(intentTextUpdate);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		showLog("onDisabled");
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		sharedDatawidget.setWidGet14Open(false);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		showLog("onupdate11");
		final int N = appWidgetIds.length;

		// Perform this loop procedure for each App Widget that belongs to this
		// provider
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			// Create an Intent to launch ExampleActivity
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.appwidget_layout11);
			// 设置监听
			// views.setOnClickPendingIntent(R.id.widgetImage1,
			// pendingIntentwifi);
			initWidget(context, views);
			// appWidgetManager.updateAppWidget(appWidgetId, views);

		}

	}

	/**
	 * 初始化状态
	 * 
	 * @param context
	 * @param views
	 */
	private void initWidget(Context context, RemoteViews views) {
		// 无值，则初始化数值
		if (SetText.textToday == "0 KB") {
			SetText.initText(context);
		}
		if (SetText.textToday != null) {
			views.setCharSequence(R.id.widget11Textview2, "setText",
					SetText.textToday);
			views.setCharSequence(R.id.widget11Textview3, "setText",
					SetText.textTodayUnit);
		}
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		appWidgetManager.updateAppWidget(new ComponentName(context,
				Appwidget11.class), views);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		showLog("onReceive=" + intent.getAction());
		// Intent intentAppUpdate = new Intent();
		// intentAppUpdate.setAction(APPWIDGET_UPDATE);
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.appwidget_layout11);
		if (intent.getAction().equals(WidgetStatic.BROADCAST_TRAFF)) {
			initWidget(context, views);
		} else if (intent.getAction().equals(WidgetStatic.APPWIDGET_UPDATE)) {
			initWidget(context, views);
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
