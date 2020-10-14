package com.hfad.social.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.hfad.social.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class GoogleApiHelper  {
    public static GoogleApiClient createGoogleApiClient(FragmentActivity fragmentActivity){
       GoogleApiClient.OnConnectionFailedListener failedListener;
       if(fragmentActivity instanceof GoogleApiClient.OnConnectionFailedListener){
           failedListener=(GoogleApiClient.OnConnectionFailedListener)fragmentActivity;
       }else{
           throw new IllegalArgumentException(fragmentActivity.getClass().getSimpleName()+" Should implement onConnectionFailedListener");
       }
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(fragmentActivity.getResources().getString(R.string.default_web_client_id))
                .requestEmail().build();
       return new GoogleApiClient.Builder(fragmentActivity)
               .enableAutoManage(fragmentActivity,failedListener)
               .addApi(Auth.GOOGLE_SIGN_IN_API)
               .build();
    }

    public static GoogleSignInClient createGoogleSignClient(FragmentActivity fragmentActivity){
        GoogleSignInClient gs;
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(String.valueOf(R.string.google_web_client_id))
                .requestEmail().build();
        gs= GoogleSignIn.getClient(fragmentActivity,gso);
        return gs;
    }
}
