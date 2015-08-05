package com.scxrh.amb.views.view;

import com.scxrh.amb.model.UserInfo;

public interface SuggView extends ProgressView
{
    void close();

    void showLogin();

    void initView(UserInfo userInfo);
}
