package com.scxrh.amb.injector.module;

import com.google.gson.Gson;
import com.scxrh.amb.App;
import com.scxrh.amb.Const;
import com.scxrh.amb.common.RxBus;
import com.scxrh.amb.manager.DirManager;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.model.City;
import com.scxrh.amb.rest.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private App mApp;
    private SettingsManager settings;
    private AppInfo sysInfo;

    public AppModule(App application)
    {
        mApp = application;
        settings = new SettingsManager(mApp);
        sysInfo = new AppInfo();
        initSysInfo(sysInfo);
    }

    @Singleton
    @Provides
    public App provideApp()
    {
        return mApp;
    }

    @Singleton
    @Provides
    public MessageManager provideMessageManager()
    {
        return new MessageManager(mApp);
    }

    @Singleton
    @Provides
    public SettingsManager provideSettingsManager()
    {
        return settings;
    }

    @Singleton
    @Provides
    public RxBus provideRxBus()
    {
        return new RxBus();
    }

    @Singleton
    @Provides
    public RestClient provideRestRepository()
    {
        return new RestClient(settings);
    }

    @Singleton
    @Provides
    public AppInfo provideSysInfo()
    {
        return sysInfo;
    }

    @Singleton
    @Provides
    public DirManager provideDirManager()
    {
        return new DirManager();
    }

    private void initSysInfo(AppInfo info)
    {
        String name = settings.getString(Const.KEY_USER_NAME);
        info.setName(name);
        Gson gson = new Gson();
        City city = gson.fromJson(settings.getString(Const.KEY_CITY), City.class);
        if (city == null)
        {
            city = new City();
            city.setId("1");
            city.setName("北京");
        }
        info.setCity(city);
        city = gson.fromJson(settings.getString(Const.KEY_COMMUNITY), City.class);
        info.setCommunity(city == null ? new City() : city);
        info.setAvatar(settings.getString(Const.KEY_AVATAR));
    }
}
