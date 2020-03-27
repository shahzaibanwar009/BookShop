package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ProgressBar objProgressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private TextView headerEmail;
    NavigationView objectNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        objProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (objProgressBar != null) {
            objProgressBar.setVisibility(View.GONE);
        }
        // call oncreate initialization method
        firebaseAuthenticatoin();
        navigationViewInitialization();
    }
    public void navigationViewInitialization()
    {

        objectNavigationView = findViewById(R.id.navigationView);

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        objectNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId() == R.id.item_logout)
                {
                    Toast.makeText(MainActivity.this, "You are Logged out", Toast.LENGTH_LONG).show();
                    signOut();
                    return true;
                }
               else if(item.getItemId() == R.id.item_auther)
               {
                   startActivity(new Intent(MainActivity.this, CreateAuther.class));
                   return true;
               }
               else if(item.getItemId() == R.id.item_auther_list)
               {
                   startActivity(new Intent(MainActivity.this, AuthersActivity.class));
                   return true;
               }
               else if(item.getItemId() == R.id.item_books)
               {
                   startActivity(new Intent(MainActivity.this, BooksActivity.class));
                   return true;
               }
                return false;
            }
        });
    }
    public void firebaseAuthenticatoin(){
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, FirebaseLoginActivity.class));
                    finish();
                }
            }
        };
    }

    public void showSuccessToast(View view)
    {
        startActivity(new Intent(MainActivity.this, FirebaseLoginActivity.class));

        Toast objectToast = new Toast(this); // making object of Toast // this is used for showing toast in current activity

        objectToast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        objectToast.setDuration(Toast.LENGTH_LONG);

        // layout inflator
        LayoutInflater objectInfloator = getLayoutInflater();

        View convertedView = objectInfloator.inflate(R.layout.success_toast_layout, (ViewGroup) findViewById(R.id.success_toast_layout), false);

        objectToast.setView(convertedView);
        objectToast.show();
    }
    public void showErrorToast(View view)
    {
        Toast objectToast = new Toast(this); // making object of Toast // this is used for showing toast in current activity

        objectToast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
        objectToast.setDuration(Toast.LENGTH_LONG);

        // layout inflator
        LayoutInflater objectInfloator = getLayoutInflater();

        View convertedView = objectInfloator.inflate(R.layout.error_toast_layout, (ViewGroup) findViewById(R.id.error_toast_layout), false);

        objectToast.setView(convertedView);
        objectToast.show();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        objProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }
}
