package com.hiapk.alertdialog;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.text.InputType;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceStaticOperator;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.provider.ColorChangeMainBeen;
import com.hiapk.regulate.Regulate;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomDialogMainBeen {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// 总流量long
	private String VALUE_MOBILE_SET = "mobilemonthuse";
	// 设置单位（月度设置）
	private String MOBILE_SET_UNIT = "mobileMonthUnit";
	private String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	private String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	private String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	private String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// 已使用总流量int
	private String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	// 设置单位（已使用）
	private String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// 已使用总流量long
	private String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	// 流量预警
	private String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	private String MOBILE_WARNING_DAY = "mobiledaywarning";
	private Context context;

	// 设置时间
	private int year;
	private int month;
	private int monthDay;
	private int hour;
	private int minute;
	private int second;
	private String time;

	public CustomDialogMainBeen(Context context) {
		this.context = context;
	}

	/**
	 * 本月已用弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogMonthHasUsed(final TextView monthMobil,
			final TextView monthMobilunit, final TextView monthRemain,
			final TextView monthRemainunit) {
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
		et_month.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL ); 
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
				.setTitle("请设置本月已用流量").setContentView(textEntryView)
				.setOtherButton("短信查询", null).setPositiveButton("确定", null)
				.setNegativeButton("取消", null).create();
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
					i = 0;
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
					sharedData
							.setMonthHasUsedStack((long) (i * 1048576 * 1024));
				}
				passfileEditor.putFloat(VALUE_MOBILE_HASUSED_OF_FLOAT, i);

				passfileEditor.putInt(MOBILE_HASUSED_SET_UNIT,
						mobileHasUsedUnit);
				passfileEditor.commit();// 委托，存入数据
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
				commitUsedTrafficTime();
				// 月度流量设置
				long monthLeft = 0;
				monthLeft = ColorChangeMainBeen.setRemainTraff(mobileSet,
						hasusedlong, monthMobil);
				//
				monthMobil.setText(UnitHandler.unitHandlerAcurrac(hasusedlong,
						monthMobilunit));
				monthRemain.setText(UnitHandler.unitHandler(monthLeft,
						monthRemainunit));
				// 启动计时
				AlarmSet alarm = new AlarmSet();
				alarm.StartWidgetAlarm(context);
				// 清除对话框
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
	public void dialogMonthSet_Main(final Button btn_toThree,
			final TextView monthSet, final TextView monthSetunit,
			final TextView monthRemain, final TextView monthRemainunit,
			final TextView monthUse, final TextView monthUseunit) {

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
		// 初始化数值
		spin_unit.setSelection(mobileUnit);
		// 判断0
		if (mobileSetFloat != 0) {
			et_month.setText(mobileSetFloat + "");
			et_month.setSelection(String.valueOf(mobileSetFloat).length());
		} else {
			et_month.setText("");
		}
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
				// showlog(i + "");
				int mobileUnit = spin_unit.getSelectedItemPosition();
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				// Log.d("main3", i + "");

				if (mobileUnit == 0) {
					long monthsetTraffMB = (long) (i * 1024 * 1024);
					// showlog(monthsetTraffMB + "");
					passfileEditor.putLong(VALUE_MOBILE_SET, monthsetTraffMB);
					passfileEditor.putLong(MOBILE_WARNING_MONTH,
							monthsetTraffMB * 9 / 10);
					passfileEditor.putLong(MOBILE_WARNING_DAY,
							monthsetTraffMB / 10);
				} else if (mobileUnit == 1) {
					long monthsetTraffGB = (long) (i * 1024 * 1024 * 1024);
					// showlog(monthsetTraffGB + "");
					passfileEditor.putLong(VALUE_MOBILE_SET, monthsetTraffGB);
					passfileEditor.putLong(MOBILE_WARNING_MONTH,
							monthsetTraffGB * 9 / 10);
					passfileEditor.putLong(MOBILE_WARNING_DAY,
							monthsetTraffGB / 10);
				}
				passfileEditor.putInt(MOBILE_SET_UNIT, mobileUnit);
				// passfileEditor.putInt(VALUE_MOBILE_SET_OF_INT, i);
				sharedData.setMonthMobileSetOfFloat(i);
				passfileEditor.commit();// 委托，存入数据
				SetText.resetWidgetAndNotify(context);
				// 判断是否未设置
				SharedPrefrenceData sharedData = new SharedPrefrenceData(
						context);
				if (i == 0) {
					sharedData.setMonthSetHasSet(false);
					btn_toThree.setText("  请设置流量套餐  ");
				} else {
					sharedData.setMonthSetHasSet(true);
					btn_toThree.setText("  校准流量  ");
				}
				PrefrenceStaticOperator.resetHasWarning(context);
				// 重设主界面数值包月流量
				long mobileSet = sharedData.getMonthMobileSetOfLong();
				if (mobileSet != 0) {
					monthSet.setText(UnitHandler.unitHandler(mobileSet,
							monthSetunit));
					monthSet.setTextColor(ColorChangeMainBeen.colorBlue);
				} else {
					monthSet.setText("未设置");
					monthSet.setTextColor(ColorChangeMainBeen.colorRed);
					monthSetunit.setText("");
				}
				// 月度流量设置
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
				// TODO Auto-generated method stub
				monthSetAlert.dismiss();
			}
		});
	}

	/**
	 * 记录修改已使用流量的时间
	 */
	private void commitUsedTrafficTime() {
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		// 记录点击修改已使用流量的时间
		String time = gettime();
		UseEditor.putInt(MOBILE_COUNT_SET_YEAR, year);
		UseEditor.putInt(MOBILE_COUNT_SET_MONTH, month);
		UseEditor.putInt(MOBILE_COUNT_SET_DAY, monthDay);
		UseEditor.putString(MOBILE_COUNT_SET_TIME, time);
		UseEditor.commit();
	}

	/**
	 * 获取时间
	 * 
	 * @return 返回00:00:00的时间
	 */
	private String gettime() {
		initTime();
		return time;
	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		hour = t.hour; // 0-23
		minute = t.minute;
		second = t.second;
		String hour2 = hour + "";
		String minute2 = minute + "";
		String second2 = second + "";
		if (hour < 10)
			hour2 = "0" + hour2;
		if (minute < 10)
			minute2 = "0" + minute2;
		if (second < 10)
			second2 = "0" + second2;
		time = hour2 + ":" + minute2 + ":" + second2;
	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		// Log.d("main3", string);
	}
}
