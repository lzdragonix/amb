package com.scxrh.amb.component;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.RetModule;
import com.scxrh.amb.view.fragment.RetrievePwdFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, RetModule.class })
public interface RetComponent
{
    void inject(RetrievePwdFragment fragment);
}
