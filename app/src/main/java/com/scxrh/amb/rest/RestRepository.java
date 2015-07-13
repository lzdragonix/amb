package com.scxrh.amb.rest;

import android.accounts.NetworkErrorException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.scxrh.amb.model.City;
import com.scxrh.amb.rest.exception.NetworkTimeOutException;
import com.scxrh.amb.rest.exception.NetworkUknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class RestRepository
{
    TypeAdapter<JSONObject> dd = new TypeAdapter<JSONObject>()
    {
        @Override
        public void write(JsonWriter out, JSONObject value) throws IOException
        {
        }

        @Override
        public JSONObject read(JsonReader in) throws IOException
        {
            try
            {
                return new JSONObject(in.toString());
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    };

JsonDeserializer<JSONObject> tt = new JsonDeserializer<JSONObject>() {
    @Override
    public JSONObject deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException
    {
        return null;
    }
};


    private AmbApi mAmbApi;

    public RestRepository()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(JSONObject.class, dd)
                                     .registerTypeAdapterFactory(new CityItemAdapterFactory())
                                     .create();
        RestAdapter.Builder builder = new RestAdapter.Builder();
        RestAdapter restAdapter = builder.setEndpoint(AmbApi.END_POINT)
                                         .setLogLevel(RestAdapter.LogLevel.FULL)
                                         .setConverter(new GsonConverter(gson))
                                         .setErrorHandler(new RetrofitErrorHandler())
                                         .build();
        mAmbApi = restAdapter.create(AmbApi.class);
    }

    public Observable<List<City>> getCities()
    {
        return mAmbApi.getCities();
    }

    public Observable<Response> login(String user, String pwd)
    {
        return mAmbApi.login(user, pwd);
    }

    public Observable<JsonObject> queryAgreement()
    {
        return mAmbApi.queryAgreement();
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
