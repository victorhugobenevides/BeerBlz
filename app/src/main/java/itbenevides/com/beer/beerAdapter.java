package itbenevides.com.beer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by victorhugo on 16/03/2016.
 */
public class beerAdapter extends RecyclerView.Adapter<beerAdapter.ViewHolder>  {
    private List<Beer> beers;
    private Context context;




    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView name;
        public TextView country;
        public TextView category;
        public TextView position;
        public ViewHolder(View v) {
            super(v);
            name = (TextView)v.findViewById(R.id.name);
            country = (TextView)v.findViewById(R.id.country);
            category= (TextView)v.findViewById(R.id.category);
            position= (TextView)v.findViewById(R.id.position);
            cardView=(CardView)v.findViewById(R.id.card_view);
        }
    }


    public beerAdapter( List<Beer> beers,Context context) {
        this.beers = beers;
        this.context=context;
    }


    @Override
    public beerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.beer_adapter, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.name.setText(beers.get(position).name);
        holder.country.setText("Country: " + beers.get(position).country);
        holder.category.setText("Category: " + beers.get(position).category);
        holder. position.setText(String.valueOf(position));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,InfoBeer.class);
                intent.putExtra("beer", new Gson().toJson(beers.get(position)));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


    }


    @Override
    public int getItemCount() {
        return beers.size();
    }
}


