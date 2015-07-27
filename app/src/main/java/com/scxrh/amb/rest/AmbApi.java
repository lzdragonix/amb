package com.scxrh.amb.rest;

import com.google.gson.JsonObject;
import com.scxrh.amb.model.BankInfo;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.model.UserInfo;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface AmbApi
{
    String END_POINT = "http://183.38.68.19:8489";

    @POST("/windforce/m/mlogin.action")
    Observable<Response> login(@Query("userKey") String user, @Query("password") String pwd);

    @POST("/windforce/m/user_register.action")
    Observable<Response> register(@Query("telephone") String telephone, @Query("password") String pwd,
            @Query("verifyNo") String verifyNo);

    @POST("/shopxx/m/agreement_queryAgreement.action")
    Observable<JsonObject> queryAgreement();

    @POST("/shopxx/m/area_queryCity.action")
    Observable<List<List<City>>> queryCities();

    @POST("/shopxx/m/area_queryCommunity.action")
    Observable<List<List<City>>> queryCommunities(@Query("cityCode") String cityCode);

    @POST("/windforce/m/mfinancialAdvisor_queryManagers.action")
    Observable<List<SalesManager>> queryManagers(@Query("communityId") String communityId);

    @POST("/windforce/m/user_restorePwd.action")
    Observable<Response> restorePwd(@Query("telephone") String tel, @Query("newPassword") String pwd,
            @Query("verifyNo") String verifyNo);

    @POST("/windforce/m/mfinancialProduct_queryFinancialProduct.action")
    Observable<List<FinancialProduct>> queryFinancialProduct(@Query("communityId") String communityId,
            @Query("bankNo") String bankNo, @Query("itemId") String itemId);

    @POST("/windforce/m/ui_queryUIData.action")
    Observable<Map<String, UIData>> queryUIData(@Query("pageName") String pageName);

    @POST("/windforce/m/ad_queryAd.action")
    Observable<DetailItem> queryAd(@Query("itemId") String itemId);

    @POST("/shopxx/m/shop_queryShop.action")
    Observable<DetailItem> queryShop(@Query("itemId") String itemId);

    @POST("/shopxx/m/product_queryProduct.action")
    Observable<DetailItem> queryProduct(@Query("itemId") String itemId);

    @POST("/windforce/m/user_queryCurUserInfo.action")
    Observable<UserInfo> queryCurUserInfo();

    @POST("/windforce/m/user_modifyUserInfo.action")
    Observable<Response> modifyUserInfo(@Query("userId") String userId, @Query("userName") String userName,
            @Query("telephone") String telephone, @Query("email") String email, @Query("address") String address,
            @Query("communityId") String communityId);

    @POST("/windforce/m/user_modifyPassword.action")
    Observable<JSONObject> modifyPassword(@Query("oldPassword") String oldPassword,
            @Query("newPassword") String newPassword);

    @POST("/windforce/m/suggestion_submitSuggest.action")
    Observable<JSONObject> submitSuggest(@Query("name") String name, @Query("contactInfo") String contactInfo,
            @Query("content") String content);

    @POST("/windforce/m/bank_queryBank.action")
    Observable<List<BankInfo>> queryBank(@Query("cityCode") String cityCode, @Query("communityId") String communityId);
}
