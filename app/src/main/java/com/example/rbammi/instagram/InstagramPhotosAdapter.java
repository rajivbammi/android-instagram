package com.example.rbammi.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rbammi on 10/7/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter <InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, ArrayList<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvcaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivphoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        tvcaption.setText(photo.caption);

        ivphoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imgUrl).into(ivphoto);
        return convertView;
    }
}


