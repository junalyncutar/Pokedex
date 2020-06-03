package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewAbilityAdapter extends RecyclerView.Adapter<RecyclerViewAbilityAdapter.ViewHolder> {

    private List<PokedexAbilityModel> abilityModelList;
    private Context context;

    // data is passed into the constructor
    RecyclerViewAbilityAdapter(List<PokedexAbilityModel> abilityModelList, Context context) {
        this.abilityModelList = abilityModelList;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokedex_list_ability, parent, false);
        return new ViewHolder(v);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PokedexAbilityModel listItem = abilityModelList.get(position);
        holder.textViewAbility.setText(listItem.getPokeAbility());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return abilityModelList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAbility;

        ViewHolder(View itemView) {
            super(itemView);
            textViewAbility = itemView.findViewById(R.id.textViewAbilityName);
        }
    }
}