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
	private String LOG_PATH_MEMORY_DIR; // 日志文件在内存中的路径(日志文件在安装目录中的路径)，用init初始化
	private String LOG_PATH_SDCARD_DIR; // 日志文件在sdcard中的路径，用init初始化
	private final int SDCARD_TYPE = 0; // 当前的日志记录类型为存储在SD卡下面
	private final int MEMORY_TYPE = 1; // 当前的日志记录类型为存储在内存中
	private int CURR_LOG_TYPE = SDCARD_TYPE; // 当前的日志记录类型
	private Context context;

	public WriteLog(Context context) {
		this.context = context;
		init();
	}

	/**
	 * 处理日志文件String 1.如果日志文件存储位置切换到内存中，删除除了正在写的日志文件 并且部署日志大小监控任务，控制日志大小不超过规定值
	 * 2.如果日志文件存储位置切换到SDCard中，删除7天之前的日志，移 动所有存储在内存中的日志到SDCard中，并将之前部署的日志大小 监控取消
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
	 * 处理日志文件exception 1.如果日志文件存储位置切换到内存中，删除除了正在写的日志文件 并且部署日志大小监控任务，控制日志大小不超过规定值
	 * 2.如果日志文件存储位置切换到SDCard中，删除7天之前的日志，移 动所有存储在内存中的日志到SDCard中，并将之前部署的日志大小 监控取消
	 */
	public void writeLog(Throwable exception) {
		String info = getErrorInfo(exception);
		writeLog(info);
	}

	/**
	 * 获取日志的前置信息
	 * 
	 * @return
	 */
	private String getPreMessage() {
		// 1.获取当前程序的版本号. 版本的id
		String versioninfo = getVersionInfo();
		// 2.获取手机的硬件信息.
		// String mobileInfo = getMobileInfo();
		// Logs.d("versioninfo", versioninfo);
		// Logs.d("mobileInfo", mobileInfo);
		Calendar calendar = Calendar.getInstance();
		Date expiredDate = calendar.getTime();
		expiredDate.toLocaleString();
		return expiredDate.toString() + "\n" + versioninfo + "\n";
	}

	/**
	 * 获取手机的版本信息
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
			return "版本号未知";
		}
	}

	/**
	 * 获取手机的硬件信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		// 通过反射获取系统的硬件信息
		try {
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				// 暴力反射 ,获取私有的信息
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
	 * 将错误信息转化成分行的String
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
		// 3.把错误的堆栈信息 获取出来
		// Logs.d("errorinfo", errorinfo);
		return error;
	}

	/**
	 * 错误日志每个月清除一次
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
		String logDate = getLogDate();
		String logFileName = "log.txt";// 日志文件名称
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

	/**
	 * 初始化路径
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
