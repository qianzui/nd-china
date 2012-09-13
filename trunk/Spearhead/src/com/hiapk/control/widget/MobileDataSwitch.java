package com.hiapk.control.widget;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * 控制移动数据开关
 * 需求权限
 * 	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 *	<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
 * @author wind
 * 
 */
public class MobileDataSwitch {
	protected final String TAG = "setMobileData";
	private ConnectivityManager cmmobile;

	/**
	 * 开启移动数据网络
	 * 
	 * @param context
	 */
	public void setMobileDataEnable(Context context) {
		cmmobile = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			invokeBooleanArgMethod("setMobileDataEnabled", true);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, "Error on starting mobiledata");
		}
	}

	/**
	 * 关闭移动数据网络
	 * 
	 * @param context
	 */
	public void setMobileDataDisable(Context context) {
		cmmobile = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			/*
			 * 调用系统中已存在的方法
			 */
			invokeBooleanArgMethod("setMobileDataEnabled", false);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, "Error on stopping mobiledata");
		}
	}

	/**
	 * 用返回值查看移动数据网络开关状态
	 * 
	 * @param context
	 */
	public boolean isMobileDataEnable(Context context) {
		cmmobile = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isabled = false;
		try {
			Object[] arg = null;
			isabled = (Boolean) invokeMethod("getMobileDataEnabled", arg);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d(TAG, "Error on get mobiledata info");
		}
		return isabled;
	}

	/*
	 * 反射機制調用
	 */
	@SuppressWarnings("rawtypes")
	private Object invokeMethod(String methodName, Object[] arg)
			throws Exception {
		Class ownerClass = cmmobile.getClass();
		Class[] argsClass = null;
		if (arg != null) {
			argsClass = new Class[1];
			argsClass[0] = arg.getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(cmmobile, arg);
	}

	/*
	 * 反射機制調用
	 */
	@SuppressWarnings("rawtypes")
	private Object invokeBooleanArgMethod(String methodName, boolean value)
			throws Exception {
		Class ownerClass = cmmobile.getClass();
		Class[] argsClass = new Class[1];
		argsClass[0] = boolean.class;
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(cmmobile, value);

	}

}