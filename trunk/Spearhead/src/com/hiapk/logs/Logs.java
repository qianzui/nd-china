package com.hiapk.logs;

import android.util.Log;

/**
 * 自定义日志打印方式
 * 
 * @author Administrator
 * 
 */
public class Logs {

	// debug
	public static final boolean debug = true;

	// debug operate
	public static final boolean opDebug = true;

	public static void i(String TAG, String msg) {
		if (debug) {
			Log.i(TAG, msg);
		}
	}

	public static void v(String TAG, String msg) {
		if (debug) {
			Log.v(TAG, msg);
		}
	}

	public static void w(String TAG, String msg) {
		if (debug) {
			Log.w(TAG, msg);
		}
	}

	public static void e(String TAG, String msg) {
		if (debug) {
			Log.e(TAG, msg);
		}
	}

	public static void d(String TAG, String msg) {
		if (debug) {
			Log.d(TAG, msg);
		}
	}

	public static void iop(String TAG, String msg) {
		if (opDebug) {
			Log.i(TAG, msg);
		}
	}

	public static void wop(String TAG, String msg) {
		if (opDebug) {
			Log.i(TAG, msg);
		}
	}

}