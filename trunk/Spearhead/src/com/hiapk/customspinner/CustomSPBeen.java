package com.hiapk.customspinner;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import com.hiapk.alertdialog.CustomDialog;
import com.hiapk.alertdialog.CustomDialogOtherBeen;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceStaticOperator;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.regulate.Regulate;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomSPBeen {
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

	int beforeDay = 0;

	public CustomSPBeen(Context context) {
		this.context = context;
	}

	/**
	 * �������ѷ�ʽ�����ĶԻ���
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogSettingFreshplv() {
		final SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);

		final int beforeFresh = sharedDatawidget.getWidgetFresh();
		// ��ʼ������
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.listview_custom_spinner, null);
		ListView lv_fresh = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		Resources res = context.getResources();
		String[] action = res.getStringArray(R.array.refreshwidget);
		CustomspAdapter adapter = new CustomspAdapter(context, action,
				beforeFresh);
		lv_fresh.setAdapter(adapter);
		lv_fresh.setDividerHeight(0);
		lv_fresh.setSelection(beforeFresh);

		final CustomSPDialog freshtime;
		CustomSPDialog.heighpar = 0.6;
		freshtime = new CustomSPDialog.Builder(context).setTitle("����Ƶ��")
				.setContentView(textEntryView).setNegativeButton("ȷ��", null)
				.create();
		freshtime.show();
		// ����cancel�ļ���
		Button btn_cancel = (Button) freshtime
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				freshtime.dismiss();
			}
		});

		// �����м䰴ť�ļ���
		lv_fresh.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				sharedDatawidget.setWidgetFresh(arg2);
				AlarmSet alset=new AlarmSet();
				alset.StartAlarm(context);
				alset.StartWidgetAlarm(context);
				freshtime.dismiss();
			}
		});
	}

	/**
	 * �������ѷ�ʽ�����ĶԻ���
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogAlertType(final Button btn_Alert) {

		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);

		final int beforeAction = sharedData.getAlertAction();
		// ��ʼ������
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.listview_custom_spinner, null);
		ListView lv_action = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		Resources res = context.getResources();
		String[] action = res.getStringArray(R.array.warningaction);
		CustomspAdapter adapter = new CustomspAdapter(context, action,
				beforeAction);
		lv_action.setAdapter(adapter);
		lv_action.setDividerHeight(0);
		lv_action.setSelection(beforeAction);

		final CustomSPDialog notifySet;
		CustomSPDialog.heighpar = 0.58;
		notifySet = new CustomSPDialog.Builder(context).setTitle("��ѡ��Ԥ������")
				.setContentView(textEntryView).setNegativeButton("ȷ��", null)
				.create();
		notifySet.show();
		// ����cancel�ļ���
		Button btn_cancel = (Button) notifySet
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				notifySet.dismiss();
			}
		});

		// �����м䰴ť�ļ���
		lv_action.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				operatorOnAlertClick();
				sharedData.setAlertAction(arg2);
				btn_Alert.setText((CharSequence) arg0.getItemAtPosition(arg2));
				notifySet.dismiss();
			}
		});

	}

	private void operatorOnAlertClick() {
		// TODO Auto-generated method stub

	}

	/**
	 * �¶Ƚ�������ʾ�����ĶԻ���
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	public void dialogDaySet(final Button btn_date, final Button btn_HasUsed) {

		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		final int beforeDay = sharedData.getCountDay();
		// ��ʼ������
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.listview_custom_spinner, null);
		ListView lv_day = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		Resources res = context.getResources();
		String[] city = res.getStringArray(R.array.day);
		CustomspAdapter adapter = new CustomspAdapter(context, city, beforeDay);
		lv_day.setAdapter(adapter);
		lv_day.setDividerHeight(0);
		lv_day.setSelection(beforeDay);
		// TextView appupload = (TextView) textEntryView
		// .findViewById(R.id.listview_btn);
		// View beforeView = adapter.getView(1, appupload, null);
		// // TextView beforetv = (TextView) beforeView
		// // .findViewById(R.id.listview_btn);
		// Drawable radio_on = res.getDrawable(R.drawable.radiobtn_on);
		// ((TextView) beforeView).setCompoundDrawablesWithIntrinsicBounds(null,
		// null, radio_on, null);
		// ����spinner
		final CustomSPDialog dateSet;
		CustomSPDialog.heighpar = 0.8;
		dateSet = new CustomSPDialog.Builder(context).setTitle("�����ý�������")
				.setContentView(textEntryView).setNegativeButton("ȷ��", null)
				.create();
		dateSet.show();
		// �����м䰴ť�ļ���
		// ����cancel�ļ���
		Button btn_cancel = (Button) dateSet.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				// showlog(sharedData.getCountDay() + "");
				dateSet.dismiss();
			}
		});
		// �����м䰴ť�ļ���
		lv_day.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 != beforeDay) {
					operatorOnClick(btn_HasUsed);
					// �������ڱ仯ʱ�����ڱ仯�����ñ���������ֵ
					Editor passfileEditor = context.getSharedPreferences(
							PREFS_NAME, 0).edit();
					// Log.d("main3", i + "");
					passfileEditor.putInt(MOBILE_COUNT_DAY, arg2);
					// Log.d("main3", i + "");
					passfileEditor.putInt(MOBILE_COUNT_SET_YEAR, 1977);
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG, 0);
					passfileEditor.putFloat(VALUE_MOBILE_HASUSED_OF_FLOAT, 0);
					passfileEditor.commit();// ί�У���������
				}
				sharedData.setCountDay(arg2);
				btn_date.setText((CharSequence) arg0.getItemAtPosition(arg2));
				dateSet.dismiss();
			}
		});

	}

	private void operatorOnClick(Button btn_HasUsed) {
		// ��������������
		long month_used = TrafficManager.getMonthUseMobile(context);
		btn_HasUsed.setText(UnitHandler.unitHandler(month_used));
		// ���������������������Ի���
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle("ע�⣡").setMessage("���ý����պ������¶���������У׼��")
				.setwindowHeight(0.35)
				// .setView(textEntryView)
				.setPositiveButton("ȷ��", null).setNegativeButton("ȡ��", null)
				.create();
		dayWarning.show();
		Button btn_ok = (Button) dayWarning.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthHasUsed();
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
		// ˢ��С������֪ͨ��
		// AlarmSet alset = new AlarmSet();
		// alset.StartWidgetAlarm(context);
		SetText.resetWidgetAndNotify(context);
	}

	/**
	 * �������õ����ĶԻ���
	 * 
	 * @param TextView_month
	 *            ��������TextView
	 * @return ���ضԻ���
	 */
	private void dialogMonthHasUsed() {
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

			et_month.setText(mobileUseString);
			et_month.setSelection(String.valueOf(mobileUseString).length());
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
				} else {
					passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG,
							(long) (i * 1048576 * 1024));
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
				PrefrenceStaticOperator.resetHasWarning(context);
				// ����Ի���
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
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("CustomSPBeen", string);
	}
}
