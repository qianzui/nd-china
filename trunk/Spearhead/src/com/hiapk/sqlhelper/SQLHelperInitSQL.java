package com.hiapk.sqlhelper;

import com.hiapk.broadcreceiver.AlarmSet;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperInitSQL {

	public SQLHelperInitSQL() {
		super();
		// initTime();
		// TODO Auto-generated constructor stub
	}

	// SQL
	private String SQLTotalname = "SQLTotal.db";
	private String SQLUidname = "SQLUid.db";
	private String SQLUidTotaldata = "SQLTotaldata.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String SQLTime = "date date,time time";
	private String CreateparamWiFiAnd23G = ",upload INTEGER, download INTEGER,type INTEGER,other varchar(15)";
	public static String TableWiFiOrG23 = "mobile";
	// public static String UidWiFiOrG23 = "";
	public static String TotalWiFiOrG23 = "";
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
	private static final int MODE_PRIVATE = 0;
	// pre
	private final String PREFS_NAME = "allprefs";
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_NOTINIT = "SQLisnotINIT";
	private final String MODE_HASINIT = "SQLhasINIT";

	// classes

	private String CreateparamUidTotal = "uid INTEGER,packagename varchar(255),upload INTEGER,download INTEGER,permission INTEGER ,type INTEGER ,other varchar(15),states varchar(15)";
	private String TableUidTotal = "uidtotal";
	private String InsertUidTotalColumn = "uid,packagename,upload,download,permission,type,other,states";

	/**
	 * ���������ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	private SQLiteDatabase creatSQLTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLTotalname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * ����uid���ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	private SQLiteDatabase creatSQLUid(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * ����uidTotal���ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	private SQLiteDatabase creatSQLUidTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidTotaldata,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * �����ݿ����wifi��mobile���ݵ�д�������ݲ����Ĳ���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param table
	 *            ���ݿ�ı���wifi��2g/3g��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 */
	private void exeSQLtotal(SQLiteDatabase mySQL, String table, int type,
			String other) {
		// TODO Auto-generated method stub
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
			// TODO: handle exception
			showLog(string);
		}
	}

	/**
	 * ��ȡ���ظ���uid
	 * 
	 * @param uidnumbers
	 * @return ��ȡ��ɵ�uid����
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
		return newnumber;
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
		long[] uiddata = SQLHelperDataexe.initUidData(uidnumber);
		String string = null;
		// ��ʾ�Ƿ�Ϊ����������������ʼ����Ϊ0
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
			// TODO: handle exception
			showLog(string);
		}
	}

	/**
	 * ��ʼ��uid���ݿ�ʱ��������
	 * 
	 * @param sqldatabase
	 *            ���в��������ݿ�
	 * @param uidnumber
	 *            Ҫ������uid��
	 */
	private void initUidTable(SQLiteDatabase sqldatabase, int uidnumber) {
		String string = null;
		string = CreateTable + "uid" + uidnumber + Start + SQLId
				+ CreateparamUid + End;
		try {
			sqldatabase.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
	}

	/**
	 * ��ʼ��ȫ����uid��
	 * 
	 * @param sqldatabase
	 *            ���в��������ݿ�
	 * @param uidnumbers
	 *            Ҫ������uid������
	 */
	protected void initUidTables(SQLiteDatabase sqldatabase, int[] uidnumbers) {
		initTime();
		try {
			// initUidTable(sqldatabase, 0);
			// exeSQLcreateUidtable(sqldatabase, date, time, 0, 0, null);
			// exeSQLcreateUidtable(sqldatabase, date, time, 0, 1, null);
			for (int uidnumber : uidnumbers) {
				// -1Ϊ�ظ���
				if (uidnumber != -1) {
					initUidTable(sqldatabase, uidnumber);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 0,
							null);
					// exeSQLcreateUidtable(sqldatabase, date, time, uidnumber,
					// 1,
					// null);
					// 3mobile��4wifi
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 3,
							null);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 4,
							null);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��ʼ��ȫ����uid��ʧ��");
		}
	}

	/**
	 * ��ʼ��uidTotal������
	 * 
	 * @param sqldatabase
	 * @return
	 */
	protected boolean initUidTotalTables(SQLiteDatabase sqldatabase) {
		// TODO Auto-generated method stub
		String string = null;
		string = CreateTable + TableUidTotal + Start + SQLId
				+ CreateparamUidTotal + End;
		// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
		// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
		// varchar(15),other varchar(15))
		try {
			sqldatabase.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
			showLog("initUidIndexTables-fail");
			return false;
		}
		return true;
	}

	/**
	 * ��ʼ��uidTotal������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumbers
	 *            ���ݿ�ı���
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
				// �����ʼ����
				// 0Ϊ��װ���ʱϵͳ��¼������
				// 1Ϊ��ʱ����
				// 2Ϊ��¼�����ʹ���������
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], upload, download, 0, "");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], upload, download, 1, "");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], 0, 0, 2, "wifi");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i],
						packagenames[i], 0, 0, 2, "mobile");
			}

		}
	}

	/**
	 * ����uid������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 *            ���ݿ�ı�uid��
	 * @param packagename
	 *            ����
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
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * ��ʼ�����ݿ�
	 * 
	 * @param context
	 * @param uidnumbers
	 *            uid����
	 * @param packagename
	 *            uid��Ӧ�İ���
	 */
	public void initSQL(Context context, int[] uidnumbers, String[] packagename) {
		// ��ʼ������״̬
		SQLStatic.initTablemobileAndwifi(context, true);
		initTime();
		// ��ʼ�����ݿ�
		boolean initsuccess = true;
		SQLiteDatabase sqldatabaseTotal = creatSQLTotal(context);
		SQLiteDatabase sqldatabaseUid = creatSQLUid(context);
		SQLiteDatabase sqldatabaseUidTotal = creatSQLUidTotal(context);
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
				// showLog("����tablewifi");
			} catch (Exception e) {
				showLog(string + "fail");
				initsuccess = false;
			}
			// ��ʼ��wifi��type=0��type=1������
			exeSQLtotal(sqldatabaseTotal, TableWiFi, 0, null);
			exeSQLtotal(sqldatabaseTotal, TableWiFi, 1, null);
			string = CreateTable + TableMobile + Start + SQLId + SQLTime
					+ CreateparamWiFiAnd23G + End;
			// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
			// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
			// varchar(15),other varchar(15))
			try {
				sqldatabaseTotal.execSQL(string);
				// showLog("����tablemobile");
			} catch (Exception e) {
				// TODO: handle exception
				showLog(string);
				showLog("mobiletable-already exist");
				initsuccess = false;
			}
			// ��ʼ��mobile��type=0��type=1������
			exeSQLtotal(sqldatabaseTotal, TableMobile, 0, null);
			exeSQLtotal(sqldatabaseTotal, TableMobile, 1, null);
			sqldatabaseTotal.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��ʼ��Totalʧ��");
			initsuccess = false;
		} finally {
			sqldatabaseTotal.endTransaction();
		}

		sqldatabaseUid.beginTransaction();
		try {
			if (initsuccess) {
				// ����ظ���
				uidnumbers = sortUids(uidnumbers);
				// ��ʼ��uid���ݿ�����ʹ�ó�ʼ�����ȫ��uids��
				initUidTables(sqldatabaseUid, uidnumbers);
			}
			sqldatabaseUid.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			initsuccess = false;
			showLog("��ʼ��uid���ݿ�ʧ��");
		} finally {
			sqldatabaseUid.endTransaction();
		}

		// uidTotal SQL
		sqldatabaseUidTotal.beginTransaction();
		try {
			// ��ʼ��uid���ݿ��Total��
			if (initsuccess) {
				initsuccess = initUidTotalTables(sqldatabaseUidTotal);
				// showLog("����tableIndex");
				// ������uid=0��
				exeSQLcreateUidTotaltables(sqldatabaseUidTotal, uidnumbers,
						packagename);
				// showLog("��ʼ��tableIndex");
			}
			sqldatabaseUidTotal.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			initsuccess = false;
			showLog("��ʼ��uidIndex���ݱ�ʧ��");
		} finally {
			// showLog("��ʼ��tableIndex���");
			sqldatabaseUidTotal.endTransaction();
		}

		if (initsuccess) {
			// ȷ��������һ�γ�ʼ��
			Editor passfileEditor = context.getSharedPreferences(PREFS_NAME, 0)
					.edit();
			passfileEditor.putString(PREF_INITSQL, MODE_HASINIT);
			passfileEditor.commit();// ί�У���������
			// ������ʱ
			AlarmSet alset = new AlarmSet();
			alset.StartAlarm(context);
		}
		closeSQL(sqldatabaseTotal);
		closeSQL(sqldatabaseUid);
		closeSQL(sqldatabaseUidTotal);
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

	/**
	 * �ⲿ�����ȡϵͳʱ��
	 * 
	 * @return
	 */
	public String gettime() {
		initTime();
		return time;
	}

	/**
	 * ����IsInit������¼ͬ��
	 * 
	 * @param context
	 */
	public boolean getIsInit(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getString(PREF_INITSQL, MODE_NOTINIT).endsWith(
				MODE_HASINIT);
	}

	/**
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("databaseTotal", string);
	}
}
