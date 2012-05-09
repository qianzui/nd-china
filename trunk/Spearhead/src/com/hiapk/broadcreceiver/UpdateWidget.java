package com.hiapk.broadcreceiver;

import com.hiapk.widget.ProgramNotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UpdateWidget extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ProgramNotify programNotify = new ProgramNotify();
		programNotify.showNotice(context);
	}

}
