package com.scxrh.amb.module;

import com.scxrh.amb.view.iview.RegView;

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
