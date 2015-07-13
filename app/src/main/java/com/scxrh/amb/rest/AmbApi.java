package com.scxrh.amb.rest;

import com.google.gson.JsonObject;
import com.scxrh.amb.model.City;

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

    @POST("/shopxx/m/area_queryCity.action")
    Observable<List<List<City>>> queryCities();

    @POST("/shopxx/m/agreement_queryAgreement.action")
    Observable<JsonObject> queryAgreement();
}
