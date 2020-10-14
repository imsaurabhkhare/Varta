package com.hfad.social.profile;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.hfad.social.Base.BaseView;
import com.hfad.social.Constants;
import com.hfad.social.R;
import com.hfad.social.manager.ProfileManager;
import com.hfad.social.manager.listener.OnObjectChangedListenerSimple;
import com.hfad.social.model.Profile;
import com.hfad.social.pickImage.PickImagePresentor;
import com.hfad.social.utils.ValidationUtil;

public class EditPresentor<V extends EditView> extends PickImagePresentor<V> {
    protected Profile profile;
    protected ProfileManager profileManager;
    public EditPresentor(Context context) {
        super(context);
        profileManager = ProfileManager.getInstance(context.getApplicationContext());
    }

    public void loadProfile(){
        ifViewAttached(BaseView::showProgress);
        profileManager.getProfileSingleValue(getCurrentUserId(), new OnObjectChangedListenerSimple<Profile>() {
            @Override
            public void onObjectChanged(Profile obj) {
                profile = obj;
                ifViewAttached(view -> {
                    if (profile != null) {
                        view.setName(profile.getUsername());

                        if (profile.getPhotoUrl() != null) {
                            view.setProfilePhoto(profile.getPhotoUrl());
                        }
                    }

                    view.hideProgress();
                    view.setNameError(null);
                });
            }
        });
    }

    public void attempCreateProfile(Uri imageUri) {
      if(checkInternetConnection()){
         ifViewAttached(view -> {
            view.setNameError(null);
            String name=view.getNameText().trim();
             boolean cancel = false;
             if(TextUtils.isEmpty(name)){
                 view.setNameError(context.getString(R.string.error_field_required));
                 cancel=true;
             }else if(!ValidationUtil.isNameValid(name)){
                 view.setNameError(context.getString(R.string.error_profile_name_length));
                 cancel=true;
             }
             Log.d("EditPresentor ",name+cancel);
             if(!cancel){
               view.showProgress();
                 profile.setUsername(name);
                 createOrUpdateProfile(imageUri);
             }
         });
      }
    }

    public void createOrUpdateProfile(Uri imageUri){
        profileManager.createOrUpdateProfile(profile,imageUri,success ->{
          ifViewAttached(view -> {
              view.hideProgress();
              if(success){
                 view.onProfileCreated();
              }else{
                  view.showSnackBar("Fail to Create Profile");
              }
          });
        });
    }
    public void onProfileUpdateSuccess(){
        ifViewAttached(BaseView::finishV);
      // return;
    }
}
