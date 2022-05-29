package com.example.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }

    public void onClickA(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        changeTextOnce(buttonText, b);
    }

    public void onClickB(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        changeTextOnce(buttonText, b);
    }

    public void onClickC(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        changeTextOnce(buttonText, b);
    }

    public void onClickD(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        changeTextOnce(buttonText, b);
    }

    public void onClickE(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        changeTextOnce(buttonText, b);
    }

    public void onClickF(View v) {
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        changeTextOnce(buttonText, b);
    }

    public void changeTextOnce(String buttonText, Button changeTextButton){
        final TextView changingText = (TextView) findViewById(R.id.textView);

        changeTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changingText.setText("Pressed: -" + buttonText);
            }
        });
    }
}