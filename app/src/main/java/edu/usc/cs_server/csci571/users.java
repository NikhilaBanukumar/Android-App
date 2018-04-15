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
import java.util.List;

public class users extends Fragment {

    ListView list_details;
    ImageView chartImage;
    TypeDetailsAdapter detailsAdapter;
    JSONObject result;
    Paginator p;
    Button b_prev,b_next;
    private int currentPage = 0;


    public int getTotalPages(){
        return Paginator.TOTAL_NUM_ITEMS/Paginator.ITEMS_PER_PAGE;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users, container, false);


        int json_array_length = 0;
        String json_data = this.getArguments().getString("json_data");
        String type = this.getArguments().getString("type");

        System.out.println("DEBUG: In USers Fragment: Type"+type);
        System.out.println("DEBUG: In USers Fragment: JSONData"+json_data);


        list_details = (ListView) view.findViewById(R.id.t1);

        b_prev = (Button) view.findViewById(R.id.btn_user_prv);
        b_next = (Button) view.findViewById(R.id.btn_user_nxt);
        b_prev.setEnabled(false);

        List<TypeDetails> list = new ArrayList<>();

        try {
            //Data part of JSON
            result = new JSONObject(json_data);
            JSONArray json_array = (JSONArray) result.get("data");
            json_array_length = json_array.length();
            System.out.println("DEBUG: In USer.java: JSON Array Length"+json_array_length);


            for(int i = 0; i < json_array_length; i++){

                String user_name = json_array.getJSONObject(i).getString("name");
                String pic_url = json_array.getJSONObject(i).getJSONObject("picture").getJSONObject("data").getString("url");
                String user_id= json_array.getJSONObject(i).getString("id");
                String user_type= type;
                System.out.println("DEBUG: Username and Picture URl for User.java"+user_id+user_type);

                TypeDetails obj = new TypeDetails(user_name,pic_url,user_id,user_type);

                list.add(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        p = new Paginator(list,json_array_length);

        b_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage += 1;
                list_details.setAdapter(new TypeDetailsAdapter(getActivity().getApplicationContext(), R.layout.user_listview, p.generatePage(currentPage)));
                System.out.println("DEBUG: In Next and Current Page Value is "+currentPage);
                toggleButton();
            }
        });

        b_prev.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("DEBUG: In previous");
                currentPage -= 1;
                list_details.setAdapter(new TypeDetailsAdapter(getActivity().getApplicationContext(), R.layout.user_listview, p.generatePage(currentPage)));
                System.out.println("DEBUG: In Next and Current Page Value is "+currentPage);
                toggleButton();
            }

        });

        System.out.println("DEBUG: Current Data from Pagination"+p.generatePage(currentPage).toString());


        detailsAdapter = new TypeDetailsAdapter(getActivity().getApplicationContext(), R.layout.user_listview, p.generatePage(currentPage));

        list_details.setAdapter(detailsAdapter);


        list_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("DEBUG: Clicked item"+position);




            }
        });


        return view;
    }


    private void toggleButton(){
        if( currentPage == getTotalPages()){
            b_prev.setEnabled(true);
            b_next.setEnabled(false);
        } else if (currentPage == 0) {
            b_prev.setEnabled(false);
            b_next.setEnabled(true);
        }
        else if( currentPage >= 1 && currentPage < 2){
            b_next.setEnabled(true);
            b_prev.setEnabled(true);
        }
    }

}

