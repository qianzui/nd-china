package com.hiapk.prefrencesetting;

import com.hiapk.spearhead.R;
import com.hiapk.widget.ProgramNotify;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class PrefrenceSetting extends PreferenceActivity {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 系统设置
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	//
	CheckBoxPreference isNotifyOpen;
	CheckBoxPreference isfloatIndicatorOpen;
	ListPreference refreshFres;
	PreferenceScreen clearData;
	Context context = this;

	SharedPrefrenceData sharedData;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.setting_pref);
		isNotifyOpen = (CheckBoxPreference) findPreference(SYS_PRE_NOTIFY);
		isfloatIndicatorOpen = (CheckBoxPreference) findPreference(SYS_PRE_FLOAT_CTRL);
		refreshFres = (ListPreference) findPreference(SYS_PRE_REFRESH_FRZ);
		clearData = (PreferenceScreen) findPreference(SYS_PRE_CLEAR_DATA);
		// 监听
		isNotifyOpen.setOnPreferenceClickListener(oclick);
		isfloatIndicatorOpen.setOnPreferenceClickListener(oclick);
		refreshFres.setOnPreferenceChangeListener(ochange);
		clearData.setOnPreferenceClickListener(oclick);
		sharedData = new SharedPrefrenceData(context);
		// 初始化状态
		boolean notifyStates = sharedData.isNotifyOpen();
		isNotifyOpen.setChecked(notifyStates);
	}

	OnPreferenceClickListener oclick = new OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			if (preference.equals(isNotifyOpen)) {
				boolean isOpenNotify = isNotifyOpen.isChecked();
				sharedData.setNotifyOpen(isOpenNotify);
				
				ProgramNotify promNotify = new ProgramNotify();
				if (isOpenNotify == true) {
					promNotify.showNotice(context);
				} else {
					promNotify.cancelProgramNotify(context);
				}
				return true;
			}
			if (preference.equals(isfloatIndicatorOpen)) {

				return true;
			}
			if (preference.equals(clearData)) {

				return true;
			}
			return false;
		}
	};
	OnPreferenceChangeListener ochange = new OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			return false;
		}
	};
}
