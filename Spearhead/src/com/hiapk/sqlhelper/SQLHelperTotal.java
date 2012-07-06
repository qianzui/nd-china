package com.hiapk.sqlhelper;

import com.hiapk.prefrencesetting.SharedPrefrenceData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;

public class SQLHelperTotal {

	public SQLHelperTotal() {
		super();
		// initTime();
		// TODO Auto-generated constructor stub
	}

	// SQL
	private String SQLTotalname = "SQLTotal.db";
	private String SQLUidname = "SQLUid.db";
	private String SQLUidIndex = "SQLUidIndex.db";
	private String SQLUidTotaldata = "SQLTotaldata.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String SQLTime = "date date,time time";
	private String CreateparamWiFiAnd23G = ",upload INTEGER, download INTEGER,type INTEGER,other varchar(15)";
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
	private String dateDelete;
	private String time;
	// data
	private long upload;
	private long download;
	private static final int MODE_PRIVATE = 0;
	// classes
	SQLHelperUid SQLhelperuid = new SQLHelperUid();
	SQLHelperUidTotal SQLhelperuidTotal = new SQLHelperUidTotal();

	/**
	 * ���������ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public SQLiteDatabase creatSQLTotal(Context context) {
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
	public SQLiteDatabase creatSQLUid(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidname,
				MODE_PRIVATE, null);
		// showLog("db-CreatComplete");
		return mySQL;
	}

	/**
	 * ����uidIndex���ݿ�
	 * 
	 * @param context
	 * @return ���ش��������ݿ�
	 */
	public SQLiteDatabase creatSQLUidIndex(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidIndex,
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
	public SQLiteDatabase creatSQLUidTotal(Context context) {
		SQLiteDatabase mySQL = context.openOrCreateDatabase(SQLUidTotaldata,
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
	 *            ���ݿ�ı���wifi��mobile��
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
	 * �����ݿ��������ݽ��и���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param table
	 *            ���ݿ�ı���wifi��mobile��
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
	private void updateSQLtotalTypeDate(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "time='" + time
				+ "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + Where + "date='" + date + AND
				+ "type=" + type;
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

	private void updateSQLtotalTypeDate0to3(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other, int typechange) {
		initTime();
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + table + UpdateSet + "time='" + time
				+ "',upload='" + upload + "',download='" + download
				+ "' ,type=" + typechange + " ,date='" + date + "' " + Where
				+ "type=" + type;
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
	 *            ���ݿ�ı���wifi��mobile��
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
	 *            ���ݿ�ı���wifi��mobile��
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
	 *            �Ƿ����ǿ�����
	 */
	private void statsSQLtotal(Context context, SQLiteDatabase mySQL,
			String table, String date, String time, long upload, long download,
			int type, String other, boolean daily) {
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
		long oldup0 = -100;
		long olddown0 = -100;

		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					// ���֮ǰ���ϴ�����ֵ
					oldup0 = cur.getLong(minup);
					olddown0 = cur.getLong(mindown);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("���ݿ�����ʧ��");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		showLog("oldup0=" + oldup0 + "olddown0=" + olddown0);
		if (cur != null) {
			cur.close();
		}
		if (oldup0 != -100) {
			cur = null;
			// ��ʼ��д�����ݣ�wifi�Լ�mobile��
			// ���֮ǰ���ݴ����µ����ݣ������¼���
			if ((oldup0 > upload) || (olddown0 > download)) {
				oldup0 = upload;
				olddown0 = download;

			} else {
				oldup0 = upload - oldup0;
				olddown0 = download - olddown0;
			}
			showLog("oldup0 up=" + oldup0 + "olddown0 down=" + olddown0);
			if ((olddown0 != 0 || oldup0 != 0)
					&& ((olddown0 > 512) || (oldup0 > 512))) {

				string = SelectTable + table + Where + "date='" + date + AND
						+ "type=" + 3;
				try {
					cur = mySQL.rawQuery(string, null);
				} catch (Exception e) {
					// TODO: handle exception
					showLog(string);
				}
				long oldup3 = 0;
				long olddown3 = 0;
				String olddate3 = "";
				// ������� ����+
				// showLog("cur.move" + cur.moveToFirst());
				if (cur.moveToFirst()) {
					showLog("���е�������");
					try {
						int minup = cur.getColumnIndex("upload");
						int mindown = cur.getColumnIndex("download");
						int dateIndex = cur.getColumnIndex("date");
						// showLog(cur.getColumnIndex("minute") + "");
						if (cur.moveToFirst()) {
							// ���֮ǰ���ϴ�����ֵ
							oldup3 = cur.getLong(minup);
							olddown3 = cur.getLong(mindown);
							olddate3 = cur.getString(dateIndex);
						}
					} catch (Exception e) {
						// TODO: handle exception
						showLog("cur-searchfail");
						oldup3 = 0;
						olddown3 = 0;
						olddate3 = "";
					}
					// showLog(oldup2+"olddown2="+olddown2+olddate2);
					// 3Ϊ��ͳ������
					if (olddate3 != "") {

						updateSQLtotalTypeDate(mySQL, table, oldup3 + oldup0,
								olddown3 + olddown0, 3, other, 3);
						updateSQLtotalType(mySQL, table, upload, download, 0,
								other, 0);
						// ʱ�̶��ڵ�����
						// exeSQLtotalSetData(mySQL, table, oldup0, olddown0, 2,
						// other);
						if (table == "mobile") {
							SharedPrefrenceData sharedData = new SharedPrefrenceData(
									context);
							long beforemobile = sharedData
									.getMonthHasUsedStack();
							if (beforemobile != -100) {
								beforemobile = beforemobile + oldup0 + olddown0;
								sharedData.setMonthHasUsedStack(beforemobile);
							}
						}

					}
					// �������add
				} else {
					showLog("�޵�������");
					updateSQLtotalTypeDate0to3(mySQL, table, oldup0, olddown0,
							0, other, 3);
					exeSQLtotalSetData(mySQL, table, upload, download, 0, other);
					// updateSQLtotalType(mySQL, table, upload, download, 0,
					// other, 0);
					// ʱ�̶��ڵ�����
					// exeSQLtotalSetData(mySQL, table, oldup0, olddown0, 2,
					// other);
					SharedPrefrenceData sharedData = new SharedPrefrenceData(
							context);
					if (table == "mobile") {
						long beforemobile = sharedData.getMonthHasUsedStack();
						if (beforemobile != -100) {

							beforemobile = beforemobile + oldup0 + olddown0;
							sharedData.setMonthHasUsedStack(beforemobile);
						}
					}
				}
				if (cur != null) {
					cur.close();
				}
			}
			// if (daily) {
			// // showLog("�ϴ�����" + oldup + "B" + "  " + "��������" + olddown + "B");
			// // ����ʵ�����ݽ������ݿ�
			// updateSQLtotalType(mySQL, table, oldup0, olddown0, 0, other,
			// type);
			// // ����µ���������
			// updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			// exeSQLtotal(mySQL, table, 1, other);
			// } else if ((olddown0 != 0 || oldup0 != 0)
			// && ((olddown0 > 1024) || (oldup0 > 1024))) {
			// // showLog("�ϴ�����" + oldup + "B" + "  " + "��������" + olddown + "B");
			// // ����ʵ�����ݽ������ݿ�
			// updateSQLtotalType(mySQL, table, oldup0, olddown0, 0, other,
			// type);
			// // ����µ���������
			// updateSQLtotalType(mySQL, table, upload, download, 1, other, 0);
			// exeSQLtotal(mySQL, table, 1, other);
			// }
		}
	}

	private void exeSQLtotalSetData(SQLiteDatabase mySQL, String table,
			long upload, long download, int type, String other) {
		// TODO Auto-generated method stub
		String string = null;
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
	public void RecordTotalwritestats(Context context,
			SQLiteDatabase sqlDataBase, boolean daily, String network) {
		// TODO Auto-generated method stub
		// �Զ��������ݼ�¼---����¼�ϴ�����Ϊ0������
		if (!network.equals("")) {
			// SQLiteDatabase sqlDataBase = creatSQLTotal(context);
			initTotalData(network);
			initTime();
			showLog("upload=" + upload + "download=" + download);
			statsSQLtotal(context, sqlDataBase, network, date, time, upload,
					download, 2, null, daily);
			// closeSQL(sqlDataBase);
		}
	}

	/**
	 * ��¼wifi��mobile��������
	 * 
	 * @param context
	 * @param daily
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	public void RecordTotalwritestats(Context context, boolean daily,
			String network) {
		// TODO Auto-generated method stub
		// �Զ��������ݼ�¼---����¼�ϴ�����Ϊ0������
		if (!network.equals("")) {
			SQLiteDatabase sqlDataBase = creatSQLTotal(context);
			initTotalData(network);
			initTime();
			statsSQLtotal(context, sqlDataBase, network, date, time, upload,
					download, 2, null, daily);
			closeSQL(sqlDataBase);
		}
	}

	/**
	 * ����Wifi��ʷ������ѯ
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] SelectWifiData(SQLiteDatabase sqlDataBase, int year, int month) {
		return SelectData(sqlDataBase, year, month, TableWiFi);
	}

	/**
	 * �����ƶ�����������ʷ������ѯ
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	// specialfortext TableWiFi- TableMobile
	public long[] SelectMobileData(SQLiteDatabase sqlDataBase, int year,
			int month) {
		return SelectData(sqlDataBase, year, month, TableMobile);
	}

	/**
	 * �����ƶ�����������ʷ������ѯ��ĳһ�쵽��һ�趨��
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param day
	 *            �����ѯ���ݿ�ʼ������.
	 * @param setday
	 *            �����ѯ���ݽ���������.��ͬ�µĻ����ֻ����ȣ�Ȼ�󷵻ص������ݣ�
	 * @param table
	 *            Ҫ��ѯ����������
	 * @return ����һ��3λ���顣a[0]Ϊ�ܼ�����a[1]�ܼ��ϴ�����a[2]�ܼ���������
	 */
	// specialfortext TableWiFi----TableMobile
	public long[] SelectMobileData(SQLiteDatabase sqlDataBase, int year,
			int month, int day, int dayset) {
		return SelectData(sqlDataBase, year, month, day, dayset, TableMobile);
	}

	/**
	 * �����ƶ������������趨ʱ��㵽��ǰ��������ѯ
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param day
	 *            �����ѯ������.
	 * @param time
	 *            �����ѯ��ʱ��.
	 * @return ����һ��3λ���顣a[0]Ϊ�ܼ�����a[1]Ϊ�ϴ�����a[2]��������
	 * 
	 */
	// specialfortext TableMobile----------TableWiFi
	public long[] SelectMobileData(SQLiteDatabase sqlDataBase, int year,
			int month, int day, String time) {

		return SelectData(sqlDataBase, year, month, day, time, TableMobile);
	}

	/**
	 * ��������������ʷ������ѯ
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param table
	 *            Ҫ��ѯ����������
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	private long[] SelectData(SQLiteDatabase sqlDataBase, int year, int month,
			String table) {
		long[] a = new long[64];
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		// showLog(month2);
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
				+ "31" + AND + "type=" + 3;
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
									// �������˳����ȷ�����лָ�
									for (int j = 1; j < 32; j++) {
										if (j < 10)
											countdate = dateStr1 + j;
										else
											countdate = dateStr2 + j;
										if (newdate.equals(countdate)) {
											i = j;
											break;
										}
										;
									}
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
				showLog("datacur-searchfail");
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
	 * ��ʽ����������
	 * 
	 * @param input
	 *            ��������ڵ�int
	 * @return ���ظ�ʽ�����String������
	 */
	private String formateMonthAndDay(int input) {
		if (input < 10) {
			String data2 = "0" + input;
			return data2;
		} else {
			return input + "";
		}

	}

	/**
	 * ��������������ʷ�ۼƷ��ص��ǵ�setday֮ǰ����������ݼ�¼
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param day
	 *            �����ѯ���ݿ�ʼ������.
	 * @param setday
	 *            �����ѯ���ݽ���������.��ͬ�µĻ���Ȼ�󷵻���������day��setday-1��
	 * @param table
	 *            Ҫ��ѯ����������
	 * @return ����һ��3λ���顣a[0]Ϊ�ܼ�����a[1]�ܼ��ϴ�����a[2]�ܼ���������
	 */
	private long[] SelectData(SQLiteDatabase sqlDataBase, int year, int month,
			int day, int setday, String table) {
		long[] a = new long[3];
		String month2 = formateMonthAndDay(month);
		String day2 = formateMonthAndDay(day);
		String setday2 = formateMonthAndDay(setday - 1);
		// showLog(month2);
		String string = null;
		int year2 = year + 1;
		String month3 = formateMonthAndDay(month + 1);
		if (day == setday) {
			if (month != 12) {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year + "-"
						+ month3 + "-" + setday2 + AND + "type=" + 3;
			} else {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year2 + "-"
						+ "01" + "-" + setday2 + AND + "type=" + 3;
			}
		} else if (setday > day) {
			string = SelectTable + table + Where + "date" + Between + year
					+ "-" + month2 + "-" + day2 + AND_B + year + "-" + month2
					+ "-" + setday2 + AND + "type=" + 3;
		} else {
			// ���п����ж�
			if (month != 12) {

				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year + "-"
						+ month3 + "-" + setday2 + AND + "type=" + 3;
			} else {
				string = SelectTable + table + Where + "date" + Between + year
						+ "-" + month2 + "-" + day2 + AND_B + year2 + "-"
						+ "01" + "-" + setday2 + AND + "type=" + 3;
			}
		}
		// showLog("testmonthUsetraff" + string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		long countup = 0;
		long countdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						countup += cur.getLong(uploadIndex);
						countdown += cur.getLong(downloadIndex);
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = countup + countdown;
		a[1] = countup;
		a[2] = countdown;
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * ���������ʱ�䵽���������ĳ����������--δ������
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param day
	 *            �����ѯ������.
	 * @param time
	 *            �����ѯʱ��.
	 * @param table
	 *            Ҫ��ѯ����������
	 * @return ����һ��3λ���顣a[0]Ϊ�ܼ�����a[1]Ϊ�ϴ�����a[2]��������
	 */
	private long[] SelectData(SQLiteDatabase sqlDataBase, int year, int month,
			int day, String time, String table) {
		long[] a = new long[3];
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		String day2 = day + "";
		if (day < 10)
			day2 = "0" + day2;
		// showLog(month2);
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + table + Where + "time" + Between + time + AND_B
				+ "23:59:59" + AND + "type=" + 2 + " AND " + "date=" + "'"
				+ year + "-" + month2 + "-" + day2 + "'";
		// showLog(string);
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		long countup = 0;
		long countdown = 0;
		if (cur != null) {
			try {
				int uploadIndex = cur.getColumnIndex("upload");
				int downloadIndex = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						countup += cur.getLong(uploadIndex);
						countdown += cur.getLong(downloadIndex);
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = countdown + countup;
		a[1] = countup;
		a[2] = countdown;
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * �����һ���汾��������type=2����
	 * 
	 * @param mySQL
	 */
	public void autoClearData(SQLiteDatabase mySQL) {
		// �����Զ���������
		initTime();
		if (hour == 3 && minute == 10) {
			String string = null;
			// delete from Yookey where tit not in (select min(tit) from
			// Yookey
			// group by SID)
			string = "DELETE   FROM " + TableMobile + Where + "type=" + 2;
			try {
				mySQL.execSQL(string);
			} catch (Exception e) {
				showLog(string + "fail");
			}
			string = "DELETE   FROM " + TableWiFi + Where + "type=" + 2;
			try {
				mySQL.execSQL(string);
			} catch (Exception e) {
				showLog(string + "fail");
			}
		}
	}

	/**
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// Log.d("databaseTotal", string);
	}
}
