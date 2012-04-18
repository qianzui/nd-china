package com.hiapk.sqlhelper;

import com.hiapk.broadcreceiver.AlarmSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperTotal {

	public SQLHelperTotal() {
		super();
		// initTime();
		// TODO Auto-generated constructor stub
	}

	// SQL
	private String SQLname = "SQL.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String SQLTime = "date date,time time";
	private String CreateparamWiFiAnd23G = ",upload INTEGER, download INTEGER,type INTEGER,other varchar(15)";
	public static String TableWiFiOrG23 = "mobile";
	private String TableWiFi = "wifi";
	private String TableMobile = "mobile";
	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String UpdateTable = "UPDATE ";
	private String UpdateSet = " SET ";
	private String Where = " WHERE ";
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND = "' AND ";
	private String Start = " (";
	private String End = ") ";
	private String InsertColumnTotal = "date,time,upload,download,type,other";
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
	private long upload;
	private long download;
	private static final int MODE_PRIVATE = 0;
	// pre
	private final String PREFS_NAME = "allprefs";
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_NOTINIT = "SQLisnotINIT";
	private final String MODE_HASINIT = "SQLhasINIT";
	// classes
	SQLHelperUid SQLhelperuid = new SQLHelperUid();

	/**
	 * �������ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public SQLiteDatabase creatSQL(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * �����ݿ��������ݽ��и���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param table
	 *            ���ݿ�ı�����wifi��mobile��
	 * @param date
	 *            ��¼���ݵ�����
	 * @param time
	 *            ��¼���ݵ�ʱ��
	 * @param upload
	 *            ��¼�ϴ�����
	 * @param download
	 *            ��¼��������
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param typechange
	 *            �ı�typeֵ
	 */
	private void updateSQLtotalType(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "date='" + date + "',time='"
				+ time + "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + Where + "type=" + type;
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
	}

	/**
	 * �����ݿ��������ݽ��и���,�Զ�����ʱ�䣬�����ϴ�����������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param table
	 *            ���ݿ�ı�����wifi��mobile��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param typechange
	 *            �ı�typeֵ2��ֵΪ���ݿ���ʵ�ʼ�¼���ݵ���ֵ
	 */
	public void updateSQLtotalType(SQLiteDatabase mySQL, String table,
			int type, String other, int typechange) {
		initTime();
		initTotalData(table);
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "date='" + date + "',time='"
				+ time + "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + Where + "type=" + type;
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
	}

	/**
	 * �����ݿ��������ݽ���ͳ�ƣ�д��ʱ�䷶Χ�ڵ��ϴ�����������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param table
	 *            ���ݿ�ı�����wifi��mobile��
	 * @param date
	 *            ��¼���ݵ�����
	 * @param time
	 *            ��¼���ݵ�ʱ��
	 * @param upload
	 *            ��¼�ϴ�����
	 * @param download
	 *            ��¼��������
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param daily
	 *            �Ƿ����ǿ������
	 */
	private void statsSQLtotal(SQLiteDatabase mySQL, String table, String date,
			String time, long upload, long download, int type, String other,
			boolean daily) {
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "type=" + 0;
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		long oldup = 0;
		long olddown = 0;
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					// ���֮ǰ���ϴ�����ֵ
					oldup = cur.getLong(minup);
					olddown = cur.getLong(mindown);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("���ݿ�����ʧ��");
			}
		}
		cur.close();
		// ��ʼ��д�����ݣ�wifi�Լ�mobile��
		// ���֮ǰ���ݴ����µ����ݣ������¼���
		if (oldup > upload || olddown > download) {
			oldup = upload;
			olddown = download;
		} else {
			oldup = upload - oldup;
			olddown = download - olddown;
		}
		if (daily) {
			// showLog("�ϴ�����" + oldup + "B" + "  " + "��������" + olddown + "B");
			// ����ʵ�����ݽ������ݿ�
			updateSQLtotalType(mySQL, table, oldup, olddown, 0, other, type);
			// �����µ���������
			updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			exeSQLtotal(mySQL, table, 1, other);
		} else if (olddown != 0 && oldup != 0) {
			// showLog("�ϴ�����" + oldup + "B" + "  " + "��������" + olddown + "B");
			// ����ʵ�����ݽ������ݿ�
			updateSQLtotalType(mySQL, table, oldup, olddown, 0, other, type);
			// �����µ���������
			updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			exeSQLtotal(mySQL, table, 1, other);
		}
	}

	/**
	 * �����ݿ����wifi��mobile���ݵ�д�������ݲ����Ĳ���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param table
	 *            ���ݿ�ı�����wifi��2g/3g��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 */
	private void exeSQLtotal(SQLiteDatabase mySQL, String table, int type,
			String other) {
		// TODO Auto-generated method stub
		String string = null;
		initTotalData(table);
		string = InsertTable + table + Start + InsertColumnTotal + End + Value
				+ date + split + time + split + upload + split + download
				+ split + type + split + other + "'" + End;
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
	 * �ر����ݿ�
	 * 
	 * @param mySQL
	 *            ��ָ�����ݿ���йر�
	 */
	public void closeSQL(SQLiteDatabase mySQL) {
		mySQL.close();
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
		initTablemobileAndwifi(context);
		initTime();
		// ��ʼ�����ݿ�
		boolean initsuccess = true;
		SQLiteDatabase sqldatabase = creatSQL(context);
		sqldatabase.beginTransaction();
		try {
			String string = null;
			string = CreateTable + TableWiFi + Start + SQLId + SQLTime
					+ CreateparamWiFiAnd23G + End;
			// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
			// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
			// varchar(15),other varchar(15))
			try {
				sqldatabase.execSQL(string);
			} catch (Exception e) {
				showLog(string + "fail");
				initsuccess = false;
			}
			// ��ʼ��wifi��type=0��type=1������
			initTotalData(TableWiFi);
			exeSQLtotal(sqldatabase, TableWiFi, 0, null);
			exeSQLtotal(sqldatabase, TableWiFi, 1, null);
			string = CreateTable + TableMobile + Start + SQLId + SQLTime
					+ CreateparamWiFiAnd23G + End;
			// CREATE TABLE IF NOT EXISTS t4 (_id INTEGER PRIMARY KEY,date
			// datetime,upload INTEGER,download INTEGER,uid INTEGER,type
			// varchar(15),other varchar(15))
			try {
				sqldatabase.execSQL(string);
			} catch (Exception e) {
				// TODO: handle exception
				showLog(string);
				showLog("mobiletable-already exist");
				initsuccess = false;
			}
			// ��ʼ��mobile����
			initTotalData(TableMobile);
			// ��ʼ��mobile��type=0��type=1������
			exeSQLtotal(sqldatabase, TableMobile, 0, null);
			exeSQLtotal(sqldatabase, TableMobile, 1, null);
			try {
				// ��ʼ��uid���ݿ��Index��
				if (initsuccess)
					initsuccess = SQLhelperuid.initUidIndexTables(sqldatabase);
				// ������uid=0��
				SQLhelperuid.exeSQLcreateUidIndextables(sqldatabase,
						uidnumbers, packagename);
				// ����ظ���
				uidnumbers = SQLhelperuid.sortUids(uidnumbers);
				// ��ʼ��uid���ݿ�����ʹ�ó�ʼ�����ȫ��uids��
				SQLhelperuid.initUidTables(sqldatabase, uidnumbers);
			} catch (Exception e) {
				// TODO: handle exception
				initsuccess = false;
				showLog("��ʼ��uid���ݿ�ʧ��");
			}
			sqldatabase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��ʼ��ʧ��");
			initsuccess = false;
		} finally {
			sqldatabase.endTransaction();
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
		closeSQL(sqldatabase);
	}

	/**
	 * ���ڳ�ʼ������״̬ȷ����ǰʹ�ú�������
	 * 
	 * @param context
	 */
	public void initTablemobileAndwifi(Context context) {
		ConnectivityManager connec = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connec.getActiveNetworkInfo() != null) {
			NetworkInfo info = connec.getActiveNetworkInfo();
			String typeName = info.getTypeName(); // mobile@wifi
			if (typeName.equals("WIFI"))
				TableWiFiOrG23 = "wifi";
			if (typeName.equals("mobile"))
				TableWiFiOrG23 = "mobile";
			// showLog("���ַ�ʽ����" + typeName);
		} else {
			TableWiFiOrG23 = "";
			// showLog("�޿�������");
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
	 * ��ʼ����������
	 * 
	 * @param table
	 *            wifi����mobile����Ϊ����������
	 */
	private void initTotalData(String table) {
		if (table == "wifi") {
			upload = TrafficStats.getTotalTxBytes()
					- TrafficStats.getMobileTxBytes();
			download = TrafficStats.getTotalRxBytes()
					- TrafficStats.getMobileRxBytes();
			if (upload == 1) {
				upload = 0;
			}
			if (download == 1) {
				download = 0;
			}
		}
		if (table == "mobile") {
			upload = TrafficStats.getMobileTxBytes();
			download = TrafficStats.getMobileRxBytes();
			if (upload == -1) {
				upload = 0;
			}
			if (download == -1) {
				download = 0;
			}
		}
		if (table == "") {
			upload = 0;
			download = 0;
		}
	}

	/**
	 * ��¼wifi��mobile��������
	 * 
	 * @param context
	 * @param daily
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	public void RecordTotalwritestats(Context context, boolean daily) {
		// TODO Auto-generated method stub
		// �Զ��������ݼ�¼---����¼�ϴ�����Ϊ0������
		if (!TableWiFiOrG23.equals("")) {
			SQLiteDatabase sqlDataBase = creatSQL(context);
			initTotalData(TableWiFiOrG23);
			initTime();
			statsSQLtotal(sqlDataBase, TableWiFiOrG23, date, time, upload,
					download, 2, null, daily);
			closeSQL(sqlDataBase);
		}
	}

	/**
	 * ����Wifi��ʷ������ѯ
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] SelectWifiData(Context context, int year, int month) {
		return SelectData(context, year, month, TableWiFi);
	}

	/**
	 * �����ƶ�����������ʷ������ѯ
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] SelectMobileData(Context context, int year, int month) {
		return SelectData(context, year, month, TableMobile);
	}

	/**
	 * ��������������ʷ������ѯ
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param table
	 *            Ҫ��ѯ����������
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	private long[] SelectData(Context context, int year, int month, String table) {
		long[] a = new long[64];
		SQLiteDatabase sqlDataBase = creatSQL(context);
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		// showLog(month2);
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
				+ "31" + AND + "type=" + 2;
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		String newdate = "";
		String countdate = "";
		String dateStr1 = year + "-" + month2 + "-" + "0";
		String dateStr2 = year + "-" + month2 + "-";
		long newup = 0;
		long newdown = 0;
		int i = 1;
		if (cur != null) {
			try {
				int dateIndex = cur.getColumnIndex("date");
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						if (i < 10)
							countdate = dateStr1 + i;
						else
							countdate = dateStr2 + i;
						newdate = cur.getString(dateIndex);
						newup = cur.getLong(uploadIndex);
						newdown = cur.getLong(downloadIndex);
						if (newdate.equals(countdate)) {
							// ��¼ÿ������
							a[i] += newup;
							a[i + 31] += newdown;
						} else {
							// ��¼�ۼ�����
							a[0] += a[i];
							a[63] += a[i + 31];
							while (!newdate.equals(countdate)) {
								// ���ڿ�Խ����
								i++;
								if (i < 10)
									countdate = dateStr1 + i;
								else
									countdate = dateStr2 + i;
								if (i > 31) {
									break;
								}
							}
							// ��¼ÿ������
							a[i] += newup;
							a[i + 31] += newdown;
						}
					} while (cur.moveToNext());
				}
				// ��¼�ۼ�����
				a[0] += a[i];
				a[63] += a[i + 31];
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		closeSQL(sqlDataBase);
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}
}