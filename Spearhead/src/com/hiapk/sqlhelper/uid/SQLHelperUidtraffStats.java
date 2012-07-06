package com.hiapk.sqlhelper.uid;

import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * ����������������ȡͳ������
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
	 * ��ȡpie�õ�uid������
	 * 
	 * @param context
	 * @param uidnumber
	 * @return a[0]������mobile��1��2����mobile�ϴ�������a[5]������wifi��3��4����wifi�ϴ�������
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
			showLog("uidstraffstats" + "error" + string);
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
				uid, other);
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
			int month, String table, int uid, String other) {
		long[] a = new long[64];
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose.creatSQLUid(context);
		String string = null;
		// select oldest upload and download ֮ǰ��¼�����ݵĲ�ѯ����
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
			// ����ʧ�����½���
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
	 * ������ʾ��־
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		Log.d("UidstraffStats", string);
	}
}
