package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.SelCityModule;
import com.scxrh.amb.views.fragment.SelCityFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, SelCityModule.class })
public interface SelCityComponent
{
    void inject(SelCityFragment fragment);
}
