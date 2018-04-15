package edu.usc.cs_server.csci571;

import java.util.ArrayList;

/**
 * Created by nikhilabanukumar on 4/23/17.
 */

public class albumModel {

    String heading;
    ArrayList<String> imageURL;

    public albumModel(String heading){
        this.heading = heading;
        imageURL = new ArrayList<String>();
    }

    public void addimageURL(String url){
        imageURL.add(url);
    }

}
