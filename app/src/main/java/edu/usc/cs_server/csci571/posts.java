package edu.usc.cs_server.csci571;

/**
 * Created by nikhilabanukumar on 4/18/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import static edu.usc.cs_server.csci571.MainActivity.makeHttpGetRequest;
import static edu.usc.cs_server.csci571.R.id.buttonnext;
import static edu.usc.cs_server.csci571.R.id.center;
import static edu.usc.cs_server.csci571.R.id.fav;
import static edu.usc.cs_server.csci571.R.id.imageView;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class posts extends Fragment {

    ListView list_details;
    ImageView chartImage;
    postAdapter detailsAdapter;
    JSONObject result;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.posts, container, false);

        int json_array_length = 0;
        String json_data = this.getArguments().getString("json_data");


//      String json_data = this.getArguments().getString("json_data");
//      System.out.println("DEBUG: In USers Fragment: Type"+type);
//      System.out.println("DEBUG: In USers Fragment: JSONData"+json_data);


        list_details = (ListView) view.findViewById(R.id.tpost);


        List<postDetails> list = new ArrayList<>();

        try {
            //Data part of JSON
            result = new JSONObject(json_data);
            JSONArray json_array = (JSONArray) result.get("posts");
            String name= result.getString("name");
            String picture= result.getJSONObject("picture").getString("url");
            json_array_length = json_array.length();
            System.out.println("DEBUG: In USer.java: JSON Array Length"+name);


            for(int i = 0; i < json_array_length; i++){

                String message = json_array.getJSONObject(i).getString("message");
                String date = json_array.getJSONObject(i).getJSONObject("created_time").getString("date");
                System.out.println("DEBUG: Username and Picture URl for User.java"+message+date);

                postDetails obj = new postDetails(name,picture,message,date);

                list.add(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("reds"+getActivity().getApplicationContext());
        detailsAdapter = new postAdapter(getActivity().getApplicationContext(), R.layout.post_listview,list);

        list_details.setAdapter(detailsAdapter);


        list_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("DEBUG: Clicked item"+position);




            }
        });


        return view;
    }

}
