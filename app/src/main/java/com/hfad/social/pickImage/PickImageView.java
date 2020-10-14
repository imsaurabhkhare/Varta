package com.hfad.social.pickImage;

import android.net.Uri;

import com.hfad.social.Base.BaseView;

public interface PickImageView extends BaseView {
    void loadImageToImageView(Uri imageUri);
}
