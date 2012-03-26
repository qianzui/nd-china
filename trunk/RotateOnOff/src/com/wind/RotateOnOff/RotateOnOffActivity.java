package com.wind.RotateOnOff;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

public class RotateOnOffActivity extends AppWidgetProvider {
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		Log.v("ooxx", "onDeleted");
	}
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		Log.v("ooxx", "onEnabled");
	}
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		Log.v("ooxx", "onDisabled");
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		
		Log.v("ooxx", "onReceive");
	}
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		final int N = appWidgetIds.length;
		for (int i = 0; i < N; i++) {
			int appWidgetId = appWidgetIds[i];
			Intent intent = new Intent(context,RotateOnOffActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.main);
			views.setOnClickPendingIntent(R.id.rotate, pendingIntent);
			int flag =Settings.System.getInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION, 0);
			if(flag == 1){
				views.setImageViewResource(R.id.rotate, R.drawable.autorotate_off);			
			}
			else {
				views.setImageViewResource(R.id.rotate, R.drawable.autorotate_on);	
			}
			Settings.System.putInt(context.getContentResolver(),Settings.System.ACCELEROMETER_ROTATION,flag==1?0:1);

			appWidgetManager.updateAppWidget(appWidgetId, views);			
			
		}
		Log.v("ooxx", "onUpdate");
	}
}