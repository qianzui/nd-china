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

public class SaveRule {

	private Context mContext;
	public File sdcarddir = android.os.Environment
			.getExternalStorageDirectory();
	public String state = android.os.Environment.getExternalStorageState();
	public String wifiFile = "";
	public String mobileFile = "";
	public String directory = "";
	public String wifi_uninstalled = "";
	public String mobile_uninstalled="";
	public String uninstalledpath = "";

	public SaveRule(Context context) {
		this.mContext = context;
		String path = context.getFilesDir().getAbsolutePath() + File.separator;
		File sdcarddir = android.os.Environment.getExternalStorageDirectory();
		this.directory = sdcarddir.getPath() + "/SpearheadLog/";
		this.wifiFile = path + "/wifi.txt";
		this.mobileFile = path + "/mobile.txt";
		this.wifi_uninstalled = path  + "uninstalledoPkg/wifi.txt";
		this.mobile_uninstalled = path + "uninstalledoPkg/mobile.txt";
		this.uninstalledpath = path  + "uninstalledoPkg/";
	}

	public void saveToMem() throws IOException {
		final SharedPreferences prefs = mContext.getSharedPreferences(
				Block.PREFS_NAME, 0);
		final String savedPkgname_wifi = prefs.getString(Block.PREF_WIFI_PKGNAME, "");
		final String savedPkgname_3g = prefs.getString(Block.PREF_3G_PKGNAME, "");
		
		File file = new File(wifiFile);
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			FileOutputStream outWifi = new FileOutputStream(file, false);
			outWifi.write(savedPkgname_wifi.getBytes());
			outWifi.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		File file2 = new File(mobileFile);
		if (!file2.exists()) {
			file2.createNewFile();
		}
		try {
			FileOutputStream outMobile = new FileOutputStream(file2, false);
			outMobile.write(savedPkgname_3g.getBytes());
			outMobile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件到SD卡
	 */
	public void copyToSD() {
		File filedir = new File(directory);
		File file1 = new File(wifiFile);
		File file2 = new File(mobileFile);
		File filePath = new File(directory);
		// 复制成功后删除原文件
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (!filedir.isDirectory()) {
				boolean mkOk = filedir.mkdirs();
				if (!mkOk) {
					return;
				}
			}
			if (file1.exists()) {
				if (filePath.isDirectory()) {
					if (copy(file1, new File(directory + "/wifi.txt"))) {
//						file1.delete();
					}
				}
			}
			if (file2.exists()) {
				if (filePath.isDirectory()) {
					if (copy(file2, new File(directory + "/mobile.txt"))) {
//						file2.delete();
					}
				}
			}
		}
	}

	/**
	 * 关闭记忆功能时删除sd卡中文件
	 */
	public void deleteRecord(){
		File f1 = new File(directory + "/wifi.txt");
		File f2 = new File(directory + "/mobile.txt");
		File f3 = new File(mobile_uninstalled);
		File f4 = new File(uninstalledpath);
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if(f1.exists()){
				f1.delete();
			}
			if(f2.exists()){
				f2.delete();
			}
			if(f3.exists()){
				f3.delete();
			}
			if(f4.exists()){
				f4.delete();
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
			out.flush();
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

	public String getWifiRules() {
		String wifiRule = "";
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			File f = new File(directory + "/wifi.txt");
			if (f.exists()) {
				try {
					InputStream in = new BufferedInputStream(
							new FileInputStream(f));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "gb2312"));
					wifiRule = br.readLine();
					br.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return wifiRule;
	}

	public String getMobileRules() {
		String mobileRule = "";
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			File f = new File(directory + "/mobile.txt");
			if (f.exists()) {
				try {
					InputStream in = new BufferedInputStream(
							new FileInputStream(f));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "gb2312"));
					mobileRule = br.readLine();
					br.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return mobileRule;
	}

	public void saveUninstalledPkgname3g(String pkgname,Boolean isAppend){
		createDirectory();
		File file = new File(mobile_uninstalled);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream outWifi = new FileOutputStream(file, isAppend);
			outWifi.write(pkgname.getBytes());
			outWifi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logs.i("test", "save to 3gpkgname:" + pkgname);
	}

	public void saveUninstalledPkgnameWifi(String pkgname,Boolean isAppend){
		createDirectory();
		File file = new File(wifi_uninstalled);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream outWifi = new FileOutputStream(file, isAppend);
			outWifi.write(pkgname.getBytes());
			outWifi.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logs.i("test", "path:" + wifi_uninstalled);
		Logs.i("test", "save to wifipkgname:" + pkgname);
	}
	
	public String getSavedUnintalledPkanameMobile() {
		String mobilePkgname = "";
		File f = new File(mobile_uninstalled);
		if (f.exists()) {
			try {
				InputStream in = new BufferedInputStream(
						new FileInputStream(f));
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in, "gb2312"));
				mobilePkgname = br.readLine();
				br.close();
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	return mobilePkgname;
	}
	public String getSavedUnintalledPkanameWifi() {
		String wifiPkgname = "";
			File f = new File(wifi_uninstalled);
			if (f.exists()) {
				try {
					InputStream in = new BufferedInputStream(
							new FileInputStream(f));
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in, "gb2312"));
					wifiPkgname = br.readLine();
					br.close();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		return wifiPkgname;
	}
	public void createDirectory(){
		File f = new File(uninstalledpath);
		if(f.exists()){
		}else{
			f.mkdirs();
		}
	}
}
