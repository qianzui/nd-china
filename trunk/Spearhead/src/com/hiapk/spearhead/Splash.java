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
		GetRoot gr = new GetRoot();
		gr.cmdRoot("chmod 777 " + getPackageCodePath());
		new AsyncTaskonResume().execute(context);
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected Integer doInBackground(Context... params) {
			AlarmSet alset = new AlarmSet();
			// 初始化网络状态
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			sqlhelperTotal.initTablemobileAndwifi(context);
			if (SQLHelperTotal.TableWiFiOrG23 != ""
					&& sqlhelperTotal.getIsInit(context)) {
				// 启动闹钟
				alset.StartAlarm(context);
				return 1;

			} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
				alset.StartAlarm(context);
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
				// 进行数据记录
				sqlhelperUid.RecordUidwritestats(context, false);
				sqlhelperTotal.RecordTotalwritestats(context, false);
			} else {
				// 进行数据记录
				sqlhelperTotal.initTablemobileAndwifi(context);
			}
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Bundle choosetab = new Bundle();
			choosetab.putInt("TAB", 1);
			mainIntent.putExtras(choosetab);
			Splash.this.startActivity(mainIntent);
			Splash.this.finish();
		}
	}
}