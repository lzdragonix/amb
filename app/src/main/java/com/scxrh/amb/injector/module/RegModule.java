package com.scxrh.amb.injector.module;

import com.scxrh.amb.views.view.RegView;

import dagger.Module;
import dagger.Provides;

@Module
public class RegModule
{
    private RegView view;

    public RegModule(RegView view)
    {
        this.view = view;
    }

    @Provides
    public RegView provideView()
    {
        return view;
    }
}
