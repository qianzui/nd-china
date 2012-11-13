package com.hiapk.ui.scene.weibo.tencent;

import java.io.File;

import org.apache.http.message.BasicNameValuePair;

 
/**
 * 微博相关API
 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3">腾讯微博弄1�7放平台上微博相关的API文档<a>
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
     * 使用完毕后，请调甄1�7 shutdownConnection() 关闭自动生成的连接管理器
     * @param OAuthVersion 根据OAuthVersion，配置�1�7�用请求参数
     */
    public TAPI(String OAuthVersion) {
        super(OAuthVersion);
    }

    /**
     * @param OAuthVersion 根据OAuthVersion，配置�1�7�用请求参数
     * @param qHttpClient 使用已有的连接管理器
     */
    public TAPI(String OAuthVersion, QHttpClient qHttpClient) {
        super(OAuthVersion, qHttpClient);
    }

    /**
	 * 获取丄1�7条微博数捄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param id 微博id
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%E6%95%B0%E6%8D%AE">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
	 */
	public String show(OAuth oAuth, String format, String id) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("id", id));
		
		return requestAPI.getResource(tShowUrl, paramsList, oAuth);
	}

	/**
	 * 发表丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
	 */
	public String add(OAuth oAuth, String format, String content,
			String clientip) throws Exception {
		return this.add(oAuth, format, content, clientip, "", "", "");
	}

	/**
	 * 发表丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param jing 经度（可以填空）
	 * @param wei 纬度（可以填空）
	 * @param syncflag  微博同步到空间分享标记（可�1�7�，0-同步＄1�7不同步，默认丄1�7�1�7  
	 * @return
	 * @throws Exception
	 * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
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
	 * 删除丄1�7条微博数捄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param id 微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%88%A0%E9%99%A4%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
	 */
	public String del(OAuth oAuth, String format, String id) throws Exception {
		QArrayList paramsList = new QArrayList();
		paramsList.add(new BasicNameValuePair("format", format));
		paramsList.add(new BasicNameValuePair("id", id));
		
		return requestAPI.postContent(tDelUrl, paramsList, oAuth);
	}

	/**
	 * 转播丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param reid 转播父结点微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
	 */
	public String reAdd(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.reAdd(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * 转播丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param jing 经度（可以填空）
	 * @param wei 纬度（可以填空）
	 * @param reid 转播父结点微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
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
	 * 点评丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param reid 点评父结点微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%82%B9%E8%AF%84%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
	 */
	public String comment(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.comment(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * 点评丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param jing 经度（可以填空）
	 * @param wei 纬度（可以填空）
	 * @param reid 点评父结点微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%82%B9%E8%AF%84%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
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
	 * 回复丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param reid 回复的父结点微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%9B%9E%E5%A4%8D%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%EF%BC%88%E5%8D%B3%E5%AF%B9%E8%AF%9D%EF%BC%89">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
	 */
	public String reply(OAuth oAuth, String format, String content,
			String clientip, String reid) throws Exception {
		return this.reply(oAuth, format, content, clientip, "", "", reid);
	}

	/**
	 * 回复丄1�7条微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param jing 经度（可以填空）
	 * @param wei 纬度（可以填空）
	 * @param reid 回复的父结点微博id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%9B%9E%E5%A4%8D%E4%B8%80%E6%9D%A1%E5%BE%AE%E5%8D%9A%EF%BC%88%E5%8D%B3%E5%AF%B9%E8%AF%9D%EF%BC%89">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
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
	 * 发表丄1�7条带图片的微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param picpath 可以是本地图片路径1�7 戄1�7 网络地址
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7本地图片</a>
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%94%A8%E5%9B%BE%E7%89%87URL%E5%8F%91%E8%A1%A8%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7网络图片</a>
	 */
	public String addPic(OAuth oAuth, String format, String content,
			String clientip, String picpath) throws Exception {
		return this.addPic(oAuth, format, content, clientip, "", "", picpath, "");
	}

	/**
	 * 发表丄1�7条带图片的微卄1�7
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地)
	 * @param jing 经度（可以填空）
	 * @param wei 纬度（可以填空）
	 * @param picpath 可以是本地图片路径1�7 戄1�7 网络地址
	 * @param syncflag  微博同步到空间分享标记（可�1�7�，0-同步＄1�7不同步，默认丄1�7�1�7  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E4%B8%80%E6%9D%A1%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7本地图片</a>
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E7%94%A8%E5%9B%BE%E7%89%87URL%E5%8F%91%E8%A1%A8%E5%B8%A6%E5%9B%BE%E7%89%87%E7%9A%84%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7网络图片</a>
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
	 * 获取微博当前已被转播次数
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param ids 微博ID列表，用 1�7,”隔弄1�7
	 * @param flag  0－获取转发计数，1－获取点评计敄1�7 2－两者都获取
 
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%BD%AC%E6%92%AD%E6%95%B0%E6%88%96%E7%82%B9%E8%AF%84%E6%95%B0">腾讯微博弄1�7放平台上关于此条API的文桄1�7a>
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
	 * 获取单条微博的转播理甄1�7/点评列表
	 * 
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param flag  标识〄1�7�转播列衄1�7 1－点评列衄1�7 2－点评与转播列表 
	 * @param rootid 转发或回复的微博根结点id（源微博id＄1�7
	 * @param pageflag 分页标识＄1�7�第丄1�7页，1：向下翻页，2：向上翻页）
	 * @param pagetime 本页起始时间（第丄1�7页：塄1�7�向上翻页：填上丄1�7次请求返回的第一条记录时间，向下翻页：填上一次请求返回的朄1�7后一条记录时间）
	 * @param reqnum 每次请求记录的条数（1-100条）
	 * @param twitterid 翻页用，笄1�7100条填0，继续向下翻页，填上丄1�7次请求返回的朄1�7后一条记录id
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E8%8E%B7%E5%8F%96%E5%8D%95%E6%9D%A1%E5%BE%AE%E5%8D%9A%E7%9A%84%E8%BD%AC%E5%8F%91%E6%88%96%E7%82%B9%E8%AF%84%E5%88%97%E8%A1%A8">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
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
	 * 发表视频微博
	 * @param oAuth
	 * @param format 返回数据的格弄1�7 是（json或xml＄1�7
	 * @param content  微博内容
	 * @param clientip 用户IP(以分析用户所在地) 用户IP（必填）用户浏览器IP,
	 * @param jing 经度（可以填空）  经度（可以填空）
	 * @param wei 纬度（可以填空）
	 * @param url 视频地址，后台自动分析视频信息，支持youku,tudou,ku6
     * @param syncflag  微博同步到空间分享标记（可�1�7�，0-同步＄1�7不同步，默认丄1�7�1�7  
	 * @return
	 * @throws Exception
     * @see <a href="http://wiki.open.t.qq.com/index.php/%E5%BE%AE%E5%8D%9A%E7%9B%B8%E5%85%B3/%E5%8F%91%E8%A1%A8%E8%A7%86%E9%A2%91%E5%BE%AE%E5%8D%9A">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
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
