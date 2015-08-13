package com.scxrh.amb.rest;

import com.google.gson.JsonObject;
import com.scxrh.amb.model.BankInfo;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.FavoriteItem;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.Order;
import com.scxrh.amb.model.Points;
import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.model.UserInfo;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

public interface AmbApi
{
    String END_POINT = "http://113.106.63.129:8489";

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

    @FormUrlEncoded
    @POST("/windforce/m/user_modifyUserInfo.action")
    Observable<Response> modifyUserInfo(@Field("userId") String userId, @Field("userName") String userName,
            @Field("telephone") String telephone, @Field("email") String email, @Field("address") String address,
            @Field("communityId") String communityId);

    @POST("/windforce/m/user_modifyPassword.action")
    Observable<JSONObject> modifyPassword(@Query("oldPassword") String oldPassword,
            @Query("newPassword") String newPassword);

    @POST("/shopxx/m/suggestion_submitSuggest.action")
    Observable<JSONObject> submitSuggest(@Query("name") String name, @Query("contactInfo") String contactInfo,
            @Query("content") String content);

    @POST("/windforce/m/bank_queryBank.action")
    Observable<List<BankInfo>> queryBank(@Query("cityCode") String cityCode, @Query("communityId") String communityId);

    @POST("/windforce/m/favorite_query.action")
    Observable<List<FavoriteItem>> queryFavorite();

    @POST("/windforce/m/favorite_add.action")
    Observable<Response> addFavorite(@Query("itemId") String itemId, @Query("contentType") String contentType);

    @POST("/windforce/m/favorite_cancel.action")
    Observable<Response> cancelFavorite(@Query("cancelFavoriteId") String cancelFavoriteId);

    @POST("/shopxx/m/order_addOrder.action")
    Observable<Response> addOrder(@Query("userId") String userId, @Query("communityId") String communityId,
            @Query("telephone") String telephone, @Query("address") String address,
            @Query("receiverName") String receiverName, @Query("zipCode") String zipCode,
            @Query("addItems") String addItems);

    @POST("/shopxx/m/order_queryOrder.action")
    Observable<List<Order>> queryOrder(@Query("userId") String userId, @Query("orderId") String orderId,
            @Query("orderState") String orderState);

    @POST("/shopxx/m/point_queryLog.action")
    Observable<List<Points>> queryPoint(@Query("userId") String userId);

    @POST("/windforce/m/mlogout.action")
    Observable<Response> logout();

    @POST("/shopxx/m/order_confirmReceiving.action")
    Observable<Response> confirmReceiving(@Query("receivingItems") String receivingItems);
}
