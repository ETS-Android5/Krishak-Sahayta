package com.example.simran.krishaksahayata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    TextView tvLatitude,tvLongitude;
    Button btnFetchDetails,btnLogout;
    GoogleApiClient gac;
    Location loc;
    double lat,log;
    double lat1,log1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLatitude = findViewById(R.id.tvLatitude);
        tvLongitude = findViewById(R.id.tvLongitude);
        btnFetchDetails = findViewById(R.id.btnFetchDetails);
        btnLogout = findViewById(R.id.btnLogout);

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        gac = builder.build();
        //Toast.makeText(this, String.valueOf(gac), Toast.LENGTH_SHORT).show();
        btnFetchDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = loc.getLatitude();
                double log = loc.getLongitude();
                //Toast.makeText(MainActivity.this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, FetchDetailsActivity.class);

                Bundle b = new Bundle();
                b.putString("latitude",String.valueOf(lat));
                b.putString("longitude",String.valueOf(log));
                i.putExtras(b);
                startActivity(i);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i1);
                finish();
            }
        });


    }

    class MyTask extends AsyncTask<String, Void, String>
    {
        String temp;
        @Override
        protected String doInBackground(String... strings) {
            String json = "";
            String line = "";

            try
            {
                Toast.makeText(MainActivity.this, "test", Toast.LENGTH_SHORT).show();
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStreamReader isr = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader br = new BufferedReader(isr);

                while((line = br.readLine()) != null)
                    json = json + line + "\n";

                JSONObject jsonObject = new JSONObject(json);
                JSONObject p = jsonObject.getJSONObject("main");
                temp = p.getString("temp");
                Log.d("test4",String.valueOf(p));
            }
            catch (Exception e)
            {
                Log.d("SS123",""+e);
            }

            return temp;
        }

        @Override
        protected void onPostExecute(String aDouble) {
            super.onPostExecute(aDouble);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(gac != null)
            gac.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(gac != null)
        {
            gac.disconnect();

        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        loc = LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc != null)
        {
            lat = loc.getLatitude();
            log = loc.getLongitude();
            tvLatitude.setText(""+lat);
            tvLongitude.setText(""+log);
            Log.d("latttt",String.valueOf(lat));

            MyTask t1 = new MyTask();
            String web = "api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(lat)+"&lon="+String.valueOf(log);
            String api = "&appid=1a3ca7900eebc64a517a486e8c5c75c0\n";
            String info = web + api;
            Log.d("testtttttt",info);
            t1.execute(info);
        }
        else
        {
            //tvShowWhenNoNetwork.setText("Please Enable GPS/ Come in Open Area.");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_LONG).show();
    }

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
