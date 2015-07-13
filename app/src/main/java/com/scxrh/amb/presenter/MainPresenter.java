package com.scxrh.amb.presenter;

import com.scxrh.amb.common.RxBus;
import com.scxrh.amb.model.City;
import com.scxrh.amb.views.view.MainView;
import com.scxrh.amb.views.view.MvpView;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainPresenter
{
    @Inject
    RxBus rxBus;
    private MainView view;

    @Inject
    public MainPresenter(MvpView view)
    {
        this.view = (MainView)view;
    }

    public void initialize()
    {
        view.initTab();
        Observable<City> observable = rxBus.register("city_change", City.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(view::changeCity);
        observable = rxBus.register("community_change", City.class);
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(view::changeCommunity);
    }

    public void changeTab(String tab)
    {
        view.changeTab(tab);
    }
}
