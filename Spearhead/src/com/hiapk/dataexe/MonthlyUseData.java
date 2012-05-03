package com.hiapk.dataexe;

import com.hiapk.sqlhelper.SQLHelperTotal;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

public class MonthlyUseData {
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi与mobile单月使用量
	private long wifi_month_use = 0;

	// 临时存放两个数据------------
	private int[] uids;
	private String[] packagenames;
	// ------------
	// 显示何种图形
	boolean ismobileshowpie = false;
	// 获取的系统时间
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;
	// wifi月度流量
	long[] wifiTraffic = new long[64];
	/**
	 * 完整月份的移动数据流量
	 */
	long[] mobileTraffic = new long[64];
	/**
	 * 部分月份的移动数据流量
	 */
	long[] mobileTrafficPart = new long[64];

	public long getMonthUseData(Context context) {
		long mobile_month_use = 0;
		// 取得系统时间。
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;
		// 月度流量设置
		String PREFS_NAME = "allprefs";
		String VALUE_MOBILE_SET = "mobilemonthuse";
		String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";
		// 设置结算日期及结算日期的设施时间，日期等
		String MOBILE_COUNT_DAY = "mobileMonthCountDay";
		String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
		String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
		String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
		String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		//
		long[] b = new long[3];
		// long[] a = new long[3];
		// a = sqlhelperTotal.SelectMobileData(context, year, month, monthDay,
		// "10:10:10");
		b = sqlhelperTotal.SelectMobileData(context, year, month, 31, 30);
		//
		// 取得每周的流量
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		// 取得月度流量
		mobileTraffic = sqlhelperTotal.SelectMobileData(context, year, month);
		//
		mobileTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		//
		wifiTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		// 月度流量设置
		// 设置结算日期及结算日期的设施时间，日期等
		// 结算日期
		/**
		 * 设置本月的结算日期
		 */
		int mobilecountDay = (prefs.getInt(MOBILE_COUNT_DAY, 0)+1);
		// 设置结算日期的时间
		int mobilecountSetYear = prefs.getInt(MOBILE_COUNT_SET_YEAR, 1977);
		int mobilecountSetMonth = prefs.getInt(MOBILE_COUNT_SET_MONTH, 1);
		/**
		 * 设置本月已用流量的日期
		 */
		int mobilecountSetDay = prefs.getInt(MOBILE_COUNT_SET_DAY, 1);
		String mobilecountSetTime = prefs.getString(MOBILE_COUNT_SET_TIME,
				"00:00:00");
		long[] oneday = new long[3];
		long[] leftday = new long[3];
		// 是否已经设置过已用流量
		//如果未对本月已用流量进行设置，则默认为非当月
		if ((mobilecountSetYear != 1977) || (mobilecountDay != 1)) {
			// 设置已用流量日期与计算日相同
			if (mobilecountSetDay == mobilecountDay) {
				showlog("SetDay = countDay");
				// 在当月设置第一天采用半天
				if (((mobilecountSetMonth == month)
						&& (mobilecountSetYear == year) && (monthDay > mobilecountSetDay))
						|| ((mobilecountSetMonth == month)
								&& (mobilecountSetYear == year) && (monthDay == mobilecountSetDay))
						|| ((monthDay < mobilecountSetDay)
								&& (mobilecountSetYear == year) && ((mobilecountSetMonth + 1) == month))
						|| ((monthDay < mobilecountSetDay)
								&& ((mobilecountSetYear + 1) == year) && ((mobilecountSetMonth - 11) == month))) {
					oneday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay, mobilecountSetTime);
					leftday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay + 1, mobilecountDay);
					mobile_month_use = oneday[0] + leftday[0];
					showlog("当月，今天开始到下个月结算日");
					showlog(oneday[0] + "oneday");
					showlog(leftday[0] + "leftday");
					showlog(mobilecountSetYear + "年" + mobilecountSetMonth
							+ "yue" + mobilecountSetDay);
					showlog(mobilecountDay + "结算日");
					showlog(mobilecountSetTime + "");

					// 设置流量日期或者计算日期后几个月
				} else {
					// 当前日期在设置的结算日之前
					if (monthDay < mobilecountDay) {
						// 判断跨年
						if (month != 1) {
							showlog("非当月，当天到这个月结算日无跨年");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year, month - 1, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						} else {
							showlog("非当月，当天到这个月结算日跨年去年12月结算日到今年1月结算日");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year - 1, 12, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						}
						// 当前日期在设置的结算日之后
					} else {
						showlog("非当月，当天到下个月结算日");
						showlog("monthDay >= mobilecountDay");
						leftday = sqlhelperTotal.SelectMobileData(context,
								year, month, mobilecountDay, mobilecountDay);
						mobile_month_use = leftday[0];
					}

				}
				// 设置流量已用日期大于结算日期
			} else if (mobilecountSetDay > mobilecountDay) {
				showlog("SetDay > countDay");
				showlog("mobilecountSetDay > mobilecountDay");
				showlog(oneday[0] + "oneday");
				showlog(leftday[0] + "leftday");
				showlog(mobilecountSetYear + "年" + mobilecountSetMonth + "yue"
						+ mobilecountSetDay);
				showlog(mobilecountDay + "结算日");
				showlog(mobilecountSetTime + "");
				// 是否当月
				if (((mobilecountSetMonth == month)
						&& (mobilecountSetYear == year) && (monthDay > mobilecountSetDay))
						|| ((mobilecountSetMonth == month)
								&& (mobilecountSetYear == year) && (monthDay == mobilecountSetDay))
						|| ((monthDay < mobilecountSetDay)
								&& (mobilecountSetYear == year) && ((mobilecountSetMonth + 1) == month))
						|| ((monthDay < mobilecountSetDay)
								&& ((mobilecountSetYear + 1) == year) && ((mobilecountSetMonth - 11) == month))) {
					showlog("当月，今天开始到下个月结算日");
					oneday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay, mobilecountSetTime);
					leftday = sqlhelperTotal.SelectMobileData(context,
							mobilecountSetYear, mobilecountSetMonth,
							mobilecountSetDay + 1, mobilecountDay);
					mobile_month_use = oneday[0] + leftday[0];
				} else {
					if (monthDay < mobilecountDay) {
						// 判断跨年
						if (month != 1) {
							showlog("非当月，当天到这个月结算日无跨年");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year, month - 1, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						} else {
							showlog("非当月，当天到这个月结算日跨年去年12月结算日到今年1月结算日");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year - 1, 12, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						}
						// 当前日期在设置的结算日之后
					} else {
						showlog("非当月，当天到下个月结算日");
						leftday = sqlhelperTotal.SelectMobileData(context,
								year, month, mobilecountDay, mobilecountDay);
						mobile_month_use = leftday[0];
					}
				}
				// 设置流量已用日期小于结算日期
			} else {
				showlog("SetDay < countDay");
				showlog("mobilecountSetDay <= mobilecountDay");
				showlog(oneday[0] + "oneday");
				showlog(leftday[0] + "leftday");
				showlog(mobilecountSetYear + "年" + mobilecountSetMonth + "yue"
						+ mobilecountSetDay);
				showlog(mobilecountDay + "结算日");
				showlog(mobilecountSetTime + "");
				// 是否当月
				if (((mobilecountSetMonth == month)
						&& (mobilecountSetYear == year) && (monthDay > mobilecountSetDay))
						|| ((mobilecountSetMonth == month)
								&& (mobilecountSetYear == year) && (monthDay == mobilecountSetDay))
						|| ((monthDay < mobilecountSetDay)
								&& (mobilecountSetYear == year) && ((mobilecountSetMonth + 1) == month))
						|| ((monthDay < mobilecountSetDay)
								&& ((mobilecountSetYear + 1) == year) && ((mobilecountSetMonth - 11) == month))) {
					if ((mobilecountSetDay + 1) == mobilecountDay) {
						showlog("当月，今天开始今天结束");
						oneday = sqlhelperTotal.SelectMobileData(context,
								mobilecountSetYear, mobilecountSetMonth,
								mobilecountSetDay, mobilecountSetTime);
						mobile_month_use = oneday[0];
					} else {
						showlog("当月，今天开始到这个月结算日");
						oneday = sqlhelperTotal.SelectMobileData(context,
								mobilecountSetYear, mobilecountSetMonth,
								mobilecountSetDay, mobilecountSetTime);
						leftday = sqlhelperTotal.SelectMobileData(context,
								mobilecountSetYear, mobilecountSetMonth,
								mobilecountSetDay + 1, mobilecountDay);
						mobile_month_use = oneday[0] + leftday[0];
					}

				} else {
					// 当前日期在设置的结算日之前
					if (monthDay < mobilecountDay) {
						// 判断跨年
						if (month != 1) {
							showlog("非当月，当天到这个月结算日无跨年");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year, month - 1, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						} else {
							showlog("非当月，当天到这个月结算日跨年去年的12月结算日到今年1月的结算日");
							leftday = sqlhelperTotal.SelectMobileData(context,
									year - 1, 12, mobilecountDay,
									mobilecountDay);
							mobile_month_use = leftday[0];
						}
						// 当前日期在设置的结算日之后
					} else {
						showlog("非当月，当天到下个月结算日");
						leftday = sqlhelperTotal.SelectMobileData(context,
								year, month, mobilecountDay, mobilecountDay);
						mobile_month_use = leftday[0];
					}
				}
			}
			// 从未设置过已用流量
		} else {
			showlog("从未设置");
			mobile_month_use = mobileTraffic[0] + mobileTraffic[63];
		}
		return mobile_month_use;
	}

	/**
	 * 显示日志
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("MonthlyUseData", string);
	}

}
