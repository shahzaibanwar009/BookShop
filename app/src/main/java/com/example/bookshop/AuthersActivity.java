package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthersActivity extends AppCompatActivity {

    private final String COLLECTION_NAME = "authers";
    private final String EXTRA_MESSAGE = "auther_date";
    //Views
    private TextView mHeaderView;
    private ListView mAutherListView;

    //Firebase
    private FirebaseFirestore db;

    //Adapter
    private AutherAdapter mAutherAdapter;
    private ArrayList<Auther> mAutherList;

    //Step 1 - Create Firebase Firestore object
    private FirebaseFirestore objectFirebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authers);

        mHeaderView = (TextView) findViewById(R.id.autherHeader);
        mAutherListView = (ListView) findViewById(R.id.autherList);

        mHeaderView.setText("Available Authers");

        //get Database
        db = FirebaseFirestore.getInstance();
        //Set up the ArrayList
        mAutherList = new ArrayList<Auther>();
        //set the Adapter
//        mAutherAdapter = new AutherAdapter(this, mAutherList);

        mAutherListView.setAdapter(mAutherAdapter);

        db.collection(COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Auther> mAutherList = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Auther miss = document.toObject(Auther.class);
                        mAutherList.add(miss);
                    }
                    ListView mAutherListView = (ListView) findViewById(R.id.autherList);
                    AutherAdapter mMissionAdapter = new AutherAdapter(AuthersActivity.this, mAutherList);
                    mAutherListView.setAdapter(mMissionAdapter);
                } else {
                    Log.d("AutherActivity", "Error getting documents: ", task.getException());
                }
            }
        });

        mAutherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Auther auther = (Auther) parent.getAdapter().getItem(position);
                editAutherDialog(auther, position);
//                Toast.makeText(AuthersActivity.this, "Id is: "+ id, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void editAutherDialog(final Auther auther, final int position)
    {
        final Dialog editAutherDialog = new Dialog(AuthersActivity.this);
        editAutherDialog.setTitle("Edit Auther");
        editAutherDialog.setContentView(R.layout.activity_create_auther);

        TextView autherGoToHome = (TextView) editAutherDialog.findViewById(R.id.AutherGoToHome);

        autherGoToHome.setVisibility(View.GONE);

        TextView topTV = (TextView) editAutherDialog.findViewById(R.id.top_header_auther_create);
        topTV.setText("Edit Auther");

        EditText autherName = (EditText) editAutherDialog.findViewById(R.id.auther_name);
        autherName.setText(auther.getName().toString());

        EditText autherCity = (EditText) editAutherDialog.findViewById(R.id.auther_city);
        autherCity.setText(auther.getCity().toString());

        EditText autherGender = (EditText) editAutherDialog.findViewById(R.id.auther_gender);
        autherGender.setText(auther.getGender().toString());

        /*EditText autherId = (EditText) editAutherDialog.findViewById(R.id.auther_id);
        autherId.setText(auther.getId());*/

       Button submitBtn = (Button) editAutherDialog.findViewById(R.id.btn_add_auther);

//        Toast.makeText(AuthersActivity.this, " ID is: " + auther.getId(), Toast.LENGTH_LONG).show();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get text view values

                EditText autherNewName = (EditText) editAutherDialog.findViewById(R.id.auther_name);

                EditText autherNewCity = (EditText) editAutherDialog.findViewById(R.id.auther_city);

                EditText autherNewGender = (EditText) editAutherDialog.findViewById(R.id.auther_gender);

//                EditText autherId = (EditText) editAutherDialog.findViewById(R.id.auther_id);

                //Step 3 -- Creating Map to store values
                Map<String,Object> objectMap = new HashMap<>();

                objectMap.put("name",autherNewName.getText().toString());
                objectMap.put("city",autherNewCity.getText().toString());
                objectMap.put("gender",autherNewGender.getText().toString());
                objectMap.put("id",auther.getId().toString());

                objectFirebaseFirestore= FirebaseFirestore.getInstance();

                objectFirebaseFirestore
                        .collection(COLLECTION_NAME)
                        .document(auther.getId().toString())
                        .update(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        editAutherDialog.dismiss();

                        Toast.makeText(AuthersActivity.this,
                                "Auther record updated successfully", Toast.LENGTH_SHORT).show();


                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AuthersActivity.this,
                                        "Fails to add values:"+
                                                e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                Toast.makeText(AuthersActivity.this, " ID is: " + auther.getId(), Toast.LENGTH_LONG).show();
            }
        });

        editAutherDialog.show();
    }
}
