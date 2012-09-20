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
 * ��ʼ���ֻ���������ҳ��
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
	 * phonesetҳ����ʾ�Ի���-2���б�����city�Լ�Ʒ��
	 * 
	 * @param content
	 *            ������ʾ������
	 * @param beforepos
	 *            ��ʼ��ѡ�е��б���
	 * @param title
	 *            �����Ի����prompt
	 * @param type
	 *            ����������city����brand
	 * @param secend
	 *            city�Լ�brand��ť��ʾ���ı�
	 */
	public void dialogPhoneSetSecond(String[] content, int beforepos,
			String title, final String type, final Button secend) {
		// ��ʼ������
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
				.setContentView(textEntryView).setNegativeButton("ȷ��", null)
				.create();
		phoneset.show();
		// ����cancel�ļ���
		Button btn_cancel = (Button) phoneset.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				phoneset.dismiss();
			}
		});

		// �����м䰴ť�ļ���
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
	 * phonesetҳ����ʾ�Ի���-1���б�����ʡ���Լ���Ӫ��
	 * 
	 * @param content
	 *            ������ʾ������
	 * @param beforepos
	 *            ��ʼ��ѡ�е��б���
	 * @param title
	 *            �����Ի����prompt
	 * @param type
	 *            ����������province������Ӫ��
	 * @param btn_first
	 *            province������Ӫ����ʾ���ı�
	 * @param btn_second
	 *            city�Լ�brand��ť��ʾ���ı�
	 */
	public void dialogPhoneSetProvinceAndOperator(String[] content,
			int beforepos, String title, final String type,
			final Button btn_first, final Button btn_second) {
		// ��ʼ������
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
				.setContentView(textEntryView).setNegativeButton("ȷ��", null)
				.create();
		phoneset.show();
		// ����cancel�ļ���
		Button btn_cancel = (Button) phoneset.findViewById(R.id.negativeButton);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 0-30����1-31
				phoneset.dismiss();
			}
		});

		// �����м䰴ť�ļ���
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
		dialogPhoneSetSecond(content, beforepos, "��ѡ��Ʒ��", "brand", btn_second);
	}

	private void init_spin_city(int provinceID, Button btn_second) {
		String[] content = res
				.getStringArray(CustomSPDataSet.getSpinCity(provinceID));
		int beforepos = sharedData.getCurrentCityID();
		dialogPhoneSetSecond(content, beforepos, "��ѡ�����", "city", btn_second);
	}

	// /**
	// * ��ʾ��־
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// Log.d("CustomSPBeen", string);
	// }
}
