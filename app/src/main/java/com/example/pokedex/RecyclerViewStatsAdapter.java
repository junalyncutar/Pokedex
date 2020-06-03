package com.example.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerViewStatsAdapter extends RecyclerView.Adapter<RecyclerViewStatsAdapter.ViewHolder> {

    private List<PokedexStatsModel> statsModelList;
    private Context context;

    // data is passed into the constructor
    RecyclerViewStatsAdapter(List<PokedexStatsModel> statsModelList, Context context) {
        this.statsModelList = statsModelList;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokedex_list_stats, parent, false);
        return new ViewHolder(v);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PokedexStatsModel listItem = statsModelList.get(position);
        holder.textViewStatsValue.setText(listItem.getPokeStatsValue());
        holder.textViewStatsName.setText(listItem.getPokeStatsName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return statsModelList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewStatsName;
        TextView textViewStatsValue;

        ViewHolder(View itemView) {
            super(itemView);
            textViewStatsName = itemView.findViewById(R.id.textViewStatsName);
            textViewStatsValue = itemView.findViewById(R.id.textViewStatsValue);
        }
    }
}