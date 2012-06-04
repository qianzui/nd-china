package com.hiapk.spearhead;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.hiapk.broadcreceiver.AlarmSet;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.firewall.GetRoot;
import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.progressbar.MyProgressBar;
import com.hiapk.progressbar.PieView;
import com.hiapk.progressbar.ProgressBarForV;
import com.hiapk.progressbar.StackedBarChart;
import com.hiapk.sqlhelper.SQLHelperTotal;
import com.hiapk.sqlhelper.SQLHelperUid;
import com.hiapk.sqlhelper.SQLHelperUidTotal;
import com.hiapk.widget.ProgramNotify;
import com.hiapk.widget.SetText;
import com.umeng.analytics.MobclickAgent;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {

	private Context context = this;
	// private SQLHelperUid sqlhelperUid = new SQLHelperUid();
	private SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// wifi��mobile����ʹ����
	public static long mobile_month_use = 0;

	// ��ʱ�����������------------
	private int[] uids;
	private String[] packagenames;
	// ------------
	// ��ʾ����ͼ��
	boolean ismobileshowpie = false;
	// ��ȡ��ϵͳʱ��
	private int year;
	private int month;
	private int monthDay;
	// wifi�¶�����
	long[] wifiTraffic = new long[64];
	/**
	 * �����·ݵ��ƶ���������
	 */
	long[] mobileTraffic = new long[64];
	/**
	 * �����·ݵ��ƶ���������
	 */
	long[] mobileTrafficPart = new long[64];
	// ��Ļ���
	int windowswidesize;
	// ϵͳ����
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	SharedPrefrenceData sharedData;
	TrafficManager trafficManager = new TrafficManager();
	// fortest
	long time;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// umeng
		MobclickAgent.onError(this);
		setContentView(R.layout.main);
		// ��ȡ�̶��������
		sharedData = new SharedPrefrenceData(context);
		// ��ʾ��ʾ�Ի������ʾһ��
		if (!sharedData.isAlertDialogOnfirstOpenDisplayed()) {
			dialogHintSetData().show();
		}
		// temp------------
		getuids();
		// ------------
		AlarmSet alset = new AlarmSet();
		if (sharedData.isNotifyOpen()) {
			alset.StartWidgetAlarm(context);
		}
		initSQLdatabase(uids, packagenames);
		setonrefreshclicklistens();
		// GetRoot.cmdRoot("chmod 777 "+getPackageCodePath());
	}

	private AlertDialog dialogHintSetData() {
		// TODO Auto-generated method stub
		AlertDialog dialogHint = new AlertDialog.Builder(Main.this)
				.setTitle("�����������ײͣ���У����������")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						sharedData.setAlertDialogOnfirstOpenDisplayed(true);
					}
				}).create();
		return dialogHint;
	}

	/**
	 * ��ʼ����ʾ��ֵ
	 */
	private void initValues() {
		// TODO Auto-generated method stub
		// ��ʼ��С����
		TextView todayMobil = (TextView) findViewById(R.id.todayRate);
		TextView todayMobilunit = (TextView) findViewById(R.id.unit1);
		TextView weekMobil = (TextView) findViewById(R.id.weekRate);
		TextView weekMobilunit = (TextView) findViewById(R.id.unit2);
		TextView monthMobil = (TextView) findViewById(R.id.monthRate);
		TextView monthMobilunit = (TextView) findViewById(R.id.unit3);
		TextView monthMobil2 = (TextView) findViewById(R.id.traffic_month_set);
		TextView monthMobilunit2 = (TextView) findViewById(R.id.unit_month_set);
		// TextView todayWifi = (TextView) findViewById(R.id.wifiTodayRate);
		// TextView todayWifiunit = (TextView) findViewById(R.id.unit4);
		// TextView weekWifi = (TextView) findViewById(R.id.wifiWeekRate);
		// TextView weekWifiunit = (TextView) findViewById(R.id.unit5);
		// TextView monthWifi = (TextView) findViewById(R.id.wifiMonthRate);
		// TextView monthWifiunit = (TextView) findViewById(R.id.unit6);
		// ��ת��У��ҳ
		Button gotoThree = (Button) findViewById(R.id.gotoThree);
		gotoThree.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				gotoThree();

			}
		});
		// ������ȡ����
		wifiTraffic = new long[64];
		// ��ʼ��������ȡ����
		// ȡ��ÿ�ܵ�����
		long[] weektraffic = new long[6];
		weektraffic = TrafficManager.mobile_week_data;
		// ȡ���¶�����
		mobileTraffic = TrafficManager.mobile_month_data;
		wifiTraffic = TrafficManager.wifi_month_data;
		// ������������
		todayMobil.setText(unitHandler(mobileTraffic[monthDay]
				+ mobileTraffic[monthDay + 31], todayMobilunit));
		// todayMobil.setText(unitHandler(8888080, todayMobilunit));
		weekMobil.setText(unitHandler(weektraffic[0], weekMobilunit));
		// �¶���������
		mobile_month_use = TrafficManager.getMonthUseData(context);
		long mobileSet = sharedData.getMonthMobileSetOfLong();
		if (mobile_month_use > mobileSet)
			monthMobil.setTextColor(Color.RED);
		else
			monthMobil.setTextColor(Color.GREEN);

		monthMobil.setText(unitHandler(mobile_month_use, monthMobilunit));
		monthMobil2.setText("/" + unitHandler(mobileSet, monthMobilunit2));
		// todayWifi.setText(unitHandler(wifi[monthDay] + wifi[monthDay + 31],
		// todayWifiunit));
		// weekWifi.setText(unitHandler(weektraffic[5], weekWifiunit));
		// wifi_month_use = wifi[0] + wifi[63];
		// monthWifi.setText(unitHandler(wifi_month_use, monthWifiunit));

	}

	/**
	 * ��ʼ�����ݿ�
	 * 
	 * @param uids
	 *            uid����
	 * @param packagename
	 *            uid��Ӧ�İ�����
	 */
	private void initSQLdatabase(int[] uids, String[] packagename) {
		// TODO Auto-generated method stub
		if (!sqlhelperTotal.getIsInit(context)) {
			sqlhelperTotal.initSQL(context, uids, packagename);
		}
	}

	// ----------

	public void gotoThree() {
		SpearheadActivity sp = new SpearheadActivity();

		sp.tabThree();
	}

	// ----------
	/**
	 * ��ʱ����
	 */
	private void getuids() {
		int j = 0;
		PackageManager pkgmanager = context.getPackageManager();
		List<PackageInfo> packages = context.getPackageManager()
				.getInstalledPackages(0);
		int[] uidstp = new int[packages.size()];
		String[] packagenamestp = new String[packages.size()];
		for (int i = 0; i < packages.size(); i++) {
			PackageInfo packageinfo = packages.get(i);
			String fliter = "com.htc.android.fusion.calculator.com.htc.android.htcsetupwizard.com.htc.android.mail.com.htc.android.psclient.com.htc.android.worldclock.com.htc.appsharing.com.htc.autorotatewidget.com.htc.calendar.com.clock3dwidget.com.htc.dcs.service.stock.com.htc.dlnamiddlelayer.com.htc.dockmode.com.htc.fm.com.htc.fusion.htcbookmarkwidget.com.htc.googlereader.com.htc.googlereaderwidget.com.htc.home.personalize.com.htc.htcMessageUploader.com.htc.htccalendarwidgets.com.htc.htccontactwidgets_3d_fusion.com.htc.htchubsyncprovider.com.htc.htcmailwidgets.com.htc.htcmsgwidgets3dcom.htc.htcsettingwidgets.com.htc.idlescreen.base.com.htc.idlescreen.shortcut.com.htc.idlescreen.socialnetwork.com.htc.launcher.com.htc.livewallpaper.streak.com.htc.ml.PhotoLocaScreen.com.htc.music.com.htc.musicnhancer.com.htc.opensense.com.htc.provider.CustomizationSettings.com.htc.provider.settings.com.htc.provider.weather.com.htc.providersuploads.com.htc.ringtonetrimmer.com.htc.rosiewidgets.backgrounddata.com.htc.rosiewidgets.dataroaming.com.htc.rosiewidgets.datastripcom.htc.rosiewidgets.powerstrip.com.htc.rosiewidgets.screenbrightness.com.htc.rosiewigets.screentimeout.com.htc.sdm.com.htc.settings.accountsync.com.htc.soundrecorder.com.htc.streamplayer.com.htc.sync.provider.weather.com.htc.videa.com.htc.weather.agent.com.htc.weatheridlescreen.com.htc.widget.profile.com.htc.widget.ringtone.com.htc.widget3d.weather.com.htc.clock3dwidgetcom.htc.messagecscom.htc.ml.PhotoLockScreencom.htc.musicenhancercom.htc.providers.uploads.com.htc.rosiewidgets.screentimeout.com.htc.video.com.broadcom.bt.app.system.com.google.android.apps.uploader.com.google.android.partnersetup.com.htc.CustomizationSetup.com.htc.FMRadioWidget.com.htc.OnlineAssetDetails.com.htc.Sync3DWidget.com.htc.UpgradeSetup.com.htc.Weather.com.htc.WeatherWallpaper.com.htc.albumcom.htc.HtcBeatsNotify.com.htc.MediaAutoUploadSetting.com.htc.MediaCacheService.com.htc.MusicWidget3D.com.htc.WifiRouter.com.htc.android.WeatherLiveWallpaper.com.htc.android.htcime.com.htc.android.image_wallpaper.com.htc.android.tvout.com.htc.android.wallpaper.com.htc.china.callocation.com.htc.connectedMedia.com.htc.cspeoplesync.com.htc.dmc.com.htc.flashlight.com.htc.flashliteplugin.com.htc.fusion.FusionApk.com.htc.htccompressviewer.com.htc.lmwN.com.htc.lockscreen.com.htc.mysketcher.com.htc.pen.com.htc.photowidget3d.android.smartcard.com.android.deviceinfo.com.android.htccontacts.com.android.htcdialer.com.android.inputmethod.latin.com.android.providers.htcmessage.com.android.restartapp.com.android.setupwizard.com.android.updater.com.android.voicedialer.com.westtek.jcp"
					+ "android.media.dlnaservicecom.android.cameracom.android.htmlviewer.com.android.music.com.android.providersuserdictionary.com.android.quicksearchbox.com.android.stk.com.android.vending.updater.com.google.android.location.com.google.android.street.com.google.android.talk.com.meizu.MzAutoInstaller.com.meizu.account.com.meizu.backupandrestore.com.meizu.cloud.com.meizu.filemanager.com.meizu.flyme.service.find.com.meizu.input.com.meizu.mzsimcontacts.com.meizu.mzsyncservice.com.meizu.notepaper.com.meizu.recent.app.com.meizu.vncviewer.com.meizu.wapisetting.android.tts.com.android.Unzip.com.android.alarmclock.com.android.providers.userdictionary.com.android.wallpaper.livpicker.com.cooliris.media.com.cooliris.video.media.com.google.android.apps.genie.geniewidget.com.meizu.mstore.com.meizu.musiconline.com.android.wallpaper.livepicker.com.svox.picoN.com.hyfsoft"
					+ "com.android.certinstaller.com.android.fileexplorer.com.android.monitor.com.android.packageinstaller.com.android.sidekick.android,com.android.bluetoothcom.adobe.flashplayer,com.android.browsercom.android.calculator2com.android.calendarcom.android.contactscom.android.deskclockcom.android.defcontainercom.android.emailcom.android.gallerycom.android.launchercom.android.mmscom.android.phonecom.android.providers.applicationscom.android.providers.calendarcom.android.providers.contactscom.android.providers.downloadscom.android.providers.downloads.uicom.android.providers.drm  appnamecom.android.providers.mediacom.android.providers.settingscom.android.providers.subscribedfeedscom.android.providers.telephonycom.android.providers.telocationcom.android.server.vpncom.android.settingscom.android.soundrecordercom.android.systemuicom.google.android.apps.mapscom.google.android.gsfcom.google.android.inputmethod.pinyincom.google.android.syncadapters.calendarcom.google.android.syncadapters.contactscom.miui.antispamcom.miui.backupcom.miui.cameracom.miui.cloudservicecom.miui.notescom.miui.playercom.miui.uac";
			String pacname = packageinfo.packageName;
			int uid = packageinfo.applicationInfo.uid;
			if (!(PackageManager.PERMISSION_GRANTED != pkgmanager
					.checkPermission(Manifest.permission.INTERNET, pacname))) {
				if (!fliter.contains(pacname)) {
					uidstp[j] = uid;
					packagenamestp[j] = pacname;
					// showLog("������ʾ��uid=" + uid);
					j++;
					// tmpInfo.packageName = pacname;
					// tmpInfo.app_uid = packageinfo.applicationInfo.uid;
				}
			}
		}
		uids = new int[j];
		packagenames = new String[j];
		for (int i = 0; i < j; i++) {
			uids[i] = uidstp[i];
			packagenames[i] = packagenamestp[i];
		}
		// SQLHelperUid sqlhelpuid = new SQLHelperUid();
		// uids = sqlhelpuid.selectUidnumbers(context);
		// packagenames = sqlhelpuid.selectPackagenames(context);
	}

	// ------------
	/**
	 * ִ�ж�̬����������
	 * 
	 * @param i
	 *            �ƶ�����
	 * @param j
	 *            wifi
	 */
	private void RefreshProgressBar(int i, int j) {
		// ProgressBar myProgressBar = (ProgressBar)
		// findViewById(R.id.progressbar);
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ���ڵĿ��
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi / 5;
		// showlog(screenWidth+"");
		myProgressBar_mobile.setTextsize(windowswidesize);
		// myProgressBar_wifi.setTextsize(fontsize);
		ProgressBarForV progforv_mobile = new ProgressBarForV();
		progforv_mobile.j = i;
		progforv_mobile.execute(myProgressBar_mobile);
		// ProgressBarForV progforv_wifi = new ProgressBarForV();
		// progforv_wifi.j = j;
		// progforv_wifi.execute(myProgressBar_wifi);
	}

	/**
	 * ����������
	 * 
	 * @param i
	 *            �ƶ�����
	 * @param j
	 *            wifi
	 */
	private void ProgressBarSet(int i, int j) {
		MyProgressBar myProgressBar_mobile = (MyProgressBar) findViewById(R.id.progressbar_mobile);
		// MyProgressBar myProgressBar_wifi = (MyProgressBar)
		// findViewById(R.id.progressbar_wifi);
		myProgressBar_mobile.setProgress(i);
		// myProgressBar_wifi.setProgress(j);
	}

	/**
	 * ��λ��׼����mb��gb��
	 * 
	 * @param count
	 *            �����long����
	 * @param unit
	 *            ��ֵ����Ҫ��ʾ��textview
	 * @return ����String��ֵ
	 */
	private String unitHandler(long count, TextView unit) {
		String value = null;
		long temp = count;
		float floatnum = count;
		float floatGB = count;
		float floatTB = count;
		if ((temp = temp / 1024) < 1) {
			value = count + "";
			unit.setText("B");
		} else if ((floatnum = (float) temp / 1024) < 1) {
			value = temp + "";
			unit.setText("KB");
		} else if ((floatGB = floatnum / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatnum) + "";
			unit.setText("MB");
		} else if ((floatTB = floatGB / 1024) < 1) {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatGB) + "";
			unit.setText("GB");
		} else {
			DecimalFormat format = new DecimalFormat("0.##");
			value = format.format(floatTB) + "";
			unit.setText("TB");
		}
		return value;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// umeng
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// umeng
		MobclickAgent.onResume(this);
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		AlarmSet alset = new AlarmSet();
		alset.StartAlarm(context);
		initWifiBar();
		if (TrafficManager.mobile_month_data[0] == 0
				&& TrafficManager.wifi_month_data[0] == 0
				&& TrafficManager.mobile_month_data[63] == 0
				&& TrafficManager.wifi_month_data[63] == 0) {

			new AsyncTaskonRefreshMain().execute(context);
		} else {
			initValues();
			initProgressBar();
			initPieBar();
			initWifiBar();
		}

		// ���ݼ�¼���ܣ�����flesh����
		// AlarmSet alset = new AlarmSet();
		// // ��ʼ������״̬
		// sqlhelperTotal.initTablemobileAndwifi(context);
		// if (SQLHelperTotal.TableWiFiOrG23 != ""
		// && sqlhelperTotal.getIsInit(context)) {
		// // ��������
		// alset.StartAlarmMobile(context);
		// // �������ݼ�¼
		// trafficManager.statsTotalTraffic(context, false);
		// } else if (SQLHelperTotal.TableWiFiOrG23 != "") {
		// alset.StartAlarmMobile(context);
		// sqlhelperTotal.initTablemobileAndwifi(context);
		// }
		// time=System.currentTimeMillis();

		// time = System.currentTimeMillis() - time;
		// showlog("����main" + time);
	}

	private class AsyncTaskonRefreshMain extends AsyncTask<Context, Long, Long> {
		@Override
		protected Long doInBackground(Context... params) {
			int timetap = 0;
			while (TrafficManager.mobile_month_data[0] == 0
					&& TrafficManager.wifi_month_data[0] == 0
					&& TrafficManager.mobile_month_data[63] == 0
					&& TrafficManager.wifi_month_data[63] == 0) {
				try {
					Thread.sleep(200);
					timetap += 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (timetap > 8)
					break;

			}

			return null;
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			SetText.resetWidgetAndNotify(context);
			initValues();
			initProgressBar();
			initPieBar();
			initWifiBar();
		}
	}

	private void setonrefreshclicklistens() {
		Button btn_refresh = (Button) findViewById(R.id.refresh);
		btn_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��¼���ˢ�´���
				MobclickAgent.onEvent(context, "refresh");
				AlarmSet alset = new AlarmSet();
				// ��ʼ������״̬
				sqlhelperTotal.initTablemobileAndwifi(context, false);
				if (SQLHelperTotal.TableWiFiOrG23 != ""
						&& sqlhelperTotal.getIsInit(context)) {
					// ��������
					alset.StartAlarmMobile(context);
					// �������ݼ�¼
					// trafficManager.statsTotalTraffic(context, false);
					// sqlhelperTotal.RecordTotalwritestats(context, false);
				} else if (SQLHelperTotal.TableWiFiOrG23 != "") {
					alset.StartAlarmMobile(context);
					sqlhelperTotal.initTablemobileAndwifi(context, false);
				}
				initValues();
				initProgressBar();
				initPieBar();
				initWifiBar();
				// specialfortext----test
				// SQLHelperUidTotal sqlUidTotal = new SQLHelperUidTotal();
				// sqlUidTotal.updateSQLUidTypes(context, uids);
			}
		});
	}

	/**
	 * ��ʼ��wifi���ֵ���״ͼ
	 */
	private void initWifiBar() {
		// TODO Auto-generated method stub
		LinearLayout layout_mobile = (LinearLayout) findViewById(R.id.linearlayout_wifi);
		StackedBarChart chartbar = initStackedBarChart(context);
		View view = chartbar.execute(context);
		layout_mobile.removeAllViews();
		layout_mobile.addView(view);
	}

	/**
	 * ����wifi���ֵ���״ͼ
	 * 
	 * @param context
	 * @return ������ʾ����״ͼ
	 */
	private StackedBarChart initStackedBarChart(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		// ȡ�ô�������
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ���ڵĿ��
		// windowswidesize = dm.widthPixels / 10;
		windowswidesize = dm.densityDpi;
		StackedBarChart chartbar = new StackedBarChart(context, windowswidesize);
		chartbar.setXaxisText(year + "��");
		// ���в�������
		// ����x����ʾ��Χ
		int monthtotalDay = countDay(year, month);
		chartbar.setMonthDay(monthtotalDay);
		// ����y����ʾֵ����Χ
		double[] wifi = new double[monthDay];
		long maxwifiTraffic = 0;
		// DecimalFormat format = new DecimalFormat("0.#");
		// wifi[0] = (double) (wifiTraffic[0] + wifiTraffic[63]) / 1000000;
		for (int i = 0; i < wifi.length; i++) {
			long temp = TrafficManager.wifi_month_data[i + 1]
					+ TrafficManager.wifi_month_data[i + 32];
			// С����2λ
			wifi[i] = (double) ((long) temp * 100 / 1024 / 1024) / 100;
			// format.format(wifi[i]);
			if (temp > maxwifiTraffic) {
				maxwifiTraffic = temp;
			}
		}
		chartbar.setData1(wifi);

		if (maxwifiTraffic < 848576) {
			chartbar.setyMaxvalue(1);
			chartbar.setMaxTraffic(1);
		} else {
			chartbar.setMaxTraffic((double) (long) maxwifiTraffic / 1048576 * 1.2);
			chartbar.setyMaxvalue((double) (long) maxwifiTraffic / 1048576 * 1.2);
		}

		// ���ñ���ɫ�������ص�����
		// chartbar.setBackgroundColor(Color.BLACK);
		// ���ó�ʼ��ʾͼ��λ��
		if ((monthDay + 2) > monthtotalDay) {
			chartbar.setxMinvalue(monthtotalDay - 6.5);
			chartbar.setxMaxvalue(monthtotalDay + 0.5);
		} else if ((monthDay - 5) < 0) {
			chartbar.setxMinvalue(0.5);
			chartbar.setxMaxvalue(7.5);
		} else {
			chartbar.setxMinvalue(monthDay - 4.5);
			chartbar.setxMaxvalue(monthDay + 2.5);
		}
		// ������ʾ������
		String[] xaxles = new String[monthtotalDay];
		for (int i = 0; i < monthtotalDay; i++) {
			int j = i + 1;
			xaxles[i] = month + "��" + j + "��";
		}
		chartbar.setXaxles(xaxles);
		// showlog(monthDay+"");
		return chartbar;
	}

	private void initPieBar() {
		// TODO Auto-generated method stub
		// ��ȡĬ��������ֵ
		long mobileSet = sharedData.getMonthMobileSetOfLong();
		// long moblileTotle = mobileTraffic[0] + mobileTraffic[63];
		int usePercent = 0;
		if (mobile_month_use == 0) {
			usePercent = 0;
		} else if (mobileSet == 0) {
			usePercent = 360;
		} else {
			usePercent = (int) (((double) mobile_month_use / mobileSet) * 360);
		}
		if (usePercent > 360)
			usePercent = 360;

		int mobilePersent = 0;
		// �¶�����Ϊ0�ж�
		if (mobile_month_use == 0) {
			mobilePersent = 0;
		} else if (mobileSet == 0) {
			mobilePersent = 100;
		} else {
			// ���г����ж�
			if (mobile_month_use > mobileSet)
				mobilePersent = 100;
			else
				mobilePersent = (int) ((double) mobile_month_use * 100 / mobileSet);
		}
		int[] percent = new int[] { usePercent, 360 - usePercent };
		final PieView pieView_mobile = new PieView(context, percent,
				mobilePersent);
		// View PieView=findViewById(R.id.pie_bar_mobile);
		LinearLayout layout_mobile = (LinearLayout) findViewById(R.id.linearlayout_bar_mobile);
		final LinearLayout laout_mobile_pie = (LinearLayout) findViewById(R.id.linearlayout_piebar_mobile);
		laout_mobile_pie.removeAllViews();
		//
		// laout_mobile_pie.setBackgroundColor(Color.WHITE);
		//
		ismobileshowpie = false;
		layout_mobile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				laout_mobile_pie.removeAllViews();
				if (ismobileshowpie) {
					ismobileshowpie = false;
				} else {
					laout_mobile_pie.addView(pieView_mobile);
					ismobileshowpie = true;
				}
			}
		});
		// laout_mobile_pie.removeAllViews();
		// laout_mobile_pie.addView(pieView_mobile);
		// laout_mobile.removeAllViews();
	}

	/**
	 * ��ʼ������������ʵ��ֵ
	 */
	private void initProgressBar() {
		// ��ȡ���õ��¶�ʹ��ֵ��Ĭ��50m
		long mobileSet = sharedData.getMonthMobileSetOfLong();
		int mobile = 0;
		int wifi = 0;
		// showlog("mobile" + mobile_month_use + "wifi" + wifi_month_use);
		// showlog("mobile" + mobileSet + "wifi" + wifiSet);
		// �¶�����Ϊ0�ж�
		if (mobile_month_use == 0) {
			mobile = 0;
		} else if (mobileSet == 0) {
			mobile = 100;
		} else {

			// ���г����ж�
			if (mobile_month_use > mobileSet)
				mobile = 100;
			else
				// mobile = (int) ((float) ((int) ((float) mobile_month_use /
				// mobileSet * 360)) / 360 * 100);
				mobile = (int) ((float) mobile_month_use * 100 / mobileSet);
			// if (wifi_month_use > wifiSet)
			// wifi = 100;
			// else
			// wifi = (int) ((float)wifi_month_use * 100 / wifiSet);
		}

		// showlog("mobile" + mobile + "wifi" + wifi);
		RefreshProgressBar(mobile, wifi);
	}

	/**
	 * ���㵥���м���
	 * 
	 * @param year
	 *            �������
	 * @param month
	 *            �����·�
	 * @return ��������
	 */
	private int countDay(int year, int month) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))
				&& month == 2) {
			return 29;
		} else {
			switch (month) {
			case 1:
				return 31;
			case 2:
				return 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			}
		}
		return 31;
	}

	/**
	 * ��ʾ��־
	 * 
	 * @param string
	 */
	private void showlog(String string) {
		Log.d("main", string);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}