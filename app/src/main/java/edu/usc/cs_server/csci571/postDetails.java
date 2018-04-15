package edu.usc.cs_server.csci571;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by nikhilabanukumar on 4/23/17.
 */

public class postDetails implements Serializable {

    public String Name;
    public String imageUrl;
    public String message;
    public String date;



    public postDetails(String name, String picture,String message,String date){
        this.Name = name;
        this.imageUrl = picture;
        this.message=message;
        this.date=date;
        //        new ImageLoadTask(this.imageUrl).execute();

    }

}
