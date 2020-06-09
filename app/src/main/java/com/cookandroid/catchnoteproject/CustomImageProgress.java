package com.cookandroid.catchnoteproject;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ImageView;


public class CustomImageProgress extends Dialog {

    Activity mContext;
    ImageView imageView;
    AnimationDrawable animationDrawable;
    ColorDrawable colorDrawable;

    public CustomImageProgress(Activity activity, int themeResId) {
        super(activity, themeResId);
        mContext = activity;
        setLayoutView();
    }

    public void setLayoutView(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        colorDrawable.setAlpha(60);
        getWindow().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.custom_image_progress);
        imageView = (ImageView)findViewById(R.id.progresss);
        imageView.setBackgroundResource(R.drawable.progress_drawable_images);
        animationDrawable = (AnimationDrawable)imageView.getBackground();
        animationDrawable.start();
    }
}
