package com.hiapk.logs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.hiapk.firewall.Block;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SaveRule {

	private Context mContext;
	public File sdcarddir = android.os.Environment.getExternalStorageDirectory();
	public String state = android.os.Environment.getExternalStorageState();
	public String wifiFile = sdcarddir.getPath() + "/SpearheadLog/wifi.txt";
	public String mobileFile = sdcarddir.getPath() + "/SpearheadLog/mobile.txt";
	
	public SaveRule(Context context){
		this.mContext = context;
	}
	public void saveToSD() throws IOException{
		final  SharedPreferences prefs = mContext.getSharedPreferences(
				Block.PREFS_NAME, 0);
		final String savedUids_wifi = prefs.getString(Block.PREF_WIFI_UIDS, "");
		final String savedUids_3g = prefs.getString(Block.PREF_3G_UIDS, "");
		if(android.os.Environment.MEDIA_MOUNTED.equals(state)){
		        File file = new File(wifiFile);
		        if (!file.exists())
		        {
		            file.createNewFile();
		        }
		        try {
					FileOutputStream outWifi = new FileOutputStream(file, false);
					outWifi.write(savedUids_wifi.getBytes());
					outWifi.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File file2 = new File(mobileFile);
		        if (!file2.exists())
		        {
		            file2.createNewFile();
		        }
		        try {
					FileOutputStream outMobile = new FileOutputStream(file2, false);
					outMobile.write(savedUids_3g.getBytes());
					outMobile.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
		}else{
			Toast.makeText(mContext, "SD¿¨²»´æÔÚ£¡", Toast.LENGTH_LONG).show();
		}
	}
	
	public String getWifiRules(){
		String wifiRule="";
		if(android.os.Environment.MEDIA_MOUNTED.equals(state)){
			File f = new File(wifiFile);
			if(f.exists()){
				try {
					InputStream in = new  BufferedInputStream(new FileInputStream(f));
					BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
					wifiRule = br.readLine();
					br.close();
					in.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return wifiRule;
	}
	public String getMobileRules(){
		String mobileRule="";
		if(android.os.Environment.MEDIA_MOUNTED.equals(state)){
			File f = new File(mobileFile);
			if(f.exists()){
				try {
					InputStream in = new  BufferedInputStream(new FileInputStream(f));
					BufferedReader br = new BufferedReader(new InputStreamReader(in, "gb2312"));
					mobileRule = br.readLine();
					br.close();
					in.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		return mobileRule;
	}

}
