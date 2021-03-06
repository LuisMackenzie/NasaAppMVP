package com.mackenzie.nasaappmvp.base;

import com.mackenzie.nasaappmvp.presentation.show_apod.ApodDetailContract;

public abstract class BasePresenter<T extends ApodDetailContract.View> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }
}
