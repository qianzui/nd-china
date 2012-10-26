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
import android.util.Log;

public class SaveRule {

	private Context mContext;
	public File sdcarddir = android.os.Environment.getExternalStorageDirectory();
	public String state = android.os.Environment.getExternalStorageState();
	public String wifiFile = "";
	public String mobileFile = "";
	public String directory = "";
	
	public SaveRule(Context context){
		this.mContext = context;
		String path = context.getFilesDir().getAbsolutePath()+ File.separator;
		File sdcarddir = android.os.Environment.getExternalStorageDirectory();
        this.directory = sdcarddir.getPath() + "/SpearheadLog/";
		this.wifiFile = path + "/wifi.txt";
		this.mobileFile = path + "/mobile.txt";
	}
	public void saveToMem() throws IOException{
		final  SharedPreferences prefs = mContext.getSharedPreferences(
				Block.PREFS_NAME, 0);
		final String savedUids_wifi = prefs.getString(Block.PREF_WIFI_UIDS, "");
		final String savedUids_3g = prefs.getString(Block.PREF_3G_UIDS, "");
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
	}
	
	public void copyToSD(){
		File file1 = new File(wifiFile);
		File file2 = new File(mobileFile);
		File filePath = new File(directory);
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if(file1.exists()){
				if(filePath.isDirectory()){
					copy(file1,new File(directory + "/wifi.txt"));
				}
			}
			if(file2.exists()){
				if(filePath.isDirectory()){
					copy(file2,new File(directory + "/mobile.txt"));
				}
			}
		}
	}
	
	private boolean copy(File source, File target) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			if (!target.exists()) {
				boolean createSucc = target.createNewFile();
				if (!createSucc) {
					return false;
				}
			}
			in = new FileInputStream(source);
			out = new FileOutputStream(target, false);
			byte[] buffer = new byte[8 * 1024];
			int count;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

	}
	public String getWifiRules(){
		String wifiRule="";
			File f = new File(directory + "/wifi.txt");
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
		f.delete();
		return wifiRule;
	}
	public String getMobileRules(){
		String mobileRule="";
		File f = new File(directory + "/mobile.txt");
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
		f.delete();
		return mobileRule;
	}

}
