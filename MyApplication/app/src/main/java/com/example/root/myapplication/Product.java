package com.example.root.myapplication;

import android.graphics.drawable.Drawable;

public class Product {
    private String model;
    private String manufacturer;
    private String price;
    private Drawable image;

    public Product(String mod, String man, String price, Drawable drawable){
        this.model = mod;
        this.manufacturer = man;
        this.price = price;
        this.image = drawable;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(manufacturer+model+"\n"+"Euro: "+price);

        return sb.toString();
    }

    public String toCartFormat(){
        String returnString = manufacturer+ " " +model +"Euro: "+price;
        return returnString;
    }

    public Drawable getDrawable(){
        return image;
    }

}
