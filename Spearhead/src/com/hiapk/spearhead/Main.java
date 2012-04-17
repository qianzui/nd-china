package com.hiapk.spearhead;


import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.widget.TextView;
public class Main extends Activity {
	TextView todayMobil;
	long todayMb;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        todayMobil = (TextView)findViewById(R.id.todayRate);
        todayMb = TrafficStats.getTotalRxBytes()/1024/1024;
        todayMobil.setText(Long.toString(todayMb)+"MB");
        
    }

	
}