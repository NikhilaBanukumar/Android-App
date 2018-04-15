package edu.usc.cs_server.csci571;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.share.Sharer;
//import com.facebook.share.model.ShareLinkContent;
//import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class result extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    int currentTabpos;
    Menu globalMenu;
    Pager adapter;

    JSONObject result;


    boolean fav = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            result = new JSONObject(getIntent().getExtras().getString("json"));
//            fbmod = new fbmodel(result);
//            actionBar.setTitle(result.Name);
            System.out.println("DEBUG:In result.java JSON is " + result);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText("Users").setIcon(R.drawable.users));
        tabLayout.addTab(tabLayout.newTab().setText("Pages").setIcon(R.drawable.pages));
        tabLayout.addTab(tabLayout.newTab().setText("Events").setIcon(R.drawable.events));
        tabLayout.addTab(tabLayout.newTab().setText("Places").setIcon(R.drawable.places));
        tabLayout.addTab(tabLayout.newTab().setText("Groups").setIcon(R.drawable.groups));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        System.out.println("DEBUG: TAbs created Succesfully");
        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new Pager(getSupportFragmentManager(), result);
        viewPager.setAdapter(adapter);
        System.out.println("DEBUG: TAbs created Succesfully");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentTabpos = position;
                System.out.println("Current Tab"+currentTabpos);
                tabLayout.setScrollPosition(position, 0, true);
                tabLayout.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    @Override
    public void onResume(){
        System.out.println("Resuming 1st activity");
        super.onResume();

        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentTabpos);


//        viewPager = (ViewPager) findViewById(R.id.pager);
//        adapter = new Pager(getSupportFragmentManager(), result);
//        viewPager.setAdapter(adapter);
    }

}
