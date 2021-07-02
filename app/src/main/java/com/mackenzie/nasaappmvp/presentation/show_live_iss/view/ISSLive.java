package com.mackenzie.nasaappmvp.presentation.show_live_iss.view;

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
                Toast.makeText(ISSLive.this, getString(R.string.youtube_video_load_failed) + youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
