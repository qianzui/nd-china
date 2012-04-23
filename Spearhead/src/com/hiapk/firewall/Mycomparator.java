package com.hiapk.firewall;

import java.util.ArrayList;


public class Mycomparator  {



	public ArrayList<AppInfo> comparator(ArrayList<AppInfo> appList)
	{
		for(int i = appList.size()-1 ; i > 0 ; i--)
		{
			for(int j = 0 ;j<i ;j++)
			{
				if(appList.get(i).getTrafficTotalComparator() > appList.get(j).getTrafficTotalComparator() )
				{
					AppInfo appInfo = new AppInfo();
					
					appInfo.setAppname(appList.get(i).getAppname());
					appInfo.setIcon(appList.get(i).getIcon());
					appInfo.setPackageName(appList.get(i).getPackageName());
					appInfo.setTrafficDown(appList.get(i).getTrafficDown());
					appInfo.setTrafficTotal(appList.get(i).getTrafficTotal());
					appInfo.setTrafficTotalComparator(appList.get(i).getTrafficTotalComparator());
					appInfo.setTrafficUp(appList.get(i).getTrafficUp());
					
					
					appList.get(i).setAppname(appList.get(j).getAppname());
					appList.get(i).setIcon(appList.get(j).getIcon());
					appList.get(i).setPackageName(appList.get(j).getPackageName());
					appList.get(i).setTrafficDown(appList.get(j).getTrafficDown());
					appList.get(i).setTrafficTotal(appList.get(j).getTrafficTotal());
					appList.get(i).setTrafficTotalComparator(appList.get(j).getTrafficTotalComparator());
					appList.get(i).setTrafficUp(appList.get(j).getTrafficUp());
					
					
					appList.get(j).setAppname(appInfo.getAppname());
					appList.get(j).setIcon(appInfo.getIcon());
					appList.get(j).setPackageName(appInfo.getPackageName());
					appList.get(j).setTrafficDown(appInfo.getTrafficDown());
					appList.get(j).setTrafficTotal(appInfo.getTrafficTotal());
					appList.get(j).setTrafficTotalComparator(appInfo.getTrafficTotalComparator());
					appList.get(j).setTrafficUp(appInfo.getTrafficUp());
					
				}
			}
		}
		
		return appList;
	}

}
