package com.slatvinskiy.formater;

import android.app.Application;
import android.content.Context;

import com.slatvinskiy.formater.di.DaggerIApplicationComponent;
import com.slatvinskiy.formater.di.FormatterModule;
import com.slatvinskiy.formater.di.IApplicationComponent;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by Vlad on 27.10.2017.
 */

public class App extends Application {

    IApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);*/

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = DaggerIApplicationComponent
                .builder()
                .formatterModule(new FormatterModule())
                .build();
    }

    public static IApplicationComponent component(Context context){
        return ((App) context.getApplicationContext()).component;
    }
}
