package com.hiapk.regulate;

public class SmsRead {
//	public boolean isRead = true;
//	SharedPrefrenceData sharedData;
//
//	public String Sms(Context context) {
//		sharedData = new SharedPrefrenceData(context);
//		String[] projection = new String[] { "_id", "address", "person", "body" };
//		StringBuilder strBuilder = new StringBuilder();
//		try {
//			Cursor myCursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"),
//					projection, null, null, "date desc");
//			strBuilder.append(processResults(myCursor, true));
//
//		} catch (SQLiteException ex) {
//			Log.d("-----------------", ex.getMessage());
//		}
//		return strBuilder.toString();
//
//	}
//
//	public StringBuilder processResults(Cursor cur, boolean all) {
//		StringBuilder strBuilder = new StringBuilder();
//		if (cur.moveToFirst()) {
//			String name;
//			String phoneNum;
//			String sms;
//			String count;
//			int nameColumn = cur.getColumnIndex("person");
//			int phoneColumn = cur.getColumnIndex("address");
//			int smsColumn = cur.getColumnIndex("body");
//
//			do {
//
//				name = cur.getString(nameColumn);
//				phoneNum = cur.getString(phoneColumn);
//				sms = cur.getString(smsColumn);
//
//				if (null == sms) {
//					sms = "";
//					isRead = false;
//				}
//
//				count = judge(name, phoneNum, sms);
//				// strBuilder.append(start+",");
//				// strBuilder.append(name+",");
//				// strBuilder.append(phoneNum+",");
//				// strBuilder.append(sms);
//				// strBuilder.append(end+",");
//				strBuilder.append(count);
//
//				if (!count.equalsIgnoreCase("无短信内容")) {
//					Regulate.smsResult.setText(count);
//					float monthHasUse = Float.valueOf(count);
//					sharedData.setMonthMobileHasUseOffloat(monthHasUse);
//					sharedData
//					.setMonthMobileHasUse((long) (monthHasUse * 1024) * 1024);
//					isRead = true;
//					break;
//				} else {
//					isRead = false;
//				}
//
//			} while (cur.moveToNext());
//		} else {
//			isRead = false;
//			strBuilder.append("无短信内容!");
//		}
//
//		return strBuilder;
//
//	}
//
//	public String judge(String name, String phoneNum, String sms) {
//		String num;
//		int start;
//		int end;
//		String count = "无短信内容";
//		//		String regEx ="\\d*";
//		String result = "无短信内容";
//		//		Pattern p = Pattern.compile(regEx);
//
//		num = Regulate.smsNum.getText().toString();
//		if (phoneNum.equalsIgnoreCase(num)) {
//			if(num.equalsIgnoreCase("10086")){
//				String ss = sms;
//				String regex = "[\\d]+[\\.]{1}[\\d]+";
//				Pattern p = Pattern.compile(regex);
//				Matcher m = p.matcher(ss);	
//				String[] a = new String[10];
//				int i =0;
//				String val = null;  
//				try {
//					while (m.find()){  			
//						val = m.group();					
//						Log.v("MATCH: ",val); 	
//						a[i]=val;	
//						i++;
//
//					}
//
//
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//
//				if(val!=null){
//					count = a[0];
//				}
//			}
//
//
//			else if(num.equalsIgnoreCase("10010")){
//				start = sms.indexOf("量");
//				end = sms.indexOf("M");
//				if (start != -1) {
//					isRead = true;
//					count = sms.substring(start + 1, end);
//				}
//			}
//			else if(num.equalsIgnoreCase("10001")){
//				start = sms.lastIndexOf("用");
//				end = sms.indexOf("兆");
//				if (start != -1) {
//					isRead = true;
//					count = sms.substring(start + 1, end);
//				}
//			}
//		}
//
//
//		if(Character.isDigit(count.charAt(0))){
//			result = count;
//		}
//
//
//		//		Log.v("++++++++++", m.replaceAll("").trim()+"");
//
//
//		return result;
//	}
//
}
