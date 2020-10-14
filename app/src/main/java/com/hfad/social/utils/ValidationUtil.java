package com.hfad.social.utils;

import android.graphics.Rect;

import com.hfad.social.Constants;

public class ValidationUtil {
    public static boolean isNameValid(String name) {
        return name.length() <= Constants.Profile.MAX_NAME_LENGTH;
    }
    public static boolean checkImageMinSize(Rect rect){
        return rect.height()>=Constants.Profile.MIN_AVATAR_SIZE && rect.width()>=Constants.Profile.MIN_AVATAR_SIZE;
    }
}
