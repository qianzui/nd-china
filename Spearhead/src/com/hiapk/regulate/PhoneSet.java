package com.hiapk.regulate;

import com.hiapk.customspinner.CustomSPBeen;
import com.hiapk.customspinner.CustomSPPhoneSetBeen;
import com.hiapk.customspinner.SPDataSet;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class PhoneSet extends Activity {
	Button province;
	Button city;
	Button operator;
	Button brand;
	Button next;
	SharedPrefrenceData sharedData;
	Resources res;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phoneset);
		// MobclickAgent.onError(this);
		sharedData = new SharedPrefrenceData(this);
		res = context.getResources();
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedData.setIsFirstRegulate(false);
				// long monthSetofLong = sharedData.getMonthMobileSetOfLong();
				// if (monthSetofLong == 0) {
				// sharedData.setMonthSetHasSet(false);
				// } else {
				// sharedData.setMonthSetHasSet(true);
				// }
				recordAndFinish();

			}
		});

	}

	private void setonItemSelectListener() {
		final CustomSPPhoneSetBeen customSP = new CustomSPPhoneSetBeen(context);
		final Resources res = context.getResources();
		province = (Button) findViewById(R.id.province);
		operator = (Button) findViewById(R.id.operator);
		city = (Button) findViewById(R.id.city);
		brand = (Button) findViewById(R.id.brand);
		province.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] content = res.getStringArray(R.array.province);
				int beforepos = sharedData.getCurrentProvinceID();
				customSP.dialogPhoneSetProvinceAndOperator(content, beforepos,
						"请选择省份", "province", province, city);
			}
		});
		operator.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] content = res.getStringArray(R.array.operator);
				int beforepos = sharedData.getCurrentYunyinshangID();
				customSP.dialogPhoneSetProvinceAndOperator(content, beforepos,
						"请选择运营商", "operator", operator, brand);
			}
		});
		city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] content = res.getStringArray(SPDataSet
						.getSpinCity(sharedData.getCurrentProvinceID()));
				int beforepos = sharedData.getCurrentCityID();
				customSP.dialogPhoneSetSecond(content, beforepos, "请选择城市",
						"city", city);
			}
		});
		brand.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] content = res.getStringArray(SPDataSet
						.getSpinBrand(sharedData.getCurrentYunyinshangID()));
				int beforepos = sharedData.getCurrentPinpaiID();
				customSP.dialogPhoneSetSecond(content, beforepos, "请选择品牌",
						"brand", brand);
			}
		});
	}

	private void init_spinners() {
		province = (Button) findViewById(R.id.province);
		operator = (Button) findViewById(R.id.operator);
		city = (Button) findViewById(R.id.city);
		brand = (Button) findViewById(R.id.brand);
		String[] content = res.getStringArray(R.array.province);
		int beforepos = sharedData.getCurrentProvinceID();
		province.setText(content[beforepos]);
		content = res.getStringArray(R.array.operator);
		beforepos = sharedData.getCurrentYunyinshangID();
		operator.setText(content[beforepos]);
		content = res.getStringArray(SPDataSet.getSpinBrand(sharedData
				.getCurrentYunyinshangID()));
		beforepos = sharedData.getCurrentPinpaiID();
		brand.setText(content[beforepos]);
		content = res.getStringArray(SPDataSet.getSpinCity(sharedData
				.getCurrentProvinceID()));
		beforepos = sharedData.getCurrentCityID();
		city.setText(content[beforepos]);
	}

	void recordAndFinish() {
		String shengfen = sharedData.getCurrentProvince(); // 所选省份
		String chengshi = sharedData.getCurrentCity() + "--";// 所选城市
		String yunyingshang = sharedData.getCurrentYunyinshang(); // 所选运营商
		String pinpai = sharedData.getCurrentPinpai(); // 所选品牌
		int shengfenId = sharedData.getCurrentProvinceID(); // 省份位置
//		 int pinpaiId; //品牌位置
		String smsNum;
		String smsText;
		 shengfen = province.getText().toString();
		 chengshi = city.getText().toString() + "--";
		 yunyingshang = operator.getText().toString();
		 pinpai = brand.getText().toString();
		 shengfenId = sharedData.getCurrentProvinceID();
//		 pinpaiId = brand.getSelectedItemPosition();
		// 记录进xml
		 sharedData.setCurrentCity(chengshi);
		 sharedData.setCurrentProvince(shengfen);
		 sharedData.setCurrentYunyinshang(yunyingshang);
		 sharedData.setCurrentPinpai(pinpai);

		SmsSet.smsSet(context, shengfen, yunyingshang, pinpai);
		// smsNum = Regulate.smsNum.getText().toString();
		// smsText = Regulate.smsText.getText().toString();
		smsNum = sharedData.getSmsNum();
		smsText = sharedData.getSmsText();

		sharedData.setPhoneInfo(chengshi, pinpai, shengfenId, smsNum, smsText);
		// Regulate.chooseBtn.setText(chengshi + pinpai);
		sharedData.setChooseCity(chengshi + pinpai);
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		init_spinners();
		setonItemSelectListener();
		// umeng
		// MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		recordAndFinish();
		finish();
	}

	// private void init_spin_pingpai(int pinpaiID) {
	// switch (pinpaiID) {
	// case 0:
	// yidong();
	// break;
	// case 1:
	// liantong();
	// break;
	// case 2:
	// dianxin();
	// break;
	// default:
	// break;
	// }
	// }

	private void brand_itemSelectlistener(Spinner brand) {
		brand.setSelection(sharedData.getCurrentPinpaiID());
		brand.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				sharedData.setCurrentPinpaiID(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

	private void city_itemSelectlistener(Spinner city) {
		city.setSelection(sharedData.getCurrentCityID());
		city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				sharedData.setCurrentCityID(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

}
