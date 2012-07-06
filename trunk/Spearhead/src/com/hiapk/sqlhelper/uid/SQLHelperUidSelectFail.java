package com.hiapk.sqlhelper.uid;

import com.hiapk.sqlhelper.pub.SQLHelperDataexe;

import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

/**
 * 获取uid信息失败等时候补充建立uid表
 * 
 * @author Administrator
 * 
 */
public class SQLHelperUidSelectFail {

	public SQLHelperUidSelectFail() {
		super();
		// initTime();
	}

	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String InsertTable = "INSERT INTO ";
	private String Start = " (";
	private String End = ") ";
	private String InsertUidColumnTotal = "date,time,upload,download,type,other";
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

	public void selectfails(SQLiteDatabase sqlDataBase, String table, int uid) {
		String string = null;
		string = CreateTable + table + Start + SQLId + CreateparamUid + End;
		try {
			sqlDataBase.execSQL(string);
		} catch (Exception e) {
			showLog("fail" + string);
		}
		initTime();
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 0, null);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 3, null);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 4, null);
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
			showLog("fail" + string);
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
		Log.d("UidSelectFail", string);
	}
}
