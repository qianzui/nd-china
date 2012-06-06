package com.hiapk.spearhead;

import com.hiapk.prefrencesetting.SharedPrefrenceData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Help extends Activity implements OnGestureListener{
	private ViewFlipper flipper;
	private GestureDetector detector;
	Context ct = this;
	SharedPrefrenceData sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		sp = new SharedPrefrenceData(ct);
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		if(!sp.isFirstBoot()){
			Intent i = new Intent();
			i.setClass(ct, Splash.class);
			startActivity(i);
			Log.v("+++++++++++", "跳过");
			this.finish();
		}
		Log.v("+++++++++++",sp.isFirstBoot()+"");
		detector =  new GestureDetector(this);
		flipper =  (ViewFlipper) findViewById(R.id.viewFlipper);
		flipper.addView(addImageViewOne());
		flipper.addView(addImageViewTwo());  
		flipper.addView(addImageViewThree()); 

		



	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		
		
		if(e1.getX() - e2.getX() > 120){//如果是从右向左滑动  
			//注册flipper的进出效果
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
			this.flipper.showNext();
			return true;
		}
		else if (e1.getX() - e2.getX() < -120) {//如果是从左向右滑动  
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));  
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));  
			this.flipper.showPrevious();  
			return true;  
		} 
		Log.v("+++++++", flipper.getDisplayedChild()+"");
		return false;  
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		float x;
		float y;
		x =  e.getX();
		y =  e.getY();
		Display dp = getWindowManager().getDefaultDisplay();
		
		
		
		Log.v("+++++++", x+"--"+y+"实际"+dp.getWidth()+"-"+dp.getHeight());
		
		if(dp.getWidth()-x<100 && dp.getHeight()-y<100 && flipper.getDisplayedChild()!=2){			
				this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
				this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
				this.flipper.showNext();			
		}
		else if(dp.getWidth()-x>(dp.getWidth()-100) && dp.getHeight()-y<100 && flipper.getDisplayedChild()!=0){
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));  
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));  
			this.flipper.showPrevious();  
		}
		else if(flipper.getDisplayedChild()==2){
			Intent i = new Intent();
			i.setClass(ct, Splash.class);
			Log.v("+++++++","onSingleTapUp");
			startActivity(i);
			this.finish();
		}	

		
		

		return false;
	}

	private View addImageViewOne() {  
		TextView tv = new TextView(this);  
		tv.setGravity(1);  
		tv.setBackgroundResource(R.drawable.help_1);
		return tv;  
	}  
	private View addImageViewTwo() {  
		ImageView iv = new ImageView(this);			
		iv.setBackgroundResource(R.drawable.help_2);

		return iv;  
	}  
	private View addImageViewThree() {  
		TextView tv = new TextView(this);  
		tv.setGravity(1);  
		tv.setBackgroundResource(R.drawable.help_3);        
		return tv;  
	}  
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		return this.detector.onTouchEvent(event);  
	}
}
