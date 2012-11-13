package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomMains;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShakePreferenceSetting extends Activity {
	
	LinearLayout layout_shake_button;
	RelativeLayout layout_shake_sensor;
	FrameLayout shakeSettingTitleBackground;
	ImageView shake_setting_back;
	ImageView setting_sensor_image;
	TextView setting_sensor_text;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_shake);
		layout_shake_button = (LinearLayout)findViewById(R.id.setting_shake_button);
		layout_shake_sensor = (RelativeLayout)findViewById(R.id.setting_shake_sensor);
		shakeSettingTitleBackground = (FrameLayout)findViewById(R.id.shakeSettingTitleBackground);
		shake_setting_back = (ImageView) findViewById(R.id.shake_setting_back);
		setting_sensor_image = (ImageView)findViewById(R.id.setting_sensor_image);
		setting_sensor_text = (TextView)findViewById(R.id.setting_sensor_text);
		initScene();
		PrefrenceBeen prefBeen = new PrefrenceBeen(context);
		prefBeen.initCheckBoxAutoShakeEnableButton(layout_shake_button,setting_sensor_image,setting_sensor_text);
		prefBeen.initShakeSensorSetting(layout_shake_sensor);
		
	}
	private void initScene() {
		shakeSettingTitleBackground.setBackgroundResource(SkinCustomMains.titleBackground());
		layout_shake_button.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_shake_sensor.setBackgroundResource(SkinCustomMains.barsBackground());
		shake_setting_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initScene();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
