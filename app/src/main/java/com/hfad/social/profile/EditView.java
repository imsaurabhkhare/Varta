package com.hfad.social.profile;

import com.hfad.social.Base.BaseView;
import com.hfad.social.pickImage.PickImageView;

public interface EditView extends PickImageView {
    void setNameError(String string);
    String getNameText();
    void setProfilePhoto(String url);
    void setName(String username);
    void onProfileCreated();
}
