package com.scxrh.amb.component;

import com.scxrh.amb.ActivityScope;
import com.scxrh.amb.module.ActivityModule;
import com.scxrh.amb.module.LoginModule;
import com.scxrh.amb.view.fragment.LoginFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, LoginModule.class })
public interface LoginComponent
{
    void inject(LoginFragment fragment);
}
