package com.hiapk.firewall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;

public class Block {

	private static final String VERSION = "1.5.1c";
	/** special application UID used to indicate "any application" */
	private static final int SPECIAL_UID_ANY = -10;
	/** special application UID used to indicate the Linux Kernel */
	private static final int SPECIAL_UID_KERNEL = -11;
	/** root script filename */
	private static final String SCRIPT_FILE = "droidwall.sh";
	public static final String PREF_3G_UIDS = "AllowedUids3G";
	public static final String PREF_WIFI_UIDS = "AllowedUidsWifi";
	public static final String PREF_ALL_UIDS = "AppListUids";
	public static final String PREF_S = "Cache";
	public static final String PREF_SHOW = "IsShowTip";
	public static final String PREF_TIP = "FireTip";
	// Preferences
	private static final String PREFS_NAME = "DroidWallPrefs";
	public static boolean isChanged = false;
	
	public static String filter = "android.media.dlnaservicecom.android.cameracom.android.htmlviewer.com.android.music.com.android.providersuserdictionary.com.android.quicksearchbox.com.android.stk.com.android.vending.updater.com.google.android.location.com.google.android.street.com.google.android.talk.com.meizu.MzAutoInstaller.com.meizu.account.com.meizu.backupandrestore.com.meizu.cloud.com.meizu.filemanager.com.meizu.flyme.service.find.com.meizu.input.com.meizu.mzsimcontacts.com.meizu.mzsyncservice.com.meizu.notepaper.com.meizu.recent.app"+".com.meizu.vncviewer.com.meizu.wapisetting.android.tts.com.android.Unzip.com.android.alarmclock.com.android.providers.userdictionary.com.android.wallpaper.livpicker.com.cooliris.media.com.cooliris.video.media.com.google.android.apps.genie.geniewidget.com.meizu.mstore.com.meizu.musiconline.com.android.wallpaper.livepicker.com.svox.picoN.com.hyfsoft"+
	"me.uubook.library.artwisdomcom.scoreloop.games.gearedcom.htc.android.fusion.calculator.com.htc.android.htcsetupwizard.com.htc.android.mail.com.htc.android.psclient.com.htc.android.worldclock.com.htc.appsharing.com.htc.autorotatewidget.com.htc.calendar.com.clock3dwidget.com.htc.dcs.service.stock.com.htc.dlnamiddlelayer.com.htc.dockmode.com.htc.fm.com.htc.fusion.htcbookmarkwidget.com.htc.googlereader.com.htc.googlereaderwidget.com.htc.home.personalize.com.htc.htcMessageUploader.com.htc.htccalendarwidgets.com.htc.htccontactwidgets_3d_fusion.com.htc.htchubsyncprovider.com.htc.htcmailwidgets.com.htc.htcmsgwidgets3dcom.htc.htcsettingwidgets.com.htc.idlescreen.base.com.htc.idlescreen.shortcut.com.htc.idlescreen.socialnetwork.com.htc.launcher.com.htc.livewallpaper.streak.com.htc.ml.PhotoLocaScreen.com.htc.music.com.htc.musicnhancer.com.htc.opensense.com.htc.provider.CustomizationSettings.com.htc.provider.settings.com.htc.provider.weather.com.htc.providersuploads.com.htc.ringtonetrimmer.com.htc.rosiewidgets.backgrounddata.com.htc.rosiewidgets.dataroaming.com.htc.rosiew			idgets.datastripcom.htc.rosiewidgets.powerstrip.com.htc.rosiewidgets.screenbrightness.com.htc.rosiewigets.screentimeout.com.htc.sdm.com.htc.settings.accountsync.com.htc.soundrecorder.com.htc.streamplayer.com.htc.sync.provider.weather.com.htc.videa.com.htc.weather.agent.com.htc.weatheridlescreen.com.htc.widget.profile.com.htc.widget.ringtone.com.htc.widget3d.weather.com.htc.clock3dwidgetcom.htc.messagecscom.htc.ml.PhotoLockScreencom.htc.musicenhancercom.htc.providers.uploads.com.htc.rosiewidgets.screentimeout.com.htc.video.com.broadcom.bt.app.system.com.google.android.apps.uploader.com.google.android.partnersetup.com.htc.CustomizationSetup.com.htc.FMRadioWidget.com.htc.OnlineAssetDetails.com.htc.Sync3DWidget.com.htc.UpgradeSetup.com.htc.Weather.com.htc.WeatherWallpaper.com.htc.albumcom.htc.HtcBeatsNotify.com.htc.MediaAutoUploadSetting.com.htc.MediaCacheService.com.htc.MusicWidget3D.com.htc.WifiRouter.com.htc.android.WeatherLiveWallpaper.com.htc.android.htcime.com.htc.android.image_wallpaper.com.htc.android.tvo			ut.com.htc.android.wallpaper.com.htc.china.callocation.com.htc.connectedMedia.com.htc.cspeoplesync.com.htc.dmc.com.htc.flashlight.com.htc.flashliteplugin.com.htc.fusion.FusionApk.com.htc.htccompressviewer.com.htc.lmwN.com.htc.lockscreen.com.htc.mysketcher.com.htc.pen.com.htc.photowidget3d.android.smartcard.com.android.deviceinfo.com.android.htccontacts.com.android.htcdialer.com.android.inputmethod.latin.com.android.providers.htcmessage.com.android.restartapp.com.android.setupwizard.com.android.updater.com.android.voicedialer.com.westtek.jcp"+
	"com.motorola.soundrecordercom.unit9.nanopandaandroid.ttscom.motorola.searchcom.android.calendarjoybits.doodlegodcom.pawprintgames.kamiretro.globalcom.gamelion.DrawSlashercom.motorola.wappushcom.motorola.android.syncml.servicecom.thepilltree.drawpongfullcom.motorola.protipscom.motorola.eventremindercom.motorola.widgetapp.weathercom.android.certinstaller.com.android.fileexplorer.com.android.monitor.com.android.packageinstaller.com.android.sidekick.android,com.android.bluetoothcom.adobe.flashplayer,com.android.browsercom.android.calculator2com.android.calendarcom.android.contactscom.android.deskclockcom.android.defcontainercom.android.emailcom.android.gallerycom.android.launchercom.android.mmscom.android.phonecom.android.providers.applicationscom.android.providers.calendarcom.android.providers.contactscom.android.providers.downloadscom.android.providers.downloads.uicom.android.providers.drm  appnamecom.android.providers.mediacom.android.providers.settingscom.android.providers.subscribedfeedscom.android.providers.telephonycom.android.providers.telocationcom.android.server.vpncom.android.settingscom.android.soundrecordercom.android.systemuicom.google.android.apps.mapscom.google.android.gsfcom.google.android.inputmethod.pinyincom.google.android.syncadapters.calendarcom.google.android.syncadapters.contactscom.miui.antispamcom.miui.backupcom.miui.cameracom.miui.cloudservicecom.miui.notescom.miui.playercom.miui.uac"
    +"com.google.android.feedbackcom.motorola.blur.service.blur.xmpp.fakeblurcom.motorola.blur.provider.myspacecom.android.htmlviewercom.android.contactscom.android.defcontainercom.android.providers.calendarcom.android.bluetoothcom.motorola.cardockcom.motorola.android.motophoneportal.androiduicom.motorola.chargeonlymodecom.motorola.blur.policymgr.providercom.motorola.dock.servicecom.android.soundrecordercom.motorola.blur.messaging.universalcom.android.packageinstallercom.adobe.flashplayercom.motorola.blur.provider.photobucketcom.android.calculator2com.motorola.blur.taskscom.motorola.blur.blurchoosercom.android.wallpapercom.motorola.blur.provider.facebookcom.android.providers.applicationscom.motorola.blur.provider.linkedincom.motorola.certificatemanagercom.motorola.contacts.preloadedcom.google.android.gsfcom.google.android.locationcom.google.android.syncadapters.calendarcom.google.android.syncadapters.contactscom.android.providers.drmcom.android.providers.downloads.uicom.motorola.android.omadownloadcom.android.providers.mediacom.android.providers.downloadscom.motorola.gallery.com.motorola.photowidget.com.motorola.android.omadrm.com.motorola.mediasync.com.motorola.android.wmdrm.dla.com.motorola.Dlna.com.motorola.blur.friendfeed.com.motorola.blur.service.snmessaging.engine.com.android.setupwizard.com.safenet.vpnclient.com.motorola.android.AudioEffectSettings.com.motorola.inputmethod.entry.com.motorola.blur.quickcontact.com.motorola.spellingcheckservice.com.motorola.blur.home.clock.com.motorola.dialer.com.motorola.inputmethod.gpinyin.com.arcsoft.photoworkshop.com.motorola.android.mtlr.com.motorola.blur.provider.suggestions.com.motorola.android.provisioning.com.motorola.android.mobad.service.com.motorola.blur.provider.lastfm.com.motorola.PerformanceManager.com.motorola.blur.provider.flickr.com.motorola.stickynote.com.motorola.mediashare.com.motorola.blur.provider.orkut.com.motorola.blur.provider.activesync.com.motorola.blur.provider.youtube.com.motorola.blur.suggestions.rulechecker.core.com.motorola.blur.adminfeed.com.motorola.blur.calendar.sync.activesync.com.motorola.blur.home.status.com.motorola.globalunplug.com.motorola.filemanager.com.motorola.togglewidgets.com.motorola.blur.service.blur.com.motorola.dlauncher.com.motorola.blur.richtext.com.motorola.blur.provider.picasa.com.motorola.blur.socialmessaging.com.motorola.blur.msexchangesvc.com.android.musicvis.com.motorola.quicksms.com.motorola.blur.email.com.motorola.blur.contacts.sync.com.motorola.android.simcontactadapter.com.motorola.blur.setupprovider.com.motorola.blur.home.com.motorola.blur.conversations.com.motorola.atcmd.plugin.com.motorola.blur.datacollector.service.com.motorola.bookmarkswidget.com.motorola.blur.updater.com.motorola.bluetooth.com.motorola.blur.service.email.com.motorola.blur.contacts.sync.activesync.com.motorola.blur.messaging.com.motorola.android.wmdrm.webpush.com.motorola.blur.policymgr.service.com.motorola.batterymanager.com.motorola.blur.provider.datacollector.com.motorola.blur.provider.email.com.motorola.inputmethod.latin.com.motorola.inputmethod.entry.tutorial.com.motorola.videoplayer.com.motorola.blur.service.storagemon.com.motorola.blur.providers.contacts.com.motorola.inputmethod.motosmarthandwriting.com.motorola.blur.provider.twitter.com.motorola.blur.alarmclock.com.motorola.android.dm.service.com.motorola.blur.setup.com.motorola.blur.news.com.motorola.blur.simmanager.com.motorola.blur.suggestions.scheduler.com.motorola.blur.service.search.com.motorola.usb.com.motorola.blur.provider.skyrock.com.motorola.blur.socialshare.com.motorola.zoom.com.motorola.blur.contacts.data.com.motorola.blur.home.message";


	/**
	 * Create the generic shell script header used to determine which iptables
	 * binary to use.
	 * 
	 * @param ctx
	 *            context
	 * @return script header
	 */
	private static String scriptHeader(Context ctx) {
		final String dir = ctx.getDir("bin", 0).getAbsolutePath();
		final String myiptables = dir + "/iptables_armv5";
		return "" + "IPTABLES=iptables\n" + "BUSYBOX=busybox\n" + "GREP=grep\n"
				+ "ECHO=echo\n" + "# Try to find busybox\n" + "if "
				+ dir
				+ "/busybox_g1 --help >/dev/null 2>/dev/null ; then\n"
				+ "	BUSYBOX="
				+ dir
				+ "/busybox_g1\n"
				+ "	GREP=\"$BUSYBOX grep\"\n"
				+ "	ECHO=\"$BUSYBOX echo\"\n"
				+ "elif busybox --help >/dev/null 2>/dev/null ; then\n"
				+ "	BUSYBOX=busybox\n"
				+ "elif /system/xbin/busybox --help >/dev/null 2>/dev/null ; then\n"
				+ "	BUSYBOX=/system/xbin/busybox\n"
				+ "elif /system/bin/busybox --help >/dev/null 2>/dev/null ; then\n"
				+ "	BUSYBOX=/system/bin/busybox\n"
				+ "fi\n"
				+ "# Try to find grep\n"
				+ "if ! $ECHO 1 | $GREP -q 1 >/dev/null 2>/dev/null ; then\n"
				+ "	if $ECHO 1 | $BUSYBOX grep -q 1 >/dev/null 2>/dev/null ; then\n"
				+ "		GREP=\"$BUSYBOX grep\"\n"
				+ "	fi\n"
				+ "	# Grep is absolutely required\n"
				+ "	if ! $ECHO 1 | $GREP -q 1 >/dev/null 2>/dev/null ; then\n"
				+ "		$ECHO The grep command is required. DroidWall will not work.\n"
				+ "		exit 1\n"
				+ "	fi\n"
				+ "fi\n"
				+ "# Try to find iptables\n"
				+ "if "
				+ myiptables
				+ " --version >/dev/null 2>/dev/null ; then\n"
				+ "	IPTABLES="
				+ myiptables + "\n" + "fi\n" + "";
	}

	/**
	 * Purge and re-add all rules (internal implementation).
	 * 
	 * @param ctx
	 *            application context (mandatory)
	 * @param uidsWifi
	 *            list of selected UIDs for WIFI to allow or disallow (depending
	 *            on the working mode)
	 * @param uids3g
	 *            list of selected UIDs for 2G/3G to allow or disallow
	 *            (depending on the working mode)
	 * @param showErrors
	 *            indicates if errors should be alerted
	 */
	private static boolean applyIptablesRulesImpl(Context ctx,
			List<Integer> uidsWifi, List<Integer> uids3g, boolean showErrors) {
		if (ctx == null) {
			return false;
		}
		GetRoot.assertBinaries(ctx, showErrors);
		final String ITFS_WIFI[] = { "tiwlan+", "wlan+", "eth+" };
		final String ITFS_3G[] = { "rmnet+", "pdp+", "ppp+", "uwbr+", "wimax+",
				"vsnet+" };
		// 不使用白名单
		final boolean whitelist = false;
		final boolean blacklist = !whitelist;
		// 默认false
		final boolean logenabled = false;
		final StringBuilder script = new StringBuilder();
		try {
			int code;
			script.append(scriptHeader(ctx));
			script.append(""
					+ "$IPTABLES --version || exit 1\n"
					+ "# Create the droidwall chains if necessary\n"
					+ "$IPTABLES -L droidwall >/dev/null 2>/dev/null || $IPTABLES --new droidwall || exit 2\n"
					+ "$IPTABLES -L droidwall-3g >/dev/null 2>/dev/null || $IPTABLES --new droidwall-3g || exit 3\n"
					+ "$IPTABLES -L droidwall-wifi >/dev/null 2>/dev/null || $IPTABLES --new droidwall-wifi || exit 4\n"
					+ "$IPTABLES -L droidwall-reject >/dev/null 2>/dev/null || $IPTABLES --new droidwall-reject || exit 5\n"
					+ "# Add droidwall chain to OUTPUT chain if necessary\n"
					+ "$IPTABLES -L OUTPUT | $GREP -q droidwall || $IPTABLES -A OUTPUT -j droidwall || exit 6\n"
					+ "# Flush existing rules\n"
					+ "$IPTABLES -F droidwall || exit 7\n"
					+ "$IPTABLES -F droidwall-3g || exit 8\n"
					+ "$IPTABLES -F droidwall-wifi || exit 9\n"
					+ "$IPTABLES -F droidwall-reject || exit 10\n" + "");
			// Check if logging is enabled
			if (logenabled) {
				script.append(""
						+ "# Create the log and reject rules (ignore errors on the LOG target just in case it is not available)\n"
						+ "$IPTABLES -A droidwall-reject -j LOG --log-prefix \"[DROIDWALL] \" --log-uid\n"
						+ "$IPTABLES -A droidwall-reject -j REJECT || exit 11\n"
						+ "");
			} else {
				script.append(""
						+ "# Create the reject rule (log disabled)\n"
						+ "$IPTABLES -A droidwall-reject -j REJECT || exit 11\n"
						+ "");
			}
			script.append("# Main rules (per interface)\n");
			for (final String itf : ITFS_3G) {
				script.append("$IPTABLES -A droidwall -o ").append(itf)
						.append(" -j droidwall-3g || exit\n");
			}
			for (final String itf : ITFS_WIFI) {
				script.append("$IPTABLES -A droidwall -o ").append(itf)
						.append(" -j droidwall-wifi || exit\n");
			}
			
			script.append("# Filtering rules\n");
			final String targetRule = (whitelist ? "RETURN"
					: "droidwall-reject");
			final boolean any_3g = uids3g.indexOf(SPECIAL_UID_ANY) >= 0;
			final boolean any_wifi = uidsWifi.indexOf(SPECIAL_UID_ANY) >= 0;
			if (whitelist && !any_wifi) {
				// When "white listing" wifi, we need to ensure that the dhcp
				// and wifi users are allowed
				int uid = android.os.Process.getUidForName("dhcp");
				if (uid != -1) {
					script.append("# dhcp user\n");
					script.append(
							"$IPTABLES -A droidwall-wifi -m owner --uid-owner ")
							.append(uid).append(" -j RETURN || exit\n");
				}
				uid = android.os.Process.getUidForName("wifi");
				if (uid != -1) {
					script.append("# wifi user\n");
					script.append(
							"$IPTABLES -A droidwall-wifi -m owner --uid-owner ")
							.append(uid).append(" -j RETURN || exit\n");
				}
			}
			if (any_3g) {
				if (blacklist) {
					/* block any application on this interface */
					script.append("$IPTABLES -A droidwall-3g -j ")
							.append(targetRule).append(" || exit\n");
				}
			} else {
				/* release/block individual applications on this interface */
				for (final Integer uid : uids3g) {
					if (uid >= 0)
						script.append(
								"$IPTABLES -A droidwall-3g -m owner --uid-owner ")
								.append(uid).append(" -j ").append(targetRule)
								.append(" || exit\n");
				}
			}
			if (any_wifi) {
				if (blacklist) {
					/* block any application on this interface */
					script.append("$IPTABLES -A droidwall-wifi -j ")
							.append(targetRule).append(" || exit\n");
				}
			} else {
				/* release/block individual applications on this interface */
				for (final Integer uid : uidsWifi) {
					if (uid >= 0)
						script.append(
								"$IPTABLES -A droidwall-wifi -m owner --uid-owner ")
								.append(uid).append(" -j ").append(targetRule)
								.append(" || exit\n");
				}
			}
			if (whitelist) {
				if (!any_3g) {
					if (uids3g.indexOf(SPECIAL_UID_KERNEL) >= 0) {
						script.append("# hack to allow kernel packets on white-list\n");
						script.append("$IPTABLES -A droidwall-3g -m owner --uid-owner 0:999999999 -j droidwall-reject || exit\n");
					} else {
						script.append("$IPTABLES -A droidwall-3g -j droidwall-reject || exit\n");
					}
				}
				if (!any_wifi) {
					if (uidsWifi.indexOf(SPECIAL_UID_KERNEL) >= 0) {
						script.append("# hack to allow kernel packets on white-list\n");
						script.append("$IPTABLES -A droidwall-wifi -m owner --uid-owner 0:999999999 -j droidwall-reject || exit\n");
					} else {
						script.append("$IPTABLES -A droidwall-wifi -j droidwall-reject || exit\n");
					}
				}
			} else {
				if (uids3g.indexOf(SPECIAL_UID_KERNEL) >= 0) {
					script.append("# hack to BLOCK kernel packets on black-list\n");
					script.append("$IPTABLES -A droidwall-3g -m owner --uid-owner 0:999999999 -j RETURN || exit\n");
					script.append("$IPTABLES -A droidwall-3g -j droidwall-reject || exit\n");
				}
				if (uidsWifi.indexOf(SPECIAL_UID_KERNEL) >= 0) {
					script.append("# hack to BLOCK kernel packets on black-list\n");
					script.append("$IPTABLES -A droidwall-wifi -m owner --uid-owner 0:999999999 -j RETURN || exit\n");
					script.append("$IPTABLES -A droidwall-wifi -j droidwall-reject || exit\n");
				}
			}
			final StringBuilder res = new StringBuilder();
			code = runScriptAsRoot(ctx, script.toString(), res);
//			code = runScript(ctx, script.toString(), res);
			if (showErrors && code != 0) {
				String msg = res.toString();
				// Remove unnecessary help message from output
				if (msg.indexOf("\nTry `iptables -h' or 'iptables --help' for more information.") != -1) {
					msg = msg
							.replace(
									"\nTry `iptables -h' or 'iptables --help' for more information.",
									"");
				}
//					alert(ctx, "应用防火墙出错: " + code + "\n\n"
//					 + msg.trim()
//					);
				alert(ctx, "部分机型不支持防火墙设置");
			
			} else {
				return true;
			}
		} catch (Exception e) {
			if (showErrors)
				alert(ctx, "应用防火墙出错" + e);
		}
		return false;
	}

	/**
	 * 用于开启防火墙 Purge and re-add all rules.
	 * 
	 * @param ctx
	 *            application context (mandatory)
	 * @param showErrors
	 *            indicates if errors should be alerted
	 */
	public static boolean applyIptablesRules(Context ctx, boolean showErrors) {
		if (ctx == null) {
			return false;
		}
		List<Integer> uids_wifi = new LinkedList<Integer>();
		List<Integer> uids_3g = new LinkedList<Integer>();
		final SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, 0);
		final String savedUids_wifi = prefs.getString(PREF_WIFI_UIDS, "");
		final String savedUids_3g = prefs.getString(PREF_3G_UIDS, "");

		if (savedUids_wifi.length() > 0) {
			// Check which applications are allowed on wifi
			final StringTokenizer tok = new StringTokenizer(savedUids_wifi, "|");
			while (tok.hasMoreTokens()) {
				final String uid = tok.nextToken();
				if (!uid.equals("")) {
					try {
						uids_wifi.add(Integer.parseInt(uid));
					} catch (Exception ex) {
					}
				}
			}
		}
		if (savedUids_3g.length() > 0) {
			// Check which applications are allowed on 2G/3G
			final StringTokenizer tok = new StringTokenizer(savedUids_3g, "|");
			while (tok.hasMoreTokens()) {
				final String uid = tok.nextToken();
				if (!uid.equals("")) {
					try {
						uids_3g.add(Integer.parseInt(uid));
					} catch (Exception ex) {
					}
				}
			}
		}
		return applyIptablesRulesImpl(ctx, uids_wifi, uids_3g, showErrors);
	}

	/**
	 * Purge all iptables rules.
	 * 
	 * @param ctx
	 *            mandatory context
	 * @param showErrors
	 *            indicates if errors should be alerted
	 * @return true if the rules were purged
	 */
	public static boolean purgeIptables(Context ctx, boolean showErrors) {
		StringBuilder res = new StringBuilder();
		try {
			 GetRoot.assertBinaries(ctx, showErrors);
			int code = runScriptAsRoot(ctx, scriptHeader(ctx)
					+ "$IPTABLES -F droidwall\n"
					+ "$IPTABLES -F droidwall-reject\n"
					+ "$IPTABLES -F droidwall-3g\n"
					+ "$IPTABLES -F droidwall-wifi\n", res);
			if (code == -1) {
				if (showErrors)
					alert(ctx, "error purging iptables. exit code: " + code
							+ "\n" + res);
				return false;
			}
			return true;
		} catch (Exception e) {
			if (showErrors)
				alert(ctx, "error purging iptables: " + e);
			return false;
		}
	}


	/**
	 * Runs a script as root (multiple commands separated by "\n").
	 * 
	 * @param ctx
	 *            mandatory context
	 * @param script
	 *            the script to be executed
	 * @param res
	 *            the script output response (stdout + stderr)
	 * @param timeout
	 *            timeout in milliseconds (-1 for none)
	 * @return the script exit code
	 */
	public static int runScriptAsRoot(Context ctx, String script,
			StringBuilder res, long timeout) {
		return runScript(ctx, script, res, timeout, true);
	}

	/**
	 * Runs a script as root (multiple commands separated by "\n") with a
	 * default timeout of 20 seconds.
	 * 
	 * @param ctx
	 *            mandatory context
	 * @param script
	 *            the script to be executed
	 * @param res
	 *            the script output response (stdout + stderr)
	 * @param timeout
	 *            timeout in milliseconds (-1 for none)
	 * @return the script exit code
	 * @throws IOException
	 *             on any error executing the script, or writing it to disk
	 */
	public static int runScriptAsRoot(Context ctx, String script,
			StringBuilder res) throws IOException {
		return runScriptAsRoot(ctx, script, res, 20000);
	}

	/**
	 * Runs a script as a regular user (multiple commands separated by "\n")
	 * with a default timeout of 20 seconds.
	 * 
	 * @param ctx
	 *            mandatory context
	 * @param script
	 *            the script to be executed
	 * @param res
	 *            the script output response (stdout + stderr)
	 * @param timeout
	 *            timeout in milliseconds (-1 for none)
	 * @return the script exit code
	 * @throws IOException
	 *             on any error executing the script, or writing it to disk
	 */
	public static int runScript(Context ctx, String script, StringBuilder res)
			throws IOException {
		return runScript(ctx, script, res, 10000, false);
	}

	/**
	 * Runs a script, wither as root or as a regular user (multiple commands
	 * separated by "\n").
	 * 
	 * @param ctx
	 *            mandatory context
	 * @param script
	 *            the script to be executed
	 * @param res
	 *            the script output response (stdout + stderr)
	 * @param timeout
	 *            timeout in milliseconds (-1 for none)
	 * @return the script exit code
	 */
	public static int runScript(Context ctx, String script, StringBuilder res,
			long timeout, boolean asroot) {
		final File file = new File(ctx.getDir("bin", 0), SCRIPT_FILE);
		final ScriptRunner runner = new ScriptRunner(file, script, res, asroot);
		runner.start();
		try {
			if (timeout > 0) {
				runner.join(timeout);
			} else {
				runner.join();
			}
			if (runner.isAlive()) {
				// Timed-out
				runner.interrupt();
				runner.join(150);
				runner.destroy();
				runner.join(50);
			}
		} catch (InterruptedException ex) {
		}
		return runner.exitcode;
	}

	/**
	 * Internal thread used to execute scripts (as root or not).
	 */
	private static final class ScriptRunner extends Thread {
		private final File file;
		private final String script;
		private final StringBuilder res;
		private final boolean asroot;
		public int exitcode = -1;
		private Process exec;

		/**
		 * Creates a new script runner.
		 * 
		 * @param file
		 *            temporary script file
		 * @param script
		 *            script to run
		 * @param res
		 *            response output
		 * @param asroot
		 *            if true, executes the script as root
		 */
		public ScriptRunner(File file, String script, StringBuilder res,
				boolean asroot) {
			this.file = file;
			this.script = script;
			this.res = res;
			this.asroot = asroot;
		}

		@Override
		public void run() {
			try {
				file.createNewFile();
				final String abspath = file.getAbsolutePath();
				Runtime.getRuntime().exec("chmod 777 " + abspath).waitFor();
				final OutputStreamWriter out = new OutputStreamWriter(
						new FileOutputStream(file));
				if (new File("/system/bin/sh").exists()) {
					out.write("#!/system/bin/sh\n");
				}
				out.write(script);
				if (!script.endsWith("\n"))
					out.write("\n");
				out.write("exit\n");
				out.flush();
				out.close();
				if (this.asroot) {
					exec = Runtime.getRuntime().exec("su -c " + abspath);
				} else {
					exec = Runtime.getRuntime().exec("sh " + abspath);
				}
				InputStreamReader r = new InputStreamReader(
						exec.getInputStream());
				final char buf[] = new char[1024];
				int read = 0;
				while ((read = r.read(buf)) != -1) {
					if (res != null)
						res.append(buf, 0, read);
				}
				r = new InputStreamReader(exec.getErrorStream());
				read = 0;
				while ((read = r.read(buf)) != -1) {
					if (res != null)
						res.append(buf, 0, read);
				}
				if (exec != null)
					this.exitcode = exec.waitFor();
			} catch (InterruptedException ex) {
				if (res != null)
					res.append("\nOperation timed-out");
			} catch (Exception ex) {
				if (res != null)
					res.append("\n" + ex);
			} finally {
				destroy();
			}
		}

		/**
		 * Destroy this script runner
		 */
		public synchronized void destroy() {
			if (exec != null)
				exec.destroy();
			exec = null;
		}
	}

	/**
	 * Display a simple alert box
	 * 
	 * @param ctx
	 *            context
	 * @param msg
	 *            message
	 */
	public static void alert(Context ctx, CharSequence msg) {
		if (ctx != null) {
			new AlertDialog.Builder(ctx)
					.setNeutralButton(android.R.string.ok, null)
					.setMessage(msg).show();
		}
	}

	public static void saveRules(Context context,
			HashMap<Integer, IsChecked> map) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final StringBuilder newuids_wifi = new StringBuilder();
		final StringBuilder newuids_3g = new StringBuilder();
		final StringBuilder newuids_all = new StringBuilder();

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			IsChecked ic = (IsChecked) entry.getValue();

			if (newuids_all.length() != 0)
				newuids_all.append('|');
			newuids_all.append(entry.getKey());

			if (ic.selected_wifi) {
				if (newuids_wifi.length() != 0)
					newuids_wifi.append('|');
				newuids_wifi.append(entry.getKey());
			}
			if (ic.selected_3g) {
				if (newuids_3g.length() != 0)
					newuids_3g.append('|');
				newuids_3g.append(entry.getKey());
			}
		}

		final Editor edit = prefs.edit();
		edit.putString(PREF_WIFI_UIDS, newuids_wifi.toString());
		edit.putString(PREF_3G_UIDS, newuids_3g.toString());
		edit.putString(PREF_ALL_UIDS, newuids_all.toString());
		edit.putBoolean(PREF_S, true);
		edit.commit();
	}

	public static HashMap<Integer, IsChecked> getMap(Context context,
			ArrayList<PackageInfo> myAppList) {

		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final String savedUids_wifi = prefs.getString(PREF_WIFI_UIDS, "");
		final String savedUids_3g = prefs.getString(PREF_3G_UIDS, "");
		final String savedUids_all = prefs.getString(PREF_ALL_UIDS, "");
		boolean cache = prefs.getBoolean(PREF_S, false);
		HashMap map = new HashMap<Integer, IsChecked>();
		List<Integer> uids_wifi = new LinkedList<Integer>();
		List<Integer> uids_3g = new LinkedList<Integer>();
		List<Integer> uids_all = new LinkedList<Integer>();
		if (cache) {
			if (savedUids_wifi.length() > 0) {
				// Check which applications are allowed on wifi
				final StringTokenizer tok = new StringTokenizer(savedUids_wifi,
						"|");
				while (tok.hasMoreTokens()) {
					final String uid = tok.nextToken();
					if (!uid.equals("")) {
						try {
							uids_wifi.add(Integer.parseInt(uid));
						} catch (Exception ex) {
						}
					}
				}
			}
			if (savedUids_3g.length() > 0) {
				// Check which applications are allowed on 3g
				final StringTokenizer tok = new StringTokenizer(savedUids_3g,
						"|");
				while (tok.hasMoreTokens()) {
					final String uid = tok.nextToken();
					if (!uid.equals("")) {
						try {
							uids_3g.add(Integer.parseInt(uid));
						} catch (Exception ex) {
						}
					}
				}
			}
			if (savedUids_all.length() > 0) {
				// Check which applications are allowed on 3g
				final StringTokenizer tok = new StringTokenizer(savedUids_all,
						"|");
				while (tok.hasMoreTokens()) {
					final String uid = tok.nextToken();
					if (!uid.equals("")) {
						try {
							uids_all.add(Integer.parseInt(uid));
						} catch (Exception ex) {
						}
					}
				}
			}
		}

		for (int i = 0; i < myAppList.size(); i++) {
			PackageInfo pi = myAppList.get(i);
			int uid = pi.applicationInfo.uid;
			IsChecked ic = new IsChecked();
			if (cache) {
				if (uids_all.contains(uid)) {
					if (uids_3g.contains(uid)) {
						ic.selected_3g = true;
					}
					if (uids_wifi.contains(uid)) {
						ic.selected_wifi = true;
					}
				}
			}
			map.put(uid, ic);
		}
		return map;
	}

	public static boolean isShowTip(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		boolean isShow = prefs.getBoolean(PREF_SHOW, true);
		return isShow;
	}

	public static void isShowTipSet(Context context, boolean isShow) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final Editor edit = prefs.edit();
		edit.putBoolean(PREF_SHOW, isShow);
		edit.commit();
	}

	public static boolean fireTip(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		boolean isShow = prefs.getBoolean(PREF_TIP, true);
		return isShow;
	}

	public static void fireTipSet(Context context, boolean isShow) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final Editor edit = prefs.edit();
		edit.putBoolean(PREF_TIP, isShow);
		edit.commit();
	}

}
