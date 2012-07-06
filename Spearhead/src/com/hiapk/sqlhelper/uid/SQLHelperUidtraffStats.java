package com.hiapk.sqlhelper.uid;

import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 用于流量详情界面获取统计数据
 * 
 * @author Administrator
 * 
 */
public class SQLHelperUidtraffStats {

	public SQLHelperUidtraffStats() {
		super();
		// initTime();
	}

	private String TableUid = "uid";
	private String SelectTable = "SELECT * FROM ";
	private String Where = " WHERE ";
	private String Between = " BETWEEN '";
	private String AND_B = "' AND '";
	private String AND = "' AND ";
	private Cursor cur;
	// flag-network-onuidtraff
	private String NETWORK_FLAG = "mobile";

	/**
	 * 获取pie用的uid总流量
	 * 
	 * @param context
	 * @param uidnumber
	 * @return a[0]代表总mobile，1，2代表mobile上传，下载a[5]代表总wifi，3，4代表wifi上传，下载
	 */
	public long[] getSQLuidPiedata(Context context, int uidnumber) {
		long[] tpmobile = new long[3];
		long[] tpwifi = new long[3];
		long[] a = new long[6];
		SQLiteDatabase mySQL = SQLHelperCreateClose.creatSQLUid(context);
		tpmobile = getSQLuidtotalData(mySQL, uidnumber, NETWORK_FLAG);
		a[0] = tpmobile[0];
		a[1] = tpmobile[1];
		a[2] = tpmobile[2];
		tpwifi = getSQLuidtotalData(mySQL, uidnumber, "wifi");
		a[5] = tpwifi[0];
		a[3] = tpwifi[1];
		a[4] = tpwifi[2];
		SQLHelperCreateClose.closeSQL(mySQL);
		return a;
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
			showLog("uidstraffstats" + "error" + string);
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
				showLog("uidstraffstats-cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		a[0] = a[1] + a[2];
		return a;
	}

	/**
	 * 进行uid历史流量查询包括wifi与mobile
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param uid
	 *            输入查询的uid号
	 * @param other
	 *            要查询的网络类型"wifi"or"mobile"
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] SelectuidWifiorMobileData(Context context, int year,
			int month, int uid, String other) {
		return SelectUidmobileOrwifiData(context, year, month, TableUid + uid,
				uid, other);
	}

	/**
	 * 进行数据流量历史流量查询
	 * 
	 * @param context
	 *            context
	 * @param year
	 *            输入查询的年份2000.
	 * @param month
	 *            输入查询的月份.
	 * @param table
	 *            要查询的数据类型
	 * @param other
	 *            要查询的网络类型"wifi"or"mobile"
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	private long[] SelectUidmobileOrwifiData(Context context, int year,
			int month, String table, int uid, String other) {
		long[] a = new long[64];
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose.creatSQLUid(context);
		String string = null;
		// select oldest upload and download 之前记录的数据的查询操作
		// SELECT * FROM table WHERE type=0
		String month2 = month + "";
		if (month < 10)
			month2 = "0" + month2;
		string = SelectTable + table + Where + "date" + Between + year + "-"
				+ month2 + "-" + "01" + AND_B + year + "-" + month2 + "-"
				+ "31" + AND + "other=" + "'" + other + AND + "type=" + 2;
		try {
			cur = sqlDataBase.rawQuery(string, null);
		} catch (Exception e) {
			// 搜索失败则新建表
			SQLHelperUidSelectFail uidselectFail = new SQLHelperUidSelectFail();
			uidselectFail.selectfails(sqlDataBase, table, uid);
			cur = null;
			showLog("selectfail" + string);

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
								// 用于跨越天数
								i++;
								if (i < 10)
									countdate = dateStr1 + i;
								else
									countdate = dateStr2 + i;
								if (i > 31) {
									// 如果天数顺序不正确，进行恢复
									for (int j = 1; j < 32; j++) {
										if (j < 10)
											countdate = dateStr1 + j;
										else
											countdate = dateStr2 + j;
										if (newdate.equals(countdate)) {
											i = j;
											break;
										}
									}
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
				showLog("uidstraffstats-cur-searchfail");
			}
		}
		if (cur != null) {
			cur.close();
		}
		SQLHelperCreateClose.closeSQL(sqlDataBase);
		// for (int j = 0; j < a.length; j++) {
		// showLog(j + "liuliang" + a[j] + "");
		// }
		return a;
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("UidstraffStats", string);
	}
}
