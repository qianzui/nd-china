package com.hiapk.logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

public class WriteLog {
	private String TAG = "LogWrite";
	private String LOG_PATH_MEMORY_DIR; // ��־�ļ����ڴ��е�·��(��־�ļ��ڰ�װĿ¼�е�·��)����init��ʼ��
	private String LOG_PATH_SDCARD_DIR; // ��־�ļ���sdcard�е�·������init��ʼ��
	private final int SDCARD_TYPE = 0; // ��ǰ����־��¼����Ϊ�洢��SD������
	private final int MEMORY_TYPE = 1; // ��ǰ����־��¼����Ϊ�洢���ڴ���
	private int CURR_LOG_TYPE = SDCARD_TYPE; // ��ǰ����־��¼����
	private Context context;

	public WriteLog(Context context) {
		this.context = context;
		init();
	}

	/**
	 * ������־�ļ�String 1.�����־�ļ��洢λ���л����ڴ��У�ɾ����������д����־�ļ� ���Ҳ�����־��С������񣬿�����־��С�������涨ֵ
	 * 2.�����־�ļ��洢λ���л���SDCard�У�ɾ��7��֮ǰ����־���� �����д洢���ڴ��е���־��SDCard�У�����֮ǰ�������־��С ���ȡ��
	 */
	public void writeLog(String info) {
		info = getPreMessage() + info + "\n" + "\n";
		if (CURR_LOG_TYPE == MEMORY_TYPE) {
			writeInfo(info);
		} else {
			moveLogfile();
			writeInfo(info);
		}
	}

	/**
	 * ������־�ļ�exception 1.�����־�ļ��洢λ���л����ڴ��У�ɾ����������д����־�ļ� ���Ҳ�����־��С������񣬿�����־��С�������涨ֵ
	 * 2.�����־�ļ��洢λ���л���SDCard�У�ɾ��7��֮ǰ����־���� �����д洢���ڴ��е���־��SDCard�У�����֮ǰ�������־��С ���ȡ��
	 */
	public void writeLog(Throwable exception) {
		String info = getErrorInfo(exception);
		writeLog(info);
	}

	/**
	 * ��ȡ��־��ǰ����Ϣ
	 * 
	 * @return
	 */
	private String getPreMessage() {
		// 1.��ȡ��ǰ����İ汾��. �汾��id
		String versioninfo = getVersionInfo();
		// 2.��ȡ�ֻ���Ӳ����Ϣ.
		// String mobileInfo = getMobileInfo();
		// Logs.d("versioninfo", versioninfo);
		// Logs.d("mobileInfo", mobileInfo);
		Calendar calendar = Calendar.getInstance();
		Date expiredDate = calendar.getTime();
		expiredDate.toLocaleString();
		return expiredDate.toString() + "\n" + versioninfo + "\n";
	}

	/**
	 * ��ȡ�ֻ��İ汾��Ϣ
	 * 
	 * @return
	 */
	private String getVersionInfo() {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			return "�汾��δ֪";
		}
	}

	/**
	 * ��ȡ�ֻ���Ӳ����Ϣ
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		// ͨ�������ȡϵͳ��Ӳ����Ϣ
		try {
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				// �������� ,��ȡ˽�е���Ϣ
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name + "=" + value);
				sb.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * ��������Ϣת���ɷ��е�String
	 * 
	 * @param arg1
	 * @return
	 */
	private String getErrorInfo(Throwable arg1) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		arg1.printStackTrace(pw);
		pw.close();
		String error = writer.toString();
		// 3.�Ѵ���Ķ�ջ��Ϣ ��ȡ����
		// Logs.d("errorinfo", errorinfo);
		return error;
	}

	/**
	 * ������־ÿ�������һ��
	 */
	public void clearmonthLog() {
		File file = new File(LOG_PATH_SDCARD_DIR);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			if (file.isDirectory()) {
				File[] allFiles = file.listFiles();
				for (File logFile : allFiles) {
					logFile.delete();
				}
			}
			file.delete();
		}
		file = new File(LOG_PATH_MEMORY_DIR);
		if (file.isDirectory()) {
			File[] allFiles = file.listFiles();
			for (File logFile : allFiles) {
				logFile.delete();
			}
			file.delete();
		}

	}

	/**
	 * ��־�ļ�д��SD�����ֻ���
	 * 
	 * @param info
	 */
	private void writeInfo(String info) {
		FileOutputStream out = null;
		// ���ļ�
		File myFile = new File(getLogPath());
		try {
			// �ж��Ƿ����,�������򴴽�
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			out = new FileOutputStream(myFile, true); // ���Ҫ׷������,�Ӷ�һ����������ֵ����Ϊtrue
			out.write(info.getBytes());
			out.close();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage(), e);
			}
		}
	}

	/**
	 * ����־�ļ�ת�Ƶ�SD������
	 */
	private void moveLogfile() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			// recordLogServiceLog("move file failed, sd card does not mount");
			return;
		}
		File file = new File(LOG_PATH_SDCARD_DIR);
		if (!file.isDirectory()) {
			boolean mkOk = file.mkdirs();
			if (!mkOk) {
				// recordLogServiceLog("move file failed,dir is not created succ");
				return;
			}
		}

		file = new File(LOG_PATH_MEMORY_DIR);
		if (file.isDirectory()) {
			File[] allFiles = file.listFiles();
			for (File logFile : allFiles) {
				String fileName = logFile.getName();
				// String createDateInfo =
				// getFileNameWithoutExtension(fileName);
				boolean isSucc = copy(logFile, new File(LOG_PATH_SDCARD_DIR
						+ File.separator + fileName));
				if (isSucc) {
					logFile.delete();
					// recordLogServiceLog("move file success,log name is:"+fileName);
				}
			}
		}
	}

	/**
	 * ���ݵ�ǰ�Ĵ洢λ�õõ���־�ľ��Դ洢·��
	 * 
	 * @return
	 */
	private String getLogPath() {
		createLogDir();
		String logDate = getLogDate();
		String logFileName = "log.txt";// ��־�ļ�����
		if (CURR_LOG_TYPE == MEMORY_TYPE) {
			Logs.d(TAG, "Log stored in memory, the path is:"
					+ LOG_PATH_MEMORY_DIR + File.separator + logDate
					+ logFileName);
			return LOG_PATH_MEMORY_DIR + File.separator + logDate + logFileName;
		} else {
			Logs.d(TAG, "Log stored in SDcard, the path is:"
					+ LOG_PATH_SDCARD_DIR + File.separator + logDate
					+ logFileName);
			return LOG_PATH_SDCARD_DIR + File.separator + logDate + logFileName;
		}
	}

	private String getLogDate() {
		Date date = new Date();
		DateFormat datef = DateFormat.getDateInstance();
		return datef.format(date);
	}

	/**
	 * �����ļ�
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private boolean copy(File source, File target) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			if (!target.exists()) {
				boolean createSucc = target.createNewFile();
				if (!createSucc) {
					return false;
				}
			}
			in = new FileInputStream(source);
			out = new FileOutputStream(target, true);
			byte[] buffer = new byte[8 * 1024];
			int count;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.getMessage(), e);
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				Log.e(TAG, e.getMessage(), e);
				return false;
			}
		}

	}

	/**
	 * ��ʼ��·��
	 */
	private void init() {
		LOG_PATH_MEMORY_DIR = context.getFilesDir().getAbsolutePath()
				+ File.separator + "log";
		LOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "SpearheadLog";
		createLogDir();

		/* ******************************************************
		 * try { writer = new OutputStreamWriter(new FileOutputStream(
		 * LOG_SERVICE_LOG_PATH, true)); } catch (FileNotFoundException e) {
		 * Log.e(TAG, e.getMessage(), e); }
		 * *****************************************************
		 */

		CURR_LOG_TYPE = getCurrLogType();
		Log.i(TAG, "LogDir Create");
	}

	/**
	 * ������־Ŀ¼
	 */
	private void createLogDir() {
		File file = new File(LOG_PATH_MEMORY_DIR);
		boolean mkOk;
		if (!file.isDirectory()) {
			mkOk = file.mkdirs();
			if (!mkOk) {
				mkOk = file.mkdirs();
			}
		}

		/* ************************************
		 * file = new File(LOG_SERVICE_LOG_PATH); if (!file.exists()) { try {
		 * mkOk = file.createNewFile(); if (!mkOk) { file.createNewFile(); } }
		 * catch (IOException e) { Log.e(TAG, e.getMessage(), e); } }
		 * ***********************************
		 */

		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File(LOG_PATH_SDCARD_DIR);
			if (!file.isDirectory()) {
				mkOk = file.mkdirs();
				if (!mkOk) {
					Log.d(TAG, "move file failed,dir is not created succ");
					return;
				}
			}
		}
	}

	/**
	 * ��ȡ��ǰӦ�洢���ڴ��л��Ǵ洢��SDCard��
	 * 
	 * @return
	 */
	private int getCurrLogType() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return MEMORY_TYPE;
		} else {
			return SDCARD_TYPE;
		}
	}
}
