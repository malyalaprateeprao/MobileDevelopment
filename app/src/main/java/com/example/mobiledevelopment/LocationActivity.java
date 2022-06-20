package com.example.mobiledevelopment;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.MessageFormat;

public class LocationActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
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
    boolean start = true;

    RadioGroup radioGroup;
    int radioId;
    Button applyPrecision;
    RadioButton radioOne, radioTwo, radioThree, radioSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        la = findViewById(R.id.text_view_lat);
        lo = findViewById(R.id.text_view_long);
        locationTextView = findViewById(R.id.location_text);
        get_loc = findViewById(R.id.get_location);
        travelDistance = findViewById(R.id.travel_distance);
        resetDistance = findViewById(R.id.reset_distance);
        radioGroup = findViewById(R.id.radio_group);
        applyPrecision = findViewById(R.id.apply_precision);
        radioOne = findViewById(R.id.radio_one);
        radioTwo = findViewById(R.id.radio_two);
        radioThree = findViewById(R.id.radio_three);

        if (savedInstanceState != null) {
            total = savedInstanceState.getDouble("myTotal");
            lat1 = savedInstanceState.getDouble("myLatitude");
            lon1 = savedInstanceState.getDouble("myLongitude");
            first = savedInstanceState.getBoolean("myFirst");
            start = savedInstanceState.getBoolean("myStart");

            la.setText(String.format("Latitude: %s N", lat1));
            lo.setText(String.format("Longitude: %s E", lon1));
            travelDistance.setText(MessageFormat.format("Total Distance: {0}", total));
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        }

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        radioId = radioGroup.getCheckedRadioButtonId();

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    //Showing the latitude, longitude and accuracy on the screen.
                    for (Location location : locationResult.getLocations()) {
                        locationTextView.setText(MessageFormat.format("Accuracy: {0}", location.getAccuracy()));
                        lat2 = location.getLatitude();
                        lon2 = location.getLongitude();
                        if (first == true) {
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
                                lo.setText(String.format("Longitude: %s E", location.getLongitude()));
                                travelDistance.setText(MessageFormat.format("Total Distance: {0}", total));
                            }
                        });
                    }
                } else {

                }
            }
        };

        get_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start == true ) {
                    if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                    }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                    start = false;
                } else {
                    Toast.makeText(getApplication(), "Location is already being fetched", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first = true;
                total = 0;
                travelDistance.setText(String.format("Traveled Distance: %s", total));
            }
        });

        applyPrecision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                radioSelected = findViewById(radioId);
                if (radioSelected == radioOne) {
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                } else if (radioSelected == radioTwo) {
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                } else {
                    locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                }
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                }
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
            }
        });
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void checkButton(View V) {
        radioId = radioGroup.getCheckedRadioButtonId();
        System.out.println("chill " + radioId);
    }

    //Use onSaveInstanceState(Bundle) and onRestoreInstanceState

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putBoolean("myFirst", first);
        savedInstanceState.putBoolean("myStart", start);
        savedInstanceState.putDouble("myTotal", total);
        savedInstanceState.putDouble("myLatitude", lat2);
        savedInstanceState.putDouble("myLongitude", lon2);

        super.onSaveInstanceState(savedInstanceState);
    }

}