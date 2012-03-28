package com.wind.RotateSwitch;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;

public class RotateSwitchActivity extends AppWidgetProvider {
	final static String button_action="button1";
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
//		Toast.makeText(context, "onupdata", Toast.LENGTH_SHORT).show();
		
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),R.layout.main);
		
		String str2 = Settings.System.getString(context.getContentResolver(), "accelerometer_rotation");
		if(str2.equals("1"))
	      {		        
//			Toast.makeText(context, "�����ǿ���", Toast.LENGTH_SHORT).show();
			mRemoteViews.setImageViewResource(R.id.Rotate, R.drawable.autorotate_on);
	        
	       
	      }
		else
	      {    
//			Toast.makeText(context, "�����ǹص�", Toast.LENGTH_SHORT).show();
			mRemoteViews.setImageViewResource(R.id.Rotate, R.drawable.autorotate_off);
	      
	      }
		
		//��Ӱ�ť����
		Intent mIntent = new Intent(context,RotateSwitchActivity.class);
		mIntent.setAction(button_action);
		PendingIntent mPendingIntent  = PendingIntent.getBroadcast(context, 0, mIntent, 0);		
		mRemoteViews.setOnClickPendingIntent(R.id.Rotate,mPendingIntent);		
		appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
		
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(),R.layout.main);		
//		Toast.makeText(context, "onReceiver", Toast.LENGTH_SHORT).show();
		String str2 = Settings.System.getString(context.getContentResolver(), "accelerometer_rotation");
		if(intent.getAction().equals(button_action))		
		{
			
//			Toast.makeText(context, "��ȡ��action��", Toast.LENGTH_SHORT).show();
			try {
//				Toast.makeText(context, "��Ҫtry��", Toast.LENGTH_SHORT).show();
//				 if (wm.isWifiEnabled()) {  
//			            wm.setWifiEnabled(false);  
//			            Toast.makeText(context, "��Ҫ����", Toast.LENGTH_SHORT).show();
//			    }  
//				 else  {  
//			            wm.setWifiEnabled(true);  
//			            Toast.makeText(context, "��Ҫ����", Toast.LENGTH_SHORT).show();  
//			    }   
				if(str2.equals("1"))
			      {		        
					
					mRemoteViews.setImageViewResource(R.id.Rotate, R.drawable.autorotate_off);
			        Settings.System.putString(context.getContentResolver(), "accelerometer_rotation", "0");
			        Toast.makeText(context, "������Ӧ�ѹر�", Toast.LENGTH_SHORT).show();
			       
			      }
				else
			      {    
			    	
			    	mRemoteViews.setImageViewResource(R.id.Rotate, R.drawable.autorotate_on);
			    	Settings.System.putString(context.getContentResolver(), "accelerometer_rotation", "1");
			    	Toast.makeText(context, "������Ӧ�ѿ���", Toast.LENGTH_SHORT).show();
			      
			      }
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		appWidgetManager.updateAppWidget(new ComponentName(context,RotateSwitchActivity.class), mRemoteViews);
		
	}
}