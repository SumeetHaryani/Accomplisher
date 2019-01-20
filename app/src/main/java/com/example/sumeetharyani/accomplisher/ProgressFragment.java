package com.example.sumeetharyani.accomplisher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;

public class ProgressFragment extends Fragment {
    String videoUrl[] = {
            "wnHW6o8WMas", "Fp5A8CXyS_E", "p-4zGxeFZOs", "h1rA2jMS-6I", "26U_seo0a1g",
            "dlz-Ne4aqDY",
            "CIvx6q0YhNk",
            "c-75K2F3jUg",
            "xGOqIiwwD_8",
            "xznOQUbFd-o",
            "xEwzDmDeyf4",
            "DwkJww5_7vw",
            "af9qXLrPadA",
            "fFalmesXWMY",
            "3rHkGy9JfCs",
            "9qNU-lvPXKw",
            "0Bpoh92TQ9g",
            "9zFJx0YOwms",
            "bcdtV98SIQA"
    };


    YouTubePlayerView youtubePlayerView;
    Button btnNext;
    Button btnPrev;
    int urlIndex = 0;

    public ProgressFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");


        View videoView = inflater.inflate(R.layout.fragment_progress, container, false);
        urlIndex = 0;
        btnNext = videoView.findViewById(R.id.button_next);
        btnPrev = videoView.findViewById(R.id.button_prev);
        youtubePlayerView = videoView.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);
        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoId = videoUrl[urlIndex];
                        initializedYouTubePlayer.loadVideo(videoId, 5);
                        initializedYouTubePlayer.pause();
                    }
                });
            }
        }, true);
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

    void initializer() {
        youtubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        String videoId = videoUrl[urlIndex];
                        initializedYouTubePlayer.loadVideo(videoId, 5);

                    }
                });
            }
        }, true);
    }
}
