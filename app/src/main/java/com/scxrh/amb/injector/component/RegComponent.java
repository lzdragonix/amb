package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.RegModule;
import com.scxrh.amb.views.fragment.RegFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, RegModule.class })
public interface RegComponent
{
    void inject(RegFragment fragment);
}
