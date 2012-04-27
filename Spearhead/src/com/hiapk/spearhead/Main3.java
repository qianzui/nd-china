package com.hiapk.spearhead;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.method.Touch;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3 extends Activity{
	Button combo;
	Button monthWarning;
	Button dayWarning;
	Button warningAct;
	EditText ed1;
	EditText ed2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main3);
		combo = (Button)findViewById(R.id.combo);
		monthWarning = (Button)findViewById(R.id.monthWarning);
		dayWarning = (Button)findViewById(R.id.dayWarning);
		warningAct = (Button)findViewById(R.id.warningAct);
		ed1 =( EditText) findViewById(R.id.ed1);
		ed2 =( EditText) findViewById(R.id.ed2);



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
				dialogDayWarning();

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
	public void dialogCombo(){
		new AlertDialog.Builder(this).setTitle("手动查询")
		.setMessage("手动查询事件")
		.setPositiveButton("确定", null)
		.show();  

	}
	public void dialogMonthWarning(){
		new AlertDialog.Builder(this).setTitle("月预警")
		.setMessage("月预警事件")
		.setPositiveButton("确定", null)
		.show();  

	}
	public void dialogDayWarning(){
		new AlertDialog.Builder(this).setTitle("日预警")
		.setMessage("日预警事件")
		.setPositiveButton("确定", null)
		.show();  

	}
	public void dialogWarningAct(){
		new AlertDialog.Builder(this).setTitle("预警动作")
		.setMessage("预警动作")
		.setPositiveButton("确定", null)
		.show();  

	}

	public boolean  onTouchEvent(MotionEvent event) {

//		float x = event.getX();
//		float y = event.getY(); 
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			//触摸屏幕时刻
			ed1.clearFocus();
			ed2.clearFocus();
//			Toast.makeText(getParent(),"x="+x+" y="+y, Toast.LENGTH_LONG).show();
			break;
			//触摸并移动时刻
		case MotionEvent.ACTION_MOVE:

			break;
			//终止触摸时刻
		case MotionEvent.ACTION_UP:
			break;
		}

		return true;

	}

}
