package com.scxrh.amb.net.http;

import org.json.JSONObject;

/**
 * http请求的回调接口
 */
public interface IHttpResponse
{
    /**
     * http请求开始的回调方法
     */
    void onHttpStart();

    /**
     * http请求成功以后的回调方法
     *
     * @param response 请求成功返回的数据
     */
    void onHttpSuccess(JSONObject response);

    /**
     * http请求失败的回调方法
     */
    void onHttpFailure(JSONObject response);

    /**
     * http请求结束的回调方法
     */
    void onHttpFinish();
}
