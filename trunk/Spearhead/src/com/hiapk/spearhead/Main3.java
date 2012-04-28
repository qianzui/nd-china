package com.hiapk.spearhead;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main3 extends Activity {
	Button combo;
	Button monthWarning;
	Button dayWarning;
	Button warningAct;
	TextView TextView_month;
	EditText ed2;

	private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FILL_PARENT = ViewGroup.LayoutParams.FILL_PARENT;
	EditText edit;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		combo = (Button) findViewById(R.id.combo);
		monthWarning = (Button) findViewById(R.id.monthWarning);
		dayWarning = (Button) findViewById(R.id.dayWarning);
		warningAct = (Button) findViewById(R.id.warningAct);
		TextView_month = (TextView) findViewById(R.id.tv_month);
//		ed2 = (EditText) findViewById(R.id.ed2);

		TextView_month.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthSet().show();

			}
		});
		combo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogCombo();

			}
		});
		monthWarning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogMonthWarning();

			}
		});

		dayWarning.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// dialogDayWarning();
				dialogMonthSet().show();

			}
		});

		warningAct.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogWarningAct();

			}
		});

	}

	protected AlertDialog dialogMonthSet() {
		// TODO Auto-generated method stub
		LayoutInflater factory = LayoutInflater.from(Main3.this);
		final View textEntryView = factory.inflate(
				R.layout.alert_dialog_text_entry, null);
		final EditText et_month=(EditText) textEntryView.findViewById(R.id.ev_alert);
		return new AlertDialog.Builder(Main3.this)
				.setTitle("请设置每月流量限额")
				.setView(textEntryView)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						TextView_month.setText(et_month.getText());
						/* User clicked OK so do some stuff */
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						/* User clicked cancel so do some stuff */
					}
				}).create();

	}

	public void dialogCombo() {
		new AlertDialog.Builder(this).setTitle("手动查询").setMessage("手动查询事件")
				.setPositiveButton("确定", null).show();

	}

	public void dialogMonthWarning() {
		new AlertDialog.Builder(this).setTitle("月预警").setMessage("月预警事件")
				.setPositiveButton("确定", null).show();

	}

	public void dialogDayWarning() {
		// edit = new EditText(Main3.this);
		// edit.setGravity(Gravity.CENTER);
		// final FrameLayout fl = new FrameLayout(null);
		// fl.addView(edit,
		// new FrameLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT)); // 给某屏幕添加组件
		// edit.setText("Preset Text");
		//
		new AlertDialog.Builder(this).setTitle("日预警").setMessage("日预警事件")
		// .setView(fl)
				.setPositiveButton("确定", null).show();

	}

	public void dialogWarningAct() {
		new AlertDialog.Builder(this).setTitle("预警动作").setMessage("预警动作")
				.setPositiveButton("确定", null).show();

	}

	public boolean onTouchEvent(MotionEvent event) {

		// float x = event.getX();
		// float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 触摸屏幕时刻
			// editText_month.clearFocus();
			// ed2.clearFocus();
			// Toast.makeText(getParent(),"x="+x+" y="+y,
			// Toast.LENGTH_LONG).show();
			break;
		// 触摸并移动时刻
		case MotionEvent.ACTION_MOVE:

			break;
		// 终止触摸时刻
		case MotionEvent.ACTION_UP:
			break;
		}

		return true;

	}

}
