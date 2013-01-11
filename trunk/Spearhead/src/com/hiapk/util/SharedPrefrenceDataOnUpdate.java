package com.hiapk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceDataOnUpdate {
	// ²Ù×÷sharedprefrence
	private String PREFS_NAME = "updateprefs";
	private String UID_RECORD_UPDATED = "isuidrecoredtypeupdated";
	private String Total_RECORD_UPDATED121 = "totalrecordupdtate121";
	private String UPDATED_NOTICE125 = "updatenotice125";
	private SharedPreferences prefs;
	private Editor UseEditor;

	public SharedPrefrenceDataOnUpdate(Context context) {
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
	}

	public boolean isUidRecordUpdated() {
		boolean blean = prefs.getBoolean(UID_RECORD_UPDATED, true);
		return blean;
	}

	public void setUidRecordUpdated(boolean blean) {
		UseEditor.putBoolean(UID_RECORD_UPDATED, blean);
		UseEditor.commit();
	}

	public boolean isTotal121updated() {
		boolean blean = prefs.getBoolean(Total_RECORD_UPDATED121, false);
		return blean;
	}

	public void setTotal121updated(boolean blean) {
		UseEditor.putBoolean(Total_RECORD_UPDATED121, blean);
		UseEditor.commit();
	}

	public boolean isVersionupdated() {
		boolean blean = prefs.getBoolean(UPDATED_NOTICE125, false);
		return blean;
	}
	
	public void setVersionupdated(boolean blean) {
		UseEditor.putBoolean(UPDATED_NOTICE125, blean);
		UseEditor.commit();
	}

}
