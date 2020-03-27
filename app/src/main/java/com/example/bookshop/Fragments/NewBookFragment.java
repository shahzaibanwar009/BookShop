package com.example.bookshop.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookshop.BooksActivity;
import com.example.bookshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewBookFragment extends Fragment {

    private final String COLLECTION_NAME = "books";
    //Step 1 - Create Firebase Firestore object
    private FirebaseFirestore objectFirebaseFirestore;

    EditText publish_date, book_title, book_isbn;
    Button btn_add_auther;
    String oldPublish_date;
    DatePickerDialog picker;

    private View objectView;

    public NewBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_book, container, false);
        initializeObjects();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

        initializeFirebaseFirestoreObject();
        book_title  = view.findViewById(R.id.book_title);
        book_isbn  = view.findViewById(R.id.book_isbn);

        // add click listner on submit button
        btn_add_auther = (Button) view.findViewById(R.id.btn_add_auther);
        btn_add_auther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewBook();
            }
        });

        // add date picker on edit view listner
        publish_date = (EditText) view.findViewById(R.id.publish_date_mine);
        publish_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatelistner();
            }
        });

        publish_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    addDatelistner();
                }
            }
        });

    }

    public void initializeObjects()
    {
        /*try
        {


        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "NewBookActivity:initializeObjectsOfFragment:"+
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/
    }

    private void addNewBook()
    {
        if(!book_title.getText().toString().isEmpty()
                && !book_isbn.getText().toString().isEmpty()
                && !publish_date.getText().toString().isEmpty()
        )
        {
            //Step 3 -- Creating Map to store values
            Map<String,Object> objectMap=new HashMap<>();
            String id = objectFirebaseFirestore.collection(COLLECTION_NAME).document().getId();


            objectMap.put("title",book_title.getText().toString());
            objectMap.put("isbn",book_isbn.getText().toString());
            objectMap.put("publish_date",publish_date.getText().toString());
            objectMap.put("id",id);



//            Toast.makeText(CreateAuther.this, "Id before addting vallue:"+ id, Toast.LENGTH_LONG).show();

            //Step 4- add objectMap to Firebase Firestore
            objectFirebaseFirestore.collection(COLLECTION_NAME)
                    .document(id)
                    .set(objectMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(),
                                    "Book added successfully", Toast.LENGTH_SHORT).show();

                            book_title.setText("");
                            book_isbn.setText("");
                            publish_date.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),
                                    "Fails to add values:"+
                                            e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else if(book_isbn.getText().toString().isEmpty())
        {
            Toast objectToast=Toast.makeText(getContext(),
                    "Please enter Book ISBN", Toast.LENGTH_SHORT);

            objectToast.setGravity(Gravity.TOP,0,0);
            objectToast.show();

        }
        else if(book_title.getText().toString().isEmpty())
        {
            Toast objectToast=Toast.makeText(getContext(),
                    "Please enter Title", Toast.LENGTH_SHORT);

            objectToast.setGravity(Gravity.TOP,0,0);
            objectToast.show();
        }
        else if(publish_date.getText().toString().isEmpty())
        {
            Toast objectToast=Toast.makeText(getContext(),
                    "Please enter Publish date", Toast.LENGTH_SHORT);

            objectToast.setGravity(Gravity.TOP,0,0);
            objectToast.show();
        }
//        Toast.makeText(getActivity(), "addNewBook() called" + book_title.getText().toString(), Toast.LENGTH_LONG).show();
    }
    private void addDatelistner()
    {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String yearSt  = String.valueOf(year);
                        String monthOfYearSt  = String.valueOf(monthOfYear+1);
                        String dayOfMonthSt  = String.valueOf(dayOfMonth);

                        publish_date.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    private void initializeFirebaseFirestoreObject()
    {
        //Step 2 - initialize Firebase Firestore object
        objectFirebaseFirestore= FirebaseFirestore.getInstance();
    }


}
