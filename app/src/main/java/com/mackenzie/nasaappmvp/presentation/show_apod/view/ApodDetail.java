package com.mackenzie.nasaappmvp.presentation.show_apod.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mackenzie.nasaappmvp.R;
import com.mackenzie.nasaappmvp.base.BaseActivity;
import com.mackenzie.nasaappmvp.data.cache.ImageCacheImpl;
import com.mackenzie.nasaappmvp.databinding.ActivityApodDetailBinding;
import com.mackenzie.nasaappmvp.domain.interactor.ApodDetailsInteractor;
import com.mackenzie.nasaappmvp.presentation.show_apod.ApodDetailContract;
import com.mackenzie.nasaappmvp.presentation.show_apod.model.Apod;
import com.mackenzie.nasaappmvp.presentation.show_apod.presenter.ApodDetailPresenter;
import com.mackenzie.nasaappmvp.presentation.show_expanded_apod_image.view.ExpandApodImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApodDetail extends BaseActivity<ApodDetailPresenter> implements ApodDetailContract.View, View.OnClickListener {

    private ProgressBar progressbarApoddetailLoading;
    private TextView textviewApoddetailFetcherror;
    private ImageView imageviewApoddetailCoverimage;
    private TextView textviewApoddetailTitle;
    private TextView textviewApoddetailApoddesc;
    private TextView tvApodDetailDate;
    // private TextView tvApodDetailCredit;
    private ImageView imageviewApoddetailReload;
    // private Button btnTest;

    private ProgressBar progressbarApoddetailImage;

    @NonNull
    @Override
    protected ApodDetailPresenter createPresenter(@NonNull Context context) {
        return new ApodDetailPresenter(this , new ApodDetailsInteractor(), new ImageCacheImpl(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Objetos del View Binding
        // binding = ActivityApodDetailBinding.inflate(getLayoutInflater());
        setUpElements();


        fetchApodDetails();
        imageviewApoddetailReload.bringToFront();
        presenter.attachView(this);
    }

    private void setUpElements() {
        progressbarApoddetailLoading = binding.progressbarApoddetailLoading;
        progressbarApoddetailImage = binding.progressbarApoddetailImage;

        textviewApoddetailFetcherror = binding.textviewApoddetailFetcherror;
        textviewApoddetailTitle = binding.textviewApoddetailTitle;
        textviewApoddetailApoddesc = binding.textviewApoddetailApoddesc;
        tvApodDetailDate = binding.textviewApoddetailDate;
        // tvApodDetailCredit = binding.textviewApoddetailCredits;

        imageviewApoddetailCoverimage = binding.imageviewApoddetailCoverimage;
        imageviewApoddetailReload = binding.imageviewApoddetailReload;

        imageviewApoddetailCoverimage.setOnClickListener(this);
        imageviewApoddetailReload.setOnClickListener(this);
    }

    /*@Override
    public int getLayout() {
        return R.layout.activity_apod_detail;
        // View view = binding.getRoot();
        // return layoutId;
    }*/

    @Override
    public void showProgressBar() {
        progressbarApoddetailLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbarApoddetailLoading.setVisibility(View.GONE);
    }

    @Override
    public void showApodImageProgressBar() {
        progressbarApoddetailImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideApodImageProgressBar() {
        progressbarApoddetailImage.setVisibility(View.GONE);
    }

    @Override
    public void hideApod() {
        textviewApoddetailApoddesc.setVisibility(View.GONE);
        textviewApoddetailTitle.setVisibility(View.GONE);
        tvApodDetailDate.setVisibility(View.GONE);
        // tvApodDetailCredit.setVisibility(View.GONE);
        imageviewApoddetailCoverimage.setVisibility(View.GONE);
    }

    @Override
    public void showApod() {
        textviewApoddetailApoddesc.setVisibility(View.VISIBLE);
        textviewApoddetailTitle.setVisibility(View.VISIBLE);
        tvApodDetailDate.setVisibility(View.VISIBLE);
        // tvApodDetailCredit.setVisibility(View.VISIBLE);
        imageviewApoddetailCoverimage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showApodDetails(Apod apodData) {
        showApodImageProgressBar();
        textviewApoddetailTitle.setText(apodData.getTitle());
        tvApodDetailDate.setText(apodData.getDate());
        if (apodData.getCopyright() == null) {
            textviewApoddetailApoddesc.setText(apodData.getExplanation());
        } else {
            textviewApoddetailApoddesc.setText(apodData.getExplanation() + "\n\nCredits:" + apodData.getCopyright());
        }
        Glide.with(getApplicationContext())
                .load(apodData.getLowresurl())
                .apply(RequestOptions.centerCropTransform())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        hideApodImageProgressBar();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hideApodImageProgressBar();
                        return false;
                    }
                })
                .into(imageviewApoddetailCoverimage);
    }

    @Override
    public void fetchApodDetails() {
        presenter.fetchApodData();
    }

    @Override
    public void showDataFetchError() {
        imageviewApoddetailReload.setVisibility(View.VISIBLE);
        textviewApoddetailFetcherror.setVisibility(View.VISIBLE);
    }

    @Override
    public void reloadData() {
        textviewApoddetailFetcherror.setVisibility(View.GONE);
        imageviewApoddetailReload.setVisibility(View.GONE);
        presenter.fetchApodData();
    }

    @Override
    public void expandApodImage() {
        startActivity(new Intent(this, ExpandApodImage.class));
    }

    /*@OnClick(R.id.imageview_apoddetail_reload)
    public void onReloadImageClicked() {
        reloadData();
    }*/

    /*@OnClick(R.id.imageview_apoddetail_coverimage)
    public void onCoverImageClicked() {
        expandApodImage();
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_apoddetail_reload:
                Toast.makeText(this, "Pulsaste en recargar", Toast.LENGTH_SHORT).show();
                reloadData();
                break;
            case R.id.imageview_apoddetail_coverimage:
                Toast.makeText(this, "Pulsaste Expandir", Toast.LENGTH_SHORT).show();
                expandApodImage();
                break;
        }
    }
}
