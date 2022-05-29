package com.example.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    Button A, B, C, D, E, F;
    TextView buttonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        A = findViewById(R.id.button_a);
        B = findViewById(R.id.button_b);
        C = findViewById(R.id.button_c);
        D = findViewById(R.id.button_d);
        E = findViewById(R.id.button_e);
        F = findViewById(R.id.button_f);
        buttonText = findViewById(R.id.textView);

        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText.setText("Pressed: A");
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText.setText("Pressed: B");
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText.setText("Pressed: C");
            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText.setText("Pressed: D");
            }
        });

        E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText.setText("Pressed: E");
            }
        });

        F.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonText.setText("Pressed: F");
            }
        });
    }
}