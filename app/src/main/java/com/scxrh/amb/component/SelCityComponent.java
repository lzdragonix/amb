package com.scxrh.amb.component;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.SelCityModule;
import com.scxrh.amb.view.fragment.SelCityFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, SelCityModule.class })
public interface SelCityComponent
{
    void inject(SelCityFragment fragment);
}
