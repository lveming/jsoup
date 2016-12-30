package com.lm.demo.slow_life.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {
    public static void with(Context context, String imageUrl, ImageView imageView) {
        Picasso.with(context).load(imageUrl).into(imageView);

    }
    public static void withAndPlaveholder(Context context, String imageUrl, int placeholderResId, int errorResId, ImageView imageView) {

        Picasso.with(context).load(imageUrl).placeholder(placeholderResId).error(errorResId).into(imageView);
    }
}