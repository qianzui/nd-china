package com.hiapk.bean;

/**
 * 总网络流量，包括移动与总共。
 * 
 * @author df_wind
 * 
 */
public class TotalTraffs {
	// 移动上传流量
	private long mobileUpload = 0;
	// 移动下载流量
	private long mobileDownload = 0;
	// wifi上传流量
	private long wifiUpload = 0;
	// wifi下载流量
	private long wifiDownload = 0;

	/**
	 * 获取移动上传流量
	 * 
	 * @return
	 */
	public long getMobileUpload() {
		return mobileUpload;
	}

	/**
	 * 设置移动上传流量
	 * 
	 * @param mobileUpload
	 */
	public void setMobileUpload(long mobileUpload) {
		this.mobileUpload = mobileUpload;
	}

	/**
	 * 获取移动下载流量
	 * 
	 * @return
	 */
	public long getMobileDownload() {
		return mobileDownload;
	}

	/**
	 * 设置移动下载流量
	 * 
	 * @param mobileUpload
	 */
	public void setMobileDownload(long mobileDownload) {
		this.mobileDownload = mobileDownload;
	}

	/**
	 * 获取wifi上传流量
	 * 
	 * @return
	 */
	public long getWifiUpload() {
		return wifiUpload;
	}

	/**
	 * 设置wifi上传流量
	 * 
	 * @param mobileUpload
	 */
	public void setWifiUpload(long wifiUpload) {
		this.wifiUpload = wifiUpload;
	}

	/**
	 * 获取wifi下载流量
	 * 
	 * @return
	 */
	public long getWifiDownload() {
		return wifiDownload;
	}

	/**
	 * 设置wifi下载流量
	 * 
	 * @param mobileUpload
	 */
	public void setWifiDownload(long wifiDownload) {
		this.wifiDownload = wifiDownload;
	}

}
