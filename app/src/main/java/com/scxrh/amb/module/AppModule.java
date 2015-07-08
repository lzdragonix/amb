package com.scxrh.amb.module;

import com.scxrh.amb.App;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.net.http.HttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private App mApp;

    public AppModule(App application)
    {
        mApp = application;
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
        return new SettingsManager(mApp);
    }
}
