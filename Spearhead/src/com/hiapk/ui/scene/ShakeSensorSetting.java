package com.hiapk.ui.scene;

import com.hiapk.logs.Logs;
import com.hiapk.spearhead.R;
import com.hiapk.ui.skin.SkinCustomMains;
import com.hiapk.util.SharedPrefrenceData;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ShakeSensorSetting extends Activity {

	private FrameLayout sensorSettingTitleBackground;
	private ImageView sensor_setting_back;
	private ImageView shake_test_image;
	private SeekBar seekBar;
	private Button shake_reset_button;
	private SharedPrefrenceData sharedpref;
	public Context context = this;
	public float medumValue = 12;
	public float DEFAULT_VALUES = 12;
	public float MIN_VALUES = 9.5f;
	public float MAX_VALUES = 24;
	public int bg_num = 0;
	SensorManager sensorManager;
	Handler handler;
	private String TAG = "ShakeSensor";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_sensor);
		sharedpref = new SharedPrefrenceData(context);
		sensorSettingTitleBackground = (FrameLayout) findViewById(R.id.sensorSettingTitleBackground);
		sensor_setting_back = (ImageView) findViewById(R.id.sensor_setting_back);
		shake_test_image = (ImageView) findViewById(R.id.shake_test_image);
		seekBar = (SeekBar) findViewById(R.id.seekBar);
		shake_reset_button = (Button) findViewById(R.id.shake_reset_button);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(mSensorEventListener,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		initScene();
		shakeTest();

	}

	private void shakeTest() {
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 20:
					changeColor();
					break;
				}
			}
		};
	}

	private void changeColor() {
		if (bg_num == 5) {
			bg_num = 0;
		} else {
			bg_num = bg_num + 1;
		}
		int bg_Id = context.getResources().getIdentifier(
				"maintab_title_bg" + bg_num, "drawable", "com.hiapk.spearhead");
		shake_test_image.setBackgroundResource(bg_Id);

	}

	private void initScene() {
		sensorSettingTitleBackground.setBackgroundResource(SkinCustomMains
				.titleBackground());
		shake_test_image.setBackgroundResource(SkinCustomMains
				.titleBackground());
		shake_reset_button.setBackgroundResource(SkinCustomMains
				.buttonBackgroundLight());
		medumValue = sharedpref.getMedianValues();
		seekBar.setMax(100);
		if (medumValue >= DEFAULT_VALUES) {
			seekBar.setProgress((int) ((MAX_VALUES - medumValue)
					/ (MAX_VALUES - DEFAULT_VALUES) * 50));
		} else {
			seekBar.setProgress((int) (100 - (medumValue - MIN_VALUES)
					/ (DEFAULT_VALUES - MIN_VALUES) * 50));
		}
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int x = seekBar.getProgress();
				if (x >= 50) {
					float temp = (100 - (float) x) / 50
							* (DEFAULT_VALUES - MIN_VALUES) + MIN_VALUES;
					medumValue = temp;
				} else {
					float temp = MAX_VALUES - (float) x / 50
							* (MAX_VALUES - DEFAULT_VALUES);
					medumValue = temp;
				}
				sharedpref.setMedianValues(medumValue);
				Logs.i(TAG, "values:" + medumValue);
			}
		});

		shake_reset_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				medumValue = DEFAULT_VALUES;
				seekBar.setProgress(50);
				sharedpref.setMedianValues(medumValue);
			}
		});

		sensor_setting_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
				sensor_setting_back.setImageResource(R.drawable.back_black);
			}
		});
	}

	@Override
	protected void onResume() {
		initScene();
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void finish() {
		sensorManager.unregisterListener(mSensorEventListener);
		sensorManager = null;
		super.finish();
	}

	private SensorEventListener mSensorEventListener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			float testValue = sharedpref.getMedianValues();
			float[] values = event.values;
			float x = values[0];
			float y = values[1];
			float z = values[2];
			if (x > testValue || x < -testValue || y > testValue
					|| y < -testValue || z > testValue || z < -testValue) {
				Message msg = new Message();
				msg.what = 20;
				handler.sendMessage(msg);
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

}
