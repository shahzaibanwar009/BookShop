package com.example.bookshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookshop.Fragments.BooksFragment;
import com.example.bookshop.Fragments.NewBookFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class BooksActivity extends AppCompatActivity {

    private NewBookFragment objectNewBookFragment;
    private BooksFragment objectBooksFragment;
    DatePickerDialog picker;
    EditText publish_date;
    String oldPublish_date;

    private BottomNavigationView objectBNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);


        initializeObjects();

    }



    private void initializeObjects()
    {
        try
        {
            objectNewBookFragment = new NewBookFragment();
            objectBooksFragment = new BooksFragment();

            objectBNV=findViewById(R.id.BNV);
            changeFragment(objectNewBookFragment);

            objectBNV.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                            if(item.getItemId()==R.id.item_book_create)
                            {
                                changeFragment(objectNewBookFragment);
                                return true;
                            }
                            else if(item.getItemId()==R.id.item_book_list)
                            {
                                changeFragment(objectBooksFragment);
                                return true;
                            }
                            return false;
                        }
                    }
            );
        }
        catch (Exception e)
        {
            Toast.makeText(this, "initializeObjects:" +
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFragment(Fragment objectFragment)
    {
        try
        {
            FragmentTransaction objectTransaction=
                    getSupportFragmentManager().beginTransaction();

            objectTransaction.replace(R.id.container,objectFragment).commit();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "changeFragment"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

