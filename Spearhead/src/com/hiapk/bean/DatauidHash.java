package com.hiapk.bean;

/**
 * Small structure to hold an application info
 */
public class DatauidHash {
	private long uploadwifi;
	private long downloadwifi;
	private long uploadmobile;
	private long downloadmobile;

	private long uploadwifiToday;
	private long downloadwifiToday;
	private long uploadmobileToday;
	private long downloadmobileToday;

	private long uploadwifiWeek;
	private long downloadwifiWeek;
	private long uploadmobileWeek;
	private long downloadmobileWeek;

	/**
	 * ��ȡ�¶�����������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllDownload() {
		return downloadwifi + downloadmobile;
	}

	/**
	 * ��ȡ�¶��ϴ�������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllUpload() {
		return uploadwifi + uploadmobile;
	}

	/**
	 * ��ȡ�¶�������
	 * 
	 * @return
	 */
	public long getTotalTraff() {

		return uploadwifi + downloadwifi + uploadmobile + downloadmobile;
	}

	/**
	 * ��ȡ��������������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllDownloadToday() {
		return downloadwifiToday + downloadmobileToday;
	}

	/**
	 * ��ȡ�����ϴ�������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllUploadToday() {
		return uploadwifiToday + uploadmobileToday;
	}

	/**
	 * ��ȡ����������
	 * 
	 * @return
	 */
	public long getTotalTraffToday() {

		return uploadwifiToday + downloadwifiToday + uploadmobileToday
				+ downloadmobileToday;
	}

	/**
	 * ��ȡ������������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllDownloadWeek() {
		return downloadwifiWeek + downloadmobileWeek;
	}

	/**
	 * ��ȡ���ϴ�������wifi��mobile��
	 * 
	 * @return
	 */
	public long getAllUploadWeek() {
		return uploadwifiWeek + uploadmobileWeek;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public long getTotalTraffWeek() {

		return uploadwifiWeek + downloadwifiWeek + uploadmobileWeek
				+ downloadmobileWeek;
	}

	/**
	 * ��ȡ����wifi�ϴ�����
	 * 
	 * @return
	 */
	public long getUploadwifiToday() {
		return uploadwifiToday;
	}

	public void setUploadwifiToday(long uploadwifiToday) {
		this.uploadwifiToday = uploadwifiToday;
	}

	/**
	 * ��ȡ����wifi��������
	 * 
	 * @return
	 */
	public long getDownloadwifiToday() {
		return downloadwifiToday;
	}

	public void setDownloadwifiToday(long downloadwifiToday) {
		this.downloadwifiToday = downloadwifiToday;
	}

	/**
	 * ��ȡ����mobile�ϴ�����
	 * 
	 * @return
	 */
	public long getUploadmobileToday() {
		return uploadmobileToday;
	}

	public void setUploadmobileToday(long uploadmobileToday) {
		this.uploadmobileToday = uploadmobileToday;
	}

	/**
	 * ��ȡ����mobile��������
	 * 
	 * @return
	 */
	public long getDownloadmobileToday() {
		return downloadmobileToday;
	}

	public void setDownloadmobileToday(long downloadmobileToday) {
		this.downloadmobileToday = downloadmobileToday;
	}

	/**
	 * ��ȡ��wifi�ϴ�����
	 * 
	 * @return
	 */
	public long getUploadwifiWeek() {
		return uploadwifiWeek;
	}

	public void setUploadwifiWeek(long uploadwifiWeek) {
		this.uploadwifiWeek = uploadwifiWeek;
	}

	/**
	 * ��ȡ��wifi��������
	 * 
	 * @return
	 */
	public long getDownloadwifiWeek() {
		return downloadwifiWeek;
	}

	public void setDownloadwifiWeek(long downloadwifiWeek) {
		this.downloadwifiWeek = downloadwifiWeek;
	}

	/**
	 * ��ȡ��mobile�ϴ�����
	 * 
	 * @return
	 */
	public long getUploadmobileWeek() {
		return uploadmobileWeek;
	}

	public void setUploadmobileWeek(long uploadmobileWeek) {
		this.uploadmobileWeek = uploadmobileWeek;
	}

	/**
	 * ��ȡ��mobile��������
	 * 
	 * @return
	 */
	public long getDownloadmobileWeek() {
		return downloadmobileWeek;
	}

	public void setDownloadmobileWeek(long downloadmobileWeek) {
		this.downloadmobileWeek = downloadmobileWeek;
	}

	/**
	 * ��ȡ�¶�wifi�ϴ�����
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
	 * ��ȡ�¶�wifi��������
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
	 * ��ȡ�¶��ƶ��ϴ�����
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
	 * ��ȡ�¶��ƶ���������
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
