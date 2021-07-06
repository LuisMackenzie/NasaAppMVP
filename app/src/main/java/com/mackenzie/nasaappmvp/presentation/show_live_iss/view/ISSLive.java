package com.mackenzie.nasaappmvp.presentation.show_live_iss.view;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.mackenzie.nasaappmvp.Constantes;
import com.mackenzie.nasaappmvp.R;
import com.mackenzie.nasaappmvp.databinding.ActivityIssliveBinding;

public class ISSLive extends YouTubeBaseActivity {

    YouTubePlayerView youtubePlayerView;
    ActivityIssliveBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Objetos del View Binding
        binding = ActivityIssliveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        youtubePlayerView = binding.issliveYoutubePlayer;
        initVideo();

    }

    private void initVideo() {
        youtubePlayerView.initialize(Constantes.YOUTUBE_APIKEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                if (!b){
                    switch (Constantes.TYPE_OF_VIDEO) {
                        case 1:
                            youTubePlayer.loadVideo(Constantes.YOUTUBE_VIDEO_ID_ISSLIVE);
                            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            break;
                        case 2:
                            youTubePlayer.loadVideo(Constantes.YOUTUBE_VIDEO_ID_NASATV);
                            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            break;
                    }
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                showYoutubeDialog();
                // Toast.makeText(ISSLive.this, getString(R.string.youtube_video_load_failed) + youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showYoutubeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_core_youtube_title);
        builder.setMessage(R.string.dialog_core_youtube_message);
        builder.setPositiveButton(R.string.dialog_core_youtube_install, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    // Esta linea abre el google play market directamente
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constantes.YOUTUBE_CORE_MARKET_URL + Constantes.YOUTUBE_CORE_PACKAGE_NAME)));
                } catch (ActivityNotFoundException e) {
                    // Si google play no se encuentra disponible, abrira el navegador
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constantes.YOUTUBE_CORE_BASE_URL + Constantes.YOUTUBE_CORE_PACKAGE_NAME)));
                }

                // Toast.makeText(ISSLive.this, "Instalando youtube...", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.dialog_core_youtube_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

}
