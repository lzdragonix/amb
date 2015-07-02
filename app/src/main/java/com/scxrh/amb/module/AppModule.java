package com.scxrh.amb.module;

import android.app.Application;

import com.scxrh.amb.App;
import com.scxrh.amb.ToastHelper;

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

    @Provides
    @Singleton
    public Application provideApplication()
    {
        return mApp;
    }

    @Provides
    @Singleton
    public ToastHelper provideToastHelper()
    {
        return new ToastHelper();
    }
}
