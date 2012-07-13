package com.hiapk.sqlhelper.uid;

import java.util.List;

import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.pub.SQLStatic;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;

public class SQLHelperUidRecord {
	private ActivityManager mActivityManager = null;

	public SQLHelperUidRecord(Context context) {
		super();
		// initTime();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// TODO Auto-generated constructor stub
	}

	private String InsertTable = "INSERT INTO ";
	private String SelectTable = "SELECT * FROM ";
	private String UpdateTable = "UPDATE ";
	private String UpdateSet = " SET ";
	private String Where = " WHERE ";
	private String AND = "' AND ";
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
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";

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

	private void updateSQLUidTypeDate2to2(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String network, int typechange) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "time='" + time
				+ "',upload='" + uidupload + "',download='" + uiddownload
				+ "' ,type=" + typechange + Where + "date='" + date + AND
				+ "other='" + network + AND + "type=" + type;
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

	private void updateSQLUidTypeDate0to2(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String network, int typechange) {
		// TODO Auto-generated method stub
		String string = null;
		string = UpdateTable + "uid" + uidnumber + UpdateSet + "time='" + time
				+ "',upload='" + uidupload + "',download='" + uiddownload
				+ "' ,type=" + typechange + " ,date='" + date + "',other='"
				+ network + "'" + Where + "type=" + type;
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
	private void exeSQLcreateUidtableSetData(SQLiteDatabase mySQL, String date,
			String time, int uidnumber, long upload, long download, int type,
			String other) {
		String string = null;
		// ��ʾ�Ƿ�Ϊ����������������ʼ����Ϊ0
		string = InsertTable + "uid" + uidnumber + Start + InsertUidColumnTotal
				+ End + Value + date + split + time + split + upload + split
				+ download + split + type + split + other + "'" + End;
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
	 * ��¼uid����������
	 * 
	 * @param context
	 * @param daily
	 *            true��ǿ�Ƽ�¼��false�򲻼�¼����Ϊ0������
	 */
	public void RecordUidwritestats(SQLiteDatabase sqlDataBase,
			int[] uidnumbers, boolean daily, String network) {
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
				.getRunningAppProcesses();
		initTime();
		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			// ͨ��pacname�ж��Ƿ�Ϊ��Ҫ��¼��Ӧ��
			String pacname = appProcessInfo.processName;
			if (SQLStatic.packagename_ALL.contains(pacname)) {
				int uidnumber = appProcessInfo.uid;
				long[] uiddata = SQLHelperDataexe.initUidData(uidnumber);
				if (uiddata[0] != 0 || uiddata[1] != 0) {
					statsSQLuid(sqlDataBase, uidnumber, date, time, uiddata[0],
							uiddata[1], 2, null, daily, network);
				}
			}

		}

		// if (!network.equals("")) {
		// uidRecordwritestats(sqlDataBase, uidnumbers, daily, network);
		// }
	}

	/**
	 * ��������״̬ѡ���Ӧ��֮ǰ������
	 * 
	 * @param mySQL
	 * @param uidnumber
	 * @param network
	 * @return a[0]Ϊ�ܼ�����a[1]�ܼ��ϴ�����a[2]�ܼ���������
	 */
	private long[] getSQLuidtotalData(SQLiteDatabase mySQL, int uidnumber,
			String network) {
		String string = null;
		if (network == NETWORK_FLAG) {
			// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
			string = SelectTable + "uid" + uidnumber + Where + "type=" + 3;
		} else {
			string = SelectTable + "uid" + uidnumber + Where + "type=" + 4;
		}
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			showLog("error" + string);
		}
		long[] a = new long[3];
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					// ���֮ǰ���ϴ�����ֵ
					a[1] = cur.getLong(minup);
					a[2] = cur.getLong(mindown);
				}
			} catch (Exception e) {
				// TODO: handle exception
				showLog("cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = a[1] + a[2];
		return a;
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
			boolean daily, String network) {
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = SelectTable + "uid" + uidnumber + Where + "type=" + 0;
		try {
			cur = mySQL.rawQuery(string, null);
		} catch (Exception e) {
			// TODO: handle exception
			SQLHelperUidSelectFail selectfail = new SQLHelperUidSelectFail();
			selectfail.selectfails(mySQL, "uid" + uidnumber, uidnumber);
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
				showLog("cur-searchfail");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		if (cur != null) {
			cur.close();
		}
		if (oldup0 != -100) {

			// ��ʼ��д�����ݣ�wifi�Լ�g23��
			// ���֮ǰ���ݴ����µ����ݣ������¼���
			if ((oldup0 > (upload + 10000)) || (olddown0 > (download + 10000))) {
				oldup0 = upload;
				olddown0 = upload;
			} else {
				oldup0 = upload - oldup0;
				olddown0 = download - olddown0;
			}

			if ((oldup0 != 0 || olddown0 != 0)
					&& ((olddown0 > 512) || (oldup0 > 512))) {
				cur = null;
				// ��������ж�
				string = SelectTable + "uid" + uidnumber + Where + "date='"
						+ date + AND + "other='" + network + AND + "type=" + 2;
				try {
					cur = mySQL.rawQuery(string, null);
				} catch (Exception e) {
					// TODO: handle exception
					showLog("error" + string);
				}
				long oldup2 = 0;
				long olddown2 = 0;
				String olddate2 = "";
				// ������� ����+
				// showLog("cur.move" + cur.moveToFirst());
				if (cur.moveToFirst()) {
					try {
						int minup = cur.getColumnIndex("upload");
						int mindown = cur.getColumnIndex("download");
						int dateIndex = cur.getColumnIndex("date");
						// showLog(cur.getColumnIndex("minute") + "");
						if (cur.moveToFirst()) {
							// ���֮ǰ���ϴ�����ֵ
							oldup2 = cur.getLong(minup);
							olddown2 = cur.getLong(mindown);
							olddate2 = cur.getString(dateIndex);
						}
					} catch (Exception e) {
						// TODO: handle exception
						showLog("cur-searchfail");
						oldup2 = 0;
						olddown2 = 0;
						olddate2 = "";
					}
					if (olddate2 != "") {
						updateSQLUidTypeDate2to2(mySQL, date, time, oldup2
								+ oldup0, olddown2 + olddown0, uidnumber, 2,
								network, 2);
						updateSQLUidType(mySQL, date, time, upload, download,
								uidnumber, 0, network, 0);
						statsSQLuidTotaldata(mySQL, uidnumber, date, time,
								oldup0, olddown0, network);
					}
					// �������add
				} else {
					updateSQLUidTypeDate0to2(mySQL, date, time, oldup0,
							olddown0, uidnumber, 0, network, 2);
					exeSQLcreateUidtableSetData(mySQL, date, time, uidnumber,
							upload, download, 0, network);
					// updateSQLUidType(mySQL, date, time, upload, download,
					// uidnumber, 0, network, 0);

					statsSQLuidTotaldata(mySQL, uidnumber, date, time, oldup0,
							olddown0, network);
				}
				if (cur != null) {
					cur.close();
				}
			}
		}
	}

	/**
	 * ��¼�ܵ���������
	 * 
	 * @param mySQL
	 * @param uidnumber
	 * @param date
	 * @param time
	 * @param upload
	 * @param download
	 * @param network
	 */
	private void statsSQLuidTotaldata(SQLiteDatabase mySQL, int uidnumber,
			String date, String time, long upload, long download, String network) {
		if (network == NETWORK_FLAG) {
			long[] beforeTotal = new long[3];
			beforeTotal = getSQLuidtotalData(mySQL, uidnumber, network);
			updateSQLUidType(mySQL, date, time, beforeTotal[1] + upload,
					beforeTotal[2] + download, uidnumber, 3, null, 3);
		} else {
			long[] beforeTotal = new long[3];
			beforeTotal = getSQLuidtotalData(mySQL, uidnumber, network);
			updateSQLUidType(mySQL, date, time, beforeTotal[1] + upload,
					beforeTotal[2] + download, uidnumber, 4, null, 4);
		}
	}

	/**
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("databaseUidRecord", string);
	}
}
