package com.hiapk.sqlhelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.os.AsyncTask;

public class SQLHelperFireWall {
	// SQL
	private String SQLTotalname = "SQLTotal.db";
	private String SQLUidname = "SQLUid.db";
	private String SQLUidIndex = "SQLUidIndex.db";
	private String SQLUidTotaldata = "SQLTotaldata.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15),states varchar(15)";
	private String CreateparamUidIndex = "uid INTEGER,packagename varchar(255),permission INTEGER,type INTEGER ,other varchar(15)";
	private String CreateparamUidTotal = "uid INTEGER,packagename varchar(255),upload INTEGER,download INTEGER,permission INTEGER ,type INTEGER ,other varchar(15),states varchar(15)";
	private String TableUid = "uid";
	private String TableUidTotal = "uidtotal";
	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String UpdateTable = "UPDATE ";
	private String DeleteTable = "DELETE FROM ";
	private String UpdateSet = " SET ";
	private String Where = " WHERE ";
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND = "' AND ";
	private String Start = " (";
	private String End = ") ";
	private String InsertUidColumnTotal = "date,time,upload,download,type,other";
	private String InsertUidIndexColumnTotal = "uid,packagename,permission";
	private String InsertUidTotalColumn = "uid,packagename,upload,download,permission,type,other,states";
	private String Value = "values('";
	private String split = "','";
	private Cursor cur;
	// date
	private int year;
	private int month;
	private int monthDay;
	private int hour;
	private int minute;
	private int second;
	private String date;
	private String time;
	// data
	private long uidupload;
	private long uiddownload;
	private static final int MODE_PRIVATE = 0;

	/**
	 * ����uidTotal���ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public SQLiteDatabase creatSQLUidTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidTotaldata,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * ����ʱ��ʼ�����������������������߳�������
	 * 
	 * @param context
	 */
	public void InitUidData(Context context) {
		if (SQLStatic.uidnumbers == null) {
			SQLHelperUid sqlhelperUid = new SQLHelperUid();
			SQLStatic.uidnumbers = sqlhelperUid.selectUidnumbers(context);
		}
		if (SQLStatic.isuiddataRecording == false) {
			if (SQLStatic.setSQLUidTotalOnUsed(true)) {
				new AsyncTaskonInitUidData().execute(context);
			} else {
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
		}
	}

	private class AsyncTaskonInitUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			SQLStatic.isuiddataRecording = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			SQLiteDatabase sqlDataBase = creatSQLUidTotal(params[0]);
			sqlDataBase.beginTransaction();
			try {
				SQLStatic.uiddata = SelectUidTotaldownloadAndupload(
						sqlDataBase, SQLStatic.uidnumbers);
				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				showLog("������ȡuid����-init");
			} finally {
				sqlDataBase.endTransaction();
			}
			closeSQL(sqlDataBase);
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			SQLStatic.setSQLUidTotalOnUsed(false);
			SQLStatic.isuiddataRecording = false;

		}

	}

	public void RefreshUidData(Context context) {
		if (SQLStatic.uidnumbers == null) {
			SQLHelperUid sqlhelperUid = new SQLHelperUid();
			SQLStatic.uidnumbers = sqlhelperUid.selectUidnumbers(context);
		}
		if (SQLStatic.isuiddataRecording == false) {
			if (SQLStatic.setSQLUidTotalOnUsed(true)) {
				new AsyncTaskonRefreshUidData().execute(context);
			} else {
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
		}

	}

	private class AsyncTaskonRefreshUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			SQLStatic.isuiddataRecording = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			SQLiteDatabase sqlDataBase = creatSQLUidTotal(params[0]);
			sqlDataBase.beginTransaction();
			try {
				SQLStatic.uiddata = SelectUidTotaldownloadAndupload(
						sqlDataBase, SQLStatic.uidnumbers);
				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				showLog("������ȡuid����-Context");
			} finally {
				sqlDataBase.endTransaction();
			}
			closeSQL(sqlDataBase);
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			SQLStatic.setSQLUidTotalOnUsed(false);
			SQLStatic.isuiddataRecording = false;

		}

	}

	private HashMap<Integer, Data> SelectUidTotaldownloadAndupload(
			SQLiteDatabase sqlDataBase, int[] uidnumber) {
		HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
		for (int i = 0; i < uidnumber.length; i++) {
			long[] get = SelectUidTotalDatabyType(sqlDataBase, uidnumber[i], 2);
			Data temp = new Data();
			temp.upload = get[1];
			temp.download = get[2];
			mp.put(uidnumber[i], temp);
		}

		return mp;
	}

	/**
	 * ��ѯ����uid��֮ǰ����
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param uidnumber
	 *            Ҫ��ѯ��uid
	 * @return ����һ��3λ���顣a[0]Ϊ������a[1]Ϊ�ܼ��ϴ�����a[2]Ϊ�ܼ���������
	 */
	private long[] SelectUidTotalDatabyType(SQLiteDatabase sqlDataBase,
			int uidnumber, int type) {
		long[] a = new long[3];
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidTotal + Where + "type='" + type + AND
				+ "uid=" + uidnumber;
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog("error@" + string);
		}
		long newup = 0;
		long newdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					newup = cur.getLong(uploadIndex);
					newdown = cur.getLong(downloadIndex);
					a[1] = newup;
					a[2] = newdown;
					a[0] = newup + newdown;
					// showLog("a[0]="+a[0]);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * �ر����ݿ�
	 * 
	 * @param mySQL
	 *            ��ָ�����ݿ���йر�
	 */
	public void closeSQL(SQLiteDatabase mySQL) {
		mySQL.close();
	}

	/**
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("databaseUidTotal", string);
	}

	/**
	 * Small structure to hold an application info
	 */
	public static class Data {
		/** linux user id */
		public static long upload;
		/** application names belonging to this user id */
		public static long download;

	}

}
