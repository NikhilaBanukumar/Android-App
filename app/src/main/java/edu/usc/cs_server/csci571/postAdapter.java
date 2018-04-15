package edu.usc.cs_server.csci571;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nikhilabanukumar on 4/23/17.
 */

public class postAdapter extends ArrayAdapter<postDetails> {
    private Context mContext;
    private int res;
    private LayoutInflater mLayoutInflater;
    List<postDetails> list;

    public postAdapter(Context context, int resource, List<postDetails> objects) {
        super(context, resource, objects);
        System.out.println("convertview context:"+context);
        list = objects;
        mContext = context;
        res = resource;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder{
        ImageView postpic;
        TextView name;
        TextView date;
        TextView message;
    }

        @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.post_listview, parent, false);
            System.out.println("convertview:"+convertView);
            holder = new ViewHolder();
            holder.postpic = (ImageView) convertView.findViewById(R.id.postimage);
            holder.name = (TextView) convertView.findViewById(R.id.postname);
            holder.date= (TextView) convertView.findViewById(R.id.postdate);
            holder.message= (TextView) convertView.findViewById(R.id.postmessage);
            convertView.setTag(holder);

        }
        else {

            holder = (ViewHolder) convertView.getTag();
        }


        postDetails item = getItem(position);
        System.out.println("DEBUG: Item name " + item.imageUrl);

        holder.name.setText(item.Name);
        holder.name.setTypeface(null, Typeface.BOLD);
        holder.message.setText(item.message);
        holder.date.setText(item.date);
        Picasso.with(mContext).load(item.imageUrl).into(holder.postpic);

        return convertView;
    }


}


