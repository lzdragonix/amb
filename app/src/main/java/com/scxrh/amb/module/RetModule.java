package com.scxrh.amb.module;

import com.scxrh.amb.view.iview.RetView;

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
