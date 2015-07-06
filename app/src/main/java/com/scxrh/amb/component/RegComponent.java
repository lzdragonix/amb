package com.scxrh.amb.component;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.RegModule;
import com.scxrh.amb.view.fragment.RegFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, RegModule.class })
public interface RegComponent
{
    void inject(RegFragment fragment);
}
