package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.Regulate;
import com.hiapk.regulate.SmsRead;
import com.hiapk.spearhead.SpearheadActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SMS_Received extends BroadcastReceiver {		
	SharedPrefrenceData sharedData; 
	SmsRead sr;
	String count;
	@Override 
	public void onReceive(Context context, Intent intent) {
		sharedData = new SharedPrefrenceData(context);
		sr =  new SmsRead();
		// TODO Auto-generated method stub
		if(intent.getAction().
				equals("android.provider.Telephony.SMS_RECEIVED")
				&&sharedData.getIsSend()){
			sharedData.setIsReceive(true);
			count = sr.Sms(context);
			if(sr.isRead){
				float monthHasUse = Float.valueOf(count);
				sharedData.setMonthMobileHasUseOffloat(monthHasUse);
				sharedData
				.setMonthMobileHasUse((long) (monthHasUse * 1024) * 1024);
				
				Toast.makeText(context, "已接收到短信，并设置月已用流量成功", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(context, "短信读取失败，请手动设置", Toast.LENGTH_LONG).show();
			}
			
			
//			Toast.makeText(context, "新短信", Toast.LENGTH_LONG).show();
//			Intent it = new Intent(context, Regulate.class);
//			it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(it);
			
//			sr.Sms(getParent());		
//			if(!SmsRead.isRead){
//				smsResult.setText("读取短信失败或短信内容有误，请手动设置");
//			}  
			
//			Log.v("+++++++++++++++++++++", "读取短信");
		}
		else{ 
			sharedData.setIsReceive (false);
		}

	}
}