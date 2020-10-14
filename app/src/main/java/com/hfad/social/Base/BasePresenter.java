package com.hfad.social.Base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public class BasePresenter<T extends BaseView & MvpView> extends MvpBasePresenter<T> {
    protected Context context;
    public BasePresenter(Context context){
       this.context=context;
    }
    public boolean checkInternetConnection(){
        return checkInternetConnection(null);
    }
    public boolean checkInternetConnection(View view){
        if(!hasInternetConnection()){
            if(isViewAttached()){
                if(view!=null){
                    getView().showSnackBar(view,"No Internet Connection");
                }else{
                    getView().showSnackBar("No Internet Connection");
                }
            }
        }
        return hasInternetConnection();
    }

    public boolean hasInternetConnection(){
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork=cm.getActiveNetworkInfo();
        return  activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    // Not completed Yet
    public boolean checkAuthorization(){
       if(isViewAttached()){
           getView().startLoginActivity();
       }
       return true;
    }
    protected String getCurrentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }
}
