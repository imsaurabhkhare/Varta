package com.hfad.social.manager.listener;

public interface OnObjectChangedListener<T> {
    void onObjectChanged(T obj);

    void onError(String errorText);
}
