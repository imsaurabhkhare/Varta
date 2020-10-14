package com.hfad.social.profile.createProfile;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hfad.social.profile.EditPresentor;
import com.hfad.social.profile.EditView;

public class CreatePresentor extends EditPresentor<CreateView> {
    public CreatePresentor(Context context) {
        super(context);
    }
   public void buildProfile(String url){
       FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
       profile=profileManager.buildProfile(user,url);
       ifViewAttached(view -> {
           view.setName(profile.getUsername());
           if(profile.getPhotoUrl()!=null){
               view.setProfilePhoto(profile.getPhotoUrl());
           }else{
            //   view.hideLocalProgress();
             //  view.setDefaultProfilePhoto();
           }
       });
   }
}
