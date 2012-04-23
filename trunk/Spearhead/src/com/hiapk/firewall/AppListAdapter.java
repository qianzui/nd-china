package com.hiapk.firewall;

import java.util.ArrayList;
import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.R;
import android.content.Context;
import android.content.DialogInterface;
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
	
	private ArrayList<AppInfo> myAppList;
	private  LayoutInflater inflater;
	private Context mContext;
	
	public AppListAdapter(Context context , ArrayList<AppInfo> myAppList)
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
			holder.trafficdown = (TextView)convertView.findViewById(R.id.trafficdown);
			holder.e_toggle = (CheckBox)convertView.findViewById(R.id.e_toggle);
			holder.wifi_toggle = (CheckBox)convertView.findViewById(R.id.wifi_toggle);
			convertView.setTag(R.id.tag_holder,holder);
		 }else{
			 holder = (ViewHolder)convertView.getTag(R.id.tag_holder);
		 }
		  AppInfo pkgInfo = myAppList.get(position);		  
		  holder.icon.setImageDrawable(pkgInfo.getIcon());
		  holder.appname.setText(pkgInfo.getAppname());
//		  holder.trafficdown.setText("总上传： " + pkgInfo.getTrafficUp());
		  holder.trafficup.setText("总流量： " + pkgInfo.getTrafficTotal());		
		  holder.e_toggle.setOnCheckedChangeListener(new ECheckBoxListener(holder.e_toggle));
		  holder.wifi_toggle.setOnCheckedChangeListener(new WifiCheckBoxListener(holder.wifi_toggle));	
		  
		  convertView.setTag(R.id.tag_pkgname,pkgInfo.getPackageName());
		  convertView.setTag(R.id.tag_traffic_up ,pkgInfo.getTrafficUp());
		  convertView.setTag(R.id.tag_traffic_down ,pkgInfo.getTrafficDown());
		  
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








