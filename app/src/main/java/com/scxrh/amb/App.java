package com.scxrh.amb;

import android.app.Application;
import android.content.Context;

import com.scxrh.amb.component.AppComponent;
import com.scxrh.amb.component.DaggerAppComponent;
import com.scxrh.amb.module.AppModule;

public class App extends Application
{
    private AppComponent component;

    public static App get(Context context)
    {
        return (App)context.getApplicationContext();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        setupGraph();
    }

    private void setupGraph()
    {
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        component.inject(this);
    }

    public AppComponent getComponent()
    {
        return component;
    }
}
