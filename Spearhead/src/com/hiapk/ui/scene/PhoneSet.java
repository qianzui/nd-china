package com.hiapk.ui.scene;

import com.hiapk.spearhead.R;
import com.hiapk.ui.custom.CustomSPDataSet;
import com.hiapk.ui.custom.CustomSPPhoneSetBeen;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceDataRegulate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PhoneSet extends Activity {
	Button province;
	Button city;
	Button operator;
	Button brand;
	Button next;
	SharedPrefrenceDataRegulate sharedData;
	Resources res;
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phoneset);
		// MobclickAgent.onError(this);
		sharedData = new SharedPrefrenceDataRegulate(this);
		res = context.getResources();

		final ImageView back = (ImageView) findViewById(R.id.phoneset_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				back.setImageResource(R.drawable.back_black);
			}
		});

		next = (Button) findViewById(R.id.next);
		// 设置皮肤
		next.setBackgroundResource(SkinCustomMains.buttonBackgroundLight());
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
				String[] content = res.getStringArray(CustomSPDataSet
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
				String[] content = res.getStringArray(CustomSPDataSet
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
		content = res.getStringArray(CustomSPDataSet.getSpinBrand(sharedData
				.getCurrentYunyinshangID()));
		beforepos = sharedData.getCurrentPinpaiID();
		brand.setText(content[beforepos]);
		content = res.getStringArray(CustomSPDataSet.getSpinCity(sharedData
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
		// int pinpaiId; //品牌位置
		String smsNum;
		String smsText;
		shengfen = province.getText().toString();
		chengshi = city.getText().toString() + "--";
		yunyingshang = operator.getText().toString();
		pinpai = brand.getText().toString();
		shengfenId = sharedData.getCurrentProvinceID();
		// pinpaiId = brand.getSelectedItemPosition();
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
		Intent intent = new Intent();
		intent.setClass(PhoneSet.this, Regulate.class);
		context.startActivity(intent);
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

}
