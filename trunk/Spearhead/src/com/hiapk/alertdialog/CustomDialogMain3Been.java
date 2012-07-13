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
	// ����sharedprefrence
	private String PREFS_NAME = "allprefs";
	// ������long
	private String VALUE_MOBILE_SET = "mobilemonthuse";
	// ���õ�λ���¶����ã�
	private String MOBILE_SET_UNIT = "mobileMonthUnit";
	// ����Ԥ��
	private String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	private String MOBILE_WARNING_DAY = "mobiledaywarning";
	private Context context;

	public CustomDialogMain3Been(Context context) {
		this.context = context;
	}

	/**
	 * �¶���ʾ�����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogMonthSet_Main3(final Button btn_month,
			final Button dayWarning, final Button monthWarning) {
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		int mobileUnit = sharedData.getMonthMobileSetUnit();
		float mobileSetFloat = sharedData.getMonthMobileSetOfFloat();
		// ��ʼ������
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
		// �ж�0
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
		// ��ʼ����ֵ
		spin_unit.setSelection(mobileUnit);
		final CustomDialog monthSetAlert = new CustomDialog.Builder(context)
				.setTitle("������ÿ�������޶�").setContentView(textEntryView)
				.setPositiveButton("ȷ��", null).setNegativeButton("ȡ��", null)
				.create();
		monthSetAlert.show();

		Button btn_ok = (Button) monthSetAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// �������ֵ
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
					// ���û�ı��3����ֵ��++
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
					// ���û�ı��3����ֵ��++
					btn_month.setText(UnitHandler.unitHandler(monthsetTraffGB));
					monthWarning.setText(UnitHandler
							.unitHandler(monthsetTraffGB * 9 / 10));
					dayWarning.setText(UnitHandler
							.unitHandler(monthsetTraffGB / 10));
				}
				passfileEditor.putInt(MOBILE_SET_UNIT, mobileUnit);
				sharedData.setMonthMobileSetOfFloat(i);
				// passfileEditor.putFloat(VALUE_MOBILE_SET_OF_INT, i);
				passfileEditor.commit();// ί�У���������
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
	 * �¶�Ԥ�������ĶԻ���
	 */
	public void dialogMonthWarning(final Button button) {
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		LayoutInflater factory = LayoutInflater.from(context);
		View textEntryView = factory.inflate(
				R.layout.month_warning_set_alert_dialog, null);
		// ����Ԥ�����ô����Ϸ����ı�
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
		// tv_month_warning.setText("�¶�Ԥ��������");
		// tv_month_warning.setGravity(Gravity.CENTER);
		// �����϶�������
		final SeekBar seekbar_warning = (SeekBar) textEntryView
				.findViewById(R.id.probar_warning_alert);
		// ��������
		final long monthset = sharedData.getMonthMobileSetOfLong();
		// final int monthset_MB = (int) (monthset / 1024 / 1024);
		long warningMonthset = sharedData.getAlertWarningMonth();
		if (monthset != 0) {
			// ���г�ʼ��
			// ������ֵǰ����˵������
			final String text = "";
			if (warningMonthset != 0) {
				seekbar_warning
						.setProgress((int) (warningMonthset * 100 / monthset));
				et_month_Traff.setText(text
						+ UnitHandler.unitHandler(warningMonthset,
								tv_month_Traff));
				// �ƶ�����λ
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
					.setTitle("�������ﵽ������ֵ����").setContentView(textEntryView)
					.setPositiveButton("ȷ��", null).setwindowHeight(0.35)
					.setNegativeButton("ȡ��", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// �������ֵ
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
								context).setTitle("ע�⣡")
								.setMessage("�����õı���Ԥ�����������˰����ײͣ�")
								.setwindowHeight(0.35)
								// .setView(textEntryView)
								.setPositiveButton("ȷ��", null).create();
						monthWarning.show();
						Button btn_ok = (Button) monthWarning
								.findViewById(R.id.positiveButton);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// �������ֵ
								monthWarning.dismiss();
							}
						});
					} else {
						// ��Сֵ1M
						UseEditor.putLong(MOBILE_WARNING_MONTH, newmonthset);
						UseEditor.commit();
						// ������ֵ
						long mobileWarning = sharedData.getAlertWarningMonth();
						// float
						// a=Float.valueOf(mobileWarning).floatValue();
						button.setText(UnitHandler.unitHandler(mobileWarning));
					}
					// ����Ԥ��״̬
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
					.setTitle("ע�⣡").setMessage("����û�����������ײͣ�����а����������á�")
					.setwindowHeight(0.35)
					// .setView(textEntryView)
					.setPositiveButton("ȷ��", null).create();
			monthWarning.show();
			Button btn_ok = (Button) monthWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// �������ֵ
					monthWarning.dismiss();
				}
			});

		}

	}

	/**
	 * ������Ԥ���Ի���
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
		// ����Ԥ�����ô����Ϸ����ı�
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
		// tv_day_warning.setText("��Ԥ��������");
		// tv_day_warning.setGravity(Gravity.CENTER);
		// �����϶�������
		final SeekBar seekbar_warning = (SeekBar) textEntryView
				.findViewById(R.id.probar_warning_alert);
		// ��������
		long warningDayset = sharedData.getAlertWarningDay();
		final long monthset = sharedData.getMonthMobileSetOfLong();
		if (monthset != 0) {
			// ���г�ʼ��
			final String text = "";
			if (warningDayset != 0) {
				seekbar_warning
						.setProgress((int) (warningDayset * 100 / monthset));
				et_month_Traff.setText(text
						+ UnitHandler
								.unitHandler(warningDayset, tv_month_Traff));
				// �ƶ�����λ
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
					.setTitle("�������ﵽ������ֵ����").setContentView(textEntryView)
					.setPositiveButton("ȷ��", null).setwindowHeight(0.35)
					.setNegativeButton("ȡ��", null).create();
			dayWarning.show();
			Button btn_ok = (Button) dayWarning
					.findViewById(R.id.positiveButton);
			btn_ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// �������ֵ
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
								context).setTitle("ע�⣡")
								.setMessage("�����õı���Ԥ�����������˰����ײͣ�")
								.setwindowHeight(0.35)
								// .setView(textEntryView)
								.setPositiveButton("ȷ��", null).create();
						dayWarning.show();
						Button btn_ok = (Button) dayWarning
								.findViewById(R.id.positiveButton);
						btn_ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// �������ֵ
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
					// ����Ԥ��״̬
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
					.setTitle("ע�⣡").setMessage("����û�����������ײͣ�����а����������á�")
					.setwindowHeight(0.35)
					// .setView(textEntryView)
					.setPositiveButton("ȷ��", null).create();
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
	// * �������õ�������
	// *
	// * @return
	// */
	// public void dialogCountDaySelected() {
	// final CustomDialog dayWarning = new CustomDialog.Builder(context)
	// .setTitle("���ý����պ������½�������У׼")
	// // .setView(textEntryView)
	// .setPositiveButton("ȷ��", null).setNegativeButton("ȡ��", null)
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
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("CustomDialogMain3Been", string);
	}
}
