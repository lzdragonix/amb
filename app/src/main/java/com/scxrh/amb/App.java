package com.scxrh.amb;

import android.app.Application;
import android.content.Context;

import com.scxrh.amb.component.AppComponent;
import com.scxrh.amb.component.DaggerAppComponent;
import com.scxrh.amb.module.AppModule;

public class App extends Application
{
    private static AppComponent component;

    public static App get(Context context)
    {
        return (App)context.getApplicationContext();
    }

    public static AppComponent getAppComponent()
    {
        return component;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector()
    {
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
