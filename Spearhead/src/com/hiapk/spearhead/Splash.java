package com.hiapk.spearhead;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.firewall.GetRoot;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class Splash extends Activity {
	private Context context = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		// ��ȡrootȨ��
		GetRoot gr = new GetRoot();
		// gr.cmdRoot("chmod 777 " + getPackageCodePath());
		gr.hasRootAccess(context, true);
		new AsyncTaskonResume().execute(context);
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			SQLHelperTotal.isSQLOnUsed = true;
		}

		@Override
		protected Integer doInBackground(Context... params) {
			AlarmSet alset = new AlarmSet();
			// ��ʼ������״̬
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			sqlhelperTotal.initTablemobileAndwifi(params[0]);
			if (SQLHelperTotal.TableWiFiOrG23 != ""
					&& sqlhelperTotal.getIsInit(params[0])) {
				// ��������
				alset.StartAlarm(params[0]);
				return 1;

			} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
				alset.StartAlarm(params[0]);
				return 2;

			}
			return 3;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			SQLHelperUid sqlhelperUid = new SQLHelperUid();
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			if (result == 1) {
				// �������ݼ�¼
				sqlhelperUid.RecordUidwritestats(context, false);
				sqlhelperTotal.RecordTotalwritestats(context, false);
			} else {
				// �������ݼ�¼
				sqlhelperTotal.initTablemobileAndwifi(context);
			}
			SQLHelperTotal.isSQLOnUsed = false;
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			mainIntent.putExtras(choosetab);
			Splash.this.startActivity(mainIntent);
			Splash.this.finish();
		}
	}
}