package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAuther extends AppCompatActivity {

    private final String COLLECTION_NAME = "authers";
    private EditText documentIdET,nameET,cityET, genderET;

    //Step 1 - Create Firebase Firestore object
    private FirebaseFirestore objectFirebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_auther);

        initializeFirebaseFirestoreObject();
        connectJavaViewsWithXMLViews();
    }

    private void initializeFirebaseFirestoreObject()
    {
        //Step 2 - initialize Firebase Firestore object
        objectFirebaseFirestore= FirebaseFirestore.getInstance();
    }

    public void addAuther(View view)
    {
        if(!cityET.getText().toString().isEmpty()
                && !nameET.getText().toString().isEmpty()
                && !genderET.getText().toString().isEmpty()
        )
        {
            //Step 3 -- Creating Map to store values
            Map<String,Object> objectMap=new HashMap<>();

            objectMap.put("name",nameET.getText().toString());
            objectMap.put("city",cityET.getText().toString());
            objectMap.put("gender",genderET.getText().toString());

            String id = objectFirebaseFirestore.collection(COLLECTION_NAME).document().getId();

            //Step 4- add objectMap to Firebase Firestore
            objectFirebaseFirestore.collection(COLLECTION_NAME)
//                    .document(id)
                    .add(objectMap)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast objectToast=Toast.makeText(CreateAuther.this,
                                    "Auther added successfully", Toast.LENGTH_SHORT);

                            objectToast.setGravity(Gravity.TOP,0,0);
                            objectToast.show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast objectToast=Toast.makeText(CreateAuther.this,
                                    "Fails to add values:"+
                                            e.getMessage(), Toast.LENGTH_SHORT);

                            objectToast.setGravity(Gravity.TOP,0,0);
                            objectToast.show();
                        }
                    });
        }
        else if(cityET.getText().toString().isEmpty())
        {
            Toast objectToast=Toast.makeText(this,
                    "Please enter City", Toast.LENGTH_SHORT);

            objectToast.setGravity(Gravity.TOP,0,0);
            objectToast.show();

        }
        else if(nameET.getText().toString().isEmpty())
        {
            Toast objectToast=Toast.makeText(this,
                    "Please enter name", Toast.LENGTH_SHORT);

            objectToast.setGravity(Gravity.TOP,0,0);
            objectToast.show();
        }
        else if(genderET.getText().toString().isEmpty())
        {
            Toast objectToast=Toast.makeText(this,
                    "Please enter Gender", Toast.LENGTH_SHORT);

            objectToast.setGravity(Gravity.TOP,0,0);
            objectToast.show();
        }
    }


    private void connectJavaViewsWithXMLViews()
    {
        nameET = findViewById(R.id.auther_name);
        cityET = findViewById(R.id.auther_city);
        genderET = findViewById(R.id.auther_gender);
    }

    public void goToHome(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }
}
