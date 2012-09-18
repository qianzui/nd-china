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
	 * 获取下载流量（部分wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllDownload() {
		return downloadwifi + downloadmobile;
	}

	/**
	 * 获取上传流量（部分wifi与mobile）
	 * 
	 * @return
	 */
	public long getAllUpload() {
		return uploadwifi + uploadmobile;
	}

	/**
	 * 获取总流量
	 * 
	 * @return
	 */
	public long getTotalTraff() {

		return uploadwifi + downloadwifi + uploadmobile + downloadmobile;
	}

	/**
	 * 获取wifi上传流量
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
	 * 获取wifi下载流量
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
	 * 获取移动上传流量
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
	 * 获取移动下载流量
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
