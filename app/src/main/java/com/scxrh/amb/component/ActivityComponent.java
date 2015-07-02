package com.scxrh.amb.component;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.ToastHelper;
import com.scxrh.amb.activity.BaseActivity;
import com.scxrh.amb.module.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{
    BaseActivity inject(BaseActivity activity);

    ToastHelper getToastHelper();
}
