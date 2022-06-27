package com.example.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showBio(View view) {
        // Do something in response to button click
        // Toast.makeText(getApplicationContext(),"Name: Prateep rao \nEmail: prateepraomalyala@gmail.com", Toast.LENGTH_SHORT).show();
        Intent activity3Intent = new Intent(getApplicationContext(), Activity3.class);
        startActivity(activity3Intent);
    }

    public void clickyClicky(View view){

        Intent activity2Intent = new Intent(getApplicationContext(), Activity2.class);
        startActivity(activity2Intent);
    }

    public void linkCollector(View view){

        Intent activity4Intent = new Intent(getApplicationContext(), Activity4.class);
        startActivity(activity4Intent);
    }

    public void primes(View view){

        Intent activity5Intent = new Intent(getApplicationContext(), Activity5.class);
        startActivity(activity5Intent);
    }

    public void location(View view){

        Intent LocationActivity = new Intent(getApplicationContext(), LocationActivity.class);
        startActivity(LocationActivity);
    }

    public void service(View view){

        Intent ServiceActivity = new Intent(getApplicationContext(), ServiceActivity.class);
        startActivity(ServiceActivity);
    }

}