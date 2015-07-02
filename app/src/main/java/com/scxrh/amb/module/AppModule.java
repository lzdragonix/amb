package com.scxrh.amb.module;

import android.app.Application;

import com.scxrh.amb.ToastHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule
{
    private Application mApp;

    public AppModule(Application application)
    {
        mApp = application;
    }

    @Singleton
    @Provides
    public Application provideApplication()
    {
        return mApp;
    }

    @Singleton
    @Provides
    public ToastHelper provideToastHelper()
    {
        return new ToastHelper();
    }
}
