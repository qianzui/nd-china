package com.nd.yhg;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Switch extends AppWidgetProvider {
	final static String button_action="button1";
	private String TAG;


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "onupdata", Toast.LENGTH_SHORT).show();
		
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),R.layout.main);
		
		//Ìí¼Ó°´Å¥¼àÌý
		Intent mIntent = new Intent(context,Switch.class);
		mIntent.setAction(button_action);
		PendingIntent mPendingIntent  = PendingIntent.getBroadcast(context, 0, mIntent, 0);
		mRemoteViews.setOnClickPendingIntent(R.id.mButton,mPendingIntent);
		
		
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "onReceiver", Toast.LENGTH_SHORT).show();
		if(intent.getAction().equals(button_action))
		{
			Log.i(TAG,"nimei");
			RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),R.layout.main);
			Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
			ComponentName mWidget = new ComponentName(context,Switch.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			manager.updateAppWidget(mWidget, mRemoteViews);
		}
		
		super.onReceive(context, intent);
	}

  
}