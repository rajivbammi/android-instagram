package com.example.rbammi.instagram;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Creating a photo object.
        InstagramPhoto photo = getItem(position);

        // Variables for views.
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //v.findViewById()
                System.out.println("wow!" + position);
            }
        });
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        ImageView ivProfileImg = (ImageView) convertView.findViewById(R.id.ivProfileImg);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

        // Setting up views value
        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvComment.setText("View All " + photo.commentCount + " Comments>>");

        String timeStr = getRelativeTime(photo.timestamp);
        tvTime.setText(timeStr);

        ivPhoto.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.imgUrl)
                .into(ivPhoto);

        ivProfileImg.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.userImgUrl)
                .transform(new CircleTransform())
                .into(ivProfileImg);


        // Add and show only 3 comments.
        int MAX_COMMENTS_TO_DISPLAY = 3;
        // Create comments view and show it.
        LinearLayout list = (LinearLayout) convertView.findViewById(R.id.llComments);
        list.removeAllViews();
        for(int i=0; i< MAX_COMMENTS_TO_DISPLAY; i++) {
            TextView tvDyncComment = new TextView(getContext());
            tvDyncComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tvDyncComment.setText((i + 1) + ". " + photo.commentList.get(i).cText);
            list.addView(tvDyncComment);
        }
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




