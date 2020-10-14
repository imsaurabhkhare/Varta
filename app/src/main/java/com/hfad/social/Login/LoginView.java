package com.hfad.social.Login;

import com.google.firebase.auth.AuthCredential;
import com.hfad.social.Base.BaseView;

public interface LoginView extends BaseView {
    void signInWithGoogle();
    void setProfilePhotoUrl(String url);
    void firebaseAuthWithCredentials(AuthCredential credential);
    void startCreateProfileActivity();
}
