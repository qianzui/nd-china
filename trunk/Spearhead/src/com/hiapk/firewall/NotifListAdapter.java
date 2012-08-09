package com.hiapk.firewall;

import java.util.ArrayList;

import com.hiapk.dataexe.TrafficManager;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.spearhead.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotifListAdapter extends BaseAdapter{
	
	
	private LayoutInflater inflater;
	public Context mContext;
	public ArrayList<String[]> notifList;
	
	public NotifListAdapter(Context mContext,ArrayList<String[]> notifList){
		this.mContext = mContext;
		this.notifList = notifList;
		inflater = LayoutInflater.from(mContext);
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.notif_list_item, null);
			holder.icon = (ImageView)convertView.findViewById(R.id.notif_icon);
			holder.appname = (TextView)convertView.findViewById(R.id.notif_name);
			holder.traffic = (TextView)convertView.findViewById(R.id.notif_traffic);
			holder.content = (TextView)convertView.findViewById(R.id.notif_content);
			convertView.setTag(R.id.tag_notif_holder,holder);
		}else{
			holder = (ViewHolder)convertView.getTag(R.id.tag_notif_holder);
		}
		String[] notifRecord = notifList.get(position);
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pkgInfo = null;
		try {
			pkgInfo = pm.getPackageInfo(notifRecord[0],0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pkgInfo == null){
			holder.appname.setText("获取失败");
			holder.icon.setImageResource(R.drawable.ic_launcher);
		}else{
			holder.appname.setText(pkgInfo.applicationInfo.loadLabel(pm).toString());
			holder.icon.setImageDrawable(pkgInfo.applicationInfo.loadIcon(pm)); 
		}
		int uid = pkgInfo.applicationInfo.uid;
		long traffic[] = TrafficManager.getUidtraff(mContext, uid);
		holder.traffic.setText("总流量: " + UnitHandler.unitHandlerAccurate(traffic[0]));
		holder.content.setText(notifRecord[2]);
		convertView.setTag(R.id.tag_notif_pkgInfo,pkgInfo);
		convertView.setTag(R.id.tag_notif_id,notifRecord[1]);
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notifList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notifList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	class ViewHolder{
		ImageView icon;
		TextView appname;
		TextView traffic;
		TextView content;
	}
	
}
