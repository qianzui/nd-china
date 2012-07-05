package com.hiapk.spearhead;

import java.text.DecimalFormat;

import com.hiapk.alertaction.AlertActionNotify;
import com.hiapk.alertdialog.CustomDialogMain3Been;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.customspinner.CustomSPBeen;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.PhoneSet;
import com.hiapk.regulate.Regulate;
import com.hiapk.regulate.SharedPrefrenceDataRegulate;
import com.hiapk.widget.ProgramNotify;
import com.hiapk.widget.SetText;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class Main3 extends Activity {
	Button combo;
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
	Context context = this;
	EditText edit;
	TextView tv;
	// Ԥ���ı�ʶ
	boolean flagWarning = false;
	// // ����ʱ��
	// private int year;
	// private int month;
	// private int monthDay;
	// private int hour;
	// private int minute;
	// private int second;
	// private String time;
	// ���õ�λ������
	UnitHandler FormatUnit = new UnitHandler();
	// ��������
	TrafficManager trafficManager = new TrafficManager();
	// ��ȡ�̶��������
	SharedPrefrenceData sharedData;
	// ��ʽ���̶�����
	DecimalFormat format = new DecimalFormat("0.##");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main3);
		sharedData = new SharedPrefrenceData(context);
		init_Spinner();
		combo = (Button) findViewById(R.id.combo);
		combo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPrefrenceDataRegulate sharedDataReg = new SharedPrefrenceDataRegulate(
						context);
				// TODO Auto-generated method stub
				if (sharedDataReg.getIsFirstRegulate()) {
					Intent i = new Intent(Main3.this, PhoneSet.class);
					startActivity(i);
				} else {
					Intent it = new Intent(Main3.this, Regulate.class);
					startActivity(it);
				}
			}
		});

	}

	/**
	 * ��ʼ��Ԥ��������ť
	 */
	private void init_warningAct() {
		// TODO Auto-generated method stub
		// Spinner warningAct = (Spinner) findViewById(R.id.warningAct);
		// // ����Adapter
		// ArrayAdapter<CharSequence> adp2 =
		// ArrayAdapter.createFromResource(this,
		// R.array.warningaction, R.layout.sptext);
		// adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// warningAct.setAdapter(adp2);
		// // ��ʼ����ֵ
		// final int beforeWarningAction = sharedData.getAlertAction();
		// warningAct.setSelection(beforeWarningAction);
		// warningAct.setOnItemSelectedListener(new OnItemSelectedListener() {
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// // TODO Auto-generated method stub
		//
		// if ((beforeWarningAction) != position) {
		// // �������ڱ仯ʱ�����ڱ仯�����ñ���������ֵ
		// Editor passfileEditor = context.getSharedPreferences(
		// PREFS_NAME, 0).edit();
		// // Log.d("main3", i + "");
		// passfileEditor.putInt(WARNING_ACTION, position);
		// passfileEditor.commit();// ί�У���������
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		// }
		// });
		final Button alertAction = (Button) findViewById(R.id.warningAct);
		// 0-30����1-31
		int beforeDay = sharedData.getAlertAction();
		Resources res = context.getResources();
		String[] alertactionString = res.getStringArray(R.array.warningaction);
		switch (beforeDay) {
		case 0:
			alertAction.setText(alertactionString[0]);
			break;
		case 1:
			alertAction.setText(alertactionString[1]);
			break;
		case 2:
			alertAction.setText(alertactionString[2]);
			break;
		case 3:
			alertAction.setText(alertactionString[3]);
			break;
		default:
			alertAction.setText(alertactionString[0]);
			break;
		}

		alertAction.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogAlertType(alertAction);
			}
		});
	}

	/**
	 * ��ʼ��������Ԥ����ť
	 */
	private void init_dayWarning() {
		// TODO Auto-generated method stub
		final Button dayWarning = (Button) findViewById(R.id.dayWarning);
		long mobileWarning = sharedData.getAlertWarningDay();
		// float a=Float.valueOf(mobileWarning).floatValue();
		dayWarning.setText(UnitHandler.unitHandler(mobileWarning));
		dayWarning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogDayWarning(dayWarning);
				// dialogDayWarning(dayWarning).show();

			}
		});
	}

	/**
	 * ��ʼ��������Ԥ����ť
	 */
	private void init_monthWarning() {
		final Button monthWarning = (Button) findViewById(R.id.monthWarning);
		long mobileWarning = sharedData.getAlertWarningMonth();
		// float a=Float.valueOf(mobileWarning).floatValue();
		monthWarning.setText(UnitHandler.unitHandler(mobileWarning));
		monthWarning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthWarning(monthWarning);
				// dialogMonthWarning(monthWarning).show();

			}
		});
	}

	/**
	 * ����������
	 */
	private void init_Spinner() {
		// TODO Auto-generated method stub
		// // ��ʼ��
		// Spinner dayUnit = (Spinner) findViewById(R.id.dayUnit);
		// // ����Adapter
		// ArrayAdapter<CharSequence> adp2 =
		// ArrayAdapter.createFromResource(this,
		// R.array.day, R.layout.sptext);
		// adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// dayUnit.setAdapter(adp2);
		// // �����¶�������������ڵ�Ĭ����ʾֵ
		// // ��0-30�ֱ����1-31��
		// int mobileSetCountDay = sharedData.getCountDay();
		// // ���ó�ʼ��ʾ��Ŀ
		// dayUnit.setSelection(mobileSetCountDay);
		// // ���ü��� �½�����
		// dayUnit.setOnItemSelectedListener(new OnItemSelectedListener() {
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// // TODO Auto-generated method stub
		// int beforeSetCount = sharedData.getCountDay();
		// if ((beforeSetCount) != position) {
		// // �������ڱ仯ʱ�����ڱ仯�����ñ���������ֵ
		// Editor passfileEditor = context.getSharedPreferences(
		// PREFS_NAME, 0).edit();
		// // Log.d("main3", i + "");
		// passfileEditor.putInt(MOBILE_COUNT_DAY, position);
		// // Log.d("main3", i + "");
		// passfileEditor.putInt(MOBILE_COUNT_SET_YEAR, 1977);
		// passfileEditor.putLong(VALUE_MOBILE_HASUSED_LONG, 0);
		// passfileEditor.putFloat(VALUE_MOBILE_HASUSED_OF_FLOAT, 0);
		// passfileEditor.commit();// ί�У���������
		// // ��������������
		// init_btn_HasUsed();
		// // ���������������������Ի���
		// CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
		// context);
		// customDialog.dialogCountDaySelected();
		// // ˢ��С������֪ͨ��
		// // AlarmSet alset = new AlarmSet();
		// // alset.StartWidgetAlarm(context);
		// SetText.resetWidgetAndNotify(context);
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		// // TODO Auto-generated method stub
		// }
		// });
		final Button dayUnit = (Button) findViewById(R.id.dayUnit);
		final Button btn_HasUsed = (Button) findViewById(R.id.btn_monthHasUseSet_Unit);
		// 0-30����1-31
		int beforeDay = sharedData.getCountDay();
		dayUnit.setText((beforeDay + 1) + " ��");
		dayUnit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogDaySet(dayUnit, btn_HasUsed);
			}
		});
	}

	/**
	 * ���¶���ʾ�ı����г�ʼ�����õ�
	 */
	private void init_btn_month() {
		final Button btn_month = (Button) findViewById(R.id.btn_monthSet_Unit);
		// ����Ĭ����ʾֵ
		long mobileSetLong = sharedData.getMonthMobileSetOfLong();
		// showlog(mobileSetLong + "");
		btn_month.setText(UnitHandler.unitHandler(mobileSetLong));
		final Button dayWarning = (Button) findViewById(R.id.dayWarning);
		final Button monthWarning = (Button) findViewById(R.id.monthWarning);
		// ���ü���
		btn_month.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthSet_Main3(btn_month, dayWarning,
						monthWarning);
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init_btn_month();
		init_monthWarning();
		init_dayWarning();
		init_warningAct();
		// umeng
		// MobclickAgent.onResume(this);

	}

	// /**
	// * ��ȡʱ��
	// *
	// * @return ����00:00:00��ʱ��
	// */
	// private String gettime() {
	// initTime();
	// return time;
	// }

	// /**
	// * ��¼�޸���ʹ��������ʱ��
	// */
	// private void commitUsedTrafficTime() {
	// Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
	// // ��¼����޸���ʹ��������ʱ��
	// String time = gettime();
	// UseEditor.putInt(MOBILE_COUNT_SET_YEAR, year);
	// UseEditor.putInt(MOBILE_COUNT_SET_MONTH, month);
	// UseEditor.putInt(MOBILE_COUNT_SET_DAY, monthDay);
	// UseEditor.putString(MOBILE_COUNT_SET_TIME, time);
	// UseEditor.commit();
	// }

	// /**
	// * ��ʼ��ϵͳʱ��
	// */
	// private void initTime() {
	// // Time t = new Time("GMT+8");
	// Time t = new Time();
	// t.setToNow(); // ȡ��ϵͳʱ�䡣
	// year = t.year;
	// month = t.month + 1;
	// monthDay = t.monthDay;
	// hour = t.hour; // 0-23
	// minute = t.minute;
	// second = t.second;
	// String hour2 = hour + "";
	// String minute2 = minute + "";
	// String second2 = second + "";
	// if (hour < 10)
	// hour2 = "0" + hour2;
	// if (minute < 10)
	// minute2 = "0" + minute2;
	// if (second < 10)
	// second2 = "0" + second2;
	// time = hour2 + ":" + minute2 + ":" + second2;
	// }

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		// Log.d("main3", string);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
