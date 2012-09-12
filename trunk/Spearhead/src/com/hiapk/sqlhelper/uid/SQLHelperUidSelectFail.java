package com.hiapk.sqlhelper.uid;

import com.hiapk.bean.UidTraffs;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;

import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

/**
 * ��ȡuid��Ϣʧ�ܵ�ʱ�򲹳佨��uid��
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
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 0, null);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 3, null);
		exeSQLcreateUidtable(sqlDataBase, date, time, uid, 4, null);
	}

	/**
	 * �����ݿ����uid���ݵ�д������Ĳ���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param date
	 *            ����
	 * @param time
	 *            ʱ��
	 * @param uidnumber
	 *            ���ݿ�ı�uid��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 */
	private void exeSQLcreateUidtable(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, int type, String other) {
		UidTraffs uiddata = SQLHelperDataexe.initUidData(uidnumber);
		StringBuilder string = new StringBuilder();
		// ��ʾ�Ƿ�Ϊ����������������ʼ����Ϊ0
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
	 * ��ʼ��ϵͳʱ��
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // ȡ��ϵͳʱ�䡣
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
		// showLog("���ڣ�" + date + "��" + SQLname + "��tableName��" + Table);
	}

}
