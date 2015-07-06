package com.scxrh.amb.module;

import com.scxrh.amb.view.iview.MainView;

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
