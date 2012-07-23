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
	private String LOG_PATH_MEMORY_DIR; // 日志文件在内存中的路径(日志文件在安装目录中的路径)
	private String LOG_PATH_SDCARD_DIR; // 日志文件在sdcard中的路径
	private final int SDCARD_TYPE = 0; // 当前的日志记录类型为存储在SD卡下面
	private final int MEMORY_TYPE = 1; // 当前的日志记录类型为存储在内存中
	private int CURR_LOG_TYPE = SDCARD_TYPE; // 当前的日志记录类型
	private Context context;

	public WriteLog(Context context) {
		this.context = context;
		init();
	}

	/**
	 * 处理日志文件 1.如果日志文件存储位置切换到内存中，删除除了正在写的日志文件 并且部署日志大小监控任务，控制日志大小不超过规定值
	 * 2.如果日志文件存储位置切换到SDCard中，删除7天之前的日志，移 动所有存储在内存中的日志到SDCard中，并将之前部署的日志大小 监控取消
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
	 * 日志文件写入SD卡或手机。
	 * 
	 * @param info
	 */
	private void writeInfo(String info) {
		FileOutputStream out = null;
		// 打开文件
		File myFile = new File(getLogPath());
		try {
			// 判断是否存在,不存在则创建
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			out = new FileOutputStream(myFile, true); // 如果要追加内容,加多一个参数并且值设置为true
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
	 * 将日志文件转移到SD卡下面
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
	 * 根据当前的存储位置得到日志的绝对存储路径
	 * 
	 * @return
	 */
	private String getLogPath() {
		createLogDir();
		String logFileName = "log.txt";// 日志文件名称
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
	 * 拷贝文件
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
	 * 创建日志目录
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
	 * 获取当前应存储在内存中还是存储在SDCard中
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
