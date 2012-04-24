package com.hiapk.sqlhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperUid {

	public SQLHelperUid() {
		super();
		// initTime();
		// TODO Auto-generated constructor stub
	}

	// SQL
	private String SQLname = "SQL.db";
	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER INTEGER,other varchar(15)";
	private String CreateparamUidIndex = "uid INTEGER,packagename varchar(255),permission,other varchar(15)";
	private String TableUid = "uid";
	private String TableUidIndex = "uidindex";
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
	 * �ڰ�װ�³���ʱ����uidIndexĿ¼
	 * 
	 * @param context
	 * @param uidnumber
	 *            �����ĳ���uid��
	 * @param packageName
	 *            �����ĳ������
	 * @param other
	 *            ����״̬
	 */
	public void updateSQLUidIndexOnInstall(Context context, int uidnumber,
			String packageName, String other) {
		SQLiteDatabase mySQL = creatSQL(context);
		mySQL.beginTransaction();
		try {
			exeSQLUidIndextable(mySQL, uidnumber, packageName, other);
			// �ж��Ƿ񸲸ǰ�װ��
			// showLog(isCoveringInstall(mySQL, packageName) + "" + packageName
			// + uidnumber);
			if (isCoveringInstall(mySQL, packageName)) {
				// showLog("���ǰ�װ" + packageName + uidnumber);
				updateSQLUidIndexOther(mySQL, packageName, uidnumber, other);
				sortSQLUidIndex(mySQL);
				delSQLUidIndexAndTable(mySQL);

			} else {
				// showLog("�°�װ���" + packageName + uidnumber);
				delSQLUidIndexAndTable(mySQL);
				initTime();
				initUidTable(mySQL, uidnumber);
				exeSQLcreateUidtable(mySQL, date, time, uidnumber, 0, null);
				exeSQLcreateUidtable(mySQL, date, time, uidnumber, 1, null);
			}

			mySQL.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("����������ʧ��");
		} finally {
			mySQL.endTransaction();
		}
		closeSQL(mySQL);
	}

	/**
	 * ��װ�����ʱ���ݰ��������ݿ�uidIndex�Լ�other���и���
	 * 
	 * @param mySQL
	 * @param packagename
	 *            ����
	 * @param uidnumber
	 *            ����uid
	 * @param other
	 *            ����״̬��¼
	 */
	private void updateSQLUidIndexOther(SQLiteDatabase mySQL,
			String packagename, int uidnumber, String other) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + TableUidIndex + UpdateSet + "other='" + other
				+ "', uid=" + uidnumber + Where + "packagename='" + packagename
				+ "'";
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
	 * ж�س���ʱ�����ݿ�uidIndex����и���
	 * 
	 * @param mySQL
	 * @param packagename
	 *            ����
	 * @param other
	 *            ����״̬��¼
	 */
	public void updateSQLUidIndexOtherOnUnInstall(SQLiteDatabase mySQL,
			String packagename, String other) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + TableUidIndex + UpdateSet + "other='" + other
				+ "'" + Where + "packagename='" + packagename + "'";
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
	 * �����ݿ�uid���ݽ��и���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param date
	 *            ��¼�����ݿ������
	 * @param time
	 *            ��¼�����ݿ��ʱ��
	 * @param uidnumber
	 *            ���ݿ�ı�uid��table���uid��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param typechange
	 *            �ı�typeֵ
	 */
	private void updateSQLUidType(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, int type, String other, int typechange) {
		initUidData(uidnumber);
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "date='" + date
				+ "',time='" + time + "',upload='" + uidupload + "',download='"
				+ uiddownload + "' ,type=" + typechange + ", other=" + "'"
				+ other + "'" + Where + "type=" + type;
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
	 * * �����ݿ�uid���ݽ��и���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param date
	 *            ��¼�����ݿ������
	 * @param time
	 *            ��¼�����ݿ��ʱ��
	 * @param uidupload
	 *            �ϴ�����
	 * @param uiddownload
	 *            ��������
	 * @param uidnumber
	 *            ���ݿ�ı�uid��table���uid��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param typechange
	 *            �ı�typeֵ
	 */
	private void updateSQLUidType(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String other, int typechange) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "date='" + date
				+ "',time='" + time + "',upload='" + uidupload + "',download='"
				+ uiddownload + "' ,type=" + typechange + ", other=" + "'"
				+ other + "'" + Where + "type=" + type;
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
	 * �����ݿ�uid���ݽ����������£��Զ�����ʱ����ϴ���������
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
	public void updateSQLUidTypes(SQLiteDatabase sqlDataBase, int[] uidnumbers,
			int type, String other, int typechange) {
		// TODO Auto-generated method stub
		initTime();
		sqlDataBase.beginTransaction();
		try {
			for (int uidnumber : uidnumbers) {
				updateSQLUidType(sqlDataBase, date, time, uidnumber, type,
						other, typechange);
			}
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��������uid��������ʧ��");
		} finally {
			sqlDataBase.endTransaction();
		}

	}

	/**
	 * ��ȡ����Ӧ�õĲ��ظ�uid����
	 * 
	 * @param sqlDataBase
	 *            ���в��������ݿ�
	 * @return
	 */
	public int[] selectSQLUidnumbers(SQLiteDatabase sqlDataBase) {
		// TODO Auto-generated method stub
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = "SELECT DISTINCT uid FROM " + TableUidIndex + Where
				+ "other='" + "Install" + "'";
		try {
			cur = sqlDataBase.rawQuery(string, null);
			// showLog(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		int[] uids = new int[cur.getCount()];
		if (cur != null) {
			try {
				int mindown = cur.getColumnIndex("uid");
				// showLog(cur.getColumnIndex("minute") + "");
				int i = 0;
				if (cur.moveToFirst()) {
					do {
						uids[i] = (int) cur.getLong(mindown);
						i++;
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		// for (int i = 0; i < uids.length; i++) {
		// showLog(uids[i] + "");
		// }
		return uids;
	}

	/**
	 * �����ݿ����uid���ݵ�д������Ĳ���
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 *            ���ݿ�ı�uid��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 */
	private void exeSQLcreateUidtable(SQLiteDatabase mySQL, int uidnumber,
			int type, String other) {
		initTime();
		initUidData(uidnumber);
		String string = null;
		string = InsertTable + "uid" + uidnumber + Start + InsertUidColumnTotal
				+ End + Value + date + split + time + split + uidupload + split
				+ uiddownload + split + type + split + other + "'" + End;
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
		initUidData(uidnumber);
		String string = null;
		string = InsertTable + "uid" + uidnumber + Start + InsertUidColumnTotal
				+ End + Value + date + split + time + split + uidupload + split
				+ uiddownload + split + type + split + other + "'" + End;
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
	 * ��UidIndex���߳������ظ��������������
	 * 
	 * @param mySQL
	 */
	private void sortSQLUidIndex(SQLiteDatabase mySQL) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = "DELETE   FROM " + TableUidIndex + Where + "_id"
				+ " not in (select min(" + "_id" + ") from " + TableUidIndex
				+ " group by packagename)";

		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * ɾ�������UidIndex�������ն����uid��
	 * 
	 * @param mySQL
	 */
	private void delSQLUidIndexAndTable(SQLiteDatabase mySQL) {
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidIndex + Where + "other='UnInstall'";
		// showLog(string);
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		int i = 0;
		int[] uids = new int[cur.getCount()];
		if (cur != null) {
			try {
				int uidIndex = cur.getColumnIndex("uid");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						uids[i] = cur.getInt(uidIndex);
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		// ɾ��otherΪUnInstall����Ŀ
		delSQLUidIndex(mySQL);
		for (int j = 0; j < uids.length; j++) {
			// showLog(uids[j] + "");
			// showLog(isUidExistingInUidIndex(mySQL, uids[j]) + "");
			// �������uid=0�ı�
			if (uids[j] != 0) {
				if (!isUidExistingInUidIndex(mySQL, uids[j])) {
					DropUnusedUidTable(mySQL, uids[j]);
				}
			}
		}
	}

	/**
	 * ���uid������
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            Ҫ��յ�uid��
	 */
	private void DropUnusedUidTable(SQLiteDatabase mySQL, int uidnumber) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = DeleteTable + "uid" + uidnumber;

		// string = InsertTable + TableUidIndex + Start
		// + InsertUidIndexColumnTotal + ",other" + End + Value
		// + uidnumber + split + packagename + split + 0 + split
		// + "Install" + "'" + End;
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		// showLog(string);
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * �ж�uid�Ƿ��Ѿ���uidIndex�д���
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            �����жϵ�uid
	 * @return ���ڷ���true�������ڷ���false
	 */
	private boolean isUidExistingInUidIndex(SQLiteDatabase mySQL, int uidnumber) {
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidIndex + Where + "uid=" + uidnumber;
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		if (cur.moveToFirst()) {
			cur.close();
			return true;
		} else {
			cur.close();
			return false;
		}
	}

	/**
	 * �Ƿ񸲸ǰ�װ
	 * 
	 * @param mySQL
	 * @param packagename
	 *            ����
	 * @return �Ǹ��ǰ�װ������true���°�װ�������false
	 */
	private boolean isCoveringInstall(SQLiteDatabase mySQL, String packagename) {
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + TableUidIndex + Where + "packagename='"
				+ packagename + "'";
		// showLog(string);
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string);
		}
		int[] uids = new int[cur.getCount()];
		if (cur != null) {
			int i = 0;
			try {
				int uid = cur.getColumnIndex("uid");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					do {
						uids[i] = (int) cur.getInt(uid);
						i++;
					} while (cur.moveToNext());
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		cur.close();
		// for (int i = 0; i < uids.length; i++) {
		// showLog(uids[i] + "");
		// }

		if (uids.length == 1) {
			return false;
		}
		return true;
	}

	/**
	 * ���uidIndex ��otherΪUnInstalld������
	 * 
	 * @param mySQL
	 */
	private void delSQLUidIndex(SQLiteDatabase mySQL) {
		String string = null;
		// delete from Yookey where tit not in (select min(tit) from Yookey
		// group by SID)
		string = "DELETE   FROM " + TableUidIndex + Where + "other='"
				+ "UnInstall" + "'";
		try {
			mySQL.execSQL(string);
		} catch (Exception e) {
			// TODO: handle exception
			showLog(string + "fail");
		}
	}

	/**
	 * ��װ�����ʱ���uid������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 *            ���ݿ�ı�uid��
	 * @param packagename
	 *            ����
	 * @param other
	 *            ���İ�װ״̬
	 */
	private void exeSQLUidIndextable(SQLiteDatabase mySQL, int uidnumber,
			String packagename, String other) {
		String string = null;
		string = InsertTable + TableUidIndex + Start
				+ InsertUidIndexColumnTotal + ",other" + End + Value
				+ uidnumber + split + packagename + split + 0 + split
				+ "Install" + "'" + End;
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
	 * ����uid������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 *            ���ݿ�ı�uid��
	 * @param packagename
	 *            ����
	 */
	private void exeSQLcreateUidIndextable(SQLiteDatabase mySQL, int uidnumber,
			String packagename) {
		String string = null;
		string = InsertTable + TableUidIndex + Start
				+ InsertUidIndexColumnTotal + ",other" + End + Value
				+ uidnumber + split + packagename + split + 0 + split
				+ "Install" + "'" + End;
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
	 * ��ʼ��uid������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumbers
	 *            ���ݿ�ı���
	 * @param packagename
	 *            ����
	 */
	protected void exeSQLcreateUidIndextables(SQLiteDatabase mySQL,
			int[] uidnumbers, String[] packagenames) {
		for (int i = 0; i < uidnumbers.length; i++) {
			exeSQLcreateUidIndextable(mySQL, uidnumbers[i], packagenames[i]);
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
	 * ��ʼ��uid������
	 * 
	 * @param sqldatabase
	 * @return
	 */
	protected boolean initUidIndexTables(SQLiteDatabase sqldatabase) {
		// TODO Auto-generated method stub
		String string = null;
		string = CreateTable + TableUidIndex + Start + SQLId
				+ CreateparamUidIndex + End;
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
//			initUidTable(sqldatabase, 0);
//			exeSQLcreateUidtable(sqldatabase, date, time, 0, 0, null);
//			exeSQLcreateUidtable(sqldatabase, date, time, 0, 1, null);
			for (int uidnumber : uidnumbers) {
				// -1Ϊ�ظ���
				if (uidnumber != -1) {
					initUidTable(sqldatabase, uidnumber);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 0,
							null);
					exeSQLcreateUidtable(sqldatabase, date, time, uidnumber, 1,
							null);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��ʼ��ȫ����uid��ʧ��");
		}
	}

	/**
	 * ��ȡ���ظ���uid
	 * 
	 * @param uidnumbers
	 * @return ��ȡ��ɵ�uid����
	 */
	protected int[] sortUids(int[] uidnumbers) {
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
	 * @return ��ʽΪ00:00:00
	 */
	public String gettime() {
		initTime();
		return time;
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
	 * ��¼uid����������
	 * 
	 * @param context
	 * @param daily
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	public void RecordUidwritestats(Context context, boolean daily) {
		// TODO Auto-generated method stub
		if (!SQLHelperTotal.TableWiFiOrG23.equals("")) {
			// isUsingSQL = true;
			SQLiteDatabase sqlDataBase = creatSQL(context);
			uidRecordwritestats(sqlDataBase, daily);
			closeSQL(sqlDataBase);
			// isUsingSQL = false;
		}
	}

	/**
	 * ��¼uid����������
	 * 
	 * @param sqlDataBase
	 * @param daily
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	private void uidRecordwritestats(SQLiteDatabase sqlDataBase, boolean daily) {
		// TODO Auto-generated method stub
		sqlDataBase.beginTransaction();
		try {
			int[] uidnumbers = selectSQLUidnumbers(sqlDataBase);
			initTime();
			statsSQLuids(sqlDataBase, uidnumbers, date, time, 2, null, daily);
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			sqlDataBase.endTransaction();
		}
	}

	/**
	 * ��¼uid��������
	 * 
	 * @param sqlDataBase
	 *            ���в��������ݿ�
	 * @param uidnumbers
	 *            ���м�¼��uid��
	 * @param date
	 *            ��¼���ݵ�����
	 * @param time
	 *            ��¼���ݵ�ʱ��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 * @param daily
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	private void statsSQLuids(SQLiteDatabase sqlDataBase, int[] uidnumbers,
			String date, String time, int type, String other, boolean daily) {
		// TODO Auto-generated method stub

		for (int uidnumber : uidnumbers) {
			initUidData(uidnumber);
			statsSQLuid(sqlDataBase, uidnumber, date, time, uidupload,
					uiddownload, type, other, daily);
		}

	}

	/**
	 * �����ݿ����ݽ���ͳ�ƣ�д��ʱ�䷶Χ�ڵ��ϴ�����������
	 * 
	 * @param mySQL
	 *            ����д����������ݿ�SQLiteDatagase
	 * @param uidnumber
	 *            ���ݿ�ı�uid��
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
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	private void statsSQLuid(SQLiteDatabase mySQL, int uidnumber, String date,
			String time, long upload, long download, int type, String other,
			boolean daily) {
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + "uid" + uidnumber + Where + "type=" + 0;
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
				showLog("cur-searchfail");
			}
		}
		cur.close();
		// ��ʼ��д�����ݣ�wifi�Լ�g23��
		// ���֮ǰ���ݴ����µ����ݣ������¼���
		if (oldup > upload || olddown > download) {
			oldup = upload;
			olddown = download;
		} else {
			oldup = upload - oldup;
			olddown = download - olddown;
		}
		if (daily) {
			// showLog("�ϴ�uid" + uidnumber + "����" + oldup + "B" + "  " + "����uid"
			// + uidnumber + "����" + olddown + "B");
			// ����ʵ�����ݽ������ݿ�
			updateSQLUidType(mySQL, date, time, oldup, olddown, uidnumber, 0,
					SQLHelperTotal.TableWiFiOrG23, 2);
			// ����µ���������
			updateSQLUidType(mySQL, date, time, upload, download, uidnumber, 1,
					SQLHelperTotal.TableWiFiOrG23, 0);
			exeSQLcreateUidtable(mySQL, uidnumber, 1,
					SQLHelperTotal.TableWiFiOrG23);
		} else if (oldup != 0 && olddown != 0) {
			// showLog("�ϴ�uid" + uidnumber + "����" + oldup + "B" + "  " + "����uid"
			// + uidnumber + "����" + olddown + "B");
			// ����ʵ�����ݽ������ݿ�
			updateSQLUidType(mySQL, date, time, oldup, olddown, uidnumber, 0,
					SQLHelperTotal.TableWiFiOrG23, 2);
			// ����µ���������
			updateSQLUidType(mySQL, date, time, upload, download, uidnumber, 1,
					SQLHelperTotal.TableWiFiOrG23, 0);
			exeSQLcreateUidtable(mySQL, uidnumber, 1,
					SQLHelperTotal.TableWiFiOrG23);
		}

	}

	/**
	 * ����uid��ʷ������ѯ����wifi��mobile
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param uid
	 *            �����ѯ��uid��
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] SelectuidData(Context context, int year, int month, int uid) {
		return SelectData(context, year, month, TableUid + uid);
	}

	/**
	 * ����uid��ʷ������ѯ����wifi��mobile
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param uid
	 *            �����ѯ��uid��
	 * @param other
	 *            Ҫ��ѯ����������"wifi"or"mobile"
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	public long[] SelectuidWifiorMobileData(Context context, int year,
			int month, int uid, String other) {
		return SelectUidmobileOrwifiData(context, year, month, TableUid + uid,
				other);
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
							a[i] += newup;
							a[i + 31] += newdown;
						} else {
							a[0] += a[i];
							a[63] += a[i + 31];
							while (!newdate.equals(countdate)) {
								i++;
								if (i < 10)
									countdate = dateStr1 + i;
								else
									countdate = dateStr2 + i;
								if (i > 31) {
									break;
								}
							}
							a[i] += newup;
							a[i + 31] += newdown;
						}
					} while (cur.moveToNext());
				}
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
	 * @param other
	 *            Ҫ��ѯ����������"wifi"or"mobile"
	 * @return ����һ��64λ���顣a[0]Ϊ�ܼ��ϴ�����a[63]Ϊ�ܼ���������
	 *         a[1]-a[31]Ϊ1�ŵ�31���ϴ�������a[32]-a[62]Ϊ1�ŵ�31����������
	 */
	private long[] SelectUidmobileOrwifiData(Context context, int year,
			int month, String table, String other) {
		long[] a = new long[64];
		SQLiteDatabase sqlDataBase = creatSQL(context);
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-" + "31"
				+ AND + "other=" + "'" + other + AND + "type=" + 2;
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
							a[i] += newup;
							a[i + 31] += newdown;
						} else {
							a[0] += a[i];
							a[63] += a[i + 31];
							while (!newdate.equals(countdate)) {
								i++;
								if (i < 10)
									countdate = dateStr1 + i;
								else
									countdate = dateStr2 + i;
								if (i > 31) {
									break;
								}
							}
							a[i] += newup;
							a[i + 31] += newdown;
						}
					} while (cur.moveToNext());
				}
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
