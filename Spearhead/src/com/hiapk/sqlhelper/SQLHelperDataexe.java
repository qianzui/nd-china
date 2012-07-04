package com.hiapk.sqlhelper;

import android.net.TrafficStats;

public class SQLHelperDataexe {
	/**
	 * 初始化流量数据
	 * 
	 * @param uidnumber
	 *            依据uid获取流量数据
	 * @return uidtraff[0]为uid的上传流量，uidtraff[1]为uid的下载流量
	 */
	public static long[] initUidData(int uidnumber) {
		long uidupload, uiddownload;
		long[] uidtraff = new long[2];
		uidupload = TrafficStats.getUidTxBytes(uidnumber);
		uiddownload = TrafficStats.getUidRxBytes(uidnumber);
		if (uidupload == -1) {
			uidupload = 0;
		}
		if (uiddownload == -1) {
			uiddownload = 0;
		}
		uidtraff[0] = uidupload;
		uidtraff[1] = uiddownload;
		return uidtraff;
	}

	/**
	 * 初始化流量数据
	 * 
	 * @param table
	 *            wifi或者mobile，若为空则无数据
	 * @return traff[0]为uid的上传流量，traff[1]为uid的下载流量
	 */
	public static long[] initTotalData(String table) {
		long upload = 0, download = 0;
		long[] totaltraff = new long[2];
		if (table == "wifi") {
			upload = TrafficStats.getTotalTxBytes()
					- TrafficStats.getMobileTxBytes();
			download = TrafficStats.getTotalRxBytes()
					- TrafficStats.getMobileRxBytes();
			if (upload == 1) {
				upload = 0;
			}
			if (download == 1) {
				download = 0;
			}
		}
		if (table == "mobile") {
			upload = TrafficStats.getMobileTxBytes();
			download = TrafficStats.getMobileRxBytes();
			if (upload == -1) {
				upload = 0;
			}
			if (download == -1) {
				download = 0;
			}
		}
		if (table == "") {
			upload = 0;
			download = 0;
		}
		totaltraff[0] = upload;
		totaltraff[1] = download;
		return totaltraff;
	}
}
