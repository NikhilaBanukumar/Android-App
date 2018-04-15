package edu.usc.cs_server.csci571;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikhilabanukumar on 4/21/17.
 */

public class fbmodel {
    public String Name;
    public String pic_url;
    public String iduser;

    public fbmodel(JSONObject result){

        try {
            String user=result.getString("user123");
            JSONObject dataObj = new JSONObject(user);
            JSONArray Output= (JSONArray) dataObj.get("data");
            this.Name= Output.getJSONObject(0).getString("name");
            this.pic_url= Output.getJSONObject(0).getJSONObject("picture").getJSONObject("data").getString("url");
            this.iduser=Output.getJSONObject(0).getString("id");

            System.out.println("In fbmodel constructor:"+this.Name+this.pic_url+this.iduser);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
