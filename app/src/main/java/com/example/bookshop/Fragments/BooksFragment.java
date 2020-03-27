package com.example.bookshop.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookshop.Auther;
import com.example.bookshop.Book;
import com.example.bookshop.BookAdapter;
import com.example.bookshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {

    private final String COLLECTION_NAME = "books";
    //Views
    private TextView mHeaderView;
    private ListView mBookListView;

    //Firebase
    private FirebaseFirestore db;

    //Adapter
    private BookAdapter mBookAdapter;
    private ArrayList<Book> mBookList;

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

        try {
            mHeaderView = (TextView) view.findViewById(R.id.bookHeader);
            mBookListView = (ListView) view.findViewById(R.id.bookList);

            mHeaderView.setText("Available Books");

            //get Database
            db = FirebaseFirestore.getInstance();
            //Set up the ArrayList
            mBookList = new ArrayList<Book>();

            mBookListView.setAdapter(mBookAdapter);

            db.collection(COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    List<Book> mBookList = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Book miss = document.toObject(Book.class);
                            mBookList.add(miss);
                        }
                        ListView mBookListView = (ListView) view.findViewById(R.id.bookList);
                        BookAdapter mBooksAdapter = new BookAdapter(getContext(), mBookList);
                        mBookListView.setAdapter(mBooksAdapter);
                    } else {
                        Log.d("BookFragmentActivity", "Error getting documents: ", task.getException());
                    }
                }
            });

            mBookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Book book = (Book) parent.getAdapter().getItem(position);
                   //  editAutherDialog(auther, position);
                Toast.makeText(getContext(), "Id is: "+ book.getId(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(getContext(), "BooksFragment:" +
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
