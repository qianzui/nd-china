package com.hiapk.regulate;

import com.hiapk.spearhead.Mapplication;
import com.hiapk.spearhead.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Regulate extends Activity {
	private static Button chooseBtn;
	private Button smsSend;
	// Button smsRead;
	private static TextView smsText;
	private static TextView smsNum;
	private static TextView smsResult;
	SharedPrefrenceDataRegulate sharedData;
	String city;
	String brand;
	SmsRead sr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regulate);
		// 为了退出。
		Mapplication.getInstance().addActivity(this);
		// MobclickAgent.onError(this);
		sharedData = new SharedPrefrenceDataRegulate(this);
		chooseBtn = (Button) findViewById(R.id.choose);
		smsSend = (Button) findViewById(R.id.smsSend);
		// smsRead = (Button)findViewById(R.id.smsRead);
		smsText = (TextView) findViewById(R.id.smsText);
		smsNum = (TextView) findViewById(R.id.smsNum);
		smsResult = (TextView) findViewById(R.id.smsResult);
		// city = sharedData.getCity();
		// brand = sharedData.getBrand();
		//
		// chooseBtn.setText(city + brand);
		// smsNum.setText(sharedData.getSmsNum());
		// smsText.setText(sharedData.getSmsText());

		chooseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Regulate.this, PhoneSet.class);
				startActivity(it);

			}
		});

		smsSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sms();
				// smsSend();

			}
		});

		// if(sharedData.getIsReceive()){
		// smsRead();
		// }

		// smsRead.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// smsRead();
		// }
		// });

	}

	public void sms() {
		String num = smsNum.getText().toString();
		String text = smsText.getText().toString();
		Log.v("+++++++++++++++++++++++++", text);
		// SmsManager sman = SmsManager.getDefault();
		// sman.sendTextMessage(num, null, text, null, null);
		Uri uri = Uri.parse("smsto:" + num);
		Intent it = new Intent(Intent.ACTION_VIEW, uri);
		it.putExtra("sms_body", text);
		startActivity(it);
	}

	public void smsSend() {

		String num = smsNum.getText().toString();
		String text = smsText.getText().toString();
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm.getSimState() == TelephonyManager.SIM_STATE_READY) {
			SmsManager sm;
			sm = SmsManager.getDefault();
			sm.sendTextMessage(num, null, text, null, null);
			Toast.makeText(this,
			// "已自动发送短信，短信接收后将自动设置本月已用流量"
					"短信已发送", Toast.LENGTH_SHORT).show();
			sharedData.setIsSend(true);
		} else {
			Toast.makeText(this, "请插入SIM卡或者关闭飞行模式", Toast.LENGTH_LONG).show();
		}

	}

	// public void smsRead(){
	//
	// sr.Sms(this);
	// if(sr.isRead){
	// smsResult.setText("短信内容可能有误，请手动设置");
	// }
	// Log.v("+++++++++++++++++++++", "读取短信");
	// Toast.makeText(this, sharedData.getIsReceive()+" ",
	// Toast.LENGTH_LONG).show();
	// }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		chooseBtn.setText(sharedData.getChooseCity());
		smsNum.setText(sharedData.getSmsNum());
		smsText.setText(sharedData.getSmsText());
		// sr = new SmsRead();
		// umeng
		// MobclickAgent.onResume(this);
	}

}
