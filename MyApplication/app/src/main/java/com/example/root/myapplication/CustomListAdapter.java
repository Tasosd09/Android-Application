package com.example.root.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Product>{
   /*Custom adapter for handling List presentation
    *
    */
    private ArrayList<Product> products;

    public CustomListAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
        products = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list,parent, false);
        }

        Product product = products.get(position);
        TextView tv = (TextView) convertView.findViewById(R.id.item);
        ImageView iv = (ImageView)convertView.findViewById(R.id.icon);
        tv.setText(product.toString());
        iv.setImageDrawable(product.getDrawable());

        return convertView;
    }
}
