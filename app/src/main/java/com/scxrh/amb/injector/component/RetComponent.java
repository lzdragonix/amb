package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.RetModule;
import com.scxrh.amb.views.fragment.RetrievePwdFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, RetModule.class })
public interface RetComponent
{
    void inject(RetrievePwdFragment fragment);
}
