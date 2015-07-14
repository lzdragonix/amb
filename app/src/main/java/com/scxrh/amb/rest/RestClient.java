package com.scxrh.amb.rest;

import android.accounts.NetworkErrorException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.rest.exception.NetworkTimeOutException;
import com.scxrh.amb.rest.exception.NetworkUknownHostException;
import com.scxrh.amb.rest.serialiers.CityListDeserializer;
import com.scxrh.amb.rest.serialiers.ManagerDeserializer;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class RestClient
{
    private String cookie;
    private AmbApi mAmbApi;

    public RestClient()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<List<List<City>>>() { }.getType(), new CityListDeserializer())
                   .registerTypeAdapter(new TypeToken<List<SalesManager>>() { }.getType(), new ManagerDeserializer());
        RestAdapter.Builder builder = new RestAdapter.Builder();
        RestAdapter restAdapter = builder.setEndpoint(AmbApi.END_POINT)
                                         .setLogLevel(RestAdapter.LogLevel.FULL)
                                         .setConverter(new GsonConverter(gsonBuilder.create()))
                                         .setErrorHandler(new RetrofitErrorHandler())
                                         .setRequestInterceptor(request -> request.addHeader("Cookie", cookie))
                                         .build();
        mAmbApi = restAdapter.create(AmbApi.class);
    }

    public Observable<Response> login(String user, String pwd)
    {
        return mAmbApi.login(user, pwd).doOnNext(response -> {
            for (Header header : response.getHeaders())
            {
                if ("Set-Cookie".equals(header.getName()))
                {
                    cookie = header.getValue();
                    break;
                }
            }
        });
    }

    public Observable<Response> register(String user, String pwd, String verify)
    {
        return mAmbApi.register(user, pwd, verify);
    }

    public Observable<JsonObject> queryAgreement()
    {
        return mAmbApi.queryAgreement();
    }

    public Observable<List<List<City>>> queryCities()
    {
        return mAmbApi.queryCities();
    }

    public Observable<List<List<City>>> queryCommunities(String cityCode)
    {
        return mAmbApi.queryCommunities(cityCode);
    }

    public Observable<List<SalesManager>> queryManagers(String communityId)
    {
        return mAmbApi.queryManagers(communityId);
    }

    public class RetrofitErrorHandler implements retrofit.ErrorHandler
    {
        @Override
        public Throwable handleError(retrofit.RetrofitError cause)
        {
            if (cause.getKind() == retrofit.RetrofitError.Kind.NETWORK)
            {
                if (cause.getCause() instanceof SocketTimeoutException) { return new NetworkTimeOutException(); }
                else if (cause.getCause() instanceof UnknownHostException) { return new NetworkUknownHostException(); }
                else if (cause.getCause() instanceof ConnectException) { return cause.getCause(); }
            }
            else
            {
                return new NetworkErrorException();
            }
            return new Exception();
        }
    }
}
