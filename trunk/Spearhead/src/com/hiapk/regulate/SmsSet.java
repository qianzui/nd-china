package com.hiapk.regulate;

public class SmsSet {
	public static String YD = "中国移动";
	public static String LT = "中国联通";
	public static String DX = "中国电信";
	public static String LT2G = "联通2G";
	public static String LT3G = "联通沃3G";

	public static void smsSet(String province,String operator,String brand){
		//一样的先设置
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
		//不同省份
		if(province.equalsIgnoreCase("北京")){
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
		if(province.equalsIgnoreCase("广东")){
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
		if(province.equalsIgnoreCase("上海")){
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
		if(province.equalsIgnoreCase("天津")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("CXLL");
			}
			if(operator.equalsIgnoreCase(LT)){				
				Regulate.smsText.setText("YHCX");
			}			
		}
		if(province.equalsIgnoreCase("重庆")){
			if(operator.equalsIgnoreCase(YD)){
				Regulate.smsText.setText("10056");
			}
			if(operator.equalsIgnoreCase(LT)){				
				Regulate.smsText.setText("797");
			}			
		}
		if(province.equalsIgnoreCase("辽宁")){
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
