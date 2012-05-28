package com.hiapk.regulate;

import com.hiapk.prefrencesetting.SharedPrefrenceData;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

public class SmsRead {
	static boolean isRead = true;
	SharedPrefrenceData sharedData;

	public String Sms(Activity at) {
		sharedData = new SharedPrefrenceData(at);
		String[] projection = new String[] { "_id", "address", "person", "body" };
		StringBuilder strBuilder = new StringBuilder();
		try {
			Cursor myCursor = at.managedQuery(Uri.parse("content://sms/inbox"),
					projection, null, null, "date desc");
			strBuilder.append(processResults(myCursor, true));

		} catch (SQLiteException ex) {
			Log.d("-----------------", ex.getMessage());
		}
		return strBuilder.toString();

	}

	public StringBuilder processResults(Cursor cur, boolean all) {
		StringBuilder strBuilder = new StringBuilder();
		if (cur.moveToFirst()) {
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

				if (null == sms) {
					sms = "";
					isRead = false;
				}

				count = judge(name, phoneNum, sms);
				// strBuilder.append(start+",");
				// strBuilder.append(name+",");
				// strBuilder.append(phoneNum+",");
				// strBuilder.append(sms);
				// strBuilder.append(end+",");
				strBuilder.append(count);

				if (!count.equalsIgnoreCase("无短信内容")) {
					Regulate.smsResult.setText("本月已用流量为:" + count + " MB");
					float monthHasUse = Float.valueOf(count);
					sharedData.setMonthMobileHasUseOffloat(monthHasUse);
					sharedData
							.setMonthMobileHasUse((long) (monthHasUse * 1024) * 1024);
					break;
				} else {
					isRead = false;
				}

			} while (cur.moveToNext());
		} else {
			isRead = false;
			strBuilder.append("无短信内容!");
		}

		return strBuilder;

	}

	public String judge(String name, String phoneNum, String sms) {
		String num;
		int start;
		int end;
		String count = "无短信内容";

		num = Regulate.smsNum.getText().toString();
		if (phoneNum.equalsIgnoreCase(num)) {
			start = sms.indexOf("为");
			end = sms.indexOf("M");
			if (start != -1) {
				isRead = true;
				count = sms.substring(start + 1, end);
			}

		}
		return count;
	}

}
