package com.scxrh.amb.rest;

import android.accounts.NetworkErrorException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.scxrh.amb.Const;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.BankInfo;
import com.scxrh.amb.model.City;
import com.scxrh.amb.model.DetailItem;
import com.scxrh.amb.model.FinancialProduct;
import com.scxrh.amb.model.SalesManager;
import com.scxrh.amb.model.UIData;
import com.scxrh.amb.model.UserInfo;
import com.scxrh.amb.rest.exception.NetworkTimeOutException;
import com.scxrh.amb.rest.exception.NetworkUknownHostException;
import com.scxrh.amb.rest.serialiers.BankInfoDeserializer;
import com.scxrh.amb.rest.serialiers.CityListDeserializer;
import com.scxrh.amb.rest.serialiers.DetailItemDeserializer;
import com.scxrh.amb.rest.serialiers.FinProductDeserializer;
import com.scxrh.amb.rest.serialiers.JSONObjectDeserializer;
import com.scxrh.amb.rest.serialiers.ManagerDeserializer;
import com.scxrh.amb.rest.serialiers.UIDataDeserializer;
import com.scxrh.amb.rest.serialiers.UserInfoDeserializer;

import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;

public class RestClient
{
    private String cookie;
    private AmbApi mAmbApi;
    private GsonConverter mConverter;
    private SettingsManager settings;

    public RestClient(SettingsManager settings)
    {
        this.settings = settings;
        cookie = settings.getString(Const.KEY_COOKIE);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<List<List<City>>>() { }.getType(), new CityListDeserializer())
                   .registerTypeAdapter(new TypeToken<List<SalesManager>>() { }.getType(), new ManagerDeserializer())
                   .registerTypeAdapter(new TypeToken<List<FinancialProduct>>() { }.getType(),
                                        new FinProductDeserializer())
                   .registerTypeAdapter(new TypeToken<Map<String, UIData>>() { }.getType(), new UIDataDeserializer())
                   .registerTypeAdapter(DetailItem.class, new DetailItemDeserializer())
                   .registerTypeAdapter(JSONObject.class, new JSONObjectDeserializer())
                   .registerTypeAdapter(new TypeToken<List<BankInfo>>() { }.getType(), new BankInfoDeserializer())
                   .registerTypeAdapter(UserInfo.class, new UserInfoDeserializer());
        mConverter = new GsonConverter(gsonBuilder.create());
        RestAdapter.Builder builder = new RestAdapter.Builder();
        RestAdapter restAdapter = builder.setEndpoint(AmbApi.END_POINT)
                                         .setLogLevel(RestAdapter.LogLevel.FULL)
                                         .setConverter(mConverter)
                                         .setErrorHandler(new RetrofitErrorHandler())
                                         .setRequestInterceptor(request -> request.addHeader("Cookie", cookie))
                                         .build();
        mAmbApi = restAdapter.create(AmbApi.class);
    }

    public Observable<Response> login(String user, String pwd)
    {
        return mAmbApi.login(user, pwd).doOnNext(response -> {
            try
            {
                JsonObject json = (JsonObject)mConverter.fromBody(response.getBody(), JsonObject.class);
                if (Const.RETURNCODE_0000.equals(json.get(Const.KEY_CODE).getAsString()))
                {
                    for (Header header : response.getHeaders())
                    {
                        if ("Set-Cookie".equals(header.getName()))
                        {
                            cookie = header.getValue();
                            settings.setValue(Const.KEY_COOKIE, cookie);
                            break;
                        }
                    }
                }
                else
                {
                    throw new RuntimeException("error-password");
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
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

    public Observable<List<FinancialProduct>> queryFinancialProduct(String communityId, String bankNo, String itemId)
    {
        return mAmbApi.queryFinancialProduct(communityId, bankNo, itemId);
    }

    public Observable<Response> restorePwd(String tel, String pwd, String verify)
    {
        return mAmbApi.restorePwd(tel, pwd, verify);
    }

    public Observable<Map<String, UIData>> queryUIData(String page)
    {
        return mAmbApi.queryUIData(page);
    }

    public Observable<DetailItem> queryAd(String itemId)
    {
        return mAmbApi.queryAd(itemId);
    }

    public Observable<DetailItem> queryShop(String itemId)
    {
        return mAmbApi.queryShop(itemId);
    }

    public Observable<DetailItem> queryProduct(String itemId)
    {
        return mAmbApi.queryProduct(itemId);
    }

    public Observable<UserInfo> queryCurUserInfo()
    {
        return mAmbApi.queryCurUserInfo();
    }

    public Observable<JSONObject> modifyPassword(String oldPassword, String newPassword)
    {
        return mAmbApi.modifyPassword(oldPassword, newPassword);
    }

    public Observable<JSONObject> submitSuggest(String name, String contactInfo, String content)
    {
        return mAmbApi.submitSuggest(name, contactInfo, content);
    }

    public Observable<List<BankInfo>> queryBank(String cityCode, String communityId)
    {
        return mAmbApi.queryBank(cityCode, communityId);
    }

    public Observable<Response> modifyUserInfo(String userId, String userName, String telephone, String email,
            String address, String communityId)
    {
        return mAmbApi.modifyUserInfo(userId, userName, telephone, email, address, communityId);
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
                return new NetworkErrorException(cause.getMessage());
            }
            return new Exception();
        }
    }
}
