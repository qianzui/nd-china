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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		  holder.e_toggle.setOnClickListener(new EListener(holder.e_toggle,ic));
		  holder.wifi_toggle.setOnClickListener(new WifiListener(holder.wifi_toggle,ic));
		  holder.e_toggle.setChecked(ic.selected_3g);
		  holder.wifi_toggle.setChecked(ic.selected_wifi);
		  
		  convertView.setTag(R.id.tag_pkgname,pkgInfo.applicationInfo.packageName);
		  convertView.setTag(R.id.tag_traffic_up ,unitHandler(up));
		  convertView.setTag(R.id.tag_traffic_down ,unitHandler(down));
		  convertView.setTag(R.id.tag_map,map);
		  return convertView;
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
				final boolean isChecked) {
			
			// TODO Auto-generated method stub
			 if(Root.isDeviceRooted())
	          {
				 if(ic.selected_3g != isChecked){
					  Block.isChanged = true;
				  }
				 
				 if(Block.isShowTip(mContext)){
				 new AlertDialog.Builder(mContext)
				 .setTitle("提示")
				 .setMessage("此功能需要root权限，是否获取？")
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Block.isShowTipSet(mContext, false);
						final ProgressDialog progress = ProgressDialog.show(mContext,
								"提示","处理中", true);						
						if(GetRoot.hasRootAccess(mContext, true)){
							
				            ic.selected_3g = isChecked;
					       }
						progress.dismiss();
					   } 
				    })
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						cb.setChecked(false);
						ic.selected_3g = false;
						Block.isChanged = false;
					}
				}).show();
				 }
		        }else{
		        	 cb.setChecked(false);
		        	 new AlertDialog.Builder(mContext)
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
		  if(Root.isDeviceRooted())
          {     
			  
			  if(ic.selected_wifi != isChecked){
				  Block.isChanged = true;
			  }
			  ic.selected_wifi = isChecked;
			  
	        }else{
	        	 cb.setChecked(false);
	        	 new AlertDialog.Builder(mContext)
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

    class EListener implements OnClickListener{
    	CheckBox cb;
    	IsChecked ic;
    	public  EListener(CheckBox cb,IsChecked ic){
    		this.cb = cb;
    		this.ic = ic;
    	}	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 if(Root.isDeviceRooted())
	          {
				 if(ic.selected_3g != cb.isChecked()){
					  Block.isChanged = true;
				  }
				 if(Block.isShowTip(mContext)){
				 new AlertDialog.Builder(mContext)
				 .setTitle("提示")
				 .setMessage("此功能需要root权限，是否获取？")
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Block.isShowTipSet(mContext, false);
//						final ProgressDialog progress = ProgressDialog.show(mContext,
//								"提示","处理中", true);	
//						progress.dismiss();
						if(GetRoot.hasRootAccess(mContext, true)){
				            ic.selected_3g = cb.isChecked();
					       }else{
					    	cb.setChecked(false);
					       }

					   } 
				    })
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						cb.setChecked(false);
						ic.selected_3g = false;
						Block.isChanged = false;
					}
				}).show();
				 }else{
					 if(GetRoot.hasRootAccess(mContext, true)){
				            ic.selected_wifi = cb.isChecked();
					       }else{
					    	cb.setChecked(false);
					       }
				       }
		        }else{
		        	 cb.setChecked(false);
		        	 new AlertDialog.Builder(mContext)
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
    class WifiListener implements OnClickListener{
    	CheckBox cb;
    	IsChecked ic;
    	public  WifiListener(CheckBox cb,IsChecked ic){
    		this.cb = cb;
    		this.ic = ic;
    	}	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 if(Root.isDeviceRooted())
	          {
				 if(ic.selected_wifi != cb.isChecked()){
					  Block.isChanged = true;
				  }
				 
				 if(Block.isShowTip(mContext)){
				 new AlertDialog.Builder(mContext)
				 .setTitle("提示")
				 .setMessage("此功能需要root权限，是否获取？")
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Block.isShowTipSet(mContext, false);
//						final ProgressDialog progress = ProgressDialog.show(mContext,
//								"提示","处理中", true);	
//						progress.dismiss();
						if(GetRoot.hasRootAccess(mContext, true)){
				            ic.selected_wifi = cb.isChecked();
					       }else{
					    	cb.setChecked(false);
					       }
					   } 
				    })
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						cb.setChecked(false);
						ic.selected_wifi = false;
						Block.isChanged = false;
					}
				}).show();
				 }else{
					 if(GetRoot.hasRootAccess(mContext, true)){
				            ic.selected_wifi = cb.isChecked();
					       }else{
					    	cb.setChecked(false);
					       }
				 }
		        }else{
		        	 cb.setChecked(false);
		        	 new AlertDialog.Builder(mContext)
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

	
}








