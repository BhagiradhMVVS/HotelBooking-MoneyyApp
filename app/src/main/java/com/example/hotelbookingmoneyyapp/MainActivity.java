package com.example.hotelbookingmoneyyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String API_URL = "http://fake-hotel-api.herokuapp.com/api/hotels";

    Animation anim_from_button, anim_from_top, anim_from_left;

    ProgressDialog dialog;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Load Animations
        anim_from_button = AnimationUtils.loadAnimation(this, R.anim.anim_from_bottom);
        anim_from_top = AnimationUtils.loadAnimation(this, R.anim.anim_from_top);
        anim_from_left = AnimationUtils.loadAnimation(this, R.anim.anim_from_left);

        ListView listView = findViewById(R.id.root_view);

        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        this.getWindow().getDecorView().setSystemUiVisibility(

                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        );

        ItemsAsyncTask task = new ItemsAsyncTask();
        dialog = ProgressDialog.show(MainActivity.this, "",
                "Loading. Please wait...", true);
        task.execute();

        listView.setAnimation(anim_from_button);
    }

    private class ItemsAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return getHttpResponse(API_URL);
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Item> Items = parseJson(result);

            ListView rootView = (ListView) findViewById(R.id.root_view);
            ItemAdapter adapter = new ItemAdapter(MainActivity.this, Items);
            rootView.setAdapter(adapter);

            rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent i = new Intent(MainActivity.this, ItemActivity.class);
                    i.putExtra("ID", Items.get(position).getId());
                    i.putExtra("NAME", Items.get(position).getName());
                    i.putExtra("STARS", Items.get(position).getRating());
                    i.putExtra("DES", Items.get(position).getDes());
                    startActivity(i);
                }
            });
        }
    }

    private URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        }
        catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return url;
    }

    private String getHttpResponse(String urlString) {
        StringBuilder jsonResponse = new StringBuilder();

        URL url = createUrl(urlString);

        HttpURLConnection con = null;
        InputStream inputStream = null;

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            inputStream = con.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            while (true) {
                String line = reader.readLine();
                if(line == null)
                    break;
                jsonResponse.append(line);
            }

            inputStream.close();
            con.disconnect();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return jsonResponse.toString();
    }

    private ArrayList<Item> parseJson(String response) {
        Log.d("sample", "Response: "+response);
        ArrayList<Item> items = new ArrayList<>();

        try {
            Log.d("sample", "tryResponse: "+response);

            JSONArray jsonArray = new JSONArray(response);

            Log.d("sample", "jsonArr: "+jsonArray);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name = object.getString("name");
                String city = object.getString("city");
                String country = object.getString("country");
                int price = Integer.parseInt(object.getString("price"));
                int rating = Integer.parseInt(object.getString("stars"));
//                JSONArray images = object.getJSONArray("images");
                String image = "";
                String id = object.getString("id");

                String des = object.getString("description");

                items.add(new Item(name, image, city, country, price, rating, id, des));
            }
            dialog.dismiss();
        }
        catch (JSONException ex) {
            Log.d("sample", "parseJson errrr: "+ ex);
            ItemsAsyncTask task = new ItemsAsyncTask();
            task.execute();
            ex.printStackTrace();
        }

        return items;
    }
}