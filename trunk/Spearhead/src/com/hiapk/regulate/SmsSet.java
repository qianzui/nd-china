package com.hiapk.regulate;

public class SmsSet {
	public static String YD = "�й��ƶ�";
	public static String LT = "�й���ͨ";
	public static String DX = "�й�����";
	public static String LT2G = "��ͨ2G";
	public static String LT3G = "��ͨ��3G";

	public static void smsSet(String province,String operator,String brand){
		//һ����������
		if(operator.equalsIgnoreCase(YD)){			
			Regulate.smsNum.setText("10086");
		}
		if(operator.equalsIgnoreCase(LT)){			
			Regulate.smsNum.setText("10010");
		}
		if(operator.equalsIgnoreCase(DX)){
			Regulate.smsText.setText("108");
			Regulate.smsNum.setText("10001");
		}
		//��ͬʡ��
		if(province.equalsIgnoreCase("����")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("CXGLL");
			}
			if(operator.equalsIgnoreCase(LT)){
				if(brand.equalsIgnoreCase(LT2G)){
					Regulate.smsText.setText("1071");				
				}
				if(brand.equalsIgnoreCase(LT3G)){
					Regulate.smsText.setText("CXLL");			
				}
			}			
		}
		if(province.equalsIgnoreCase("�㶫")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("CXLL");
			}
			if(operator.equalsIgnoreCase(LT)){
				if(brand.equalsIgnoreCase(LT2G)){
					Regulate.smsText.setText("CXGPRSLL");				
				}
				if(brand.equalsIgnoreCase(LT3G)){
					Regulate.smsText.setText("CXLL");			
				}
			}			
		}
		if(province.equalsIgnoreCase("�Ϻ�")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("CXLL");
			}
			if(operator.equalsIgnoreCase(LT)){
				if(brand.equalsIgnoreCase(LT2G)){
					Regulate.smsText.setText("CXLL");				
				}
				if(brand.equalsIgnoreCase(LT3G)){
					Regulate.smsText.setText("1071");			
				}
			}			
		}
		if(province.equalsIgnoreCase("���")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("CXLL");
			}
			if(operator.equalsIgnoreCase(LT)){				
				Regulate.smsText.setText("YHCX");
			}			
		}
		if(province.equalsIgnoreCase("����")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("10056");
			}
			if(operator.equalsIgnoreCase(LT)){				
				Regulate.smsText.setText("797");
			}			
		}
		if(province.equalsIgnoreCase("����")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("CXLL");
			}
			if(operator.equalsIgnoreCase(LT)){
				if(brand.equalsIgnoreCase(LT2G)){
					Regulate.smsText.setText("LLCXGSM");				
				}
				if(brand.equalsIgnoreCase(LT3G)){
					Regulate.smsText.setText("CXLL");			
				}
			}			
		}
		


	}

	public static void defSms(){
		
		Regulate.smsText.setText("CXLL");
	}

}
