package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.MainModule;
import com.scxrh.amb.views.fragment.MainFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent
{
    void inject(MainFragment fragment);
}
