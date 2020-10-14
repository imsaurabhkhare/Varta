package com.hfad.social.Login;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.hfad.social.Base.BasePresenter;
import com.hfad.social.Constants;
import com.hfad.social.R;
import com.hfad.social.intractor.ProfileIntractor;
import com.hfad.social.manager.ProfileManager;

import java.net.URI;
import java.net.URL;
import java.util.concurrent.Executor;

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(Context context) {
        super(context);
    }

    public void onGoogleSignInClick(){
        if(checkInternetConnection()){
           if(isViewAttached())
               getView().signInWithGoogle();
        }
    }
    public void handleGoogleSignInResult(GoogleSignInAccount account){

        ifViewAttached(view -> {
                view.showProgress();
            //    Log.d(TAG,"this page");


                view.setProfilePhotoUrl(buildGooglePhotoUrl(account.getPhotoUrl()));

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                view.firebaseAuthWithCredentials(credential);
                Toast.makeText(context,"sign success",Toast.LENGTH_SHORT).show();

           //     LogUtil.logDebug(TAG, "firebaseAuthWithGoogle:" + account.getId());

            });
    }
    public String buildGooglePhotoUrl(Uri url){
         return String.format(context.getString(R.string.google_large_image_url_pattern),url, Constants.Profile.MAX_AVATAR_SIZE);
    }

    public void handleAuthError(Task<AuthResult> task){
         Exception exception=task.getException();
         ifViewAttached(view -> {
             if(exception!=null){
                 view.showWarningDialog(exception.getMessage());
             }else{
               view.showSnackBar("Authentication Failed");
             }
             view.hideProgress();
         });
    }

    public void checkIsProfileExist(String uid) {
          ProfileManager.getInstance(context).isProfileExist(uid,exist -> {
           ifViewAttached(view -> {
               if(!exist) {
                   view.startCreateProfileActivity();
               }else{
                  // PreferencesUtil.setProfileCreated(context, true);
                   ProfileIntractor.getInstance(context.getApplicationContext())
                           .addRegistrationToken(FirebaseInstanceId.getInstance().getToken(), uid);
                   view.finishV();
               }
               //view.hideProgress();

           });
          });


    }
}
