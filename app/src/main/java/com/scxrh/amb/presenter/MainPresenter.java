package com.scxrh.amb.presenter;

import com.scxrh.amb.common.RxBus;
import com.scxrh.amb.model.City;
import com.scxrh.amb.views.view.MainView;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainPresenter
{
    @Inject
    MainView view;
    @Inject
    RxBus rxBus;

    @Inject
    public MainPresenter() { }

    public void initialize()
    {
        view.initTab();
        Observable<City> observable = rxBus.register("city_change", City.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(view::changeCity);
    }

    public void changeTab(String tab)
    {
        view.changeTab(tab);
    }
}
