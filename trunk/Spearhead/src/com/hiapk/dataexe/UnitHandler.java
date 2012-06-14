package com.hiapk.dataexe;

import java.text.DecimalFormat;

import android.widget.TextView;

/**
 * 进行单位的标准化
 * 
 * @author Administrator
 * 
 */
public class UnitHandler {
	/**
	 * 单位标准化，只有mb，gb及以上等 只有一个textview显示数值与单位 输入数字小于1024则显示0.0 MB；
	 * 
	 * @param count
	 *            输入的long型数
	 * @param unit
	 *            数值后面要显示的textview
	 * @return 返回String型值
	 */
	public String unitHandler(long count) {
		String value = null;
		long temp = count;
		float floatnum = count;
		float floatGB = count;
		float floatTB = count;
		DecimalFormat format = new DecimalFormat("0.##");
		if ((temp = temp / 1024) < 1) {
			value = "0.0 MB";
		} else if ((floatnum = (float) temp / 1024) < 1) {
			value = format.format(floatnum) + " MB";
		} else if ((floatGB = floatnum / 1024) < 1) {
			value = format.format(floatnum) + " MB";
		} else if ((floatTB = floatGB / 1024) < 1) {
			value = format.format(floatGB) + " GB";
		} else {
			value = format.format(floatTB) + " TB";
		}
		return value;
	}

	/**
	 * 单位标准化，mb，gb等
	 * 
	 * @param count
	 *            输入的long型数
	 * @param unit
	 *            数值后面要显示的textview
	 * @return 返回String型值
	 */
	public String unitHandlerAccurate(long count) {
		String value = null;
		float temp = count;
		float floatnum = count;
		float floatGB = count;
		float floatTB = count;
		if ((temp = temp / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(temp) + " KB";
		} else if ((floatnum = (float) temp / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value =  format.format(temp) + " KB";
		} else if ((floatGB = floatnum / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatnum) + " MB";
		} else if ((floatTB = floatGB / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatGB) + " GB";
		} else {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatTB) + " TB";
		}
		return value;
	}

}
