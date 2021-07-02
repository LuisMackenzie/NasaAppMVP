package com.mackenzie.nasaappmvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mackenzie.nasaappmvp.databinding.ActivityApodDetailBinding;


public abstract class BaseActivity<Presenter extends BasePresenter> extends AppCompatActivity {

    protected Presenter presenter;
    protected ActivityApodDetailBinding binding;

    @NonNull
    protected abstract Presenter createPresenter(@NonNull final Context context);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Objetos del View Binding
        binding = ActivityApodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // setContentView(getLayout());
        // ButterKnife.bind(this);
        presenter = createPresenter(this);

    }

    /*public abstract
    @LayoutRes
    int getLayout();*/
}
