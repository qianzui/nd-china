package com.hiapk.firewall;

import java.text.Collator;
import java.util.Comparator;

public class MyComparator implements  Comparator {

	@Override
	public int compare(Object one, Object two) {
		Collator myCollator = Collator.getInstance(java.util.Locale.CHINA);
		// TODO Auto-generated method stub
		AppInfo ai1 = (AppInfo)one;
		AppInfo ai2 = (AppInfo)two;
	    String name1 = ai1.appname.replaceAll(" ","").replaceAll(" ","");
	    String name2 = ai2.appname.replaceAll(" ","").replaceAll(" ","");
		if(myCollator.compare(name1, name2) < 0){
			return -1;
		}else if(myCollator.compare(name1, name2) > 0){
			return 1;
		}else{
			return 0;
		}
	}
}
