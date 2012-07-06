package com.hiapk.prefrencesetting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.hiapk.alertdialog.CustomDialogOtherBeen;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.customspinner.CustomSPBeen;
import com.hiapk.firewall.Block;
import com.hiapk.spearhead.R;

public class PrefrenceBeen {
	Context context;
	SharedPrefrenceDataWidget sharedDatawidget;

	public PrefrenceBeen(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		sharedDatawidget = new SharedPrefrenceDataWidget(context);
	}

	public void initListBoxFresh(LinearLayout layout_freshplv) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View listView = factory.inflate(R.layout.settings_listbox, null);
		final Button showText = (Button) listView
				.findViewById(R.id.setting_tv_box);
		showText.setText("刷新状态栏和小部件的频率");
		layout_freshplv.removeAllViews();
		layout_freshplv.addView(listView);
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomSPBeen customSpbenn = new CustomSPBeen(context);
				customSpbenn.dialogSettingFreshplv();
			}
		});

	}

	public void initClickBoxDataClear(LinearLayout layout_cleardata) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View clickView = factory
				.inflate(R.layout.settings_clickbox, null);
		final Button showText = (Button) clickView
				.findViewById(R.id.setting_tv_box);
		showText.setText("清空历史数据");
		layout_cleardata.removeAllViews();
		layout_cleardata.addView(clickView);
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		showText.setText("通知栏提示");
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
				// TODO Auto-generated method stub
				boolean isopen = sharedDatawidget.isNotifyOpen();
				AlarmSet alset = new AlarmSet();
				if (isopen) {
					alset.StopWidgetAlarm(context);
					sharedDatawidget.setNotifyOpen(false);
					checkBoxRightDrawChange(showText, isopen);
				} else {
					alset.StartWidgetAlarm(context);
					sharedDatawidget.setNotifyOpen(true);
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
		showText.setText("流量指示悬浮窗");
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
				// TODO Auto-generated method stub
				boolean isopen = sharedDatawidget.isFloatOpen();
				if (isopen) {
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
		showText.setText("显示软件提示信息");
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
				// TODO Auto-generated method stub
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

}
