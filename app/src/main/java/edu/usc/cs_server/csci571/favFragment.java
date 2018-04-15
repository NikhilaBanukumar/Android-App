package edu.usc.cs_server.csci571;

/**
 * Created by nikhilabanukumar on 4/18/17.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.Map;

public class favFragment extends Fragment {

    ListView list_details;
    ImageView chartImage;
    favDetailsAdapter detailsAdapter;
    JSONObject result;
    Button b_prev,b_next;
    private int currentPage = 0;

    @Override
    public void onResume() {
        super.onResume();

    }

    public int getTotalPages(){
        return Paginator.TOTAL_NUM_ITEMS/Paginator.ITEMS_PER_PAGE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users, container, false);
        b_prev = (Button) view.findViewById(R.id.btn_user_prv);
        b_next = (Button) view.findViewById(R.id.btn_user_nxt);
        b_prev.setVisibility(View.GONE);
        b_next.setVisibility(View.GONE);

        //int json_array_length = 0;
        String json_data = this.getArguments().getString("json_data");
        String type = this.getArguments().getString("type");

        System.out.println("DEBUG: In USers Fragment: Type"+type);
        System.out.println("DEBUG: In USers Fragment: JSONData"+json_data);

        List<TypeDetails> fav_list = new ArrayList<>();

        SharedPreferences sp=getActivity().getSharedPreferences("MYPREF",0);

        Map<String, ?> allEntries = sp.getAll();
       for (Map.Entry<String, ?> entry : allEntries.entrySet()) {

           String user_id;
           String user_type;
           String picture_url;
           String user_name;
           TypeDetails fav_obj;
            String entry_string = entry.getValue().toString();

            try {
                JSONArray entry_json = new JSONArray(entry_string);
                user_id = entry_json.getString(0);
                user_name = entry_json.getString(1);
                user_type = entry_json.getString(2);
                picture_url = entry_json.getString(3);
                System.out.println("User Type"+user_type+" "+type);
                if(user_type.equals(type)){
                    fav_obj = new TypeDetails(user_name, picture_url,user_id,user_type);
                    fav_list.add(fav_obj);
                    System.out.println("Fav Object"+fav_obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

           System.out.println("fav obj"+fav_list.toString());

        }



        list_details = (ListView) view.findViewById(R.id.t1);


        detailsAdapter = new favDetailsAdapter(getActivity().getApplicationContext(), R.layout.user_listview, fav_list);

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

