package com.hiapk.exception;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class WriteLog {
	private static final String TAG = "LogWrite";
	private String LOG_PATH_MEMORY_DIR; // ��־�ļ����ڴ��е�·��(��־�ļ��ڰ�װĿ¼�е�·��)
	private String LOG_PATH_SDCARD_DIR; // ��־�ļ���sdcard�е�·��
	private final int SDCARD_TYPE = 0; // ��ǰ����־��¼����Ϊ�洢��SD������
	private final int MEMORY_TYPE = 1; // ��ǰ����־��¼����Ϊ�洢���ڴ���
	private int CURR_LOG_TYPE = SDCARD_TYPE; // ��ǰ����־��¼����
	private Context context;

	public WriteLog(Context context) {
		this.context = context;
		init();
	}

	/**
	 * ������־�ļ� 1.�����־�ļ��洢λ���л����ڴ��У�ɾ����������д����־�ļ� ���Ҳ�����־��С������񣬿�����־��С�������涨ֵ
	 * 2.�����־�ļ��洢λ���л���SDCard�У�ɾ��7��֮ǰ����־���� �����д洢���ڴ��е���־��SDCard�У�����֮ǰ�������־��С ���ȡ��
	 */
	public void writeLog(String info) {
		if (CURR_LOG_TYPE == MEMORY_TYPE) {
			writeInfo(info);
		} else {
			moveLogfile();
			writeInfo(info);
		}
	}

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
		String logFileName = "log.txt";// ��־�ļ�����
		if (CURR_LOG_TYPE == MEMORY_TYPE) {
			Log.d(TAG, "Log stored in memory, the path is:"
					+ LOG_PATH_MEMORY_DIR + File.separator + logFileName);
			return LOG_PATH_MEMORY_DIR + File.separator + logFileName;
		} else {
			Log.d(TAG, "Log stored in SDcard, the path is:"
					+ LOG_PATH_SDCARD_DIR + File.separator + logFileName);
			return LOG_PATH_SDCARD_DIR + File.separator + logFileName;
		}
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
