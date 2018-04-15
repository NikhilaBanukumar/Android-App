package edu.usc.cs_server.csci571;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class favorite extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
  //  private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager viewPager;
    favPagerAdapter adapter;
    ListView list_details;
    TypeDetailsAdapter detailsAdapter;
    String result;
    TabLayout tabLayout;
    int current_tab_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favfrag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarfav);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutfav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ImageView img=(ImageView) findViewById(R.id.imageView);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewfav);
        navigationView.setNavigationItemSelectedListener(this);

 //       List<TypeDetails> fav_list = new ArrayList<>();

//        SharedPreferences sp=getSharedPreferences("MYPREF",0);
//
//            Map<String, ?> allEntries = sp.getAll();
//            result = allEntries.toString();

//       for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
//
//           String user_id;
//           String user_type;
//           String picture_url;
//           String user_name;
//           TypeDetails fav_obj;
//            String entry_string = entry.getValue().toString();
//
//            try {
//                JSONArray entry_json = new JSONArray(entry_string);
//                user_id = entry_json.getString(0);
//                user_name = entry_json.getString(1);
//                user_type = entry_json.getString(2);
//                picture_url = entry_json.getString(3);
//                fav_obj = new TypeDetails(user_name, picture_url,user_id,user_type);
//                fav_list.add(fav_obj);
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//

//
//           System.out.println("map values"+entry.getKey() + ": " + entry.getValue());
//
//
//        }



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
       // mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
//        mViewPager = (ViewPager) findViewById(R.id.containerfav);
//        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabsfav);
        tabLayout.addTab(tabLayout.newTab().setText("Users").setIcon(R.drawable.users));
        tabLayout.addTab(tabLayout.newTab().setText("Pages").setIcon(R.drawable.pages));
        tabLayout.addTab(tabLayout.newTab().setText("Events").setIcon(R.drawable.events));
        tabLayout.addTab(tabLayout.newTab().setText("Places").setIcon(R.drawable.places));
        tabLayout.addTab(tabLayout.newTab().setText("Groups").setIcon(R.drawable.groups));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        System.out.println("DEBUG: Fav TAbs created Succesfully");

        System.out.println("DEBUG: Please Listen"+result);
        viewPager = (ViewPager) findViewById(R.id.containerfav);
        adapter = new favPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPager.setAdapter(adapter);
        System.out.println("DEBUG: Fav TAbs created Succesfully");


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                current_tab_pos = position;
                System.out.println("Current Tab"+current_tab_pos);
                tabLayout.setScrollPosition(position, 0, true);
                tabLayout.setSelected(true);
                //viewPager.setCurrentItem(current_tab_pos);
                //adapter.notifyDataSetChanged();
                //viewPager.setAdapter(adapter);

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
//                adapter.notifyDataSetChanged();
//                viewPager.setAdapter(adapter);
                //viewPager.setCurrentItem(current_tab_pos);

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
        viewPager.setCurrentItem(current_tab_pos);


//        viewPager = (ViewPager) findViewById(R.id.pager);
//        adapter = new Pager(getSupportFragmentManager(), result);
//        viewPager.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_favorite, menu);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutfav);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
