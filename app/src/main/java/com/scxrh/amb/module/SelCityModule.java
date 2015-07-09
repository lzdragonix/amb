package com.scxrh.amb.module;

import com.scxrh.amb.view.iview.SelCityView;

import dagger.Module;
import dagger.Provides;

@Module
public class SelCityModule
{
    private SelCityView view;

    public SelCityModule(SelCityView view)
    {
        this.view = view;
    }

    @Provides
    public SelCityView provideView()
    {
        return view;
    }
}
