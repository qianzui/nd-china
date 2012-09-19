package com.hiapk.control.bootandclose;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.NotificationFireFailOnsysBoot;
import com.hiapk.control.widget.SetText;
import com.hiapk.firewall.Block;
import com.hiapk.spearhead.SpearheadApplication;
import com.hiapk.sqlhelper.pub.SQLHelperDataexe;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

public class Onsysreboot {
	boolean isNotifyOpen = true;
	boolean isFloatOpen = false;
	boolean isWidget1X4Open = true;
	private static final String ACTION_TIME_CHANGED = Intent.ACTION_TIME_CHANGED;
	Context context;
	AlarmSet alset = new AlarmSet();

	public void onsysreboot(Context context) {
		this.context = context;
		SharedPrefrenceDataWidget sharedDatawidget = new SharedPrefrenceDataWidget(
				context);
		// ��ʼ�������ź�
		SQLStatic.initTablemobileAndwifi(context);

		alset.StartAlarm(context);
		// �鿴���ݿ��Ƿ��ѳ�ʼ��
		if (SQLStatic.getIsInit(context)) {
			isNotifyOpen = sharedDatawidget.isNotifyOpen();
			isFloatOpen = sharedDatawidget.isFloatOpen();
			isWidget1X4Open = sharedDatawidget.isWidGet14Open();
			// showLog("isNotifyOpen"+isNotifyOpen);
			// showLog("isFloatOpen"+isFloatOpen);
			if (TrafficManager.mobile_month_use == 1) {
				if (SQLStatic.TableWiFiOrG23 == "") {
					SQLHelperDataexe.initShowDataOnBroadCast(context);
				} else
					new AsyncTaskonBoot().execute(context);
			} else {
				if (isFloatOpen) {
					context.startService(new Intent("com.hiapk.server"));
				} else {
					context.stopService(new Intent("com.hiapk.server"));
				}
				SetText.resetWidgetAndNotify(context);
			}

			// Intent intentTextUpdate = new Intent();
			// intentTextUpdate.setAction(BROADCAST_TRAFF);
			// context.sendBroadcast(intentTextUpdate);
			// Toast.makeText(context, "qidong", Toast.LENGTH_SHORT);
			showLog("xitongqidong");
		}
		// ��������ǽ
		boolean isOpenSucess = false;
		if (!Block.iptableEmpty(context)) {
			isOpenSucess = Block.applyIptablesRules(context, true, false);
			if (!isOpenSucess) {
				SpearheadApplication.getInstance().getsharedData()
						.setIsFireWallOpenFail(true);
				NotificationFireFailOnsysBoot openFireFail = new NotificationFireFailOnsysBoot(
						context);
				openFireFail.startNotifyDay(context, false);
			}
		}
		// ����������ù㲥
		context.sendBroadcast(new Intent(ACTION_TIME_CHANGED));

	}

	private class AsyncTaskonBoot extends AsyncTask<Context, Long, Long> {

		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while (TrafficManager.mobile_month_use == 1) {
				timetap++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 10) {
					break;
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			if (isFloatOpen) {
				context.startService(new Intent("com.hiapk.server"));
			} else {
				context.stopService(new Intent("com.hiapk.server"));
			}
			if (isNotifyOpen || isWidget1X4Open) {
				alset.StartWidgetAlarm(context);
			} else {
				alset.StopWidgetAlarm(context);
			}
		}
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("Onsysreboot", string);
	}
}
