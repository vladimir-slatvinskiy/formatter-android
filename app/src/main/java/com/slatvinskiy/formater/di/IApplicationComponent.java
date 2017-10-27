package com.slatvinskiy.formater.di;

import com.slatvinskiy.formater.ui.MainActivity;
import com.slatvinskiy.formater.ui.MainPresenter;

import dagger.Component;

/**
 * Created by Vlad on 27.10.2017.
 */

@Component(modules = {FormatterModule.class})
public interface IApplicationComponent {
    void inject(MainActivity mainActivity);
    MainPresenter presenter();
}
