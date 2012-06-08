package com.hiapk.regulate;

import com.hiapk.prefrencesetting.SharedPrefrenceData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.util.Log;

public class SmsRead {
	public boolean isRead = true;
	SharedPrefrenceData sharedData;

	public String Sms(Context context) {
		sharedData = new SharedPrefrenceData(context);
		String[] projection = new String[] { "_id", "address", "person", "body" };
		StringBuilder strBuilder = new StringBuilder();
		try {
			Cursor myCursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"),
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

				if (!count.equalsIgnoreCase("�޶�������")) {
					float monthHasUse = Float.valueOf(count);
					sharedData.setMonthMobileHasUseOffloat(monthHasUse);
					sharedData
					.setMonthMobileHasUse((long) (monthHasUse * 1024) * 1024);
					isRead = true;
					break;
				} else {
					isRead = false;
				}

			} while (cur.moveToNext());
		} else {
			isRead = false;
			strBuilder.append("�޶�������!");
		}

		return strBuilder;

	}

	public String judge(String name, String phoneNum, String sms) {
		String num;
		int start;
		int end;
		String count = "�޶�������";
		//		String regEx ="\\d*";
		String result = "�޶�������";
		//		Pattern p = Pattern.compile(regEx);

		num = Regulate.smsNum.getText().toString();
		if (phoneNum.equalsIgnoreCase(num)) {
			if(num.equalsIgnoreCase("10086")){
				start = sms.indexOf("Ϊ");
				end = sms.indexOf("M");
				if (start != -1) {
					isRead = true;
					count = sms.substring(start + 1, end);

				}

			}
			else if(num.equalsIgnoreCase("10010")){
				start = sms.indexOf("��");
				end = sms.indexOf("M");
				if (start != -1) {
					isRead = true;
					count = sms.substring(start + 1, end);
				}
			}
			else if(num.equalsIgnoreCase("10001")){
				start = sms.lastIndexOf("��");
				end = sms.indexOf("��");
				if (start != -1) {
					isRead = true;
					count = sms.substring(start + 1, end);
				}
			}

		}

		if(Character.isDigit(count.charAt(0))){
			result = count;
		}


		//		Log.v("++++++++++", m.replaceAll("").trim()+"");


		return result;
	}

}
