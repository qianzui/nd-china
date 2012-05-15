package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.widget.ProgramNotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateWidget extends BroadcastReceiver {
	

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		SharedPrefrenceData sharedData=new SharedPrefrenceData(context);
		if (sharedData.isSQLinited() == true) {

			ProgramNotify programNotify = new ProgramNotify();
			programNotify.showNotice(context);
		}
	}

}
