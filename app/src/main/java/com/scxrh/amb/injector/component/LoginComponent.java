package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.LoginModule;
import com.scxrh.amb.views.fragment.LoginFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, LoginModule.class })
public interface LoginComponent
{
    void inject(LoginFragment fragment);
}
