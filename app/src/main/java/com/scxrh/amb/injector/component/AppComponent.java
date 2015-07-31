package com.scxrh.amb.injector.component;

import com.scxrh.amb.App;
import com.scxrh.amb.common.RxBus;
import com.scxrh.amb.injector.module.AppModule;
import com.scxrh.amb.manager.MessageManager;
import com.scxrh.amb.manager.SettingsManager;
import com.scxrh.amb.manager.WindowManager;
import com.scxrh.amb.model.AppInfo;
import com.scxrh.amb.rest.RestClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent
{
    App getApp();

    MessageManager getMessageManager();

    SettingsManager getSettingsManager();

    RxBus getRxBus();

    RestClient getRestClient();

    AppInfo getSysInfo();

    WindowManager getWindowManager();
}
