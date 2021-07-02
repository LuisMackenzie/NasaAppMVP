package com.mackenzie.nasaappmvp.presentation.show_apod;

import com.mackenzie.nasaappmvp.presentation.show_apod.model.Apod;

public interface ApodDetailContract {

    interface View {

        void showProgressBar();

        void hideProgressBar();

        void hideApod();

        void showApod();

        void showApodDetails(Apod apodData);

        void fetchApodDetails();

        void showDataFetchError();

        void reloadData();

        void expandApodImage();

        void showApodImageProgressBar();

        void hideApodImageProgressBar();
    }

    interface Presenter {

        void fetchApodData();
    }


}
