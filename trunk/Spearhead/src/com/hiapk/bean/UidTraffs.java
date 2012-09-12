package com.hiapk.bean;

/**
 * Uid流量信息
 * 
 * @author df_wind
 * 
 */
public class UidTraffs {
	// uid号
	private int uid = -1;
	// 上传流量
	private long upload = 0;
	// 下载流量
	private long download = 0;
	// 用于缓存的timetap
	private long timetap = -1;

	/**
	 * 获取时间戳，默认为-1
	 * 
	 * @return
	 */
	public long getTimetap() {
		return timetap;
	}

	/**
	 * 设置时间戳
	 * 
	 * @param timetap
	 */
	public void setTimetap(long timetap) {
		this.timetap = timetap;
	}

	/**
	 * 获取uid号，-1则无效
	 * 
	 * @return
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * 设置uid号
	 * 
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * 获取上传流量默认0
	 * 
	 * @return
	 */
	public long getUpload() {
		return upload;
	}

	/**
	 * 设置上传流量
	 * 
	 * @param upload
	 */
	public void setUpload(long upload) {
		this.upload = upload;
	}

	/**
	 * 获取下载流量默认0
	 * 
	 * @return
	 */
	public long getDownload() {
		return download;
	}

	/**
	 * 设置上传流量
	 * 
	 * @param download
	 */
	public void setDownload(long download) {
		this.download = download;
	}

}
