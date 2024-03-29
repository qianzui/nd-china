package com.hiapk.ui.custom;

import com.hiapk.spearhead.R;

/**
 * 手机卡页面的相关值设置
 * 
 * @author df_wind
 * 
 */
public class CustomSPDataSet {

	/**
	 * 依据传入的值返回对应的array地址（int）
	 * 
	 * @param provinceID
	 * @return
	 */
	public static int getSpinCity(int provinceID) {
		switch (provinceID) {
		case 0:
			return R.array.beijing;
		case 1:
			return R.array.guangdong;
		case 2:
			return R.array.shanghai;
		case 3:
			return R.array.tianjin;
		case 4:
			return R.array.chongqing;
		case 5:
			return R.array.liaoning;
		case 6:
			return R.array.jiangsu;
		case 7:
			return R.array.hubei;
		case 8:
			return R.array.sichuan;
		case 9:
			return R.array.shanxi_1;
		case 10:
			return R.array.hebei;
		case 11:
			return R.array.shanxi_2;
		case 12:
			return R.array.henan;
		case 13:
			return R.array.jilin;
		case 14:
			return R.array.heilongjiang;
		case 15:
			return R.array.neimenggu;
		case 16:
			return R.array.shandong;
		case 17:
			return R.array.anhui;
		case 18:
			return R.array.zhejiang;
		case 19:
			return R.array.fujian;
		case 20:
			return R.array.hunan;
		case 21:
			return R.array.guangxi;
		case 22:
			return R.array.jiangxi;
		case 23:
			return R.array.guizhou;
		case 24:
			return R.array.yunnan;
		case 25:
			return R.array.xizang;
		case 26:
			return R.array.hainan;
		case 27:
			return R.array.gansu;
		case 28:
			return R.array.ningxia;
		case 29:
			return R.array.qinghai;
		case 30:
			return R.array.xinjiang;
		default:
			return R.array.beijing;
		}
	}

	/**
	 * 依据传入的值返回对应的array地址（int）
	 * 
	 * @param operatorID
	 * @return
	 */
	public static int getSpinBrand(int operatorID) {
		switch (operatorID) {
		case 0:
			return R.array.yidong;
		case 1:
			return R.array.liantong;
		case 2:
			return R.array.dianxin;
		default:
			return R.array.yidong;
		}
	}
}
