package com.hiapk.spearhead;

import com.hiapk.ui.custom.CustomDialogMain3Been;
import com.hiapk.ui.custom.CustomSPBeen;
import com.hiapk.ui.scene.PhoneSet;
import com.hiapk.ui.scene.Regulate;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.ui.skin.UiColors;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.SharedPrefrenceDataRegulate;
import com.hiapk.util.UnitHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Main3 extends Activity {
	/**
	 * �Ƿ�ִ��Ԥ������
	 */
	private LinearLayout line_isdoAlert;
	private ImageView isalow_img;
	/**
	 * ��ѯ�����ײͰ�ť
	 */
	private LinearLayout line_findtaocan;
	/**
	 * Ԥ��������ť
	 */
	private LinearLayout warningAlertActionButton;
	/**
	 * Ԥ�������ı�
	 */
	private TextView warning_tv1;
	private TextView warning_tv2;
	/**
	 * ������Ԥ����ť
	 */
	private LinearLayout dayWarningButton;
	private TextView dayWarning_tv1;
	private TextView dayWarning_tv2;
	/**
	 * ������Ԥ��
	 */
	private LinearLayout monthWarningButton;
	private TextView monthWarning_tv1;
	private TextView monthWarning_tv2;
	/**
	 * ѡ��������ڰ�ť
	 */
	private LinearLayout countDaySpButton;
	private Button btn_HasUsed;
	private TextView countDaySpButton_tv;
	/**
	 * �����ײ����ð�ť
	 */
	private LinearLayout btn_monthSet;
	private TextView monthSet_Unit_tv;
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
		initAllView();
		init_Spinner();
		onclickListeners();
		// //ģ���쳣
		// throw new RuntimeException("my exception error");
	}

	/**
	 * ��ʼ��������ͼ
	 */
	private void initAllView() {
		// ��ѯ����
		line_findtaocan = (LinearLayout) findViewById(R.id.combo);
		// Ԥ������ ��ʼ��
		warningAlertActionButton = (LinearLayout) findViewById(R.id.warningAct);
		warning_tv1 = (TextView) findViewById(R.id.warningAct_tv1);
		warning_tv2 = (TextView) findViewById(R.id.warningAct_tv2);
		// ������Ԥ��
		dayWarningButton = (LinearLayout) findViewById(R.id.dayWarning);
		dayWarning_tv1 = (TextView) findViewById(R.id.dayWarning_tv1);
		dayWarning_tv2 = (TextView) findViewById(R.id.dayWarning_tv2);
		// ������Ԥ��
		monthWarningButton = (LinearLayout) findViewById(R.id.monthWarning);
		monthWarning_tv1 = (TextView) findViewById(R.id.monthWarning_tv1);
		monthWarning_tv2 = (TextView) findViewById(R.id.monthWarning_tv2);
		// ������
		countDaySpButton = (LinearLayout) findViewById(R.id.dayUnit);
		btn_HasUsed = (Button) findViewById(R.id.btn_monthHasUseSet_Unit);
		countDaySpButton_tv = (TextView) findViewById(R.id.countDaySpButton_tv);
		// ����������
		btn_monthSet = (LinearLayout) findViewById(R.id.btn_monthSet_Unit);
		monthSet_Unit_tv = (TextView) findViewById(R.id.monthSet_Unit_tv);
		// allowԤ��
		line_isdoAlert = (LinearLayout) findViewById(R.id.isalert_notify);
		isalow_img = (ImageView) findViewById(R.id.isalert_notify_img);
	}

	/**
	 * ��ʼ��Ԥ��������ť
	 */
	private void init_warningAct() {
		// 0-30����1-31
		int beforeDay = sharedData.getAlertAction();
		Resources res = context.getResources();
		String[] alertactionString = res.getStringArray(R.array.warningaction);
		switch (beforeDay) {
		case 0:
			warning_tv2.setText(alertactionString[0]);
			break;
		case 1:
			warning_tv2.setText(alertactionString[1]);
			break;
		case 2:
			warning_tv2.setText(alertactionString[2]);
			break;
		case 3:
			warning_tv2.setText(alertactionString[3]);
			break;
		default:
			warning_tv2.setText(alertactionString[0]);
			break;
		}

	}

	/**
	 * ��ʼ��������Ԥ����ť
	 */
	private void init_dayWarning() {
		long mobileWarning = sharedData.getAlertWarningDay();
		dayWarning_tv2.setText(UnitHandler.unitHandler(mobileWarning));

	}

	/**
	 * ��ʼ��������Ԥ����ť
	 */
	private void init_monthWarning() {
		long mobileWarning = sharedData.getAlertWarningMonth();
		monthWarning_tv2.setText(UnitHandler.unitHandler(mobileWarning));

	}

	/**
	 * ����������
	 */
	private void init_Spinner() {
		// 0-30����1-31
		int beforeDay = sharedData.getCountDay();
		countDaySpButton_tv.setText((beforeDay + 1) + " ��");
	}

	/**
	 * ���¶���ʾ�ı����г�ʼ�����õ�
	 */
	private void init_btn_month() {
		// ����Ĭ����ʾֵ
		long mobileSetLong = sharedData.getMonthMobileSetOfLong();
		// showlog(mobileSetLong + "");
		monthSet_Unit_tv.setText(UnitHandler.unitHandler(mobileSetLong));

	}

	/**
	 * �Ƿ����Ԥ��������
	 */
	private void init_isAllow_ALert() {
		boolean isAllowAlert = sharedData.IsAllowAlert();
		ImageView changearrow1 = (ImageView) findViewById(R.id.main3_arrow1);
		ImageView changearrow2 = (ImageView) findViewById(R.id.main3_arrow2);
		ImageView changearrow3 = (ImageView) findViewById(R.id.main3_arrow3);
		if (isAllowAlert) {
			isalow_img.setBackgroundResource(R.drawable.check_open);
			changearrow1.setImageResource(R.drawable.arrow_enabled);
			changearrow2.setImageResource(R.drawable.arrow_enabled);
			changearrow3.setImageResource(R.drawable.arrow_enabled);
			warningAlertActionButton.setClickable(true);
			warning_tv1.setTextColor(UiColors.colorDarkGray2);
			warning_tv2.setTextColor(UiColors.colorDarkGray2);
			dayWarningButton.setClickable(true);
			dayWarning_tv1.setTextColor(UiColors.colorDarkGray2);
			dayWarning_tv2.setTextColor(UiColors.colorDarkGray2);
			monthWarningButton.setClickable(true);
			monthWarning_tv1.setTextColor(UiColors.colorDarkGray2);
			monthWarning_tv2.setTextColor(UiColors.colorDarkGray2);
		} else {
			isalow_img.setBackgroundResource(R.drawable.check_close);
			changearrow1.setImageResource(R.drawable.arrow_unabled);
			changearrow2.setImageResource(R.drawable.arrow_unabled);
			changearrow3.setImageResource(R.drawable.arrow_unabled);
			warningAlertActionButton.setClickable(false);
			warning_tv1.setTextColor(Color.GRAY);
			warning_tv2.setTextColor(Color.GRAY);
			dayWarningButton.setClickable(false);
			dayWarning_tv1.setTextColor(Color.GRAY);
			dayWarning_tv2.setTextColor(Color.GRAY);
			monthWarningButton.setClickable(false);
			monthWarning_tv1.setTextColor(Color.GRAY);
			monthWarning_tv2.setTextColor(Color.GRAY);
		}

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
		init_isAllow_ALert();
		// /skin
		init_skin();
		// umeng
		// MobclickAgent.onResume(this);

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

	private void onclickListeners() {
		final CustomDialogMain3Been customDialog = new CustomDialogMain3Been(
				context);
		final CustomSPBeen customSP = new CustomSPBeen(context);
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
		countDaySpButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				customSP.dialogDaySet(countDaySpButton, btn_HasUsed,
						countDaySpButton_tv);
			}
		});
		warningAlertActionButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customSP.dialogAlertType(warningAlertActionButton, warning_tv2);
			}
		});
		dayWarningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customDialog.dialogDayWarning(dayWarningButton, dayWarning_tv2);

			}
		});
		monthWarningButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customDialog.dialogMonthWarning(monthWarningButton,
						monthWarning_tv2);

			}
		});
		btn_monthSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				customDialog.dialogMonthSet_Main3(btn_monthSet, dayWarning_tv2,
						monthWarning_tv2, monthSet_Unit_tv);
			}
		});
		line_isdoAlert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isAllowAlert = sharedData.IsAllowAlert();
				if (isAllowAlert) {
					isalow_img.setBackgroundResource(R.drawable.check_close);
					sharedData.setIsAllowAlert(false);
					init_isAllow_ALert();
				} else {
					isalow_img.setBackgroundResource(R.drawable.check_open);
					sharedData.setIsAllowAlert(true);
					init_isAllow_ALert();
				}

			}
		});
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
