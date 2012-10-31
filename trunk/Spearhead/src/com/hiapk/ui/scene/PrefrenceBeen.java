package com.hiapk.ui.scene;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.widget.SetText;
import com.hiapk.firewall.Block;
import com.hiapk.logs.SaveRule;
import com.hiapk.spearhead.R;
import com.hiapk.ui.custom.CustomDialogOtherBeen;
import com.hiapk.ui.custom.CustomSPBeen;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;

public class PrefrenceBeen {
	Context context;
	SharedPrefrenceDataWidget sharedDatawidget;
	SharedPrefrenceData sharedDate;

	public PrefrenceBeen(Context context) {
		this.context = context;
		sharedDatawidget = new SharedPrefrenceDataWidget(context);
		sharedDate = new SharedPrefrenceData(context);
	}

	public void initListBoxFresh(LinearLayout layout_freshplv) {
		layout_freshplv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSpbenn = new CustomSPBeen(context);
				customSpbenn.dialogSettingFreshplv();
			}
		});

	}

	public void initClickBoxDataClear(LinearLayout layout_cleardata) {
		layout_cleardata.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogOtherBeen customOther = new CustomDialogOtherBeen(
						context);
				customOther.dialogConfirmClearData();
			}
		});
	}

	public void initCheckBoxNotyfy(LinearLayout layout_notyfy) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View boxView = factory.inflate(R.layout.settings_checkbox, null);
		final Button showText = (Button) boxView
				.findViewById(R.id.setting_tv_box);
		showText.setText(R.string.prefrence_setting_checkbox_notify);
		initScene(showText);
		boolean isopen = sharedDatawidget.isNotifyOpen();
		if (isopen) {
			checkBoxRightDrawinit(showText, isopen);
		} else {
			checkBoxRightDrawinit(showText, isopen);
		}
		layout_notyfy.removeAllViews();
		layout_notyfy.addView(boxView);
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = sharedDatawidget.isNotifyOpen();
				AlarmSet alset = new AlarmSet();
				if (isopen) {
					sharedDatawidget.setNotifyOpen(false);
					alset.StopWidgetAlarm(context);
					checkBoxRightDrawChange(showText, isopen);
				} else {
					sharedDatawidget.setNotifyOpen(true);
					SetText.resetWidgetAndNotify(context);
					alset.StartWidgetAlarm(context);
					checkBoxRightDrawChange(showText, isopen);
				}
			}
		});
	}

	public void initCheckBoxFloat(LinearLayout layout_float) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View boxView = factory.inflate(R.layout.settings_checkbox, null);
		final Button showText = (Button) boxView
				.findViewById(R.id.setting_tv_box);
		showText.setText(R.string.prefrence_setting_clickbox_floatwindow);
		initScene(showText);
		boolean isopen = sharedDatawidget.isFloatOpen();
		if (isopen) {
			checkBoxRightDrawinit(showText, isopen);
		} else {
			checkBoxRightDrawinit(showText, isopen);
		}
		layout_float.removeAllViews();
		layout_float.addView(boxView);
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = sharedDatawidget.isFloatOpen();
				if (isopen) {
					if (SetText.FloatIntX != 50) {
						sharedDatawidget.setIntX(SetText.FloatIntX);
					}
					if (SetText.FloatIntY != 50) {
						sharedDatawidget.setIntY(SetText.FloatIntY);
					}
					context.stopService(new Intent("com.hiapk.server"));
					checkBoxRightDrawChange(showText, isopen);
					sharedDatawidget.setFloatOpen(false);
				} else {
					context.startService(new Intent("com.hiapk.server"));
					checkBoxRightDrawChange(showText, isopen);
					sharedDatawidget.setFloatOpen(true);
				}
			}
		});
	}

	public void initCheckBoxHelpMessage(LinearLayout layout_help_info) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View boxView = factory.inflate(R.layout.settings_checkbox, null);
		final Button showText = (Button) boxView
				.findViewById(R.id.setting_tv_box);
		showText.setText(R.string.prefrence_setting_clickbox_help);
		initScene(showText);
		boolean isopen = Block.fireTip(context);
		if (isopen) {
			checkBoxRightDrawinit(showText, isopen);
		} else {
			checkBoxRightDrawinit(showText, isopen);
		}
		layout_help_info.removeAllViews();
		layout_help_info.addView(boxView);
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = Block.fireTip(context);
				if (isopen) {
					Block.fireTipSet(context, false);
					checkBoxRightDrawChange(showText, isopen);
				} else {
					Block.fireTipSet(context, true);
					checkBoxRightDrawChange(showText, isopen);
				}
			}
		});
	}

	public void initCheckBoxAutoSaveFireWall(
			LinearLayout layout_auto_save_firewall) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View boxView = factory.inflate(R.layout.settings_checkbox, null);
		final Button showText = (Button) boxView
				.findViewById(R.id.setting_tv_box);
		showText.setText(R.string.prefrence_setting_auto_save_firewall);
		initScene(showText);
		boolean isopen = sharedDate.isAutoSaveFireWallRule();
		if (isopen) {
			checkBoxRightDrawinit(showText, isopen);
		} else {
			checkBoxRightDrawinit(showText, isopen);
		}
		layout_auto_save_firewall.removeAllViews();
		layout_auto_save_firewall.addView(boxView);
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sharedDate.isHasknowFireWallSave()) {
					boolean isopen = sharedDate.isAutoSaveFireWallRule();
					if (isopen) {
						sharedDate.setisAutoSaveFireWallRule(false);
						checkBoxRightDrawChange(showText, isopen);
						SaveRule sr = new SaveRule(context);
						sr.deleteRecord();
					} else {
						sharedDate.setisAutoSaveFireWallRule(true);
						checkBoxRightDrawChange(showText, isopen);
					}
				} else {
					CustomDialogOtherBeen customdia = new CustomDialogOtherBeen(
							context);
					customdia.dialogisHasknowFirewallRuleSave();
				}
			}
		});

	}

	private void checkBoxRightDrawinit(Button btn, boolean on_off) {
		if (!on_off) {
			btn.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_close, 0);
		} else {
			btn.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_open, 0);
		}
	}

	private void checkBoxRightDrawChange(Button btn, boolean on_off) {
		if (on_off) {
			btn.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_close, 0);
		} else {
			btn.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_open, 0);
		}
	}

	/**
	 * …Ë÷√∆§∑Ù
	 * 
	 * @param btn
	 */
	private void initScene(Button btn) {
		btn.setBackgroundResource(SkinCustomMains.barsBackground());
	}
}
