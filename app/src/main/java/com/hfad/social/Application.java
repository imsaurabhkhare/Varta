package com.hfad.social;

public class Application extends android.app.Application {
    public static final String TAG = Application.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        ApplicationHelper.initDatabaseHelper(this);
       // PostInteractor.getInstance(this).subscribeToNewPosts();
    }
}
