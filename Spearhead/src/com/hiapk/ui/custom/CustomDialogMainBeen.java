package com.hiapk.ui.custom;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.ui.scene.Regulate;
import com.hiapk.ui.skin.ColorChangeMainBeen;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.UnitHandler;

public class CustomDialogMainBeen {
	private Context context;
	private SharedPrefrenceData sharedData;

	public CustomDialogMainBeen(Context context) {
		this.context = context;
		sharedData = SpearheadApplication.getInstance().getsharedData();
	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogMonthHasUsed(final TextView monthMobil,
			final TextView monthMobilunit, final TextView monthRemain,
			final TextView monthRemainunit) {
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
		et_month.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
						.format(((double) mobileUselong) / 1024 / 1024);
			} else {
				mobileUseString = format
						.format(((double) mobileUselong) / 1024 / 1024 / 1024);
			}
			int length = String.valueOf(mobileUseString).length();
			if (length > 6) {
				length = 6;
			}
			et_month.setText(mobileUseString);
			et_month.setSelection(length);
		} else {
			et_month.setText("");
		}

		final CustomDialog monthHasUsedAlert = new CustomDialog.Builder(context)
				.setTitle("�����ñ�����������").setContentView(textEntryView)
				.setOtherButton("���Ų�ѯ", null).setPositiveButton("ȷ��", null)
				.setNegativeButton("ȡ��", null).create();
		monthHasUsedAlert.show();

		Button btn_other = (Button) monthHasUsedAlert
				.findViewById(R.id.otherButton);
		btn_other.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
				// �������ֵ
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					showlog(i + "shuziError" + et_month.getText().toString());
					i = 0;
				}
				// btn_Used.setText(format.format(i));
				int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();

				if (mobileHasUsedUnit == 0) {
					sharedData.setMonthHasUsedStack((long) (i * 1048576));
				} else {
					sharedData
							.setMonthHasUsedStack((long) (i * 1048576 * 1024));
				}
				sharedData.setMonthMobileHasUseOffloat(i);
				sharedData.setMonthHasUsedUnit(mobileHasUsedUnit);
				// commitUsedTrafficTime();
				// init_btn_HasUsed();
				/* User clicked OK so do some stuff */
				long hasusedlong = sharedData.getMonthHasUsedStack();
				long mobileSet = sharedData.getMonthMobileSetOfLong();
				if (hasusedlong > mobileSet) {
					CustomDialogOtherBeen customOther = new CustomDialogOtherBeen(
							context);
					customOther.dialogHasUsedLongTooMuch();
				}
				SetText.resetWidgetAndNotify(context);
				PrefrenceStaticOperator.resetHasWarning(context);
				// �¶���������
				long monthLeft = 0;
				monthLeft = ColorChangeMainBeen.setRemainTraff(mobileSet,
						hasusedlong, monthMobil);
				//
				monthMobil.setText(UnitHandler.unitHandlerAcurrac(hasusedlong,
						monthMobilunit));
				monthRemain.setText(UnitHandler.unitHandler(monthLeft,
						monthRemainunit));
				// ������ʱ
				AlarmSet alarm = new AlarmSet();
				alarm.StartWidgetAlarm(context);
				// ����Ի���
				resumeMain();
				monthHasUsedAlert.dismiss();
			}
		});
		Button btn_cancel = (Button) monthHasUsedAlert
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				resumeMain();
				monthHasUsedAlert.dismiss();
			}
		});
		monthHasUsedAlert.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				resumeMain();
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
	public void dialogMonthSet_Main(final TextView monthSet,
			final TextView monthRemain, final TextView monthRemainunit,
			final TextView monthUse, final TextView monthUseunit) {

		int mobileUnit = sharedData.getMonthMobileSetUnit();
		float mobileSetFloat = sharedData.getMonthMobileSetOfFloat();
		// ��ʼ������
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
		// ��ʼ����ֵ
		spin_unit.setSelection(mobileUnit);
		// �ж�0
		if (mobileSetFloat != 0) {
			et_month.setText(mobileSetFloat + "");
			et_month.setSelection(String.valueOf(mobileSetFloat).length());
		} else {
			et_month.setText("");
		}
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
				// �������ֵ
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					i = 0;
				}
				// showlog(i + "");
				int mobileUnit = spin_unit.getSelectedItemPosition();
				if (mobileUnit == 0) {
					long monthsetTraffMB = (long) (i * 1024 * 1024);
					// showlog(monthsetTraffMB + "");
					sharedData.setMonthMobileSetOfLong(monthsetTraffMB);
					sharedData.setAlertWarningMonth(monthsetTraffMB * 9 / 10);
					sharedData.setAlertWarningDay(monthsetTraffMB / 10);
				} else if (mobileUnit == 1) {
					long monthsetTraffGB = (long) (i * 1024 * 1024 * 1024);
					// showlog(monthsetTraffGB + "");
					sharedData.setMonthMobileSetOfLong(monthsetTraffGB);
					sharedData.setAlertWarningMonth(monthsetTraffGB * 9 / 10);
					sharedData.setAlertWarningDay(monthsetTraffGB / 10);
				}
				sharedData.setMonthMobileSetUnit(mobileUnit);
				// passfileEditor.putInt(VALUE_MOBILE_SET_OF_INT, i);
				sharedData.setMonthMobileSetOfFloat(i);
				SetText.resetWidgetAndNotify(context);
				// �ж��Ƿ�δ����
				if (i == 0) {
					sharedData.setMonthSetHasSet(false);
				} else {
					sharedData.setMonthSetHasSet(true);
				}
				PrefrenceStaticOperator.resetHasWarning(context);
				// ������������ֵ��������
				long mobileSet = sharedData.getMonthMobileSetOfLong();
				// �¶���������
				long mobile_month_use = TrafficManager
						.getMonthUseMobile(context);
				long monthLeft = 0;
				//
				monthUse.setText(UnitHandler.unitHandlerAcurrac(
						mobile_month_use, monthUseunit));
				monthLeft = ColorChangeMainBeen.setRemainTraff(mobileSet,
						mobile_month_use, monthUse);
				monthRemain.setText(UnitHandler.unitHandler(monthLeft,
						monthRemainunit));
				resumeMain();
				monthSetAlert.dismiss();
				// Intent intent = new Intent(context, PhoneSet.class);
				// context.startActivity(intent);
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
				resumeMain();
				monthSetAlert.dismiss();
			}
		});
		monthSetAlert.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				resumeMain();
			}
		});
	}

	private void resumeMain() {
		SpearheadActivity.switchScene(2);
		SpearheadActivity.switchScene(0);
	}

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		// Log.d("main3", string);
	}
}
