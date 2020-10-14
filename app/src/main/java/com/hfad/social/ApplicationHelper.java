package com.hfad.social;

import com.hfad.social.manager.DatabaseConnector;

public class ApplicationHelper {
    private static final String TAG = ApplicationHelper.class.getSimpleName();
    private static DatabaseConnector databaseHelper;

    public static DatabaseConnector getDatabaseHelper() {
        return databaseHelper;
    }

    public static void initDatabaseHelper(android.app.Application application) {
        databaseHelper = DatabaseConnector.getInstance(application);
        databaseHelper.init();
    }

}
