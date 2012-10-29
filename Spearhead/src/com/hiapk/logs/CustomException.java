package com.hiapk.logs;

import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Context;

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
			WriteLog writelog = new WriteLog(context);
			writelog.writeLog(exception);
			// System.out.println(errorinfo);
			// Log.e("tag",
			// "exception >>>>>>>"+exception.getLocalizedMessage());
			// ���쳣�׳�����Ӧ�ûᵯ���쳣�Ի���.������ע�͵�
			defaultExceptionHandler.uncaughtException(thread, exception);
			// android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	public void init(Context context) {
		this.context = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
}