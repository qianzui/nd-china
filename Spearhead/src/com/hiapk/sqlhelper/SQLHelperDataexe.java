package com.hiapk.sqlhelper;

import android.net.TrafficStats;

public class SQLHelperDataexe {
	/**
	 * ��ʼ����������
	 * 
	 * @param uidnumber
	 *            ����uid��ȡ��������
	 * @return uidtraff[0]Ϊuid���ϴ�������uidtraff[1]Ϊuid����������
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
	 * ��ʼ����������
	 * 
	 * @param table
	 *            wifi����mobile����Ϊ����������
	 * @return traff[0]Ϊuid���ϴ�������traff[1]Ϊuid����������
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
