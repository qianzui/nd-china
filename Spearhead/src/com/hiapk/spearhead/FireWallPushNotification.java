package com.hiapk.spearhead;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.hiapk.sqlhelper.pub.SQLStatic;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FireWallPushNotification extends Activity {
	long time = 0;
	static String TAG = "notifyt";
	private static final String SCRIPT_FILE = "spearedhead.sh";
	Context context;
	static StringBuilder resMain = new StringBuilder();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.main2_nofity);
		// 为了退出。
		Mapplication.getInstance().addActivity(this);
		context = this;

		// 获取notification信息你
		final StringBuilder cmd = new StringBuilder();
		cmd.append("dumpsys notification");
		String back = null;
		try {
			back = run(context, cmd.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Log.d(TAG, "resMain=" + resMain);

		Button btn_scan = (Button) findViewById(R.id.btn_scan_notify);
		btn_scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (resMain != null) {
					// ArrayList<String[]> appInfos=new ArrayList<String[]>();
					// appInfos=
					getNotificationApp(resMain);
					// String[] app=appInfos.get(3);
					// for (int i = 0; i < app.length; i++) {
					// Log.d(TAG, app[i]);
					// }
				}

			}
		});
		// initList();
		// handler.post(new Runnable() {
		// @Override
		// public void run() {
		// initList();
		// }
		// });
		// handler2 = new Handler() {
		// public void handleMessage(Message msg) {
		// try {
		// setAdapter();
		// if (Block.isShowHelp(mContext)) {
		// showHelp(mContext);
		// SpearheadActivity.isHide = true;
		// } else {
		// if (Block.fireTip(mContext)) {
		// Toast.makeText(mContext, "下拉列表可以进行刷新!",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// } catch (Exception ex) {
		// }
		// }
		// };
	}

	private ArrayList<String[]> getNotificationApp(StringBuilder res) {
		ArrayList<String[]> apps = new ArrayList<String[]>();

		int startPKG = 0;
		int endPKG = res.indexOf("Notification List:");
		int startIcon = 0;
		int endIcon = 0;
		int startText = 0;
		int endText = 0;
		// String test =
		// "123123123 pkg=com.sdfou.sdfiu id=2342,sdfkj  / com.sdfou.owuer:dra/diousdf contentIntent=sdflkasjdf tickerText=sdfousdf contentView=sdfklj sdfl;a";
		// test += test;
		// test += test;
		// Log.d(TAG, "test=" + test);
		while (startPKG != -1) {
			startPKG = res.indexOf(" pkg=", endPKG + 1);
			endPKG = res.indexOf(" id=", endPKG + 1);
			startIcon = res.indexOf(" / ", endIcon + 1);
			endIcon = res.indexOf("contentIntent=", endIcon + 3);
			startText = res.indexOf("tickerText=", endText + 3);
			endText = res.indexOf("contentView=", endText + 3);
			if (startPKG != -1) {
				String[] notificationAppInfo = new String[3];
				notificationAppInfo[0] = res.substring(startPKG + 5, endPKG);
				notificationAppInfo[1] = res.substring(startIcon + 3,
						endIcon - 7);
				notificationAppInfo[2] = res.substring(startText + 11,
						endText - 7);
				Log.d(TAG, "pkg=" + notificationAppInfo[0]);
				Log.d(TAG, "iconStr=" + notificationAppInfo[1]);
				Log.d(TAG, "textStr=" + notificationAppInfo[2]);
				apps.add(notificationAppInfo);
			}
			// Log.d(TAG, "no=" + res);
		}
		// Log.d(TAG, "test=" + res);

		return apps;
	}

	/**
	 * 执行一个shell命令，并返回字符串值
	 * 
	 * @param cmd
	 *            命令名称&参数组成的数组（例如：{"/system/bin/cat", "/proc/version"}）
	 * @param workdirectory
	 *            命令执行路径（例如："system/bin/"）
	 * @return 执行结果组成的字符串
	 * @throws IOException
	 */
	public static synchronized String run(Context context, String cmd)
			throws IOException {

		final File file = new File(context.getDir("bin", 0), SCRIPT_FILE);
		final ScriptRunner runner = new ScriptRunner(file, cmd, true);
		runner.start();
		return null;
	}

	/**
	 * Internal thread used to execute scripts (as root or not).
	 */
	private static final class ScriptRunner extends Thread {
		private final File file;
		private final String script;
		private final boolean asroot;
		public int exitcode = -1;
		private Process exec;

		/**
		 * Creates a new script runner.
		 * 
		 * @param file
		 *            temporary script file
		 * @param script
		 *            script to run
		 * @param res
		 *            response output
		 * @param asroot
		 *            if true, executes the script as root
		 */
		public ScriptRunner(File file, String script, boolean asroot) {
			this.file = file;
			this.script = script;
			this.asroot = asroot;
		}

		@Override
		public void run() {
			try {
				resMain = new StringBuilder();
				file.createNewFile();
				final String abspath = file.getAbsolutePath();
				Runtime.getRuntime().exec("chmod 777 " + abspath).waitFor();
				final OutputStreamWriter out = new OutputStreamWriter(
						new FileOutputStream(file));
				if (new File("/system/bin/sh").exists()) {
					out.write("#!/system/bin/sh\n");
				}
				out.write(script);
				if (!script.endsWith("\n"))
					out.write("\n");
				out.write("exit\n");
				out.flush();
				out.close();
				if (this.asroot) {
					exec = Runtime.getRuntime().exec("su -c " + abspath);
				} else {
					exec = Runtime.getRuntime().exec("sh " + abspath);
				}
				InputStreamReader r = new InputStreamReader(
						exec.getInputStream());
				final char buf[] = new char[1024];
				int read = 0;
				while ((read = r.read(buf)) != -1) {
					if (resMain != null)
						resMain.append(buf, 0, read);
				}
				r = new InputStreamReader(exec.getErrorStream());
				read = 0;
				while ((read = r.read(buf)) != -1) {
					if (resMain != null)
						resMain.append(buf, 0, read);
				}
				if (exec != null)
					this.exitcode = exec.waitFor();
			} catch (InterruptedException ex) {
				if (resMain != null)
					resMain.append("\nOperation timed-out");
			} catch (Exception ex) {
				if (resMain != null)
					resMain.append("\n" + ex);
			} finally {
				destroy();
			}
		}

		/**
		 * Destroy this script runner
		 */
		public synchronized void destroy() {
			if (exec != null)
				exec.destroy();
			exec = null;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// MobclickAgent.onResume(this);
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 用于显示日志
	 * 
	 * @param string
	 */
	private void showLog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("FireWallActivity", string);
		}
	}
}
