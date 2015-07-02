package com.scxrh.amb.component;

import com.scxrh.amb.App;
import com.scxrh.amb.ToastHelper;
import com.scxrh.amb.activity.BaseActivity;
import com.scxrh.amb.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent
{
    void inject(App app);
    void inject(BaseActivity activity);

    ToastHelper getToastHelper();
}
