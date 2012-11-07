package com.hiapk.firewall;

import com.hiapk.logs.Logs;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;

import android.Manifest;
import android.view.View;

import com.hiapk.ui.custom.CustomDialog;
import com.hiapk.ui.custom.CustomPopupWindow;
import com.hiapk.ui.scene.UidMonthTraff;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.Extra;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.UnitHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FireWallItemMenu extends CustomPopupWindow implements OnClickListener {

	public int MENU_FIRE = 1;
	public int MENU_NOTIF = 2;
	public int menu_type = 1;
	public View root;
	public LayoutInflater inflater;
	public Context context;
	public SharedPrefrenceData sharedpref;
	public LinearLayout fire_menu;
	public Button bt_manager;
	public Button bt_detail;
	public Button bt_uninstall;
	public PackageInfo pkgInfo = null;
	public int uid;
	public String pkgname = "";
	public String appname = "";
	public String savedPkgname_wifi;
	public String savedPkgname_3g;
	public SharedPreferences prefs;
	
	public FireWallItemMenu(Context mContext,View anchor,int type) {
		super(mContext, anchor, type);
		this.menu_type = type;
//		context	= anchor.getContext();
	    this.context = mContext;
		sharedpref = new SharedPrefrenceData(context);
		getAppMsg(anchor);
		setView();
		
	}
	public void getAppMsg(View anchor){
		switch (menu_type) {
		case 1:
			pkgInfo = (PackageInfo) anchor.getTag(R.id.tag_pkginfo);
			break;
		case 2:
			prefs = context.getSharedPreferences(Block.PREFS_NAME, 0);
			pkgInfo = (PackageInfo) anchor.getTag(R.id.tag_notif_pkgInfo);
			break;
		
		}
		if(pkgInfo == null){
			Logs.i("test", "pkgInfo is null");
		}else{
			uid = pkgInfo.applicationInfo.uid;
			pkgname = pkgInfo.applicationInfo.packageName;
			appname = pkgInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
			Logs.i("test", pkgname);
		}
	}

	public void setView(){
		inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		root		= (ViewGroup) inflater.inflate(R.layout.fire_item_menu, null);
		fire_menu   = (LinearLayout) root.findViewById(R.id.fire_menu);
		Button bt_manager = (Button) root.findViewById(R.id.fire_item_manage);
		Button bt_detail = (Button) root.findViewById(R.id.fire_item_detail);
		Button bt_uninstall = (Button) root.findViewById(R.id.fire_item_uninstalled);
		bt_manager.setBackgroundResource(SkinCustomMains.buttonBackgroundDark());
		bt_detail.setBackgroundResource(SkinCustomMains.buttonBackgroundDark());
		bt_uninstall.setBackgroundResource(SkinCustomMains.buttonBackgroundDark());
		bt_manager.setOnClickListener(this);
		bt_uninstall.setOnClickListener(this);
		bt_detail.setOnClickListener(this);
		if (menu_type == 2) {
			bt_detail.setText("禁止联网");
			if (!isBanNewWork()) {
				bt_detail.setTextColor(Color.GRAY);
			}
		}
		setContentView(root);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fire_item_manage:
			this.dismiss();
			Extra.showInstalledAppDetails(context, pkgname);
			break;
		case R.id.fire_item_detail:
			detailClicked();
			break;
		case R.id.fire_item_uninstalled:
			this.dismiss();
			Uri uri = Uri.fromParts("package", pkgname, null);
			Intent intent = new Intent(Intent.ACTION_DELETE, uri);
			((Activity) context).startActivityForResult(intent,menu_type);
			break;
		}
	}
	

	
	/**
	 * Show popup window
	 */
	public void show () {
		preShow();
		int[] location = new int[2];
		anchor.getLocationOnScreen(location);
		Rect anchorRect 	= new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] 
		                	+ anchor.getHeight());
		root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		root.measure(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int rootWidth 		= root.getMeasuredWidth();
//		int rootHeight 		= root.getMeasuredHeight();
		int screenWidth 	= windowManager.getDefaultDisplay().getWidth();
		int screenHeight 	= windowManager.getDefaultDisplay().getHeight();
		int xPos 			= (screenWidth - rootWidth) / 2;
		int yPos	 		= anchorRect.top - 80;
		boolean onTop		= true;
		if ( screenHeight/2 > anchor.getBottom()) {
			yPos 	= anchorRect.bottom;
			onTop	= false;
		}
		showArrow(((onTop) ? R.drawable.fire_menu_up: R.drawable.fire_menu_down));
		window.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}
	private void showArrow(int whichArrow) {
		fire_menu.setBackgroundResource(whichArrow);
	}
	
	public void detailClicked(){
		switch(menu_type){
		case 1:
			showTraffDetail();
			break;
		case 2:
			banNetwork();
			break;
		}
	}
	public void ifShowingAndClose(){
		if(root.isShown()){
			this.dismiss();
		}
	}
	public void showTraffDetail() {
		this.dismiss();
		LayoutInflater infalter = LayoutInflater.from(context);
		final View mDetailView = infalter.inflate(R.layout.fire_detail, null);
		final CustomDialog detailDialog = new CustomDialog.Builder(context)
				.setContentView(mDetailView).setTitle("流量详情")
				.setPositiveButton("确定", null)
				.setNegativeButton("历史记录", null).create();
		detailDialog.show();
		final TextView traffic_up = (TextView) mDetailView
				.findViewById(R.id.fire_traffic_up);
		final TextView traffic_down = (TextView) mDetailView
				.findViewById(R.id.fire_traffic_down);
		final Button detail_ok = (Button) detailDialog
				.findViewById(R.id.positiveButton);
		final Button detail_history = (Button) detailDialog
				.findViewById(R.id.negativeButton);

		if (SQLStatic.uiddata != null) {
			if (sharedpref.getFireWallType() == 3) {
				traffic_up.setText("上传： "
						+ UnitHandler
								.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getUploadmobile()));
				traffic_down.setText("下载： "
						+ UnitHandler
								.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid)
										.getDownloadmobile()));
			} else if (sharedpref.getFireWallType() == 4) {
				traffic_up.setText("上传： "
						+ UnitHandler
								.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getUploadwifi()));
				traffic_down.setText("下载： "
						+ UnitHandler
								.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getDownloadwifi()));
			} else {
				traffic_up.setText("上传： "
						+ UnitHandler
								.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getAllUpload()));
				traffic_down.setText("下载： "
						+ UnitHandler
								.unitHandlerAccurate(SQLStatic.uiddata
										.get(uid).getAllDownload()));
			}
		}

		detail_ok.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				detailDialog.cancel();
			}
		});
		detail_history
				.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(context,UidMonthTraff.class);
						Bundle bData = new Bundle();
						bData.putInt("uid", uid);
						bData.putString("appname", appname);
						bData.putString("pkname", pkgname);
						intent.putExtras(bData);
						context.startActivity(intent);
						detailDialog.cancel();
					}
				});
	

	}

	public boolean isBanNewWork(){
		if (FireWallActivity.uidList.contains(uid)
				&& (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
						.checkPermission(Manifest.permission.INTERNET,pkgname))
				&& SQLStatic.packagename_ALL.contains(pkgname)
				&& !Block.filter.contains(pkgname)) {
			savedPkgname_wifi = prefs.getString(Block.PREF_WIFI_PKGNAME, "");
			savedPkgname_3g = prefs.getString(Block.PREF_3G_PKGNAME, "");
			if (savedPkgname_wifi.contains(pkgname) && savedPkgname_3g.contains(pkgname)) {
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	
	
	public void banNetwork() {
		if (isBanNewWork()) {
			this.dismiss();
			String savedUids_wifi = "";
			String savedUids_3g = "";
			if (savedPkgname_wifi.contains(pkgname)) {
				savedUids_wifi = savedPkgname_wifi;
			} else {
				savedUids_wifi = savedPkgname_wifi + "|" + pkgname;
			}
			if (savedPkgname_3g.contains(pkgname)) {
				savedUids_3g = savedPkgname_3g;
			} else {
				savedUids_3g = savedPkgname_3g + "|" + pkgname;
			}
			final Editor edit = prefs.edit();
			edit.putString(Block.PREF_WIFI_PKGNAME, savedUids_wifi);
			edit.putString(Block.PREF_3G_PKGNAME, savedUids_3g);
			edit.putBoolean(Block.PREF_S, true);
			edit.commit();
			if (Block.applyIptablesRules(context, true, true)) {
				Toast.makeText(context, R.string.fire_applyed,
						Toast.LENGTH_SHORT).show();
			} else {
				final Editor edit2 = prefs.edit();
				edit2.putString(Block.PREF_WIFI_PKGNAME, savedPkgname_wifi);
				edit2.putString(Block.PREF_3G_PKGNAME, savedPkgname_3g);
				edit2.putBoolean(Block.PREF_S, true);
				edit2.commit();
			}

		} else {
		}
	}


}
