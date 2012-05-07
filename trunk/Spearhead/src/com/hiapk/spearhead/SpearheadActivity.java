package com.hiapk.spearhead;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SpearheadActivity extends TabActivity {
	private static RadioGroup group;
	public static TabHost tabHost;
	public static final String TAB_MONITOR = "tabMonitor";
	public static final String TAB_FIREWALL = "tabFireWall";
	public static final String TAB_WARNING = "tabWarning";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		group = (RadioGroup) findViewById(R.id.main_radio);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_MONITOR)
				.setIndicator(TAB_MONITOR)
				.setContent(new Intent(this, Main.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_FIREWALL)
				.setIndicator(TAB_FIREWALL)
				.setContent(new Intent(this, FireWallActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_WARNING)
				.setIndicator(TAB_WARNING)
				.setContent(new Intent(this, Main3.class)));
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_MONITOR);
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag(TAB_FIREWALL);
					break;
				case R.id.radio_button2:
					tabHost.setCurrentTabByTag(TAB_WARNING);
					break;

				default:
					break;
				}
			}
		});
		// —°‘ÒΩÁ√Ê
		Bundle choose = this.getIntent().getExtras();
		int tab = choose.getInt("TAB");
		Log.d("spearhead", tab+"");
		switch (tab) {
		case 3:
			group.clearCheck();
			group.check(R.id.radio_button2);
			tabHost.setCurrentTabByTag(TAB_WARNING);
			break;

		default:
			group.clearCheck();
			group.check(R.id.radio_button0);
			tabHost.setCurrentTabByTag(TAB_MONITOR);
			break;
		}
	}

	public void tabThree() {
		group.clearCheck();
		group.check(R.id.radio_button2);
		tabHost.setCurrentTabByTag(TAB_WARNING);
	}

}