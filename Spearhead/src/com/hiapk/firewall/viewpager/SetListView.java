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
import android.content.SharedPreferences;
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
	public boolean isLoadinged = false;
	public boolean firstLoad = true;

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
				if (FireWallActivity.mPop.isShowing()) {
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
		if (isLoadinged)
			return;
		isLoadinged = true;
		firstLoad = false;
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
		Block.saveRecord(mContext);
		loading.setVisibility(View.INVISIBLE);
	}

	HashMap<Integer, DatauidHash> uiddata = new HashMap<Integer, DatauidHash>();

	public HashMap<Integer, DatauidHash> getDataForList() {

		return uiddata;
	}

	public boolean isRuleChanged(int i) {
		switch (i) {
		case 0:
			if (!Block.get3g_set(mContext).equals(Block.old_3g0)
					|| !Block.getWifi_set(mContext).equals(Block.old_wifi0)) {
				Logs.i("test", "get rule 0:"
						+ Block.get3g_set(mContext).toString()
						+ Block.getWifi_set(mContext).toString());
				Logs.i("test", "old 0:" + Block.old_3g0.toString()
						+ Block.old_wifi0.toString());
				return true;
			} else {
				return false;
			}
		case 1:
			if (!Block.get3g_set(mContext).equals(Block.old_3g1)
					|| !Block.getWifi_set(mContext).equals(Block.old_wifi1)) {
				Logs.i("test", "get rule 1:"
						+ Block.get3g_set(mContext).toString()
						+ Block.getWifi_set(mContext).toString());
				Logs.i("test", "old 1:" + Block.old_3g1.toString()
						+ Block.old_wifi1.toString());
				return true;
			} else {
				return false;
			}
		case 2:
			if (!Block.get3g_set(mContext).equals(Block.old_3g2)
					|| !Block.getWifi_set(mContext).equals(Block.old_wifi2)) {
				Logs.i("test", "get rule 2:"
						+ Block.get3g_set(mContext).toString()
						+ Block.getWifi_set(mContext).toString());
				Logs.i("test", "old 2:" + Block.old_3g2.toString()
						+ Block.old_wifi2.toString());
				return true;
			} else {
				return false;
			}
		case 3:
			if (!Block.get3g_set(mContext).equals(Block.old_3g3)
					|| !Block.getWifi_set(mContext).equals(Block.old_wifi3)) {
				return true;
			} else {
				return false;
			}
		case 4:
			if (!Block.get3g_set(mContext).equals(Block.old_3g4)
					|| !Block.getWifi_set(mContext).equals(Block.old_wifi4)) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	public void menuDismiss() {
		if (menu != null)
			menu.ifShowingAndClose();
	}

	public void setLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	public void resetAdapter() {
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
