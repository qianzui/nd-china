package com.hiapk.sqlhelper.uid;

import java.util.HashMap;
import java.util.List;

import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.uid.SQLHelperFireWall.Data;

import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLHelperUidRecordAll {
	private ActivityManager mActivityManager = null;

	public SQLHelperUidRecordAll(Context context) {
		super();
		// initTime();
		mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		// TODO Auto-generated constructor stub
	}

	private String SelectTable = "SELECT * FROM ";
	private String Where = " WHERE ";
	private Cursor cur;
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";

//	/**
//	 * 对数据库uid数据进行批量更新，
//	 * 
//	 * @param sqlDataBase
//	 *            进行操作的数据库SQLiteDatagase
//	 * @param uidnumbers
//	 *            数据库的表数组集合：uid的table表的uid号，
//	 * @param type
//	 *            用于记录数据状态，以统计数据
//	 * @param other
//	 *            用于记录特殊数据等
//	 * @param typechange
//	 *            改变type值
//	 */
//	public HashMap<Integer, Data> initSQLUidtraff(SQLiteDatabase sqlDataBase,
//			int[] uidnumbers) {
//		HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
//		mp = SelectUiddownloadAnduploadAll(sqlDataBase, uidnumbers);
//		return mp;
//	}

	/**
	 * 对数据库uid数据进行批量更新，
	 * 
	 * @param sqlDataBase
	 *            进行操作的数据库SQLiteDatagase
	 * @param uidnumbers
	 *            数据库的表数组集合：uid的table表的uid号，
	 * @param type
	 *            用于记录数据状态，以统计数据
	 * @param other
	 *            用于记录特殊数据等
	 * @param typechange
	 *            改变type值
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
				// 通过uidnumber判断是否为需要记录的应用
				int uidnumber = appProcessInfo.uid;
				if (isIn(uidnumber, uidnumbers)) {
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
		// while (SQLStatic.setSQLUidOnUsed(true)) {
		// try {
		// Thread.sleep(100);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		HashMap<Integer, Data> mp = new HashMap<Integer, Data>();
		long[] tpmobile = new long[3];
		long[] tpwifi = new long[3];
		for (int i = 0; i < uidnumbers.length; i++) {
			tpmobile = getSQLuidtotalData(sqlDataBase, uidnumbers[i],
					NETWORK_FLAG);
			tpwifi = getSQLuidtotalData(sqlDataBase, uidnumbers[i], "wifi");
			Data temp = new Data();
			temp.upload = tpmobile[1] + tpwifi[1];
			temp.download = tpmobile[2] + tpwifi[2];
			mp.put(uidnumbers[i], temp);
			// showLog(uidnumber[i]+"traff"+get[1]+"");
		}
		// SQLStatic.setSQLUidOnUsed(false);
		return mp;
	}

	/**
	 * 依据网络状态选择对应的之前流量。
	 * 
	 * @param mySQL
	 * @param uidnumber
	 * @param network
	 * @return a[0]为总计流量a[1]总计上传流量a[2]总计下载流量
	 */
	private long[] getSQLuidtotalData(SQLiteDatabase mySQL, int uidnumber,
			String network) {
		String string = null;
		if (network == NETWORK_FLAG) {
			// select oldest upload and download 之前记录的数据的查询操作
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
					// 获得之前的上传下载值
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

	private boolean isIn(int subnumber, int[] source) {
		if (source == null || source.length == 0) {
			return false;
		}
		for (int i = 0; i < source.length; i++) {
			if (source[i] == subnumber) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("databaseUidRecord", string);
	}
}
