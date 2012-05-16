package com.hiapk.firewall;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
import android.util.Log;
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
	HashMap map;
	
	public AppListAdapter(Context context , ArrayList<PackageInfo> myAppList ,HashMap<Integer,IsChecked> map)
	{
		inflater = LayoutInflater.from(context);
		this.myAppList = myAppList;
		this.mContext = context;
		this.map = map;
//        this.map = Block.getMap(mContext, myAppList);
		
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
		  
		
		  IsChecked ic = (IsChecked)map.get(pkgInfo.applicationInfo.uid);
		  holder.e_toggle.setOnCheckedChangeListener(new ECheckBoxListener(holder.e_toggle,ic));
		  holder.wifi_toggle.setOnCheckedChangeListener(new WifiCheckBoxListener(holder.wifi_toggle,ic));
		  holder.e_toggle.setChecked(ic.selected_3g);
		  holder.wifi_toggle.setChecked(ic.selected_wifi);
		  
		  convertView.setTag(R.id.tag_pkgname,pkgInfo.applicationInfo.packageName);
		  convertView.setTag(R.id.tag_traffic_up ,unitHandler(up));
		  convertView.setTag(R.id.tag_traffic_down ,unitHandler(down));
		  convertView.setTag(R.id.tag_map,map);
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
	//判断手机是否root
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
	
	//单位处理
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
	
//	public static void saveAndApply(Context context){
//		 Block.saveRules(context,map);
//		 if(Block.applyIptablesRules(context,true)){
//			   Toast.makeText(context, "iptables规则已应用！", Toast.LENGTH_SHORT).show();
//		    }else{
//			   Toast.makeText(context, "写入规则失败！", Toast.LENGTH_SHORT).show();
//			}
//	}
	
	class ECheckBoxListener implements OnCheckedChangeListener
	{
        CheckBox cb;
        IsChecked ic;
        public ECheckBoxListener(CheckBox cb,IsChecked ic)
        {
        	this.cb = cb ;
        	this.ic = ic;
        }
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			
			// TODO Auto-generated method stub
			 if(isRoot())
	          {
		        ic.selected_3g = isChecked;
//		        Block.saveRules(mContext,map);
//				if(Block.applyIptablesRules(mContext,true)){
//					   Toast.makeText(mContext, "iptables规则已应用！", Toast.LENGTH_SHORT).show();
//				    }else{
//					   Toast.makeText(mContext, "写入规则失败！", Toast.LENGTH_SHORT).show();
//					}
		        	
		        }else{
		        	 cb.setChecked(false);
		        	 AlertDialog alt = new AlertDialog.Builder(mContext)
		 			 .setTitle("提示")
		 			 .setMessage("此功能需要Root权限！")
		 			 .setPositiveButton(
							 "确定",
							 new DialogInterface.OnClickListener() {
								 @Override
								 public void onClick(
										 DialogInterface dialog,
										 int which) {
									 // TODO Auto-generated
									 // method stub
								}
							}).show();
		        	}
		}
	  }
	class WifiCheckBoxListener implements OnCheckedChangeListener
	{
        CheckBox cb;
        IsChecked ic;
        public WifiCheckBoxListener(CheckBox cb,IsChecked ic)
        {
        	this.cb = cb ;
        	this.ic = ic;
        }
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			
			// TODO Auto-generated method stub
		  if(isRoot())
          {
			  ic.selected_wifi = isChecked;
//		      Block.saveRules(mContext,map);
		      
//			  if(Block.applyIptablesRules(mContext,true)){
//					   Toast.makeText(mContext, "iptables规则已应用！", Toast.LENGTH_SHORT).show();
//				    }else{
//					   Toast.makeText(mContext, "写入规则失败！", Toast.LENGTH_SHORT).show();
//					}


			  
	        }else{
	        	 cb.setChecked(false);
	        	 AlertDialog alt = new AlertDialog.Builder(mContext)
	 			 .setTitle("提示")
	 			 .setMessage("此功能需要Root权限！")
	 			 .setPositiveButton(
						 "确定",
						 new DialogInterface.OnClickListener() {
							 @Override
							 public void onClick(
									 DialogInterface dialog,
									 int which) {
								 // TODO Auto-generated
								 // method stub
							}
						}).show();
	        	}}}

    

	
}








