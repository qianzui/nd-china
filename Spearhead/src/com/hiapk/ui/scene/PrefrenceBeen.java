package com.hiapk.ui.scene;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.widget.FloatWindowOperator;
import com.hiapk.control.widget.SetText;
import com.hiapk.firewall.Block;
import com.hiapk.logs.Logs;
import com.hiapk.logs.SaveRule;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.ui.custom.CustomDialogOtherBeen;
import com.hiapk.ui.custom.CustomSPBeen;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;

public class PrefrenceBeen {
	private Context context;
	private SharedPrefrenceDataWidget sharedDatawidget;
	private SharedPrefrenceData sharedDate;
	private String TAG = "PrefrenceBeen";

	public PrefrenceBeen(Context context) {
		this.context = context;
		sharedDatawidget = new SharedPrefrenceDataWidget(context);
		sharedDate = SpearheadApplication.getInstance().getsharedData();
	}

	public void initListBoxFresh(final TextView tv_freshplv) {
		tv_freshplv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSpbenn = new CustomSPBeen(context);
				customSpbenn.dialogSettingFreshplv();
			}
		});

	}

	public void initClickBoxDataClear(final TextView tv_cleardata) {
		tv_cleardata.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogOtherBeen customOther = new CustomDialogOtherBeen(
						context);
				customOther.dialogConfirmClearData();
			}
		});
	}

	public void initCheckBoxNotyfy(final TextView tv_notyfy) {
		boolean isopen = sharedDatawidget.isNotifyOpen();
		if (isopen) {
			checkBoxRightDrawinit(tv_notyfy, isopen);
		} else {
			checkBoxRightDrawinit(tv_notyfy, isopen);
		}
		tv_notyfy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = sharedDatawidget.isNotifyOpen();
				AlarmSet alset = new AlarmSet();
				if (isopen) {
					sharedDatawidget.setNotifyOpen(false);
					alset.StopWidgetAlarm(context);
					checkBoxRightDrawChange(tv_notyfy, isopen);
				} else {
					sharedDatawidget.setNotifyOpen(true);
					SetText.resetWidgetAndNotify(context);
					alset.StartWidgetAlarm(context);
					checkBoxRightDrawChange(tv_notyfy, isopen);
				}
			}
		});
	}

	public void initCheckBoxFloat(final TextView tv_float,
			final TextView tv_floatUnTouchable) {
		boolean isopen = sharedDatawidget.isFloatOpen();
		if (isopen) {
			checkBoxRightDrawinit(tv_float, isopen);
		} else {
			checkBoxRightDrawinit(tv_float, isopen);
		}
		tv_float.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = sharedDatawidget.isFloatOpen();
				boolean isUnTouchable = sharedDatawidget.isFloatUnTouchable();
				TextView float_untouchable = (TextView) tv_floatUnTouchable
						.findViewById(R.id.setting_isfloat_touchable);
				if (isopen) {
					FloatWindowOperator.saveXYvalue(context);
					context.stopService(new Intent("com.hiapk.server"));
					checkBoxRightDrawChange(tv_float, isopen);
					sharedDatawidget.setFloatOpen(false);
					// 关联的固定悬浮窗
					float_untouchable.setTextColor(Color.GRAY);
					if (isUnTouchable) {
						checkBoxRightDrawGraySet(tv_floatUnTouchable, true);
					}
				} else {
					context.startService(new Intent("com.hiapk.server"));
					checkBoxRightDrawChange(tv_float, isopen);
					sharedDatawidget.setFloatOpen(true);
					// 关联的固定悬浮窗
					float_untouchable.setTextColor(context.getResources()
							.getColor(R.color.darkgray2));
					if (isUnTouchable) {
						checkBoxRightDrawGraySet(tv_floatUnTouchable, false);
					}
				}
			}
		});
	}

	public void initCheckBoxHelpMessage(final TextView tv_help_info) {
		boolean isopen = Block.fireTip(context);
		if (isopen) {
			checkBoxRightDrawinit(tv_help_info, isopen);
		} else {
			checkBoxRightDrawinit(tv_help_info, isopen);
		}
		tv_help_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = Block.fireTip(context);
				if (isopen) {
					Block.fireTipSet(context, false);
					checkBoxRightDrawChange(tv_help_info, isopen);
				} else {
					Block.fireTipSet(context, true);
					checkBoxRightDrawChange(tv_help_info, isopen);
				}
			}
		});
	}

	public void initCheckBoxAutoSaveFireWall(
			final TextView tv_auto_save_firewall) {
		boolean isopen = sharedDate.isAutoSaveFireWallRule();
		if (isopen) {
			checkBoxRightDrawinit(tv_auto_save_firewall, isopen);
		} else {
			checkBoxRightDrawinit(tv_auto_save_firewall, isopen);
		}
		tv_auto_save_firewall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sharedDate.isHasknowFireWallSave()) {
					boolean isopen = sharedDate.isAutoSaveFireWallRule();
					if (isopen) {
						sharedDate.setisAutoSaveFireWallRule(false);
						checkBoxRightDrawChange(tv_auto_save_firewall, isopen);
						SaveRule sr = new SaveRule(context);
						sr.deleteRecord();
					} else {
						sharedDate.setisAutoSaveFireWallRule(true);
						checkBoxRightDrawChange(tv_auto_save_firewall, isopen);
					}
				} else {
					CustomDialogOtherBeen customdia = new CustomDialogOtherBeen(
							context);
					customdia.dialogisHasknowFirewallRuleSave();
				}
			}
		});

	}

	public void initCheckBoxIsFloatTouchable(
			final TextView tv_is_float_touchable) {
		boolean isopen = sharedDatawidget.isFloatUnTouchable();
		if (isopen) {
			checkBoxRightDrawinit(tv_is_float_touchable, isopen);
		} else {
			checkBoxRightDrawinit(tv_is_float_touchable, isopen);
		}
		tv_is_float_touchable.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isopen = sharedDatawidget.isFloatOpen();
				boolean isFloat = sharedDatawidget.isFloatUnTouchable();
				if (isopen) {
					if (isFloat) {
						sharedDatawidget.setFloatUnTouchable(false);
						checkBoxRightDrawChange(tv_is_float_touchable, isFloat);
					} else {
						sharedDatawidget.setFloatUnTouchable(true);
						checkBoxRightDrawChange(tv_is_float_touchable, isFloat);
					}
					Intent intent = new Intent("com.hiapk.server");
					context.startService(intent);
					Logs.d(TAG, "intent=com.hiapk.server");
				}
			}
		});

	}

	public void initCheckBoxShakeToSwitch(final TextView tv_shake_switch) {
		tv_shake_switch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!sharedDate.isKnowShakeToSwitch()) {
					CustomDialogOtherBeen customdia = new CustomDialogOtherBeen(
							context);
					customdia.dialogisKnowShakeToSwitch();
				} else {
					Intent intent = new Intent();
					intent.setClass(context, ShakePreferenceSetting.class);
					context.startActivity(intent);
				}
			}
		});
	}

	public void initCheckBoxAutoShakeEnableButton(
			LinearLayout layout_shake_button,
			final ImageView setting_sensor_image,
			final TextView setting_sensor_text) {
		LayoutInflater factory = LayoutInflater.from(context);
		final View boxView = factory.inflate(R.layout.settings_checkbox, null);
		final Button showText = (Button) boxView
				.findViewById(R.id.setting_tv_box);
		showText.setText(R.string.prefrence_setting_enable_switch);
		initScene(showText);
		boolean isShake = sharedDate.isShakeToSwitch();
		if (isShake) {
			sensorColorchange(setting_sensor_image, setting_sensor_text, true);
			checkBoxRightDrawinit(showText, isShake);
		} else {
			sensorColorchange(setting_sensor_image, setting_sensor_text, false);
			checkBoxRightDrawinit(showText, isShake);
		}
		layout_shake_button.removeAllViews();
		layout_shake_button.addView(boxView, new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		showText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isShake = sharedDate.isShakeToSwitch();
				if (isShake) {
					sharedDate.setIsShakeToSwitch(false);
					sensorColorchange(setting_sensor_image,
							setting_sensor_text, false);
					checkBoxRightDrawChange(showText, isShake);
				} else {
					sharedDate.setIsShakeToSwitch(true);
					sensorColorchange(setting_sensor_image,
							setting_sensor_text, true);
					checkBoxRightDrawChange(showText, isShake);
				}
			}
		});
	}

	public void initShakeSensorSetting(RelativeLayout layout_shake_sensor) {
		layout_shake_sensor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sharedDate.isShakeToSwitch()) {
					Intent intent = new Intent();
					intent.setClass(context, ShakeSensorSetting.class);
					context.startActivity(intent);
				} else {
				}
			}
		});
	}

	private void sensorColorchange(final ImageView setting_sensor_image,
			final TextView setting_sensor_text, boolean isEnable) {
		setting_sensor_image
				.setBackgroundResource(isEnable ? R.drawable.arrow_setting
						: R.drawable.arrow_setting_gray);
		setting_sensor_text.setTextColor(isEnable ? context.getResources()
				.getColor(R.color.darkgray2) : Color.GRAY);
	}

	private void checkBoxRightDrawinit(TextView tv, boolean on_off) {
		if (!on_off) {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_close, 0);
		} else {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_open, 0);
		}
	}

	private void checkBoxRightDrawChange(TextView tv, boolean on_off) {
		if (on_off) {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_close, 0);
		} else {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_open, 0);
		}
	}

	private void checkBoxRightDrawGraySet(TextView tv, boolean isGray) {
		if (isGray) {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_open_gray, 0);
		} else {
			tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.check_open, 0);
		}
	}

	/**
	 * 设置皮肤
	 * 
	 * @param btn
	 */
	private void initScene(Button btn) {
		btn.setBackgroundResource(SkinCustomMains.barsBackground());
	}
}
