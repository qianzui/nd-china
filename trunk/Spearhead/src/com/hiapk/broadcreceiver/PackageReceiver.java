package com.hiapk.broadcreceiver;

import com.hiapk.rebootandstartaction.Onreinstall;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// �������ݿ�
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		String packageNames = intent.getDataString();
		String packageName[] = packageNames.split(":");
		if (sqlhelperTotal.getIsInit(context)) {
			// �������İ�װ
			if (intent.getAction()
					.equals("android.intent.action.PACKAGE_ADDED")) {
				// ���Ϊ����װ
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					sqlhelperTotal.initTablemobileAndwifi(context,false);
				} else {
					// ���������װ
					// �������Ȩ��
					PackageManager pkgmanager = context.getPackageManager();
					if (PackageManager.PERMISSION_GRANTED != pkgmanager
							.checkPermission(Manifest.permission.INTERNET,
									packageName[1])) {
						// ������Ȩ��
						// showLog("û����Ȩ�޵İ�װ");
					} else {
						// ������Ȩ�޽��и��±��
						// showLog("������Ȩ�޵İ�װ");
						int uidnumber = 99999;
						try {
							uidnumber = context.getPackageManager()
									.getPackageInfo(packageName[1],
											getResultCode()).applicationInfo.uid;
						} catch (NameNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							showLog("��ȡ����Ϣʧ��");
						}
						if (uidnumber != 99999) {
							sqlhelperUid.updateSQLUidIndexOnInstall(context,
									uidnumber, packageName[1], "Install");
						}
					}
				}

			}
			// ��������ж��
			if (intent.getAction().equals(
					"android.intent.action.PACKAGE_REMOVED")) {
				if (packageNames.equals("package:com.hiapk.spearhead")) {
					Onreinstall reinstall = new Onreinstall();
					reinstall.reInstallAction(context);
					// showLog("����װ");
					showLog("ж��" + packageName[1]);
				} else {
					SQLiteDatabase mySQL = sqlhelperUid
							.creatSQLUidIndex(context);
					sqlhelperUid.updateSQLUidIndexOtherOnUnInstall(mySQL,
							packageName[1], "UnInstall");
					sqlhelperUid.closeSQL(mySQL);
					showLog("����ж��" + packageName[1]);
				}

			}
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("Receiver", string);
	}
}
