package com.scxrh.amb.injector.component;

import com.scxrh.amb.injector.ActivityScope;
import com.scxrh.amb.injector.module.ActivityModule;
import com.scxrh.amb.injector.module.MvpModule;
import com.scxrh.amb.views.fragment.AgreementFragment;
import com.scxrh.amb.views.fragment.BankFragment;
import com.scxrh.amb.views.fragment.DetailFragment;
import com.scxrh.amb.views.fragment.FavoriteFragment;
import com.scxrh.amb.views.fragment.FinProFragment;
import com.scxrh.amb.views.fragment.FinanceFragment;
import com.scxrh.amb.views.fragment.LiveFragment;
import com.scxrh.amb.views.fragment.LiveMSFragment;
import com.scxrh.amb.views.fragment.LoginFragment;
import com.scxrh.amb.views.fragment.MainFragment;
import com.scxrh.amb.views.fragment.ManagerFragment;
import com.scxrh.amb.views.fragment.MineFragment;
import com.scxrh.amb.views.fragment.ModifyPwdFragment;
import com.scxrh.amb.views.fragment.OrderDetailFragment;
import com.scxrh.amb.views.fragment.PerInfoFragment;
import com.scxrh.amb.views.fragment.RecommendFragment;
import com.scxrh.amb.views.fragment.RegFragment;
import com.scxrh.amb.views.fragment.RetrievePwdFragment;
import com.scxrh.amb.views.fragment.SelCityFragment;
import com.scxrh.amb.views.fragment.SuggestionFragment;

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

    void inject(LoginFragment fragment);

    void inject(LiveFragment fragment);

    void inject(RecommendFragment fragment);

    void inject(DetailFragment fragment);

    void inject(PerInfoFragment fragment);

    void inject(MineFragment fragment);

    void inject(ModifyPwdFragment fragment);

    void inject(SuggestionFragment fragment);

    void inject(BankFragment fragment);

    void inject(FinanceFragment fragment);

    void inject(FavoriteFragment fragment);

    void inject(LiveMSFragment fragment);

    void inject(OrderDetailFragment fragment);
}
