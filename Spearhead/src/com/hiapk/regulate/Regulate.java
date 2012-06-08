package com.hiapk.regulate;

import com.hiapk.broadcreceiver.SMS_Received;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Regulate extends Activity {
	public static Button chooseBtn;
	Button smsSend;	
	//	Button smsRead;	
	public static TextView smsText;
	public static TextView smsNum;
	public static TextView smsResult;		
	SharedPrefrenceData sharedData;
	String city;
	String brand;	
	SmsRead sr;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regulate);	
		MobclickAgent.onError(this);
		sharedData = new SharedPrefrenceData(this);
		chooseBtn = (Button)findViewById(R.id.choose);
		smsSend = (Button)findViewById(R.id.smsSend);
		//		smsRead = (Button)findViewById(R.id.smsRead);
		smsText = (TextView)findViewById(R.id.smsText);
		smsNum = (TextView)findViewById(R.id.smsNum);
		smsResult = (TextView)findViewById(R.id.smsResult);
		city = sharedData.getCity();
		brand = sharedData.getBrand();

		chooseBtn.setText(city+brand);
		smsNum.setText(sharedData.getSmsNum());
		smsText.setText(sharedData.getSmsText());
		sr =  new SmsRead();


		chooseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Regulate.this,PhoneSet.class);
				startActivity(it);

			}
		});

		smsSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				smsSend();				
				
			}
		});

//				if(sharedData.getIsReceive()){			
//					smsRead();
//				}


		//		smsRead.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//
		//				smsRead();
		//			}
		//		});




	}
	public void smsSend(){

		String num = smsNum.getText().toString();
		String text = smsText.getText().toString();	
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() == TelephonyManager.SIM_STATE_READY){
			SmsManager sm ;
			sm = SmsManager.getDefault();
			sm.sendTextMessage(num, null, text, null, null);
			Toast.makeText(this, "已自动发送短信，短信接收后将自动设置本月已用流量", Toast.LENGTH_LONG).show();
			sharedData.setIsSend(true);
		}
		else{
			Toast.makeText(this, "请插入SIM卡或者关闭飞行模式", Toast.LENGTH_LONG).show();
		}

	}	

	public void smsRead(){

		sr.Sms(this);		
		if(sr.isRead){
			smsResult.setText("短信内容可能有误，请手动设置");
		}
				Log.v("+++++++++++++++++++++", "读取短信");
				Toast.makeText(this, sharedData.getIsReceive()+" ", Toast.LENGTH_LONG).show();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// umeng
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		MobclickAgent.onPause(this);
	}
	//	@Override
	//	protected void onResume() {
	//		// TODO Auto-generated method stub
	//		if(SMS_Received.isReceive){			
	//			smsRead();
	//		}
	//		Toast.makeText(this, SMS_Received.isReceive+"onResume() ", Toast.LENGTH_LONG).show();
	//		super.onResume();
	//	}



}
