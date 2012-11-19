package com.hiapk.control.widget;

import com.hiapk.bean.FloatWindowStr;
import com.hiapk.util.UnitHandler;

import android.net.TrafficStats;

public class FloatWindowTextSet {
	private static long traff = 0;

	public static FloatWindowStr getspeed() {
		FloatWindowStr showStr = new FloatWindowStr();

		if (traff == 0) {
			traff = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();

			showStr.setFloatString(UnitHandler.unitHandlerFloat(0)
					+ "/s");
			return showStr;
		} else {
			long old = traff;
			traff = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();
			long returnTraff = traff - old;
			if (returnTraff < 0) {
				showStr.setFloatString(UnitHandler.unitHandlerFloat(0)
						+ "/s");
				return showStr;
			} else {
				showStr.setFloatString(UnitHandler.unitHandlerFloat(returnTraff) + "/s");
				return showStr;
			}

		}
	}
}
