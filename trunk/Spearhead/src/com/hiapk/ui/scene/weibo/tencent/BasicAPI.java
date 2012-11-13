package com.hiapk.ui.scene.weibo.tencent;


/**
 * API类的通用部分
 */
public abstract class BasicAPI {
    
    protected RequestAPI requestAPI;
    protected String apiBaseUrl=null;

    public BasicAPI(String OAuthVersion){
        if (OAuthVersion == OAuthConstants.OAUTH_VERSION_1 ) {
            requestAPI = new OAuthV1Request();
            apiBaseUrl=APIConstants.API_V1_BASE_URL;
        }
    }
    
    public BasicAPI(String OAuthVersion, QHttpClient qHttpClient){
        if (OAuthVersion == OAuthConstants.OAUTH_VERSION_1  ) {
            requestAPI = new OAuthV1Request(qHttpClient);
            apiBaseUrl=APIConstants.API_V1_BASE_URL;
        }
    }
    
    public void shutdownConnection(){
        requestAPI.shutdownConnection();
    }

    public String getAPIBaseUrl() {
        return apiBaseUrl;
    }

    public abstract  void setAPIBaseUrl(String apiBaseUrl);
    
}
