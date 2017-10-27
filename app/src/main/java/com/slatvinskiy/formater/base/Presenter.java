package com.slatvinskiy.formater.base;

/**
 * Created by Vlad on 27.10.2017.
 */

public interface Presenter <V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}

