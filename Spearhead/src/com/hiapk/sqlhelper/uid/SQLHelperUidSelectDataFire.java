package com.hiapk.sqlhelper.uid;

import java.util.HashMap;
import java.util.List;

import com.hiapk.bean.DatauidHash;
import com.hiapk.logs.Logs;
import com.hiapk.util.SQLStatic;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

public class SQLHelperUidSelectDataFire {
	private ActivityManager mActivityManager = null;
	// log
	private String TAG = "SQLHelperUidSelectDate";
	// date
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;
	private String weekStart;
	private String date;
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND_A = " AND ";

	protected SQLHelperUidSelectDataFire(Context context) {
		super();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	private String SelectTable = "SELECT * FROM ";
	private String Where = " WHERE ";
	private Cursor cur;
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";
	private String WIFI_FLAG = "wifi";

	// /**
	// * �����ݿ�uid���ݽ����������£�
	// *
	// * @param sqlDataBase
	// * ���в��������ݿ�SQLiteDatagase
	// * @param uidnumbers
	// * ���ݿ�ı����鼯�ϣ�uid��table���uid�ţ�
	// * @param type
	// * ���ڼ�¼����״̬����ͳ������
	// * @param other
	// * ���ڼ�¼�������ݵ�
	// * @param typechange
	// * �ı�typeֵ
	// */
	// public HashMap<Integer, Data> initSQLUidtraff(SQLiteDatabase sqlDataBase,
	// int[] uidnumbers) {
	// HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
	// mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers);
	// return mp;
	// }

	/**
	 * �����ݿ�uid���ݽ����������£����¶��������ݣ�
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
	 * @param flagFire
	 *            ����ǽ��ֵ���ͣ�0-2��0Ϊÿ�գ�1Ϊÿ�ܣ�2Ϊÿ��--ȡ��flag����
	 */
	protected HashMap<Integer, DatauidHash> getSQLUidtraffMonth(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire) {
		HashMap<Integer, DatauidHash> mp = new HashMap<Integer, DatauidHash>();
		mp = SelectUiddownloadAndupload(sqlDataBase, uidnumbers, flagFire);
		return mp;
	}

	private HashMap<Integer, DatauidHash> SelectUiddownloadAndupload(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire) {
		HashMap<Integer, DatauidHash> mp = new HashMap<Integer, DatauidHash>();
		initTime();
		weekStart = selectWeekStart(sqlDataBase, year, month, monthDay, weekDay);
		Logs.d(TAG, weekStart);
		if (SQLStatic.uiddataCache == null) {
			mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers);
		} else {

			switch (flagFire) {
			case 1:
				mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers,
						flagFire, SQLStatic.uiddataCache);
				break;
			case 2:
				mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers,
						flagFire, SQLStatic.uiddataCache);
				break;
			default:
				mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers,
						flagFire, SQLStatic.uiddataCache);
				break;
			}
		}
		return mp;
	}

	private HashMap<Integer, DatauidHash> SelectUiddownloadAnduploadPart(
			SQLiteDatabase sqlDataBase, int[] uidnumbers, int flagFire,
			HashMap<Integer, DatauidHash> mp) {
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
				.getRunningAppProcesses();
		long[] tpmobile = new long[3];
		long[] tpwifi = new long[3];
		int uidnumber;
		String pacname;
		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
			// ͨ��uidnumber�ж��Ƿ�Ϊ��Ҫ��¼��Ӧ��
			pacname = appProcessInfo.processName;
			uidnumber = appProcessInfo.uid;
			if (SQLStatic.packagename_ALL.contains(pacname)) {
				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumber,
						NETWORK_FLAG, flagFire);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumber, "wifi",
						flagFire);
				DatauidHash temp = mp.get(uidnumbers);
				if (temp == null) {
					temp = new DatauidHash();
				}
				temp = setUidData(temp, tpmobile, tpwifi, flagFire);
				mp.put(uidnumber, temp);
				// showLog(uidnumber[i]+"traff"+get[1]+"");
			}
		}
		return mp;
	}

	private HashMap<Integer, DatauidHash> SelectUiddownloadAnduploadAll(
			SQLiteDatabase sqlDataBase, int[] uidnumbers) {
		HashMap<Integer, DatauidHash> mp = new HashMap<Integer, DatauidHash>();
		long[] tpmobile = new long[3];
		long[] tpwifi = new long[3];
		for (int i = 0; i < uidnumbers.length; i++) {
			if (uidnumbers[i] != -1) {
				DatauidHash temp = new DatauidHash();
				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumbers[i],
						NETWORK_FLAG, 0);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumbers[i], "wifi",
						0);
				temp = setUidData(temp, tpmobile, tpwifi, 0);
				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumbers[i],
						NETWORK_FLAG, 1);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumbers[i], "wifi",
						1);
				temp = setUidData(temp, tpmobile, tpwifi, 1);
				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumbers[i],
						NETWORK_FLAG, 2);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumbers[i], "wifi",
						2);
				temp = setUidData(temp, tpmobile, tpwifi, 2);
				mp.put(uidnumbers[i], temp);
				// showLog(uidnumber[i]+"traff"+get[1]+"");
			}
		}
		return mp;
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
			String network, int flagFire) {
		StringBuilder string = new StringBuilder();
		String strDate = getDateString(mySQL, flagFire);
		if (network == NETWORK_FLAG) {
			// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
			string = string.append(SelectTable).append("uid").append(uidnumber)
					.append(Where).append("other='").append(NETWORK_FLAG)
					.append("'").append(AND_A).append(" type=").append(2)
					.append(strDate);
		} else {
			string = string.append(SelectTable).append("uid").append(uidnumber)
					.append(Where).append("other='").append(WIFI_FLAG)
					.append("'").append(AND_A).append(" type=").append(2)
					.append(strDate);
		}
		// Logs.d(TAG, string.toString());
		try {
			cur = mySQL.rawQuery(string.toString(), null);
		} catch (Exception e) {
			Logs.d(TAG, "error+CreateTable" + string);
			SQLHelperUidSelectFail selectFail = new SQLHelperUidSelectFail();
			selectFail.selectfails(mySQL, "uid" + uidnumber, uidnumber);
		}
		long[] a = new long[3];
		if (cur != null) {
			try {
				int minup = cur.getColumnIndex("upload");
				int mindown = cur.getColumnIndex("download");
				// showLog(cur.getColumnIndex("minute") + "");
				if (cur.moveToFirst()) {
					a[1] = cur.getLong(minup);
					a[2] = cur.getLong(mindown);
				}
				while (cur.moveToNext()) {
					a[1] += cur.getLong(minup);
					a[2] += cur.getLong(mindown);
				}
			} catch (Exception e) {
				Logs.d(TAG, "cur-searchfail" + e);
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = a[1] + a[2];
		return a;
	}

	// ��ͬ��ͳ�Ʒ�ʽ���ز�ͬ������
	private String getDateString(SQLiteDatabase mySQL, int flagFire) {
		StringBuilder strDate = new StringBuilder();
		// ��ʽ������
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		// �ֶο�ͷ
		strDate.append(AND_A).append("date");
		switch (flagFire) {
		case 1:
			return strDate.append(Between).append(weekStart).append(AND_B)
					.append(date).append("'").toString();
		case 2:
			return strDate.append(Between).append(year).append("-")
					.append(month2).append("-").append(00).append(AND_B)
					.append(date).append("'").toString();
		default:
			return strDate.append("='").append(date).append("'").toString();
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
		weekDay = t.weekDay;
		// SQLname = year + SQLname;
		String month2 = month + "";
		String monthDay2 = monthDay + "";
		if (month < 10)
			month2 = "0" + month2;
		if (monthDay < 10)
			monthDay2 = "0" + monthDay2;
		date = year + "-" + month2 + "-" + monthDay2;
		// Table = Table + year;
		// showLog("���ڣ�" + date + "��" + SQLname + "��tableName��" + Table);
	}

	/**
	 * ����������ʹ������ѯ
	 * 
	 * @param sqlDataBase
	 *            sqlDataBase
	 * @param year
	 *            �����ѯ�����2000.
	 * @param month
	 *            �����ѯ���·�.
	 * @param monthDay
	 *            ���µ�����
	 * @param weekDay
	 *            ����
	 * @return ����һ�ľ�������ڸ�ʽ
	 */
	private String selectWeekStart(SQLiteDatabase sqlDataBase, int year,
			int month, int monthDay, int weekDay) {
		if (weekDay == 0) {
			weekDay = 6;
		} else {
			weekDay = weekDay - 1;
		}
		// showLog(weekDay + "");
		String weekStart = null;
		StringBuilder stringB = new StringBuilder();
		stringB.append("select date('now'").append(",'-").append(weekDay)
				.append(" day'" + ")");
		// Logs.d(TAG, stringB.toString());
		try {
			cur = sqlDataBase.rawQuery(stringB.toString(), null);
		} catch (Exception e) {
			Logs.d(TAG, stringB.toString() + "fail" + e);
		}
		if (cur != null) {
			try {
				int dateIndex = cur.getColumnIndex("date('now'" + ",'-"
						+ weekDay + " day'" + ")");
				if (cur.moveToFirst()) {
					weekStart = cur.getString(dateIndex);
				}
			} catch (Exception e) {
				Logs.d(TAG, "cur-searchfail" + e);
			}
		}
		if (cur != null) {
			cur.close();
		}
		// Logs.d(TAG, weekStart);
		return weekStart;
	}

	/**
	 * ����uid������
	 * 
	 * @param temp
	 *            �����DatauidHash ���ܲ����Ѿ���ֵ
	 * @param tpmobile
	 *            �ƶ�����
	 * @param tpwifi
	 *            wifi����
	 * @param flagFire
	 *            ��ʾ��ͳ���գ��ܣ���
	 * @return uid���ݼ��ϵ� DatauidHash
	 */
	private DatauidHash setUidData(DatauidHash temp, long[] tpmobile,
			long[] tpwifi, int flagFire) {
		switch (flagFire) {
		case 1:
			temp.setUploadmobileWeek(tpmobile[1]);
			temp.setDownloadmobileWeek(tpmobile[2]);
			temp.setUploadwifiWeek(tpwifi[1]);
			temp.setDownloadwifiWeek(tpwifi[2]);
			break;
		case 2:
			temp.setUploadmobile(tpmobile[1]);
			temp.setDownloadmobile(tpmobile[2]);
			temp.setUploadwifi(tpwifi[1]);
			temp.setDownloadwifi(tpwifi[2]);
			break;

		default:
			temp.setUploadmobileToday(tpmobile[1]);
			temp.setDownloadmobileToday(tpmobile[2]);
			temp.setUploadwifiToday(tpwifi[1]);
			temp.setDownloadwifiToday(tpwifi[2]);
			break;
		}
		return temp;
	}
}
