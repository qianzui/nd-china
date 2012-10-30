package com.hiapk.contral.weibo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hiapk.logs.Logs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;

public class ScreenShot {
	private String PIC_PATH_MEMORY_DIR;
	private String PIC_PATH_SDCARD_DIR;
	private String USEING_PATH_DIR = "";
	private String TAG = "ScreenShot";
	private Activity activity;

	public ScreenShot(Activity activity) {
		this.activity = activity;
	}

	// 获取指定Activity的截屏，保存到png文件
	private Bitmap takeScreenShot(Activity activity) {

		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();

		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		if (b1 == null) {
			Logs.d(TAG, "b1=null");
		}
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width,
				b1.getHeight() - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	// 保存到sdcard
	private void savePic(Bitmap b, String strFileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(strFileName);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.PNG, 90, fos);
				// b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!b.isRecycled()) {
			b.recycle();
		}
	}

	// 程序入口
	public String shoot() {
		PIC_PATH_MEMORY_DIR = activity.getFilesDir().getAbsolutePath()
				+ File.separator + "ScreenShoot";
		PIC_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "SpearheadLog";
		createShootDir();
		if (USEING_PATH_DIR == "") {
			return "";
		}
		savePic(takeScreenShot(activity), USEING_PATH_DIR + "/shoot.png");
		return USEING_PATH_DIR + "/shoot.png";
	}

	/**
	 * 创建截图目录
	 */
	private void createShootDir() {
		File file = new File(PIC_PATH_MEMORY_DIR);
		boolean mkOk;
		if (!file.isDirectory()) {
			mkOk = file.mkdirs();
			if (!mkOk) {
				mkOk = file.mkdirs();
			}
		}
		USEING_PATH_DIR = PIC_PATH_MEMORY_DIR;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File(PIC_PATH_SDCARD_DIR);
			if (!file.isDirectory()) {
				mkOk = file.mkdirs();
				if (!mkOk) {
					Log.d(TAG, "createSDcard dir fail");
					return;
				}
			}
			if (hasenoughSpace()) {
				USEING_PATH_DIR = PIC_PATH_SDCARD_DIR;
			}

		}
	}

	/**
	 * 是否有足够的sd卡空间
	 * 
	 * @return
	 */
	private boolean hasenoughSpace() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long sdCardSize = (availableBlocks * blockSize) / 1024;// KB值
		if (sdCardSize > 300) {
			Logs.d(TAG, "SDcardSize:::" + sdCardSize + "KB");
			return true;
		} else {
			Logs.d(TAG, "SD卡空间不足无法获取屏幕截图" + "SDcardSize:::" + sdCardSize + "KB");
			return false;
		}
	}
}
