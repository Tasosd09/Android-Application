package com.example.root.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Product> products;
    public static final String PRODUCT_ID = "ProductId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView lv = (ListView)findViewById(R.id.listView);

        Toast t = Toast.makeText(getApplicationContext(),"Collecting data" ,Toast.LENGTH_LONG);
        t.show();
        runThread(lv); //Runs the thread to fetch the data from the web

        if (lv != null) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                /* Handles the actions of taping on a list item */
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent details = new Intent(MainActivity.this, DetailActivity.class);
                    details.putExtra(PRODUCT_ID, position);
                    startActivity(details);
                }
            });
        }
    }

    public static Product getProducts(int i){
        return products.get(i);
    }

    public void runThread(final ListView lv){
    /* Handles the thread for fetching the data from the web.
     * The method used is html scrapping, although it is slow
     */
        new Thread(){
            public void run(){
                final String s = getData("http://alpamayopro.gr/products/list.php?ItemTypeID=130&CatID=6");
                final ArrayList<Product> products = parseData(s);
                MainActivity.this.products = products;
                try{
                    runOnUiThread(new Runnable(){
                        public void run(){
                            CustomListAdapter adapter = new CustomListAdapter(MainActivity.this, R.layout.custom_list,products);
                            lv.setAdapter(adapter);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public Drawable toDrawable(String url) throws IOException {
        try{
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, url);
            return d;
        } catch(Exception e){
            return null;
        }
    }

    public String getData(String url){
        String inputLine;
        StringBuilder content = new StringBuilder();

        try{
            URL link = new URL(url);
            URLConnection con = link.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while((inputLine = in.readLine()) != null){
                content.append(inputLine+"\n");
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public ArrayList<Product> parseData(String s){

        ArrayList<Product> returnProducts = new ArrayList<>();
        int startIndex = s.indexOf("/images/items/th");
        int endIndex = 1;

        StringBuilder temp = new StringBuilder(s.subSequence(startIndex, s.length() - 1));

        while(temp != null && (startIndex = temp.indexOf("/images/items/th")) >= 0){

            endIndex = temp.indexOf("jpg", startIndex);
            String imageUrl = "http://alpamayopro.gr" + temp.subSequence(startIndex, endIndex+3);
            Drawable dr = null;
            try {
                 dr = toDrawable(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //     System.out.println(siteUrl);
            startIndex = temp.indexOf("/products/list.php?ManufacturerID=", endIndex);
            startIndex = temp.indexOf(">", startIndex);
            endIndex = temp.indexOf("</a>", startIndex);

            String manufacturer = temp.subSequence(startIndex + 2, endIndex).toString().replace(" ", "");

            startIndex = temp.indexOf("/products/item.php?ItemID=", endIndex);
            startIndex = temp.indexOf("g>", startIndex);
            endIndex = temp.indexOf("<", startIndex);

            String model = temp.subSequence(startIndex+3, endIndex).toString().replace(" ", "");

            endIndex = temp.indexOf("&euro", endIndex);
            startIndex = endIndex - 7;
            String price = temp.subSequence(startIndex, endIndex).toString();

            temp = new StringBuilder(temp.subSequence(endIndex, temp.length()));


            returnProducts.add(new Product(model,manufacturer,price,dr));

        }
        return returnProducts;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast t = Toast.makeText(getApplicationContext(),"Settings Selected" ,Toast.LENGTH_LONG);
            t.show();
            return true;
        }
        else if(id == R.id.action_about){
            Intent newIntent = new Intent(this, AboutActivity.class);
            startActivity(newIntent);
            return true;
        }
        else if(id == R.id.action_contact){
            Intent newIntent = new Intent(this, ContactActivity.class);
            startActivity(newIntent);
            return true;
        }
        else if(id == R.id.action_cart){
            Intent newIntent = new Intent(this, CartActivity.class);
            startActivity(newIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
