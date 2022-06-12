package com.example.mobiledevelopment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Activity5 extends AppCompatActivity {

    Button prime, terminate;
    PrimeThread p = null;
    Thread t = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);

        System.out.println("killed thread successfully");

        prime = findViewById(R.id.button5);
        terminate = findViewById(R.id.button6);

        TextView currentnoview = findViewById(R.id.textView3);
        TextView latestprimeview = findViewById(R.id.textView5);


        prime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t == null) {
                    //new Thread();
                    p = new PrimeThread(2);
                    p.callme(currentnoview, latestprimeview);
                    t = new Thread(p);
                    t.start();
                }
                else{
                    Toast.makeText(getApplication(), "Prime Thread is already running", Toast.LENGTH_SHORT).show();
                }
            }
        });

        terminate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("killed thread successfully1");
                //terminating thread
                if(t != null){
                    //print p.currentvalue if p!=null

                    //Toast.makeText(getApplicationContext(),"CurrentValueChecking:" + p.currentvalue + "\nLatestPrimeNo:" + p.latestprimeno , Toast.LENGTH_SHORT).show();

                    p.exit = true;
                    t.interrupt();
                    t = null;

                    System.out.println("killed thread successfully inside");
                }
                else{
                    Toast.makeText(getApplication(), "Prime Thread already Terminated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed()
    {
        /*super.onBackPressed();
        if(t != null) {
            p.exit = true;
            t.interrupt();
            t = null;
            Toast.makeText(getApplication(), "Terminating Prime Thread", Toast.LENGTH_SHORT).show();
        }*/

        if(t != null) {
            new AlertDialog.Builder(this).setMessage("This will terminate the search, please confirm if you want to exit?")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            p.exit = true;
                            t.interrupt();
                            t = null;
                            finish();
                        }
                    }).show();
        } else {
            super.onBackPressed();
        }
    }
}