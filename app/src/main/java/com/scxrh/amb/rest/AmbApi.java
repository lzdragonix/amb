package com.scxrh.amb.rest;

import com.google.gson.JsonObject;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.SalesManager;

import java.util.List;

import retrofit.client.Response;
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
            @Query("bankNo") String bankNo);
}
