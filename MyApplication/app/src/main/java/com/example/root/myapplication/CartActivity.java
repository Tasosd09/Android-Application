package com.example.root.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class CartActivity extends AppCompatActivity {
    /* Code for handling "Cart" section of the menu.
     * Use addToCart method to add an item to the cart.
     * There is no actual utility at this time, the
     * cart is just represented as a collection of
     * concatenated Strings
     */

    private static String cartContainer = "";
    private static int itemId = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tv = (TextView) findViewById(R.id.cartText);
        tv.setText(cartContainer);

    }

    public static void addToCart(String s){
        StringBuilder sb = new StringBuilder(cartContainer);

        sb.append(itemId +": " + s + "\n\n");
        itemId++;
        cartContainer = sb.toString();
    }
}
