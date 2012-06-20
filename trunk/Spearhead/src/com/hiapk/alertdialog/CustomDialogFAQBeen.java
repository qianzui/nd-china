package com.hiapk.alertdialog;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiapk.dataexe.ColorChangeMainBeen;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.PhoneSet;
import com.hiapk.regulate.Regulate;
import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.Main3;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomDialogFAQBeen {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 总流量long
	String VALUE_MOBILE_SET = "mobilemonthuse";
	// 显示在预警页面的int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// 设置单位（月度设置）
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 设置结算日期及结算日期的设施时间，日期等
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// 已使用总流量int
	String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	// 设置单位（已使用）
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// 已使用总流量long
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
	// 流量预警
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	String WARNING_ACTION = "warningaction";
	Context context;

	public CustomDialogFAQBeen(Context context) {
		this.context = context;
	}

	/**
	 * 本月已用弹出的对话框
	 * 
	 * @param TextView_month
	 *            传入点击的TextView
	 * @return 返回对话框
	 */
	public void dialogFAQ() {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.faq, null);
		final CustomDialogFAQ monthHasUsedAlert = new CustomDialogFAQ.Builder(
				context).setTitle("先锋流量监控  FAQ :")
				.setContentView(textEntryView).setPositiveButton("确定", null)
				.create();
		try {
			WebView wb = (WebView) textEntryView.findViewById(R.id.webview);
			String url = "file:///android_asset/faq/faq.html";
			wb.loadUrl(url);
		} catch (Exception e) {
			System.out.println("Exception while showing PopUp : "
					+ e.getMessage());
		}
		monthHasUsedAlert.show();

		Button btn_ok = (Button) monthHasUsedAlert
				.findViewById(R.id.positiveButton);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
		// Log.d("main3", string);
	}
}
