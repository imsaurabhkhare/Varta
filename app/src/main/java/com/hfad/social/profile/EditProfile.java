package com.hfad.social.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hfad.social.Base.BaseActivity;
import com.hfad.social.MainActivity;
import com.hfad.social.R;
import com.hfad.social.pickImage.PickImageActivity;
import com.hfad.social.utils.GlideApp;
import com.hfad.social.utils.ImageUtils;

public class EditProfile<V extends EditView,P extends EditPresentor<V>> extends PickImageActivity<V,P> implements EditView {

    // UI
    private EditText nameEditText;
    protected ImageView imageView;
    private ProgressBar avatarProgressBar;


    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_edit_profile);
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        nameEditText=findViewById(R.id.name);
        imageView=findViewById(R.id.imageDp);
        avatarProgressBar=findViewById(R.id.progressBar);
        initContent();
    }
    protected void initContent() {
        presenter.loadProfile();
    }

    @Override
    protected ProgressBar getProgressView() {
        return avatarProgressBar;
    }

    @Override
    protected ImageView getImageView() {
        return imageView;
    }

    @Override
    protected void onImagePikedAction() {
       startImageCropActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleCropImageResult(requestCode,resultCode,data);
    }

    @NonNull
    @Override
    public P createPresenter() {
        if(presenter==null){
            return (P) new EditPresentor(this);
        }
        return presenter;
    }

    @Override
    public void setNameError(String string) {
        nameEditText.setError(string);
        nameEditText.requestFocus();
    }

    @Override
    public String getNameText() {
        return nameEditText.getText().toString();
    }

    @Override
    public void setProfilePhoto(String url) {
        ImageUtils.loadImage(GlideApp.with(this), url, imageView, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                avatarProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                avatarProgressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }

    @Override
    public void setName(String username) {
        nameEditText.setText(username);
    }

    @Override
    public void onProfileCreated() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile, menu);
        return true;
    }
}