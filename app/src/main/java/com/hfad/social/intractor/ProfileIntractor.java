package com.hfad.social.intractor;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Parcelable;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.UploadTask;
import com.hfad.social.ApplicationHelper;
import com.hfad.social.enums.UploadImagePrefix;
import com.hfad.social.manager.DatabaseConnector;
import com.hfad.social.manager.listener.OnObjectChangedListener;
import com.hfad.social.manager.listener.OnObjectExistListener;
import com.hfad.social.manager.listener.OnProfileCreatedListener;
import com.hfad.social.model.Profile;
import com.hfad.social.utils.ImageUtils;

public class ProfileIntractor {
   private static ProfileIntractor instance;
   private Context context;
    private DatabaseConnector databaseHelper;

   public ProfileIntractor(Context context){
       this.context=context;
       databaseHelper= ApplicationHelper.getDatabaseHelper();
   }
   public static ProfileIntractor getInstance(Context context){
       instance=new ProfileIntractor(context);
       return instance;
   }

   public void createOrUpdateProfile(final Profile profile,final OnProfileCreatedListener onProfileCreatedListener){
       Task<Void> task=databaseHelper.databaseReference()
               .child(DatabaseConnector.PROFILES_DB_KEY)
               .child(profile.getId())
               .setValue(profile)
               .addOnCompleteListener(task1 -> {
                     onProfileCreatedListener.onProfileCreated(task1.isSuccessful());
                     addRegistrationToken(FirebaseInstanceId.getInstance().getToken(),profile.getId());
               });
   }
   public void createOrUpdateProfileWithImage(final Profile profile, Uri ImageUri,final OnProfileCreatedListener onProfileCreatedListener){
       String imageTitle = ImageUtils.generateImageTitle(UploadImagePrefix.PROFILE, profile.getId());
       UploadTask uploadTask=databaseHelper.uploadImage(ImageUri,imageTitle);
       if (uploadTask != null) {
           uploadTask.addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(task1 -> {
                       if (task1.isSuccessful()) {
                           Uri downloadUrl = task1.getResult();
                          // LogUtil.logDebug(TAG, "successful upload image, image url: " + String.valueOf(downloadUrl));

                           profile.setPhotoUrl(downloadUrl.toString());
                           createOrUpdateProfile(profile, onProfileCreatedListener);
                       } else {
                           onProfileCreatedListener.onProfileCreated(false);
                           //LogUtil.logDebug(TAG, "createOrUpdateProfileWithImage, fail to getDownloadUrl");
                       }
                   });
               } else {
                   onProfileCreatedListener.onProfileCreated(false);
                   //LogUtil.logDebug(TAG, "createOrUpdateProfileWithImage, fail to upload image");
               }

           });
       } else {
           onProfileCreatedListener.onProfileCreated(false);
   //        LogUtil.logDebug(TAG, "fail to upload image");
       }
   }
   public void isProfileExit(String id,final OnObjectExistListener<Profile> onObjectExistListener){
       Log.d("ProfileIntractor","getdatabaseReference()");
       DatabaseReference reference=databaseHelper.databaseReference().child("profiles").child(id);
       Log.d("ProfileIntractor",reference.toString());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Log.d("ProfileIntractor",""+dataSnapshot.exists());
                   onObjectExistListener.onDataChanged(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   }
   public void getProfileSingleValue(String id,final OnObjectChangedListener<Profile> listener){
      DatabaseReference reference=databaseHelper.databaseReference().child(DatabaseConnector.PROFILES_DB_KEY).child(id);
       reference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Profile profile = dataSnapshot.getValue(Profile.class);
               listener.onObjectChanged(profile);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
               listener.onError(databaseError.getMessage());
              // LogUtil.logError(TAG, "getProfileSingleValue(), onCancelled", new Exception(databaseError.getMessage()));
           }
       });
   }

   public void addRegistrationToken(String token,String userId){
       Task<Void> task = databaseHelper
               .databaseReference()
               .child(DatabaseConnector.PROFILES_DB_KEY)
               .child(userId).child("notificationTokens")
               .child(token).setValue(true);
    //   task.addOnCompleteListener(task1 -> LogUtil.logDebug(TAG, "addRegistrationToken, success: " + task1.isSuccessful()));
   }
}
