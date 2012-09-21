package com.hiapk.firewall;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.hiapk.bean.DatauidHash;
import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.logs.Logs;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomDialog;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceData;
import com.hiapk.util.UnitHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AppListAdapter extends BaseAdapter {

	private ArrayList<PackageInfo> myAppList;
	private LayoutInflater inflater;
	private Context mContext;
	public HashMap map;
	public static SyncImageLoader syncImageLoader = new SyncImageLoader();
	public SharedPrefrenceData sharedpref;
	ArrayList<Integer> uidList;
	HashMap<Integer, PackageInfo> appList;
	HashMap<Integer, String> appname;
	HashMap<Integer, DatauidHash> uiddata;
	int uid;

	public AppListAdapter(Context context, ArrayList<PackageInfo> myAppList,
			HashMap<Integer, String> appname,
			HashMap<Integer, DatauidHash> uiddata,
			HashMap<Integer, PackageInfo> appList, ArrayList<Integer> uidList) {
		inflater = LayoutInflater.from(context);
		this.mContext = context;
		sharedpref = new SharedPrefrenceData(mContext);
		this.map = Block.getMap(context, myAppList);
		this.appname = appname;
		this.uiddata = uiddata;
		this.appList = appList;
		this.uidList = uidList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return uidList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return uidList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.app_list_item, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.icon);
			holder.appname = (TextView) convertView.findViewById(R.id.app_name);
			holder.trafficup = (TextView) convertView
					.findViewById(R.id.trafficup);
			holder.traffic_title = (TextView) convertView
					.findViewById(R.id.traffic_title);
			holder.e_toggle = (CheckBox) convertView
					.findViewById(R.id.e_toggle);
			holder.wifi_toggle = (CheckBox) convertView
					.findViewById(R.id.wifi_toggle);
			holder.ll = (LinearLayout) convertView
					.findViewById(R.id.detail_menu);
			convertView.setTag(R.id.tag_holder, holder);
			// 设置皮肤
			LinearLayout layout = (LinearLayout) convertView
					.findViewById(R.id.list_backgroundlayout);
			layout.setBackgroundResource(SkinCustomDialog.spinnerBackground());
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.tag_holder);
		}

		int uid = uidList.get(position);
		PackageInfo pkgInfo = appList.get(uid);

		if (appname.containsKey(uid)) {
			holder.appname.setText(appname.get(uid));
		} else {
			holder.appname.setText("获取中...");
		}
		IsChecked ic = (IsChecked) map.get(uid);
		holder.icon.setImageDrawable(mContext.getResources().getDrawable(
				R.drawable.ic_launcher));

		holder.icon.setTag(position);
		switch (sharedpref.getFireWallType()) {
		case 0:
			holder.traffic_title.setText("今日流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid).getTotalTraff()));
			} else {
				holder.trafficup.setText("1:" + uid);
			}
			break;
		case 1:
			holder.traffic_title.setText("本周流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid).getTotalTraff()));
			} else {
				holder.trafficup.setText("0");
			}
			break;
		case 2:
			holder.traffic_title.setText("本月流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid).getTotalTraff()));
			} else {
				holder.trafficup.setText("0");
			}
			break;
		case 3:
			holder.traffic_title.setText("移动流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid)
								.getDownloadmobile()
								+ uiddata.get(uid).getUploadmobile()));
			} else {
				holder.trafficup.setText("0");
			}
			break;
		case 4:
			holder.traffic_title.setText("WIFI流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid).getDownloadwifi()
								+ uiddata.get(uid).getUploadwifi()));
			} else {
				holder.trafficup.setText("0");
			}

			break;
		case 5:
			holder.traffic_title.setText("通知栏流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid).getTotalTraff()));
			} else {
				holder.trafficup.setText("0");
			}
			break;
		default:
			holder.traffic_title.setText("今日流量：");
			if (uiddata.containsKey(uid)) {
				holder.trafficup.setText(UnitHandler
						.unitHandlerAccurate(uiddata.get(uid).getTotalTraff()));
			} else {
				holder.trafficup.setText("0");
			}
			break;
		}

		syncImageLoader.loadImage(position, pkgInfo, mContext,
				imageLoadListener, holder.icon, uid);
		holder.e_toggle.setChecked(ic.selected_3g);
		holder.wifi_toggle.setChecked(ic.selected_wifi);
		holder.e_toggle.setOnClickListener(new EListener(holder.e_toggle, ic));
		holder.wifi_toggle.setOnClickListener(new WifiListener(
				holder.wifi_toggle, ic));
		holder.ll.setVisibility(View.GONE);
		convertView.setTag(R.id.tag_pkginfo, pkgInfo);
		return convertView;
	}

	SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener() {
		@Override
		public void onImageLoad(Integer t, Drawable drawable, ImageView view,
				int uid) {
			ImageView icon = (ImageView) FireWallActivity.appListView
					.findViewWithTag(t);
			if (icon != null) {
				icon.setImageDrawable(drawable);
			}
		}

		@Override
		public void onError(Integer t) {

		}
	};

	public long judge(long tff) {
		if (tff == -1)
			tff = 0;
		return tff;
	}

	class ViewHolder {
		ImageView icon;
		TextView appname;
		TextView traffic_title;
		TextView trafficup;
		CheckBox e_toggle;
		CheckBox wifi_toggle;
		LinearLayout ll;
	}

	class EListener implements OnClickListener {
		CheckBox cb;
		IsChecked ic;

		// final String cmd = "chmod 777 "+mContext.getPackageCodePath();
		public EListener(CheckBox cb, IsChecked ic) {
			this.cb = cb;
			this.ic = ic;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (Root.isDeviceRooted()) {
				if (Block.isShowTip(mContext)) {
					LayoutInflater factory = LayoutInflater.from(mContext);
					final View mDialogView = factory.inflate(
							R.layout.fire_root_tip, null);
					final AlertDialog mDialog = new AlertDialog.Builder(
							mContext).create();
					mDialog.show();
					Window window = mDialog.getWindow();
					window.setContentView(mDialogView, new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					final int heigh = window.getWindowManager()
							.getDefaultDisplay().getHeight();
					final int width = window.getWindowManager()
							.getDefaultDisplay().getWidth();
					window.setLayout((int) (width * 0.8),
							LayoutParams.WRAP_CONTENT);

					final TextView fire_tip = (TextView) mDialogView
							.findViewById(R.id.fire_tip);
					final Button fire_ok = (Button) mDialogView
							.findViewById(R.id.fire_ok);
					final Button fire_cancel = (Button) mDialogView
							.findViewById(R.id.fire_cancel);

					fire_tip.setText(mContext.getResources().getString(
							R.string.tip_content));

					fire_ok.setOnClickListener(new Button.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							mDialog.cancel();
							Block.isShowTipSet(mContext, false);
							if (GetRoot.assertBinaries(mContext, true)) {
								ic.selected_3g = cb.isChecked();
								Block.saveRules(mContext, map);
								if (Block.applyIptablesRules(mContext, true,
										true)) {
									Toast.makeText(mContext,
											R.string.fire_applyed,
											Toast.LENGTH_SHORT).show();
								} else {
									cb.setChecked(!cb.isChecked());
									ic.selected_3g = cb.isChecked();
									Block.saveRules(mContext, map);
								}
							} else {
								Block.isShowTipSet(mContext, true);
								cb.setChecked(ic.selected_3g);
							}

						}
					});

					fire_cancel
							.setOnClickListener(new Button.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									cb.setChecked(ic.selected_3g);
									mDialog.cancel();
								}
							});

				} else {
					if (GetRoot.assertBinaries(mContext, true)) {
						ic.selected_3g = cb.isChecked();
						Block.saveRules(mContext, map);
						if (Block.applyIptablesRules(mContext, true, true)) {
							Toast.makeText(mContext, R.string.fire_applyed,
									Toast.LENGTH_SHORT).show();
						} else {
							cb.setChecked(!cb.isChecked());
							ic.selected_3g = cb.isChecked();
							Block.saveRules(mContext, map);
						}
					} else {
						cb.setChecked(ic.selected_3g);
					}
				}
			} else {
				cb.setChecked(false);
				LayoutInflater factory = LayoutInflater.from(mContext);
				final View mDialogView = factory.inflate(R.layout.fire_tip,
						null);
				final AlertDialog mDialog = new AlertDialog.Builder(mContext)
						.create();
				mDialog.show();
				Window window = mDialog.getWindow();
				window.setContentView(mDialogView, new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				final int heigh = window.getWindowManager().getDefaultDisplay()
						.getHeight();
				final int width = window.getWindowManager().getDefaultDisplay()
						.getWidth();
				window.setLayout((int) (width * 0.8), LayoutParams.WRAP_CONTENT);

				final TextView fire_tip = (TextView) mDialogView
						.findViewById(R.id.fire_tip2);
				final Button fire_yes = (Button) mDialogView
						.findViewById(R.id.fire_yes);

				fire_tip.setText(mContext.getResources()
						.getString(R.string.tip));
				fire_yes.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog.cancel();
					}
				});
			}

		}

	}

	class WifiListener implements OnClickListener {
		CheckBox cb;
		IsChecked ic;

		public WifiListener(CheckBox cb, IsChecked ic) {
			this.cb = cb;
			this.ic = ic;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (Root.isDeviceRooted()) {
				if (Block.isShowTip(mContext)) {
					LayoutInflater factory = LayoutInflater.from(mContext);
					final View mDialogView = factory.inflate(
							R.layout.fire_root_tip, null);
					final AlertDialog mDialog = new AlertDialog.Builder(
							mContext).create();
					mDialog.show();
					Window window = mDialog.getWindow();
					window.setContentView(mDialogView, new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
					final int heigh = window.getWindowManager()
							.getDefaultDisplay().getHeight();
					final int width = window.getWindowManager()
							.getDefaultDisplay().getWidth();
					window.setLayout((int) (width * 0.8),
							LayoutParams.WRAP_CONTENT);

					final TextView fire_tip = (TextView) mDialogView
							.findViewById(R.id.fire_tip);
					final Button fire_ok = (Button) mDialogView
							.findViewById(R.id.fire_ok);
					final Button fire_cancel = (Button) mDialogView
							.findViewById(R.id.fire_cancel);

					fire_tip.setText(mContext.getResources().getString(
							R.string.tip_content));

					fire_ok.setOnClickListener(new Button.OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							mDialog.cancel();
							if (GetRoot.hasRootAccess(mContext, true)) {
								Block.isShowTipSet(mContext, false);
								ic.selected_wifi = cb.isChecked();
								Block.saveRules(mContext, map);
								if (Block.applyIptablesRules(mContext, true,
										true)) {
									Toast.makeText(mContext,
											R.string.fire_applyed,
											Toast.LENGTH_SHORT).show();
								} else {
									cb.setChecked(!cb.isChecked());
									ic.selected_wifi = cb.isChecked();
									Block.saveRules(mContext, map);
								}
							} else {
								Block.isShowTipSet(mContext, true);
								cb.setChecked(ic.selected_wifi);
							}

						}
					});

					fire_cancel
							.setOnClickListener(new Button.OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									cb.setChecked(ic.selected_wifi);
									mDialog.cancel();
								}
							});
				} else {
					if (GetRoot.assertBinaries(mContext, true)) {
						ic.selected_wifi = cb.isChecked();
						Block.saveRules(mContext, map);
						if (Block.applyIptablesRules(mContext, true, true)) {
							Toast.makeText(mContext, R.string.fire_applyed,
									Toast.LENGTH_SHORT).show();
						} else {
							cb.setChecked(!cb.isChecked());
							ic.selected_wifi = cb.isChecked();
							Block.saveRules(mContext, map);
						}
					} else {
						cb.setChecked(ic.selected_wifi);
					}
				}
			} else {
				cb.setChecked(false);
				LayoutInflater factory = LayoutInflater.from(mContext);
				final View mDialogView = factory.inflate(R.layout.fire_tip,
						null);
				final AlertDialog mDialog = new AlertDialog.Builder(mContext)
						.create();
				mDialog.show();
				Window window = mDialog.getWindow();
				window.setContentView(mDialogView, new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				final int heigh = window.getWindowManager().getDefaultDisplay()
						.getHeight();
				final int width = window.getWindowManager().getDefaultDisplay()
						.getWidth();
				window.setLayout((int) (width * 0.8), LayoutParams.WRAP_CONTENT);

				final TextView fire_tip = (TextView) mDialogView
						.findViewById(R.id.fire_tip2);
				final Button fire_yes = (Button) mDialogView
						.findViewById(R.id.fire_yes);

				fire_tip.setText(mContext.getResources()
						.getString(R.string.tip));
				fire_yes.setOnClickListener(new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mDialog.cancel();
					}
				});
			}

		}

	}

}
