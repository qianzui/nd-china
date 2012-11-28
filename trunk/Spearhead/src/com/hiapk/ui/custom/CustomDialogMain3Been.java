package com.hiapk.ui.custom;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.hiapk.control.widget.SetText;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.UnitHandler;

public class CustomDialogMain3Been {
	private Context context;
	private SharedPrefrenceData sharedData;

	public CustomDialogMain3Been(Context context) {
		sharedData = SpearheadApplication.getInstance().getsharedData();
		this.context = context;
	}

	/**
	 * 月度显示弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogMonthSet_Main3(final LinearLayout btn_month,
			final TextView dayWarning_tv, final TextView monthWarning_tv,
			final TextView monthSet_Unit_tv) {
		int mobileUnit = sharedData.getMonthMobileSetUnit();
		float mobileSetFloat = sharedData.getMonthMobileSetOfFloat();
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_dialog_on_main_text_entry, null);
		final EditText et_month = (EditText) textEntryView
				.findViewById(R.id.ev_alert);
		et_month.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
				// 输入的数值
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					i = 0;
				}
				showlog(i + "");
				int mobileUnit = spin_unit.getSelectedItemPosition();
				// Log.d("main3", i + "");

				if (mobileUnit == 0) {
					long monthsetTraffMB = (long) (i * 1024 * 1024);
					// showlog(monthsetTraffMB + "");
					sharedData.setMonthMobileSetOfLong(monthsetTraffMB);
					sharedData.setAlertWarningMonth(monthsetTraffMB * 9 / 10);
					sharedData.setAlertWarningDay(monthsetTraffMB / 10);
					// 设置会改变的3个数值。++
					monthSet_Unit_tv.setText(UnitHandler
							.unitHandler(monthsetTraffMB));
					monthWarning_tv.setText(UnitHandler
							.unitHandler(monthsetTraffMB * 9 / 10));
					dayWarning_tv.setText(UnitHandler
							.unitHandler(monthsetTraffMB / 10));
				} else if (mobileUnit == 1) {
					long monthsetTraffGB = (long) (i * 1024 * 1024 * 1024);
					// showlog(monthsetTraffGB + "");
					sharedData.setMonthMobileSetOfLong(monthsetTraffGB);
					sharedData.setAlertWarningMonth(monthsetTraffGB * 9 / 10);
					sharedData.setAlertWarningDay(monthsetTraffGB / 10);
					// 设置会改变的3个数值。++
					monthSet_Unit_tv.setText(UnitHandler
							.unitHandler(monthsetTraffGB));
					monthWarning_tv.setText(UnitHandler
							.unitHandler(monthsetTraffGB * 9 / 10));
					dayWarning_tv.setText(UnitHandler
							.unitHandler(monthsetTraffGB / 10));
				}
				sharedData.setMonthMobileSetUnit(mobileUnit);
				sharedData.setMonthMobileSetOfFloat(i);
				// passfileEditor.putFloat(VALUE_MOBILE_SET_OF_INT, i);
				SetText.resetWidgetAndNotify(context);
				// showlog(mobileSetLong + "");
				// long monthSetofLong = sharedData.getMonthMobileSetOfLong();
				if (i == 0) {
					sharedData.setMonthSetHasSet(false);
				} else {
					sharedData.setMonthSetHasSet(true);
				}
				PrefrenceStaticOperator.resetHasWarning(context);
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
				monthSetAlert.dismiss();
			}
		});
	}

	/**
	 * 月度预警弹出的对话框
	 */
	public void dialogMonthWarning(final LinearLayout button,
			final TextView monthWarning_tv) {
		LayoutInflater factory = LayoutInflater.from(context);
		View textEntryView = factory.inflate(
				R.layout.custom_warning_set_alert_dialog, null);
		// 流量预警设置窗口上方的文本
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		final EditText et_month_Traff = (EditText) textEntryView
				.findViewById(R.id.et_show_Traff);
		et_month_Traff.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
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
					.setPositiveButton("确定", null)
					.setNegativeButton("取消", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 输入的数值
					float i = 0;
					try {
						i = Float.valueOf(et_month_Traff.getText().toString());
					} catch (NumberFormatException e) {
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
								// .setView(textEntryView)
								.setPositiveButton("确定", null).create();
						monthWarning.show();
						Button btn_ok = (Button) monthWarning
								.findViewById(R.id.positiveButton);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// 输入的数值
								monthWarning.dismiss();
							}
						});
					} else {
						// 最小值1M
						sharedData.setAlertWarningMonth(newmonthset);
						// 设置数值
						long mobileWarning = sharedData.getAlertWarningMonth();
						// float
						// a=Float.valueOf(mobileWarning).floatValue();
						monthWarning_tv.setText(UnitHandler
								.unitHandler(mobileWarning));
					}
					// 重置预警状态
					PrefrenceStaticOperator.resetHasWarningMonth(context);
					monthWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) monthWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					monthWarning.dismiss();
				}
			});
		} else {
			final CustomDialog monthWarning = new CustomDialog.Builder(context)
					.setTitle("注意！").setMessage("您还没有设置流量套餐，请进行包月流量设置。")
					// .setView(textEntryView)
					.setPositiveButton("确定", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
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
	public void dialogDayWarning(final LinearLayout button,
			final TextView warning_tv) {
		LayoutInflater factory = LayoutInflater.from(context);
		View textEntryView = factory.inflate(
				R.layout.custom_warning_set_alert_dialog, null);
		// final TextView tv_day_warning = (TextView) textEntryView
		// .findViewById(R.id.tv_warning_alert);
		// 流量预警设置窗口上方的文本
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		final EditText et_month_Traff = (EditText) textEntryView
				.findViewById(R.id.et_show_Traff);
		et_month_Traff.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
						}

						@Override
						public void onStartTrackingTouch(SeekBar seekBar) {
						}

						@Override
						public void onProgressChanged(SeekBar seekBar,
								int progress, boolean fromUser) {
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
					.setPositiveButton("确定", null)
					.setNegativeButton("取消", null).create();
			dayWarning.show();
			Button btn_ok = (Button) dayWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 输入的数值
					float i = 0;
					try {
						i = Float.valueOf(et_month_Traff.getText().toString());
					} catch (NumberFormatException e) {
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
								// .setView(textEntryView)
								.setPositiveButton("确定", null).create();
						dayWarning.show();
						Button btn_ok = (Button) dayWarning
								.findViewById(R.id.positiveButton);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// 输入的数值
								dayWarning.dismiss();
							}
						});
					} else {
						sharedData.setAlertWarningDay(newdayset);
						long mobileWarning = sharedData.getAlertWarningDay();
						// float
						// a=Float.valueOf(mobileWarning).floatValue();
						warning_tv.setText(UnitHandler
								.unitHandler(mobileWarning));
					}

					// init_dayWarning();
					// 重置预警状态
					PrefrenceStaticOperator.resetHasWarningDay(context);
					dayWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) dayWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dayWarning.dismiss();
				}
			});
		} else {
			final CustomDialog monthWarning = new CustomDialog.Builder(context)
					.setTitle("注意！").setMessage("您还没有设置流量套餐，请进行包月流量设置。")
					// .setView(textEntryView)
					.setPositiveButton("确定", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					monthWarning.dismiss();
				}
			});

			Button btn_cancel = (Button) monthWarning
					.findViewById(R.id.negativeButton);
			btn_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
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
	// dayWarning.dismiss();
	// }
	// });
	// Button btn_cancel = (Button) dayWarning
	// .findViewById(R.id.negativeButton);
	// btn_cancel.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
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
