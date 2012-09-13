package com.hiapk.sqlhelper.uid;

import java.util.List;

import com.hiapk.bean.UidTraffs;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataOnUpdate;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLHelperUidRecord {
	private ActivityManager mActivityManager = null;

	public SQLHelperUidRecord(Context context) {
		super();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
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
	// logs
	private String TAG = "databaseUidRecord";

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
		StringBuilder string = new StringBuilder();
		string = string.append(UpdateTable).append("uid").append(uidnumber)
				.append(UpdateSet).append("date='").append(date)
				.append("',time='").append(time).append("',upload='")
				.append(uidupload).append("',download='").append(uiddownload)
				.append("' ,type=").append(typechange).append(", other=")
				.append("'").append(other).append("'").append(Where)
				.append("type=").append(type);
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail-updateSQLUidType");
		}
	}

	private void updateSQLUidTypeDate2to2(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String network, int typechange) {
		StringBuilder string = new StringBuilder();
		string = string.append(UpdateTable).append("uid").append(uidnumber)
				.append(UpdateSet).append("time='").append(time)
				.append("',upload='").append(uidupload).append("',download='")
				.append(uiddownload).append("' ,type=").append(typechange)
				.append(Where).append("date='").append(date).append(AND)
				.append("other='").append(network).append(AND).append("type=")
				.append(type);
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail-updateSQLUidTypeDate2to2");
		}
	}

	private void updateSQLUidTypeDate0to2(SQLiteDatabase mySQL, String date,
			String time, long uidupload, long uiddownload, int uidnumber,
			int type, String network, int typechange) {
		StringBuilder string = new StringBuilder();
		string = string.append(UpdateTable).append("uid").append(uidnumber)
				.append(UpdateSet).append("time='").append(time)
				.append("',upload='").append(uidupload).append("',download='")
				.append(uiddownload).append("' ,type=").append(typechange)
				.append(" ,date='").append(date).append("',other='")
				.append(network).append("'").append(Where).append("type=")
				.append(type);
		// UPDATE Person SET
		// date='date',time='time',upload='upload',download='download'
		// ,type='typechange' WHERE type=type
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail-updateSQLUidTypeDate0to2");
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
		StringBuilder string = new StringBuilder();
		// ��ʾ�Ƿ�Ϊ����������������ʼ����Ϊ0
		string = string.append(InsertTable).append("uid").append(uidnumber)
				.append(Start).append(InsertUidColumnTotal).append(End)
				.append(Value).append(date).append(split).append(time)
				.append(split).append(upload).append(split).append(download)
				.append(split).append(type).append(split).append(other)
				.append("'").append(End);
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('1','1','1','1','1','1')
		// INSERT INTO t4 (date,time,upload,download,uid,type) VALUES
		// ('date','time','upload','download','uid','type')
		try {
			mySQL.execSQL(string.toString());
		} catch (Exception e) {
			Logs.d(TAG, string.toString() + "fail-exeSQLcreateUidtableSetData");
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
	public void RecordUidwritestats(Context context,
			SQLiteDatabase sqlDataBase, int[] uidnumbers, boolean daily,
			String network) {
		SharedPrefrenceDataOnUpdate sharedUpdate = new SharedPrefrenceDataOnUpdate(
				context);
		boolean isUpdated = sharedUpdate.isUidRecordUpdated();
		if (isUpdated) {
			List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
					.getRunningAppProcesses();
			initTime();
			for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
				// ͨ��pacname�ж��Ƿ�Ϊ��Ҫ��¼��Ӧ��
				String pacname = appProcessInfo.processName;
				if (SQLStatic.packagename_ALL.contains(pacname)) {
					int uidnumber = appProcessInfo.uid;
					UidTraffs uiddata = SQLHelperDataexe.initUidData(uidnumber);
					if (uiddata.getUpload() != 0 || uiddata.getDownload() != 0) {
						statsSQLuid(context, sqlDataBase, uidnumber, date,
								time, uiddata.getUpload(),
								uiddata.getDownload(), 2, null, daily, network);
					}
				}

			}
		} else {
			// ��ʼ�����ݣ�Ϊ�˸���
			while (SQLStatic.uidnumbers == null) {
				SQLStatic.getuidsAndpacname(context);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					Logs.d(TAG, "��ȡuid����ʧ��");
					e.printStackTrace();
				}
			}
			for (int uidnumber : SQLStatic.uidnumbers) {
				statsSQLuidinitxml(context, sqlDataBase, uidnumber, network,
						network);
			}
		}
		if (isUpdated == false) {
			sharedUpdate.setUidRecordUpdated(true);
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
		StringBuilder string = new StringBuilder();
		if (network == NETWORK_FLAG) {
			// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
			string = string.append(SelectTable).append("uid").append(uidnumber)
					.append(Where).append("type=").append(3);
		} else {
			string = string.append(SelectTable).append("uid").append(uidnumber)
					.append(Where).append("type=").append(4);
		}
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {

			Logs.d(TAG, string.toString() + "fail" + e);
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
				Logs.d(TAG, "cur-searchfail");
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
	private void statsSQLuid(Context context, SQLiteDatabase mySQL,
			int uidnumber, String date, String time, long upload,
			long download, int type, String other, boolean daily, String network) {
		StringBuilder string = new StringBuilder();
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = string.append(SelectTable).append("uid").append(uidnumber)
				.append(Where).append("type=").append(0);
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {
			SQLHelperUidSelectFail selectfail = new SQLHelperUidSelectFail();
			selectfail.selectfails(mySQL, "uid" + uidnumber, uidnumber);
		}
		long oldup0 = -50;
		long olddown0 = -50;
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
				Logs.d(TAG, "cur-searchfail");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		if (cur != null) {
			cur.close();
		}
		if (oldup0 != -100 && olddown0 != -100) {
			if (oldup0 < 0)
				oldup0 = 0;
			if (olddown0 < 0)
				olddown0 = 0;
			// TrafficManager
			// .setUidtraffinit(context, uidnumber, oldup0, olddown0);
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
				string = new StringBuilder();
				string = string.append(SelectTable).append("uid")
						.append(uidnumber).append(Where).append("date='")
						.append(date).append(AND).append("other='")
						.append(network).append(AND).append("type=").append(2);
				try {
					cur = mySQL.rawQuery(string.toString(), null);
				} catch (Exception e) {
					Logs.d(TAG, string.toString() + "fail" + e);
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
						Logs.d(TAG, "cur-searchfail");
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
						statsSQLuidTotaldata(context, mySQL, uidnumber, date,
								time, oldup0, olddown0, network);
					}
					// �������add
				} else {
					updateSQLUidTypeDate0to2(mySQL, date, time, oldup0,
							olddown0, uidnumber, 0, network, 2);
					exeSQLcreateUidtableSetData(mySQL, date, time, uidnumber,
							upload, download, 0, network);
					// updateSQLUidType(mySQL, date, time, upload, download,
					// uidnumber, 0, network, 0);

					statsSQLuidTotaldata(context, mySQL, uidnumber, date, time,
							oldup0, olddown0, network);
				}
				if (cur != null) {
					cur.close();
				}
			}
		}
	}

	/**
	 * �����ݿ����ݽ���ͳ�ƣ�д��ʱ�䷶Χ�ڵ��ϴ����������ݣ�δ��ɾ�������ڰ汾����ʱ���¼�¼��xml
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
	private void statsSQLuidinitxml(Context context, SQLiteDatabase mySQL,
			int uidnumber, String date, String time) {
		long totalup = 0;
		long totaldown = 0;
		StringBuilder string = new StringBuilder();
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
		// SELECT * FROM table WHERE type=0
		string = string.append(SelectTable).append("uid").append(uidnumber)
				.append(Where).append("type=").append(3);
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {
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
				Logs.d(TAG, "cur-searchfail");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		if (oldup0 != -100) {
			totalup += oldup0;
			totaldown += olddown0;
		}
		if (cur != null) {
			cur.close();
		}
		string = string.append(SelectTable).append("uid").append(uidnumber)
				.append(Where).append("type=").append(4);
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {
			SQLHelperUidSelectFail selectfail = new SQLHelperUidSelectFail();
			selectfail.selectfails(mySQL, "uid" + uidnumber, uidnumber);
		}
		oldup0 = -100;
		olddown0 = -100;
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
				Logs.d(TAG, "cur-searchfail");
				oldup0 = -100;
				olddown0 = -100;
			}
		}
		if (oldup0 != -100) {
			totalup += oldup0;
			totaldown += olddown0;
		}
		if (cur != null) {
			cur.close();
		}

		if (totaldown != 0) {
			TrafficManager.setUidtraffinit(context, uidnumber, totalup,
					totaldown);
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
	private void statsSQLuidTotaldata(Context context, SQLiteDatabase mySQL,
			int uidnumber, String date, String time, long upload,
			long download, String network) {
		if (network == NETWORK_FLAG) {
			long[] beforeTotal = new long[3];
			TrafficManager.setUidtraff(context, uidnumber, upload, download);
			beforeTotal = getSQLuidtotalData(mySQL, uidnumber, network);
			updateSQLUidType(mySQL, date, time, beforeTotal[1] + upload,
					beforeTotal[2] + download, uidnumber, 3, null, 3);
		} else {
			long[] beforeTotal = new long[3];
			TrafficManager.setUidtraff(context, uidnumber, upload, download);
			beforeTotal = getSQLuidtotalData(mySQL, uidnumber, network);
			updateSQLUidType(mySQL, date, time, beforeTotal[1] + upload,
					beforeTotal[2] + download, uidnumber, 4, null, 4);
		}
	}

}
