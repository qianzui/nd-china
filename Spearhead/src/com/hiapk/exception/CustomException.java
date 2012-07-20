package com.hiapk.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public class CustomException implements UncaughtExceptionHandler {
	// 获取application 对象；
	private Context mContext;

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
		// TODO Auto-generated method stub
		if (defaultExceptionHandler != null) {
			// 1.获取当前程序的版本号. 版本的id  
	        String versioninfo = getVersionInfo();  
	          
	        // 2.获取手机的硬件信息.  
	        String mobileInfo  = getMobileInfo();  
	          
	        // 3.把错误的堆栈信息 获取出来   
	        String errorinfo = getErrorInfo(exception);
	        Log.e("versioninfo",versioninfo);
	        Log.e("mobileInfo",mobileInfo);
			Log.e("errorinfo",errorinfo);
			System.out.println(errorinfo);  
			// Log.e("tag",
			// "exception >>>>>>>"+exception.getLocalizedMessage());
			// 将异常抛出，则应用会弹出异常对话框.这里先注释掉
//			defaultExceptionHandler.uncaughtException(thread, exception);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
	/** 
     * 获取错误的信息  
     * @param arg1 
     * @return 
     */  
    private String getErrorInfo(Throwable arg1) {  
        Writer writer = new StringWriter();  
        PrintWriter pw = new PrintWriter(writer);  
        arg1.printStackTrace(pw);  
        pw.close();  
        String error= writer.toString();  
        return error;  
    }  
  
    /** 
     * 获取手机的硬件信息  
     * @return 
     */  
    private String getMobileInfo() {  
        StringBuffer sb = new StringBuffer();  
        //通过反射获取系统的硬件信息   
        try {  
  
            Field[] fields = Build.class.getDeclaredFields();  
            for(Field field: fields){  
                //暴力反射 ,获取私有的信息   
                field.setAccessible(true);  
                String name = field.getName();  
                String value = field.get(null).toString();  
                sb.append(name+"="+value);  
                sb.append("\n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sb.toString();  
    }  
  
    /** 
     * 获取手机的版本信息 
     * @return 
     */  
    private String getVersionInfo(){  
        try {  
            PackageManager pm = mContext.getPackageManager();  
             PackageInfo info =pm.getPackageInfo(mContext.getPackageName(), 0);  
             return  info.versionName;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "版本号未知";  
        }  
    } 
	public void init(Context context) {
		mContext = context;
		defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
}