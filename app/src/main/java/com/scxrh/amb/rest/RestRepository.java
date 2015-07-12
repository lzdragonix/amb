package com.scxrh.amb.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.scxrh.amb.model.City;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class RestRepository
{
    private AmbApi mAmbApi;

    public RestRepository()
    {
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new CityItemAdapterFactory()).create();
        RestAdapter.Builder builder = new RestAdapter.Builder();
        RestAdapter restAdapter = builder.setEndpoint(AmbApi.URL_BASE)
                                         .setLogLevel(RestAdapter.LogLevel.FULL)
                                         .setConverter(new GsonConverter(gson))
                                         .build();
        mAmbApi = restAdapter.create(AmbApi.class);
    }

    public Observable<List<City>> getCities()
    {
        return mAmbApi.getCities();
    }
}
