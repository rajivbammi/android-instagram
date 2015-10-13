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

    private static class ViewHolder {
        TextView tvComment;
        TextView tvUsername;
        TextView tvTime;
        TextView tvCaption;
        ImageView ivPhoto;
        ImageView ivProfileImg;
        LinearLayout list;
    }

    public InstagramPhotosAdapter(Context context, ArrayList<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int MAX_COMMENTS_TO_DISPLAY = 3;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.tvComment);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.ivProfileImg = (ImageView) convertView.findViewById(R.id.ivProfileImg);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.list = (LinearLayout) convertView.findViewById(R.id.llComments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Creating a photo object.
        InstagramPhoto photo = getItem(position);
        String timeStr = getRelativeTime(photo.timestamp);

        // Setting up views value
        viewHolder.tvCaption.setText(photo.caption);
        viewHolder.tvUsername.setText(photo.username);
        viewHolder.tvTime.setText(timeStr);

        viewHolder.tvComment.setText("View All " + photo.commentCount + " Comments>>");
        viewHolder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("wow!" + position);
            }
        });

        viewHolder.ivPhoto.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.imgUrl)
                .into(viewHolder.ivPhoto);

        viewHolder.ivProfileImg.setImageResource(0);
        Picasso.with(getContext())
                .load(photo.userImgUrl)
                .transform(new CircleTransform())
                .into(viewHolder.ivProfileImg);


        // Create comments view and show it.
        //LinearLayout list = (LinearLayout) convertView.findViewById(R.id.llComments);
        viewHolder.list.removeAllViews();
        int commentSize = photo.commentList.size();
        if(commentSize > MAX_COMMENTS_TO_DISPLAY) {
            commentSize = MAX_COMMENTS_TO_DISPLAY;
        }
        for (int i = 0; i < commentSize; i++) {
            TextView tvDyncComment = new TextView(getContext());
            tvDyncComment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            String res = (i + 1) + ". " + photo.commentList.get(i).cText;
            tvDyncComment.setText(res);
            viewHolder.list.addView(tvDyncComment);
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




