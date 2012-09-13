package com.hiapk.ui.widget;

import android.content.Context;
import android.net.TrafficStats;

public class TrafficInfomation {
	static long traff = 0;

	public static long getspeed(Context context) {
		if (traff == 0) {
			traff = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();
			return 0;
		} else {
			long old = traff;
			traff = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();
			long returnTraff = traff - old;
			if (returnTraff < 0) {
				return 0;
			} else {
				return returnTraff/3;
			}

		}
	}
}
