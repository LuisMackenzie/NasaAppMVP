package com.mackenzie.nasaappmvp.presentation.show_apod.presenter;

import androidx.annotation.NonNull;

import com.mackenzie.nasaappmvp.base.BasePresenter;
import com.mackenzie.nasaappmvp.data.cache.ImageCacheImpl;
import com.mackenzie.nasaappmvp.domain.interactor.ApodDetailsInteractor;
import com.mackenzie.nasaappmvp.presentation.show_apod.ApodDetailContract;
import com.mackenzie.nasaappmvp.presentation.show_apod.model.Apod;

public class ApodDetailPresenter extends BasePresenter<ApodDetailContract.View> implements ApodDetailContract.Presenter {

    private ApodDetailContract.View view;
    private ApodDetailsInteractor apodInteractor;
    private ImageCacheImpl cache;

    public ApodDetailPresenter(@NonNull ApodDetailContract.View view ,ApodDetailsInteractor apodInteractor, ImageCacheImpl cache) {
        this.view = view;
        this.apodInteractor = apodInteractor;
        this.cache = cache;
    }

    @Override
    public void fetchApodData() {
        view.hideApod();
        view.showProgressBar();
        apodInteractor.getApodDataFromRemote(new ApodDetailsInteractor.onDetailsFetched() {
            @Override
            public void onSuccess(Apod apodFetchedData) {
                    if(isViewAttached()){
                        view.hideProgressBar();
                        view.showApod();
                        view.showApodDetails(apodFetchedData);
                        cache.saveHdUrl(apodFetchedData.getHdurl());
                    }
            }

            @Override
            public void onFailure() {
                if(isViewAttached()){
                    view.showDataFetchError();
                    view.hideProgressBar();
                }

            }
        });
    }


}
