package com.hfad.social.manager;

import android.content.Context;

import com.google.firebase.database.ValueEventListener;
import com.hfad.social.ApplicationHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {
    Map<Context, List<ValueEventListener>> activeListeners = new HashMap<>();

    void addListenerToMap(Context context, ValueEventListener valueEventListener) {
        if (activeListeners.containsKey(context)) {
            activeListeners.get(context).add(valueEventListener);
        } else {
            List<ValueEventListener> valueEventListeners = new ArrayList<>();
            valueEventListeners.add(valueEventListener);
            activeListeners.put(context, valueEventListeners);
        }
    }

    public void closeListeners(Context context) {
        DatabaseConnector databaseConnector = ApplicationHelper.getDatabaseHelper();
        if (activeListeners.containsKey(context)) {
            for (ValueEventListener listener : activeListeners.get(context)) {
                databaseConnector.closeListener(listener);
            }
            activeListeners.remove(context);
        }
    }
}
