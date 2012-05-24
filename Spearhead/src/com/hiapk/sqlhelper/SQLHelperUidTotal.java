package com.hiapk.sqlhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;
import android.widget.SlidingDrawer;

public class SQLHelperUidTotal {
	// SQL
	private String SQLTotalname = "SQLTotal.db";
	private String SQLUidname = "SQLUid.db";
	private String SQLUidIndex = "SQLUidIndex.db";
	private String SQLUidTotaldata = "SQLTotaldata.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String CreateparamUidIndex = "uid INTEGER,packagename varchar(255),permission INTEGER,type INTEGER ,other varchar(15)";
	private String CreateparamUidTotal = "uid INTEGER,upload INTEGER,download INTEGER,permission INTEGER ,type INTEGER ,other varchar(15)";
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
	private String InsertUidTotalColumn = "uid,upload,download,permission,type,other";
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
			int[] uidnumbers) {
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
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i], upload,
						download, 0, "");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i], upload,
						download, 1, "");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i], 0, 0, 2, "wifi");
				exeSQLcreateUidTotaltable(mySQL, uidnumbers[i], 0, 0, 2,
						"mobile");
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
			long upload, long download, int type, String other) {
		String string = null;
		string = InsertTable + TableUidTotal + Start + InsertUidTotalColumn
				+ End + Value + uidnumber + split + upload + split + download
				+ split + 0 + split + type + split + other + "'" + End;
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
	 * �����ݿ�uid���ݽ����������£�
	 * 
	 * @param sqlDataBase
	 *            ���в��������ݿ�SQLiteDatagase
	 * @param uidnumbers
	 *            ���ݿ�ı����鼯�ϣ�uid��table���uid�ţ�
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param typechange
	 *            �ı�typeֵ
	 */
	public void updateSQLUidTypes(Context context, int[] uidnumbers,
			String network) {
		// TODO Auto-generated method stub
		SQLHelperTotal.isSQLUidTotalOnUsed = true;
		// String other = SQLHelperTotal.TableWiFiOrG23;
		SQLiteDatabase sqlDataBase = creatSQLUidTotal(context);
		sqlDataBase.beginTransaction();
		try {
			for (int uidnumber : uidnumbers) {
				updateSQLUidTotal(sqlDataBase, uidnumber, network);
			}
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��������uidTotal��������ʧ��");
		} finally {
			sqlDataBase.endTransaction();
			SQLHelperTotal.isSQLUidTotalOnUsed = false;
		}
		closeSQL(sqlDataBase);

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
	private long[] SelectUidTotalData(SQLiteDatabase sqlDataBase,
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
		cur.close();
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
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
	private long[] SelectUidTotalData(SQLiteDatabase sqlDataBase,
			int uidnumber, int type, String other) {
		long[] a = new long[3];
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		string = SelectTable + TableUidTotal + Where + "type='" + type + AND
				+ "uid='" + uidnumber + AND + "other='" + other + "'";
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
		cur.close();
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * �����ݿ�uid���ݽ��и���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 *            ���ݿ�ı�uid��table���uid��
	 */
	private void updateSQLUidTotal(SQLiteDatabase mySQL, int uidnumber,
			String other) {
		// ���֮ǰ��¼����ʱ��������
		long[] beforeData1 = SelectUidTotalData(mySQL, uidnumber, 1);
		// ��ȡ��ǰtrafficststa����
		initUidData(uidnumber);
		// �ж��Ƿ������б仯
		if ((beforeData1[1] == uidupload) && (beforeData1[2] == uiddownload)) {
		} else {
			// ��ȡ֮ǰ��¼������
			long[] beforeData2 = SelectUidTotalData(mySQL, uidnumber, 2, other);
			long newupload;
			long newdownload;
			// �������ӵ�����
			if (uidupload > beforeData1[1]) {
				newupload = uidupload - beforeData1[1];
			} else {
				newupload = uidupload;
			}
			if (uiddownload > beforeData1[2]) {
				newdownload = uiddownload - beforeData1[2];
			} else {
				newdownload = uiddownload;
			}
			// ������������
			updateSQLUidTotalType(mySQL, uidnumber, beforeData2[1] + newupload,
					beforeData2[2] + newdownload, 2, other);
			updateSQLUidTotalType(mySQL, uidnumber, uidupload, uiddownload, 1);
		}

	}

	/**
	 * �����ݿ�uid���ݸ��²���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 * @param upload
	 * @param download
	 */
	private void updateSQLUidTotalType(SQLiteDatabase mySQL, int uidnumber,
			long upload, long download, int type, String other) {
		String string = null;
		string = UpdateTable + TableUidTotal + UpdateSet + "upload='" + upload
				+ "',download='" + download + "'" + Where + "type=" + type
				+ " AND " + "other='" + other + "'" + " AND " + "uid="
				+ uidnumber;
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
	 * �����ݿ�uid���ݸ��²���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 * @param upload
	 * @param download
	 */
	private void updateSQLUidTotalType(SQLiteDatabase mySQL, int uidnumber,
			long upload, long download, int type) {
		String string = null;
		string = UpdateTable + TableUidTotal + UpdateSet + "upload='" + upload
				+ "',download='" + download + "'" + Where + "type=" + type
				+ " AND " + "uid=" + uidnumber;
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
	 * ��ʼ����������
	 * 
	 * @param uidnumber
	 *            ����uid��ȡ��������
	 */
	private void initUidData(int uidnumber) {
		uidupload = TrafficStats.getUidTxBytes(uidnumber);
		uiddownload = TrafficStats.getUidRxBytes(uidnumber);
		if (uidupload == -1) {
			uidupload = 0;
		}
		if (uiddownload == -1) {
			uiddownload = 0;
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
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}

}
