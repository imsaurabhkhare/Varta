package com.hfad.social.profile.createProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.hfad.social.R;
import com.hfad.social.profile.EditProfile;

public class CreateProfile extends EditProfile<CreateView,CreatePresentor> implements CreateView{
    public static final String LARGE_IMAGE_URL_EXTRA_KEY = "CreateProfileActivity.LARGE_IMAGE_URL_EXTRA_KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initContent() {
        String largeAvatarURL = getIntent().getStringExtra(LARGE_IMAGE_URL_EXTRA_KEY);
        presenter.buildProfile(largeAvatarURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.create:
                presenter.attempCreateProfile(imageUri);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @NonNull
    @Override
    public CreatePresentor createPresenter() {
        if(presenter==null){
            return new CreatePresentor(this);
        }
        return presenter;
    }
}