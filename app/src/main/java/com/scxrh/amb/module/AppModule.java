package com.scxrh.amb.module;

import android.app.Application;

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

    @Provides
    @Singleton
    public Application provideApplication()
    {
        return mApp;
    }
}
