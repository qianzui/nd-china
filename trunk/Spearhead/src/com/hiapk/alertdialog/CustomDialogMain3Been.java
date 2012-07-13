package com.hiapk.alertdialog;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.InputType;
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

import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomDialogMain3Been {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// 总流量long
	private String VALUE_MOBILE_SET = "mobilemonthuse";
	// 设置单位（月度设置）
	private String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 流量预警
	private String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	private String MOBILE_WARNING_DAY = "mobiledaywarning";
	private Context context;

	public CustomDialogMain3Been(Context context) {
		this.context = context;
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
		et_month.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL ); 
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
					monthWarning.setText(UnitHandler
							.unitHandler(monthsetTraffGB * 9 / 10));
					dayWarning.setText(UnitHandler
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
		final EditText et_month_Traff = (EditText) textEntryView
				.findViewById(R.id.et_show_Traff);
		et_month_Traff.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		final TextView tv_percent = (TextView) textEntryView
				.findViewById(R.id.tv_percent);
		tv_month_Traff.setTextSize(20);
		et_month_Traff.setTextSize(20);
		tv_percent.setTextSize(20);
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
			if (warningMonthset != 0) {
				seekbar_warning
						.setProgress((int) (warningMonthset * 100 / monthset));
				et_month_Traff.setText(text
						+ UnitHandler.unitHandler(warningMonthset,
								tv_month_Traff));
				// 移动符号位
				et_month_Traff.setSelection(et_month_Traff.getText().toString()
						.length());
			} else {
				seekbar_warning.setProgress((int) (0));
				et_month_Traff.setText(text);
				tv_month_Traff.setText(" MB");
			}
			tv_percent.setText("( " + (int) (warningMonthset * 100 / monthset)
					+ "% )");
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
							et_month_Traff.setText(text
									+ UnitHandler.unitHandler(monthset
											* progress / 100, tv_month_Traff));
							et_month_Traff.setSelection(et_month_Traff
									.getText().toString().length());
							tv_percent.setText("( " + progress + "% )");
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
					float i = 0;
					try {
						i = Float.valueOf(et_month_Traff.getText().toString());
					} catch (NumberFormatException e) {
						// TODO: handle exception
						i = 0;
					}
					long newmonthset = 0;
					if (tv_month_Traff.getText() == " KB") {
						newmonthset = (long) (i * 1024);
					} else if (tv_month_Traff.getText() == " MB") {
						newmonthset = (long) (i * 1024 * 1024);
					} else if (tv_month_Traff.getText() == " GB") {
						newmonthset = (long) (i * 1024 * 1024 * 1024);
					} else if (tv_month_Traff.getText() == " TB") {
						newmonthset = (long) (i * 1024 * 1024 * 1024 * 1024);
					}
					// int progre = seekbar_warning.getProgress();
					if (newmonthset > monthset) {
						final CustomDialog monthWarning = new CustomDialog.Builder(
								context).setTitle("注意！")
								.setMessage("您设置的本月预警流量超过了包月套餐！")
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
					} else {
						// 最小值1M
						UseEditor.putLong(MOBILE_WARNING_MONTH, newmonthset);
						UseEditor.commit();
						// 设置数值
						long mobileWarning = sharedData.getAlertWarningMonth();
						// float
						// a=Float.valueOf(mobileWarning).floatValue();
						button.setText(UnitHandler.unitHandler(mobileWarning));
					}
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
		// 流量预警设置窗口上方的文本
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		final EditText et_month_Traff = (EditText) textEntryView
				.findViewById(R.id.et_show_Traff);
		et_month_Traff.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		final TextView tv_percent = (TextView) textEntryView
				.findViewById(R.id.tv_percent);
		tv_month_Traff.setTextSize(20);
		et_month_Traff.setTextSize(20);
		tv_percent.setTextSize(20);
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
			if (warningDayset != 0) {
				seekbar_warning
						.setProgress((int) (warningDayset * 100 / monthset));
				et_month_Traff.setText(text
						+ UnitHandler
								.unitHandler(warningDayset, tv_month_Traff));
				// 移动符号位
				et_month_Traff.setSelection(et_month_Traff.getText().toString()
						.length());
			} else {
				seekbar_warning.setProgress((int) (0));
				et_month_Traff.setText(text);
				tv_month_Traff.setText(" MB");
			}
			tv_percent.setText("( " + (int) (warningDayset * 100 / monthset)
					+ "% )");
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
							et_month_Traff.setText(text
									+ UnitHandler.unitHandler(monthset
											* progress / 100, tv_month_Traff));
							et_month_Traff.setSelection(et_month_Traff
									.getText().toString().length());
							tv_percent.setText("( " + progress + "% )");
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
					float i = 0;
					try {
						i = Float.valueOf(et_month_Traff.getText().toString());
					} catch (NumberFormatException e) {
						// TODO: handle exception
						i = 0;
					}
					long newdayset = 0;
					if (tv_month_Traff.getText() == " KB") {
						newdayset = (long) (i * 1024);
					} else if (tv_month_Traff.getText() == " MB") {
						newdayset = (long) (i * 1024 * 1024);
					} else if (tv_month_Traff.getText() == " GB") {
						newdayset = (long) (i * 1024 * 1024 * 1024);
					} else if (tv_month_Traff.getText() == " TB") {
						newdayset = (long) (i * 1024 * 1024 * 1024 * 1024);
					}
					// int progre = seekbar_warning.getProgress();
					if (newdayset > monthset) {
						final CustomDialog dayWarning = new CustomDialog.Builder(
								context).setTitle("注意！")
								.setMessage("您设置的本日预警流量超过了包月套餐！")
								.setwindowHeight(0.35)
								// .setView(textEntryView)
								.setPositiveButton("确定", null).create();
						dayWarning.show();
						Button btn_ok = (Button) dayWarning
								.findViewById(R.id.positiveButton);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// 输入的数值
								dayWarning.dismiss();
							}
						});
					} else {
						UseEditor.putLong(MOBILE_WARNING_DAY, newdayset);
						UseEditor.commit();
						long mobileWarning = sharedData.getAlertWarningDay();
						// float
						// a=Float.valueOf(mobileWarning).floatValue();
						button.setText(UnitHandler.unitHandler(mobileWarning));
					}

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
