package com.epicqueststudios.trailers;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Henry on 12/6/2017.
 */

class TrailerItem {
    private final JSONObject item;
    private String posterPath;
    private String title;

    public TrailerItem(JSONObject item) {
        this.item = item;
        try {
            this.title = item.getString("title");
            this.posterPath = item.getString("poster_path");
            Log.d("test01", title+"      posterpath: "+posterPath);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String createImageUrl() {
        return ApiUtils.getImageUrl(posterPath);
    }

    public boolean hasImageUrl() {
        return posterPath != null && !posterPath.isEmpty();
    }
}
