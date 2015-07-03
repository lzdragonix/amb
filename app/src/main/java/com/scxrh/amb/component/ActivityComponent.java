package com.scxrh.amb.component;

import android.app.Activity;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.module.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{
    void inject(Activity activity);
}
