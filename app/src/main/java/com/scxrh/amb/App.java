package com.scxrh.amb;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.scxrh.amb.common.CrashHandler;
import com.scxrh.amb.injector.component.AppComponent;
import com.scxrh.amb.injector.component.DaggerAppComponent;
import com.scxrh.amb.injector.module.AppModule;

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
        Fresco.initialize(this);
        CrashHandler.initInstance(this);
        SDKInitializer.initialize(this);
    }

    private void initializeInjector()
    {
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }
}
