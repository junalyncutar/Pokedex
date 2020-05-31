package com.example.pokedex;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class PokedexDetail extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex_item_detail);

        // Get Intents
        getIncomingIntent();
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

            setImage(poke_Id,poke_Url,poke_Name);

            getSupportActionBar().setTitle(String.format("%s %s",poke_Id,poke_Name));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private void setImage(String pokeId, String pokeUrl, String pokeName) {
        Log.d("MainActivity","set_image: setting image");

        // Image in front detail
        ImageView imageViewUrl = findViewById(R.id.imageViewFrontDetail);
        Picasso.with(this)
                .load(pokeUrl)
                .into(imageViewUrl);

        // Name
        TextView name = findViewById(R.id.textViewName);
        name.setText(pokeName);

        // Url
        TextView url = findViewById(R.id.textViewUrl);
        url.setText(pokeUrl);

        // Type Name
        TextView type = findViewById(R.id.textViewTypeName);

        // Ability Name
        TextView ability_name = findViewById(R.id.textViewAbilityName);

        // Stats Name
        TextView stats_value = findViewById(R.id.textViewStatsName);

        // Stats Value
        TextView stats_name = findViewById(R.id.textViewStatsValue);

    }
}