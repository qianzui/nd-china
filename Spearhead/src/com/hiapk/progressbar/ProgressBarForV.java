package com.hiapk.progressbar;

import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.R;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

public class ProgressBarForV extends AsyncTask<ProgressBar , ProgressBar, Integer> {
	ProgressBar proBar;
	private int i=0;
	public int j=50;
	protected Integer doInBackground(ProgressBar... params) {
		// TODO Auto-generated method stub
//		proBar=params[0];
//		params[0].setProgress(10);
		for (; i < j; i++) {
			publishProgress(params[0]);
		}
		
		return i;
	}

	@Override
	protected void onProgressUpdate(ProgressBar... progress) {
		progress[0].setProgress(i);
//		progress[0].setBackgroundColor(Color.rgb(255-i*2, i*2, 0));
//		Log.d("onProgress", "progress:" );
	}

	@Override
	protected void onPostExecute(Integer result) {
//		proBar.setProgress(result);
//		Log.d("onProgress", "result:" + result);
	}

	
}
