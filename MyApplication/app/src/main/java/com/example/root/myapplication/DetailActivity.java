package com.example.root.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    /*Class for handling details for each product */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int details = getIntent().getIntExtra(MainActivity.PRODUCT_ID, 0);

        final Product p = MainActivity.getProducts(details);

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        TextView tv = (TextView) findViewById(R.id.description);
        Button bt = (Button) findViewById(R.id.button);

        try {
            tv.setText(p.toString());
            iv.setImageDrawable(p.getDrawable());
        } catch(NullPointerException e){
            e.printStackTrace();
        }


        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException e){
            e.printStackTrace();
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast t = Toast.makeText(getApplicationContext(),"Added to cart" ,Toast.LENGTH_LONG);
                t.show();
                CartActivity.addToCart(p.toCartFormat());
            }
        });
    }

}
