package edu.usc.cs_server.csci571;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by nikhilabanukumar on 4/23/17.
 */

public class albumsAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private HashMap<String,List<String>> album_list;
    private List<String> image_list;
    private LayoutInflater mLayoutInflater;

    public albumsAdapter(Context ctx, HashMap<String,List<String>> album_list,List<String> image_list){
        this.album_list = album_list;
        this.image_list = image_list;
        this.ctx = ctx;
        this.mLayoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        System.out.println("DEBUG: inflater"+this.mLayoutInflater);
    }

    @Override
    public int getGroupCount() {
        return album_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return album_list.get(image_list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return image_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return album_list.get(image_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group_title = (String) getGroup(groupPosition);
        System.out.println("Group Title"+group_title);
        if(convertView == null){

            //LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate(R.layout.album_heading,parent,false);
            System.out.println("DEBUG: In Albums adapter In convert view");
        }
        System.out.println("DEBUG: In Albums adapter In convert view"+convertView);
        TextView tv = (TextView) convertView.findViewById(R.id.albumhead);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(group_title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String child_title = (String) getChild(groupPosition,childPosition);
        System.out.println("DEBUG: In Albums adapter child view"+child_title);

            convertView = mLayoutInflater.inflate(R.layout.albums_listview,parent,false);

            ImageView iv = (ImageView)convertView.findViewById(R.id.albumimage);
            Picasso.with(ctx).load(child_title).into(iv);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
