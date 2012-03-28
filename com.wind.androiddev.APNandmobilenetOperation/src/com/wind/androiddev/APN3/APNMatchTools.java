package com.wind.androiddev.APN3;

import android.R.bool;
/**
 * 对APN进行匹配
 * @author Administrator
 *
 */
public final class APNMatchTools {
	/**
	 * 可操作的apn类型
	 * @author Administrator
	 *
	 */
	public static class APNNet {
		public static String CMWAP = "cmwap";

		public static String CMNET = "cmnet";
		// 中国联通3GWAP设置 中国联通3G因特网设置 中国联通WAP设置 中国联通因特网设置
		// 3gwap 3gnet uniwap uninet

		public static String GWAP_3 = "3gwap";

		public static String GNET_3 = "3gnet";

		public static String UNIWAP = "uniwap";

		public static String UNINET = "uninet";
	}
	
	/**
	 * 判断网络是否为gprs
	 * @author Administrator
	 *
	 */
	public static boolean isgprs(String apnname){
		boolean isnet=false;
		if (apnname.startsWith("cmwap")) {
			isnet=true;
		}else if (apnname.startsWith("cmnet")) {
			isnet=true;
		}else if (apnname.startsWith("3gwap")) {
			isnet=true;
		}else if (apnname.startsWith("3gnet")) {
			isnet=true;
		}else if (apnname.startsWith("uniwap")) {
			isnet=true;
		}else if (apnname.startsWith("uninet")) {
			isnet=true;
		}
				
		return isnet;		
	}
	/**
	 * 匹配网络名称进行apn恢复
	 * @author Administrator
	 *
	 */
	public static String matchAPN(String currentName) {
		if ("".equals(currentName) || null == currentName) {
			return "";
		}
		currentName = currentName.toLowerCase();
		if (currentName.startsWith(APNNet.CMNET))
			return APNNet.CMNET;
		else if (currentName.startsWith(APNNet.CMWAP))
			return APNNet.CMWAP;
		else if (currentName.startsWith(APNNet.GNET_3))
			return APNNet.GNET_3;
		else if (currentName.startsWith(APNNet.GWAP_3))
			return APNNet.GWAP_3;
		else if (currentName.startsWith(APNNet.UNINET))
			return APNNet.UNINET;
		else if (currentName.startsWith(APNNet.UNIWAP))
			return APNNet.UNIWAP;
		else if (currentName.startsWith("default"))
			return "default";
		else
			return "";
		// return currentName.substring(0, currentName.length() -
		// SUFFIX.length());

	}
}
