package com.hiapk.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar {

	String text;
    Paint mPaint;
    int textsize=50;
    public MyProgressBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        System.out.println("1");
        initText(); 
    }
     
    public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        System.out.println("2");
        initText();
    }
 
 
    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        System.out.println("3");
        initText();
    }
     
    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText(progress);
        super.setProgress(progress);
         
    }
    	@Override
    	public Drawable getProgressDrawable() {
    		// TODO Auto-generated method stub
    		
    		return super.getProgressDrawable();
    	}
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();  
        int y = (getHeight() / 2) - rect.centerY();  
        canvas.drawText(this.text, x, y, this.mPaint);  
    }
     
    public void setTextsize	(int textsize){
    	this.textsize=textsize;
    }
    //��ʼ��������
    private void initText(){
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize(textsize);
    }
     
    private void setText(){
        setText(this.getProgress());
    }
     
    //������������
    private void setText(int progress){
        int i = (progress * 100)/this.getMax();
        this.text = String.valueOf(i) + "%";
    }

}