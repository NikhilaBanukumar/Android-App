package edu.usc.cs_server.csci571;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.key;
import static edu.usc.cs_server.csci571.R.id.imageView;



public class TypeDetailsAdapter extends ArrayAdapter<TypeDetails> {

    private Context mContext;
    private int res;
    private LayoutInflater mLayoutInflater;
    List<TypeDetails> list;

    public TypeDetailsAdapter(Context context, int resource, List<TypeDetails> objects) {
        super(context, resource, objects);
        list = objects;
        mContext = context;
        res = resource;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder{
        TextView tv;
        ImageView iv;
    }

    public boolean isSetFavorites(String key){
        SharedPreferences sp= mContext.getSharedPreferences("MYPREF",0);
        return sp.contains(key);
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder holder;
        String key;
        TypeDetails item = getItem(position);
        key = item.user_id+item.user_type;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate( res, null );

            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.username);
            holder.iv = (ImageView) convertView.findViewById(R.id.profilepic);

            convertView.setTag(holder);

        }
        else {

            holder = (ViewHolder) convertView.getTag();
        }

            System.out.println("DEBUG: Position" + position);
            holder.tv.setText(item.userName);
            Picasso.with(mContext).load(item.imageUrl).into(holder.iv);


            ImageView iv_fav = (ImageView) convertView.findViewById(R.id.fav) ;
            ImageView iv_detail=(ImageView) convertView.findViewById(R.id.detail);
            ImageView iv_favorite = (ImageView) convertView.findViewById(R.id.fav);

            if(isSetFavorites(key)){
                iv_favorite.setImageResource(R.drawable.favorites_on);
            }

            iv_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pref_key;

                    SharedPreferences sp= mContext.getSharedPreferences("MYPREF",0);
                    TypeDetails obj= getItem(position);

                    pref_key = obj.user_id+obj.user_type;

                    ImageView iv1 = (ImageView) v.findViewById(R.id.fav);
                    //System.out.println("venky"+sp.getString(pref_key, "[]"));
                    if(!sp.contains(pref_key)){
                        System.out.println("enters the loop");
                        Set<String> obj_values = new LinkedHashSet<>();
                        obj_values.add(obj.user_id);
                        obj_values.add(obj.userName);
                        obj_values.add(obj.user_type);
                        obj_values.add(obj.imageUrl);
                        JSONArray jsonArray = new JSONArray(obj_values);
         //               SharedPreferences sp= mContext.getSharedPreferences("MYPREF",0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(pref_key, jsonArray.toString());
                        editor.commit();
                        iv1.setImageResource(R.drawable.favorites_on);


                    }


                    else{
                        iv1.setImageResource(R.drawable.favoritesoff);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.remove(pref_key);
                        editor.apply();
                        System.out.println(sp.getString(pref_key,"venky"));
                    }
//                    String json_obj = sp.getString(pref_key,"");
//
//                    System.out.println("venky"+json_obj);

                    System.out.println("DEBUG: In Fav");
                }
            });

            iv_detail.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    TypeDetails obj= getItem(position);
                    System.out.println("DEBUG:details values "+obj.userName);
                    Intent intent = new Intent(getContext(),userdetails.class);
                    intent.putExtra("object",obj);
                    mContext.startActivity(intent);
                }
            });

        return convertView;
    }



}
