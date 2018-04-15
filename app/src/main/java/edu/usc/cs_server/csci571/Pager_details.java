package edu.usc.cs_server.csci571;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhilabanukumar on 4/22/17.
 */

public class Pager_details extends FragmentStatePagerAdapter {

    private String JsonResult;

    public Pager_details(FragmentManager fm, String result) {
        super(fm);

        this.JsonResult = result;
        System.out.println("DEBUG: In Pager Details constructer"+JsonResult);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        try {
            switch (position) {
                case 0:
                    //System.out.println("DEBUG: in Pager Fragment is" + fragment);
                    //fragment = getItem(position);

 //                   JSONObject data= new JSONObject(JsonResult);
//                    JSONArray json_data = data.getJSONArray("albums");


                    System.out.println("DEBUG: In Pager Details Switch Case 0:" + JsonResult);
                     fragment = new albums();
                    Bundle bundle = new Bundle();
                    bundle.putString("json_data", JsonResult);
                    fragment.setArguments(bundle);
                    return fragment;
                //break;
                case 1:
                    JSONObject data1= new JSONObject(JsonResult);
//                    JSONArray json_data1 = data1.getJSONArray("posts");

//                    System.out.println("DEBUG: In Pager Details Switch Case 1:\n" + json_data1);
                    fragment = new posts();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("json_data", JsonResult);
////                    bundle1.putString("type", "page");
                    fragment.setArguments(bundle1);
                    return fragment;
                //break;

                default:
                    fragment = null;
                    return fragment;
                //break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return 2;
    }


}

