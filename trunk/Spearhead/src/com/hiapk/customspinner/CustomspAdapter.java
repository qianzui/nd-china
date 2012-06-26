package com.hiapk.customspinner;

import java.util.ArrayList;

import com.hiapk.spearhead.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomspAdapter extends BaseAdapter {
	Context context;
	ArrayList<String> datalist = new ArrayList<String>();
	int beforepos;

	public CustomspAdapter(Context context, String[] inputData, int beforepos) {
		this.context = context;
		this.beforepos = beforepos;
		datalist.clear();
		for (int i = 0; i < inputData.length; i++) {
			datalist.add(inputData[i]);
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datalist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		String showtext = datalist.get(position);
		if (v == null) {
			// LayoutInflater factory = LayoutInflater.from(context);
			// v = factory.inflate(R.layout.listview_custom_spinner, null);
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listview_custom_spinner_text, null);
			// v.setClickable(true);
		}
		TextView appupload = (TextView) v.findViewById(R.id.listview_btn);
		appupload.setText(showtext);
		Resources res = context.getResources();
		Drawable radio_on = res.getDrawable(R.drawable.radiobtn_on);
		Drawable radio_off = res.getDrawable(R.drawable.radiobtn_off);
		appupload.setCompoundDrawablesWithIntrinsicBounds(null, null,
				radio_off, null);
		if (beforepos == position) {
			appupload.setCompoundDrawablesWithIntrinsicBounds(null, null,
					radio_on, null);
		}
		return v;
	}

}
