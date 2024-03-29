package com.hiapk.sqlhelper.uid;

import com.hiapk.bean.UidTraffs;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;

import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

/**
 * 获取uid信息失败等时候补充建立uid表
 * 
 * @author Administrator
 * 
 */
public class SQLHelperUidSelectFail {
	private String TAG = "UidSelectFail";

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
		StringBuilder string = new StringBuilder();
		string = string.append(CreateTable).append(table).append(Start)
				.append(SQLId).append(CreateparamUid).append(End);
		try {
			sqlDataBase.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
		}
		initTime();
		UidTraffs uiddata = new UidTraffs();
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 0, null, uiddata);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 3, null, uiddata);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 4, null, uiddata);
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
			String time, int uidnumber, int type, String other,
			UidTraffs uiddata) {
		uiddata = SQLHelperDataexe.initUidData(uidnumber, uiddata);
		StringBuilder string = new StringBuilder();
		// 表示是否为总流量，总流量初始数据为0
		if (type == 3 || type == 4) {
			string = string.append(InsertTable).append("uid").append(uidnumber)
					.append(Start).append(InsertUidColumnTotal).append(End)
					.append(Value).append(date).append(split).append(time)
					.append(split).append("0").append(split).append("0")
					.append(split).append(type).append(split).append(other)
					.append("'").append(End);
		} else {
			string = string.append(InsertTable).append("uid").append(uidnumber)
					.append(Start).append(InsertUidColumnTotal).append(End)
					.append(Value).append(date).append(split).append(time)
					.append(split).append(uiddata.getUpload()).append(split)
					.append(uiddata.getDownload()).append(split).append(type)
					.append(split).append(other).append("'").append(End);
		}
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail" + e);
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

}
