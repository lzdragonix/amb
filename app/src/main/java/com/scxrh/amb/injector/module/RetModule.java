package com.scxrh.amb.injector.module;

import com.scxrh.amb.views.view.RetView;

import dagger.Module;
import dagger.Provides;

@Module
public class RetModule
{
    private RetView view;

    public RetModule(RetView view)
    {
        this.view = view;
    }

    @Provides
    public RetView provideView()
    {
        return view;
    }
}
