package com.hiapk.control.widget;

import com.hiapk.bean.FloatWindowStr;
import com.hiapk.util.SharedPrefrenceDataWidget;
import com.hiapk.util.UnitHandler;

import android.content.Context;
import android.net.TrafficStats;

public class FloatWindowOperator {
	private static long traff = 0;

	public static FloatWindowStr getspeed() {
		FloatWindowStr showStr = new FloatWindowStr();

		if (traff == 0) {
			traff = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();

			showStr.setFloatString(UnitHandler.unitHandlerFloat(0) + "/s");
			return showStr;
		} else {
			long old = traff;
			traff = TrafficStats.getTotalRxBytes()
					+ TrafficStats.getTotalTxBytes();
			long returnTraff = traff - old;
			if (returnTraff < 0) {
				showStr.setFloatString(UnitHandler.unitHandlerFloat(0) + "/s");
				return showStr;
			} else {
				showStr.setFloatString(UnitHandler
						.unitHandlerFloat(returnTraff) + "/s");
				return showStr;
			}

		}
	}

	/**
	 * 保存悬浮窗的XY坐标，重置时使用
	 * 
	 * @param context
	 */
	public static void saveXYvalue(Context context) {
		SharedPrefrenceDataWidget sharedWidget = new SharedPrefrenceDataWidget(
				context);
		if (sharedWidget.isFloatOpen()) {
			if (SetText.FloatIntX != 50) {
				sharedWidget.setIntX(SetText.FloatIntX);
			}
			if (SetText.FloatIntY != 50) {
				sharedWidget.setIntY(SetText.FloatIntY);
			}

		}
	}
}
