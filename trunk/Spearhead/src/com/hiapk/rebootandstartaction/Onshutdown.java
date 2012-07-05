package com.hiapk.rebootandstartaction;

import android.content.Context;
import android.content.Intent;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.sqlhelper.SQLStatic;

public class Onshutdown {
	public void onsysshutdown(Context context) {
		AlarmSet alset = new AlarmSet();
		// �鿴���ݿ��Ƿ��ѳ�ʼ��
		// �ر�wifi�Զ���¼����¼��ǰ����
		if (SQLStatic.getIsInit(context)) {
			if (SQLStatic.TableWiFiOrG23 != "") {
				alset.StartAlarm(context);
				// trafficManager.statsTotalTraffic(context, true,
				// SQLHelperTotal.TableWiFiOrG23);
				// // sqlhelperTotal.RecordTotalwritestats(context, false);
				// trafficManager.statsUidTraffic(context, true,
				// SQLHelperTotal.TableWiFiOrG23);
			}
		}
		alset.StopAlarm(context);
		context.stopService(new Intent("com.hiapk.server"));
		alset.StopWidgetAlarm(context);
	}

//	private void showLog(String string) {
//		// TODO Auto-generated method stub
////		Log.d("Onshutdown", string);
//	}
}
