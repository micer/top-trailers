package com.epicqueststudios.trailers;

/**
 * Created by Henry on 12/6/2017.
 */

public final class ApiUtils {

    final public static String IMAGE_URL = "https://image.tmdb.org/t/p/w150";


    public static String getImageUrl(String posterPath) {
        return IMAGE_URL.concat(posterPath);
    }
}
