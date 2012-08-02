/*
 * Copyright (C) 2011 Patrik Åkerfeldt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hiapk.viewflow;

import java.util.ArrayList;
import java.util.LinkedList;

import com.hiapk.spearhead.Main;
import com.hiapk.spearhead.R;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AndroidVersionAdapter extends BaseAdapter implements TitleProvider {

	private LayoutInflater mInflater;
	LinkedList<View> views;
	LinkedList<Bitmap> bitmap;
	ArrayList<Intent> intents;
	private static final String[] versions = { "1.5", "1.6" };
	private static final String[] names = { "Cupcake", "Donut" };
	private ActivityGroup group;
	private Context context;

	private static final int[] ids = { R.drawable.cupcake, R.drawable.donut,
			R.drawable.eclair, R.drawable.froyo
	// ,R.drawable.gingerbread, R.drawable.honeycomb, R.drawable.icecream
	};

	// public AndroidVersionAdapter(ArrayList<View> views) {
	// // mInflater = (LayoutInflater) context
	// // .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// this.views = views;
	// }
	public AndroidVersionAdapter(LinkedList<View> views, Context context) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// this.group = group;
		// this.intents = intents;
		this.views = views;
		this.context = context;
	}

	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public Object getItem(int position) {
		// View convertView = null;
		// convertView = mInflater.inflate(R.layout.loading_layout, null);
		// switch (position) {
		// case 1:
		// convertView = views.get(0);
		// break;
		// case 2:
		// convertView = views.get(1);
		// break;
		// }
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// if (position==3) {
		// convertView =
		// mInflater.inflate(R.layout.alert_dialog_text_entry_monthset, null);
		// return convertView;
		// // ((TextView) convertView.findViewById(R.id.textLabel))
		// // .setText(versions[position]);
		// }
		// if (convertView == null) {
		// convertView = mInflater.inflate(R.layout.flow_item, null);
		// }
		// ((TextView) convertView.findViewById(R.id.mainTitle))
		// .setText(versions[position]);
		// // ((TextView) convertView.findViewById(R.id.textLabel))
		// // .setText(versions[position]);
		// View view = views.get(position);
		// notifyDataSetInvalidated();
		//
		convertView = mInflater.inflate(R.layout.loading_blank, null);
		switch (position) {
		case 1:
			convertView = views.get(0);
			break;
		case 2:
			convertView = views.get(1);
			break;
		}
		return convertView;

		// PanelAdapter panview = new PanelAdapter(context,
		// bitmap.get(position));
		// // convertView = views.get(position);
		//
		// if (convertView == null) {
		// convertView = mInflater.inflate(R.layout.image_item, null);
		// }
		// ((ImageView) convertView.findViewById(R.id.imgView))
		// .setImageBitmap(bitmap.get(position));
		// return convertView;

		// if (convertView == null) {
		// convertView = mInflater.inflate(R.layout.image_item, null);
		// }
		// ((ImageView)
		// convertView.findViewById(R.id.imgView)).setImageResource(ids[position]);
		// return convertView;
	}

	class PanelAdapter extends View {
		Bitmap bmp;

		public PanelAdapter(Context context, Bitmap bmp) {
			super(context);
			this.bmp = bmp;
		}

		// View view = mAdapter.getView(position, convertView, this);
		// Bitmap bitmap=view.getDrawingCache(true);
		// BitmapDrawable bitdraw=new BitmapDrawable(bitmap);
		public void onDraw(Canvas canvas) {
			// Bitmap bmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.fire_help);
			// if (position == 0) {
			// bmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.fire_help);
			// }
			// if (position == 1) {
			// bmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.fire_help);
			// }
			// if (position == 2) {
			// View view = mAdapter.getView(2, null, adapterview);
			// bmp = view.getDrawingCache();
			//
			// }
			// if (position == 3) {
			// bmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.fire_help);
			// }
			// if (position == 4) {
			// bmp = BitmapFactory.decodeResource(getResources(),
			// R.drawable.fire_help);
			// }
			// View view = mAdapter.getView(position, null, null);
			// bmp = view.getDrawingCache();
			canvas.drawColor(Color.GRAY);
			if (bmp == null) {
				// bmp = BitmapFactory.decodeResource(getResources(),
				// R.drawable.fire_help);
				// canvas.drawBitmap(bmp, 0, 0, null);
			} else {
				canvas.drawBitmap(bmp, 0, 0, null);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.taptwo.android.widget.TitleProvider#getTitle(int)
	 */
	@Override
	public String getTitle(int position) {
		return names[position];
	}

}
