package com.example.pokedex;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<PokedexListModel> listItems;
    private Context context;

    public RecyclerViewAdapter(List<PokedexListModel> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokedex_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final PokedexListModel listItem = listItems.get(position);
        final int pokeIndex = position+1;

        // Format to 3 digits 000
        String countStr = String.format("#%03d",pokeIndex);
        holder.textViewIdCount.setText(countStr);

        // Set first letter to uppercase format
        final String nameStr = listItem.getPokeName().substring(0, 1).toUpperCase() + listItem.getPokeName().substring(1);
        holder.textViewName.setText(nameStr);

        // Get image url
        final String imgStr = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokeIndex+".png";
        Picasso.with(context)
                .load(imgStr)
                .into(holder.imageViewUrl);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked " + nameStr,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, PokedexDetail.class);
                intent.putExtra("poke_id",String.format("#%03d",pokeIndex));
                intent.putExtra("poke_url",listItem.getPokeUrl());
                intent.putExtra("poke_name",nameStr);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewIdCount;
        public TextView textViewName;
        public ImageView imageViewUrl;
        public ConstraintLayout constraintLayout;
        public RecyclerView rc;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIdCount = (TextView) itemView.findViewById(R.id.textViewIdCount);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            imageViewUrl = (ImageView) itemView.findViewById(R.id.imageViewUrl);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constraintLayout);
            context = itemView.getContext();
        }
    }
}
