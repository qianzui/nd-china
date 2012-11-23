package com.hiapk.ui.custom;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.spearhead.R;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.ui.scene.Regulate;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataWidget;
import com.hiapk.util.UnitHandler;

public class CustomSPBeen {
	// 已使用总流量long
	private Context context;
	private SharedPrefrenceData sharedData;
	private SharedPrefrenceDataWidget sharedDatawidget;

	public CustomSPBeen(Context context) {
		this.context = context;
		sharedData = new SharedPrefrenceData(context);
		sharedDatawidget = new SharedPrefrenceDataWidget(context);
	}

	/**
	 * 设置提醒方式弹出的对话框
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogSettingFreshplv() {
		final int beforeFresh = sharedDatawidget.getWidgetFresh();
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_listview_spinner, null);
		ListView lv_fresh = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		Resources res = context.getResources();
		String[] action = res.getStringArray(R.array.refreshwidget);
		CustomSPAdapter adapter = new CustomSPAdapter(context, action,
				beforeFresh);
		lv_fresh.setAdapter(adapter);
		lv_fresh.setSelection(beforeFresh);

		final CustomDialog freshtime;
		freshtime = new CustomDialog.Builder(context).setTitle("更新频率")
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		freshtime.show();
		// 设置cancel的监听
		Button btn_cancel = (Button) freshtime
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0-30代表1-31
				freshtime.dismiss();
			}
		});

		// 设置中间按钮的监听
		lv_fresh.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				sharedDatawidget.setWidgetFresh(arg2);
				AlarmSet alset = new AlarmSet();
				alset.StartAlarm(context);
				alset.StartWidgetAlarm(context);
				freshtime.dismiss();
			}
		});
	}

	/**
	 * 设置提醒方式弹出的对话框
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogAlertType(final LinearLayout btn_Alert,
			final TextView warning_tv) {

		final SharedPrefrenceData sharedData = new SharedPrefrenceData(context);

		final int beforeAction = sharedData.getAlertAction();
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_listview_spinner, null);
		ListView lv_action = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		Resources res = context.getResources();
		String[] action = res.getStringArray(R.array.warningaction);
		CustomSPAdapter adapter = new CustomSPAdapter(context, action,
				beforeAction);
		lv_action.setAdapter(adapter);
		lv_action.setSelection(beforeAction);

		final CustomDialog notifySet;
		notifySet = new CustomDialog.Builder(context).setTitle("请选择预警动作")
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		notifySet.show();
		// 设置cancel的监听
		Button btn_cancel = (Button) notifySet
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0-30代表1-31
				notifySet.dismiss();
			}
		});

		// 设置中间按钮的监听
		lv_action.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				operatorOnAlertClick();
				sharedData.setAlertAction(arg2);
				warning_tv.setText((CharSequence) arg0.getItemAtPosition(arg2));
				notifySet.dismiss();
			}
		});

	}

	private void operatorOnAlertClick() {

	}

	/**
	 * 月度结算日显示弹出的对话框
	 * 
	 * @param btn_HasUsed
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogDaySet(final LinearLayout btn_date,
			final Button btn_HasUsed, final TextView countDaySpButton_tv) {

		final int beforeDay = sharedData.getCountDay();
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_listview_spinner, null);
		ListView lv_day = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		Resources res = context.getResources();
		String[] city = res.getStringArray(R.array.day);
		CustomSPAdapter adapter = new CustomSPAdapter(context, city, beforeDay);
		lv_day.setAdapter(adapter);
		lv_day.setSelection(beforeDay);
		// TextView appupload = (TextView) textEntryView
		// .findViewById(R.id.listview_btn);
		// View beforeView = adapter.getView(1, appupload, null);
		// // TextView beforetv = (TextView) beforeView
		// // .findViewById(R.id.listview_btn);
		// Drawable radio_on = res.getDrawable(R.drawable.radiobtn_on);
		// ((TextView) beforeView).setCompoundDrawablesWithIntrinsicBounds(null,
		// null, radio_on, null);
		// 启动spinner
		final CustomDialog dateSet;
		dateSet = new CustomDialog.Builder(context).setTitle("请设置结算日期")
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		dateSet.show();
		// 设置中间按钮的监听
		// 设置cancel的监听
		Button btn_cancel = (Button) dateSet.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0-30代表1-31
				// showlog(sharedData.getCountDay() + "");
				dateSet.dismiss();
			}
		});
		// 设置中间按钮的监听
		lv_day.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 != beforeDay) {
					operatorOnClick(btn_HasUsed);
					// 结算日期变化时做日期变化并重置本月已用数值
					// Log.d("main3", i + "");
					sharedData.setCountSetDay(arg2);
					sharedData.setCountSetYear(1977);
					// Log.d("main3", i + "");
					sharedData.setMonthHasUsedStack(0);
					sharedData.setMonthMobileHasUseOffloat(0);
				}
				sharedData.setCountDay(arg2);
				countDaySpButton_tv.setText((CharSequence) arg0
						.getItemAtPosition(arg2));
				dateSet.dismiss();
			}
		});

	}

	private void operatorOnClick(Button btn_HasUsed) {
		// 重置月已用流量
		long month_used = TrafficManager.getMonthUseMobile(context);
		btn_HasUsed.setText(UnitHandler.unitHandler(month_used));
		// 弹出建议设置已用流量对话框
		final CustomDialog dayWarning = new CustomDialog.Builder(context)
				.setTitle("注意！").setMessage("设置结算日后请重新对流量进行校准。")
				// .setView(textEntryView)
				.setPositiveButton("确定", null).setNegativeButton("取消", null)
				.create();
		dayWarning.show();
		Button btn_ok = (Button) dayWarning.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogMonthHasUsed();
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
		// 刷新小部件与通知栏
		// AlarmSet alset = new AlarmSet();
		// alset.StartWidgetAlarm(context);
		SetText.resetWidgetAndNotify(context);
	}

	/**
	 * 本月已用弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	private void dialogMonthHasUsed() {
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
		et_month.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
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

			et_month.setText(mobileUseString);
			et_month.setSelection(String.valueOf(mobileUseString).length());
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
				// 输入的数值
				float i = 0;
				try {
					i = Float.valueOf(et_month.getText().toString());
				} catch (NumberFormatException e) {
					showlog(i + "shuziError" + et_month.getText().toString());
				}

				// btn_Used.setText(format.format(i));
				int mobileHasUsedUnit = spin_unit.getSelectedItemPosition();
				// Log.d("main3", i + "");
				//
				//

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
				// 清除对话框
				monthHasUsedAlert.dismiss();
			}
		});
		Button btn_cancel = (Button) monthHasUsedAlert
				.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				monthHasUsedAlert.dismiss();
			}
		});

	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("CustomSPBeen", string);
	}
}
