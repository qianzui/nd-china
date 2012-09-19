package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceDataRegulate;

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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Regulate extends Activity {
	private static Button chooseBtn;
	private Button smsSend;
	// Button smsRead;
	private static TextView smsText;
	private static TextView smsNum;
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
		SpearheadApplication.getInstance().addActivity(this);
		// MobclickAgent.onError(this);
		sharedData = new SharedPrefrenceDataRegulate(this);
		chooseBtn = (Button) findViewById(R.id.choose);
		smsSend = (Button) findViewById(R.id.smsSend);
		// smsRead = (Button)findViewById(R.id.smsRead);
		smsText = (TextView) findViewById(R.id.smsText);
		smsNum = (TextView) findViewById(R.id.smsNum);

		// 设置皮肤
		// city = sharedData.getCity();
		// brand = sharedData.getBrand();
		//
		// chooseBtn.setText(city + brand);
		// smsNum.setText(sharedData.getSmsNum());
		// smsText.setText(sharedData.getSmsText());

		final ImageView back = (ImageView) findViewById(R.id.regulate_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				back.setImageResource(R.drawable.back_black);
			}
		});
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
				Regulate.this.finish();
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
		initScene();
		// sr = new SmsRead();
		// umeng
		// MobclickAgent.onResume(this);
	}

	/**
	 * 皮肤
	 */
	public void initScene() {
		FrameLayout title = (FrameLayout) findViewById(R.id.regulateTitleBackground);
		title.setBackgroundResource(SkinCustomMains.buttonTitleBackground());
		smsSend.setBackgroundResource(SkinCustomMains.buttonBackgroundLight());
	}

}
