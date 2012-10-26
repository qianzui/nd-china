package com.hiapk.contral.weibo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.hiapk.logs.Logs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ScreenShot {
	private static String PIC_PATH_MEMORY_DIR;
	private static String PIC_PATH_SDCARD_DIR;
	private static String USEING_PATH_DIR;
	private static String TAG = "ScreenShot";

	// ��ȡָ��Activity�Ľ��������浽png�ļ�
	private static Bitmap takeScreenShot(Activity activity) {

		// View������Ҫ��ͼ��View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// ��ȡ״̬���߶�
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// ��ȡ��Ļ���͸�
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();

		// ȥ��������
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		if (b1 == null) {
			Logs.d(TAG, "b1=null");
		}
		Logs.d(TAG, "statusBarHeight=" + statusBarHeight);
		Logs.d(TAG, "width=" + width);
		Logs.d(TAG, "height=" + height);
		Logs.d(TAG, "b1width=" + b1.getWidth());
		Logs.d(TAG, "b1height=" + b1.getHeight());
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width,
				b1.getHeight() - statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	// ���浽sdcard
	private static void savePic(Bitmap b, String strFileName) {
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

	// �������
	public static String shoot(Activity a) {
		PIC_PATH_MEMORY_DIR = a.getFilesDir().getAbsolutePath()
				+ File.separator + "ScreenShoot";
		PIC_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator + "SpearheadLog";
		createShootDir();
		ScreenShot.savePic(ScreenShot.takeScreenShot(a), USEING_PATH_DIR
				+ "/shoot.png");
		return USEING_PATH_DIR + "/shoot.png";
	}

	/**
	 * ������ͼĿ¼
	 */
	private static void createShootDir() {
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
			USEING_PATH_DIR = PIC_PATH_SDCARD_DIR;
		}
	}

	private static boolean isenoughspace(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		long sdCardSize = (availableBlocks * blockSize) / 1024;// KBֵ
		if (sdCardSize > 1000) {
			System.out.println("SDcardSize:::" + 500 + "KB");
			return true;
		} else {
			Toast.makeText(context, "SD���ռ䲻��", Toast.LENGTH_SHORT).show();
			return false;
		}
	}
}
