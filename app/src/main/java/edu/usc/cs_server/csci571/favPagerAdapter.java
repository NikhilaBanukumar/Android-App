package edu.usc.cs_server.csci571;

/**
 * Created by nikhilabanukumar on 4/21/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class favPagerAdapter extends FragmentStatePagerAdapter {

    private String Result;
    Context mContext;



    public favPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mContext = context;

        System.out.println("DEBUG: In FAv Pager constructer\n");

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;


            SharedPreferences sp= mContext.getSharedPreferences("MYPREF",0);

            Map<String, ?> allEntries = sp.getAll();
            Result = allEntries.toString();

            switch (position) {
                case 0:
                    System.out.println("DEBUG: in Pager Fragment is" + fragment);
                    //fragment = getItem(position);

                    fragment = new favFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("json_data", Result);
                    bundle.putString("type", "user");
                    fragment.setArguments(bundle);
                    return fragment;
                //break;
                case 1:

                    fragment = new favFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("json_data", Result);
                    bundle1.putString("type", "page");
                    fragment.setArguments(bundle1);
                    return fragment;
                //break;


                case 2:
                    fragment = new favFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("json_data", Result);
                    bundle2.putString("type", "event");
                    fragment.setArguments(bundle2);
                    return fragment;                //break;
                case 3:
                    fragment = new favFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("json_data", Result);
                    bundle3.putString("type", "place");
                    fragment.setArguments(bundle3);
                    return fragment;                //break;
                //break;

                case 4:
                    fragment = new favFragment();
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("json_data", Result);
                    bundle4.putString("type", "group");
                    fragment.setArguments(bundle4);
                    return fragment;
                //break;

                default:
                    fragment = null;
                    return fragment;
                //break;
            }

    }


    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return 5;
    }


}