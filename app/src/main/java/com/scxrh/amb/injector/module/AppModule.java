package com.scxrh.amb.injector.module;

import com.scxrh.amb.App;
import com.scxrh.amb.common.RxBus;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.model.SysInfo;
import com.scxrh.amb.net.http.HttpClient;
import com.scxrh.amb.rest.RestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private App mApp;
    private SettingsManager settings;

    public AppModule(App application)
    {
        mApp = application;
        settings = new SettingsManager(mApp);
    }

    @Singleton
    @Provides
    public App provideApp()
    {
        return mApp;
    }

    @Singleton
    @Provides
    public HttpClient provideHttpClient()
    {
        return new HttpClient(mApp);
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
    public SysInfo provideSysInfo()
    {
        return new SysInfo();
    }
}
