package com.hiapk.firewall;

import com.hiapk.spearhead.R;
import android.view.View;

import com.hiapk.ui.custom.CustomPopupWindow;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class FireWallItemMenu extends CustomPopupWindow {

	private final View root;
	private final LayoutInflater inflater;
	private final Context context;
	private final LinearLayout fire_menu;
	public FireWallItemMenu(View anchor) {
		super(anchor);
		
		context		= anchor.getContext();
		inflater 	= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		root		= (ViewGroup) inflater.inflate(R.layout.fire_item_menu, null);
		fire_menu   = (LinearLayout) root.findViewById(R.id.fire_menu);
		setContentView(root);
	}


	public View  getView(){
		if(root != null){
			return root;
		}else{
			return null;
		}
	}
	
	/**
	 * Show popup window
	 */
	public void show () {
		preShow();
		int[] location = new int[2];
		anchor.getLocationOnScreen(location);
		Rect anchorRect 	= new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] 
		                	+ anchor.getHeight());
		root.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		root.measure(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		int rootWidth 		= root.getMeasuredWidth();
//		int rootHeight 		= root.getMeasuredHeight();
		int screenWidth 	= windowManager.getDefaultDisplay().getWidth();
		int screenHeight 	= windowManager.getDefaultDisplay().getHeight();
		int xPos 			= (screenWidth - rootWidth) / 2;
		int yPos	 		= anchorRect.top - 80;
		boolean onTop		= true;
		if ( screenHeight/2 > anchor.getBottom()) {
			yPos 	= anchorRect.bottom;
			onTop	= false;
		}
		showArrow(((onTop) ? R.drawable.fire_menu_up: R.drawable.fire_menu_down));
		window.showAtLocation(this.anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}
	private void showArrow(int whichArrow) {
		fire_menu.setBackgroundResource(whichArrow);
	}

}
