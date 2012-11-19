package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceScreen;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PrefrenceSetting extends Activity {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	//
	CheckBoxPreference isNotifyOpen;
	CheckBoxPreference isfloatIndicatorOpen;
	CheckBoxPreference fireTip;
	ListPreference refreshFres;
	PreferenceScreen clearData;
	Context context = this;

	SharedPrefrenceData sharedData;
	ProgressDialog mydialog;
	// 几个大选项
	LinearLayout layout_notyfy;
	LinearLayout layout_float;
	LinearLayout layout_help_info;
	LinearLayout layout_freshplv;
	LinearLayout layout_autosave_firewall;
	LinearLayout layout_isfloat_touchable;
	RelativeLayout layout_shake_switch;
	LinearLayout layout_cleardata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		layout_notyfy = (LinearLayout) findViewById(R.id.setting_notify);
		layout_float = (LinearLayout) findViewById(R.id.setting_float);
		layout_help_info = (LinearLayout) findViewById(R.id.setting_help_message);
		layout_freshplv = (LinearLayout) findViewById(R.id.setting_freshplv);
		layout_autosave_firewall = (LinearLayout) findViewById(R.id.setting_auto_saveFirewalldata);
		layout_isfloat_touchable = (LinearLayout) findViewById(R.id.setting_isfloat_touchable);
		layout_shake_switch = (RelativeLayout) findViewById(R.id.setting_shake_switch);
		layout_cleardata = (LinearLayout) findViewById(R.id.setting_cleardata);
		PrefrenceBeen prefBeen = new PrefrenceBeen(context);
		prefBeen.initCheckBoxNotyfy(layout_notyfy);
		prefBeen.initCheckBoxFloat(layout_float);
		prefBeen.initCheckBoxHelpMessage(layout_help_info);
		prefBeen.initListBoxFresh(layout_freshplv);
		prefBeen.initCheckBoxAutoSaveFireWall(layout_autosave_firewall);
		prefBeen.initCheckBoxIsFloatTouchable(layout_isfloat_touchable);
		prefBeen.initCheckBoxShakeToSwitch(layout_shake_switch);
		prefBeen.initClickBoxDataClear(layout_cleardata);
		// 皮肤设置
		final ImageView back = (ImageView) findViewById(R.id.setting_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				back.setImageResource(R.drawable.back_black);
			}
		});
	}

	private void initScene() {
		FrameLayout title = (FrameLayout) findViewById(R.id.settingTitleBackground);
		title.setBackgroundResource(SkinCustomMains.titleBackground());
		layout_notyfy.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_float.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_help_info
				.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_freshplv.setBackgroundResource(SkinCustomMains.barsBackground());
		layout_autosave_firewall.setBackgroundResource(SkinCustomMains
				.barsBackground());
		layout_isfloat_touchable.setBackgroundResource(SkinCustomMains
				.barsBackground());
		layout_shake_switch.setBackgroundResource(SkinCustomMains
				.barsBackground());
		layout_cleardata
				.setBackgroundResource(SkinCustomMains.barsBackground());
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
