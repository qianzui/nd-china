package com.hiapk.sqlhelper.uid;

import java.util.List;

import com.hiapk.bean.UidTraffs;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.firewall.Block;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLHelperUidother {

	public SQLHelperUidother() {
		super();
	}

	private String CreateTable = "CREATE TABLE IF NOT EXISTS ";
	private String SQLId = "_id INTEGER PRIMARY KEY,";
	private String CreateparamUid = "date date,time time,upload INTEGER,download INTEGER,type INTEGER ,other varchar(15)";
	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String DeleteTable = "DELETE FROM ";
	private String Where = " WHERE ";
	private String Start = " (";
	private String End = ") ";
	private String InsertUidColumnTotal = "date,time,upload,download,type,other";
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
	// log
	private String TAG = "databaseUidother";

	/**
	 * �ڰ�װ�³���ʱ����uidIndexĿ¼
	 * 
	 * @param context
	 * @param uidnumber
	 *            �����ĳ���uid��
	 * @param packageName
	 *            �����ĳ������
	 * @param other
	 *            ����״̬ ����Ҫ���д�����uid���鸲�ǰ�װ����null���°�װ����{1019}
	 */
	public int[] updateSQLUidOnInstall(Context context, int uidnumber,
			String packageName, String other) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		// ���¶��徲̬��uid����
		// String newpackage = selectPackagenames(context);
		SQLStatic.getuidsAndpacname(context);
		// SQLStatic.packagename_ALL = selectPackagenames(context);
		String pacTemp = sharedData.getPackageNames();
		if (pacTemp != "") {
			SQLStatic.packagename_ALL = pacTemp;
		}

		if (SQLStatic.packagename_ALL.contains(packageName)) {
			// ���ǰ�װ
			return null;
		} else {
			// �°�װ����
			SQLStatic.packagename_ALL = selectPackagenames(context);
			sharedData.setPackageNames(SQLStatic.packagename_ALL);
			if (SQLStatic.TableWiFiOrG23 == "") {
				SQLStatic.initTablemobileAndwifi(context);
			}
			AlarmSet alset = new AlarmSet();
			alset.StartAlarm(context);
			return new int[] { 1019 };
		}

	}

	public void updateSQLUidOnInstall(Context context, int uidnumber,
			String packageName, String other, List<Integer> uid_List_Add,
			List<Integer> uid_List_Del) {
		SQLiteDatabase mySQL = SQLHelperCreateClose.creatSQLUid(context);
		// ����Uid���ݿ�
		mySQL.beginTransaction();
		try {
			// if (uids != null && uids[0] != 1019) {
			// for (int i = 0; i < uids.length; i++) {
			// if (uids[i] != 0) {
			// DropUnusedUidTable(mySQL, uids[i]);
			// }
			// }
			// }
			if (uid_List_Del != null) {
				for (Integer uid : uid_List_Del) {
					DropUnusedUidTable(mySQL, uid);
					// initTime();
					// initUidTable(mySQL, uid);
					// exeSQLcreateUidtable(mySQL, date, time, uid, 0, null);
					// exeSQLcreateUidtable(mySQL, date, time, uid, 1, null);
				}
			}

			// showLog("�°�װ����" + packageName + uidnumber);
			// ������ٽ���
			if (uid_List_Add != null) {
				UidTraffs uiddata = new UidTraffs();
				for (Integer uid : uid_List_Add) {
					// DropUnusedUidTable(mySQL, uid);
					// ����������ɵ�uid����
					TrafficManager.clearUidtraff(context, uid);
					initTime();
					initUidTable(mySQL, uid);
					StringBuilder string = new StringBuilder();
					string = string.append(SelectTable).append("uid")
							.append(uid).append(Where).append("type=")
							.append(0);
					// showLog(string);
					try {
						cur = mySQL.rawQuery(string.toString(), null);
					} catch (Exception e) {
						// ����ʧ�����½���
						Logs.d(TAG, "selectfail" + string.toString());
					}
					if (!cur.moveToFirst()) {
						exeSQLcreateUidtable(mySQL, date, time, uid, 0, null,
								uiddata);
						exeSQLcreateUidtable(mySQL, date, time, uid, 3, null,
								uiddata);
						exeSQLcreateUidtable(mySQL, date, time, uid, 4, null,
								uiddata);
					} else {
						cur.close();
					}

				}

			}
			mySQL.setTransactionSuccessful();
		} catch (Exception e) {
			Logs.d(TAG, "����������ʧ��");
		} finally {
			mySQL.endTransaction();
			SQLHelperCreateClose.closeSQL(mySQL);
		}

	}

	/**
	 * ��ȡ����Ӧ�õĲ��ظ�����
	 * 
	 * @param sqlDataBase
	 *            ���в��������ݿ�
	 * @return
	 */
	public String selectPackagenames(Context context) {
		PackageManager pkgmanager = context.getPackageManager();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		String pacstemp = new String();
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			String fliter = Block.filter;
			String pacname = packageinfo.packageName;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					pacstemp += pacname;
					// j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		// String[] pacs = new String[j];
		// for (int i = 0; i < j; i++) {
		// pacs[i] = pacstemp[i];
		// }
		return pacstemp;
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
	 *            ���ݿ�ı���uid��
	 * @param type
	 *            ���ڼ�¼����״̬����ͳ������
	 * @param other
	 *            ���ڼ�¼�������ݵ�
	 */
	private void exeSQLcreateUidtable(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, int type, String other,
			UidTraffs uiddata) {
		uiddata = SQLHelperDataexe.initUidData(uidnumber, uiddata);
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
	 * ���uid������
	 * 
	 * @param mySQL
	 * @param uidnumber
	 *            Ҫ��յ�uid��
	 */
	private void DropUnusedUidTable(SQLiteDatabase mySQL, int uidnumber) {
		StringBuilder string = new StringBuilder();
		string = string.append(DeleteTable).append("uid").append(uidnumber);
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string + "fail");
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
		StringBuilder string = new StringBuilder();
		string = string.append(CreateTable).append("uid").append(uidnumber)
				.append(Start).append(SQLId).append(CreateparamUid).append(End);
		try {
			sqldatabase.execSQL(string.toString());
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