package com.hiapk.spearhead;

import java.util.Timer;
import java.util.TimerTask;

import com.hiapk.alertaction.AlertActionNotify;
import com.hiapk.alertdialog.CustomDialogFAQBeen;
import com.hiapk.dataexe.NotificationInfo;
import com.hiapk.firewall.Block;
import com.hiapk.prefrencesetting.PrefrenceSetting;
import com.hiapk.rebootandstartaction.OnExit;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SpearheadActivity extends TabActivity {
	WebView webView;
	private static RadioGroup group;
	public static TabHost tabHost;
	public static final String TAB_MONITOR = "tabMonitor";
	public static final String TAB_FIREWALL = "tabFireWall";
	public static final String TAB_WARNING = "tabWarning";
	Context context = this;
	// 按两次退出
	public static Boolean isExit = false;
	private static Boolean hasTask = false;
	public ProgressDialog pro;
	public static ImageView firehelp;
	private Timer tExit = new Timer();
	public static boolean isHide = false;
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// MobclickAgent.onError(this);
		setContentView(R.layout.maintabs);
		// 为了退出。
		Mapplication.getInstance().addActivity(this);
		firehelp = (ImageView) findViewById(R.id.help_image);
		initScene();
		switchScene();
	}

	/**
	 * 初始化
	 */
	private void initScene() {
		// TODO Auto-generated method stub
		group = (RadioGroup) findViewById(R.id.main_radio);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_MONITOR)
				.setIndicator(TAB_MONITOR)
				.setContent(new Intent(context, Main.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_FIREWALL)
				.setIndicator(TAB_FIREWALL)
				.setContent(new Intent(context, FireWallMainScene.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_WARNING)
				.setIndicator(TAB_WARNING)
				.setContent(new Intent(this, Main3.class)));
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					hideHelp();
					isHide = false;
					tabHost.setCurrentTabByTag(TAB_MONITOR);
					break;
				case R.id.radio_button1:
					showHelp();
					NotificationInfo.callbyonFirstBacktoFire=true;
					tabHost.setCurrentTabByTag(TAB_FIREWALL);
					FireWallMainScene.switScene(0);
					break;
				case R.id.radio_button2:
					hideHelp();
					isHide = false;
					tabHost.setCurrentTabByTag(TAB_WARNING);
					break;
				default:
					break;
				}
			}
		});
	}

	public void showHelp(Context mContext) {
		Drawable d = mContext.getResources().getDrawable(R.drawable.fire_help);
		firehelp.setBackgroundDrawable(d);
		firehelp.setVisibility(View.VISIBLE);
		firehelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				firehelp.setVisibility(View.INVISIBLE);
				Block.isShowHelpSet(context, false);
			}
		});
	}

	public void hideHelp() {
		firehelp.setVisibility(View.INVISIBLE);
	}

	public void showHelp() {
		if (Block.isShowHelp(context)) {
			firehelp.setVisibility(View.VISIBLE);
		}
		if (Block.isShowHelp(context) && isHide) {
			showHelp(context);
		}
	}

	/**
	 * 初始显示第几个页面
	 */
	private void switchScene() {
		// 选择界面
		Bundle choose = this.getIntent().getExtras();
		int tab = choose.getInt("TAB");
		// Log.d("spearhead", tab + "");
		switch (tab) {
		case 3:
			group.clearCheck();
			group.check(R.id.radio_button2);
			tabHost.setCurrentTabByTag(TAB_WARNING);
			break;

		default:
			group.clearCheck();
			group.check(R.id.radio_button0);
			tabHost.setCurrentTabByTag(TAB_MONITOR);
			break;
		}
	}

	/**
	 * 第一个页面切换到第三个页面的按钮
	 */
	public void tabThree() {
		group.clearCheck();
		group.check(R.id.radio_button2);
		tabHost.setCurrentTabByTag(TAB_WARNING);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "设置");
		menu.add(0, 2, 2, "FAQ");
		menu.add(0, 3, 3, "关于");
		menu.add(0, 4, 4, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			Intent intentPref = new Intent();
			intentPref.setClass(context, PrefrenceSetting.class);
			startActivity(intentPref);
			break;
		case 2:
			// showFaqPopUp("file:///android_asset/faq/faq.html");
			CustomDialogFAQBeen customFAQ = new CustomDialogFAQBeen(context);
			customFAQ.dialogFAQ();
			break;
		case 3:
			// showAboutPopUp("file:///android_asset/about/about.html");
			CustomDialogFAQBeen customAbout = new CustomDialogFAQBeen(context);
			customAbout.dialogAbout();
			break;
		case 4:
			OnExit exit = new OnExit();
			exit.onExit(context);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showFaqPopUp(String url) {
		try {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View vi = inflater.inflate(R.layout.faq, null);
			dialog.setView(vi);
			dialog.setTitle("先锋流量监控  FAQ :");
			dialog.setCancelable(true);
			dialog.setNegativeButton("确定", null);
			WebView wb = (WebView) vi.findViewById(R.id.webview);
			wb.loadUrl(url);
			dialog.show();
		} catch (Exception e) {
			System.out.println("Exception while showing PopUp : "
					+ e.getMessage());
		}
	}

	public void showAboutPopUp(String url) {
		try {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View vi = inflater.inflate(R.layout.faq, null);
			dialog.setView(vi);
			dialog.setTitle("关于先锋流量监控");
			dialog.setCancelable(true);
			dialog.setNegativeButton("确定", null);
			WebView wb = (WebView) vi.findViewById(R.id.webview);
			wb.loadUrl(url);
			dialog.show();
		} catch (Exception e) {
			System.out.println("Exception while showing PopUp : "
					+ e.getMessage());
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 清楚通知栏信息
		AlertActionNotify notifyctrl = new AlertActionNotify();
		notifyctrl.cancelAlertNotify(context);
		isExit = false;
		hasTask = false;
		// MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		// MobclickAgent.onPause(this);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(context, "再按一次即退出监控", Toast.LENGTH_SHORT).show();
				tExit.cancel();
				task.cancel();
				tExit = new Timer();
				task = new TimerTask() {
					@Override
					public void run() {
						isExit = false;
					}
				};
				tExit.schedule(task, 8000);

				return false;
			}
			OnExit exit = new OnExit();
			exit.onExit(context);
			tExit.cancel();
			task.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}