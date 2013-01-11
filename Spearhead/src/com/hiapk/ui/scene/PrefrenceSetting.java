package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PrefrenceSetting extends Activity {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	//
	private Context context = this;

	SharedPrefrenceData sharedData;
	ProgressDialog mydialog;
	// 几个大选项
	TextView tv_notyfy;
	TextView tv_float;
	TextView tv_help_info;
	TextView tv_freshplv;
	TextView tv_autosave_firewall;
	TextView tv_isfloat_touchable;
	TextView tv_shake_switch;
	TextView tv_cleardata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		tv_notyfy = (TextView) findViewById(R.id.setting_notify);
		tv_float = (TextView) findViewById(R.id.setting_float);
		tv_help_info = (TextView) findViewById(R.id.setting_help_message);
		tv_freshplv = (TextView) findViewById(R.id.setting_freshplv);
		tv_autosave_firewall = (TextView) findViewById(R.id.setting_auto_saveFirewalldata);
		tv_isfloat_touchable = (TextView) findViewById(R.id.setting_isfloat_touchable);
		tv_shake_switch = (TextView) findViewById(R.id.setting_shake_switch);
		tv_cleardata = (TextView) findViewById(R.id.setting_cleardata);
		PrefrenceBeen prefBeen = new PrefrenceBeen(context);
		prefBeen.initCheckBoxNotyfy(tv_notyfy);
		prefBeen.initCheckBoxFloat(tv_float,tv_isfloat_touchable);
		prefBeen.initCheckBoxHelpMessage(tv_help_info);
		prefBeen.initListBoxFresh(tv_freshplv);
		prefBeen.initCheckBoxAutoSaveFireWall(tv_autosave_firewall);
		prefBeen.initCheckBoxIsFloatTouchable(tv_isfloat_touchable);
		prefBeen.initCheckBoxShakeToSwitch(tv_shake_switch);
		prefBeen.initClickBoxDataClear(tv_cleardata);
		// 皮肤设置
		final ImageView back = (ImageView) findViewById(R.id.setting_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				back.setImageResource(R.drawable.back_black);
			}
		});
	}

	private void initScene() {
		FrameLayout title = (FrameLayout) findViewById(R.id.settingTitleBackground);
		title.setBackgroundResource(SkinCustomMains.titleBackground());
		tv_notyfy.setBackgroundResource(SkinCustomMains.barsBackground());
		tv_float.setBackgroundResource(SkinCustomMains.barsBackground());
		tv_help_info.setBackgroundResource(SkinCustomMains.barsBackground());
		tv_freshplv.setBackgroundResource(SkinCustomMains.barsBackground());
		tv_autosave_firewall.setBackgroundResource(SkinCustomMains
				.barsBackground());
		tv_isfloat_touchable.setBackgroundResource(SkinCustomMains
				.barsBackground());
		tv_shake_switch.setBackgroundResource(SkinCustomMains.barsBackground());
		tv_cleardata.setBackgroundResource(SkinCustomMains.barsBackground());
	}

	@Override
	protected void onResume() {
		super.onResume();
		initScene();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
