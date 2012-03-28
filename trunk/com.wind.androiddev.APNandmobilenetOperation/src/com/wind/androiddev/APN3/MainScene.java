package com.wind.androiddev.APN3;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainScene extends Activity {
	/** Called when the activity is first created. */
	
//	Uri uri = Uri.parse("content://telephony/carriers"); 
	Uri uri = Uri.parse("content://telephony/carriers/current"); 
//	Uri uri = Uri.parse("content://telephony/carriers/preferapn"); 
	String disable_APN="hiapk";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btn_open = (Button) findViewById(R.id.open);
		Button btn_close = (Button) findViewById(R.id.close);
		Button btn_getinfo = (Button) findViewById(R.id.getinfo);
		
		OnClickListener ocl_open =new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		};
		
		btn_open.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				openAPN();
				openMOBILE_NET();
			}
		});
		btn_close.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				closeAPN();
				closeMOBILE_NET();
				
				
			}
		});
		btn_getinfo.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				openAPN();
				List<APN> list = getAPNList();
			}
		});
	}

	protected void openMOBILE_NET() {
		// TODO Auto-generated method stub
		MobileDataControl p = new MobileDataControl();
		if (!p.isMobileDataEnable(getApplicationContext())) {
			
			p.setMobileDataEnable(MainScene.this);
		}

	}

	protected void closeMOBILE_NET() {
		// TODO Auto-generated method stub
		MobileDataControl p = new MobileDataControl();
		if (p.isMobileDataEnable(getApplicationContext())) {
			
			p.setMobileDataDisable(MainScene.this);
		}
	}

	protected void closeAPN() {
		// TODO Auto-generated method stub
		List<APN> list = getAPNList();
		for (APN apn : list) {
			ContentValues cv = new ContentValues();
			cv.put("apn", APNMatchTools.matchAPN(apn.apn) + disable_APN);
			cv.put("type", APNMatchTools.matchAPN(apn.type) + disable_APN);
			getContentResolver().update(uri, cv, "_id=?",
					new String[] { apn.id });
		}

	}

	protected void openAPN() {
		// TODO Auto-generated method stub
		List<APN> list = getAPNList();
		for (APN apn : list) {
			ContentValues cv = new ContentValues();
			cv.put("apn", APNMatchTools.matchAPN(apn.apn));
			cv.put("type", APNMatchTools.matchAPN(apn.type));
			getContentResolver().update(uri, cv, "_id=?",
					new String[] { apn.id });
		}
	}

	private List<APN> getAPNList() {
		String tag = "MainScene.getAPNList()";
		// current不为空表示可以使用的APN
		String projection[] = { "_id,apn,type,current,name,numeric,mcc,mnc,user,server,password,proxy,port,mmsproxy,mmsport,mmsc" };
//		String projection[] = { "_id,apn,type,current,name,user,server,password" };
		Cursor cr = this.getContentResolver().query(uri, projection, null,
				null, null);
		List<APN> list = new ArrayList<APN>();
		while (cr != null && cr.moveToNext()) {
			/*
			 * 获取读取的数据库日志
			 */
			Log.d(tag,
					cr.getString(cr.getColumnIndex("_id")) + " apn| "
							+ cr.getString(cr.getColumnIndex("apn")) + " type| "
							+ cr.getString(cr.getColumnIndex("type")) + " name| "
							+ cr.getString(cr.getColumnIndex("current")) + " current| "
							+ cr.getString(cr.getColumnIndex("name"))+ " mcc| "
							+ cr.getString(cr.getColumnIndex("numeric"))+ " mcc| "
							+ cr.getString(cr.getColumnIndex("mcc"))+ " mnc| "
							+ cr.getString(cr.getColumnIndex("mnc"))+ " user| "
							+ cr.getString(cr.getColumnIndex("user"))+ " server| "
							+ cr.getString(cr.getColumnIndex("server"))+ " password| "
							+ cr.getString(cr.getColumnIndex("password"))+ " proxy| "
							+ cr.getString(cr.getColumnIndex("proxy"))+ " proxy| "
							+ cr.getString(cr.getColumnIndex("port"))+ " port| "
							+ cr.getString(cr.getColumnIndex("mmsproxy"))+ " mmsproxy| "
							+ cr.getString(cr.getColumnIndex("mmsport"))+ " mmsport| "
							+ cr.getString(cr.getColumnIndex("mmsc"))+ " mmsport| " 
//							+ cr.getString(cr.getColumnIndex("carrier_enabled")) + " carrier_enabled| "
							);
			APN a = new APN();
			a.id = cr.getString(cr.getColumnIndex("_id"));
			a.apn = cr.getString(cr.getColumnIndex("apn"));
			a.type = cr.getString(cr.getColumnIndex("type"));
			if (APNMatchTools.isgprs(a.apn)) {
				list.add(a);
			}
			
		}
		if (cr != null)
			cr.close();
		return list;
	}

	public static class APN{ 
		String id; 
		String apn; 
		String type; 
		} 
}