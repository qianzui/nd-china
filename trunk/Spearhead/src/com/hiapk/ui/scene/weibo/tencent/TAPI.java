package com.hiapk.ui.scene.weibo.tencent;

import java.io.File;

import org.apache.http.message.BasicNameValuePair;

 
/**
 * 寰崥鐩稿叧API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3">鑵捐寰崥寮�鏀惧钩鍙颁笂寰崥鐩稿叧鐨凙PI鏂囨。<a>
 */

public class TAPI extends BasicAPI{
    
    private String tShowUrl=apiBaseUrl+"/t/show";
    private String tAddUrl=apiBaseUrl+"/t/add";
    private String tAddPicUrl=apiBaseUrl+"/t/add_pic";
    private String tAddPicUrlUrl=apiBaseUrl+"/t/add_pic_url";
    private String tAddVideoUrl=apiBaseUrl+"/t/add_video";
    private String tCommentUrl=apiBaseUrl+"/t/comment";
    private String tDelUrl=apiBaseUrl+"/t/del";
    private String tReAddUrl=apiBaseUrl+"/t/re_add";
    private String tReCountUrl=apiBaseUrl+"/t/re_count";
    private String tReListUrl=apiBaseUrl+"/t/re_list";
    private String tReplyUrl=apiBaseUrl+"/t/reply";
    /**
     * 浣跨敤瀹屾瘯鍚庯紝璇疯皟鐢� shutdownConnection() 鍏抽棴鑷姩鐢熸垚鐨勮繛鎺ョ鐞嗗櫒
     * @param OAuthVersion 鏍规嵁OAuthVersion锛岄厤缃�氱敤璇锋眰鍙傛暟
     */
    public TAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion 鏍规嵁OAuthVersion锛岄厤缃�氱敤璇锋眰鍙傛暟
     * @param qHttpClient 浣跨敤宸叉湁鐨勮繛鎺ョ鐞嗗櫒
     */
    public TAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * 鑾峰彇涓�鏉″井鍗氭暟鎹�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param id 寰崥id
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%E6%95%B0%E6%8D%AE">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String show(OAuth oAuth, String format, String id) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("id", id));
		
		return requestAPI.getResource(tShowUrl, paramsList, oAuth);
	}

	/**
	 * 鍙戣〃涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String add(OAuth oAuth, String format, String content,
			String clientip) throws Exception {
		return this.add(oAuth, format, content, clientip, "", "", "");
	}

	/**
	 * 鍙戣〃涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param jing 缁忓害锛堝彲浠ュ～绌猴級
	 * @param wei 绾害锛堝彲浠ュ～绌猴級
	 * @param syncflag  寰崥鍚屾鍒扮┖闂村垎浜爣璁帮紙鍙�夛紝0-鍚屾锛�涓嶅悓姝ワ紝榛樿涓��  
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String add(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String syncflag) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
        paramsList.add(new BasicNameValuePair("syncflag", syncflag));
		
		return requestAPI.postContent(tAddUrl, paramsList, oAuth);
	}

	/**
	 * 鍒犻櫎涓�鏉″井鍗氭暟鎹�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param id 寰崥id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%88%A0%E9%99%A4%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String del(OAuth oAuth, String format, String id) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("id", id));
		
		return requestAPI.postContent(tDelUrl, paramsList, oAuth);
	}

	/**
	 * 杞挱涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param reid 杞挱鐖剁粨鐐瑰井鍗歩d
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String reAdd(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.reAdd(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * 杞挱涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param jing 缁忓害锛堝彲浠ュ～绌猴級
	 * @param wei 绾害锛堝彲浠ュ～绌猴級
	 * @param reid 杞挱鐖剁粨鐐瑰井鍗歩d
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String reAdd(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String reid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("reid", reid));
		
		return requestAPI.postContent(tReAddUrl, paramsList, oAuth);
	}

	/**
	 * 鐐硅瘎涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param reid 鐐硅瘎鐖剁粨鐐瑰井鍗歩d
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%82%B9%E8%AF%84%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String comment(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.comment(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * 鐐硅瘎涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param jing 缁忓害锛堝彲浠ュ～绌猴級
	 * @param wei 绾害锛堝彲浠ュ～绌猴級
	 * @param reid 鐐硅瘎鐖剁粨鐐瑰井鍗歩d
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%82%B9%E8%AF%84%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String comment(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String reid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("reid", reid));
		
		return requestAPI.postContent(tCommentUrl, paramsList,
				oAuth);
	}
	
	/**
	 * 鍥炲涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param reid 鍥炲鐨勭埗缁撶偣寰崥id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%9B%9E%E5%A4%8D%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%EF%BC%88%E5%8D%B3%E5%AF%B9%E8%AF%9D%EF%BC%89">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String reply(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.reply(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * 鍥炲涓�鏉″井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param jing 缁忓害锛堝彲浠ュ～绌猴級
	 * @param wei 绾害锛堝彲浠ュ～绌猴級
	 * @param reid 鍥炲鐨勭埗缁撶偣寰崥id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%9B%9E%E5%A4%8D%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%EF%BC%88%E5%8D%B3%E5%AF%B9%E8%AF%9D%EF%BC%89">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String reply(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String reid)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("reid", reid));
		
		return requestAPI.postContent(tReplyUrl, paramsList,
				oAuth);
	}

	/**
	 * 鍙戣〃涓�鏉″甫鍥剧墖鐨勫井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param picpath 鍙互鏄湰鍦板浘鐗囪矾寰� 鎴� 缃戠粶鍦板潃
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�鏈湴鍥剧墖</a>
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%94%A8%E5%9B%BE%E7%89%87URL%E5%8F%91%E8%A1%A8%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�缃戠粶鍥剧墖</a>
	 */
	public String addPic(OAuth oAuth, String format, String content,
			String clientip, String picpath) throws Exception {
		return this.addPic(oAuth, format, content, clientip, "", "", picpath, "");
	}

	/**
	 * 鍙戣〃涓�鏉″甫鍥剧墖鐨勫井鍗�
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴)
	 * @param jing 缁忓害锛堝彲浠ュ～绌猴級
	 * @param wei 绾害锛堝彲浠ュ～绌猴級
	 * @param picpath 鍙互鏄湰鍦板浘鐗囪矾寰� 鎴� 缃戠粶鍦板潃
	 * @param syncflag  寰崥鍚屾鍒扮┖闂村垎浜爣璁帮紙鍙�夛紝0-鍚屾锛�涓嶅悓姝ワ紝榛樿涓��  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�鏈湴鍥剧墖</a>
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%94%A8%E5%9B%BE%E7%89%87URL%E5%8F%91%E8%A1%A8%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�缃戠粶鍥剧墖</a>
	 */
	public String addPic(OAuth oAuth, String format, String content,
			String clientip, String jing, String wei, String picpath,String syncflag)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
        paramsList.add(new BasicNameValuePair("syncflag", syncflag));
		
		if(new File(picpath).exists()){
			//
			QArrayList pic = new QArrayList();
			pic.add(new BasicNameValuePair("pic", picpath));
			return requestAPI.postFile(tAddPicUrl, paramsList, pic,
					oAuth);
		}else{
			paramsList.add(new BasicNameValuePair("pic_url", picpath));
			return requestAPI.postContent(tAddPicUrlUrl, paramsList, oAuth);
		}
		
	}

	/**
	 * 鑾峰彇寰崥褰撳墠宸茶杞挱娆℃暟
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param ids 寰崥ID鍒楄〃锛岀敤鈥�,鈥濋殧寮�
	 * @param flag  0锛嶈幏鍙栬浆鍙戣鏁帮紝1锛嶈幏鍙栫偣璇勮鏁� 2锛嶄袱鑰呴兘鑾峰彇
 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E6%95%B0%E6%88%96%E7%82%B9%E8%AF%84%E6%95%B0">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�a>
	 */
	public String reCount(OAuth oAuth, String format, String ids, String flag)
			throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("ids", ids));
        paramsList.add(new BasicNameValuePair("flag", flag));
		
		return requestAPI.getResource(tReCountUrl, paramsList,
				oAuth);
	}

	/**
	 * 鑾峰彇鍗曟潯寰崥鐨勮浆鎾悊鐢�/鐐硅瘎鍒楄〃
	 * 
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param flag  鏍囪瘑銆�嶈浆鎾垪琛� 1锛嶇偣璇勫垪琛� 2锛嶇偣璇勪笌杞挱鍒楄〃 
	 * @param rootid 杞彂鎴栧洖澶嶇殑寰崥鏍圭粨鐐筰d锛堟簮寰崥id锛�
	 * @param pageflag 鍒嗛〉鏍囪瘑锛�氱涓�椤碉紝1锛氬悜涓嬬炕椤碉紝2锛氬悜涓婄炕椤碉級
	 * @param pagetime 鏈〉璧峰鏃堕棿锛堢涓�椤碉細濉�屽悜涓婄炕椤碉細濉笂涓�娆¤姹傝繑鍥炵殑绗竴鏉¤褰曟椂闂达紝鍚戜笅缈婚〉锛氬～涓婁竴娆¤姹傝繑鍥炵殑鏈�鍚庝竴鏉¤褰曟椂闂达級
	 * @param reqnum 姣忔璇锋眰璁板綍鐨勬潯鏁帮紙1-100鏉★級
	 * @param twitterid 缈婚〉鐢紝绗�100鏉″～0锛岀户缁悜涓嬬炕椤碉紝濉笂涓�娆¤姹傝繑鍥炵殑鏈�鍚庝竴鏉¤褰昳d
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E5%8D%95%E6%9D%A1%E5%BE%AE%E5%8D%9A%E7%9A%84%E8%BD%AC%E5%8F%91%E6%88%96%E7%82%B9%E8%AF%84%E5%88%97%E8%A1%A8">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String reList(OAuth oAuth, String format, String flag,String rootid,
			String pageflag, String pagetime, String reqnum,
			String twitterid) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("flag", flag));
		paramsList.add(new BasicNameValuePair("rootid", rootid));
		paramsList.add(new BasicNameValuePair("pageflag", pageflag));
		paramsList.add(new BasicNameValuePair("pagetime", pagetime));
		paramsList.add(new BasicNameValuePair("reqnum", reqnum));
		paramsList.add(new BasicNameValuePair("twitterid", twitterid));
		
		return requestAPI.getResource(tReListUrl, paramsList,
				oAuth);
	}
 

	
	/**
	 * 鍙戣〃瑙嗛寰崥
	 * @param oAuth
	 * @param format 杩斿洖鏁版嵁鐨勬牸寮� 鏄紙json鎴杧ml锛�
	 * @param content  寰崥鍐呭
	 * @param clientip 鐢ㄦ埛IP(浠ュ垎鏋愮敤鎴锋墍鍦ㄥ湴) 鐢ㄦ埛IP锛堝繀濉級鐢ㄦ埛娴忚鍣↖P,
	 * @param jing 缁忓害锛堝彲浠ュ～绌猴級  缁忓害锛堝彲浠ュ～绌猴級
	 * @param wei 绾害锛堝彲浠ュ～绌猴級
	 * @param url 瑙嗛鍦板潃锛屽悗鍙拌嚜鍔ㄥ垎鏋愯棰戜俊鎭紝鏀寔youku,tudou,ku6
     * @param syncflag  寰崥鍚屾鍒扮┖闂村垎浜爣璁帮紙鍙�夛紝0-鍚屾锛�涓嶅悓姝ワ紝榛樿涓��  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E8%A7%86%E9%A2%91%E5%BE%AE%E5%8D%9A">鑵捐寰崥寮�鏀惧钩鍙颁笂鍏充簬姝ゆ潯API鐨勬枃妗�/a>
	 */
	public String addVideo(
			OAuth oAuth, String format, String content,
			String clientip, String jing, String wei,String url,
			String syncflag) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("content", content));
		paramsList.add(new BasicNameValuePair("clientip", clientip));
		paramsList.add(new BasicNameValuePair("jing", jing));
		paramsList.add(new BasicNameValuePair("wei", wei));
		paramsList.add(new BasicNameValuePair("url", url));
        paramsList.add(new BasicNameValuePair("syncflag", syncflag));
		
		return requestAPI.postContent(tAddVideoUrl, paramsList,
				oAuth);
	}
	
    public void setAPIBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl=apiBaseUrl;
        tShowUrl=apiBaseUrl+"/t/show";
        tAddUrl=apiBaseUrl+"/t/add";
        tAddPicUrl=apiBaseUrl+"/t/add_pic";
        tAddPicUrlUrl=apiBaseUrl+"/t/add_pic_url";
        tAddVideoUrl=apiBaseUrl+"/t/add_video";
        tCommentUrl=apiBaseUrl+"/t/comment";
        tDelUrl=apiBaseUrl+"/t/del";
        tReAddUrl=apiBaseUrl+"/t/re_add";
        tReCountUrl=apiBaseUrl+"/t/re_count";
        tReListUrl=apiBaseUrl+"/t/re_list";
        tReplyUrl=apiBaseUrl+"/t/reply";
    }
}
