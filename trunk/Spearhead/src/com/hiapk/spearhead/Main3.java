package com.hiapk.spearhead;

import com.hiapk.ui.custom.CustomDialogMain3Been;
import com.hiapk.ui.custom.CustomSPBeen;
import com.hiapk.ui.scene.PhoneSet;
import com.hiapk.ui.scene.Regulate;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataRegulate;
import com.hiapk.util.UnitHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class Main3 extends Activity {
	/**
	 * 查询流量套餐按钮
	 */
	private Button findtaocan;
	/**
	 * 预警动作按钮
	 */
	private Button warningAlertActionButton;
	/**
	 * 日流量预警按钮
	 */
	private Button dayWarningButton;
	/**
	 * 月流量预警
	 */
	private Button monthWarningButton;
	/**
	 * 选择结算日期按钮
	 */
	private Button countDaySpButton;
	/**
	 * 包月套餐设置按钮
	 */
	private Button btn_monthSet;
	private Context context = this;
	// 调用单位处理函数
	// 获取固定存放数据
	private SharedPrefrenceData sharedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main3);
		// 为了退出。
		SpearheadApplication.getInstance().addActivity(this);
		sharedData = new SharedPrefrenceData(context);
		init_Spinner();
		findtaocan = (Button) findViewById(R.id.combo);
		findtaocan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPrefrenceDataRegulate sharedDataReg = new SharedPrefrenceDataRegulate(
						context);
				if (sharedDataReg.getIsFirstRegulate()) {
					Intent i = new Intent(Main3.this, PhoneSet.class);
					startActivity(i);
				} else {
					Intent it = new Intent(Main3.this, Regulate.class);
					startActivity(it);
				}
			}
		});
		// //模拟异常
		// throw new RuntimeException("my exception error");
	}

	/**
	 * 初始化按钮皮肤
	 */
	private void init_skin() {
		findtaocan
				.setBackgroundResource(SkinCustomMains.buttonBackgroundDark());
		warningAlertActionButton.setBackgroundResource(SkinCustomMains
				.buttonBackgroundDark());
		dayWarningButton.setBackgroundResource(SkinCustomMains
				.buttonBackgroundDark());
		monthWarningButton.setBackgroundResource(SkinCustomMains
				.buttonBackgroundDark());
		countDaySpButton.setBackgroundResource(SkinCustomMains
				.buttonBackgroundDark());
		btn_monthSet.setBackgroundResource(SkinCustomMains
				.buttonBackgroundDark());
		RelativeLayout title = (RelativeLayout) findViewById(R.id.main3TitleBackground);
		title.setBackgroundResource(SkinCustomMains.buttonTitleBackground());
	}

	/**
	 * 初始化预警动作按钮
	 */
	private void init_warningAct() {
		warningAlertActionButton = (Button) findViewById(R.id.warningAct);
		// 0-30代表1-31
		int beforeDay = sharedData.getAlertAction();
		Resources res = context.getResources();
		String[] alertactionString = res.getStringArray(R.array.warningaction);
		switch (beforeDay) {
		case 0:
			warningAlertActionButton.setText(alertactionString[0]);
			break;
		case 1:
			warningAlertActionButton.setText(alertactionString[1]);
			break;
		case 2:
			warningAlertActionButton.setText(alertactionString[2]);
			break;
		case 3:
			warningAlertActionButton.setText(alertactionString[3]);
			break;
		default:
			warningAlertActionButton.setText(alertactionString[0]);
			break;
		}

		warningAlertActionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogAlertType(warningAlertActionButton);
			}
		});
	}

	/**
	 * 初始化日流量预警按钮
	 */
	private void init_dayWarning() {
		dayWarningButton = (Button) findViewById(R.id.dayWarning);
		long mobileWarning = sharedData.getAlertWarningDay();
		dayWarningButton.setText(UnitHandler.unitHandler(mobileWarning));
		dayWarningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogDayWarning(dayWarningButton);

			}
		});
	}

	/**
	 * 初始化月流量预警按钮
	 */
	private void init_monthWarning() {
		monthWarningButton = (Button) findViewById(R.id.monthWarning);
		long mobileWarning = sharedData.getAlertWarningMonth();
		monthWarningButton.setText(UnitHandler.unitHandler(mobileWarning));
		monthWarningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthWarning(monthWarningButton);

			}
		});
	}

	/**
	 * 设置下拉条
	 */
	private void init_Spinner() {
		countDaySpButton = (Button) findViewById(R.id.dayUnit);
		final Button btn_HasUsed = (Button) findViewById(R.id.btn_monthHasUseSet_Unit);
		// 0-30代表1-31
		int beforeDay = sharedData.getCountDay();
		countDaySpButton.setText((beforeDay + 1) + " 日");
		countDaySpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogDaySet(countDaySpButton, btn_HasUsed);
			}
		});
	}

	/**
	 * 对月度显示文本进行初始化设置等
	 */
	private void init_btn_month() {
		btn_monthSet = (Button) findViewById(R.id.btn_monthSet_Unit);
		// 设置默认显示值
		long mobileSetLong = sharedData.getMonthMobileSetOfLong();
		// showlog(mobileSetLong + "");
		btn_monthSet.setText(UnitHandler.unitHandler(mobileSetLong));
		final Button dayWarning = (Button) findViewById(R.id.dayWarning);
		final Button monthWarning = (Button) findViewById(R.id.monthWarning);
		// 设置监听
		btn_monthSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthSet_Main3(btn_monthSet, dayWarning,
						monthWarning);
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		// umeng
		// MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		init_btn_month();
		init_monthWarning();
		init_dayWarning();
		init_warningAct();
		init_skin();
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
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
