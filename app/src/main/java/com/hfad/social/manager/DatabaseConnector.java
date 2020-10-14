package com.hfad.social.manager;

import android.content.Context;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class DatabaseConnector {
    private static final String TAG=DatabaseConnector.class.getSimpleName();
    private static DatabaseConnector instance;
    private Context context;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private Map<ValueEventListener, DatabaseReference> activeListeners = new HashMap<>();
    public static final String POSTS_DB_KEY = "posts";
    public static final String PROFILES_DB_KEY = "profiles";
    public static final String POST_COMMENTS_DB_KEY = "post-comments";
    public static final String POST_LIKES_DB_KEY = "post-likes";
    public static final String FOLLOW_DB_KEY = "follow";
    public static final String FOLLOWINGS_DB_KEY = "followings";
    public static final String FOLLOWINGS_POSTS_DB_KEY = "followingPostsIds";
    public static final String FOLLOWERS_DB_KEY = "followers";
    public static final String IMAGES_STORAGE_KEY = "images";
    public static final String IMAGES_MEDIUM_KEY = "medium";
    public static final String IMAGES_SMALL_KEY = "small";
    public DatabaseConnector(Context context){
        this.context=context;
    }
    public static DatabaseConnector getInstance(Context context){
        instance=new DatabaseConnector(context);
        return  instance;
    }
    public DatabaseReference databaseReference(){
        return database.getReference();
    }
    public void init(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public UploadTask uploadImage(Uri uri,String imageTitle){
        StorageReference reference=getStorageReference().child(IMAGES_STORAGE_KEY+"/"+imageTitle);
        // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCacheControl("max-age=7776000, Expires=7776000, public, must-revalidate")
                .build();

        return reference.putFile(uri, metadata);
    }
    public StorageReference getStorageReference(){
          return firebaseStorage.getReference("gs://social-67bf5.appspot.com");
    }

    public void closeListener(ValueEventListener listener) {
        if (activeListeners.containsKey(listener)) {
            DatabaseReference reference = activeListeners.get(listener);
            reference.removeEventListener(listener);
            activeListeners.remove(listener);
           // LogUtil.logDebug(TAG, "closeListener(), listener was removed: " + listener);
        } else {
           // LogUtil.logDebug(TAG, "closeListener(), listener not found :" + listener);
        }
    }

}
