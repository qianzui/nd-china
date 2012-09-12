package com.hiapk.ui.scene;

import com.hiapk.util.SharedPrefrenceDataRegulate;

import android.content.Context;

public class SmsSet {
	public static String YD = "�й��ƶ�";
	public static String LT = "�й���ͨ";
	public static String DX = "�й�����";
	public static String LT2G = "��ͨ2G";
	public static String LT3G = "��ͨ��3G";

	public static void smsSet(Context context, String province,
			String operator, String brand) {
		SharedPrefrenceDataRegulate sharedData = new SharedPrefrenceDataRegulate(context);
		// һ����������
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
		// ��ͬʡ��
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("�㶫")) {
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
		if (province.equalsIgnoreCase("�Ϻ�")) {
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
		if (province.equalsIgnoreCase("���")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("YHCX");
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("10056");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("797");
			}
		}
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXGPRS");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("705");
			}
		}
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("�Ĵ�")) {
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
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("3053");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// �õ�����֧��
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("�ӱ�")) {
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
		if (province.equalsIgnoreCase("ɽ��")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("107");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("1071");
			}
		}
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("������")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("1071");
				}
			}
		}
		if (province.equalsIgnoreCase("���ɹ�")) {
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
		if (province.equalsIgnoreCase("ɽ��")) {
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
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("�㽭")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("1561");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("1071");
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("18");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("11803");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("����")) {
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
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXGPRS");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
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
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("7013");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXLL");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("CXTCSY");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("106");
			}
			if (operator.equalsIgnoreCase(LT)) {
				sharedData.setSmsText("CXLL");
			}
		}
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("TCCX");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
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
		if (province.equalsIgnoreCase("����")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("17*05#");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
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
		if (province.equalsIgnoreCase("�ຣ")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("30617");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
					defSms(context);
				}
				if (brand.equalsIgnoreCase(LT3G)) {
					sharedData.setSmsText("CXLL");
				}
			}
		}
		if (province.equalsIgnoreCase("�½�")) {
			if (operator.equalsIgnoreCase(YD)) {
				sharedData.setSmsText("108");
			}
			if (operator.equalsIgnoreCase(LT)) {
				if (brand.equalsIgnoreCase(LT2G)) {
					// ��֧��
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
