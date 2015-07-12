package com.scxrh.amb.injector.module;

import com.scxrh.amb.views.view.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule
{
    private MainView view;

    public MainModule(MainView view)
    {
        this.view = view;
    }

    @Provides
    public MainView provideView()
    {
        return view;
    }
}
