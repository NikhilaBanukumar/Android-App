package edu.usc.cs_server.csci571;

/**
 * Created by nikhilabanukumar on 4/21/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import org.json.JSONException;
import org.json.JSONObject;



public class Pager extends FragmentStatePagerAdapter {

    private JSONObject JsonResult;



    public Pager(FragmentManager fm, JSONObject result) {
        super(fm);
        this.JsonResult = result;

        System.out.println("DEBUG: In Pager constructer\n");

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        try {
            switch (position) {
                case 0:


                    System.out.println("DEBUG: in Pager Fragment is" + fragment);
                    //fragment = getItem(position);

                    String json_data = this.JsonResult.getString("user123");
                    System.out.println("DEBUG: In Pager Switch Case 0:\n" + json_data);
                    fragment = new users();
                    Bundle bundle = new Bundle();
                    bundle.putString("json_data", json_data);
                    bundle.putString("type", "user");
                    fragment.setArguments(bundle);
                    return fragment;
                //break;
                case 1:
                    String json_data1 = this.JsonResult.getString("page123");
                    System.out.println("DEBUG: In Pager Switch Case 1:\n" + json_data1);
                    fragment = new users();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("json_data", json_data1);
                    bundle1.putString("type", "page");
                    fragment.setArguments(bundle1);
                    return fragment;
                //break;


                case 2:
                    String json_data2 = this.JsonResult.getString("event123");
                    System.out.println("DEBUG: In Pager Switch Case 2:\n" + json_data2);
                    fragment = new users();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("json_data", json_data2);
                    bundle2.putString("type", "event");
                    fragment.setArguments(bundle2);
                    return fragment;
                //break;
                case 3:
                    String json_data3 = this.JsonResult.getString("place123");
                    System.out.println("DEBUG: In Pager Switch Case 3:\n" + json_data3);
                    fragment = new users();
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("json_data", json_data3);
                    bundle3.putString("type", "place");
                    fragment.setArguments(bundle3);
                    return fragment;
                //break;

                case 4:
                    String json_data4 = this.JsonResult.getString("group123");
                    System.out.println("DEBUG: In Pager Switch Case 4:\n" + json_data4);
                    fragment = new users();
                    Bundle bundle4 = new Bundle();
                    bundle4.putString("json_data", json_data4);
                    bundle4.putString("type", "group");
                    fragment.setArguments(bundle4);
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
        return 5;
    }


}