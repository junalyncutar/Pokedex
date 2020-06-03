package com.example.pokedex;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PokedexDetail extends AppCompatActivity {

    private String pokeSprites;
    private ArrayList<String> pokeTypeArray;

    private TextView textViewPokeType;
    private ImageView imageViewSprites;

    private static String URL_DATA = "";

    // RecyclerView for Stats
    private RecyclerView recyclerViewPokeStats;
    private RecyclerView.Adapter adapterStats;
    private List<PokedexStatsModel> listItemsStats;

    // RecyclerView for Ability
    private RecyclerView recyclerViewPokeAbility;
    private RecyclerView.Adapter adapterAbility;
    private List<PokedexAbilityModel> listItemsAbility;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex_item_detail);

        // SPRITES
        imageViewSprites = findViewById(R.id.imageViewSprites);

        // TYPES
        textViewPokeType = findViewById(R.id.textViewTypeName);

        // STATS
        recyclerViewPokeStats = (RecyclerView) findViewById(R.id.recyclerViewStats);
        recyclerViewPokeStats.setHasFixedSize(true);
        recyclerViewPokeStats.setLayoutManager(new LinearLayoutManager(this));
        listItemsStats = new ArrayList<>();

        // ABILITY
        recyclerViewPokeAbility = (RecyclerView) findViewById(R.id.recyclerViewAbility);
        recyclerViewPokeAbility.setHasFixedSize(true);
        recyclerViewPokeAbility.setLayoutManager(new LinearLayoutManager(this));
        listItemsAbility = new ArrayList<>();

        // Get Intents
        getIncomingIntent();

        // START REQUEST THEN LOAD DATA
        loadPokeData();
    }

    private void loadPokeData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        // Set sprites
                        setPokeSprites(response);

                        // Set type
                        setPokeType(response);

                        // Set abilities
                        setPokeAbility(response);

                        // Set stats name and base stats
                        setPokeStats(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private  void getIncomingIntent() {
        Log.d("MainActivity","getIncomingIntent: checking for incoming intents.");
        if (getIntent().hasExtra("poke_url") && getIntent().hasExtra("poke_name")) {
            Log.d("MainActivity","getIncomingIntent: found intent extras.");
            String poke_Id = getIntent().getStringExtra("poke_id");
            String poke_Url = getIntent().getStringExtra("poke_url");
            String poke_Name = getIntent().getStringExtra("poke_name");

            URL_DATA = poke_Url;

            getSupportActionBar().setTitle(String.format("%s %s",poke_Id,poke_Name));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            // Name
            TextView name = findViewById(R.id.textViewName);
            name.setText(poke_Name);
        }
    }

    private void setPokeSprites(String response) {
        try {
            JSONObject json = new JSONObject(response);
            JSONObject sprites = json.getJSONObject("sprites");
            pokeSprites = sprites.getString("front_default");

            // Image in front detail
            Picasso.with(this)
                    .load(pokeSprites)
                    .into(imageViewSprites);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPokeType(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray typesArray = jsonObject.getJSONArray("types");
            pokeTypeArray = new ArrayList<String>();
            String str = "";

            JSONArray array = sortJsonArray(typesArray);

            for (int i = 0; i < array.length(); i++) {
                JSONObject objectTypes = array.getJSONObject(i);
                JSONObject objectType = objectTypes.getJSONObject("type");

                // Get name array
                String name = objectType.getString("name");

                // Set first letter to uppercase format
                String nameUpperCase = name.substring(0, 1).toUpperCase() + name.substring(1);

                // Add to array list
                pokeTypeArray.add(nameUpperCase);

                // Set to TextView
                textViewPokeType.append(nameUpperCase + "  ");

                str = String.format("%s  ", nameUpperCase);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray sortJsonArray(JSONArray array) {
        // SORT SLOT
        ArrayList<JSONObject> jsonObjectArray = new ArrayList<JSONObject>();
        for (int i = 0; i < array.length(); i++) {
            try {
                jsonObjectArray.add(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(jsonObjectArray, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject lhs, JSONObject rhs) {
                try {
                    String lid = lhs.getString("slot");
                    String rid = rhs.getString("slot");
                    // Here you could parse string id to integer and then compare.
                    return lid.compareTo(rid);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        return new JSONArray(jsonObjectArray);
    }

    private void setPokeAbility(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("abilities");

            for (int i = 0; i < array.length(); i++) {
                JSONObject abilities = array.getJSONObject(i);
                JSONObject ability = abilities.getJSONObject("ability");

                // Get name array
                String name = ability.getString("name");

                // Set first letter to uppercase format
                String nameUpperCase = name.substring(0, 1).toUpperCase() + name.substring(1);

                PokedexAbilityModel item = new PokedexAbilityModel(
                        nameUpperCase
                );
                listItemsAbility.add(item);
            }

            adapterAbility = new RecyclerViewAbilityAdapter(listItemsAbility, getApplicationContext());
            recyclerViewPokeAbility.setAdapter(adapterAbility);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPokeStats(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray typesArray = jsonObject.getJSONArray("stats");

            for (int i = 0; i < typesArray.length(); i++) {
                JSONObject objectStats = typesArray.getJSONObject(i);

                // Get stat name
                JSONObject objectStat = objectStats.getJSONObject("stat");
                String statNameStr = objectStat.getString("name");
                String statNameUpperCase = statNameStr.substring(0, 1).toUpperCase() + statNameStr.substring(1);

                // Get base_stat value
                String baseStatStr = objectStats.getString("base_stat");
                String baseStatUpperCase = baseStatStr.substring(0, 1).toUpperCase() + baseStatStr.substring(1);

                PokedexStatsModel item = new PokedexStatsModel(
                        statNameUpperCase,
                        baseStatUpperCase
                );
                listItemsStats.add(item);
            }

            adapterStats = new RecyclerViewStatsAdapter(listItemsStats, getApplicationContext());
            recyclerViewPokeStats.setAdapter(adapterStats);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}