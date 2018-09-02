package com.example.sumeetharyani.accomplisher;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;

public class MotivationVerticalViewPagerAdapter extends PagerAdapter {
    String mResources[] = {"Spider-Man is a fictional superhero appearing in American comic books published by Marvel Comics. The character was created by writer-editor Stan Lee and writer-artist Steve Ditko, and first appeared in the anthology comic book Amazing Fantasy #15 (Aug. 1962) in the Silver Age of Comic Books. Lee and Ditko conceived the character as an orphan being raised by his Aunt May and Uncle Ben, and as a teenager, having to deal with the normal struggles of adolescence in addition to those of a costumed crime-fighter. Spider-Man's creators gave him super strength and agility, the ability to cling to most surfaces, shoot spider-webs using wrist-mounted devices of his own invention, which he calls \"web-shooters\", and react to danger quickly with his \"spider-sense\", enabling him to combat his foes.",
            "Iron Man is a 2008 American superhero film based on the Marvel Comics character of the same name, produced by Marvel Studios and distributed by Paramount Pictures.1 It is the first film in the Marvel Cinematic Universe. The film was directed by Jon Favreau, with a screenplay by Mark Fergus & Hawk Ostby and Art Marcum & Matt Holloway. It stars Robert Downey Jr., Terrence Howard, Jeff Bridges, Shaun Toub and Gwyneth Paltrow. In Iron Man, Tony Stark, an industrialist and master engineer, builds a powered exoskeleton and becomes the technologically advanced superhero Iron Man.\n" +
                    "\n"};
    String[] imagesUrl = {
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_1.png?alt=media&token=8ef79255-f627-46d1-aa84-2c54407c5630",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_10.png?alt=media&token=86e33486-ea85-4382-b65a-2748f58e226e",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_11.png?alt=media&token=83f6230a-dd01-4091-907b-3f0bfc7aa331",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_12.png?alt=media&token=cc81a453-d2d1-4e8b-8a3c-67a1b2ba2906",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_13.png?alt=media&token=d3d3360d-1067-45e8-84b7-065add46b0ea",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_14.png?alt=media&token=1fc832ef-afcc-4aad-b196-f40d43f02b17",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_16.png?alt=media&token=41427833-b824-4507-9f26-226b6b04054d",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_18.png?alt=media&token=2ffa9c94-02c9-4698-b37e-e106542a0a56",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_19.png?alt=media&token=c7e2e922-0207-4f6c-bcdb-0031df88356c",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_2.png?alt=media&token=03518126-b1cb-47c2-95d6-6ed8add33c10",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_20.png?alt=media&token=00a90f08-b934-497d-bbae-795019b0c021",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_21.png?alt=media&token=916ebe8e-6b95-4405-bd37-8f4bca386abf",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_22.png?alt=media&token=dbc0ba68-70a5-41ee-a63e-95aea0a7a92e",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_23.png?alt=media&token=3c49529c-e2ab-427c-92f4-6a1dfce7406c",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_24.png?alt=media&token=ef33590a-5e50-4a93-9a6d-d6e1ba5020c0",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_3.png?alt=media&token=08555adc-93a5-4d13-bc43-06ed7d480d77",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_4.png?alt=media&token=93405fd9-a40c-4e52-9231-654b80d55e62",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_5.png?alt=media&token=781c93d6-3994-4493-b972-87e849cd8a9f",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_7.png?alt=media&token=634f5818-8223-423f-8f8c-f410b484fb22",
            "https://firebasestorage.googleapis.com/v0/b/accomplisher-7cdf5.appspot.com/o/IMG_9.png?alt=media&token=84a38f7b-7f7c-4a54-bdc9-7ca2468994c4"
    };
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
        wallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(mContext)
                        .asBitmap()
                        .load(imagesUrl[position])
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
                .load(imagesUrl[position])
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
