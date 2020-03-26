package com.example.bookshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSuccessToast(View view)
    {
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
}
