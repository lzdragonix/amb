package com.scxrh.amb.module;

import com.scxrh.amb.view.iview.LoginView;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule
{
    private LoginView view;

    public LoginModule(LoginView view)
    {
        this.view = view;
    }

    @Provides
    public LoginView provideView()
    {
        return view;
    }
}
