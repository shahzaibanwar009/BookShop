package com.example.bookshop;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, List<Book> object){
        super(context,0, object);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_book,parent,false);
        }

        TextView book_listview_title = (TextView) convertView.findViewById(R.id.book_listview_title);
        TextView book_listview_isbn = (TextView) convertView.findViewById(R.id.book_listview_isbn);
        TextView book_listview_publish_date = (TextView) convertView.findViewById(R.id.book_listview_publish_date);

        Book objBook = getItem(position);

        book_listview_title.setText(objBook.getTitle().toString());
        book_listview_isbn.setText(objBook.getIsbn().toString());
        book_listview_publish_date.setText(objBook.getPublish_date().toString());

        return convertView;
    }
}
