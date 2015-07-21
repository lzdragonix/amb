package com.scxrh.amb.views.view;

public interface LoginView extends ProgressView
{
    void initUser(String user);

    void initPwd(String pwd);

    void changeAvatar(String path);

    void close();
}
