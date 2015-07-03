package com.scxrh.amb.component;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.module.MainModule;
import com.scxrh.amb.view.fragment.MainFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent
{
    void inject(MainFragment fragment);
}
