package com.apk.wifi;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainScene extends Activity {
	private Button btn_wifi_control, btn_2g_control, btn_3g_control;
	private ImageButton imbtn_wifi, imbtn_2g, imbtn_3g;
	private TextView tv_showstate;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initScene();
		btnControl();

	}

	public void initScene() {
		imbtn_wifi = (ImageButton) findViewById(R.id.imbtn_wifi_state);
		imbtn_2g = (ImageButton) findViewById(R.id.imbtn_2g_state);
		imbtn_3g = (ImageButton) findViewById(R.id.imbtn_3g_state);
		WifiManager wfm_state;
		wfm_state = (WifiManager) MainScene.this
				.getSystemService(Context.WIFI_SERVICE);
		if (wfm_state.isWifiEnabled()) {
			imbtn_wifi.setBackgroundResource(R.drawable.onfocus);

		} else {
			imbtn_wifi.setBackgroundResource(R.drawable.lostfocus);
		}
	}

	public void btnControl() {
		// imbtn_wifi=(ImageButton) findViewById(R.id.imbtn_wifi_state);
		// btn_wifi_control = (Button) findViewById(R.id.btn_wifion_off);
		OnClickListener ocl_wifi = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				wifiControl();
			}
		};

		imbtn_wifi.setOnClickListener(ocl_wifi);
	}
	public void gsmControl(){
		
	}
	public void wifiControl() {

		WifiManager wfm_on_off;
		tv_showstate = (TextView) findViewById(R.id.tv_showstate);
		wfm_on_off = (WifiManager) MainScene.this
				.getSystemService(Context.WIFI_SERVICE);

		if (wfm_on_off.isWifiEnabled()) {
			wfm_on_off.setWifiEnabled(false);
			tv_showstate.setText("wifi正在关闭");
			imbtn_wifi.setBackgroundResource(R.drawable.lostfocus);
			Toast.makeText(MainScene.this, "wifi正在关闭", Toast.LENGTH_SHORT)
					.show();

		} else {
			wfm_on_off.setWifiEnabled(true);
			tv_showstate.setText("wifi已开启");
			imbtn_wifi.setBackgroundResource(R.drawable.onfocus);
			Toast.makeText(MainScene.this, "wifi正在开启", Toast.LENGTH_SHORT)
					.show();
		}

	}

}