package com.hiapk.spearhead;

import com.hiapk.broadcreceiver.AlarmSet;
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
		new AsyncTaskonResume().execute(context);
	}

	private class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {
		@Override
		protected Integer doInBackground(Context... params) {
			AlarmSet alset = new AlarmSet();
			// 初始化网络状态
			SQLHelperUid sqlhelperUid = new SQLHelperUid();
			SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
			sqlhelperTotal.initTablemobileAndwifi(context);
			if (SQLHelperTotal.TableWiFiOrG23 != ""
					&& sqlhelperTotal.getIsInit(context)) {
				// 启动闹钟
				alset.StartAlarm(context);
				// 进行数据记录
				sqlhelperUid.RecordUidwritestats(context, false);
				sqlhelperTotal.RecordTotalwritestats(context, false);
			} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
				alset.StartAlarm(context);
				sqlhelperTotal.initTablemobileAndwifi(context);
			}
			return null;
			// TODO Auto-generated method stub
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			Intent mainIntent = new Intent(Splash.this, SpearheadActivity.class);
			Splash.this.startActivity(mainIntent);
			Splash.this.finish();
		}
	}
}