package com.hiapk.regulate;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Regulate extends Activity{
	public static Button chooseBtn;
	Button smsSend;	
	Button smsRead;	
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
		sharedData = new SharedPrefrenceData(this);
		chooseBtn = (Button)findViewById(R.id.choose);
		smsSend = (Button)findViewById(R.id.smsSend);
		smsRead = (Button)findViewById(R.id.smsRead);
		smsText = (TextView)findViewById(R.id.smsText);
		smsNum = (TextView)findViewById(R.id.smsNum);
		smsResult = (TextView)findViewById(R.id.smsResult);
		city = sharedData.getCity();
		brand = sharedData.getBrand();

		chooseBtn.setText(city+brand);
		smsNum.setText(sharedData.getSmsNum());
		smsText.setText(sharedData.getSmsText());


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

		smsRead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				smsRead();
			}
		});


	}
	public void smsSend(){
		String num = smsNum.getText().toString();
		String text = smsText.getText().toString();	
		Uri uri = Uri.parse("smsto:"+num);
		Intent it = new Intent(Intent.ACTION_VIEW,uri);	
		it.putExtra("sms_body", text);
		startActivity(it);
	}	

	public void smsRead(){
		sr =  new SmsRead();
		sr.Sms(this);
		if(!SmsRead.isRead){
			smsResult.setText("读取短信失败或短信内容有误，请手动设置");
		}
		Log.v("+++++++++++++++++++++", "读取短信");
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		sr =  new SmsRead();
		//		Log.v("----------------",sr.Sms(this));
	}
}
