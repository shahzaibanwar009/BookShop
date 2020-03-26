package com.example.bookshop;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class AutherAdapter  extends ArrayAdapter<Auther> {
    public AutherAdapter(Context context, List<Auther> object){
        super(context,0, object);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.item_auther,parent,false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.auther_listview_name);
        TextView cityTextView = (TextView) convertView.findViewById(R.id.auther_listview_city);
        TextView genderTextView = (TextView) convertView.findViewById(R.id.auther_listview_gender);

        Auther objAuther = getItem(position);

        nameTextView.setText(objAuther.getName().toString());
        cityTextView.setText(objAuther.getCity().toString());
        genderTextView.setText(objAuther.getGender().toString());


        return convertView;
    }
}
