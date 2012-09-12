package com.hiapk.ui.scene;

import com.hiapk.util.SharedPrefrenceDataRegulate;

import android.content.Context;

public class SmsSet {
	public static String YD = "中国移动";
	public static String LT = "中国联通";
	public static String DX = "中国电信";
	public static String LT2G = "联通2G";
	public static String LT3G = "联通沃3G";

	public static void smsSet(Context context, String province,
			String operator, String brand) {
		SharedPrefrenceDataRegulate sharedData = new SharedPrefrenceDataRegulate(context);
		// 一样的先设置
		if (operator.equalsIgnoreCase(YD)) {
			sharedData.setSmsNum("10086");
		}
		if (operator.equalsIgnoreCase(LT)) {
			sharedData.setSmsNum("10010");
		}
		if (operator.equalsIgnoreCase(DX)) {
			sharedData.setSmsText("108");
			sharedData.setSmsNum("10001");
		}
		// 不同省份
		if (province.equalsIgnoreCase("北京")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXGLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("CXGPRS");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("广东")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("CXGPRSLL");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("上海")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("CXLL");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("天津")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("YHCX");
			}
		}
		if (province.equalsIgnoreCase("重庆")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("10056");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("797");
			}
		}
		if (province.equalsIgnoreCase("辽宁")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("LLCXGSM");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("江苏")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXGPRS");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("705");
			}
		}
		if (province.equalsIgnoreCase("湖北")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXSJLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("SJLL");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("四川")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("HF");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("1071");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("陕西")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("3053");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 该地区不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("河北")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("5011");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("7011#201203");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("山西")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("107");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("1071");
			}
		}
		if (province.equalsIgnoreCase("河南")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("603");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(DX)) {
				sharedData.setSmsText("506");
			}
		}
		if (province.equalsIgnoreCase("吉林")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("1173");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("7011");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("黑龙江")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("内蒙古")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXGPRS");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("519");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("山东")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("3");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("412");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("安徽")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("703");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("LL");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsNum("10655898");
					sharedData.setSmsText("LL");
				}
			}
		}
		if (province.equalsIgnoreCase("浙江")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("1561");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("1071");
			}
		}
		if (province.equalsIgnoreCase("福建")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("18");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("湖南")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("11803");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("广西")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					sharedData.setSmsText("SSLL");
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("江西")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXGPRS");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
			if (operator.equalsIgnoreCase(DX)) {
				sharedData.setSmsText("3535");
			}
		}
		if (province.equalsIgnoreCase("贵州")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("7013");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("云南")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("西藏")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXTCSY");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("海南")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("106");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("甘肃")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("TCCX");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
			if (operator.equalsIgnoreCase(DX)) {
				sharedData.setSmsText("151");
			}
		}
		if (province.equalsIgnoreCase("宁夏")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("17*05#");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
			if (operator.equalsIgnoreCase(DX)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("青海")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("30617");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("新疆")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("108");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// 不支持
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("2082");
				}
			}
		}

	}

	public static void defSms(Context context) {
		SharedPrefrenceDataRegulate sharedData = new SharedPrefrenceDataRegulate(context);
		sharedData.setSmsText("CXLL");
	}

}
