package com.hiapk.ui.scene.weibo.tencent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


/**
 * OAuth鉴权信息通用部分
 */
public class OAuth implements Serializable{

    private static final long serialVersionUID = -6939447877705891817L;
    protected String seqid=null;
    protected String clientIP="127.0.0.1";
    protected String appFrom=null;//android应用应该设置丄1�7 android-sdk-1.0
    protected String openid= null;//用户统一标识
    protected String openkey= null;
    protected String oauthVersion = OAuthConstants.OAUTH_VERSION_1;// OAuth鉴权的版朄1�7 1.0 戄1�7 2.a
    protected String scope = "all"; // 授权范围
    protected int status = 0;// 认证状�1�7�1�7,0:成功,1:获取Request失败 2:获取授权码失贄1�7, 3:获取Access失败
    protected String msg = null;//服务器返回的消息
    
    protected Random random = new Random();
    
    public List<NameValuePair> getCommonParamsList(){
        seqid=this.generateSeqId();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//        parameters.add(new BasicNameValuePair("seqid",  seqid));
           parameters.add(new BasicNameValuePair("clientip",clientIP));
//        parameters.add(new BasicNameValuePair("appfrom",appFrom));
//        parameters.add(new BasicNameValuePair("openid",  openid));
        return parameters;
    }
    
    /**
     * 生成seqId＄1�7位随机数
     * @return
     */
    public String generateSeqId(){
        String result="";
        for(int i=0;i<2;i++){
            result=String.valueOf(random.nextInt(1000000))+result;
            for(;result.length()<(i+1)*6;result="0"+result);
        }
        return result;
    }

    /**请求砄1�7*/
    public String getSeqId() {
        return seqid;
    }

    /**请求码，使用 {@link #generateSeqId()} 生成 */
    public void setSeqId(String seqId) {
        this.seqid = seqId;
    }

    /**用户真实IP，需自行取得*/
    public String getClientIP() {
        return clientIP;
    }

    /**请设置用户真实IP，避免使用内网IP，缺省�1�7�为"127.0.0.1" */
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    /**应用扄1�7使用的SDK版本*/
    public String getAppFrom() {
        return appFrom;
    }

    /**应用扄1�7使用的SDK版本，请仄1�7 {@link com.tencent.weibo.constants.OAuthConstants#APP_FROM_ANDROID_SDK_1} 丄1�7 
     * {@link com.tencent.weibo.constants.OAuthConstants#APP_FROM_JAVA_SDK_2} 中�1�7�择  */
    public void setAppFrom(String appFrom) {
        this.appFrom = appFrom;
    }

    /**用户登录怄1�7*/
    public String getOpenid() {
        return openid;
    }

    /**该�1�7�在用户授权过程中取得，标记用户登录怄1�7*/
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**与openid对应的Key*/
    public String getOpenkey() {
        return openkey;
    }

    /**该�1�7�在用户授权后取径1�7,与openid对应*/
    public void setOpenkey(String openkey) {
        this.openkey = openkey;
    }

    /**使用的OAuth授权的版朄1�7*/
    public String getOauthVersion() {
        return oauthVersion;
    }

    /**请从 {@link com.tencent.weibo.constants.OAuthConstants#OAUTH_VERSION_1} 丄1�7
     *  {@link com.tencent.weibo.constants.OAuthConstants#OAUTH_VERSION_2_A} 中�1�7�择  */
    public void setOauthVersion(String oauthVersion) {
        this.oauthVersion = oauthVersion;
    }

    /**授权范围 */
    public String getScope() {
        return scope;
    }

    /**授权范围 */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**认证状�1�7�1�7<br>
     * <li>0:成功,
     * <li>1:Request失败,
     * <li>2:获取验证码失贄1�7,
     * <li>3:Access失败
     */
    public int getStatus() {
        return status;
    }

    /**认证状�1�7�1�7<br>
     * <li>0:成功,
     * <li>1:Request失败,
     * <li>2:获取验证码失贄1�7,
     * <li>3:Access失败
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**服务器返回的消息*/
    public String getMsg() {
        return msg;
    }

    /**服务器返回的消息*/
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
