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

/**
 * Created by Ajay on 5/2/2016.
 */

public class albums extends Fragment {

    HashMap<String,List<String>> album_image_list;
    List<String> album_list;
    ExpandableListView list_details;
    albumsAdapter adapter;
    JSONObject result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums, container, false);


        int albums_length = 0;
        String hd_pic_1 = "https://graph.facebook.com/v2.8/";
        String hd_pic_2="/picture?access_token=EAAJ2M5gdCMABAPlhndDRGQmRIVQHvEfzl89A9e5h8IOFOtQZAUMFOQOcEhekjA14CI2WA2afQxXm8bZAjFeTYK6nHUUe12y0dZAtNYh63GNkalDi4M3u9p8SSloAeVBxbn1FZAOqUnPmLb6zygINj0UQJRlwDNoZD";

        String json_data = this.getArguments().getString("json_data");

        list_details = (ExpandableListView) view.findViewById(R.id.talbums);

        album_image_list = new HashMap<>();
        album_list = new ArrayList<>();


            try {
                result = new JSONObject(json_data);
                JSONArray json_array = (JSONArray) result.get("albums");
                albums_length = json_array.length();
                System.out.println("DEBUG: In albums.java: Album Array Length"+albums_length);

                for(int i = 0; i < albums_length; i++) {

                    String album_head = json_array.getJSONObject(i).getString("name");

                    album_list.add(album_head);

                    JSONArray photo_list = json_array.getJSONObject(i).getJSONArray("photos");
                    int plist_length = photo_list.length();

                        List<String> pic_list = new ArrayList<>();

                    for (int j = 0; j < plist_length; j++) {

                         //pic_list.add(photo_list.getJSONObject(j).getString("picture"));
                        pic_list.add(hd_pic_1+photo_list.getJSONObject(j).getString("id")+hd_pic_2);
                    }
                    System.out.println("fhsufhua:"+pic_list.toString());

                   album_image_list.put(album_head,pic_list);

                }
                System.out.println("DEBUG: In albums List"+album_list.toString());
                System.out.println("DEBUG: In albums HashMap"+album_image_list.toString());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }

          adapter =new albumsAdapter(getActivity().getApplicationContext(),album_image_list,album_list);

//        detailsAdapter = new TypeDetailsAdapter(getActivity().getApplicationContext(), R.layout.user_listview, list);
//
        list_details.setAdapter(adapter);

        list_details.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println("DEBUG: Clicked item"+position);




            }
        });


        return view;
    }

}

