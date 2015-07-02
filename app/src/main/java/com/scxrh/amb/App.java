package com.scxrh.amb;

import android.app.Application;

import com.scxrh.amb.component.AppComponent;
import com.scxrh.amb.component.DaggerAppComponent;
import com.scxrh.amb.module.AppModule;

public class App extends Application
{
    private AppComponent component;

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
