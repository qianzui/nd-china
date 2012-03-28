package com.wind.androiddev.APN3;

import android.R.bool;
/**
 * ��APN����ƥ��
 * @author Administrator
 *
 */
public final class APNMatchTools {
	/**
	 * �ɲ�����apn����
	 * @author Administrator
	 *
	 */
	public static class APNNet {
		public static String CMWAP = "cmwap";

		public static String CMNET = "cmnet";
		// �й���ͨ3GWAP���� �й���ͨ3G���������� �й���ͨWAP���� �й���ͨ����������
		// 3gwap 3gnet uniwap uninet

		public static String GWAP_3 = "3gwap";

		public static String GNET_3 = "3gnet";

		public static String UNIWAP = "uniwap";

		public static String UNINET = "uninet";
	}
	
	/**
	 * �ж������Ƿ�Ϊgprs
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
	 * ƥ���������ƽ���apn�ָ�
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
