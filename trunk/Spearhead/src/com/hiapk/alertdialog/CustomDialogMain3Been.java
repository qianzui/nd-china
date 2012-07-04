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
	// ����sharedprefrence
	String PREFS_NAME = "allprefs";
	// ������long
	String VALUE_MOBILE_SET = "mobilemonthuse";
	// ��ʾ��Ԥ��ҳ���int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// ���õ�λ���¶����ã�
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// ���ý������ڼ��������ڵ���ʩʱ�䣬���ڵ�
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// ��ʹ��������int
	String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	// ���õ�λ����ʹ�ã�
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// ��ʹ��������long
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	// ����Ԥ��
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// Ԥ������
	String WARNING_ACTION = "warningaction";
	Context context;
	DecimalFormat format;

	public CustomDialogMain3Been(Context context) {
		this.context = context;
		format = new DecimalFormat("0.##");
	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogMonthHasUsed(final Button btn_HasUsed) {
		// TODO Auto-generated method stub
		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		final DecimalFormat format = new DecimalFormat("0.##");
		int mobileUseUnit = sharedData.getMonthHasUsedUnit();
		// float mobileUsefloat = sharedData.getMonthMobileHasUseOffloat();
		long mobileUselong = TrafficManager.getMonthUseMobile(context);
		// ��ʼ������
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
		// ��ʼ����ֵ
		spin_unit.setSelection(mobileUseUnit);
		int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();
		// ����ֵΪ0������ʾ��
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
				.setTitle("�����ñ�����������").setContentView(textEntryView)
				.setPositiveButton("ȷ��", null).setNegativeButton("ȡ��", null)
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
				// �������ֵ
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
				passfileEditor.commit();// ί�У���������
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
				// ��ֵ
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
					dayWarning.setText(UnitHandler
							.unitHandler(monthsetTraffGB * 9 / 10));
					monthWarning.setText(UnitHandler
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
		tv_month_Traff.setTextSize(20);
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
					int progre = seekbar_warning.getProgress();
					long newmonthset = monthset * progre / 100;
					// ��Сֵ1M
					UseEditor.putLong(MOBILE_WARNING_MONTH, newmonthset);
					UseEditor.commit();
					// ������ֵ
					long mobileWarning = sharedData.getAlertWarningMonth();
					// float
					// a=Float.valueOf(mobileWarning).floatValue();
					button.setText(UnitHandler.unitHandler(mobileWarning));
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
		final TextView tv_month_Traff = (TextView) textEntryView
				.findViewById(R.id.tv_show_Traff);
		tv_month_Traff.setTextSize(20);
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
					int progre = seekbar_warning.getProgress();
					// ��Сֵ1M
					long newdayset = monthset * progre / 100;
					UseEditor.putLong(MOBILE_WARNING_DAY, newdayset);
					UseEditor.commit();
					long mobileWarning = sharedData.getAlertWarningDay();
					// float
					// a=Float.valueOf(mobileWarning).floatValue();
					button.setText(UnitHandler.unitHandler(mobileWarning));
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
