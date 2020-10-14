package com.hfad.social;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hfad.social.Base.BasePresenter;

import java.net.NetworkInterface;

public class MainPresenter extends BasePresenter<MainView> {
    public MainPresenter(Context context){
        super(context);
    }
      public void onClickPostClickAction(View view){
           if(checkInternetConnection(view)){
               if(checkAuthorization()){

               }
           }
      }



}
