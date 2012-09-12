package com.hiapk.dataexe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.hiapk.util.SQLStatic;

import android.content.Context;
import android.util.Log;

public class NotificationInfo {
	private final static String SCRIPT_FILE = "spearedhead.sh";
	public static StringBuilder notificationRes = new StringBuilder();
	public static boolean callbyonResume = false;
	/**
	 * ÿ����ת����������ǽ
	 */
	public static boolean callbyonFirstBacktoFire = false;

	/**
	 * ��ȡ��ǰ֪ͨ��������֪ͨ�б�
	 * 
	 * @param res
	 *            ���жϲ�Ϊ��ʱ��ʹ��NotificationInfo.notificationRes
	 * @return ����һ��ArrayList<String[]>
	 *         ÿ��String[0]Ϊ֪ͨ������String[1]Ϊ֪ͨid��String[2]Ϊ֪ͨ���ı���Ϣ��
	 */
	public static ArrayList<String[]> getNotificationApp(StringBuilder res) {
		showlog("length=" + res.length() + "");
		ArrayList<String[]> apps = new ArrayList<String[]>();
		int startPKG = 0;
		int endPKG = res.indexOf("Notification List:");
		int startId = 0;
		int endId = 0;
		int startText = 0;
		int endText = 0;
		while (startPKG != -1) {
			startPKG = res.indexOf(" pkg=", endPKG + 1);
			endPKG = res.indexOf(" id=", endPKG + 1);
			startId = res.indexOf(" id=", endId + 1);
			endId = res.indexOf(" tag=", endId + 3);
			startText = res.indexOf("tickerText=", endText + 3);
			endText = res.indexOf("contentView=", startText);
			if (startPKG != -1) {
				String[] notificationAppInfo = new String[3];
				notificationAppInfo[0] = res.substring(startPKG + 5, endPKG);

				notificationAppInfo[1] = res.substring(startId + 4, endId);
				if (startText != -1) {
					notificationAppInfo[2] = res.substring(startText + 11,
							endText - 7);
				} else {
					notificationAppInfo[2] = "";
				}
				showlog("pkg=" + notificationAppInfo[0]);
				showlog("idStr=" + notificationAppInfo[1]);
				showlog("textStr=" + notificationAppInfo[2]);
				// ��������
				if (!notificationAppInfo[0].startsWith("com.hiapk.spearhead")) {
					// ���˰����ظ���ͬʱ��ʾ�ı�Ϊnull��֪ͨ��
					if (apps.size() != 0) {
						for (String[] strings : apps) {
							if (!strings[0].startsWith(notificationAppInfo[0]
									.trim())) {
								continue;
							} else if (!strings[2]
									.startsWith(notificationAppInfo[2].trim())) {
								continue;
							} else {
								// showlog(strings[0] + " STlength="
								// + strings[0].length());
								// showlog(strings[2] + " STlength="
								// + strings[2].length());
								// showlog(notificationAppInfo[0] + " length="
								// + notificationAppInfo[0].length());
								// showlog(notificationAppInfo[2] + " length="
								// + notificationAppInfo[2].length());
								notificationAppInfo[0] = "noUse";
							}
						}
						if (notificationAppInfo[0] != "noUse") {
							apps.add(notificationAppInfo);
						}
					} else {
						apps.add(notificationAppInfo);
					}
				}

			}
		}

		return apps;
	}

	/**
	 * ִ��һ����ȡ֪ͨ����Ϣ�����ִ�гɹ���
	 * NotificationInfo.notificationRes�������õ���Ϣ����Ҫͨ��getNotificationApp���д���
	 * 
	 * @param context
	 */
	public static synchronized void startRootcomand(Context context) {
		StringBuilder cmd = new StringBuilder();
		cmd.append("dumpsys notification");
		final File file = new File(context.getDir("bin", 0), SCRIPT_FILE);
		final ScriptRunner runner = new ScriptRunner(file, cmd.toString(), true);
		runner.start();
	}

	/**
	 * ����ִ��adb����
	 */
	private static final class ScriptRunner extends Thread {
		private final File file;
		private final String script;
		private final boolean asroot;
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
				if (!file.isFile()) {
					file.createNewFile();
				}
				final String abspath = file.getAbsolutePath();
				Runtime.getRuntime().exec("chmod 777 " + abspath).waitFor();
				final OutputStreamWriter out = new OutputStreamWriter(
						new FileOutputStream(file));
				if (new File("/system/bin/sh").exists()) {
					out.write("#!/system/bin/sh\n");
				}
				// out.write("chown root " +abspath);
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
					if (notificationRes != null)
						notificationRes.append(buf, 0, read);
				}
				r = new InputStreamReader(exec.getErrorStream());
				read = 0;
				while ((read = r.read(buf)) != -1) {
					if (notificationRes != null)
						notificationRes.append(buf, 0, read);
				}
				// } catch (InterruptedException ex) {
				// if (notificationRes != null) {
				// notificationRes = new StringBuilder();
				// notificationRes.append("\nOperation timed-out");
				// }
			} catch (Exception ex) {
				if (notificationRes != null) {
					notificationRes = new StringBuilder();
					notificationRes.append("\n" + ex);
				}
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

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private static void showlog(String string) {
		if (SQLStatic.isshowLog) {
			Log.d("NotificationInfo", string);
		}
	}

}
