package com.hfad.social.Base;

import android.view.View;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface BaseView extends MvpView {
    void startLoginActivity();
    void showSnackBar(View view, String messageId);
    void showSnackBar(String messageId);
    void showWarningDialog(String message);
    void showProgress();
    void hideProgress();
    void showProgress(String message);
    void finishV();
}
