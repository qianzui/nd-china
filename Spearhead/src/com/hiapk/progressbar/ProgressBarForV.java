package com.hiapk.progressbar;

import android.os.AsyncTask;
import android.widget.ProgressBar;

public class ProgressBarForV extends
		AsyncTask<ProgressBar, ProgressBar, Integer> {
	ProgressBar proBar;
	private int i = 0;
	public int j = 50;

	protected Integer doInBackground(ProgressBar... params) {
		// TODO Auto-generated method stub
		// proBar=params[0];
		// params[0].setProgress(10);
		proBar = params[0];
		for (; i < j; i++) {
			publishProgress(params[0]);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return i;
	}

	@Override
	protected void onProgressUpdate(ProgressBar... progress) {
		progress[0].setProgress(i);
		// progress[0].setBackgroundColor(Color.rgb(255-i*2, i*2, 0));
		// Log.d("onProgress", "progress:" );
	}

	@Override
	protected void onPostExecute(Integer result) {
		proBar.setProgress(result);
		// proBar.setProgress(result);
		// Log.d("onProgress", "result:" + result);
	}

}
