package com.example.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

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
        Toast.makeText(getApplicationContext(),"Name: Prateep rao \nEmail: prateepraomalyala@gmail.com", Toast.LENGTH_SHORT).show();
    }
}