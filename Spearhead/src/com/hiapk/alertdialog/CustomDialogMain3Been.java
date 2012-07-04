package com.hiapk.alertdialog;

import java.text.DecimalFormat;
import java.text.Format;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.PhoneSet;
import com.hiapk.regulate.Regulate;
import com.hiapk.spearhead.Main3;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomDialogMain3Been {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 总流量long
	String VALUE_MOBILE_SET = "mobilemonthuse";
	// 显示在预警页面的int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// 设置单位（月度设置）
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 设置结算日期及结算日期的设施时间，日期等
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// 已使用总流量int
	String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	// 设置单位（已使用）
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// 已使用总流量long
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	// 流量预警
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	String WARNING_ACTION = "warningaction";
	Context context;
	DecimalFormat format;

	public CustomDialogMain3Been(Context context) {
		this.context = context;
		format = new DecimalFormat("0.##");
	}

	/**
	 * 本月已用弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogMonthHasUsed(final Button btn_HasUsed) {
		// TODO Auto-generated method stub
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		final DecimalFormat format = new DecimalFormat("0.##");
		int mobileUseUnit = sharedData.getMonthHasUsedUnit();
		// float mobileUsefloat = sharedData.getMonthMobileHasUseOffloat();
		long mobileUselong = TrafficManager.getMonthUseMobile(context);
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_dialog_on_main_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		final Spinner spin_unit = (Spinner) textEntryView
				.findViewById(R.id.sp_unit);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(
				context, R.array.unit, R.layout.sptext_on_alert);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_unit.setAdapter(adp);
		// 初始化数值
		spin_unit.setSelection(mobileUseUnit);
		int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();
		// 若数值为0，则显示空
		if (mobileUselong != 0) {
			String mobileUseString;
			if (mobileHasUsedUnit == 0) {
				mobileUseString = format
						.format((double) (mobileUselong / 1024) / 1024);
			} else {
				mobileUseString = format
						.format((double) (mobileUselong / 1024) / 1024 / 1024);
			}

			et_month.setText(mobileUseString);
			et_month.setSelection(String.valueOf(mobileUseString).length());
		} else {
			et_month.setText("");
		}

		final CustomDialog monthHasUsedAlert = new CustomDialog.Builder(context)
				.setTitle("请设置本月已用流量").setContentView(textEntryView)
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create();
		monthHasUsedAlert.show();

		Button btn_other = (Button) monthHasUsedAlert
				.findViewById(R.id.otherButton);
		btn_other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthHasUsedAlert.dismiss();
				Intent intent = new Intent(context, Regulate.class);
				context.startActivity(intent);

			}
		});
		Button btn_ok = (Button) monthHasUsedAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 输入的数值
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					// TODO: handle exception
					showlog(i + "shuziError" + et_month.getText().toString());
				}

				// btn_Used.setText(format.format(i));
				int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				// Log.d("main3", i + "");
				//
				//

				if (mobileHasUsedUnit == 0) {
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
							(long) (i * 1048576));
					sharedData.setMonthHasUsedStack((long) (i * 1048576));
				} else {
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
							(long) (i * 1048576 * 1024));
					sharedData.setMonthHasUsedStack((long) (i * 1048576 * 1024));
				}
				passfileEditor.putFloat(VALUE_MOBILE_HASUSED_OF_FLOAT, i);

				passfileEditor.putInt(MOBILE_HASUSED_SET_UNIT,
						mobileHasUsedUnit);
				passfileEditor.commit();// 委托，存入数据
				// commitUsedTrafficTime();
				// init_btn_HasUsed();
				/* User clicked OK so do some stuff */
				long hasusedlong = sharedData.getMonthMobileHasUse();
				long setlong = sharedData.getMonthMobileSetOfLong();
				if (hasusedlong > setlong) {
					CustomDialogOtherBeen customOther = new CustomDialogOtherBeen(
							context);
					customOther.dialogHasUsedLongTooMuch();
				}
				SetText.resetWidgetAndNotify(context);
				PrefrenceOperatorUnit.resetHasWarning(context);
				// 赋值
				long month_used = TrafficManager.getMonthUseMobile(context);
				btn_HasUsed.setText(UnitHandler.unitHandler(month_used));
				monthHasUsedAlert.dismiss();

			}
		});
		Button btn_cancel = (Button) monthHasUsedAlert
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthHasUsedAlert.dismiss();
			}
		});

	}

	/**
	 * 月度显示弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogMonthSet_Main3(final Button btn_month,
			final Button dayWarning, final Button monthWarning) {
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int mobileUnit = sharedData.getMonthMobileSetUnit();
		float mobileSetFloat = sharedData.getMonthMobileSetOfFloat();
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_dialog_on_main_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		final Spinner spin_unit = (Spinner) textEntryView
				.findViewById(R.id.sp_unit);
		ArrayAdapter<CharSequence> adp = ArrayAdapter.createFromResource(
				context, R.array.unit, R.layout.sptext_on_alert);
		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin_unit.setAdapter(adp);
		// 判断0
		if (mobileSetFloat != 0) {
			et_month.setText(mobileSetFloat + "");
			int lenth = String.valueOf(mobileSetFloat).length();
			if (lenth > 6) {
				lenth = 6;
			}
			et_month.setSelection(lenth);
		} else {
			et_month.setText("");
		}
		// 初始化数值
		spin_unit.setSelection(mobileUnit);
		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("请设置每月流量限额").setContentView(textEntryView)
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create();
		monthSetAlert.show();

		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 输入的数值
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					// TODO: handle exception
					i = 0;
				}
				showlog(i + "");
				int mobileUnit = spin_unit.getSelectedItemPosition();
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				// Log.d("main3", i + "");

				if (mobileUnit == 0) {
					long monthsetTraffMB = (long) (i * 1024 * 1024);
					// showlog(monthsetTraffMB + "");
					sharedData.setMonthMobileSetOfLong(monthsetTraffMB);
					passfileEditor.putLong(VALUE_MOBILE_SET, monthsetTraffMB);
					passfileEditor.putLong(MOBILE_WARNING_MONTH,
							monthsetTraffMB * 9 / 10);
					passfileEditor.putLong(MOBILE_WARNING_DAY,
							monthsetTraffMB / 10);
					// 设置会改变的3个数值。++
					btn_month.setText(UnitHandler.unitHandler(monthsetTraffMB));
					monthWarning.setText(UnitHandler
							.unitHandler(monthsetTraffMB * 9 / 10));
					dayWarning.setText(UnitHandler
							.unitHandler(monthsetTraffMB / 10));
				} else if (mobileUnit == 1) {
					long monthsetTraffGB = (long) (i * 1024 * 1024 * 1024);
					// showlog(monthsetTraffGB + "");
					passfileEditor.putLong(VALUE_MOBILE_SET, monthsetTraffGB);
					passfileEditor.putLong(MOBILE_WARNING_MONTH,
							monthsetTraffGB * 9 / 10);
					passfileEditor.putLong(MOBILE_WARNING_DAY,
							monthsetTraffGB / 10);
					// 设置会改变的3个数值。++
					btn_month.setText(UnitHandler.unitHandler(monthsetTraffGB));
					dayWarning.setText(UnitHandler
							.unitHandler(monthsetTraffGB * 9 / 10));
					monthWarning.setText(UnitHandler
							.unitHandler(monthsetTraffGB / 10));
				}
				passfileEditor.putInt(MOBILE_SET_UNIT, mobileUnit);
				sharedData.setMonthMobileSetOfFloat(i);
				// passfileEditor.putFloat(VALUE_MOBILE_SET_OF_INT, i);
				passfileEditor.commit();// 委托，存入数据
				SetText.resetWidgetAndNotify(context);
				// showlog(mobileSetLong + "");
				SharedPrefrenceData sharedData = new SharedPrefrenceData(
						context);
				// long monthSetofLong = sharedData.getMonthMobileSetOfLong();
				if (i == 0) {
					sharedData.setMonthSetHasSet(false);
				} else {
					sharedData.setMonthSetHasSet(true);
				}
				PrefrenceOperatorUnit.resetHasWarning(context);
				monthSetAlert.dismiss();
				/* User clicked OK so do some stuff */
				// MobclickAgent.onEvent(context, "monthUse",
				// String.valueOf(i));
			}
		});

		Button btn_cancel = (Button) monthSetAlert
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				monthSetAlert.dismiss();
			}
		});
	}

	/**
	 * 月度预警弹出的对话框
	 */
	public void dialogMonthWarning(final Button button) {
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		LayoutInflater factory = LayoutInflater.from(context);
		View textEntryView = factory.inflate(
				R.layout.month_warning_set_alert_dialog, null);
		// 流量预警设置窗口上方的文本
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		tv_month_Traff.setTextSize(20);
		// tv_month_Traff.setTextColor(Color.BLACK);
		// tv_month_warning.setText("月度预警流量：");
		// tv_month_warning.setGravity(Gravity.CENTER);
		// 设置拖动进度条
		final SeekBar seekbar_warning = (SeekBar) textEntryView
				.findViewById(R.id.probar_warning_alert);
		// 包月流量
		final long monthset = sharedData.getMonthMobileSetOfLong();
		// final int monthset_MB = (int) (monthset / 1024 / 1024);
		long warningMonthset = sharedData.getAlertWarningMonth();
		if (monthset != 0) {
			// 进行初始化
			// 流量数值前方的说明文字
			final String text = "";
			seekbar_warning
					.setProgress((int) (warningMonthset * 100 / monthset));
			tv_month_Traff.setText(text
					+ UnitHandler.unitHandler(warningMonthset));
			seekbar_warning
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							// TODO Auto-generated method stub
							tv_month_Traff.setText(text
									+ UnitHandler.unitHandler(monthset
											* progress / 100));
						}
					});
			final CustomDialog monthWarning = new CustomDialog.Builder(context)
					.setTitle("月流量达到下列数值报警").setContentView(textEntryView)
					.setPositiveButton("确定", null).setwindowHeight(0.35)
					.setNegativeButton("取消", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 输入的数值
					Editor UseEditor = context.getSharedPreferences(PREFS_NAME,
							0).edit();
					int progre = seekbar_warning.getProgress();
					long newmonthset = monthset * progre / 100;
					// 最小值1M
					UseEditor.putLong(MOBILE_WARNING_MONTH, newmonthset);
					UseEditor.commit();
					// 设置数值
					long mobileWarning = sharedData.getAlertWarningMonth();
					// float
					// a=Float.valueOf(mobileWarning).floatValue();
					button.setText(UnitHandler.unitHandler(mobileWarning));
					// 重置预警状态
					PrefrenceOperatorUnit.resetHasWarning(context);
					monthWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) monthWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					monthWarning.dismiss();
				}
			});
		} else {
			final CustomDialog monthWarning = new CustomDialog.Builder(context)
					.setTitle("注意！").setMessage("您还没有设置流量套餐，请进行包月流量设置。")
					.setwindowHeight(0.35)
					// .setView(textEntryView)
					.setPositiveButton("确定", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 输入的数值
					monthWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) monthWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					monthWarning.dismiss();
				}
			});
		}

	}

	/**
	 * 日流量预警对话框
	 * 
	 * @param button
	 * @return
	 */
	public void dialogDayWarning(final Button button) {
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		LayoutInflater factory = LayoutInflater.from(context);
		View textEntryView = factory.inflate(
				R.layout.month_warning_set_alert_dialog, null);
		// final TextView tv_day_warning = (TextView) textEntryView
		// .findViewById(R.id.tv_warning_alert);
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		tv_month_Traff.setTextSize(20);
		// tv_month_Traff.setTextColor(Color.BLACK);
		// tv_day_warning.setText("日预警流量：");
		// tv_day_warning.setGravity(Gravity.CENTER);
		// 设置拖动进度条
		final SeekBar seekbar_warning = (SeekBar) textEntryView
				.findViewById(R.id.probar_warning_alert);
		// 包月流量
		long warningDayset = sharedData.getAlertWarningDay();
		final long monthset = sharedData.getMonthMobileSetOfLong();
		if (monthset != 0) {
			// 进行初始化
			final String text = "";
			seekbar_warning.setProgress((int) (warningDayset * 100 / monthset));
			tv_month_Traff.setText(text
					+ UnitHandler.unitHandler(warningDayset));
			seekbar_warning
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

						@Override
						public void onStopTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
							// TODO Auto-generated method stub
							tv_month_Traff.setText(text
									+ UnitHandler.unitHandler(progress
											* monthset / 100));
						}
					});
			final CustomDialog dayWarning = new CustomDialog.Builder(context)
					.setTitle("日流量达到下列数值报警").setContentView(textEntryView)
					.setPositiveButton("确定", null).setwindowHeight(0.35)
					.setNegativeButton("取消", null).create();
			dayWarning.show();
			Button btn_ok = (Button) dayWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 输入的数值
					Editor UseEditor = context.getSharedPreferences(PREFS_NAME,
							0).edit();
					int progre = seekbar_warning.getProgress();
					// 最小值1M
					long newdayset = monthset * progre / 100;
					UseEditor.putLong(MOBILE_WARNING_DAY, newdayset);
					UseEditor.commit();
					long mobileWarning = sharedData.getAlertWarningDay();
					// float
					// a=Float.valueOf(mobileWarning).floatValue();
					button.setText(UnitHandler.unitHandler(mobileWarning));
					// init_dayWarning();
					// 重置预警状态
					PrefrenceOperatorUnit.resetHasWarning(context);
					dayWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) dayWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dayWarning.dismiss();
				}
			});
		} else {
			final CustomDialog monthWarning = new CustomDialog.Builder(context)
					.setTitle("注意！").setMessage("您还没有设置流量套餐，请进行包月流量设置。")
					.setwindowHeight(0.35)
					// .setView(textEntryView)
					.setPositiveButton("确定", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					monthWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) monthWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					monthWarning.dismiss();
				}
			});
		}

	}

	// /**
	// * 本月已用弹出窗口
	// *
	// * @return
	// */
	// public void dialogCountDaySelected() {
	// final CustomDialog dayWarning = new CustomDialog.Builder(context)
	// .setTitle("设置结算日后建议重新进行流量校准")
	// // .setView(textEntryView)
	// .setPositiveButton("确定", null).setNegativeButton("取消", null)
	// .create();
	// dayWarning.show();
	// Button btn_ok = (Button) dayWarning.findViewById(R.id.positiveButton);
	// btn_ok.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dayWarning.dismiss();
	// }
	// });
	// Button btn_cancel = (Button) dayWarning
	// .findViewById(R.id.negativeButton);
	// btn_cancel.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dayWarning.dismiss();
	// }
	// });
	// }

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("CustomDialogMain3Been", string);
	}
}
