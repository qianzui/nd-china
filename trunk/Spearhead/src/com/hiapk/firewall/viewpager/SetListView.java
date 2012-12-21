package com.hiapk.firewall.viewpager;

import java.util.ArrayList;
import java.util.HashMap;

import com.hiapk.bean.DatauidHash;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.Block;
import com.hiapk.firewall.FireWallItemMenu;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.logs.Logs;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class SetListView {
	public View view;
	public Context mContext;
	public MyListView appListView;
	public SharedPrefrenceData sharedpref;
	public FireWallItemMenu menu = null;
	private OnDragRefreshListener onDragRefreshListener;
	public LinearLayout loading;
    public boolean isLoadinged;
	public SetListView(View view, Context mContext) {
		this.view = view;
		this.mContext = mContext;
		sharedpref = new SharedPrefrenceData(mContext);
		initView();
	}

	public void initView() {
		appListView = (MyListView) view.findViewById(R.id.app_list);
		loading = (LinearLayout) view.findViewById(R.id.loading_content);
		appListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(FireWallActivity.mPop.isShowing()){
					FireWallActivity.mPop.dismiss();
				}
				int type = 1;
				if (sharedpref.getFireWallType() == 5) {
					type = 2;
				}
				menu = new FireWallItemMenu(mContext, arg1, type);
				menu.show();
			}
		});
	}

	public void setAdapter(ArrayList<PackageInfo> myAppList) {
		if(isLoadinged)
			return;
		isLoadinged = true;
		loading.setVisibility(View.VISIBLE);
		Logs.i("test", "set Adapter");
		AppListAdapter appListAdapter = new AppListAdapter(mContext, myAppList,
				Block.appnamemap, SQLStatic.uiddata, Block.appList,
				FireWallActivity.uidList, appListView);
		appListView.setAdapter(appListAdapter);
		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				onDragRefreshListener.onDragRefresh();
			}
		});
		loading.setVisibility(View.INVISIBLE);
	}
	HashMap<Integer, DatauidHash> uiddata = new HashMap<Integer, DatauidHash>();
	public HashMap<Integer, DatauidHash> getDataForList(){
		
		
		return uiddata;
	}

	public void menuDismiss() {
		if (menu != null)
			menu.ifShowingAndClose();
	}

	public void setLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	public void resetAdaper(){
		isLoadinged = false;
	}
	public void compeletRefresh() {
		appListView.onRefreshComplete();
		AppListAdapter.syncImageLoader.setLoadLimit(0, 10);
		AppListAdapter.syncImageLoader.unlock();
	}

	public interface OnDragRefreshListener {
		public void onDragRefresh();
	}

	public void setOnDragRefreshListener(
			OnDragRefreshListener onDragRefreshListener) {
		this.onDragRefreshListener = onDragRefreshListener;
	}

}
