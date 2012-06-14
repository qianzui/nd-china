package com.hiapk.broadcreceiver;

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

public class RecordUidDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	SQLHelperUid sqlhelperUid = new SQLHelperUid();
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	long time;
	private String network;

	@Override
	public void onReceive(Context context, Intent intent) {
		SQLStatic.isUidAlarmRecording = true;
		// TODO Auto-generated method stub
		// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
		if (sqlhelperTotal.getIsInit(context)) {
			if (SQLHelperTotal.TableWiFiOrG23 != "") {
				// ����֮ǰʹ�õ������Ǻ�����������ж�
				// network = SQLHelperTotal.TableWiFiOrG23;
				network = SQLHelperTotal.TableWiFiOrG23;
				// �����������ݵļ�¼
				if (SQLStatic.setSQLUidOnUsed(true)) {
					new AsyncTaskonRecordUidData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else {
					SQLStatic.setSQLUidOnUsed(false);
					SQLStatic.isUidAlarmRecording = false;
					showLog("Uid���ݿ�æ");
				}

			} else {
				// �����������½������һ�μ�¼
				network = SQLHelperTotal.TableWiFiOrG23;
				// �����������ݵļ�¼
				if (SQLStatic.setSQLUidOnUsed(true)) {
					new AsyncTaskonRecordUidData().execute(context);
					// showLog(SQLHelperTotal.TableWiFiOrG23);
				} else {
					SQLStatic.setSQLUidOnUsed(false);
					SQLStatic.isUidAlarmRecording = false;
					showLog("Uid���ݿ�æ");
				}

			}
		} else {
			// sqlhelper.initSQL(context);
			showLog("please init the database");
			SQLStatic.isUidAlarmRecording = false;
		}
	}

	/**
	 * ��������
	 * 
	 * @param context
	 */
	private int uidRecord(Context context) {
		// ʵʱ������������1�������ݸ���
		// showLog(SQLHelperTotal.isSQLIndexOnUsed+"");
		int[] numbers = null;
		if (SQLStatic.uidnumbers == null) {
			// ���¶��徲̬��uid����
			SQLStatic.isuidnumbersOperating=true;
			SQLStatic.uidnumbers=sqlhelperUid.selectUidnumbers(context);
			
			// if (SQLHelperTotal.isSQLIndexOnUsed == false) {
			// SQLHelperTotal.isSQLIndexOnUsed = true;
			// if (SQLStatic.setSQLIndexOnUsed(true)) {
			// SQLHelperUid.uidnumbers = sqlhelperUid
			// .selectSQLUidnumbers(context);
			// SQLStatic.setSQLIndexOnUsed(false);
			// }
			// SQLHelperTotal.isSQLIndexOnUsed = false;
			// }

		}
		if (SQLStatic.uidnumbers != null) {
			numbers = SQLStatic.uidnumbers;
		} else {
			return 0;
		}
		SQLiteDatabase sqlDataBase = sqlhelperUid.creatSQLUid(context);
		sqlDataBase.beginTransaction();
		try {
			// ��������
			sqlhelperUid.updateSQLUidTypes(sqlDataBase, numbers, 1, network, 1);
			// ��¼����
			sqlhelperUid.RecordUidwritestats(sqlDataBase, numbers, false,
					network);
			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��������uid��������ʧ��");
		} finally {
			sqlDataBase.endTransaction();
		}
		sqlhelperUid.closeSQL(sqlDataBase);
		return 1;
	}

	private class AsyncTaskonRecordUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			time = System.currentTimeMillis();
			// SQLHelperTotal.isSQLUidOnUsed = true;

		}

		@Override
		protected Integer doInBackground(Context... params) {
			return uidRecord(params[0]);
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			// SQLHelperTotal.isSQLUidOnUsed = false;
			SQLStatic.setSQLUidOnUsed(false);
			SQLStatic.isUidAlarmRecording = false;
			time = System.currentTimeMillis() - time;
			showLog("���¼�¼���" + time);
			if (result == 0) {
				showLog("��ȡuid��ʧ��");
			}
			if (result == 1) {
				showLog("uid���ݸ���");
			}

		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("ReceiverUid", string);
	}

}
