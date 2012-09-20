package com.hiapk.ui.custom;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.hiapk.spearhead.R;
import com.hiapk.util.SharedPrefrenceDataRegulate;

/**
 * 初始化手机卡归属地页面
 * 
 * @author df_wind
 * 
 */
public class CustomSPPhoneSetBeen {
	Context context;
	SharedPrefrenceDataRegulate sharedData;
	Resources res;

	public CustomSPPhoneSetBeen(Context context) {
		this.context = context;
		sharedData = new SharedPrefrenceDataRegulate(context);
		res = context.getResources();
	}

	/**
	 * phoneset页面显示对话框-2级列表，比如city以及品牌
	 * 
	 * @param content
	 *            设置显示的内容
	 * @param beforepos
	 *            初始被选中的列表项
	 * @param title
	 *            弹出对话框的prompt
	 * @param type
	 *            用来区别是city还是brand
	 * @param secend
	 *            city以及brand按钮显示的文本
	 */
	public void dialogPhoneSetSecond(String[] content, int beforepos,
			String title, final String type, final Button secend) {
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_listview_spinner, null);
		ListView lv_fresh = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		CustomSPAdapter adapter = new CustomSPAdapter(context, content,
				beforepos);
		lv_fresh.setAdapter(adapter);
		lv_fresh.setDividerHeight(0);
		lv_fresh.setSelection(beforepos);

		final CustomDialog phoneset;
		phoneset = new CustomDialog.Builder(context).setTitle(title)
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		phoneset.show();
		// 设置cancel的监听
		Button btn_cancel = (Button) phoneset.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30代表1-31
				phoneset.dismiss();
			}
		});

		// 设置中间按钮的监听
		lv_fresh.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (type == "city") {
					sharedData.setCurrentCityID(arg2);
					secend.setText((CharSequence) arg0.getItemAtPosition(arg2));
				}
				if (type == "brand") {
					sharedData.setCurrentPinpaiID(arg2);
					secend.setText((CharSequence) arg0.getItemAtPosition(arg2));
				}
				phoneset.dismiss();
			}

		});
	}

	/**
	 * phoneset页面显示对话框-1级列表，比如省份以及运营商
	 * 
	 * @param content
	 *            设置显示的内容
	 * @param beforepos
	 *            初始被选中的列表项
	 * @param title
	 *            弹出对话框的prompt
	 * @param type
	 *            用来区别是province还是运营商
	 * @param btn_first
	 *            province还是运营商显示的文本
	 * @param btn_second
	 *            city以及brand按钮显示的文本
	 */
	public void dialogPhoneSetProvinceAndOperator(String[] content,
			int beforepos, String title, final String type,
			final Button btn_first, final Button btn_second) {
		// 初始化窗体
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(
				R.layout.custom_listview_spinner, null);
		ListView lv_fresh = (ListView) textEntryView
				.findViewById(R.id.lv_custom_spinner);
		CustomSPAdapter adapter = new CustomSPAdapter(context, content,
				beforepos);
		lv_fresh.setAdapter(adapter);
		lv_fresh.setDividerHeight(0);
		lv_fresh.setSelection(beforepos);

		final CustomDialog phoneset;
		phoneset = new CustomDialog.Builder(context).setTitle(title)
				.setContentView(textEntryView).setNegativeButton("确定", null)
				.create();
		phoneset.show();
		// 设置cancel的监听
		Button btn_cancel = (Button) phoneset.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30代表1-31
				phoneset.dismiss();
			}
		});

		// 设置中间按钮的监听
		lv_fresh.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				btn_first.setText((CharSequence) arg0.getItemAtPosition(arg2));
				phoneSetsharedDataOperate(type, arg2, btn_second);
				phoneset.dismiss();
			}

		});
	}

	private void phoneSetsharedDataOperate(String type, final int arg2,
			final Button btn_second) {
		// TODO Auto-generated method stub
		if (type == "province") {
			if (arg2 != sharedData.getCurrentProvinceID()) {
				sharedData.setCurrentCityID(0);
				sharedData.setCurrentProvinceID(arg2);
				int cityName = CustomSPDataSet.getSpinCity((int) arg2);
				String[] cityNames = res.getStringArray(cityName);
				btn_second.setText(cityNames[0]);
				btn_second.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						init_spin_city(arg2, btn_second);
					}
				});
			}

		}
		if (type == "operator") {
			if (arg2 != sharedData.getCurrentYunyinshangID()) {
				sharedData.setCurrentPinpaiID(0);
				sharedData.setCurrentYunyinshangID(arg2);
				int brandName = CustomSPDataSet.getSpinBrand((int) arg2);
				String[] brandNames = res.getStringArray(brandName);
				btn_second.setText(brandNames[0]);
				btn_second.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						init_spin_pingpai(arg2, btn_second);
					}
				});
			} else {

			}

		}
	}

	private void init_spin_pingpai(int operatorID, Button btn_second) {
		String[] content = res.getStringArray(CustomSPDataSet
				.getSpinBrand(operatorID));
		int beforepos = sharedData.getCurrentPinpaiID();
		dialogPhoneSetSecond(content, beforepos, "请选择品牌", "brand", btn_second);
	}

	private void init_spin_city(int provinceID, Button btn_second) {
		String[] content = res
				.getStringArray(CustomSPDataSet.getSpinCity(provinceID));
		int beforepos = sharedData.getCurrentCityID();
		dialogPhoneSetSecond(content, beforepos, "请选择城市", "city", btn_second);
	}

	// /**
	// * 显示日志
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// Log.d("CustomSPBeen", string);
	// }
}
