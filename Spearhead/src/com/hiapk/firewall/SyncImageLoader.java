package com.hiapk.firewall;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import android.R.integer;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SyncImageLoader {

	private Object lock = new Object();
	private boolean mAllowLoad = true;
	private boolean firstLoad = true;
	private int mStartLoadLimit = 0;
	private int mStopLoadLimit = 0;
	final Handler handler = new Handler();
	final HashMap<Integer, SoftReference<Drawable>> imageCache =
		new HashMap<Integer, SoftReference<Drawable>>();
	final HashMap<Integer,Drawable> imageCache2 =
		new HashMap<Integer,Drawable>();

	
	public interface OnImageLoadListener {  
        public void onImageLoad(Integer t, Drawable drawable, ImageView view,int uid);  
        public void onError(Integer t);  
    }  
	
	public void setLoadLimit(int startLoadLimit,int stopLoadLimit){
		if(startLoadLimit > stopLoadLimit){
			return;
		}
		mStartLoadLimit = startLoadLimit;
		mStopLoadLimit = stopLoadLimit;
	}
	public void restore(){
		mAllowLoad = true;
		firstLoad = true;
	}
	public void lock(){
		mAllowLoad = false;
		firstLoad = false;
	}
	
	public void unlock(){
		mAllowLoad = true;
		synchronized(lock){
			lock.notifyAll();
		}
	}
	
	public void loadImage(Integer position,PackageInfo pkgInfo ,final Context mContext
			,OnImageLoadListener listener , final ImageView view ,final int uid){
		final PackageInfo pInfo = pkgInfo;
		final OnImageLoadListener mListener = listener; 
		final Integer mPosition = position;
		new Thread(new Runnable() {  
            @Override   
            public void run() {  
                if(!mAllowLoad){  
                    synchronized (lock) {  
                        try {  
                            lock.wait();  
                        } catch (InterruptedException e) {  
                            // TODO Auto-generated catch block  
                            e.printStackTrace();  
                        }  
                    }  
                }  
                   
                if(mAllowLoad && firstLoad){  
                    loadImage(pInfo, mPosition,mContext,mListener,view,uid); 
                }  
                  
                if(mAllowLoad && mPosition <= mStopLoadLimit && mPosition >= mStartLoadLimit){  
                    loadImage(pInfo,mPosition,mContext, mListener ,view,uid);  
                }  
            }  
        }).start();  
		  
	} 
	
	 private void loadImage(final PackageInfo pkgInfo,final Integer mPosition,Context mContext,
			 final OnImageLoadListener mListener , final ImageView view ,final int uid){  
		 if(imageCache.containsKey(uid)){
	        	final Drawable d = imageCache2.get(uid);
	        	  if (d != null) {    
		                handler.post(new Runnable() {  
		                    @Override  
		                    public void run() {  
		                        if(mAllowLoad){  
		                            mListener.onImageLoad(mPosition, d ,view,uid); 
		                        }  
		                    }  
		                });  
		                return;    
		            }   
	        }else{
	        	if(pkgInfo != null){
	        		if(pkgInfo.applicationInfo.uid == uid){
				        final Drawable d = pkgInfo.applicationInfo.loadIcon(mContext.getPackageManager()); 
				        imageCache2.put(uid, d);
				        if (d != null) {    
			                handler.post(new Runnable() {  
			                    @Override  
			                    public void run() {  
			                        if(mAllowLoad){  
			                            mListener.onImageLoad(mPosition, d ,view,uid);  
			                        }  
			                    }  
			                });  
			                return;    
			            }}else{} 
	        	}else{
	        		
	        	}
	        	
		        }
	    }  

	
}
