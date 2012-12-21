package com.hiapk.firewall.viewpager;

import java.util.ArrayList;

import com.hiapk.comparator.ComparatorUtil;
import com.hiapk.control.traff.NotificationInfo;
import com.hiapk.firewall.AppListAdapter;
import com.hiapk.firewall.FireWallItemMenu;
import com.hiapk.firewall.MyListView;
import com.hiapk.firewall.NotifListAdapter;
import com.hiapk.firewall.MyListView.OnRefreshListener;
import com.hiapk.firewall.viewpager.SetListView.OnDragRefreshListener;
import com.hiapk.logs.Logs;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;
import com.hiapk.ui.custom.CustomDialogMain2Been;
import com.hiapk.util.SharedPrefrenceData;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class SetNotifListView {
	public Context mContext;
	public View view;
	public MyListView appListView;
	public SharedPrefrenceData sharedpref;
	public FireWallItemMenu menu = null;
	private OnDragNotifRefreshListener onDragNotifRefreshListener;
	public LinearLayout loading;
	
	protected ArrayList<String[]> notificationInfos;

	public SetNotifListView(View view, Context mContext) {
		this.mContext = mContext;
		this.view = view;
		sharedpref = new SharedPrefrenceData(mContext);
		notificationInfos = new ArrayList<String[]>();
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

	public void setAdapter() {
		Logs.i("test", "setNotifAdapter");
		setLoading();
		notificationInfos = NotificationInfo
				.getNotificationApp(NotificationInfo.notificationRes);
		notificationInfos = ComparatorUtil.compNotifList(mContext,
				notificationInfos);
		final NotifListAdapter notifAdapter = new NotifListAdapter(mContext,
				notificationInfos);
		appListView.setAdapter(notifAdapter);

		appListView.setonRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						if (NotificationInfo.notificationRes.length() == 0) {
							if (NotificationInfo.isgettingdata == false) {
								onDragNotifRefreshListener.onDragRefresh();
							}
						} else {
							setAdapter();
						}
					}
				}.execute();
			}
		});
		loading.setVisibility(View.INVISIBLE);
		if(sharedpref.getFireWallType() ==5){
			Logs.i("test", "notificationRes be clear");
			NotificationInfo.notificationRes = new StringBuilder();
		}
	}

	public void menuDismiss() {
		if (menu != null)
			menu.ifShowingAndClose();

	}

	public void setLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	public interface OnDragNotifRefreshListener {
		public void onDragRefresh();
	}

	public void completeRefresh() {
		appListView.onRefreshComplete();
		AppListAdapter.syncImageLoader.setLoadLimit(0, 10);
		AppListAdapter.syncImageLoader.unlock();
	}

	public void setOnDragNotifRefreshListener(
			OnDragNotifRefreshListener onDragNotifRefreshListener) {
		this.onDragNotifRefreshListener = onDragNotifRefreshListener;
	}

}
