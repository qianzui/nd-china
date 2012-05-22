package com.hiapk.regulate;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.spearhead.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class PhoneSet extends Activity{	
	Spinner province;
	Spinner city;
	Spinner operator;
	Spinner brand;	
	Button next;
	SharedPrefrenceData sharedData;
	Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phoneset);
		sharedData = new SharedPrefrenceData(this);
		init_Spinner();
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				end();
				
			}
		});

	}
	private void init_Spinner() {
		// TODO Auto-generated method stub
		// 初始化				
		province = (Spinner) findViewById(R.id.province);
		operator = (Spinner) findViewById(R.id.operator);		
		
		
		ArrayAdapter<CharSequence> adpProvince = ArrayAdapter.createFromResource(this,
				R.array.province, R.layout.sptext);
		adpProvince.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province.setAdapter(adpProvince);
		
		ArrayAdapter<CharSequence> adpOperator = ArrayAdapter.createFromResource(this,
				R.array.operator, R.layout.sptext);
		adpOperator.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		operator.setAdapter(adpOperator);
		
		province.setSelection(sharedData.getProvinceID());
		
		operator.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				switch (pos){
				case 0:					
					yidong();
					break;
				case 1:					
					liantong();
					break;
				case 2:					
					dianxin();
					break;
				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		province.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				switch (pos) {
				case 0:					
					beijing();
					
					break;
				case 1:					
					guangdong();
					break;
				case 2:					
					shanghai();
					break;
				case 3:					
					tianjin();
					break;
				case 4:					
					chongqing();
					break;
				case 5:					
					liaoning();
					break;
				case 6:					
					jiangsu();
					break;
				case 7:					
					hubei();
					break;
				case 8:					
					sichuan();
					break;
				case 9:					
					shanxi_1();
					break;
				case 10:					
					hebei();
					break;
				case 11:					
					shanxi_2();
					break;
				case 12:					
					henan();
					break;
				case 13:					
					jilin();
					break;
				case 14:					
					heilongjiang();
					break;
				case 15:					
					neimenggu();
					break;
				case 16:					
					shandong();
					break;
				case 17:					
					anhui();
					break;
				case 18:					
					zhejiang();
					break;
				case 19:					
					fujian();
					break;
				case 20:					
					hunan();
					break;
				case 21:					
					guangxi();
					break;
				case 22:					
					jiangxi();
					break;
				case 23:					
					guizhou();
					break;
				case 24:					
					yunnan();
					break;
				case 25:					
					xizang();
					break;
				case 26:					
					hainan();
					break;
				case 27:					
					gansu();
					break;
				case 28:					
					ningxia();
					break;
				case 29:					
					qinghai();
					break;
				case 30:					
					xinjiang();
					break;
				default:
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		

	}
	
	//不同省的城市选择	
	void beijing(){		
		city = (Spinner) findViewById(R.id.city);		
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.beijing, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);

	}
	void guangdong(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.guangdong, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}

	void shanghai(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.shanghai, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	
	void tianjin(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.tianjin, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void chongqing(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.chongqing, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void liaoning(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.liaoning, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void jiangsu(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.jiangsu, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void hubei(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.hubei, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void sichuan(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.sichuan, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void shanxi_1(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.shanxi_1, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void hebei(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.hebei, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void shanxi_2(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.shanxi_2, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void henan(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.henan, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void jilin(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.jilin, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void heilongjiang(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.heilongjiang, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void neimenggu(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.neimenggu, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void shandong(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.shandong, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void anhui(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.anhui, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void zhejiang(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.zhejiang, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void fujian(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.fujian, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void hunan(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.hunan, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void guangxi(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.guangxi, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void jiangxi(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.jiangxi, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void guizhou(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.guizhou, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void yunnan(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.yunnan, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void xizang(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.xizang, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void hainan(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.hainan, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void gansu(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.gansu, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void ningxia(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.ningxia, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void qinghai(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.qinghai, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	void xinjiang(){		
		city = (Spinner) findViewById(R.id.city);
		ArrayAdapter<CharSequence> adpCity = ArrayAdapter.createFromResource(this,
				R.array.xinjiang, R.layout.sptext);
		adpCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		city.setAdapter(adpCity);
	}
	
	//不同运营商
	void yidong(){
		brand = (Spinner) findViewById(R.id.brand);		
		ArrayAdapter<CharSequence> adpBrand = ArrayAdapter.createFromResource(this,
				R.array.yidong, R.layout.sptext);
		adpBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		brand.setAdapter(adpBrand);
	}
	void liantong(){
		brand = (Spinner) findViewById(R.id.brand);		
		ArrayAdapter<CharSequence> adpBrand = ArrayAdapter.createFromResource(this,
				R.array.liantong, R.layout.sptext);
		adpBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		brand.setAdapter(adpBrand);
	}
	void dianxin(){
		brand = (Spinner) findViewById(R.id.brand);		
		ArrayAdapter<CharSequence> adpBrand = ArrayAdapter.createFromResource(this,
				R.array.dianxin, R.layout.sptext);
		adpBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		brand.setAdapter(adpBrand);
	}
	
	void end(){		
		String shengfen; //所选省份
		String chengshi;// 所选城市
		String yunyingshang; //所选运营商
		String pinpai;	//所选品牌		
		int shengfenId; //省份位置
//		int pinpaiId; //品牌位置
		String smsNum;
		String smsText;
		shengfen = province.getSelectedItem().toString();
		chengshi = city.getSelectedItem().toString()+"--";
		yunyingshang = operator.getSelectedItem().toString();
		pinpai = brand.getSelectedItem().toString();		
		shengfenId = province.getSelectedItemPosition();
//		pinpaiId = brand.getSelectedItemPosition();		
		SmsSet.smsSet(shengfen,yunyingshang, pinpai);
		smsNum = Regulate.smsNum.getText().toString();
		smsText = Regulate.smsText.getText().toString();

		sharedData.setPhoneInfo(chengshi, pinpai,shengfenId,smsNum,smsText);
		Regulate.chooseBtn.setText(chengshi+pinpai);
		finish();
	}
	
}
