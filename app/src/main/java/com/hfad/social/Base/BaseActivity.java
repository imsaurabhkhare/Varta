package com.hfad.social.Base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.hannesdorfmann.mosby3.mvp.MvpPresenter;
import com.hfad.social.Login.LoginActivity;
import com.hfad.social.MainActivity;
import com.hfad.social.R;

import java.lang.reflect.Field;

abstract public class BaseActivity<V extends BaseView,P extends BasePresenter<V>> extends MvpActivity<V,P> implements BaseView {

    public ActionBar actionBar;
    public ProgressDialog progressDialog;

    protected void onCreate(Bundle saved){
        super.onCreate(saved);
        actionBar=getSupportActionBar();
    }

    @Override
    public void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent,LoginActivity.LOGIN_REQUEST_CODE);
    }

    @Override
    public void showSnackBar(View view, String messageId) {
        Snackbar snackbar=Snackbar.make(view,messageId,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showSnackBar(String messageId) {
        Snackbar.make(findViewById(android.R.id.content),messageId,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showWarningDialog(String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    @Override
    public void showProgress() {
        showProgress("Loading...");
    }

    @Override
    public void hideProgress() {
     if(progressDialog!=null){
         progressDialog.dismiss();
         progressDialog=null;
     }
    }

    @Override
    public void showProgress(String message) {
        hideProgress();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void finishV() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
