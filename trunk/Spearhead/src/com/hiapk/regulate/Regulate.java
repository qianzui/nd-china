package com.hiapk.regulate;

import com.hiapk.spearhead.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Regulate extends Activity{
	Button chooseBtn;
	Button smsSend;
	public static TextView smsText;
	public static TextView smsNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regulate);		
		chooseBtn = (Button)findViewById(R.id.choose);
		smsSend = (Button)findViewById(R.id.smsSend);
		smsText = (TextView)findViewById(R.id.smsText);
		smsNum = (TextView)findViewById(R.id.smsNum);

		
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
				String num = smsNum.getText().toString();
				String text = smsText.getText().toString();				
				Uri uri = Uri.parse("smsto:"+num);
				Intent it = new Intent(Intent.ACTION_SENDTO,uri);		
				it.putExtra("sms_body", text);
				startActivity(it);
			}
		});
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		smsText.setText("onResume");
//
//		super.onResume();
//
//		smsNum.setText("super.onResume");
//	}
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		smsText.setText("onPause");
//		super.onPause();
//		smsNum.setText("super.onPause");
//	}
//	
}
