package com.hfad.social.manager;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.hfad.social.Constants;
import com.hfad.social.intractor.ProfileIntractor;
import com.hfad.social.manager.listener.OnObjectChangedListener;
import com.hfad.social.manager.listener.OnObjectExistListener;
import com.hfad.social.manager.listener.OnProfileCreatedListener;
import com.hfad.social.model.Profile;

public class ProfileManager extends FirebaseManager {
     private static final String TAG=ProfileManager.class.getSimpleName();
     private static ProfileManager instance;
     private Context context;
    private ProfileIntractor profileInteractor;
     public static ProfileManager getInstance(Context context){
         if(instance==null){
         instance=new ProfileManager(context);
         }
         return  instance;
     }
     public ProfileManager(Context context){
         this.context=context;
         profileInteractor=new ProfileIntractor(context);
     }
    public void isProfileExist(String id, final OnObjectExistListener<Profile> onObjectExistListener) {
        profileInteractor.isProfileExit(id,onObjectExistListener);
    }
    public void getProfileSingleValue(String id, final OnObjectChangedListener<Profile> listener) {
        profileInteractor.getProfileSingleValue(id, listener);
    }
    public Profile buildProfile(FirebaseUser user,String url){
        Profile profile=new Profile(user.getUid());
        profile.setEmail(user.getEmail());
        profile.setUsername(user.getDisplayName());
        profile.setPhotoUrl(url != null ? url : user.getPhotoUrl().toString());
        return profile;
    }

    public void createOrUpdateProfile(Profile profile, Uri uri, final OnProfileCreatedListener onProfileCreatedListener){
        if (uri == null) {
            profileInteractor.createOrUpdateProfile(profile, onProfileCreatedListener);
        } else {
            profileInteractor.createOrUpdateProfileWithImage(profile, uri, onProfileCreatedListener);
        }
    }

}
