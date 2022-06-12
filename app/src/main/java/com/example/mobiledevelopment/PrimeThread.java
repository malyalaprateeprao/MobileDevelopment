package com.example.mobiledevelopment;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PrimeThread implements Runnable {
    long minPrime;
    long currentvalue = 0;
    long latestprimeno = 0;

    TextView currentnoview;
    TextView latestprimeview;

    boolean exit = false;

    PrimeThread(long minPrime) {
        this.minPrime = minPrime;
    }

    public void callme(TextView currentnoview, TextView latestprimeview){

        this.currentnoview = currentnoview;
        this.latestprimeview = latestprimeview;
    }

    public void run() {
        // compute primes larger than minPrime

        ArrayList<Long> primenos = new ArrayList<>();
        long v = minPrime;
        while(true) {
            int k = 0;
            for(int i=2; i<v; i++){
                if(v%i == 0){
                    k = 1; break;
                }
            }
            if( k == 0) { primenos.add(v); latestprimeno = v;}

            /*
            if(v - currentvalue > 200){
                currentnoview.setText ("CurrentNumberChecking: " + v);
            }*/
            currentnoview.setText ("CurrentNumberChecking: " + v);
            latestprimeview.setText ("LatestPrimeNumber: " + latestprimeno);

            currentvalue = v; v = v + 1;
            System.out.println(v);

            // if v > long type terminate raise exception

            if(exit == true) break;
        }
    }

}