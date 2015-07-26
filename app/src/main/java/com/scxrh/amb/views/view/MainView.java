package com.scxrh.amb.views.view;

import com.scxrh.amb.model.City;

public interface MainView extends MvpView
{
    void changeCity(City city);

    void changeCommunity(City city);

    void changeTab(String tab);
}
