package com.scxrh.amb.views.view;

import com.scxrh.amb.model.City;

public interface PerInfoView extends ProgressView
{
    void setName(String name);

    void changeCity(City city);

    void changeCommunity(City city);

    void changeAvatar(String path);

    void close();
}
