package com.hiapk.customspinner;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.hiapk.alertdialog.CustomDialog;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.prefrencesetting.PrefrenceOperatorUnit;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.regulate.PhoneSet;
import com.hiapk.regulate.Regulate;
import com.hiapk.spearhead.Main3;
import com.hiapk.spearhead.R;
import com.hiapk.widget.SetText;

public class CustomSPPhoneSetBeen {
	Context context;

	// int beforeDay = 0;
	SharedPrefrenceData sharedData;
	Resources res;

	public CustomSPPhoneSetBeen(Context context) {
		this.context = context;
		sharedData = new SharedPrefrenceData(context);
		res = context.getResources();
	}

	/**
	 * phoneset页面显示对话框
	 * 
	 * @param content
	 *            设置显示的内容
	 * @param beforepos
	 *            之前选择的项目
	 * @param title
	 *            对话框的标题
	 * @param type
	 *            表示哪个按钮被按下
	 */
	public void dialogPhoneSetSecond(String[] content, int beforepos,
			String title, final String type, final Button secend) {
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.listview_custom_spinner, null);
		ListView lv_fresh = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		CustomspAdapter adapter = new CustomspAdapter(context, content,
				beforepos);
		lv_fresh.setAdapter(adapter);
		lv_fresh.setDividerHeight(0);
		lv_fresh.setSelection(beforepos);

		final CustomSPDialog phoneset;
		SPDataSet.setHeight(content.length);
		phoneset = new CustomSPDialog.Builder(context).setTitle(title)
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		phoneset.show();
		// 设置cancel的监听
		Button btn_cancel = (Button) phoneset.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30代表1-31
				phoneset.dismiss();
			}
		});

		// 设置中间按钮的监听
		lv_fresh.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (type == "city") {
					sharedData.setCurrentCityID(arg2);
					secend.setText((CharSequence) arg0.getItemAtPosition(arg2));
				}
				if (type == "brand") {
					sharedData.setCurrentPinpaiID(arg2);
					secend.setText((CharSequence) arg0.getItemAtPosition(arg2));
				}
				phoneset.dismiss();
			}

		});
	}

	/**
	 * phoneset页面显示对话框
	 * 
	 * @param content
	 *            设置显示的内容
	 * @param beforepos
	 *            之前选择的项目
	 * @param title
	 *            对话框的标题
	 * @param type
	 *            表示哪个按钮被按下
	 */
	public void dialogPhoneSetProvinceAndOperator(String[] content,
			int beforepos, String title, final String type,
			final Button btn_first, final Button btn_second) {
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.listview_custom_spinner, null);
		ListView lv_fresh = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		CustomspAdapter adapter = new CustomspAdapter(context, content,
				beforepos);
		lv_fresh.setAdapter(adapter);
		lv_fresh.setDividerHeight(0);
		lv_fresh.setSelection(beforepos);

		final CustomSPDialog phoneset;
		SPDataSet.setHeight(content.length);
		phoneset = new CustomSPDialog.Builder(context).setTitle(title)
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		phoneset.show();
		// 设置cancel的监听
		Button btn_cancel = (Button) phoneset.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30代表1-31
				phoneset.dismiss();
			}
		});

		// 设置中间按钮的监听
		lv_fresh.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				btn_first.setText((CharSequence) arg0.getItemAtPosition(arg2));
				phoneSetsharedDataOperate(type, arg2, btn_second);
				phoneset.dismiss();
			}

		});
	}

	private void phoneSetsharedDataOperate(String type, final int arg2,
			final Button btn_second) {
		// TODO Auto-generated method stub
		if (type == "province") {
			if (arg2 != sharedData.getCurrentProvinceID()) {
				sharedData.setCurrentCityID(0);
				sharedData.setCurrentProvinceID(arg2);
				int cityName = SPDataSet.getSpinCity((int) arg2);
				String[] cityNames = res.getStringArray(cityName);
				btn_second.setText(cityNames[0]);
				btn_second.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						init_spin_city(arg2, btn_second);
					}
				});
			}

		}
		if (type == "operator") {
			if (arg2 != sharedData.getCurrentYunyinshangID()) {
				sharedData.setCurrentPinpaiID(0);
				sharedData.setCurrentYunyinshangID(arg2);
				int brandName = SPDataSet.getSpinBrand((int) arg2);
				String[] brandNames = res.getStringArray(brandName);
				btn_second.setText(brandNames[0]);
				btn_second.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						init_spin_pingpai(arg2, btn_second);
					}
				});
			} else {

			}

		}
	}

	private void init_spin_pingpai(int operatorID, Button btn_second) {
		String[] content = res.getStringArray(SPDataSet
				.getSpinBrand(operatorID));
		int beforepos = sharedData.getCurrentPinpaiID();
		dialogPhoneSetSecond(content, beforepos, "请选择品牌", "brand", btn_second);
	}

	private void init_spin_city(int provinceID, Button btn_second) {
		String[] content = res
				.getStringArray(SPDataSet.getSpinCity(provinceID));
		int beforepos = sharedData.getCurrentCityID();
		dialogPhoneSetSecond(content, beforepos, "请选择城市", "city", btn_second);
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
