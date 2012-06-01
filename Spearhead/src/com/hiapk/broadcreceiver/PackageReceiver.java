package com.hiapk.broadcreceiver;

import java.util.LinkedList;
import java.util.List;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.rebootandstartaction.Onreinstall;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;
import com.hiapk.sqlhelper.SQLStatic;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// �������ݿ�
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		String packageNames = intent.getDataString();
		SQLStatic.packageName = packageNames.split(":");
		if (sqlhelperTotal.getIsInit(context)) {
			// ��������ж��
			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					Onreinstall reinstall = new Onreinstall();
					reinstall.reInstallAction(context);
					showLog("ж��" + SQLStatic.packageName[1]);
				} else {
					new AsyTaskOnUninstall().execute(context);
					showLog("����ж��" + SQLStatic.packageName[1]);
				}

			}
			// �������İ�װ
			if (intent.getAction()
					.equals("android.intent.action.PACKAGE_ADDED")) {
				// ���Ϊ����װ
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					sqlhelperTotal.initTablemobileAndwifi(context, false);
				} else {
					// ���������װ
					// �������Ȩ��
					PackageManager pkgmanager = context.getPackageManager();
					if (PackageManager.PERMISSION_GRANTED != pkgmanager
							.checkPermission(Manifest.permission.INTERNET,
									SQLStatic.packageName[1])) {
						// ������Ȩ��
						showLog("û����Ȩ�޵İ�װ");
					} else {
						// ������Ȩ�޽��и��±��
						showLog("������Ȩ�޵İ�װ");
						SQLStatic.uidnumber = 999999;
						try {
							SQLStatic.uidnumber = context.getPackageManager()
									.getPackageInfo(SQLStatic.packageName[1],
											getResultCode()).applicationInfo.uid;
						} catch (NameNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							showLog("��ȡ����Ϣʧ��");
						}
						if (SQLStatic.uidnumber != 999999) {
							showLog("��װ" + SQLStatic.packageName[1]
									+ SQLStatic.uidnumber);
							SQLHelperUid sqlhelperUid = new SQLHelperUid();
							// �ж��Ƿ����°�װ����
							int[] uids = sqlhelperUid.updateSQLUidOnInstall(
									context, SQLStatic.uidnumber,
									SQLStatic.packageName[1], "Install");
							// ���ݷ���ֵ�������uid�����
							List<Integer> uid_List_Add = new LinkedList<Integer>();
							List<Integer> uid_List_Del = new LinkedList<Integer>();
							if (uids == null) {
								showLog("N���ǰ�װ");
							}
							if (uids != null) {
								showLog("Nuids!=null");
								while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
								}
								// list���ǿռ�����������
								showLog("building list");
								SQLHelperUidTotal sqlhelperUidTotal = new SQLHelperUidTotal();
								uid_List_Add = sqlhelperUidTotal
										.updateSQLUidTotalOnInstallgetAdd(
												context, SQLStatic.uidnumber,
												SQLStatic.packageName[1],
												"Install", uids);
								uid_List_Del = sqlhelperUidTotal
										.updateSQLUidTotalOnInstallgetDel(
												context, SQLStatic.uidnumber,
												SQLStatic.packageName[1],
												"Install", uids);
							}
							if ((uid_List_Add != null)
									|| (uid_List_Del != null)) {
								if (uid_List_Add != null) {
									// showLog("N�°�װ���");
								}
								if (uid_List_Del != null) {
									// showLog("N�����Ҫɾ��");
								}
								while (!SQLStatic.setSQLUidOnUsed(true)) {
								}
								sqlhelperUid.updateSQLUidOnInstall(context,
										SQLStatic.uidnumber,
										SQLStatic.packageName[1], "Install",
										uid_List_Add, uid_List_Del);
							}
							// new AsyTaskOnInstall().execute(context);
						}
					}
				}

			}
		}

	}

	private class AsyTaskOnUninstall extends
			AsyncTask<Context, Integer, Integer> {
		// private int uid;
		// private String pacName;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// this.uid = SQLStatic.uidnumber;
			// this.pacName = SQLStatic.packageName[1];
			// showLog("����ж��exeIndexPre" + packageName[1]);
		}

		@Override
		protected Integer doInBackground(Context... params) {
			// TODO Auto-generated method stub
			SQLHelperUid sqlhelperUid = new SQLHelperUid();
			// while (!SQLStatic.setSQLIndexOnUsed(true)) {
			// publishProgress(1);
			// publishProgress(1);
			// try {
			// Thread.sleep(50 + (long) (200 * Math.random()));
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			// publishProgress(0);
			// SQLiteDatabase mySQL = sqlhelperUid.creatSQLUidIndex(params[0]);
			// sqlhelperUid.updateSQLUidIndexOtherOnUnInstall(mySQL,
			// SQLStatic.packageName[1], "UnInstall");
			// ���¶��徲̬��uid����
			SQLStatic.uidnumbers = sqlhelperUid.selectUidnumbers(params[0]);
			// sqlhelperUid.closeSQL(mySQL);

			return null;
		}

		// @Override
		// protected void onProgressUpdate(Integer... values) {
		// // TODO Auto-generated method stub
		// super.onProgressUpdate(values);
		// if (values[0] == 0) {
		// showLog("����ж��outwhile" + pacName);
		// }
		// if (values[0] == 1) {
		// showLog("����ж��inwhile" + pacName);
		// }
		//
		// // if (values[0] == 1) {
		// // if (SQLHelperTotal.isSQLIndexOnUsed == false) {
		// // SQLHelperTotal.isSQLIndexOnUsed = true;
		// // isUidIndexSQLonUse_AsyUninstall = true;
		// // }
		// // }
		// }

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// SQLHelperTotal.isSQLIndexOnUsed = false;
			// isUidIndexSQLonUse_AsyUninstall = false;
			// showLog("����ж��post" + SQLStatic.isSQLIndexOnUsed);
			// SQLStatic.setSQLIndexOnUsed(false);
			// showLog("����ж��post" + SQLStatic.isSQLIndexOnUsed);
			// showLog("����ж��exeIndexpost" + packageName[1]);
		}
	}

	private class AsyTaskOnInstall extends AsyncTask<Context, Integer, Integer> {
		// private int uid;
		// private String pacName;

		// private boolean isUidIndexSQLonUse_Asy = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// this.uid = SQLStatic.uidnumber;
			// this.pacName = SQLStatic.packageName[1];
			// isUidIndexSQLonUse_Asy = false;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			return null;
		}

		// @Override
		// protected void onProgressUpdate(Integer... values) {
		// // TODO Auto-generated method stub
		// super.onProgressUpdate(values);
		// if (values[0] == 1) {
		// if (SQLHelperTotal.isSQLIndexOnUsed == false
		// && SQLHelperTotal.isSQLUidOnUsed == false
		// && SQLHelperTotal.isSQLUidTotalOnUsed == false) {
		// SQLHelperTotal.isSQLIndexOnUsed = true;
		// SQLHelperTotal.isSQLUidOnUsed = true;
		// SQLHelperTotal.isSQLUidTotalOnUsed = true;
		// isUidIndexSQLonUse_Asy = true;
		// }
		// }
		// }

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// SQLHelperTotal.isSQLIndexOnUsed = false;
			// SQLHelperTotal.isSQLUidOnUsed = false;
			// SQLHelperTotal.isSQLUidTotalOnUsed = false;
			// isUidIndexSQLonUse_Asy = false;
			SQLStatic.setSQLUidOnUsed(false);
			SQLStatic.setSQLIndexOnUsed(false);
			SQLStatic.setSQLUidTotalOnUsed(false);
		}
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
