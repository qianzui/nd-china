package com.wind.androiddev.getapplist;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppInfoAdapter extends BaseAdapter {
	Context context;
	ArrayList<AppInfo> datalist=new ArrayList<AppInfo>();
	public AppInfoAdapter(Context context,ArrayList<AppInfo> inputDatalist){
		this.context=context;
		datalist.clear();
		for (int i = 0; i < inputDatalist.size(); i++) {
			datalist.add(inputDatalist.get(i));
		}
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datalist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		final AppInfo appUnit=datalist.get(position);
		if (v==null) {
			LayoutInflater vi= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v=vi.inflate(R.layout.app_row, null);
			v.setClickable(true);
		}
		TextView appName=(TextView) v.findViewById(R.id.appName);
		TextView appPackageName=(TextView) v.findViewById(R.id.appPackageName);
		ImageView appIcon=(ImageView) v.findViewById(R.id.appIcon);
		if (appName!=null) {
			appName.setText(appUnit.appName);
		}
		if (appPackageName!=null) {
			appPackageName.setText(appUnit.packageName);
		}
		if (appIcon!=null) {
			appIcon.setImageDrawable(appUnit.appIcon);
		}
		return v;
	}

}
