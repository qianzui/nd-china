package com.hiapk.ui.scene.weibo.tencent;

import java.util.List;

import org.apache.http.NameValuePair;


/**
 * 调用Http和Https协议通讯的接叄1�7
 */
public interface RequestAPI {
    /**
     * 使用Get方法发�1�7�API请求
     * 
     * @param url  远程API请求地址
     * @param paramsList 参数列表
     * @param oAuth OAuth鉴权信息
     * @return  Json 戄1�7 XML 格式的资溄1�7
     * @throws Exception
     * @see <a href="">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
     */
    public String getResource(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception;
    
    /**
     * 使用Post方法发�1�7�API请求
     * 
     * @param url  远程API请求地址
     * @param paramsList 参数列表
     * @param oAuth OAuth鉴权信息
     * @return  Json 戄1�7 XML 格式的资溄1�7
     * @throws Exception
     * @see <a href="">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
     */
    public String postContent(String url, List<NameValuePair> paramsList, OAuth oAuth) throws Exception;
    
    /**
     * 使用Post方法发�1�7�API请求，并上传文件
     * 
     * @param url  远程API请求地址
     * @param paramsList 参数列表
     * @param files 霄1�7要上传的文件列表
     * @param oAuth OAuth鉴权信息
     * @return  Json 戄1�7 XML 格式的资溄1�7
     * @throws Exception
     * @see <a href="">腾讯微博弄1�7放平台上关于此条API的文桄1�7/a>
     */
    public String postFile(String url, List<NameValuePair> paramsList,
            List<NameValuePair> files, OAuth oAuth) throws Exception;
    
    /**
     * 关闭连接管理噄1�7
     */
    public void shutdownConnection();

}
