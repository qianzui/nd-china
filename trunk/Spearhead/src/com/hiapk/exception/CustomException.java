package com.hiapk.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class CustomException implements UncaughtExceptionHandler {
	// ��ȡapplication ����
	private Context context;

	private Thread.UncaughtExceptionHandler defaultExceptionHandler;
	// ��������CustomException;
	private static CustomException customException;

	private CustomException() {
	}

	public static CustomException getInstance() {
		if (customException == null) {
			customException = new CustomException();
		}
		return customException;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception) {
		if (defaultExceptionHandler != null) {
			// 1.��ȡ��ǰ����İ汾��. �汾��id
			String versioninfo = getVersionInfo();

			// 2.��ȡ�ֻ���Ӳ����Ϣ.
			// String mobileInfo = getMobileInfo();
			// 3.�Ѵ���Ķ�ջ��Ϣ ��ȡ����
			String errorinfo = getErrorInfo(exception);
			// Log.e("versioninfo", versioninfo);
			// Log.e("mobileInfo", mobileInfo);
			// Log.e("errorinfo", errorinfo);
			WriteLog writelog = new WriteLog(context);
			Calendar calendar = Calendar.getInstance();
			Date expiredDate = calendar.getTime();
			expiredDate.toLocaleString();
			writelog.writeLog(expiredDate.toString() + "\n");
			writelog.writeLog(versioninfo + "\n");
			writelog.writeLog(errorinfo);
			writelog.writeLog("\n" + "\n");
			// System.out.println(errorinfo);
			// Log.e("tag",
			// "exception >>>>>>>"+exception.getLocalizedMessage());
			// ���쳣�׳�����Ӧ�ûᵯ���쳣�Ի���.������ע�͵�
			defaultExceptionHandler.uncaughtException(thread, exception);
			// android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * ��ȡ�������Ϣ
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
		return error;
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

	public void init(Context context) {
		this.context = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
}