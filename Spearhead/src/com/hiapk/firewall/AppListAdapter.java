package com.hiapk.firewall;

import java.util.ArrayList;

import com.hiapk.spearhead.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
	
	private ArrayList<PackageInfo> installedPackageList;
	private  LayoutInflater inflater;
	private Context mContext;
	
	public AppListAdapter(Context context , ArrayList<PackageInfo> installedPackageList)
	{
		inflater = LayoutInflater.from(context);
		this.installedPackageList = installedPackageList;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return installedPackageList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return installedPackageList.get(position);
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
			holder.trafficdown = (TextView)convertView.findViewById(R.id.trafficdown);
			holder.e_toggle = (CheckBox)convertView.findViewById(R.id.e_toggle);
			holder.wifi_toggle = (CheckBox)convertView.findViewById(R.id.wifi_toggle);
			convertView.setTag(R.id.tag_holder,holder);
		 }else{
			 holder = (ViewHolder)convertView.getTag(R.id.tag_holder);
		 }
		  PackageInfo pkgInfo = installedPackageList.get(position);
		  holder.icon.setImageDrawable(pkgInfo.applicationInfo.loadIcon(mContext.getPackageManager()));
		  holder.appname.setText("名称： "+pkgInfo.applicationInfo.loadLabel(mContext.getPackageManager()));
		  holder.trafficdown.setText("接收： " + TrafficStats.getUidRxBytes(pkgInfo.applicationInfo.uid)/1024/1024 + "KB");
		  holder.trafficup.setText("发送： " + TrafficStats.getUidTxBytes(pkgInfo.applicationInfo.uid)/1024 + "KB");		
		  holder.e_toggle.setOnCheckedChangeListener(new ECheckBoxListener(holder.e_toggle));
		  holder.wifi_toggle.setOnCheckedChangeListener(new WifiCheckBoxListener(holder.wifi_toggle));		  
		  convertView.setTag(R.id.tag_pkgname,pkgInfo.applicationInfo.packageName);
		  return convertView;
	}
	class ViewHolder{
		ImageView icon;
		TextView appname;
		TextView trafficup;
		TextView trafficdown;
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
	        	Toast.makeText(mContext, "开1", Toast.LENGTH_SHORT).show();
	        }else{
	        	Toast.makeText(mContext, "关1", Toast.LENGTH_SHORT).show();
	        }
		}
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








