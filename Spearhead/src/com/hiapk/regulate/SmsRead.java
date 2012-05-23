package com.hiapk.regulate;

import com.hiapk.spearhead.Main3;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class SmsRead  {
	

	public String Sms(Activity at){
		String[] projection =  new String[]{
				"_id",
				"address",
				"person",
				"body"			
		};
		StringBuilder strBuilder = new StringBuilder();
		try {
			Cursor myCursor = at.managedQuery(Uri.parse("content://sms/inbox"),
					projection,
					null, null , "date desc");
			strBuilder.append(processResults(myCursor, true));  
			
		}
		catch (SQLiteException ex)
		{
			Log.d("-----------------", ex.getMessage());
		}
		return strBuilder.toString();

	}

	public StringBuilder processResults(Cursor cur,boolean all){
		StringBuilder strBuilder = new StringBuilder();
		if(cur.moveToFirst()){
			String name;
			String phoneNum;
			String sms;			
			String count;
			int nameColumn = cur.getColumnIndex("person");
			int phoneColumn = cur.getColumnIndex("address");
			int smsColumn = cur.getColumnIndex("body");

			do {
				name = cur.getString(nameColumn);             
				phoneNum = cur.getString(phoneColumn);
				sms = cur.getString(smsColumn);
				
				count = judge(name, phoneNum, sms);
//				strBuilder.append(start+",");
//				strBuilder.append(name+",");
//				strBuilder.append(phoneNum+",");
//				strBuilder.append(sms);
//				strBuilder.append(end+",");
				strBuilder.append(count);
				Regulate.smsResult.setText("本月已用流量为:"+count+" MB");


				if (null==sms){
					sms="";
				}					
				if (!count.equalsIgnoreCase("0")){
					break;
				}
			
			} while (cur.moveToNext());
		}
		else{
			strBuilder.append("no result!");
		}
		
		return strBuilder;

	}
	public String  judge(String name,String phoneNum,String sms){
		String num;
		int start;
		int end;
		String count = "0";
		
		num = Regulate.smsNum.getText().toString();
		if(phoneNum.equalsIgnoreCase(num)){
			start = sms.indexOf("为");
			end = sms.indexOf("M");
			count = sms.substring(start+1, end);
		}
		return count;
	}

}
