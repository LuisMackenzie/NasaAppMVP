package com.mackenzie.nasaappmvp.presentation.show_expanded_apod_image.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.mackenzie.nasaappmvp.R;
import com.mackenzie.nasaappmvp.databinding.ActivityApodDetailBinding;
import com.mackenzie.nasaappmvp.databinding.ActivityExpandApodDetailsBinding;
import com.mackenzie.nasaappmvp.presentation.show_expanded_apod_image.ExpandApodImageContract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExpandApodImage extends AppCompatActivity implements ExpandApodImageContract.View, View.OnClickListener {

    ProgressBar progressLoadingFullImage;
    PhotoView imageviewApoddetail;
    ImageView imageviewApoddetailReload;

    TextView textviewApoddetailFetcherror;
    private ActivityExpandApodDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Objetos del View Binding
        binding = ActivityExpandApodDetailsBinding.inflate(getLayoutInflater());
        setUpElements();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());

        textviewApoddetailFetcherror.bringToFront();
        showFullScreenImage();
    }

    private void setUpElements() {
        progressLoadingFullImage = binding.progressLoadingFullImageDetail;
        imageviewApoddetail = binding.imageviewApoddetailview;
        imageviewApoddetailReload = binding.imageviewApoddetailReloadDetail;
        textviewApoddetailFetcherror = binding.textviewApoddetailFetcherrorDetail;
        imageviewApoddetailReload.setOnClickListener(this);
    }

    @Override
    public void showProgressBar() {
        progressLoadingFullImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressLoadingFullImage.setVisibility(View.GONE);
    }

    @Override
    public void showFullScreenImage() {
        showProgressBar();
        String hdUrl = PreferenceManager.getDefaultSharedPreferences(this).getString("hdurl", "defaultStringIfNothingFound");
        Glide.with(getApplicationContext()).load(hdUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                showFetchError();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
               hideProgressBar();
                return false;
            }
        }).into(imageviewApoddetail);
    }

    @Override
    public void showFetchError() {
        imageviewApoddetailReload.setVisibility(View.VISIBLE);
        textviewApoddetailFetcherror.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_apoddetail_reload:
                imageviewApoddetailReload.setVisibility(View.GONE);
                textviewApoddetailFetcherror.setVisibility(View.GONE);
                break;
        }
    }

}
