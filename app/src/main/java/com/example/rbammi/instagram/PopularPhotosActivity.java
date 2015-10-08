package com.example.rbammi.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PopularPhotosActivity extends AppCompatActivity {

    private String CLIENT_ID = "367bb259c6de4d8a8ae9910d1290325f";
    private String instaURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_photos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        photos = new ArrayList<>();
        photosAdapter = new InstagramPhotosAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photosAdapter);
        fetchPhotosData();
    }

    public void fetchPhotosData() {
        AsyncHttpClient client = new AsyncHttpClient();
            client.get(instaURL, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //super.onSuccess(statusCode, headers, response);
                    Log.i("DEBUG", response.toString());
                    // Decode item and convert into Java object.
                    JSONArray photoJson = null;
                    try{
                        photoJson = response.getJSONArray("data");

                        for (int i = 0; i < photoJson.length(); i++) {
                            InstagramPhoto photo = new InstagramPhoto();
                            JSONObject photoObj = photoJson.getJSONObject(i);
                            photo.username  = photoObj.getJSONObject("user").getString("username");
                            photo.caption = photoObj.getJSONObject("caption").getString("text");
                            photo.imgUrl = photoObj.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                            photo.imgHeight = photoObj.getJSONObject("images").getJSONObject("standard_resolution").getString("height");
                            photo.likesCount = photoObj.getJSONObject("likes").getString("count");
                            photos.add(photo);
                        }
                    } catch(JSONException e) {
                        Log.i("Error", "Unable to parse the feed", e);
                    }
                    // Update the list view
                    photosAdapter.notifyDataSetChanged();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.i("DEBUG", "Unable to fetch feed");
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
