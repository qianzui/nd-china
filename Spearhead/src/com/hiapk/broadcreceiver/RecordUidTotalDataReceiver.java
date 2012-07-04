package com.hiapk.broadcreceiver;

import java.util.HashMap;

import com.hiapk.sqlhelper.SQLHelperFireWall.Data;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;
import com.hiapk.sqlhelper.SQLStatic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.util.Log;

public class RecordUidTotalDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	SQLHelperUid sqlhelperUid = new SQLHelperUid();
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	long time;
	private String network;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (SQLStatic.isUidTotalAlarmRecording == false) {
			showLog(SQLStatic.isUidTotalAlarmRecording + "-before");
			SQLStatic.isUidTotalAlarmRecording = true;
			time = System.currentTimeMillis();
			// TODO Auto-generated method stub
			// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
			if (sqlhelperTotal.getIsInit(context)) {
				if (SQLHelperTotal.TableWiFiOrG23 != "") {
					// ����֮ǰʹ�õ������Ǻ�����������ж�
					// network = SQLHelperTotal.TableWiFiOrG23;
					network = SQLHelperTotal.TableWiFiOrG23;
					if (SQLStatic.setSQLUidTotalOnUsed(true)) {
						new AsyncTaskonRecordUidTotal().execute(context);
					} else {
						SQLStatic.setSQLUidTotalOnUsed(false);
						SQLStatic.isUidTotalAlarmRecording = false;
						time = System.currentTimeMillis() - time;
						showLog("fail1" + time + "ms");
						showLog("UidTotal���ݿ�æ");
					}

				} else {
					// �����������½������һ�μ�¼
					network = SQLHelperTotal.TableWiFiOrG23;
					// �����������ݵļ�¼
					if (SQLStatic.setSQLUidTotalOnUsed(true)) {
						new AsyncTaskonRecordUidTotal().execute(context);
					} else {
						SQLStatic.setSQLUidTotalOnUsed(false);
						SQLStatic.isUidTotalAlarmRecording = false;
						showLog("fail2" + time + "ms");
						showLog("UidTotal���ݿ�æ");
					}

				}
			} else {
				// sqlhelper.initSQL(context);
				showLog("please init the database");
				SQLStatic.isUidTotalAlarmRecording = false;
			}
		}
	}

	private class AsyncTaskonRecordUidTotal extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// SQLHelperTotal.isSQLUidTotalOnUsed = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			int[] numbers = null;
			if (SQLStatic.uidnumbers == null) {
				// ���¶��徲̬��uid����
				SQLStatic.uidnumbers = sqlhelperUid.selectUidnumbers(params[0]);
				// SQLHelperTotal.isSQLIndexOnUsed = false;
				// }

			}
			if (SQLStatic.uidnumbers != null) {
				numbers = SQLStatic.uidnumbers;
			} else {
				return 0;
			}
			// HashMap<Integer, Data> mp = null;
			SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
			SQLiteDatabase sqlDataBase = sqlUidTotal
					.creatSQLUidTotal(params[0]);
			sqlDataBase.beginTransaction();
			boolean success = true;
			try {
				// mp = sqlUidTotal.updateSQLUidTypes(sqlDataBase, numbers,
				// network);
				sqlDataBase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				showLog("������ȡuid����-Context");
				success = false;
			} finally {
				sqlDataBase.endTransaction();
			}
			sqlUidTotal.closeSQL(sqlDataBase);
			showLog("success=" + success);
			if (success) {
				// SQLStatic.uiddata = mp;
			}

			// sqlUidTotal.updateSQLUidTypes(params[0],
			// SQLStatic.uidnumbers,
			// network);

			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			if (result == 1) {
				showLog("uidTotal���³ɹ�");
				time = System.currentTimeMillis() - time;
				showLog("success" + time + "ms");
				// SQLHelperTotal.isSQLUidTotalOnUsed = false;
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
			if (result == 0) {
				showLog("uidTotal����ʧ����uidindex");
				// SQLHelperTotal.isSQLUidTotalOnUsed = false;
				time = System.currentTimeMillis() - time;
				showLog("��uidindex" + time + "ms");
				SQLStatic.setSQLUidTotalOnUsed(false);
			}
			SQLStatic.isUidTotalAlarmRecording = false;
			// if (result == 3) {
			// showLog("uidTotalUnknow");
			// }
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("ReceiverUidTotal", string);
	}

}