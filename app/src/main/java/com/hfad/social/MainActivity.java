package com.hfad.social;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hfad.social.Base.BaseActivity;
import com.hfad.social.Base.BasePresenter;
import com.hfad.social.Base.BaseView;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements  MainView,FirebaseAuth.AuthStateListener {
    private MaterialToolbar tool;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private TextView textView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tool=findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startLoginActivity();
            finish();
        }
        initContentView();

    }

    private void initContentView() {
        if(recyclerView==null){
            progressBar=findViewById(R.id.progressBar);
            swipeRefreshLayout=findViewById(R.id.swipeContainer);
        }
        initFloatingActionButton();
        initPostRecycleView();
    }



    private void initFloatingActionButton(){
        floatingActionButton=findViewById(R.id.addNewPostFab);
        if(floatingActionButton!=null){
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onClickPostClickAction(floatingActionButton);
                }
            });
        }
    }

    private void initPostRecycleView(){
          recyclerView=(RecyclerView)findViewById(R.id.recycleView);

    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            startLoginActivity();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                AuthUI.getInstance().signOut(this);
        }
        return super.onOptionsItemSelected(item);
    }
}