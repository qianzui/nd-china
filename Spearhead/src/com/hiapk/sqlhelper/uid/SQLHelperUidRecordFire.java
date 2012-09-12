package com.hiapk.sqlhelper.uid;

import java.util.HashMap;
import java.util.List;

import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall.Data;
import com.hiapk.util.SQLStatic;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLHelperUidRecordFire {
	private ActivityManager mActivityManager = null;
	// log
	private String TAG = "databaseUidRecord";

	public SQLHelperUidRecordFire(Context context) {
		super();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
	}

	private String SelectTable = "SELECT * FROM ";
	private String Where = " WHERE ";
	private Cursor cur;
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";

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
	public HashMap<Integer, Data> getSQLUidtraff(SQLiteDatabase sqlDataBase,
			int[] uidnumbers) {
		HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
		mp = SelectUiddownloadAnduploadPart(sqlDataBase, uidnumbers);
		return mp;
	}

	private HashMap<Integer, Data> SelectUiddownloadAnduploadPart(
			SQLiteDatabase sqlDataBase, int[] uidnumbers) {
		HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
		if (SQLStatic.uiddata != null) {
			mp = SQLStatic.uiddata;

			List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
					.getRunningAppProcesses();
			for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
				// ͨ��uidnumber�ж��Ƿ�Ϊ��Ҫ��¼��Ӧ��
				String pacname = appProcessInfo.processName;
				int uidnumber = appProcessInfo.uid;
				if (SQLStatic.packagename_ALL.contains(pacname)) {
					long[] tpmobile = new long[3];
					long[] tpwifi = new long[3];
					tpmobile = getSQLuidtotalData(sqlDataBase, uidnumber,
							NETWORK_FLAG);
					tpwifi = getSQLuidtotalData(sqlDataBase, uidnumber, "wifi");
					Data temp = new Data();
					temp.upload = tpmobile[1] + tpwifi[1];
					temp.download = tpmobile[2] + tpwifi[2];
					mp.put(uidnumber, temp);
					// showLog(uidnumber[i]+"traff"+get[1]+"");
				}
			}
		} else {
			mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers);
		}
		return mp;
	}

	private HashMap<Integer, Data> SelectUiddownloadAnduploadAll(
			SQLiteDatabase sqlDataBase, int[] uidnumbers) {

		HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
		long[] tpmobile = new long[3];
		long[] tpwifi = new long[3];
		for (int i = 0; i < uidnumbers.length; i++) {
			if (uidnumbers[i] != -1) {

				tpmobile = getSQLuidtotalData(sqlDataBase, uidnumbers[i],
						NETWORK_FLAG);
				tpwifi = getSQLuidtotalData(sqlDataBase, uidnumbers[i], "wifi");
				Data temp = new Data();
				temp.upload = tpmobile[1] + tpwifi[1];
				temp.download = tpmobile[2] + tpwifi[2];
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
					// ���֮ǰ���ϴ�����ֵ
					a[1] = cur.getLong(minup);
					a[2] = cur.getLong(mindown);
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

}
