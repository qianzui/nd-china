package com.hiapk.broadcreceiver;

import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootAndShutdownBroadcast extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		// �������������ݿ����
		AlarmSet alset = new AlarmSet();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLHelperUid sqlhelperUid = new SQLHelperUid();
		// ����IsInit��Ϣ
		sqlhelperTotal.getIsInit(context);

		// ʶ�𵽿����ź�
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			// ��ʼ�������ź�
			sqlhelperTotal.initTablemobileAndwifi(context);
			// �鿴���ݿ��Ƿ��ѳ�ʼ��

			if (sqlhelperTotal.getIsInit(context)) {
				// ��������
				alset.StartAlarm(context);
				// ������������uid�Զ���¼���ܲ������״μ�¼
				sqlhelperTotal.RecordTotalwritestats(context, false);
				sqlhelperUid.RecordUidwritestats(context, false);
				showLog(sqlhelperTotal.gettime() + "onboot the system");
			}
		}
		// ʶ�𵽹ػ��ź�
		if (intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
			// �ر�wifi�Զ���¼����¼��ǰ����
			alset.StopAlarm(context);
			if (sqlhelperTotal.getIsInit(context)) {
				if (SQLHelperTotal.TableWiFiOrG23 != "") {
					sqlhelperTotal.RecordTotalwritestats(context, false);
					sqlhelperUid.RecordUidwritestats(context, false);
				}
			}
			showLog(sqlhelperTotal.gettime() + "shutdown the system");
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("database", string);
	}
}
