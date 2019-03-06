package com.example.sumeetharyani.accomplisher.motivation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sumeetharyani.accomplisher.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

public class VideoFragment extends Fragment {
    private final String[] videoUrl = {"h1rA2jMS-6I", "3rHkGy9JfCs",
            "wnHW6o8WMas", "p-4zGxeFZOs", "26U_seo0a1g",
            "dlz-Ne4aqDY",
            "CIvx6q0YhNk",
            "c-75K2F3jUg",
            "xGOqIiwwD_8",
            "xznOQUbFd-o",
            "xEwzDmDeyf4",
            "DwkJww5_7vw",
            "af9qXLrPadA",
            "fFalmesXWMY",
            "Fp5A8CXyS_E",
            "9qNU-lvPXKw",
            "0Bpoh92TQ9g",
            "9zFJx0YOwms",
            "bcdtV98SIQA"
    };


    private YouTubePlayerView youtubePlayerView;
    private YouTubePlayer youTubePlayer;
    private Button btnNext;
    private Button btnPrev;
    private Button btnPlay;
    private int urlIndex = 0;

    public VideoFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View videoView = inflater.inflate(R.layout.fragment_progress, container, false);
        urlIndex = 0;
        btnNext = videoView.findViewById(R.id.button_next);
        btnPrev = videoView.findViewById(R.id.button_prev);
        btnPlay = videoView.findViewById(R.id.button_play);
        youtubePlayerView = videoView.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);
        initializer();
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youTubePlayer != null)
                    youTubePlayer.play();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlIndex++;
                initializer();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urlIndex > 0) {
                    urlIndex--;

                }
                initializer();

            }
        });
        return videoView;
    }

    private void initializer() {
        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoId = videoUrl[urlIndex];
                        initializedYouTubePlayer.cueVideo(videoId, 5);
                    }
                });
                youTubePlayer = initializedYouTubePlayer;

            }
        }, true);

    }
}
