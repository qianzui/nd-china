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
	 * 获取月度下载流量（wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllDownload() {
		return downloadwifi + downloadmobile;
	}

	/**
	 * 获取月度上传流量（wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllUpload() {
		return uploadwifi + uploadmobile;
	}

	/**
	 * 获取月度总流量
	 * 
	 * @return
	 */
	public long getTotalTraff() {

		return uploadwifi + downloadwifi + uploadmobile + downloadmobile;
	}

	/**
	 * 获取单日下载流量（wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllDownloadToday() {
		return downloadwifiToday + downloadmobileToday;
	}

	/**
	 * 获取单日上传流量（wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllUploadToday() {
		return uploadwifiToday + uploadmobileToday;
	}

	/**
	 * 获取单日总流量
	 * 
	 * @return
	 */
	public long getTotalTraffToday() {

		return uploadwifiToday + downloadwifiToday + uploadmobileToday
				+ downloadmobileToday;
	}

	/**
	 * 获取周下载流量（wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllDownloadWeek() {
		return downloadwifiWeek + downloadmobileWeek;
	}

	/**
	 * 获取周上传流量（wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllUploadWeek() {
		return uploadwifiWeek + uploadmobileWeek;
	}

	/**
	 * 获取周总流量
	 * 
	 * @return
	 */
	public long getTotalTraffWeek() {

		return uploadwifiWeek + downloadwifiWeek + uploadmobileWeek
				+ downloadmobileWeek;
	}

	/**
	 * 获取单日wifi上传流量
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
	 * 获取单日wifi下载流量
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
	 * 获取单日mobile上传流量
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
	 * 获取单日mobile下载流量
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
	 * 获取周wifi上传流量
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
	 * 获取周wifi下载流量
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
	 * 获取周mobile上传流量
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
	 * 获取周mobile下载流量
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
	 * 获取月度wifi上传流量
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
	 * 获取月度wifi下载流量
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
	 * 获取月度移动上传流量
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
	 * 获取月度移动下载流量
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
