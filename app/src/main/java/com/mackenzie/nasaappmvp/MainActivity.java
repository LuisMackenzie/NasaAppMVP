package com.mackenzie.nasaappmvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mackenzie.nasaappmvp.databinding.ActivityMainOptionsBinding;
import com.mackenzie.nasaappmvp.presentation.show_apod.view.ApodDetail;
import com.mackenzie.nasaappmvp.presentation.show_live_iss.view.ISSLive;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainOptionsBinding binding;
    private LinearLayout apodAction, issAction, tvAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Esto quita el titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Esto esconde la barra de tareas del sistema
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Objetos del View Binding
        binding = ActivityMainOptionsBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());
        View view = binding.getRoot();
        setContentView(view);
        setUpElements();
    }

    private void setUpElements() {
        apodAction = binding.apodAction;
        issAction = binding.issAction;
        tvAction = binding.nasaTVAction;
        apodAction.setOnClickListener(this);
        issAction.setOnClickListener(this);
        tvAction.setOnClickListener(this);
    }

    public void apodAction(View view) {
        navigateToActivity(ApodDetail.class);
    }

    public void issAction(View view) {
        navigateToActivity(ISSLive.class);
    }

    private void navigateToActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.apodAction:
                apodAction(v);
                // Toast.makeText(this, "Pulsaste 01", Toast.LENGTH_SHORT).show();
                break;
            case R.id.issAction:
                Constantes.TYPE_OF_VIDEO = 1;
                issAction(v);
                break;
            case R.id.nasaTVAction:
                Constantes.TYPE_OF_VIDEO = 2;
                issAction(v);
                // Toast.makeText(this, "Pulsaste TV", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}