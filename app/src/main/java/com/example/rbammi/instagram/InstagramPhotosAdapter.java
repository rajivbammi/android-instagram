package com.example.rbammi.instagram;

import android.content.Context;
import android.text.format.DateUtils;
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
        TextView tvusername = (TextView) convertView.findViewById(R.id.tvUsername);
        ImageView ivProfileImg = (ImageView) convertView.findViewById(R.id.ivProfileImg);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        tvcaption.setText(photo.caption);
        tvusername.setText(photo.username);

        String timeStr = getRelativeTime(photo.timestamp);
        tvTime.setText(timeStr);

        ivphoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.imgUrl).into(ivphoto);


        ivProfileImg.setImageResource(0);
        Picasso.with(getContext()).load(photo.userImgUrl).transform(new CircleTransform()).into(ivProfileImg);
        return convertView;

    }

    // Utility functions.
    public String getRelativeTime(String timestamp) {
        Long timeVal = Long.parseLong(timestamp) * 1000;
        String timeStr = DateUtils.getRelativeTimeSpanString(timeVal, System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS).toString();
        return timeStr;
    }

}




