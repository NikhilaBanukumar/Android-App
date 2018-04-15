package edu.usc.cs_server.csci571;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.LinkedHashSet;
import java.util.Set;

import static edu.usc.cs_server.csci571.MainActivity.makeHttpGetRequest;

public class userdetails extends AppCompatActivity {
    private Target loadtarget;
    ViewPager viewPager;
    TabLayout tabLayout;
    Pager_details adapter;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    public JSONObject for_fb;
    public String fb_url;
    public Bitmap fb_bitmap;
    public TypeDetails obj_fav;

  //  public static String albums_posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        TypeDetails result = (TypeDetails) getIntent().getSerializableExtra("object");
        obj_fav = result;
        String user_id=result.user_id;



        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        // this part is optional
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
//                System.out.println("Post ID"+result.getPostId());
//                if(result.getPostId() == null) {
//                    Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
//                } else {
                    Toast.makeText(getApplicationContext(), "FB Post Successful", Toast.LENGTH_SHORT).show();
                }
//            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Post Failed", Toast.LENGTH_SHORT).show();
            }
        });



        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        tabLayout = (TabLayout) findViewById(R.id.tabsdetail);
        tabLayout.addTab(tabLayout.newTab().setText("Albums").setIcon(R.drawable.albums));
        tabLayout.addTab(tabLayout.newTab().setText("Posts").setIcon(R.drawable.posts));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        System.out.println("DEBUG: TAbs created Succesfully");

        //Find the View Pager
        viewPager = (ViewPager) findViewById(R.id.containerdetail);
        if(true) {
            System.out.println("helloooooo");

            class getalbumsposts extends AsyncTask<String, Void, String> {

                protected String doInBackground(String... params) {
                    System.out.print("Clear Text is " + params[0]);
                    String url = "http://sample-env-1.6vd9zq2qsg.us-west-2.elasticbeanstalk.com/?id=" + params[0];
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {
                        String result1 = makeHttpGetRequest(url);
                        System.out.println("DEBUG: In Http Call" + result1);
                        //System.out.println(result1);
                        return result1;
                    }

                    return "Returning Null";

                }

                @Override
                protected void onPostExecute(String result1) {

                    super.onPostExecute(result1);
                    sendData(result1);
                //    albums_posts = result1;

                    System.out.println("DEBUG: In Post execute" + result1);
                //    System.out.println("DEBUG: In Post albums_posts execute" + albums_posts);
                }

            }
            getalbumsposts sendPostReqAsyncTask = new getalbumsposts();
            sendPostReqAsyncTask.execute(user_id);
        }



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageSelected(int position) {
            tabLayout.setScrollPosition(position, 0, true);
            tabLayout.setSelected(true);
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    });

    //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}
        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    });
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //MenuItem layout=menu.findItem(R.id.fav_settings);

        getMenuInflater().inflate(R.menu.menu_userdetails, menu);
        MenuItem layout = menu.getItem(0);
        if(isSetFavorites(obj_fav.user_id+obj_fav.user_type)) {
            layout.setTitle("Remove from Favorites");
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){

          // users fg=(users) getFragmentManager().beginTransaction().d.;
            onBackPressed();
            return true;
        }


        if(id == R.id.fav_settings){
            SharedPreferences sp=getApplicationContext().getSharedPreferences("MYPREF",0);
            String pref_key=obj_fav.user_id+obj_fav.user_type;


            if(isSetFavorites(pref_key)){

                SharedPreferences.Editor editor = sp.edit();
                editor.remove(pref_key);
                editor.apply();
                //MenuItem fav_item = (MenuItem) findViewById(R.id.fav_settings);
                item.setTitle(R.string.fav_add);
                CharSequence text = "Removed from Favorites";
                int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(),text, duration);
                    toast.show();
                System.out.println(sp.getString(pref_key,"nikhila"));
            }
            else{
                System.out.println("enters the loop");
                Set<String> obj_values = new LinkedHashSet<>();
                obj_values.add(obj_fav.user_id);
                obj_values.add(obj_fav.userName);
                obj_values.add(obj_fav.user_type);
                obj_values.add(obj_fav.imageUrl);
                JSONArray jsonArray = new JSONArray(obj_values);
                //               SharedPreferences sp= mContext.getSharedPreferences("MYPREF",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(pref_key, jsonArray.toString());
                editor.commit();
                CharSequence text = "Added to Favorites";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(),text, duration);
                toast.show();

                //MenuItem fav_item = (MenuItem) findViewById(R.id.fav_settings);
                item.setTitle("Remove from Favorites");
            }

            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.share_settings) {

            loadBitmap(fb_url);
            if (ShareDialog.canShow(ShareLinkContent.class)) {


                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(obj_fav.userName)
                        .setContentDescription("FB SEARCH FROM USC CSCI 571")
                        .setContentUrl(Uri.parse("https://developers.facebook.com/android"))
                        .setImageUrl(Uri.parse(obj_fav.imageUrl))
                        .build();
                shareDialog.show(linkContent, ShareDialog.Mode.AUTOMATIC);

//                SharePhoto sharePhoto1 = new SharePhoto.Builder()
//                        .setBitmap(fb_bitmap)
//                        .setCaption("FB POST FROM CSCI571")
//                        .build();
//
////                ShareFeedContent feed=new ShareFeedContent.Builder()
////                        .setLinkName(obj_fav.userName)
////                        .setLinkDescription("i hate you")
////                        .setPicture(obj_fav.imageUrl)
////                        .build();
//
//                ShareContent content = new ShareMediaContent.Builder()
//                        .addMedium(sharePhoto1)
//                        .build();
//
//                System.out.println("FB URL"+fb_url);
//                System.out.println("Bitmap for Fb is "+fb_bitmap);
//
//                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
            }



            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isSetFavorites(String key){
        SharedPreferences sp= this.getSharedPreferences("MYPREF",0);
        return sp.contains(key);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    protected void sendData(String result){
        System.out.println("DEBUG: Albums and Posts"+result);
        if(result.contains("Graph returned an error")){
            result = "{}";
            System.out.println("EMpty JSON");
        }

        try {
            for_fb = new JSONObject(result);
             fb_url = for_fb.getJSONObject("picture").getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }




        adapter = new Pager_details(getSupportFragmentManager(),result);
        viewPager.setAdapter(adapter);

    }



    public void loadBitmap(String url) {
        loadtarget = new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                handleLoadedBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(this).load(url).into(loadtarget);
    }

    public void handleLoadedBitmap(Bitmap b) {
        fb_bitmap = b;

    }


    }

