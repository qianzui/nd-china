package com.hiapk.broadcreceiver;

import java.util.LinkedList;
import java.util.List;

import com.hiapk.control.bootandclose.OnUninstallitself;
import com.hiapk.sqlhelper.uid.SQLHelperUidother;
import com.hiapk.sqlhelper.uid.SQLHelperUidTotal;
import com.hiapk.util.SQLStatic;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// �������ݿ�
		String packageNames = intent.getDataString();
		SQLStatic.packageName = packageNames.split(":");
		if (SQLStatic.getIsInit(context)) {
			// ��������ж��
			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					OnUninstallitself uninstall = new OnUninstallitself();
					uninstall.unInstallAction(context);
					showLog("ж��" + SQLStatic.packageName[1]);
				} else {
					// new AsyTaskOnUninstall().execute(context);
					SQLStatic.getuidsAndpacname(context);
					showLog("����ж��" + SQLStatic.packageName[1]);
				}

			}
			// �������İ�װ
			if (intent.getAction()
					.equals("android.intent.action.PACKAGE_ADDED")) {
				// ���Ϊ����װ
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					// OnReinstallitself reInstall = new OnReinstallitself();
					// reInstall.reInstallAction(context);
					showLog("��װ" + SQLStatic.packageName[1]);
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
						SQLStatic.initTablemobileAndwifi(context);
						// ������Ȩ�޽��и��±��
						showLog("������Ȩ�޵İ�װ");
						new AsyTaskOnInstall().execute(context);

					}
				}

			}
		}

	}

	private class AsyTaskOnInstall extends AsyncTask<Context, Integer, Integer> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// this.uid = SQLStatic.uidnumber;
			// this.pacName = SQLStatic.packageName[1];
			// isUidIndexSQLonUse_Asy = false;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			SQLStatic.uidnumber = 999999;
			try {
				SQLStatic.uidnumber = params[0].getPackageManager()
						.getPackageInfo(SQLStatic.packageName[1],
								getResultCode()).applicationInfo.uid;
			} catch (NameNotFoundException e1) {
				e1.printStackTrace();
				showLog("��ȡ����Ϣʧ��");
			}
			if (SQLStatic.uidnumber != 999999) {
				showLog("��װ" + SQLStatic.packageName[1] + SQLStatic.uidnumber);
				SQLHelperUidother sqlhelperUid = new SQLHelperUidother();
				// �ж��Ƿ����°�װ����
				int[] uids = sqlhelperUid.updateSQLUidOnInstall(params[0],
						SQLStatic.uidnumber, SQLStatic.packageName[1],
						"Install");
				// ���ݷ���ֵ�������uid�����
				List<Integer> uid_List_Add = new LinkedList<Integer>();
				List<Integer> uid_List_Del = new LinkedList<Integer>();
				if (uids == null) {
					showLog("N���ǰ�װ");
				}
				if (uids != null) {
					showLog("Nuids!=null");
					while (!SQLStatic.setSQLUidTotalOnUsed(true)) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// list���ǿռ�����������
					showLog("building list");
					SQLHelperUidTotal sqlhelperUidTotal = new SQLHelperUidTotal();
					uid_List_Add = sqlhelperUidTotal
							.updateSQLUidTotalOnInstallgetAdd(params[0],
									SQLStatic.uidnumber,
									SQLStatic.packageName[1], "Install", uids);
					uid_List_Del = sqlhelperUidTotal
							.updateSQLUidTotalOnInstallgetDel(params[0],
									SQLStatic.uidnumber,
									SQLStatic.packageName[1], "Install", uids);
				}
				SQLStatic.setSQLUidTotalOnUsed(false);
				if ((uid_List_Add != null) || (uid_List_Del != null)) {
					if (uid_List_Add != null) {
						// showLog("N�°�װ���");
					}
					if (uid_List_Del != null) {
						// showLog("N�����Ҫɾ��");
					}
					while (!SQLStatic.setSQLUidOnUsed(true)) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					sqlhelperUid.updateSQLUidOnInstall(params[0],
							SQLStatic.uidnumber, SQLStatic.packageName[1],
							"Install", uid_List_Add, uid_List_Del);
				}
				SQLStatic.setSQLUidOnUsed(false);
				// new AsyTaskOnInstall().execute(context);
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
		}
	}

	private void showLog(String string) {
		// Log.d("Receiver", string);
	}
}
