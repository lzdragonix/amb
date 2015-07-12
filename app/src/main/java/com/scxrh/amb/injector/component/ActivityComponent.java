package com.scxrh.amb.injector.component;

import android.app.Activity;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent
{
    void inject(Activity activity);

    Activity getActivity();
}
