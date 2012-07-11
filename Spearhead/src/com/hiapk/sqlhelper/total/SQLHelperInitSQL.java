package com.hiapk.sqlhelper.total;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.spearhead.SpearheadActivity;
import com.hiapk.spearhead.Splash;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.pub.SQLStatic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperInitSQL {
	private Context context;

	public SQLHelperInitSQL(Context context) {
		super();
		// initTime();
		this.context = context;
	}

	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String SQLTime = "date date,time time";
	private String CreateparamWiFiAnd23G = ",upload INTEGER, download INTEGER,type INTEGER,other varchar(15)";
	private String InsertUidColumnTotal = "date,time,upload,download,type,other";
	private String TableWiFi = "wifi";
	private String TableMobile = "mobile";
	private String InsertTable = "INSERT INTO ";
	private String Start = " (";
	private String End = ") ";
	private String InsertColumnTotal = "date,time,upload,download,type,other";
	private String Value = "values('";
	private String split = "','";
	// date
	private int year;
	private int month;
	private int monthDay;
	private int hour;
	private int minute;
	private int second;
	private String date;
	private String time;
	// pre
	private final String PREFS_NAME = "allprefs";
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_HASINIT = "SQLhasINIT";

	// classes

	private String CreateparamUidTotal = "uid INTEGER,packagename varchar(255),upload INTEGER,download INTEGER,permission INTEGER ,type INTEGER ,other varchar(15),states varchar(15)";
	private String TableUidTotal = "uidtotal";
	private String InsertUidTotalColumn = "uid,packagename,upload,download,permission,type,other,states";
	// 初始化数据库
	private boolean initTotalsuccess = false;
	private boolean initUidsuccess = false;
	private boolean initUidTotalsuccess = false;
	private int[] uidnumbers_init;
	private String[] packagename_init;
	private long timemillion = 0;

	/**
	 * 对数据库进行wifi，mobile数据的写入新数据操作的操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param table
	 *            数据库的表：有wifi，2g/3g等
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 */
	private void exeSQLtotal(SQLiteDatabase mySQL, String table, int type,
			String other) {
		String string = null;
		long[] totalTraff = SQLHelperDataexe.initTotalData(table);
		string = InsertTable + table + Start + InsertColumnTotal + End + Value
				+ date + split + time + split + totalTraff[0] + split
				+ totalTraff[1] + split + type + split + other + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')

		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			showLog("fail+" + string);
		}
	}

	/**
	 * 提取不重复的uid
	 * 
	 * @param uidnumbers
	 * @return 提取完成的uid集合
	 */
	private int[] sortUids(int[] uidnumbers) {
		int[] newnumber = uidnumbers;
		for (int i = 0; i < newnumber.length; i++) {
			if (newnumber[i] != -1) {
				for (int j = i + 1; j < newnumber.length; j++) {
					if (newnumber[i] == newnumber[j]) {
						newnumber[j] = -1;
					}
				}
			}

		}

		// for (int i = 0; i < newnumber.length; i++) {
		// showLog(newnumber[i] + "");
		// }
		return newnumber;
	}

	/**
	 * 对数据库进行uid数据的写入操作的操作
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param date
	 *            日期
	 * @param time
	 *            时间
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 */
	private void exeSQLcreateUidtable(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, int type, String other) {
		long[] uiddata = SQLHelperDataexe.initUidData(uidnumber);
		String string = null;
		// 表示是否为总流量，总流量初始数据为0
		if (type == 3 || type == 4) {
			string = InsertTable + "uid" + uidnumber + Start
					+ InsertUidColumnTotal + End + Value + date + split + time
					+ split + "0" + split + "0" + split + type + split + other
					+ "'" + End;
		} else {
			string = InsertTable + "uid" + uidnumber + Start
					+ InsertUidColumnTotal + End + Value + date + split + time
					+ split + uiddata[0] + split + uiddata[1] + split + type
					+ split + other + "'" + End;
		}
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			showLog("fail+" + string);
		}
	}

	/**
	 * 初始化uid数据库时建立单表
	 * 
	 * @param sqldatabase
	 *            进行操作的数据库
	 * @param uidnumber
	 *            要建立的uid表
	 */
	private void initUidTable(SQLiteDatabase sqldatabase, int uidnumber) {
		String string = null;
		string = CreateTable + "uid" + uidnumber + Start + SQLId
				+ CreateparamUid + End;
		try {
			sqldatabase.execSQL(string);
		} catch (Exception e) {
			showLog("fail+SQLinit" + string);
		}
	}

	/**
	 * 初始化全部的uid表
	 * 
	 * @param sqldatabase
	 *            进行操作的数据库
	 * @param uidnumbers
	 *            要建立的uid表数组
	 */
	protected void initUidTables(SQLiteDatabase sqldatabase, int[] uidnumbers) {
		initTime();
		try {
			// initUidTable(sqldatabase, 0);
			// exeSQLcreateUidtable(sqldatabase, date, time, 0, 0, null);
			// exeSQLcreateUidtable(sqldatabase, date, time, 0, 1, null);
			for (int uidnumber : uidnumbers) {
				// -1为重复项
				if (uidnumber != -1) {
					initUidTable(sqldatabase, uidnumber);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 0,
							null);
					// exeSQLcreateUidtable(sqldatabase, date, time, uidnumber,
					// 1,
					// null);
					// 3mobile，4wifi
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 3,
							null);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 4,
							null);
				}
			}
		} catch (Exception e) {
			showLog("fail+" + "初始化全部的uid表失败");
		}
	}

	/**
	 * 初始化uidTotal索引表
	 * 
	 * @param sqldatabase
	 * @return
	 */
	protected boolean initUidTotalTables(SQLiteDatabase sqldatabase) {
		String string = null;
		string = CreateTable + TableUidTotal + Start + SQLId
				+ CreateparamUidTotal + End;
		// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
		// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
		// varchar(15),other varchar(15))
		try {
			sqldatabase.execSQL(string);
		} catch (Exception e) {
			showLog("fail+" + string);
			showLog("initUidIndexTables-fail");
			return false;
		}
		return true;
	}

	/**
	 * 初始化uidTotal索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumbers
	 *            数据库的表集合
	 */
	protected void exeSQLcreateUidTotaltables(SQLiteDatabase mySQL,
			int[] uidnumbers, String[] packagenames) {
		for (int i = 0; i < uidnumbers.length; i++) {
			if (uidnumbers[i] != -1) {
				long upload = TrafficStats.getUidTxBytes(uidnumbers[i]);
				if (upload == -1)
					upload = 0;
				long download = TrafficStats.getUidRxBytes(uidnumbers[i]);
				if (download == -1)
					download = 0;
				// 输入初始数据
				// 0为安装软件时系统记录的数据
				// 1为临时变量
				// 2为记录的软件使用流量情况
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], upload, download, 0, "");
//				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
//						packagenames[i], upload, download, 1, "");
//				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
//						packagenames[i], 0, 0, 2, "wifi");
//				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
//						packagenames[i], 0, 0, 2, "mobile");
			}

		}
	}

	/**
	 * 建立uid索引表
	 * 
	 * @param mySQL
	 *            进行写入操作的数据库SQLiteDatagase
	 * @param uidnumber
	 *            数据库的表：uid表
	 * @param packagename
	 *            包名
	 */
	private void exeSQLcreateUidTotaltable(SQLiteDatabase mySQL, int uidnumber,
			String packagename, long upload, long download, int type,
			String other) {
		String string = null;
		string = InsertTable + TableUidTotal + Start + InsertUidTotalColumn
				+ End + Value + uidnumber + split + packagename + split
				+ upload + split + download + split + 0 + split + type + split
				+ other + split + "Install" + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			showLog("fail+" + string);
		}
	}

	/**
	 * 初始化数据库
	 * 
	 * @param context
	 * @param uidnumbers
	 *            uid集合
	 * @param packagename
	 *            uid对应的包名
	 */
	public void initSQL(Context context, int[] uidnumbers, String[] packagename) {
		// 初始化网络状态
		timemillion = System.currentTimeMillis();
		// SQLStatic.initTablemobileAndwifi(context);
		initTime();
		uidnumbers_init = uidnumbers;
		packagename_init = packagename;
		// 清除重复表
		uidnumbers_init = sortUids(uidnumbers_init);
		showLog("uidnumbers_init=" + uidnumbers_init.length
				+ "packagename_init=" + packagename_init.length);
		// for (int i = 0; i < packagename.length; i++) {
		// showLog("uidnumbers[i]=" + uidnumbers[i] + "packagename="
		// + packagename[i] + "");
		// }
		// for (int i = 0; i < uidnumbers.length; i++) {
		// showLog(uidnumbers[i]+"");
		// }
		// uidnumbers_init=new int[uidnumbers.length];
		showLog("startinit" + (System.currentTimeMillis() - timemillion));
		new AsyncTaskinitTotalDB().execute(context);
		new AsyncTaskinitUidDB().execute(context);
		new AsyncTaskinitUidTotalDB().execute(context);

	}

	private class AsyncTaskinitUidTotalDB extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			SQLiteDatabase sqldatabaseUidTotal = SQLHelperCreateClose
					.creatSQLUidTotal(context);
			// uidTotal SQL
			sqldatabaseUidTotal.beginTransaction();
			try {
				// 初始化uid数据库的Total表
				initUidTotalTables(sqldatabaseUidTotal);
				// showLog("建立tableIndex");
				// 不包含uid=0的
				exeSQLcreateUidTotaltables(sqldatabaseUidTotal,
						uidnumbers_init, packagename_init);
				// showLog("初始化tableIndex");
				sqldatabaseUidTotal.setTransactionSuccessful();
			} catch (Exception e) {
				showLog("fail+" + "初始化uidIndex数据表失败");
				return 0;
			} finally {
				// showLog("初始化tableIndex完成");
				sqldatabaseUidTotal.endTransaction();
			}
			SQLHelperCreateClose.closeSQL(sqldatabaseUidTotal);
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				initUidTotalsuccess = true;
			}
			showLog("initTotal=" + initUidTotalsuccess
					+ (System.currentTimeMillis() - timemillion));
			if (initTotalsuccess && initUidsuccess && initUidTotalsuccess) {
				// 确保仅进行一次初始化
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				passfileEditor.putString(PREF_INITSQL, MODE_HASINIT);
				passfileEditor.commit();// 委托，存入数据
				// 开启计时
				AlarmSet alset = new AlarmSet();
				alset.StartAlarm(context);
			}
		}
	}

	private class AsyncTaskinitUidDB extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			SQLiteDatabase sqldatabaseUid = SQLHelperCreateClose
					.creatSQLUid(context);
			sqldatabaseUid.beginTransaction();
			try {
				// 初始化uid数据库这里使用初始化后的全部uids表
				initUidTables(sqldatabaseUid, uidnumbers_init);
				sqldatabaseUid.setTransactionSuccessful();
			} catch (Exception e) {
				showLog("fail+" + "初始化uid数据库失败");
				return 0;
			} finally {
				sqldatabaseUid.endTransaction();
			}
			SQLHelperCreateClose.closeSQL(sqldatabaseUid);
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				initUidsuccess = true;
			}
			showLog("initUid=" + initUidsuccess
					+ (System.currentTimeMillis() - timemillion));
			if (initTotalsuccess && initUidsuccess && initUidTotalsuccess) {
				// 确保仅进行一次初始化
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				passfileEditor.putString(PREF_INITSQL, MODE_HASINIT);
				passfileEditor.commit();// 委托，存入数据
				// 开启计时
				AlarmSet alset = new AlarmSet();
				alset.StartAlarm(context);
			}
		}
	}

	private class AsyncTaskinitTotalDB extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			SQLiteDatabase sqldatabaseTotal = SQLHelperCreateClose
					.creatSQLTotal(params[0]);
			sqldatabaseTotal.beginTransaction();
			try {
				String string = null;
				string = CreateTable + TableWiFi + Start + SQLId + SQLTime
						+ CreateparamWiFiAnd23G + End;
				// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
				// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
				// varchar(15),other varchar(15))
				try {
					sqldatabaseTotal.execSQL(string);
					// showLog("建立tablewifi");
				} catch (Exception e) {
					showLog(string + "fail");
					return 0;
				}
				// 初始化wifi的type=0及type=1的数据
				exeSQLtotal(sqldatabaseTotal, TableWiFi, 0, null);
				// exeSQLtotal(sqldatabaseTotal, TableWiFi, 1, null);
				string = CreateTable + TableMobile + Start + SQLId + SQLTime
						+ CreateparamWiFiAnd23G + End;
				// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
				// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
				// varchar(15),other varchar(15))
				try {
					sqldatabaseTotal.execSQL(string);
					// showLog("建立tablemobile");
				} catch (Exception e) {
					showLog("fail+" + string);
					showLog("mobiletable-already exist");
					return 0;
				}
				// 初始化mobile的type=0及type=1的数据
				exeSQLtotal(sqldatabaseTotal, TableMobile, 0, null);
				// exeSQLtotal(sqldatabaseTotal, TableMobile, 1, null);
				sqldatabaseTotal.setTransactionSuccessful();
			} catch (Exception e) {
				showLog("fail+SQLinit" + "初始化Total失败");
				return 0;
			} finally {
				sqldatabaseTotal.endTransaction();
			}
			SQLHelperCreateClose.closeSQL(sqldatabaseTotal);
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result == 1) {
				initTotalsuccess = true;
			}
			showLog("initUidTotal=" + initTotalsuccess
					+ (System.currentTimeMillis() - timemillion));
			// for (int i = 0; i < SQLStatic.uidnumbers.length; i++) {
			// showLog(SQLStatic.uidnumbers[i] + "");
			// }
			if (initTotalsuccess && initUidsuccess && initUidTotalsuccess) {
				// 确保仅进行一次初始化
				Editor passfileEditor = context.getSharedPreferences(
						PREFS_NAME, 0).edit();
				passfileEditor.putString(PREF_INITSQL, MODE_HASINIT);
				passfileEditor.commit();// 委托，存入数据
				// 开启计时
				AlarmSet alset = new AlarmSet();
				alset.StartAlarm(context);
			}
		}
	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		hour = t.hour; // 0-23
		minute = t.minute;
		second = t.second;
		// SQLname = year + SQLname;
		String month2 = month + "";
		String monthDay2 = monthDay + "";
		String hour2 = hour + "";
		String minute2 = minute + "";
		String second2 = second + "";
		if (month < 10)
			month2 = "0" + month2;
		if (monthDay < 10)
			monthDay2 = "0" + monthDay2;
		if (hour < 10)
			hour2 = "0" + hour2;
		if (minute < 10)
			minute2 = "0" + minute2;
		if (second < 10)
			second2 = "0" + second2;
		date = year + "-" + month2 + "-" + monthDay2;
		time = hour2 + ":" + minute2 + ":" + second2;
		// Table = Table + year;
		// showLog("日期：" + date + "，" + SQLname + "，tableName：" + Table);
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("databaseSQLInit", string);
	}
}
