package edu.usc.cs_server.csci571;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nikhilabanukumar on 4/21/17.
 */

public class TypeDetails implements Serializable {
    public String userName;
    public String imageUrl;
    public Bitmap myBitmap;
    public String user_id;
    public String user_type;



    public TypeDetails(String name, String url,String user_id,String user_type){
        this.userName = name;
        this.imageUrl = url;
        this.user_id=user_id;
        this.user_type=user_type;
        //        new ImageLoadTask(this.imageUrl).execute();
        System.out.println("DEBUG:Bitmap"+myBitmap);
    }


}

