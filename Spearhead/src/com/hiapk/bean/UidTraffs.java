package com.hiapk.bean;

/**
 * Uid������Ϣ
 * 
 * @author df_wind
 * 
 */
public class UidTraffs {
	// uid��
	private int uid = -1;
	// �ϴ�����
	private long upload = 0;
	// ��������
	private long download = 0;
	// ���ڻ����timetap
	private long timetap = -1;

	/**
	 * ��ȡʱ�����Ĭ��Ϊ-1
	 * 
	 * @return
	 */
	public long getTimetap() {
		return timetap;
	}

	/**
	 * ����ʱ���
	 * 
	 * @param timetap
	 */
	public void setTimetap(long timetap) {
		this.timetap = timetap;
	}

	/**
	 * ��ȡuid�ţ�-1����Ч
	 * 
	 * @return
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * ����uid��
	 * 
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * ��ȡ�ϴ�����Ĭ��0
	 * 
	 * @return
	 */
	public long getUpload() {
		return upload;
	}

	/**
	 * �����ϴ�����
	 * 
	 * @param upload
	 */
	public void setUpload(long upload) {
		this.upload = upload;
	}

	/**
	 * ��ȡ��������Ĭ��0
	 * 
	 * @return
	 */
	public long getDownload() {
		return download;
	}

	/**
	 * �����ϴ�����
	 * 
	 * @param download
	 */
	public void setDownload(long download) {
		this.download = download;
	}

}
