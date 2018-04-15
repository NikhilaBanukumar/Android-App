package edu.usc.cs_server.csci571;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener {
    public final static String EXTRA_MESSAGE = "edu.usc.cs_server.csci571";
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Double lat;
    Double lng;


    public class GPST extends Service implements LocationListener{
        private final Context mContext;

        // flag for GPS status
        boolean isGPSEnabled = false;

        // flag for network status
        boolean isNetworkEnabled = false;

        boolean canGetLocation = false;
        Location location;
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
        private final String[] LOCATION_PERMS={
                Manifest.permission.ACCESS_FINE_LOCATION};
        protected LocationManager locationManager;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public GPST (Context context) {
            this.mContext = context;
            getLocation();
        }

        private boolean canAccessLocation() {
            return(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_FINE_LOCATION));
        }




        @RequiresApi(api = Build.VERSION_CODES.M)
        public Location getLocation() {
            try {
                if (!canAccessLocation()) {
                    requestPermissions(LOCATION_PERMS, 1340);
                }
                locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                // getting network status
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    System.out.println("No network");
                    // no network provider is enabled
                } else {
                    this.canGetLocation = true;
                    // First get location from Network Provider
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    }
                    if (isNetworkEnabled) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                                System.out.println("lat+lng"+lat+lng);
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    lat = location.getLatitude();
                                    lng = location.getLongitude();
                                    System.out.println("lat+lng"+lat+lng);
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }


        public double getLatitude(){
            if(location != null){
                lat = location.getLatitude();
                System.out.println("latitude"+lat.toString());
            }

            // return latitude
            return lat;
        }

        /**
         * Function to get longitude
         * */
        public double getLongitude(){
            if(location != null){
                lng = location.getLongitude();
                System.out.println("longitude"+lng.toString());
            }

            // return longitude
            return lng;
        }



        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ImageView img=(ImageView) findViewById(R.id.imageView);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        GoogleApiClient client = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */,
//                        this /* OnConnectionFailedListener */)
//                .addApi(Drive.API)
//                .addScope(Drive.SCOPE_FILE)
//                .setAccountName("users.account.name@gmail.com")
//                .build();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void clear(View v){
        EditText clear_text = (EditText) findViewById(R.id.editText4);
//        EditText clear_text = new EditText(R.id.editText4);
        clear_text.setText("");
    }
//    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static String getString(InputStream stream) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        StringBuilder output = new StringBuilder();
        char[] buffer = new char[1000];
        for(;;) {
            int abh = reader.read(buffer, 0, buffer.length);
            if(abh < 0)
                break;
            output.append(buffer, 0, abh);
        }
        return output.toString();
    }

    public static String makeHttpGetRequest(String url){
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)(new URL(url)).openConnection();
           // conn.setReadTimeout(10000 /* milliseconds */);
           // conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            String contentAsString = getString(conn.getInputStream());
            return contentAsString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public void search(View v){
        Intent intent = new Intent(getApplicationContext(), result.class);
        EditText clear_text = (EditText) findViewById(R.id.editText4);
        String message = clear_text.getText().toString();
        CharSequence text = "Please enter a keyword!";
        if(message.matches(""))
        {
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(getApplicationContext(),text, duration);
            toast.show();
        }
        else{
//        intent.putExtra( message);

            class SendPostReqAsyncTask extends AsyncTask<String,Void,String> {
                @Override
                protected String doInBackground(String... params) {
                    System.out.print("Clear Text is "+params[0]);
                    String url = "http://sample-env-1.6vd9zq2qsg.us-west-2.elasticbeanstalk.com/?Name="+params[0];
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        System.out.println("In Http Call");
                        String result = makeHttpGetRequest(url);
                        System.out.println(result);
                        return result;
                    }

                    return "Returning Null";


                }

                @Override
                protected void onPostExecute(String result)
                {
                    {
                        super.onPostExecute(result);
                        try
                        {
                            System.out.println("You got JSON. Ha ha ha ...");
                            System.out.println(result);
                            displayData(result);
                            JSONObject jObject = new JSONObject(result);

                            String Output_code = (String)jObject.get("user123");

                            JSONObject dataObj = new JSONObject(Output_code);
                            String Output_code1 = dataObj.get("data").toString();

                            System.out.println(Output_code1);


                            System.out.println("You got JSON. Ha ha ha ...");
                            displayData(result);


                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                }


            }
            SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
            sendPostReqAsyncTask.execute(message);
            }
    }

    protected void displayData(String result) {
        Intent intent = new Intent(getApplicationContext(), result.class);
        intent.putExtra("json", result);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(getApplicationContext(),favorite.class);
            startActivity(i);

        }   else if (id == R.id.nav_share) {
            Intent i = new Intent(getApplicationContext(),aboutme.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
