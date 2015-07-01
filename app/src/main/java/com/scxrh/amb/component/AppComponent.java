package com.scxrh.amb.component;

import android.app.Application;

import javax.inject.Singleton;

@Singleton
//@Component(modules = {AppModule.class, ApiServiceModule.class, AppServiceModule.class})
public interface AppComponent
{
    Application getApplication();
    //    ApiService getService();
    //
    //    User getUser();
}
