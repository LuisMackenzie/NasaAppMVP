package com.mackenzie.nasaappmvp.base;

import com.mackenzie.nasaappmvp.presentation.show_apod.ApodDetailContract;

public interface Presenter<V extends ApodDetailContract.View> {

    void attachView(V view);

    void detachView();
}