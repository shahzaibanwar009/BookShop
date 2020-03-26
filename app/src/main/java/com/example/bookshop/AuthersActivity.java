package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authers);

        mHeaderView = (TextView) findViewById(R.id.autherHeader);
        mAutherListView = (ListView) findViewById(R.id.autherList);

        mHeaderView.setText("Available Missions");

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
                Toast.makeText(AuthersActivity.this, "Name is: "+ auther.getName(), Toast.LENGTH_LONG).show();

               /* Intent intent = new Intent(AuthersActivity.this, SendMessage.class);
                String message = auther.getName();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/
            }
        });
    }
}
