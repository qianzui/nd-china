package com.hiapk.spearhead;

import com.hiapk.alertdialog.CustomDialogMain3Been;
import com.hiapk.customspinner.CustomSPBeen;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.PhoneSet;
import com.hiapk.regulate.Regulate;
import com.hiapk.regulate.SharedPrefrenceDataRegulate;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main3 extends Activity {
	private Button combo;
	private Context context = this;
	// 调用单位处理函数
	// 获取固定存放数据
	private SharedPrefrenceData sharedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main3);
		// 为了退出。
		Mapplication.getInstance().addActivity(this);
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
	 * 初始化预警动作按钮
	 */
	private void init_warningAct() {
		final Button alertAction = (Button) findViewById(R.id.warningAct);
		// 0-30代表1-31
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
	 * 初始化日流量预警按钮
	 */
	private void init_dayWarning() {
		// TODO Auto-generated method stub
		final Button dayWarning = (Button) findViewById(R.id.dayWarning);
		long mobileWarning = sharedData.getAlertWarningDay();
		dayWarning.setText(UnitHandler.unitHandler(mobileWarning));
		dayWarning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogDayWarning(dayWarning);

			}
		});
	}

	/**
	 * 初始化月流量预警按钮
	 */
	private void init_monthWarning() {
		final Button monthWarning = (Button) findViewById(R.id.monthWarning);
		long mobileWarning = sharedData.getAlertWarningMonth();
		monthWarning.setText(UnitHandler.unitHandler(mobileWarning));
		monthWarning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthWarning(monthWarning);

			}
		});
	}

	/**
	 * 设置下拉条
	 */
	private void init_Spinner() {
		final Button dayUnit = (Button) findViewById(R.id.dayUnit);
		final Button btn_HasUsed = (Button) findViewById(R.id.btn_monthHasUseSet_Unit);
		// 0-30代表1-31
		int beforeDay = sharedData.getCountDay();
		dayUnit.setText((beforeDay + 1) + " 日");
		dayUnit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogDaySet(dayUnit, btn_HasUsed);
			}
		});
	}

	/**
	 * 对月度显示文本进行初始化设置等
	 */
	private void init_btn_month() {
		final Button btn_month = (Button) findViewById(R.id.btn_monthSet_Unit);
		// 设置默认显示值
		long mobileSetLong = sharedData.getMonthMobileSetOfLong();
		// showlog(mobileSetLong + "");
		btn_month.setText(UnitHandler.unitHandler(mobileSetLong));
		final Button dayWarning = (Button) findViewById(R.id.dayWarning);
		final Button monthWarning = (Button) findViewById(R.id.monthWarning);
		// 设置监听
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
	// * 显示日志
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// // Log.d("main3", string);
	// }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
