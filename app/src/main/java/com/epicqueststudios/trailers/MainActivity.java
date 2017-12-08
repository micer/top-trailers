package com.epicqueststudios.trailers;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String apiKey;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    public OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiKey = getString(R.string.app_id);;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        });
        downloadTrailers("https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&query=Matter");
    }

    private void downloadTrailers(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();

                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    JSONObject responseJson = new JSONObject(responseBody.string());
                    populateList(responseJson);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateList(JSONObject json) throws JSONException {
        final JSONArray results = json.getJSONArray("results");
        final ArrayList<TrailerItem> trailers = new ArrayList<>();

        for (int i = 0; i < results.length(); i++) {
            JSONObject item = results.getJSONObject(i);
            trailers.add(new TrailerItem(item));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Creating adapter: "+results.length());
                TrailerAdapter adapter = new TrailerAdapter(trailers);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public static void onTrailerClickListener(TrailerItem trailerItem) {

    }
}
