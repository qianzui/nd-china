package com.hiapk.broadcreceiver;

import java.util.HashMap;

import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.pub.SQLStatic;
import com.hiapk.sqlhelper.total.SQLHelperFireWall.Data;
import com.hiapk.sqlhelper.uid.SQLHelperUidother;
import com.hiapk.sqlhelper.uid.SQLHelperUidRecord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class RecordUidDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	private SQLHelperUidRecord sqlhelperUidRecord = new SQLHelperUidRecord();
	long time;
	private String network;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (SQLStatic.isUidAlarmRecording == false) {
			SQLStatic.isUidAlarmRecording = true;
			// TODO Auto-generated method stub
			// showLog("TableWiFiOrG23=" + SQLHelperTotal.TableWiFiOrG23);
			if (SQLStatic.getIsInit(context)) {
				if (SQLStatic.TableWiFiOrG23 != "") {
					// ����֮ǰʹ�õ������Ǻ�����������ж�
					// network = SQLHelperTotal.TableWiFiOrG23;
					network = SQLStatic.TableWiFiOrG23;
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
					network = SQLStatic.TableWiFiOrG23;
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
			SQLStatic.uidnumbers = SQLStatic.selectUidnumbers(context);

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
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose.creatSQLUid(context);
		HashMap<Integer, Data> mp = null;
		sqlDataBase.beginTransaction();
		try {
			// ��¼����
			sqlhelperUidRecord.RecordUidwritestats(sqlDataBase, numbers, false,
					network);
			// ��ȡuid������������
			mp = sqlhelperUidRecord.getSQLUidtraff(sqlDataBase, numbers, network);

			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			showLog("��������uid��������ʧ��");
		} finally {
			sqlDataBase.endTransaction();
		}
		SQLHelperCreateClose.closeSQL(sqlDataBase);
		SQLStatic.uiddata = mp;

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
