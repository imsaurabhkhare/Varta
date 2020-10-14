package com.hfad.social.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hfad.social.Base.BaseActivity;
import com.hfad.social.R;
import com.hfad.social.profile.createProfile.CreateProfile;
import com.hfad.social.utils.GoogleApiHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends BaseActivity<LoginView,LoginPresenter> implements LoginView,FirebaseAuth.AuthStateListener
{

    public static final int LOGIN_REQUEST_CODE = 10001;
    private static final int SIGN_IN_GOOGLE=9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private String photoUrl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(actionBar!=null){
           actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initGoogleSignIn();
    }

    private void initGoogleSignIn() {
        mAuth=FirebaseAuth.getInstance();
        mGoogleSignInClient=GoogleApiHelper.createGoogleSignClient(this);
        findViewById(R.id.googleSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             presenter.onGoogleSignInClick();
            }
        });
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        List<AuthUI.IdpConfig> provider= Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());
        Intent intent=AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .build();
        startActivityForResult(intent,SIGN_IN_GOOGLE);
    }

    @Override
    public void setProfilePhotoUrl(String url) {
      photoUrl=url;
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==SIGN_IN_GOOGLE){
            if(resultCode==RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    presenter.handleGoogleSignInResult(account);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
       //     mCallbackManager.onActivityResult(requestCode, resultCode, data);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        }

    }
    @Override
    public void firebaseAuthWithCredentials(AuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,task -> {
                    if (!task.isSuccessful()) {
                        presenter.handleAuthError(task);
                    }else{
                        Toast.makeText(this,"sign in",Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    public void onStop(){
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
   /*     if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            AuthUI.getInstance().signOut(this);
        }*/
       final FirebaseUser user=firebaseAuth.getCurrentUser();
       if(user!=null){
           Log.d("User id: ",user.getUid());
           presenter.checkIsProfileExist(user.getUid());
       }else{

       }


    }

    @Override
    public void startCreateProfileActivity() {
        Log.d("LoginActivity", "CreateActivity");
        startActivity(new Intent(this, CreateProfile.class));
        finish();
    }
}