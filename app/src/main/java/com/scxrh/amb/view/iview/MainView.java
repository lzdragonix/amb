package com.scxrh.amb.view.iview;

import com.scxrh.amb.model.City;

public interface MainView
{
    void initTab();

    void changeTab(String name);

    void changeCity(City city);
}
