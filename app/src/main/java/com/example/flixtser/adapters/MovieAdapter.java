package com.example.flixtser.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixtser.DetailActivity;
import com.example.flixtser.R;
import com.example.flixtser.models.movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<movie> movies;

    public MovieAdapter(Context context, List<movie> movies){
        this.context=context;
        this.movies=movies;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView= LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false );
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the movie at the passed in position
        movie film= movies.get(position);
        //Bind the movie data into the viewholder
        holder.bind(film);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvOverview=itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container=itemView.findViewById(R.id.container);
        }

        public void bind(movie film) {
            tvTitle.setText(film.getTitle());
            tvOverview.setText(film.getOverview());
            Glide.with(context).load(film.getPosterPath()).into(ivPoster);

            //Register click listener on the whole row

           container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Navigate to a new activity on tap
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("film", Parcels.wrap(film));
                    context.startActivity(i);
                }
            });
        }
    }


}
