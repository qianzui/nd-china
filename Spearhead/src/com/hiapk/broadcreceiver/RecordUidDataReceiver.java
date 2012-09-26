package com.hiapk.broadcreceiver;

import com.hiapk.logs.Logs;
import com.hiapk.sqlhelper.pub.SQLHelperCreateClose;
import com.hiapk.sqlhelper.uid.SQLHelperUidRecord;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataOnUpdate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

public class RecordUidDataReceiver extends BroadcastReceiver {
	public static final int MODE_PRIVATE = 0;
	// use database
	long time;
	private String network;
	private String TAG = "ReceiverUid";

	@Override
	public void onReceive(Context context, Intent intent) {
		showLog("isUidAlarmRecording=" + SQLStatic.isUidAlarmRecording);
		if (SQLStatic.isUidAlarmRecording == false) {
			SQLStatic.isUidAlarmRecording = true;
			SQLStatic.initTablemobileAndwifi(context);
			if (SQLStatic.TableWiFiOrG23 == "") {
				network = SQLStatic.TableWiFiOrG23Before;
			} else {
				network = SQLStatic.TableWiFiOrG23;
			}
			showLog("TableWiFiOrG23=" + SQLStatic.TableWiFiOrG23);
			showLog("network=" + network);
			if (SQLStatic.getIsInit(context)) {
				if (network != "") {
					// ����֮ǰʹ�õ������Ǻ�����������ж�
					// network = SQLHelperTotal.TableWiFiOrG23;
					if (SQLStatic.setSQLUidOnUsed(true)) {
						new AsyncTaskonRecordUidData().execute(context);
						// showLog(SQLHelperTotal.TableWiFiOrG23);
					} else {
						SQLStatic.isUidAlarmRecording = false;
						showLog("Uid���ݿ�æ");
					}

					// } else {
					// // �����������½������һ�μ�¼
					// if (SQLStatic.setSQLUidOnUsed(true)) {
					// new AsyncTaskonRecordUidData().execute(context);
					// // showLog(SQLHelperTotal.TableWiFiOrG23);
					// } else {
					// SQLStatic.setSQLUidOnUsed(false);
					// SQLStatic.isUidAlarmRecording = false;
					// showLog("Uid���ݿ�æ");
					// }
				} else {
					// 1.0.3���¶���������
					SharedPrefrenceDataOnUpdate sharedUpdate = new SharedPrefrenceDataOnUpdate(
							context);
					boolean isupdated = sharedUpdate.isUidRecordUpdated();
					if (isupdated == false) {
						if (SQLStatic.setSQLUidOnUsed(true)) {
							new AsyncTaskonRecordUidData().execute(context);
							// showLog(SQLHelperTotal.TableWiFiOrG23);
						} else {
							SQLStatic.isUidAlarmRecording = false;
							showLog("Uid���ݿ�æ");
						}
					} else
						SQLStatic.isUidAlarmRecording = false;
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
			SQLStatic.getuidsAndpacname(context);

		}
		if (SQLStatic.uidnumbers != null) {
			numbers = SQLStatic.uidnumbers;
		} else {
			return 0;
		}
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose.creatSQLUid(context);
		sqlDataBase.beginTransaction();
		try {
			// ��¼����
			SQLHelperUidRecord sqlhelperUidRecord = new SQLHelperUidRecord(
					context);
			sqlhelperUidRecord.RecordUidwritestats(context, sqlDataBase,
					numbers, false, network);
			// // // // ��ȡuid������������
			// SQLStatic.uiddata = sqlhelperUidFire.getSQLUidtraff(sqlDataBase,
			// numbers);

			sqlDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			showLog("��������uid��������ʧ��" + e);
		} finally {
			sqlDataBase.endTransaction();
		}
		SQLHelperCreateClose.closeSQL(sqlDataBase);

		return 1;
	}

	private class AsyncTaskonRecordUidData extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
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
		Logs.d(TAG, string);
	}

}
