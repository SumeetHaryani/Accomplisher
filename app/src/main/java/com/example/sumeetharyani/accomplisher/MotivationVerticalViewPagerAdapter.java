package com.example.sumeetharyani.accomplisher;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static android.support.constraint.Constraints.TAG;

public class MotivationVerticalViewPagerAdapter extends PagerAdapter {


    Context mContext;
    LayoutInflater mLayoutInflater;
    ImageView imageView;
    Button wallpaperButton;
    // Random random=new Random();

    public MotivationVerticalViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return 20;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.motivation_content, container, false);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        wallpaperButton = itemView.findViewById(R.id.btn_wallpaper);
        //  final StorageReference pathReference = storageReference.child("images/IMG_1.png");
        final StorageReference pathReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://accomplisher-7cdf5.appspot.com/IMG_" + (position + 1) + ".png");


        wallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mContext)
                        .asBitmap()
                        .load(pathReference)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                try {
                                    WallpaperManager.getInstance(mContext).setBitmap(resource);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });

        Glide.with(mContext)
                .load(pathReference)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_img))
                .into(imageView);


        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
