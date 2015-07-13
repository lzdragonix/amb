package com.scxrh.amb.injector.module;

import com.scxrh.amb.views.view.MvpView;

import dagger.Module;
import dagger.Provides;

@Module
public class MvpModule
{
    private MvpView view;

    public MvpModule(MvpView view)
    {
        this.view = view;
    }

    @Provides
    public MvpView provideView()
    {
        return view;
    }
}
