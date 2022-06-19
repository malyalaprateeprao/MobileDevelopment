package com.example.mobiledevelopment;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.MessageFormat;

public class LocationActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    //private FusedLocationProviderClient client;
    TextView la, lo;
    TextView locationTextView;
    TextView travelDistance;
    Button resetDistance;
    Button get_loc;
    LocationRequest locationRequest;
    private Handler textHandler = new Handler();

    double lat1 = 0, lon1 = 0, lat2 = 0, lon2 = 0;
    double total = 0;
    boolean first = true;
    boolean reset = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //requestPermission();

        la = findViewById(R.id.text_view_lat);
        lo = findViewById(R.id.text_view_long);
        locationTextView = findViewById(R.id.location_text);
        get_loc = findViewById(R.id.get_location);
        travelDistance = findViewById(R.id.travel_distance);
        resetDistance = findViewById(R.id.reset_distance);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        /*client = LocationServices.getFusedLocationProviderClient(this);

        get_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(LocationActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                client.getLastLocation().addOnSuccessListener(LocationActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location != null) {
                            locationTextView.setText(location.toString());
                        }
                    }
                });
            }
        });*/

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }

        resetDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = true;
                total = 0;
                travelDistance.setText(String.format("Traveled Distance: %s", total));
            }
        });

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    if (locationResult == null) {

                        return;
                    }
                    //Showing the latitude, longitude and accuracy on the screen.
                    for (Location location : locationResult.getLocations()) {
                        locationTextView.setText(MessageFormat.format("Lat: {0} Long: {1} Accuracy: {2}", location.getLatitude(),
                                location.getLongitude(), location.getAccuracy()));
                        //la.setText(MessageFormat.format("Lat: {0}   ", location.getLatitude()));
                        //la.setText((int) location.getLatitude());
                        //lo.setText((int) location.getLongitude());
                        System.out.println("chill" + location.getLatitude());
                        lat2 = location.getLatitude();
                        lon2 = location.getLongitude();
                        if(first == true) {
                            total = 0;
                            first = false;
                        } else {
                            double theta = lon1 - lon2;
                            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
                            dist = Math.acos(dist);
                            dist = rad2deg(dist);
                            dist = dist * 60 * 1.1515;
                            total = total + dist;
                        }
                        lat1 = lat2;
                        lon1 = lon2;
                        textHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                la.setText(String.format("Latitude: %s N", location.getLatitude()));
                                lo.setText(String.format("Longitude: %s W", location.getLongitude()));
                                //travelDistance.setText(String.format("TD: %s", total));
                                travelDistance.setText(MessageFormat.format("Total Distance: {0}", total));
                            }
                        });
                    }
                } else {

                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    /*private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }*/

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}