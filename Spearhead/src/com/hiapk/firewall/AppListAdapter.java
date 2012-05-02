package com.hiapk.firewall;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AppListAdapter extends BaseAdapter {
	
	private ArrayList<PackageInfo> myAppList;
	private  LayoutInflater inflater;
	private Context mContext;
	
	public AppListAdapter(Context context , ArrayList<PackageInfo> myAppList)
	{
		inflater = LayoutInflater.from(context);
		this.myAppList = myAppList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myAppList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return myAppList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder ;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.app_list_item,null);
			holder.icon = (ImageView)convertView.findViewById(R.id.icon);
			holder.appname = (TextView)convertView.findViewById(R.id.app_name);
			holder.trafficup = (TextView)convertView.findViewById(R.id.trafficup);
			holder.e_toggle = (CheckBox)convertView.findViewById(R.id.e_toggle);
			holder.wifi_toggle = (CheckBox)convertView.findViewById(R.id.wifi_toggle);
//			isClick(holder.e_toggle);
//			isClick( holder.wifi_toggle);
			
			
			convertView.setTag(R.id.tag_holder,holder);
		 }else{
			 holder = (ViewHolder)convertView.getTag(R.id.tag_holder);
		 }
		  PackageInfo pkgInfo = myAppList.get(position);
		  
		  long down = TrafficStats.getUidRxBytes(pkgInfo.applicationInfo.uid);
		  down = judge(down);
		  long up = TrafficStats.getUidTxBytes(pkgInfo.applicationInfo.uid);
		  up = judge(up);
		  holder.icon.setImageDrawable(pkgInfo.applicationInfo.loadIcon(mContext.getPackageManager()));
		  holder.appname.setText(pkgInfo.applicationInfo.loadLabel(mContext.getPackageManager()));
		  holder.trafficup.setText("总流量： " + unitHandler(up + down));	
		  holder.e_toggle.setOnCheckedChangeListener(new ECheckBoxListener(holder.e_toggle));
		  holder.wifi_toggle.setOnCheckedChangeListener(new WifiCheckBoxListener(holder.wifi_toggle));	

		  convertView.setTag(R.id.tag_pkgname,pkgInfo.applicationInfo.packageName);
		  convertView.setTag(R.id.tag_traffic_up ,unitHandler(up));
		  convertView.setTag(R.id.tag_traffic_down ,unitHandler(down));
		  
		  return convertView;
	}
	
	public void isClick(CheckBox cb)
	{
		if(isRoot())
		{
			cb.setClickable(true);
		}else
		{
			cb.setClickable(false);
			Toast.makeText(mContext, "root first", Toast.LENGTH_LONG).show();
		}
	}
	
	public static boolean isRoot() {
		boolean blnResult = false;
		File su = new File("/tmp/su.txt");
		FileOutputStream fos = null;
		try {
		     fos = new FileOutputStream(su);
		} catch (Exception e) {
		     e.printStackTrace();
		   } finally {
	     if (fos != null) {
	 	    try {
		          fos.close();
		         } catch (Exception e) {
		        e.printStackTrace();
	              	}
	              }
	          	}
		if (su.exists()) {
		   blnResult = true;
		   try {
		     su.delete();
		       } catch (SecurityException e) {
	             	e.printStackTrace();
	           	}
	    	}
		return blnResult;
		}
	public long judge(long tff)
	{
		if(tff == -1)
		tff = 0 ;
		return tff;
	}
	class ViewHolder{
		ImageView icon;
		TextView appname;
		TextView trafficup;
		CheckBox e_toggle;
		CheckBox wifi_toggle;
	}
	class ECheckBoxListener implements OnCheckedChangeListener
	{
        CheckBox cb;
        public ECheckBoxListener(CheckBox cb)
        {
        	this.cb = cb ;
        }
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			
			// TODO Auto-generated method stub
	        if(cb.isChecked())
	        {
	        	if(isRoot())
	        	{
	        		Toast.makeText(mContext, "rooted", Toast.LENGTH_SHORT).show();
	        	}else
	        	{
	        		cb.setChecked(false);
	        		Toast.makeText(mContext, "root first!", Toast.LENGTH_SHORT).show();
	        	}
	        }else{
	        	Toast.makeText(mContext, "关1", Toast.LENGTH_SHORT).show();
	        }
		}
	  }
	public String unitHandler(long count) {
		String value = null;
		long temp = count;
		float floatnum = count;
		if ((temp = temp / 1000) < 1) {
			value = count + "B";
		} else if ((floatnum = (float) temp / 1000) < 1) {
			value = temp + "KB";
		} else {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatnum) + "MB";
		}
		return value;
	}
	class WifiCheckBoxListener implements OnCheckedChangeListener
	{
        CheckBox cb;
        public WifiCheckBoxListener(CheckBox cb)
        {
        	this.cb = cb ;
        }
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			
			// TODO Auto-generated method stub
	        if(cb.isChecked())
	        {
	        	Toast.makeText(mContext, "开2", Toast.LENGTH_SHORT).show();
	        }else{
	        	Toast.makeText(mContext, "关2", Toast.LENGTH_SHORT).show();
	        }
		}
	  }
	
}








