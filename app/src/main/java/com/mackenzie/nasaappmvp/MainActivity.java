package com.mackenzie.nasaappmvp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mackenzie.nasaappmvp.databinding.ActivityMainOptionsBinding;
import com.mackenzie.nasaappmvp.presentation.show_apod.view.ApodDetail;
import com.mackenzie.nasaappmvp.presentation.show_live_iss.view.ISSLive;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainOptionsBinding binding;


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
        setContentView(binding.getRoot());
        setUpElements();
    }

    private void setUpElements() {
        binding.apodAction.setOnClickListener(this);
        binding.issAction.setOnClickListener(this);
        binding.nasaTVAction.setOnClickListener(this);
    }

    public void apodAction(View view) {
        navigateToActivity(ApodDetail.class);
    }

    public void issAction() {
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
                break;
            case R.id.issAction:
                showStateISS();
                break;
            case R.id.nasaTVAction:
                Constantes.TYPE_OF_VIDEO = 3;
                issAction();
                break;
        }
    }

    private void showStateISS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_ISS_title);
        builder.setMessage(R.string.dialog_ISS_message);
        builder.setPositiveButton(R.string.dialog_ISS_outdoor, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constantes.TYPE_OF_VIDEO = 2;
                issAction();
            }
        });
        builder.setNegativeButton(R.string.dialog_ISS_indoor, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Constantes.TYPE_OF_VIDEO = 1;
                issAction();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}