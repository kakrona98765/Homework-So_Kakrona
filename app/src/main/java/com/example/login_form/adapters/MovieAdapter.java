package com.example.login_form.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.login_form.R;
import com.example.login_form.models.MovieModel;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<MovieModel> movieList;

    public MovieAdapter(List<MovieModel> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        MovieModel movie = movieList.get(position);

        holder.tvTitle.setText(movie.getTitle());
        holder.tvRating.setText(String.valueOf(movie.getRating()));
        holder.tvYear.setText(String.valueOf(movie.getYear()));
        holder.tvDuration.setText(movie.getDuration() + " Minutes");
        holder.tvAgeRating.setText(movie.getAgeRating());
        holder.tvGenre.setText(movie.getGenre());
        holder.ivPoster.setImageResource(movie.getImageResId());

    // Set badge type premium or free
        holder.tvBadgeType.setText(movie.getType());
        if ("Free".equalsIgnoreCase(movie.getType())) {
            holder.tvBadgeType.setBackgroundResource(R.drawable.bg_badge_free);
        } else {
            holder.tvBadgeType.setBackgroundResource(R.drawable.bg_badge_premium);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvRating, tvBadgeType, tvYear, tvDuration, tvAgeRating, tvGenre;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivMoviePoster);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvBadgeType = itemView.findViewById(R.id.tvBadgeType);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvAgeRating = itemView.findViewById(R.id.tvAgeRating);
            tvGenre = itemView.findViewById(R.id.tvGenre);
        }
    }
}