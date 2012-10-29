package com.hiapk.logs;

import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Context;

public class CustomException implements UncaughtExceptionHandler {
	// 获取application 对象；
	private Context context;

	private Thread.UncaughtExceptionHandler defaultExceptionHandler;
	// 单例声明CustomException;
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
			// 将异常抛出，则应用会弹出异常对话框.这里先注释掉
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