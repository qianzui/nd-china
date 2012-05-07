package com.hiapk.regulate;

import com.hiapk.spearhead.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PhoneSet extends Activity{	
	Spinner province;
	Spinner city;
	Spinner operator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.v("+++++++++++++++++++", "out");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phoneset);
		Log.v("+++++++++++++++++++", "onCreate");
		init_Spinner();

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
}
