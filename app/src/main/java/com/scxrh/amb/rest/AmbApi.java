package com.scxrh.amb.rest;

import com.scxrh.amb.model.City;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface AmbApi
{
    public static final String URL_BASE = "http://113.106.63.129:8489";
    public static final String URL_LOGIN = URL_BASE + "/windforce/m/mlogin.action";
    public static final String URL_USER_REGISTER = URL_BASE + "/windforce/m/user_register.action";
    public static final String URL_SMS_SEND = URL_BASE + "/windforce/m/sms_send.action";

    @GET("/shopxx/m/area_queryCity.action")
    Observable<List<City>> getCities();
}
