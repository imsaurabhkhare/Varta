package com.hfad.social.pickImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hfad.social.Base.BaseActivity;
import com.hfad.social.Constants;
import com.hfad.social.R;
import com.hfad.social.utils.GlideApp;
import com.hfad.social.utils.ImageUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public abstract class PickImageActivity<V extends PickImageView, P extends PickImagePresentor<V>> extends BaseActivity<V,P> implements PickImageView {

    private static final String SAVED_STATE_IMAGE_URI = "RegistrationActivity.SAVED_STATE_IMAGE_URI";

    protected Uri imageUri;

    protected abstract ProgressBar getProgressView();

    protected abstract ImageView getImageView();

    protected abstract void onImagePikedAction();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_STATE_IMAGE_URI,imageUri);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(SAVED_STATE_IMAGE_URI)){
                imageUri = savedInstanceState.getParcelable(SAVED_STATE_IMAGE_URI);
                loadImageToImageView(imageUri);
            }
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void loadImageToImageView(Uri imageUri) {
        if(imageUri==null){
            return;
        }
        this.imageUri = imageUri;
        ImageUtils.loadLocalImage(GlideApp.with(this), imageUri, getImageView(), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                getProgressView().setVisibility(View.GONE);
                return false;
            }
        });

    }

    @NonNull
    @Override
    public P createPresenter() {
        return null;
    }

    protected void startImageCropActivity(){
        if(imageUri==null)
            return;
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setFixAspectRatio(true)
                .setMinCropResultSize(Constants.Profile.MIN_AVATAR_SIZE,Constants.Profile.MIN_AVATAR_SIZE)
                .setRequestedSize(Constants.Profile.MAX_AVATAR_SIZE,Constants.Profile.MAX_AVATAR_SIZE)
                .start(this);
    }
    protected void handleCropImageResult(int requestCode, int resultCode, Intent data) {
        presenter.handleCropImageResult(requestCode, resultCode, data);
    }

}