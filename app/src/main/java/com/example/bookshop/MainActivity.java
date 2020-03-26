package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ProgressBar objProgressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

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
}
