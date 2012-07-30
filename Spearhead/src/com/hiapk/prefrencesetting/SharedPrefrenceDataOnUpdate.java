package com.hiapk.prefrencesetting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceDataOnUpdate {
	// ²Ù×÷sharedprefrence
	private String PREFS_NAME = "allprefs";
	private String UID_RECORD_UPDATED = "isuidrecoredtypeupdated";
	private SharedPreferences prefs;
	private Editor UseEditor;

	public SharedPrefrenceDataOnUpdate(Context context) {
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
	}

	public boolean isUidRecordUpdated() {
		boolean blean = prefs.getBoolean(UID_RECORD_UPDATED, false);
		return blean;
	}

	public void setUidRecordUpdated(boolean blean) {
		UseEditor.putBoolean(UID_RECORD_UPDATED, blean);
		UseEditor.commit();
	}

}
