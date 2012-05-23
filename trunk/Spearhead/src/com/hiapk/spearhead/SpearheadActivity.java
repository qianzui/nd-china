package com.hiapk.spearhead;

import java.util.Timer;
import java.util.TimerTask;
import com.hiapk.alertaction.AlertActionNotify;
import com.hiapk.prefrencesetting.PrefrenceSetting;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SpearheadActivity extends TabActivity {
	WebView webView;
	private static RadioGroup group;
	public static TabHost tabHost;
	public static final String TAB_MONITOR = "tabMonitor";
	public static final String TAB_FIREWALL = "tabFireWall";
	public static final String TAB_WARNING = "tabWarning";
	Context context = this;
	// �������˳�
	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	private Timer tExit = new Timer();
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
		setContentView(R.layout.maintabs);	
		
		initScene();
		switchScene();
	}

	/**
	 * ��ʼ��
	 */
	private void initScene() {
		// TODO Auto-generated method stub
		group = (RadioGroup) findViewById(R.id.main_radio);
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_MONITOR)
				.setIndicator(TAB_MONITOR)
				.setContent(new Intent(this, Main.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_FIREWALL)
				.setIndicator(TAB_FIREWALL)
				.setContent(new Intent(this, FireWallActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_WARNING)
				.setIndicator(TAB_WARNING)
				.setContent(new Intent(this, Main3.class)));
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button0:
					tabHost.setCurrentTabByTag(TAB_MONITOR);
					break;
				case R.id.radio_button1:
					tabHost.setCurrentTabByTag(TAB_FIREWALL);
					break;
				case R.id.radio_button2:
					tabHost.setCurrentTabByTag(TAB_WARNING);
					break;

				default:
					break;
				}
			}
		});
	}

	/**
	 * ��ʼ��ʾ�ڼ���ҳ��
	 */
	private void switchScene() {
		// ѡ�����
		Bundle choose = this.getIntent().getExtras();
		int tab = choose.getInt("TAB");
		Log.d("spearhead", tab + "");
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
	 * ��һ��ҳ���л���������ҳ��İ�ť
	 */
	public void tabThree() {
		group.clearCheck();
		group.check(R.id.radio_button2);
		tabHost.setCurrentTabByTag(TAB_WARNING);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, "ϵͳ����");
		menu.add(0, 2, 2, "FAQ");
		menu.add(0, 3, 3, "����");
		menu.add(0, 4, 4, "�˳�");
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
			showFaqPopUp("file:///android_asset/faq/faq.html");
			break;
		case 3:
			showAboutPopUp("file:///android_asset/about/about.html");
			break;
		case 4:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void showFaqPopUp(String url){		
		try{	
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
			View vi = inflater.inflate(R.layout.faq, null);		
			dialog.setView(vi);		
			dialog.setTitle("�ȷ��������  FAQ :");		
			dialog.setCancelable(true);	
			dialog.setNegativeButton("ȷ��", null);
			WebView wb = (WebView) vi.findViewById(R.id.webview);		
			wb.loadUrl(url);					
			dialog.show();		
		}catch(Exception e){		
			System.out.println("Exception while showing PopUp : " + e.getMessage());		
		}		
	}
	public void showAboutPopUp(String url){		
		try{	
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
			View vi = inflater.inflate(R.layout.faq, null);		
			dialog.setView(vi);		
			dialog.setTitle("�����ȷ��������");		
			dialog.setCancelable(true);	
			dialog.setNegativeButton("ȷ��", null);
			WebView wb = (WebView) vi.findViewById(R.id.webview);		
			wb.loadUrl(url);					
			dialog.show();		
		}catch(Exception e){		
			System.out.println("Exception while showing PopUp : " + e.getMessage());		
		}		
	}
	



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ���֪ͨ����Ϣ
		AlertActionNotify notifyctrl = new AlertActionNotify();
		notifyctrl.cancelAlertNotify(context);
		isExit = false;
		hasTask = false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(context, "�ٰ�һ�μ��˳����", Toast.LENGTH_SHORT).show();
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
			tExit.cancel();
			task.cancel();
		}
		return super.onKeyDown(keyCode, event);
	}
}