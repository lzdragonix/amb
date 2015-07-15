package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.views.fragment.AgreementFragment;
import com.scxrh.amb.views.fragment.FinProFragment;
import com.scxrh.amb.views.fragment.MainFragment;
import com.scxrh.amb.views.fragment.ManagerFragment;
import com.scxrh.amb.views.fragment.RegFragment;
import com.scxrh.amb.views.fragment.RetrievePwdFragment;
import com.scxrh.amb.views.fragment.SelCityFragment;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = { ActivityModule.class, MvpModule.class })
public interface MvpComponent
{
    void inject(MainFragment fragment);

    void inject(AgreementFragment fragment);

    void inject(SelCityFragment fragment);

    void inject(RegFragment fragment);

    void inject(ManagerFragment fragment);

    // 找回密码
    void inject(RetrievePwdFragment fragment);

    void inject(FinProFragment fragment);
}
