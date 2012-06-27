package com.hiapk.firewall;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.UnitHandler;
import com.hiapk.spearhead.FireWallActivity;
import com.hiapk.spearhead.R;
import com.hiapk.sqlhelper.SQLStatic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.net.TrafficStats;
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
import android.widget.TextView;
import android.widget.Toast;

public class AppListAdapter extends BaseAdapter {

	private ArrayList<AppInfo> myAppList;
	private LayoutInflater inflater;
	private Context mContext;
	HashMap map;
	HashMap imageAndNameMap;

	public AppListAdapter(Context context, ArrayList<AppInfo> myAppList) {
		inflater = LayoutInflater.from(context);
		this.myAppList = myAppList;
		this.mContext = context;
		this.map = Block.getMap(context, myAppList);
		this.imageAndNameMap = imageAndNameMap;
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
			holder.e_toggle = (CheckBox) convertView
					.findViewById(R.id.e_toggle);
			holder.wifi_toggle = (CheckBox) convertView
					.findViewById(R.id.wifi_toggle);
			convertView.setTag(R.id.tag_holder, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.tag_holder);
		}
		AppInfo pkgInfo = myAppList.get(position);
		IsChecked ic = (IsChecked) map.get(pkgInfo.uid);
		// Info info = (Info)imageAndNameMap.get(position);
		holder.icon.setImageDrawable(pkgInfo.d);
		holder.appname.setText(pkgInfo.appname);
		if (pkgInfo.up == -1000 && pkgInfo.down == -1000) {
			holder.trafficup.setText("ªÒ»°÷–...");
		} else {
			holder.trafficup.setText(UnitHandler.unitHandlerAccurate(pkgInfo.up + pkgInfo.down));
		}

		holder.e_toggle.setChecked(ic.selected_3g);
		holder.wifi_toggle.setChecked(ic.selected_wifi);
		holder.e_toggle.setOnClickListener(new EListener(holder.e_toggle, ic));
		holder.wifi_toggle.setOnClickListener(new WifiListener(
				holder.wifi_toggle, ic));
		convertView.setTag(R.id.tag_pkginfo, pkgInfo);
		return convertView;
	}

	public long judge(long tff) {
		if (tff == -1)
			tff = 0;
		return tff;
	}

	class ViewHolder {
		ImageView icon;
		TextView appname;
		TextView trafficup;
		CheckBox e_toggle;
		CheckBox wifi_toggle;
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
								if (Block.applyIptablesRules(mContext, true)) {
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
						if (Block.applyIptablesRules(mContext, true)) {
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
								if (Block.applyIptablesRules(mContext, true)) {
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
						if (Block.applyIptablesRules(mContext, true)) {
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
