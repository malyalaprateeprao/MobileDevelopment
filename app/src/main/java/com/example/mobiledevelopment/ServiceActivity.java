package com.example.mobiledevelopment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;

public class ServiceActivity extends AppCompatActivity {

    Button cityId;
    TextView gender, probability, count;
    EditText edit_city;
    ListView list_weather;
    WebView webV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        cityId = findViewById(R.id.b_getCityId);
        edit_city = findViewById(R.id.edit_text_input);
        list_weather = findViewById(R.id.list_report);
        gender = findViewById(R.id.text_gender);
        probability = findViewById(R.id.text_probability);
        count = findViewById(R.id.text_count);
        webV = findViewById(R.id.webview);


        webV.getSettings().setJavaScriptEnabled(true);

        cityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("id ");

                // Instantiate the RequestQueue.
                String name = edit_city.getText().toString();

                ProgressDialog p = new ProgressDialog(ServiceActivity.this);
                p.setMessage("Loading ...");
                p.setCancelable(false);
                p.show();

                RequestQueue queue = Volley.newRequestQueue(ServiceActivity.this);
                String url ="https://api.genderize.io/?name=" + name;

                //webV.loadUrl("https://api.genderize.io/?name=sri");
                webV.loadUrl(url);

                // Request a string response from the provided URL.
                /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                p.dismiss();
                                // Display the first 500 characters of the response string.
                                //textView.setText("Response is: "+ response.substring(0,500));
                                System.out.println(response);
                                Toast.makeText(ServiceActivity.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();
                        //textView.setText("That didn't work!");
                        System.out.println("Error");
                        Toast.makeText(ServiceActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                });*/

                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                p.dismiss();
                                // Display the first 500 characters of the response string.
                                //textView.setText("Response is: "+ response.substring(0,500));
                                System.out.println(response);
                                //Toast.makeText(ServiceActivity.this, response, Toast.LENGTH_SHORT).show();

                                JSONObject data = response;
                                try {
                                    String gen = response.getString("gender");
                                    double prob = response.getDouble("probability");
                                    int c = response.getInt("count");
                                    if(c == 0) {
                                        gender.setText("Gender: " + "No data found");
                                    } else {
                                        gender.setText("Gender: " + gen);
                                    }
                                    probability.setText(String.format("Probability: %s", prob));
                                    count.setText(MessageFormat.format("Database count: {0}", c));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();
                        //textView.setText("That didn't work!");
                        System.out.println("Error");
                        Toast.makeText(ServiceActivity.this, "Something went wrong. Make sure you input the name", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                //Toast.makeText(ServiceActivity.this, "City Id", Toast.LENGTH_SHORT).show();
            }
        });

    }
}