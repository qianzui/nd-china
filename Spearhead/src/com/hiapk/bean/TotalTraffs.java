package com.hiapk.bean;

/**
 * �����������������ƶ����ܹ���
 * 
 * @author df_wind
 * 
 */
public class TotalTraffs {
	// �ƶ��ϴ�����
	private long mobileUpload = 0;
	// �ƶ���������
	private long mobileDownload = 0;
	// wifi�ϴ�����
	private long wifiUpload = 0;
	// wifi��������
	private long wifiDownload = 0;

	/**
	 * ��ȡ�ƶ��ϴ�����
	 * 
	 * @return
	 */
	public long getMobileUpload() {
		return mobileUpload;
	}

	/**
	 * �����ƶ��ϴ�����
	 * 
	 * @param mobileUpload
	 */
	public void setMobileUpload(long mobileUpload) {
		this.mobileUpload = mobileUpload;
	}

	/**
	 * ��ȡ�ƶ���������
	 * 
	 * @return
	 */
	public long getMobileDownload() {
		return mobileDownload;
	}

	/**
	 * �����ƶ���������
	 * 
	 * @param mobileUpload
	 */
	public void setMobileDownload(long mobileDownload) {
		this.mobileDownload = mobileDownload;
	}

	/**
	 * ��ȡwifi�ϴ�����
	 * 
	 * @return
	 */
	public long getWifiUpload() {
		return wifiUpload;
	}

	/**
	 * ����wifi�ϴ�����
	 * 
	 * @param mobileUpload
	 */
	public void setWifiUpload(long wifiUpload) {
		this.wifiUpload = wifiUpload;
	}

	/**
	 * ��ȡwifi��������
	 * 
	 * @return
	 */
	public long getWifiDownload() {
		return wifiDownload;
	}

	/**
	 * ����wifi��������
	 * 
	 * @param mobileUpload
	 */
	public void setWifiDownload(long wifiDownload) {
		this.wifiDownload = wifiDownload;
	}

}
