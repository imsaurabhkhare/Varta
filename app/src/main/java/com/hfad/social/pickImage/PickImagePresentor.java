package com.hfad.social.pickImage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hfad.social.Base.BasePresenter;
import com.hfad.social.utils.ValidationUtil;
import com.theartofdev.edmodo.cropper.CropImage;

import static android.app.Activity.RESULT_OK;

public class PickImagePresentor<V extends PickImageView> extends BasePresenter<V> {
    public PickImagePresentor(Context context) {
        super(context);
    }
    protected void handleCropImageResult(int requestCode, int resultCode, Intent data){
        ifViewAttached(view -> {
            if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result=CropImage.getActivityResult(data);
                if(requestCode==RESULT_OK){
                   if(ValidationUtil.checkImageMinSize(result.getCropRect())){
                       Uri uri=result.getUri();
                       view.loadImageToImageView(uri);
                   }
                }
            }
        });
    }
}
