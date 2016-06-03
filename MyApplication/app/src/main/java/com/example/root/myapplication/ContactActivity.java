package com.example.root.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {
    /* Code for handling "Contact" section of the menu
     * The user can find our contact info and
     * tap the appropriate message to launch a phone,
     * a browser or an e-mail applicati
     */

    private final String EMAIL = "******@gmail.com";
    private final String PHONENUMBER = "+3069********";
    private final String WEBSITE = "https://www.linkedin.com/in/*******";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView mailTv = (TextView) findViewById(R.id.email);
        TextView phoneTv = (TextView) findViewById(R.id.phone);
        TextView siteTv = (TextView) findViewById(R.id.site);

        phoneTv.setText(this.PHONENUMBER);
        mailTv.setText(this.EMAIL);
        siteTv.setText(this.WEBSITE);

        mailTv.setOnClickListener(this);
        phoneTv.setOnClickListener(this);
        siteTv.setOnClickListener(this);
        
    }

    public void onClick(View v){
        switch(v.getId()){

            case R.id.email:
                Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                mailIntent.setData(Uri.parse("mailto:"));
                mailIntent.putExtra(Intent.EXTRA_EMAIL, this.EMAIL);
                if(mailIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(mailIntent);
                }
                break;
            case R.id.site:
                Intent phoneIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.WEBSITE));
                if(phoneIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(phoneIntent);
                }
                break;
            case R.id.phone:
                Intent webIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + this.PHONENUMBER));
                if(webIntent.resolveActivity(getPackageManager()) != null){
                    startActivity(webIntent);
                }
                break;

        }
    }

}
