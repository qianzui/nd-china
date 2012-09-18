package com.hiapk.bean;

/**
 * Small structure to hold an application info
 */
public class DatauidHash {
	private long uploadwifi;
	private long downloadwifi;
	private long uploadmobile;
	private long downloadmobile;

	/**
	 * ��ȡ��������������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllDownload() {
		return downloadwifi + downloadmobile;
	}

	/**
	 * ��ȡ�ϴ�����������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllUpload() {
		return uploadwifi + uploadmobile;
	}

	/**
	 * ��ȡ������
	 * 
	 * @return
	 */
	public long getTotalTraff() {

		return uploadwifi + downloadwifi + uploadmobile + downloadmobile;
	}

	/**
	 * ��ȡwifi�ϴ�����
	 * 
	 * @return
	 */
	public long getUploadwifi() {
		return uploadwifi;
	}

	public void setUploadwifi(long uploadwifi) {
		this.uploadwifi = uploadwifi;
	}

	/**
	 * ��ȡwifi��������
	 * 
	 * @return
	 */
	public long getDownloadwifi() {
		return downloadwifi;
	}

	public void setDownloadwifi(long downloadwifi) {
		this.downloadwifi = downloadwifi;
	}

	/**
	 * ��ȡ�ƶ��ϴ�����
	 * 
	 * @return
	 */
	public long getUploadmobile() {
		return uploadmobile;
	}

	public void setUploadmobile(long uploadmobile) {
		this.uploadmobile = uploadmobile;
	}

	/**
	 * ��ȡ�ƶ���������
	 * 
	 * @return
	 */
	public long getDownloadmobile() {
		return downloadmobile;
	}

	public void setDownloadmobile(long downloadmobile) {
		this.downloadmobile = downloadmobile;
	}

}
