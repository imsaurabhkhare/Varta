package com.hfad.social.utils;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.hfad.social.R;
import com.hfad.social.enums.UploadImagePrefix;

import java.util.Date;

public class ImageUtils {
    public static final String TAG= ImageUtils.class.getSimpleName();

    public static String generateImageTitle(UploadImagePrefix prefix,String parentId){
        if(parentId!=null){
            return parentId.toString()+parentId;
        }
        return prefix.toString() + new Date().getTime();
    }
    public static String generatePostImageTitle(String parentId) {
        return generateImageTitle(UploadImagePrefix.POST, parentId) + "_" + new Date().getTime();
    }
    public static void loadImage(GlideRequests glideRequests, String url, ImageView imageView,
                                 RequestListener<Drawable> listener) {
        glideRequests.load(url)
                .error(R.drawable.ic_stub)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);
    }
    public static void loadLocalImage(GlideRequests glideRequests, Uri uri, ImageView imageView,
                                      RequestListener<Drawable> listener) {
        glideRequests.load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .listener(listener)
                .into(imageView);
    }


}
