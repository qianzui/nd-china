package com.hiapk.dataexe;

import java.text.DecimalFormat;
/**
 * ���е�λ�ı�׼��
 * @author Administrator
 *
 */
public class UnitHandler {
	/**
	 * ��λ��׼����ֻ��mb��gb�����ϵ� ֻ��һ��textview��ʾ��ֵ�뵥λ
	 * ��������С��1024����ʾ0.0 MB��
	 * @param count
	 *            �����long����
	 * @param unit
	 *            ��ֵ����Ҫ��ʾ��textview
	 * @return ����String��ֵ
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
	
	
}