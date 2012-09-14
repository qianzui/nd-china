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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main3 extends Activity {
	/**
	 * �Ƿ�ִ��Ԥ������
	 */
	private LinearLayout line_isdoAlert;
	/**
	 * ��ѯ�����ײͰ�ť
	 */
	private LinearLayout line_findtaocan;
	/**
	 * Ԥ��������ť
	 */
	private LinearLayout warningAlertActionButton;
	/**
	 * ������Ԥ����ť
	 */
	private LinearLayout dayWarningButton;
	/**
	 * ������Ԥ��
	 */
	private LinearLayout monthWarningButton;
	/**
	 * ѡ��������ڰ�ť
	 */
	private LinearLayout countDaySpButton;
	/**
	 * �����ײ����ð�ť
	 */
	private LinearLayout btn_monthSet;
	private Context context = this;
	// ���õ�λ������
	// ��ȡ�̶��������
	private SharedPrefrenceData sharedData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main3);
		// Ϊ���˳���
		SpearheadApplication.getInstance().addActivity(this);
		sharedData = new SharedPrefrenceData(context);
		init_Spinner();
		line_findtaocan = (LinearLayout) findViewById(R.id.combo);
		line_findtaocan.setOnClickListener(new OnClickListener() {
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
		// //ģ���쳣
		// throw new RuntimeException("my exception error");
	}

	/**
	 * ��ʼ����ťƤ��
	 */
	private void init_skin() {
		line_isdoAlert.setBackgroundResource(SkinCustomMains.barsBackground());
		line_findtaocan.setBackgroundResource(SkinCustomMains.barsBackground());
		warningAlertActionButton.setBackgroundResource(SkinCustomMains
				.barsBackground());
		dayWarningButton
				.setBackgroundResource(SkinCustomMains.barsBackground());
		monthWarningButton.setBackgroundResource(SkinCustomMains
				.barsBackground());
		countDaySpButton
				.setBackgroundResource(SkinCustomMains.barsBackground());
		btn_monthSet.setBackgroundResource(SkinCustomMains.barsBackground());
		RelativeLayout title = (RelativeLayout) findViewById(R.id.main3TitleBackground);
		title.setBackgroundResource(SkinCustomMains.buttonTitleBackground());
	}

	/**
	 * ��ʼ��Ԥ��������ť
	 */
	private void init_warningAct() {
		warningAlertActionButton = (LinearLayout) findViewById(R.id.warningAct);
		final TextView warning_tv = (TextView) findViewById(R.id.warningAct_tv);
		// 0-30����1-31
		int beforeDay = sharedData.getAlertAction();
		Resources res = context.getResources();
		String[] alertactionString = res.getStringArray(R.array.warningaction);
		switch (beforeDay) {
		case 0:
			warning_tv.setText(alertactionString[0]);
			break;
		case 1:
			warning_tv.setText(alertactionString[1]);
			break;
		case 2:
			warning_tv.setText(alertactionString[2]);
			break;
		case 3:
			warning_tv.setText(alertactionString[3]);
			break;
		default:
			warning_tv.setText(alertactionString[0]);
			break;
		}

		warningAlertActionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogAlertType(warningAlertActionButton, warning_tv);
			}
		});
	}

	/**
	 * ��ʼ��������Ԥ����ť
	 */
	private void init_dayWarning() {
		dayWarningButton = (LinearLayout) findViewById(R.id.dayWarning);
		final TextView dayWarning_tv = (TextView) findViewById(R.id.dayWarning_tv);
		long mobileWarning = sharedData.getAlertWarningDay();
		dayWarning_tv.setText(UnitHandler.unitHandler(mobileWarning));
		dayWarningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogDayWarning(dayWarningButton, dayWarning_tv);

			}
		});
	}

	/**
	 * ��ʼ��������Ԥ����ť
	 */
	private void init_monthWarning() {
		monthWarningButton = (LinearLayout) findViewById(R.id.monthWarning);
		final TextView monthWarning_tv = (TextView) findViewById(R.id.monthWarning_tv);
		long mobileWarning = sharedData.getAlertWarningMonth();
		monthWarning_tv.setText(UnitHandler.unitHandler(mobileWarning));
		monthWarningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthWarning(monthWarningButton,
						monthWarning_tv);

			}
		});
	}

	/**
	 * ����������
	 */
	private void init_Spinner() {
		countDaySpButton = (LinearLayout) findViewById(R.id.dayUnit);
		final Button btn_HasUsed = (Button) findViewById(R.id.btn_monthHasUseSet_Unit);
		final TextView countDaySpButton_tv = (TextView) findViewById(R.id.countDaySpButton_tv);
		// 0-30����1-31
		int beforeDay = sharedData.getCountDay();
		countDaySpButton_tv.setText((beforeDay + 1) + " ��");
		countDaySpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomSPBeen customSP = new CustomSPBeen(context);
				customSP.dialogDaySet(countDaySpButton, btn_HasUsed,
						countDaySpButton_tv);
			}
		});
	}

	/**
	 * ���¶���ʾ�ı����г�ʼ�����õ�
	 */
	private void init_btn_month() {
		btn_monthSet = (LinearLayout) findViewById(R.id.btn_monthSet_Unit);
		final TextView monthSet_Unit_tv = (TextView) findViewById(R.id.monthSet_Unit_tv);
		// ����Ĭ����ʾֵ
		long mobileSetLong = sharedData.getMonthMobileSetOfLong();
		// showlog(mobileSetLong + "");
		monthSet_Unit_tv.setText(UnitHandler.unitHandler(mobileSetLong));
		final TextView dayWarning_tv = (TextView) findViewById(R.id.dayWarning_tv);
		final TextView monthWarning_tv = (TextView) findViewById(R.id.monthWarning_tv);
		// ���ü���
		btn_monthSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
						context);
				customDialog.dialogMonthSet_Main3(btn_monthSet, dayWarning_tv,
						monthWarning_tv, monthSet_Unit_tv);
			}
		});
	}

	/**
	 * �Ƿ����Ԥ��������
	 */
	private void init_isdo_ALert() {
		line_isdoAlert = (LinearLayout) findViewById(R.id.isalert_notify);
		line_isdoAlert.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

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
		init_isdo_ALert();
		// /skin
		init_skin();

		// umeng
		// MobclickAgent.onResume(this);

	}

	// /**
	// * ��ʾ��־
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
