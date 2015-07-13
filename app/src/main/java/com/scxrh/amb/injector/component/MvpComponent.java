package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.views.fragment.AgreementFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, MvpModule.class })
public interface MvpComponent
{
    void inject(AgreementFragment fragment);
}
