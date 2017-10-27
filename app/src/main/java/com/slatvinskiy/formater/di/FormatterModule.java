package com.slatvinskiy.formater.di;

import com.slatvinskiy.formater.ui.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vlad on 27.10.2017.
 */

@Module
public class FormatterModule {

    @Provides
    public MainPresenter providePresenter(){
        MainPresenter presenter = new MainPresenter();
        return presenter;
    }
}
