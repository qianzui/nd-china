package com.nd.yhg;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Switch extends AppWidgetProvider {
	final static String button_action="button1";
	private String TAG="xxxxx";


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		Toast.makeText(context, "onupdata", Toast.LENGTH_SHORT).show();
		
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),R.layout.main);
		
		//添加按钮监听
		Intent mIntent = new Intent(context,Switch.class);
		mIntent.setAction(button_action);
		PendingIntent mPendingIntent  = PendingIntent.getBroadcast(context, 0, mIntent, 0);		
		mRemoteViews.setOnClickPendingIntent(R.id.mButton,mPendingIntent);		
		appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		WifiManager wm;
		wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		Toast.makeText(context, "onReceiver", Toast.LENGTH_SHORT).show();
//		String str2 = Settings.System.getString(context.getContentResolver(), "accelerometer_rotation");
		if(intent.getAction().equals(button_action))		{
			
			Toast.makeText(context, "获取到action了", Toast.LENGTH_SHORT).show();
			try {
				Toast.makeText(context, "我要try了", Toast.LENGTH_SHORT).show();
				 if (wm.isWifiEnabled()) {  
			            wm.setWifiEnabled(false);  
			            Toast.makeText(context, "我要关了", Toast.LENGTH_SHORT).show();
			    }  
			    else {  
			            wm.setWifiEnabled(true);  
			            Toast.makeText(context, "我要开了", Toast.LENGTH_SHORT).show();  
			    }   
//				if(str2.equals("0"))
//			      {		        
//					Toast.makeText(context, "我到开了", Toast.LENGTH_SHORT).show();
//			        Settings.System.putString(context.getContentResolver(), "accelerometer_rotation", "1");
//			       
//			      }
//			      if (str2.equals("1"))
//			      {    
//			    	Toast.makeText(context, "我到关了", Toast.LENGTH_SHORT).show();
//			    	Settings.System.putString(context.getContentResolver(), "accelerometer_rotation", "0");
//			      
//			      }
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
	}

  
}