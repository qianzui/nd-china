package com.hiapk.util;

public class MonthDay {
	/**
	 * ���㵥���м���
	 * 
	 * @param year
	 *            �������
	 * @param month
	 *            �����·�
	 * @return ��������
	 */
	public static synchronized int countDay(int year, int month) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))
				&& month == 2) {
			return 29;
		} else {
			switch (month) {
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			}
		}
		return 31;
	}

	/**
	 * ��ʽ����������xxxx-xx-xx��ʽ
	 * 
	 * @param year
	 * @param month
	 * @param dayMonth
	 * @return
	 */
	public static String formatDate(int year, int month, int dayMonth) {
		StringBuilder stringB = new StringBuilder();
		stringB.append(year).append("-");
		if (month < 10) {
			stringB.append("0");
		}
		stringB.append(month).append("-");
		if (dayMonth < 10) {
			stringB.append("0");
		}
		stringB.append(dayMonth);
		return stringB.toString();
	}
}
