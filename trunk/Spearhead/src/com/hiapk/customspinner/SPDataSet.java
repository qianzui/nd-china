package com.hiapk.customspinner;

import com.hiapk.spearhead.R;

public class SPDataSet {

	public static void setHeight(int length) {
		// TODO Auto-generated method stub
		switch (length) {
		case 1:
			CustomSPDialog.heighpar = 0.3;
			break;
		case 2:
			CustomSPDialog.heighpar = 0.35;
			break;
		case 3:
			CustomSPDialog.heighpar = 0.45;
			break;
		case 4:
			CustomSPDialog.heighpar = 0.45;
			break;
		case 5:
			CustomSPDialog.heighpar = 0.5;
			break;
		case 6:
			CustomSPDialog.heighpar = 0.5;
			break;
		case 7:
			CustomSPDialog.heighpar = 0.6;
			break;
		case 8:

			break;
		case 9:
			CustomSPDialog.heighpar = 0.7;
			break;
		default:
			CustomSPDialog.heighpar = 0.8;
			break;
		}
	}

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
