package com.example.simran.krishaksahayata;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FetchDetailsActivity extends AppCompatActivity {

    TextView tvLocation, tvTemperature, tvWind, tvHumidity, tvPressure;
    EditText etArea;
    Button btnPredict;
    ProgressDialog progressDialog;
    String latitude1, longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_details);

        tvTemperature = findViewById(R.id.tvTemperature);
        tvWind = findViewById(R.id.tvWind);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvPressure = findViewById(R.id.tvPressure);
        etArea = findViewById(R.id.etArea);
        tvLocation = findViewById(R.id.tvLocation);
        btnPredict = findViewById(R.id.btnPredict);
        latitude1 = getIntent().getStringExtra("latitude");
        longitude1 = getIntent().getStringExtra("longitude");
        //Log.d("Lappi1",String.valueOf(latitude1));

        //progressDialog = new ProgressDialog(FetchDetailsActivity.this);
        //progressDialog.setCancelable(false);
        //progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progressDialog.setTitle("Fetching App List");
        //progressDialog.setMessage("Please Wait...");

        //tvLocation.setText("Chembur");
        //tvWind.setText("2.1 m/s");
        //tvTemperature.setText("29 deg C");
        //tvPressure.setText("1014 hPa");
        //tvHumidity.setText("34%");

        find_weather();
    }

    public void find_weather()
    {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+latitude1+"&lon="+longitude1+"&appid=2dd78c1ebfd0be1a3dedb2e75843e0a2";
        Log.d("tampil",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject jsonObject = response.getJSONObject("main");
                    JSONObject jsonObject1 = response.getJSONObject("wind");
                    /*String temperature = String.valueOf(jsonObject.getDouble("temp"));
                    String humidity = String.valueOf(jsonObject.getInt("humidity"));
                    String pressure = String.valueOf(jsonObject.getInt("pressure"));
                    String windspeed = String.valueOf(jsonObject1.getDouble("speed"));
                    String city = response.getString("name");

                    //tvTemperature.setText(temperature);
                    tvPressure.setText(pressure);
                    tvHumidity.setText(humidity);
                    tvWind.setText(windspeed);

                    double temperature_int = Double.parseDouble(temperature);
                    double centigrate = (temperature_int - 32)/ 1.8000;
                    centigrate = Math.round(centigrate);
                    int i = (int)centigrate;
                    tvTemperature.setText(String.valueOf(i));*/
                    Toast.makeText(FetchDetailsActivity.this, String.valueOf(jsonObject), Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley",String.valueOf(error));

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

        /*private void prod() {
            progressDialog.show();
        }*/

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this application? ");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.setTitle("Exit");
        alertDialog.show();
    }
}
